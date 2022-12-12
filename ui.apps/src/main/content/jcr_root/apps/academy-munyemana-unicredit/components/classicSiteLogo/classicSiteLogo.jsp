<%--

  classicImage component.

  

--%><%
%><%@include file="/libs/foundation/global.jsp"%><%
%><%@page session="false" %><%
%><%
	String image = properties.get("fileReference","configure Site Logo");
%>
<img src="<%=image%>" alt="unicredit-image" >