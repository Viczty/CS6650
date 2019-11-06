package model;

public class Statistics {
  protected String URL;
  protected String operation;
  protected int mean;
  protected int max;

  public Statistics(String URL, String operation, int mean, int max) {
    this.URL = URL;
    this.operation = operation;
    this.mean = mean;
    this.max = max;
  }

  public String getURL() {
    return URL;
  }

  public void setURL(String URL) {
    this.URL = URL;
  }

  public String getOperation() {
    return operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }

  public int getMean() {
    return mean;
  }

  public void setMean(int mean) {
    this.mean = mean;
  }

  public int getMax() {
    return max;
  }

  public void setMax(int max) {
    this.max = max;
  }
}
