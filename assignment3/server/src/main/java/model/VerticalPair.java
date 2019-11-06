package model;

public class VerticalPair {
  protected String seasonId;
  protected int totalVert;

  public VerticalPair(String seasonId, int totalVert) {
    this.seasonId = seasonId;
    this.totalVert = totalVert;
  }

  public String getSeasonId() {
    return seasonId;
  }

  public void setSeasonId(String seasonId) {
    this.seasonId = seasonId;
  }

  public int getTotalVert() {
    return totalVert;
  }

  public void setTotalVert(int totalVert) {
    this.totalVert = totalVert;
  }
}
