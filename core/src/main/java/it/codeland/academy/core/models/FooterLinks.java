package it.codeland.academy.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FooterLinks {
  private static final Logger linkLogger = LoggerFactory.getLogger(FooterLinks.class);

  @SlingObject
  private Resource currentResource;

  public List<Map<String, String>> getFooterLinks() {
    List<Map<String, String>> footerlinks = new ArrayList<>();

    try {
      Resource links = currentResource.getChild("footerlinks");
      if (links != null) {
        for (Resource childLink : links.getChildren()) {
          Map<String, String> linksMap = new HashMap<>();
          linksMap.put("label", childLink.getValueMap().get("label", String.class));
          linksMap.put("link", childLink.getValueMap().get("link", String.class));
          linksMap.put("target", childLink.getValueMap().get("target", String.class));
          footerlinks.add(linksMap);
        }
      } else {
        return Collections.emptyList();
      }
    } catch (Exception e) {
      // TODO: handle exception
      linkLogger.info("\n ERROR while getting footer links {} ", e.getMessage());

    }
    return footerlinks;
  }
}
