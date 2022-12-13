package it.codeland.academy.core.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ClassicHeader {

  private static final Logger loggingError = LoggerFactory.getLogger(ClassicHeader.class);
  private List<String> firstLevelNav = new ArrayList<String>();
  private List<String> secondLevelNav = new ArrayList<String>();

  @SlingObject
  Resource componentResource;

  public List<String> getFirstLevelNav() {
    PageManager managePage = componentResource.getResourceResolver().adaptTo(PageManager.class);
    Page currentPage = managePage.getContainingPage(componentResource);
    Iterator<Page> LevelIterator = currentPage.getAbsoluteParent(4).listChildren();
    while (LevelIterator.hasNext()) {
      Page page = LevelIterator.next();
      if (page != null) {
        if (page.getContentResource() != null) {
          firstLevelNav.add(page.getName());
        }
      }
    }
    return firstLevelNav;
  }

  public List<String> getSecondLevelNav() {
    PageManager manageSecondPage = componentResource.getResourceResolver().adaptTo(PageManager.class);
    Page currentPage = manageSecondPage.getContainingPage(componentResource);
    Iterator<Page> LevelIterator = currentPage.getAbsoluteParent(2).listChildren();
    while (LevelIterator.hasNext()) {
      Page page = LevelIterator.next();
      if (page != null) {
        if (page.getContentResource() != null) {
          secondLevelNav.add(page.getName());
        }
      }
    }
    return secondLevelNav;
  }
}
