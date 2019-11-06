package dal;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Resort;
import model.ResortsList;
import model.SeasonsList;
import model.Statistics;

public class StatisticsDao {

  protected ConnectionManager connectionManager;

  private static StatisticsDao instance = null;

  protected StatisticsDao() {
    connectionManager = new ConnectionManager();
  }

  public static StatisticsDao getInstance() {
    if(instance == null) {
      instance = new StatisticsDao();
    }
    return instance;
  }


  public List<Statistics> getStatistics() throws SQLException{
    String tmp = "SELECT URL, operation, AVG(mean), MAX(max) FROM Statistics GROUP BY URL, operation;";
    Connection connection = null;
    PreparedStatement selectStatement = null;
    ResultSet results = null;

    try {
      connection = connectionManager.getConnection();
      selectStatement = connection.prepareStatement(tmp);
      results = selectStatement.executeQuery();
      List<Statistics> list = new ArrayList<>();
      while (results.next()) {
        list.add(new Statistics(results.getString(1), results.getString(2), results.getInt(3), results.getInt(4)));
      }
      return list;
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

  public void update(String URL, String operation, int mean , int max) throws SQLException {
    String tmp1 = "SELECT COUNT(*), AVG(mean), MAX(max) FROM Statistics WHERE (URL=?) and (operation=?);";
    String tmp2 = "DELETE FROM Statistics WHERE (URL=?) and (operation=?);";
    String tmp = "INSERT INTO Statistics(URL, operation, mean, max) VALUES(?,?,?,?);";
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet results = null;

    try {
      connection = connectionManager.getConnection();
      int count = 0;
      int currentMean = 0;
      int currentMax = 0;
      preparedStatement = connection.prepareStatement(tmp1);
      preparedStatement.setString(1, URL);
      preparedStatement.setString(2, operation);
      results = preparedStatement.executeQuery();
      if (results.next()) {
        count = results.getInt(1);
        currentMean = results.getInt(2);
        currentMax = results.getInt(3);
      }
      if (count > 1000) {
        preparedStatement = connection.prepareStatement(tmp2);
        preparedStatement.setString(1, URL);
        preparedStatement.setString(2, operation);
        preparedStatement.executeUpdate();
        mean = (currentMean * count + mean) / (count + 1);
        max = Math.max(currentMax, max);
      }
      preparedStatement = connection.prepareStatement(tmp);
      preparedStatement.setString(1, URL);
      preparedStatement.setString(2, operation);
      preparedStatement.setInt(3, mean);
      preparedStatement.setInt(4, max);

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

}

