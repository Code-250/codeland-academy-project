package it.codeland.academy.core.services;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.joda.time.DateTime;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.dam.api.Rendition;
import com.day.cq.dam.api.Asset;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.text.csv.Csv;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreatePageService {
    private static final Logger LOG = LoggerFactory.getLogger(CreatePageService.class);

    private Session session;
    PageManager pageManager;

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    int createdRows = 0;
    int skippedRows = 0;
    int csvRows = 0;
    int processedRows = 0;

    DateTime savedDateTime = null;

    String articlePath = "/content/academy-munyemana-unicredit/us/en/unicredit-home-page/magazine/";
    String templatePath = "/apps/academy-munyemana-unicredit/templates/articlePage";

    public void CreatePage(ResourceResolver resourceResolver) {
        try {
            session = resourceResolver.adaptTo(Session.class);
            pageManager = resourceResolver.adaptTo(PageManager.class);

            Resource articleParent = resourceResolver.getResource(articlePath);

            List<String> existingPages = new ArrayList<>();

            Page pagearticle = pageManager.getContainingPage(articleParent);
            Iterator<Page> pageIterator = pagearticle.listChildren();
            while (pageIterator.hasNext()) {
                Page pg = pageIterator.next();
                existingPages.add(pg.getName());
            }
            if (articleParent == null) {
                LOG.error("\n\n > ###### ERROR ##### artilclesParentPage not found! \n\n");
                return;
            }
            LOG.info("\n\n##### INFO  ##### SUCESS PART 2 \n\n");

            Iterator<String[]> articleData = readArticleCSV(resourceResolver);

            if (articleData != null) {
                Node articleContentNode = articleParent.adaptTo(Node.class);
                Node articleJCRNode = articleContentNode.getNode("jcr:content");

                savedDateTime = new DateTime(articleJCRNode.getProperty("cq:lastModified").getValue().toString());

                Resource articleResource = resourceResolver.getResource(articlePath);

                // DateTime articleDateTime = new
                // DateTime(articleResource.adaptTo(Asset.class).getLastModified());
                DateTime articleDateTime = new DateTime("2022-11-11T13:52:50.697+02:00");
                LOG.info("\n\n##### INFO  ##### SUCESS PART 3"
                        + articleResource.getValueMap().get("cq:lastModified", String.class) + "\n\n");
                // if((savedDateTime !=null) && savedDateTime.equals(articleDateTime)){
                // LOG.error("\n\n > ##### ERROR ##### File Not Updated \n\n");
                // return;
                // }

                articleJCRNode.setProperty("cq:lastModified", articleDateTime.toString());

                LOG.info("\n\n##### INFO  ##### SUCESS PART 4 \n\n");
                List<Map<String, String>> uploadedArticles = new ArrayList<Map<String, String>>();

                while (articleData.hasNext()) {
                    String[] row = articleData.next();
                    row = row[0].split(",");
                    LOG.info("\n\n##### INFO  ##### CSV DATA " + row.length + "  \n\n");
                    // if(row.length == 1){
                    // row = row[0].split(",");
                    // }
                    // if(row.length >= 5){
                    Map<String, String> currentRow = new HashMap<String, String>();
                    DateTime dates = new DateTime();
                    currentRow.put("title", row[1]);
                    currentRow.put("text", row[0]);
                    currentRow.put("image", row[2]);
                    currentRow.put("tags", row[3]);
                    currentRow.put("abstract", row[4]);
                    currentRow.put("date", dates.toString());

                    uploadedArticles.add(currentRow);

                    // }
                }

                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(uploadedArticles);

                LOG.info("\n\n##### INFO  ##### CSV DATA ADDED IN LIST " + json + "  \n\n");
                if (uploadedArticles.size() > 0) {
                    csvRows = uploadedArticles.size() - 1;
                    resetReport(articleJCRNode);

                    for (int i = 1; i < uploadedArticles.size(); i++) {
                        processedRows++;
                        articleJCRNode.setProperty("ProcessedRows", processedRows);
                        Map<String, String> article = uploadedArticles.get(i);
                        Page currentPage = pageManager.getPage(articlePath + article.get("title").toLowerCase() + "/");

                        LOG.info("\n\n##### INFO  ##### skipped this Page" + currentPage + "\t" + articlePath
                                + article.get("title") + "  \n\n");
                        if (currentPage != null) {
                            skippedRows++;
                            articleJCRNode.setProperty("skippedRows", skippedRows);
                            continue;

                        }
                        // else {
                        // for(Page articleIN: )

                        Page newPage = pageManager.create(articlePath, null, templatePath, article.get("title"));
                        if (newPage != null) {
                            Node articlePageJCR = newPage.adaptTo(Node.class).getNode("jcr:content");
                            if (articlePageJCR != null) {
                                updateJCRContent(articlePageJCR, article);
                            }
                            createdRows++;
                            articlePageJCR.setProperty("created Articles", createdRows);
                            // }
                        }

                    }
                }
                articleJCRNode.setProperty("state", "completed");
                session.save();

                LOG.info("\n \n  --------- Report Started --------- \n \n --------- Created Articles: " + createdRows
                        + " --------- \n --------- Skipped Articles[Existing ones]: " + skippedRows
                        + "  ---------  \n  --------- CSV Articles: " + csvRows
                        + "  ---------  \n  --------- Processed Articles: "
                        + processedRows + "  ---------  \n --------- Report Ended --------- \n \n ");

            } else {
                LOG.error("\n\n > ##### ERROR ##### CSV DATA EMPTY \n\n");
            }

        } catch (Exception e) {
            LOG.error("\n\n##### ERROR #####  creating page \t " + e.getMessage() + "\t"
                    + ExceptionUtils.getStackTrace(e) + "\n\n");
        }
    }

    private void updateJCRContent(Node articlePageJCR, Map<String, String> article) {
        try {
            articlePageJCR.setProperty("jcr:title", article.get("title"));
            articlePageJCR.setProperty("text", article.get("text"));
            articlePageJCR.setProperty("image", article.get("image"));
            articlePageJCR.setProperty("cq:tags", article.get("tags"));
            articlePageJCR.setProperty("date", article.get("date"));
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("\n \n  --------- error [updateJcrContent] " + e.getMessage()
                    + " --------- \n \n ");
        }
    }

    private void resetReport(Node articleJCRNode) {
        try {
            articleJCRNode.setProperty("state", "processing");
            articleJCRNode.setProperty("csv Articles", csvRows);
            articleJCRNode.setProperty("created Articles", createdRows);
            articleJCRNode.setProperty("skipped Articles", skippedRows);
            articleJCRNode.setProperty("processed Articles", processedRows);

            LOG.info("\n\n##### INFO  ##### CSV DATA IN NODE OF PATH : " + articleJCRNode.getPath() + " \n\n");

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("\n \n  - error [resetReport] " + e.getMessage() + " -\n \n ");
        }
        return;
    }

    public Iterator<String[]> readArticleCSV(ResourceResolver resolver) {
        try {
            Resource csvpath = resolver.getResource("/content/dam/academy-munyemana-unicredit/articles.csv");
            if (csvpath != null) {
                Asset asset = csvpath.adaptTo(Asset.class);
                Rendition assetRendition = asset.getOriginal();
                InputStream inputStream = assetRendition.getStream();

                Csv csv = new Csv();

                csv.setFieldDelimiter('\"');
                csv.setFieldSeparatorRead('|');

                Iterator<String[]> articleIterator = csv.read(inputStream, null);
                LOG.info("\n\n##### INFO  ##### CSV READ SUCESS \n\n");

                return articleIterator;

            } else {
                LOG.error("\n\n##### ERROR ##### CSV NOT FOUND! \n\n");
                return null;
            }
        } catch (Exception e) {
            LOG.error("\n\n##### ERROR #####  READ ARTICLE CSV \t " + e.getMessage() + "\n\n");
            return null;
        }
    }

}
