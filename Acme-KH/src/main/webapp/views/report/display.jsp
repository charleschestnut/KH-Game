<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<ul style="list-style-type: disc">

	<li><b><spring:message code="report.title"></spring:message></b>
		<jstl:out value="${report.title}" /></li>

	<li><b><spring:message code="report.content"></spring:message></b>
		<jstl:out value="${report.content}" /></li>
		
	<li><b><spring:message code="report.status"></spring:message></b>
		<jstl:out value="${report.status}" /></li>
		
	<li><b><spring:message code="report.date"></spring:message></b>
		<jstl:out value="${report.date}" /></li>
			
	<li><b><spring:message code="report.creator"></spring:message></b>
		<jstl:out value="${report.keybladeWielder.nickname}" /></li><br/>
		
	<jstl:forEach items="${report.photos}" var="photo">
	<img src="${photo}"/><br/>
	</jstl:forEach>

	<acme:goback/>

</ul>