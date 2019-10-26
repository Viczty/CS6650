package io.swagger.client;

import io.swagger.client.api.StatisticsApi;
import io.swagger.client.model.APIStats;

public class StatisticsApiExample {
  public static APIStats doGet() throws ApiException {

    StatisticsApi apiInstance = new StatisticsApi();
    ApiClient client = apiInstance.getApiClient();
    String basePath = "http://skier-1649773410.us-west-2.elb.amazonaws.com/server_war";
    client.setBasePath(basePath);

    APIStats apiStats = apiInstance.getPerformanceStats();
    return apiStats;

  }
}
