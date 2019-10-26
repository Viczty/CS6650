package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.LiftRide;
import model.SeasonsList;
import model.Skier;
import model.VerticalList;
import model.VerticalPair;

public class SkiersDao {

  protected ConnectionManager connectionManager;

  private static SkiersDao instance = null;

  protected SkiersDao() {
    connectionManager = new ConnectionManager();
  }

  public static SkiersDao getInstance() {
    if(instance == null) {
      instance = new SkiersDao();
    }
    return instance;
  }

  public void create(int resortId, String seasonId, String dayId, int skierId, LiftRide liftRide) throws SQLException {

    String tmp = "INSERT INTO Skiers(resortId, seasonId, dayId, skierId, time, liftId, vertical) VALUES(?,?,?,?,?,?,?);";
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = connectionManager.getConnection();
      preparedStatement = connection.prepareStatement(tmp);
      preparedStatement.setInt(1, resortId);
      preparedStatement.setString(2, String.valueOf(seasonId));
      preparedStatement.setString(3, dayId);
      preparedStatement.setInt(4, skierId);
      preparedStatement.setInt(5, liftRide.getTime());
      preparedStatement.setInt(6, liftRide.getLiftID());
      preparedStatement.setInt(7, liftRide.getLiftID() * 10);

      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(preparedStatement != null) {
        preparedStatement.close();
      }
    }
  }

  public int getVertical(int resortId, String seasonId, String dayId, int skierId) throws SQLException{
    String tmp = "SELECT SUM(vertical) FROM Skiers WHERE (ResortId=?) and (SeasonId=?) and (DayId=?) and (SkierId=?);";
    Connection connection = null;
    PreparedStatement selectStatement = null;
    ResultSet results = null;

    try {
      connection = connectionManager.getConnection();
      selectStatement = connection.prepareStatement(tmp);
      selectStatement.setInt(1, resortId);
      selectStatement.setString(2, seasonId);
      selectStatement.setString(3, dayId);
      selectStatement.setInt(4, skierId);
      results = selectStatement.executeQuery();

      if(results.next()) {
        int result = results.getInt(1);

        return result;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(selectStatement != null) {
        connection.close();
      }
      if(results != null) {
        results.close();
      }
    }
    return 0;
  }

  public List<VerticalPair> getTotalVertical(int skierId, String[] resortId, String[] seasonId) throws SQLException{
    String tmp = "SELECT SeasonId, SUM(vertical) FROM Skiers WHERE (SkierId=?) and (ResortId=?) and (SeasonId=?);";
    Connection connection = null;
    PreparedStatement selectStatement = null;
    ResultSet results = null;


    try {
      connection = connectionManager.getConnection();
      List<VerticalPair> verticalList = new ArrayList<>();
      if (seasonId == null) {
        String tmp1 = "SELECT SeasonId FROM Skiers WHERE (SkierId=?) and (ResortId=?) GROUP BY SeasonId;";
        Set<String> set = new HashSet<>();
        for (int i = 0; i < resortId.length; i++) {
          selectStatement = connection.prepareStatement(tmp1);
          selectStatement.setInt(1, skierId);
          selectStatement.setInt(2, Integer.valueOf(resortId[i]));
          results = selectStatement.executeQuery();
          while (results.next()) set.add(results.getString(1));
        }
        seasonId = new String[set.size()];
        seasonId = set.toArray(seasonId);
      }
      for (int i = 0; i < resortId.length; i++) {
        for (int j = 0; j < seasonId.length; j++) {
          selectStatement = connection.prepareStatement(tmp);
          selectStatement.setInt(1, skierId);
          selectStatement.setInt(2, Integer.valueOf(resortId[i]));
          selectStatement.setString(3, seasonId[j]);
          results = selectStatement.executeQuery();
          while (results.next()) {
            int totalVert = results.getInt(2);
            verticalList.add(new VerticalPair(results.getString(1), totalVert));
          }
        }

      }
      return verticalList;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(selectStatement != null) {
        connection.close();
      }
      if(results != null) {
        results.close();
      }
    }

  }


}

