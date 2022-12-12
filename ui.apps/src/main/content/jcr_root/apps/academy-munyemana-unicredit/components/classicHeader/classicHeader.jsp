<%@page language="java"%>
<%@page import="it.codeland.academy.core.models.ClassicHeader"%>
<%@page import="com.day.cq.wcm.api.Page"%>
<%@include file="/libs/foundation/global.jsp"%>
<cq:include script="/libs/wcm/core/components/init/init.jsp"/>

<c:set var="navigation" value="<%= resource.adaptTo(ClassicHeader.class)%>"/>

<header class="header ucg-icons__newPackage">
    <nav class="navbar container-fixed">
      <div class="navbar__zero-level">
        <%-- <div class="navbar__logo" data-sly-resource="${'logo' @ resourceType='academy-munyemana-unicredit/components/logo'}"></div> --%>
        <div class="navbar__logo">
          <cq:include path="siteLogo" resourceType="academy-munyemana-unicredit/components/classicSiteLogo"/>
        </div>
        <%-- <div class="navbar__links">
          <div id="academy-to-navigations" data-sly-resource="${'top-nav' @resourceType='academy-munyemana-unicredit/components/top-nav'}"></div>
        </div> --%>
        <a class="navbar__entry-point" href="#">
          <div class="icon icon-online-Banking_A"></div>
          <div class="entry-point__text text--label">ONLINE BANKING</div>
        </a>
      </div>

        <div class="navbar__hamburger">
          <div class="navbar__first-level">
              <div class="navbar__back text--label">
                  <div class="icon icon-back"></div>
                  <span>BACK</span>
              </div>
              <div class="first-level__items">
                <c:forEach var="nav" items="${navigation.firstLevelNav}">
                  <div data-nav-1="${nav}" class="first-level__item ${nav}">
                      <span class="item__text text--label">${nav}</span>
                      <span class="icon icon-forth"></span>
                  </div>
                </c:forEach>
              </div>
              <div class="navbar__languages">
                  <div class="languages__selector">
                      <p class="selector__text text--label">ITA</p>
                      <span class="icon icon-down"></span>
                      <div class="selector__items">
                          <a class="selector__item selector__item--active text--label" data-lang="ITA" data-lang-dk="ITALIANO"
                            data-lang-m="IT" href="#">ITALIANO</a>
                          <a class="selector__item text--label" data-lang="ENG" href="#" data-lang-dk="ENGLISH"
                            data-lang-m="EN">ENGLISH</a>
                          <a class="selector__item selector__item--disabled text--label" data-lang="DE" data-lang-dk="DEUTSCHE"
                            data-lang-m="DE" href="#">DEUTSCHE</a>
                      </div>
                  </div>
              </div>
              <c:forEach var="secondLevel" items="${navigation.secondLevelNav}">
                <div data-sly-test="${!secondLevel}" class="navbar__second-level">
                    <div class="second-level__items ${secondLevel}" data-nav-2="${secondLevel}">
                        <div class="container">
                            <a class="second-level__item--title text--paragraph-title">
                                ${secondLevel}
                            </a>
                            <%-- <c:forEach var="secondLevelChild" items="${navigation.secondLevelNav}">
                            <a data-sly-test="${!secondLevel}" class="second-level__item text--body">${secondLevel}</a>
                            </c:forEach> --%>
                        </div>
                    </div>
                </div>
              </c:forEach>
          </div>
        </div>
    </nav>
    <div class="hamburger">
      <div class="icon"></div>
    </div>
    <!-- ******************* START SEARCH OVERLAY  COMPONENT ******************* -->
    <aside class="ucg-search">
      <div class="ucg-search__wrap">
        <span class="ucg-search--close icon icon-close"></span>
        <div class="panel ucg_search">
          <div class="container">
            <input class="findAsYouType" type="text" name="querySearch"
              data-resultspage="/content/unicreditgroup-eu/it/search.html" placeholder="Typing" data-threshold="3"
              data-lang="it" />
            <span class="icon icon-search"></span>
          </div>
          <div class="searchSuggestion">
            <div class="autocomplete-suggestions">
              <div class="autocomplete-suggestion text--body" data-index="0">
                Typing
              </div>
              <div class="autocomplete-suggestion text--body" data-index="0">
                Typing <strong>suggestion two</strong>
              </div>
              <div class="autocomplete-suggestion text--body" data-index="1">
                Typing <strong>suggestion three</strong>
              </div>
              <div class="autocomplete-suggestion text--body" data-index="2">
                Typing <strong>suggestion four</strong>
              </div>
              <div class="autocomplete-suggestion text--body" data-index="3">
                Typing <strong>suggestion five</strong>
              </div>
              <div class="autocomplete-suggestion text--body" data-index="4">
                Typing <strong>suggestion six</strong>
              </div>
            </div>

            <button class="button button--arrow">MORE DETAILS</button>
          </div>
        </div>
      </div>
    </aside>
    <!-- ******************* END OF SEARCH OVERLAY COMPONENT ******************* -->
  </header>