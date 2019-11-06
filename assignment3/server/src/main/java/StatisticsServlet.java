import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dal.StatisticsDao;
import model.ResortsList;
import model.SeasonsList;
import model.StatisticsList;

@WebServlet(name = "StatisticsServlet")
public class StatisticsServlet extends HttpServlet {
  protected StatisticsDao statisticsDao;

  @Override
  public void init() throws ServletException {
    statisticsDao = StatisticsDao.getInstance();
  }

  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    String urlPath = req.getPathInfo();
    // check we have a URL!
    if (urlPath == null || urlPath.isEmpty()) {
      StatisticsList statisticsList = null;
      try {
        statisticsList = new StatisticsList(statisticsDao.getStatistics());
      } catch (Exception e) {
        e.printStackTrace();
      }

      String jsonString = new Gson().toJson(statisticsList);
      PrintWriter out = res.getWriter();
      res.setStatus(HttpServletResponse.SC_OK);
      res.setContentType("application/json");
      res.setCharacterEncoding("UTF-8");
      out.print(jsonString);
      out.flush();
    }



  }

  private boolean isUrlValid(String[] urlPath) {
    // TODO: validate the request url path according to the API spec
    // urlPath  = "/1/seasons/2019/day/1/skier/123"
    // urlParts = [, 1, seasons, 2019, day, 1, skier, 123]
    return true;
  }
}
