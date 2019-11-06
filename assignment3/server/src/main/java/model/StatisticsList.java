package model;

import java.util.List;

public class StatisticsList {
  protected List<Statistics> endpointStats;

  public StatisticsList(List<Statistics> endpointStats) {
    this.endpointStats = endpointStats;
  }

  public List<Statistics> getEndpointStats() {
    return endpointStats;
  }

  public void setEndpointStats(List<Statistics> endpointStats) {
    this.endpointStats = endpointStats;
  }
}
