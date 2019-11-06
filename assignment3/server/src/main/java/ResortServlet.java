import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dal.ResortsDao;
import dal.StatisticsDao;
import model.ResortsList;
import model.SeasonsList;

@WebServlet(name = "ResortServlet")
public class ResortServlet extends HttpServlet {
  protected ResortsDao resortsDao;
  protected StatisticsDao statisticsDao;

  @Override
  public void init() throws ServletException {
    resortsDao = ResortsDao.getInstance();
    statisticsDao = StatisticsDao.getInstance();
  }

  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    String urlPath = req.getPathInfo();

    // check we have a URL!
    if (urlPath == null || urlPath.isEmpty()) {
      ResortsList resortsList = null;
      try {
        Timestamp start = new Timestamp(System.currentTimeMillis());
        long beforeGet = start.getTime();
        // resortsList = new ResortsList(resortsDao.getResorts());
        Timestamp after = new Timestamp(System.currentTimeMillis());
        long afterGet = after.getTime();
        int time = (int)(afterGet - beforeGet);
        // statisticsDao.update("/resorts", "GET", time, time);
      } catch (Exception e) {
        e.printStackTrace();
      }

      String jsonString = new Gson().toJson(resortsList);
      PrintWriter out = res.getWriter();
      res.setStatus(HttpServletResponse.SC_OK);
      res.setContentType("application/json");
      res.setCharacterEncoding("UTF-8");
      out.print(jsonString);
      out.flush();
      return;
    }

    String[] urlParts = urlPath.split("/");
    // and now validate url path and return the response status code
    // (and maybe also some value if input is valid)

    SeasonsList seasonsList = null;
    if (!isUrlValid(urlParts)) {
      res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    } else if (urlParts.length == 3) {
      String resortId = urlParts[1];
      try {
        Timestamp start = new Timestamp(System.currentTimeMillis());
        long beforeGet = start.getTime();
        seasonsList = resortsDao.getSeasons(Integer.valueOf(resortId));
        Timestamp after = new Timestamp(System.currentTimeMillis());
        long afterGet = after.getTime();
        int time = (int)(afterGet - beforeGet);
        statisticsDao.update("/resorts/{resortID}/seasons", "GET", time, time);
      } catch (SQLException e) {
        e.printStackTrace();
      }

      String jsonString = new Gson().toJson(seasonsList);
      PrintWriter out = res.getWriter();
      res.setStatus(HttpServletResponse.SC_OK);
      res.setContentType("application/json");
      res.setCharacterEncoding("UTF-8");
      out.print(jsonString);
      out.flush();

    }
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    String urlPath = req.getPathInfo();
    res.setContentType("text/plain");

    String[] urlParts = urlPath.split("/");
    // and now validate url path and return the response status code
    // (and maybe also some value if input is valid)

    if (!isUrlValid(urlParts)) {
      res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    } else {
      res.setStatus(HttpServletResponse.SC_OK);
      // do any sophisticated processing with urlParts which contains all the url params
      // TODO: process url params in `urlParts`
      BufferedReader buffIn = req.getReader();
      String line;
      StringBuilder sb = new StringBuilder();
      int year = 0;
      while ((line = buffIn.readLine()) != null) {
        sb.append(line);
        String s = line.split(" ")[1];
        year = Integer.valueOf(s.substring(0, s.length() - 1));
      }
      int resortsId = Integer.valueOf(urlParts[1]);

      try {
        Timestamp start = new Timestamp(System.currentTimeMillis());
        long beforeGet = start.getTime();
        resortsDao.create(resortsId, year);
        Timestamp after = new Timestamp(System.currentTimeMillis());
        long afterGet = after.getTime();
        int time = (int)(afterGet - beforeGet);
        statisticsDao.update("/resorts/{resortID}/seasons", "POST", time, time);
      } catch (SQLException e) {
        e.printStackTrace();
      }
      res.getWriter().write(sb.toString());
    }
  }

  private boolean isUrlValid(String[] urlPath) {
    // TODO: validate the request url path according to the API spec
    if ((urlPath.length == 1) && (urlPath[0].equals(""))) return true;
    return (urlPath.length != 3) && (urlPath[2].equals("seasons"));
  }
}
