package model;

public class Skier {
  protected int resortId;
  protected String seasonId;
  protected String dayId;
  protected int skierId;
  protected LiftRide liftRide;

  public Skier(int resortId, String seasonId, String dayId, int skierId, LiftRide liftRide) {
    this.resortId = resortId;
    this.seasonId = seasonId;
    this.dayId = dayId;
    this.skierId = skierId;
    this.liftRide = liftRide;
  }

  public int getResortId() {
    return resortId;
  }

  public void setResortId(int resortId) {
    this.resortId = resortId;
  }

  public String getSeasonId() {
    return seasonId;
  }

  public void setSeasonId(String seasonId) {
    this.seasonId = seasonId;
  }

  public String getDayId() {
    return dayId;
  }

  public void setDayId(String dayId) {
    this.dayId = dayId;
  }

  public int getSkierId() {
    return skierId;
  }

  public void setSkierId(int skierId) {
    this.skierId = skierId;
  }

  public LiftRide getLiftRide() {
    return liftRide;
  }

  public void setLiftRide(LiftRide liftRide) {
    this.liftRide = liftRide;
  }
}
