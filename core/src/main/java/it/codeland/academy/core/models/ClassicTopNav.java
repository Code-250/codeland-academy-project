package it.codeland.academy.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ClassicTopNav {
  private static final Logger logger = LoggerFactory.getLogger(ClassicTopNav.class);

  @SlingObject
  private ResourceResolver resolver;

  @SlingObject
  private Resource currentResource;

  public List<Map<String, String>> getLinks() {
    List<Map<String, String>> topLinks = new ArrayList<>();

    try {
      Resource linkDetails = currentResource.getChild("linkwithdetails");
      if (linkDetails == null) {
        return Collections.emptyList();
      }
      if (linkDetails != null) {
        for (Resource children : linkDetails.getChildren()) {
          Map<String, String> linksMap = new HashMap<>();
          linksMap.put("linklabel", children.getValueMap().get("linklabel", String.class));
          linksMap.put("icon", children.getValueMap().get("link", String.class));
          linksMap.put("link", children.getValueMap().get("icon", String.class));
          linksMap.put("target", children.getValueMap().get("target", String.class));
          topLinks.add(linksMap);
        }
      }
      logger.info("this iis a samplelog  {}", topLinks.toString());
    } catch (Exception e) {
      // TODO: handle exception
      logger.info("ERROR while getting links {} ", e.getMessage());
    }
    return topLinks;
  }
}
