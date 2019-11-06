package model;

public class Resort {
  protected int resortId;
  protected String resortName;
  protected int seasonId;

  public Resort(int resortId, String resortName, int seasonId) {
    this.resortId = resortId;
    this.resortName = resortName;
    this.seasonId = seasonId;
  }

  public int getResortId() {
    return resortId;
  }

  public void setResortId(int resortId) {
    this.resortId = resortId;
  }

  public String getResortName() {
    return resortName;
  }

  public void setResortName(String resortName) {
    this.resortName = resortName;
  }

  public int getSeasonId() {
    return seasonId;
  }

  public void setSeasonId(int seasonId) {
    this.seasonId = seasonId;
  }

}
