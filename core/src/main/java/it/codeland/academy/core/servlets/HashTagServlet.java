package it.codeland.academy.core.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.jcr.Node;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = { Servlet.class })
@SlingServletPaths(value = { "/bin/relatedtags" })
@ServiceDescription("Related hashtag for articles Servlet")
public class HashTagServlet extends SlingSafeMethodsServlet {

  private static final long serialVersionUID = 1L;
  private static final Logger log = LoggerFactory.getLogger(HashTagServlet.class);

  @Override
  protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse res)
      throws ServletException, IOException {
    Session session = req.getResourceResolver().adaptTo(Session.class);
    try {
      String tag = req.getParameter("tag");
      String length = req.getParameter("length");
      String orderBy = req.getParameter("orderBy");
      String sortBy = req.getParameter("sortBy");
      List<Node> nodes = new ArrayList<>();
      int maxLength = (length.length() > 0) ? Integer.parseInt(length) : 20;
      List<ArticleData> articles = new ArrayList<>();
      QueryManager queryMgr = session.getWorkspace().getQueryManager();

      String queryText = "SELECT * FROM [cq:PageContent] AS nodes WHERE ISDESCENDANTNODE ([/content/academy-munyemana-unicredit/us/en/unicredit-home-page/magazine]) AND nodes.[cq:tags] LIKE '%"
          + tag + "' ORDER BY [" + orderBy + "] " + sortBy + "";
      Query query = queryMgr.createQuery(queryText, "JCR-SQL2");
      query.setLimit(maxLength);
      QueryResult result = query.execute();

      Iterator<Node> resultNodes = result.getNodes();
      while (resultNodes.hasNext())
        nodes.add(resultNodes.next());

      for (Node item : nodes) {
        String title = item.getProperty("jcr:title").getString();
        Date date = item.getProperty("date").getDate().getTime();
        String image = item.getProperty("image").getString();
        String text = item.getProperty("text").getString();
        String url = item.getParent().getPath() + ".html";

        String[] tags = { tag };
        ArticleData article = new ArticleData(title, tags, image, text,
            new SimpleDateFormat("E dd MMM yyyy").format(date), url);
        articles.add(article);
      }

      res.setCharacterEncoding("UTF-8");
      res.setContentType("application/json");
      res.getWriter().write(new ObjectMapper().writeValueAsString(articles));
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    } finally {
      session.logout();
    }
  }
}
