package it.codeland.academy.core.models;

import javax.inject.Named;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ArticleModel {
  public Logger LOG = LoggerFactory.getLogger(getClass());

  @ValueMapValue
  @Named("text")
  private String title;
  private String order;

  public String getTitle() {
    return title;
  }

  public String getOrder() {
    return order;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}
