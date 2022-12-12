<%@include file="/libs/foundation/global.jsp"%>
<cq:include script="/libs/wcm/core/components/init/init.jsp"/>
<%@page session="false"%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Electro classic Unicredit Project</title>
  <cq:includeClientLib categories="academy-munyemana-unicredit.official"/>
</head>
<body>
  <%-- <sly
    data-sly-resource="${'/content/academy-munyemana-unicredit/us/en/unicredit-home-page/jcr:content/header' @resourceType='academy-munyemana-unicredit/components/header'}"
  ></sly> --%>
  <cq:include path="classic Header" resourceType="academy-munyemana-unicredit/components/classicHeader"/>
  <cq:include path="parsys" resourceType="foundation/components/parsys"/>

  <cq:include path="footer" resourceType="academy-munyemana-unicredit/components/classicFooter"/>
  <%-- <div id="academy-hero-slider" data-sly-resource="${'footer' @resourceType='academy-munyemana-unicredit/components/footer'}"></div> --%>
</body>
</html>