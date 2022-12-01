package it.codeland.academy.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component(service = { Servlet.class })
@SlingServletResourceTypes(resourceTypes = "cq:Page", methods = "GET", extensions = "json", selectors = "export")

public class ArticleExportServlet extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        Resource res = request.getResource();

        Map<String, String> article = new HashMap<>();

        Resource resource = res.getChild("jcr:content");
        //

        String articleTitle = resource.getValueMap().get("jcr:title", String.class);
        String articleDate = resource.getValueMap().get("date", String.class);
        String articleImage = resource.getValueMap().get("image", String.class);
        String articleTag = resource.getValueMap().get("cq:tags", String.class);
        String articleLink = resource.getParent().getPath() + ".html";

        // //
        article.put("title", articleTitle);
        article.put("date", articleDate.toString());
        article.put("Image Url", articleImage);
        article.put("tags", articleTag);
        article.put("article Link", articleLink);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(article);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(json);
    }
}
