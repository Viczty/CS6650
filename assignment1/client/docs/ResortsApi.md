# ResortsApi

All URIs are relative to */*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addSeason**](ResortsApi.md#addSeason) | **POST** /resorts/{resortID}/seasons | Add a new season for a resort
[**getResortSeasons**](ResortsApi.md#getResortSeasons) | **GET** /resorts/{resortID}/seasons | get a list of seasons for the specified resort
[**getResorts**](ResortsApi.md#getResorts) | **GET** /resorts | get a list of ski resorts in the database

<a name="addSeason"></a>
# **addSeason**
> addSeason(body, resortID)

Add a new season for a resort

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ResortsApi;


ResortsApi apiInstance = new ResortsApi();
Body body = new Body(); // Body | Specify new Season value
Integer resortID = 56; // Integer | ID of the resort of interest
try {
    apiInstance.addSeason(body, resortID);
} catch (ApiException e) {
    System.err.println("Exception when calling ResortsApi#addSeason");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Body**](Body.md)| Specify new Season value |
 **resortID** | **Integer**| ID of the resort of interest |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getResortSeasons"></a>
# **getResortSeasons**
> SeasonsList getResortSeasons(resortID)

get a list of seasons for the specified resort

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ResortsApi;


ResortsApi apiInstance = new ResortsApi();
Integer resortID = 56; // Integer | ID of the resort of interest
try {
    SeasonsList result = apiInstance.getResortSeasons(resortID);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ResortsApi#getResortSeasons");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **resortID** | **Integer**| ID of the resort of interest |

### Return type

[**SeasonsList**](SeasonsList.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getResorts"></a>
# **getResorts**
> List&lt;ResortsList&gt; getResorts()

get a list of ski resorts in the database

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ResortsApi;


ResortsApi apiInstance = new ResortsApi();
try {
    List<ResortsList> result = apiInstance.getResorts();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ResortsApi#getResorts");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;ResortsList&gt;**](ResortsList.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

