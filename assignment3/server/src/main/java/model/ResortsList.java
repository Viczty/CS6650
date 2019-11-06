package model;

import java.util.List;

public class ResortsList {
  protected List<Resort> resorts;

  public ResortsList(List<Resort> resortsList) {
    this.resorts = resortsList;
  }

  public List<Resort> getResortsList() {
    return resorts;
  }

  public void setResortsList(List<Resort> resortsList) {
    this.resorts = resortsList;
  }
}
