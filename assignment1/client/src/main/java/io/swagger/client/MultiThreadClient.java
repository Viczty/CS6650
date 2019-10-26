package io.swagger.client;

import io.swagger.client.*;
import io.swagger.client.auth.*;
import io.swagger.client.model.*;
import io.swagger.client.api.ResortsApi;
import io.swagger.client.api.SkiersApi;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.Random;

public class MultiThreadClient {

  private static int numThreads;
  private int numSkiers = 50000;
  private int numLifts = 40;
  private int numRuns = 10;
  private String IP;

  private int success = 0;
  private int failure = 0;

  List<Long> latency = Collections.synchronizedList(new ArrayList<Long>());
  List<String> output = Collections.synchronizedList(new ArrayList<String>());


  public synchronized void successInc() {
    success++;
  }

  public synchronized void failureInc() {
    failure++;
  }

  public MultiThreadClient(int numThreads, int numSkiers, int numLifts, int numRuns, String IP) {
    this.numThreads = numThreads;
    this.numSkiers = numSkiers;
    this.numLifts = numLifts;
    this.numRuns = numRuns;
    this.IP = IP;
  }

  public static void main(String[] args) throws InterruptedException {
    final ResortsApiExample rae = new ResortsApiExample();
    final SkiersApiExample sae = new SkiersApiExample();
    System.out.println("Enter number of threads: ");
    Scanner scanner = new Scanner(System.in);
    int threads = Integer.valueOf(scanner.nextLine());
    final MultiThreadClient client = new MultiThreadClient(threads, 20000, 40, 20, "http://54.211.60.243:8080/server_war");
    final CountDownLatch total = new CountDownLatch(numThreads + numThreads / 2);
    final CountDownLatch phase1 = new CountDownLatch(numThreads / 10);
    final CountDownLatch phase2 = new CountDownLatch(numThreads / 10);
    final CountDownLatch phase3 = new CountDownLatch(numThreads / 5);

    Random random = new Random();
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    long before = timestamp.getTime();

    for (int i = 0; i < numThreads / 4; i++) {
      Runnable thread = () -> {
        try {
          // wait for the main thread to tell us to start
          int skiers = client.numSkiers / (client.numThreads / 4);
          for (int j = 0; j < client.numRuns * 0.1 * skiers; j++) {
            boolean added = false;
            Timestamp start = new Timestamp(System.currentTimeMillis());
            long beforePost = start.getTime();
            while (!added) {
              int skierId = random.nextInt(client.numSkiers);
              int liftId = random.nextInt(client.numLifts);
              int time = 1 + random.nextInt(90);
              start = new Timestamp(System.currentTimeMillis());
              beforePost = start.getTime();
              try {
                sae.doPost(skierId, liftId, time);
                client.successInc();
                added = true;
              } catch (Exception e) {
                added = false;
                client.failureInc();
              }
            }
            long afterPost = new Timestamp(System.currentTimeMillis()).getTime();
            String current = start.toString() + ",POST," + (afterPost - beforePost) + ",201";
            synchronized (client.latency) {
              client.latency.add(afterPost - beforePost);
            }
            synchronized (client.output) {
              client.output.add(current);
            }


          }
        } finally {
          // we've finished - let the main thread know
          phase1.countDown();
          total.countDown();
        }
      };
      new Thread(thread).start();

    }
    phase1.await();

    for (int i = 0; i < numThreads; i++) {
      Runnable thread = () -> {
        try {
          // wait for the main thread to tell us to start
          int skiers = client.numSkiers / client.numThreads;
          for (int j = 0; j < client.numRuns * 0.8 * skiers; j++) {
            boolean added = false;
            Timestamp start = new Timestamp(System.currentTimeMillis());
            long beforePost = start.getTime();
            while (!added) {
              int skierId = random.nextInt(client.numSkiers);
              int liftId = random.nextInt(client.numLifts);
              int time = 91 + random.nextInt(270);
              start = new Timestamp(System.currentTimeMillis());
              beforePost = start.getTime();
              try {
                sae.doPost(skierId, liftId, time);
                client.successInc();
                added = true;
              } catch (Exception e) {
                added = false;
                client.failureInc();
              }
            }
            long afterPost = new Timestamp(System.currentTimeMillis()).getTime();
            String current = start.toString() + ",POST," + (afterPost - beforePost) + ",201";
            synchronized (client.latency) {
              client.latency.add(afterPost - beforePost);
            }
            synchronized (client.output) {
              client.output.add(current);
            }

          }
        } finally {
          // we've finished - let the main thread know
          phase2.countDown();
          total.countDown();
        }
      };
      new Thread(thread).start();

    }

    phase2.await();

    for (int i = 0; i < numThreads / 4; i++) {
      Runnable thread = () -> {
        try {
          // wait for the main thread to tell us to start
          int skiers = client.numSkiers / (client.numThreads / 4);
          for (int j = 0; j < client.numRuns * 0.1 * skiers; j++) {
            boolean added = false;
            Timestamp start = new Timestamp(System.currentTimeMillis());
            long beforePost = start.getTime();
            while (!added) {
              int skierId = random.nextInt(client.numSkiers);
              int liftId = random.nextInt(client.numLifts);
              int time = 361 + random.nextInt(60);
              start = new Timestamp(System.currentTimeMillis());
              beforePost = start.getTime();
              try {
                sae.doPost(skierId, liftId, time);
                sae.doGet(skierId, liftId, time);
                client.successInc();
                added = true;
              } catch (Exception e) {
                added = false;
                client.failureInc();
              }
            }
            long afterPost = new Timestamp(System.currentTimeMillis()).getTime();
            String current = start.toString() + ",POST," + (afterPost - beforePost) + ",201";
            synchronized (client.latency) {
              client.latency.add(afterPost - beforePost);
            }
            synchronized (client.output) {
              client.output.add(current);
            }

          }
        } finally {
          // we've finished - let the main thread know
          phase3.countDown();
          total.countDown();
        }
      };
      new Thread(thread).start();

    }

    phase3.await();
    total.await();
    timestamp = new Timestamp(System.currentTimeMillis());
    long after = timestamp.getTime();
    // Client1
    System.out.println("success " + client.success);
    System.out.println("failure " + client.failure);
    System.out.println("wall time " + (after - before));

    FileWriter fileWriter = null;
    try {
      fileWriter = new FileWriter("output.csv");
      // Write a new record to the CSV file
      for (String line : client.output) {
        fileWriter.append(line);
        fileWriter.append("\n");
      }
    } catch (Exception e) {
      System.out.println("Error in CsvFileWriter!");
      e.printStackTrace();
    } finally {

      try {
        fileWriter.flush();
        fileWriter.close();
      } catch (Exception e) {
        System.out.println("Error while flushing/closing fileWriter!");
        e.printStackTrace();
      }

    }

    Long[] response = client.latency.toArray(new Long[client.latency.size()]);
    Arrays.sort(response);
    Long sum = 0L;
    for (Long res : response) {
      sum += res;
    }

    // Client2
    System.out.println("mean " + sum / response.length);
    System.out.println("median " + response[response.length / 2]);
    System.out.println("throughput " + 1000 * response.length / (float) (after - before));
    System.out.println("p99 " + response[(int) (response.length * 0.99)]);
    System.out.println("max " + response[response.length - 1]);

    try {
      Process	p = Runtime.getRuntime().exec("curl http://skier-1649773410.us-west-2.elb.amazonaws.com/server_war/statistics");
      p.waitFor();
      BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
      String line = "";
      StringBuilder output = new StringBuilder();
      while ((line = reader.readLine())!= null)
      {
        output.append(line + "\n");
      }
      System.out.println(output.toString());
    } catch(Exception e) {
      e.printStackTrace();
    }

  }
}