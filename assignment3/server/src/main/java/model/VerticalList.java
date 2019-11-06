package model;

import java.util.ArrayList;
import java.util.List;

public class VerticalList {
  protected List<VerticalPair> resorts;

  public VerticalList(List<VerticalPair> resorts) {
    this.resorts = resorts;
  }

  public List<VerticalPair> getResorts() {
    return resorts;
  }

  public void setResorts(List<VerticalPair> resorts) {
    this.resorts = resorts;
  }
}
