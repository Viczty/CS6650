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

public class ResortsDao {

  protected ConnectionManager connectionManager;

  private static ResortsDao instance = null;

  protected ResortsDao() {
    connectionManager = new ConnectionManager();
  }

  public static ResortsDao getInstance() {
    if(instance == null) {
      instance = new ResortsDao();
    }
    return instance;
  }

  public int create(int resortId, int season) throws SQLException {

    String tmp = "INSERT INTO Resorts(ResortId, ResortName, SeasonId) VALUES(?,?,?);";
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = connectionManager.getConnection();
      preparedStatement = connection.prepareStatement(tmp);
      preparedStatement.setInt(1, resortId);
      preparedStatement.setString(2, String.valueOf(resortId));
      preparedStatement.setInt(3, season);

      preparedStatement.executeUpdate();
      return season;
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

  public List<Resort> getResorts(Connection connection) throws SQLException{
    String tmp = "SELECT * FROM Resorts;";
    //Connection connection = null;
    PreparedStatement selectStatement = null;
    ResultSet results = null;

    try {
      //connection = connectionManager.getConnection();
      selectStatement = connection.prepareStatement(tmp);
      results = selectStatement.executeQuery();
      List<Resort> list = new ArrayList<>();
      while (results.next()) {
        list.add(new Resort(results.getInt(1), results.getString(2), results.getInt(3)));
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

  public SeasonsList getSeasons(int resortId) throws SQLException{
    String tmp = "SELECT * FROM Resorts WHERE ResortId=?;";
    Connection connection = null;
    PreparedStatement selectStatement = null;
    ResultSet results = null;

    try {
      connection = connectionManager.getConnection();
      selectStatement = connection.prepareStatement(tmp);
      selectStatement.setInt(1, resortId);
      results = selectStatement.executeQuery();
      SeasonsList seasonsList = new SeasonsList();
      while (results.next()) {
        seasonsList.getSeasonList().add(results.getString(3));
      }
      return seasonsList;
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

