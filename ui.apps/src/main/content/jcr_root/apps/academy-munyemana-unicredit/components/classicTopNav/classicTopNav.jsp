<%@include file="/libs/foundation/global.jsp"%>
<cq:include script="/libs/wcm/core/components/init/init.jsp"/>
<%@page session="false"%>

<c:set var="topNav" value="<%=resource.adaptTo(TopNav.class)%>"/>


<c:forEach var="navigations" items="${}" data-sly-use.header="it.codeland.academy.core.models.TopNav">
  <div class="navbar__link" data-sly-test="${!header.links}">Click here to add a top links</div>

  <div class="navbar__links" data-sly-list.item="${header.links}">
    <a class="navbar__link" href="${item.icon}.html" target="${item.target}">
      <div class="${item.link}"></div>
      <p class="text--label">${item.linklabel}</p>
    </a>
  </div>
</c:forEach>