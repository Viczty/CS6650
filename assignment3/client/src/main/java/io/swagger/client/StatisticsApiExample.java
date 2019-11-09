package io.swagger.client;

import io.swagger.client.api.StatisticsApi;
import io.swagger.client.model.APIStats;

public class StatisticsApiExample {
  public static APIStats doGet() throws ApiException {

    StatisticsApi apiInstance = new StatisticsApi();
    ApiClient client = apiInstance.getApiClient();
    String basePath = "https://my-project-1555284861245.appspot.com";
    client.setBasePath(basePath);

    APIStats apiStats = apiInstance.getPerformanceStats();
    return apiStats;

  }
}
