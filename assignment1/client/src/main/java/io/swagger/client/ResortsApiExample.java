package io.swagger.client;

import io.swagger.client.*;
import io.swagger.client.auth.*;
import io.swagger.client.model.*;
import io.swagger.client.api.ResortsApi;

import java.io.File;
import java.util.*;

public class ResortsApiExample {

  public static void doPost() {

    ResortsApi apiInstance = new ResortsApi();
    ApiClient client = apiInstance.getApiClient();
    String basePath = "http://54.211.60.243:8080/server_war";
    client.setBasePath(basePath);
    try {
      List result = apiInstance.getResorts();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ResortsApi#doPost");
      e.printStackTrace();
    }
  }
}
