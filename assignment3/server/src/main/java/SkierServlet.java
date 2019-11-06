import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dal.SkiersDao;
import dal.StatisticsDao;
import model.LiftRide;
import model.VerticalList;

@javax.servlet.annotation.WebServlet(name = "SkierServlet")
public class SkierServlet extends javax.servlet.http.HttpServlet {
  protected SkiersDao skiersDao;
  protected StatisticsDao statisticsDao;

  @Override
  public void init() throws ServletException {
    skiersDao = SkiersDao.getInstance();
    statisticsDao = StatisticsDao.getInstance();
  }

  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    String urlPath = req.getPathInfo();

    // check we have a URL!
    if (urlPath == null || urlPath.isEmpty()) {
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
      res.getWriter().write("missing paramterers");
      return;
    }

    String[] urlParts = urlPath.split("/");
    // and now validate url path and return the response status code
    // (and maybe also some value if input is valid)

    if (!isUrlValid(urlParts)) {
      res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    } else if (urlParts.length == 8) {
      int vertical = 0;
      try {
        int resortId = Integer.valueOf(urlParts[1]);
        String seasonId = urlParts[3];
        String dayId = urlParts[5];
        int skierId = Integer.valueOf(urlParts[7]);
        Timestamp start = new Timestamp(System.currentTimeMillis());
        long beforeGet = start.getTime();
        vertical = skiersDao.getVertical(resortId, seasonId, dayId, skierId);
        Timestamp after = new Timestamp(System.currentTimeMillis());
        long afterGet = after.getTime();
        int time = (int)(afterGet - beforeGet);
        statisticsDao.update("/skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}", "GET", time, time);

      } catch (SQLException e) {
        e.printStackTrace();
      }
      res.setStatus(HttpServletResponse.SC_OK);
      res.setContentType("application/json");
      res.setCharacterEncoding("UTF-8");
      res.getWriter().print(vertical);
    } else {
      VerticalList verticalList = null;
      try {
        int skierId = Integer.valueOf(urlParts[1]);
        String[] resortId = req.getParameterValues("resort");
        String[] seasonId = req.getParameterValues("season");
        Timestamp start = new Timestamp(System.currentTimeMillis());
        long beforeGet = start.getTime();
        verticalList = new VerticalList(skiersDao.getTotalVertical(skierId, resortId, seasonId));
        Timestamp after = new Timestamp(System.currentTimeMillis());
        long afterGet = after.getTime();
        int time = (int)(afterGet - beforeGet);
        statisticsDao.update("/skiers/{skierID}/vertical", "GET", time, time);

      } catch (SQLException e) {
        e.printStackTrace();
      }

      String jsonString = new Gson().toJson(verticalList);
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

    // check we have a URL!
    if (urlPath == null || urlPath.isEmpty()) {
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
      res.getWriter().write("missing paramterers");
      return;
    }

    String[] urlParts = urlPath.split("/");
    // and now validate url path and return the response status code
    // (and maybe also some value if input is valid)

    if (!isUrlValid(urlParts)) {
      res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    } else {
      res.setStatus(HttpServletResponse.SC_OK);
      BufferedReader buffIn = req.getReader();
      String line;
      StringBuilder sb = new StringBuilder();
      while ((line = buffIn.readLine()) != null) {
        sb.append(line);
      }
      LiftRide liftride = new Gson().fromJson(sb.toString(), LiftRide.class);
      try {
        int resortId = Integer.valueOf(urlParts[1]);
        String seasonId = urlParts[3];
        String dayId = urlParts[5];
        int skierId = Integer.valueOf(urlParts[7]);
        Timestamp start = new Timestamp(System.currentTimeMillis());
        long beforeGet = start.getTime();
        skiersDao.create(resortId, seasonId, dayId, skierId, liftride);
        Timestamp after = new Timestamp(System.currentTimeMillis());
        long afterGet = after.getTime();
        int time = (int)(afterGet - beforeGet);
        statisticsDao.update("/skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}", "POST", time, time);

      } catch (SQLException e) {
        e.printStackTrace();
        if (e.getErrorCode() == 1205) throw new ServletException(e);
      }
      res.setStatus(HttpServletResponse.SC_OK);
      res.getWriter().write(sb.toString());
    }
  }

  private boolean isUrlValid(String[] urlPath) {
    // TODO: validate the request url path according to the API spec
    if (urlPath.length == 8 && urlPath[2].equals("seasons") && urlPath[4].equals("days") && urlPath[6].equals("skiers")) return true;
    if (urlPath.length == 3 && urlPath[2].equals("vertical")) return true;
    return false;
  }
}
