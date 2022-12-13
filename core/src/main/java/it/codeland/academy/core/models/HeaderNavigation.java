package it.codeland.academy.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeaderNavigation {

  private static final Logger loggingError = LoggerFactory.getLogger(HeaderNavigation.class);

  @SlingObject
  Resource componentResource;

  public Page getFirstLevelNav() {
    PageManager managePage = componentResource.getResourceResolver().adaptTo(PageManager.class);
    Page currentPage = managePage.getContainingPage(componentResource);

    return currentPage.getAbsoluteParent(4);
  }

  public Page getSecondLevelNav() {
    PageManager manageSecondPage = componentResource.getResourceResolver().adaptTo(PageManager.class);
    Page currentPage = manageSecondPage.getContainingPage(componentResource);
    return currentPage.getAbsoluteParent(2);
  }

}
