<%-- <sly data-sly-use.footer="it.codeland.academy.core.models.FooterLinks">
  <div class="footer__mainLink" data-sly-test="${!footer.footerLinks}">
    <div class="footer__link" data-sly-test="${!footer.footerLinks}">click here to add Footer Main Links</div>
  </div>

  <div class="footer__mainLink" data-sly-list.item="${footer.footerLinks}">
    <a href="${item.link}.html" class="footer__link" target="${item.target}">${item.label}</a>
  </div>
</sly> --%>
<%@page language="java"%>
<%@include file="/libs/foundation/global.jsp"%>
<cq:include script="/libs/wcm/core/components/init/init.jsp"/>

<div class="footer__mainLink">
    <div class="footer__link">click here to add Footer Main Links</div>
  </div>

  <%-- <div class="footer__mainLink" data-sly-list.item="${footer.footerLinks}">
    <a href="${item.link}.html" class="footer__link" target="${item.target}">${item.label}</a>
  </div> --%>
