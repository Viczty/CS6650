package io.swagger.client;

import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;

public class SkiersApiExample {
  public static void doPost(int skierId, int liftId, int time) throws ApiException {

    SkiersApi apiInstance = new SkiersApi();
    ApiClient client = apiInstance.getApiClient();
    String basePath = "http://skier-1649773410.us-west-2.elb.amazonaws.com/server_war";
    client.setBasePath(basePath);

    LiftRide lift = new LiftRide();
    lift.setLiftID(liftId);
    lift.setTime(time);
    apiInstance.writeNewLiftRide(lift, 2019, "1", "123", skierId);

  }

  public static String doGet(int skierId, int liftId, int time) throws ApiException {

    SkiersApi apiInstance = new SkiersApi();
    ApiClient client = apiInstance.getApiClient();
    String basePath = "http://skier-1649773410.us-west-2.elb.amazonaws.com/server_war";
    client.setBasePath(basePath);

    String total = apiInstance.getSkierDayVertical(2019, "1", "123", skierId);
    return total;

  }
}
