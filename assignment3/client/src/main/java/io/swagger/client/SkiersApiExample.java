package io.swagger.client;

import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;

public class SkiersApiExample {
  public static void doPost(int skierId, int liftId, int time) throws ApiException {

    SkiersApi apiInstance = new SkiersApi();
    ApiClient client = apiInstance.getApiClient();
    String basePath = "https://my-project-1555284861245.appspot.com";
    client.setBasePath(basePath);

    LiftRide lift = new LiftRide();
    lift.setLiftID(liftId);
    lift.setTime(time);
    apiInstance.writeNewLiftRide(lift, 2019, "1", "123", skierId);

  }

  public static String doGet(int skierId, int liftId, int time) throws ApiException {

    SkiersApi apiInstance = new SkiersApi();
    ApiClient client = apiInstance.getApiClient();
    String basePath = "https://my-project-1555284861245.appspot.com";
    client.setBasePath(basePath);

    String total = apiInstance.getSkierDayVertical(2019, "1", "123", skierId);
    return total;

  }
}
