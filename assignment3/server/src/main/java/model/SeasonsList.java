package model;

import java.util.ArrayList;
import java.util.List;

public class SeasonsList {
  protected List<String> seasons;

  public SeasonsList() {
    this.seasons = new ArrayList<>();
  }

  public SeasonsList(List<String> seasons) {
    this.seasons = seasons;
  }

  public List<String> getSeasonList() {
    return seasons;
  }

  public void setSeasonList(List<String> seasonList) {
    this.seasons = seasonList;
  }
}
