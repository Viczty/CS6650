package io.swagger.client;

import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;

public class SkiersApiExample {
  public static void doPost(int skierId, int liftId, int time) throws ApiException {

    SkiersApi apiInstance = new SkiersApi();
    ApiClient client = apiInstance.getApiClient();
    String basePath = "http://54.211.60.243:8080/server_war";
    client.setBasePath(basePath);

    LiftRide lift = new LiftRide();
    lift.setLiftID(liftId);
    lift.setTime(time);
    apiInstance.writeNewLiftRide(lift, 2019, "1", "123", skierId);
    // System.out.println("post success");

  }
}
