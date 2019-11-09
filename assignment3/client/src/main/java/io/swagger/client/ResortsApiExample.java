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
    String basePath = "https://my-project-1555284861245.appspot.com";
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
