<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<ul style="list-style-type: disc">

	<li><b><spring:message code="report.title"></spring:message></b> <jstl:out
			value="${report.title}" /></li>

	<li><b><spring:message code="report.content"></spring:message></b>
		<jstl:out value="${report.content}" /></li>

	<li><b><spring:message code="report.status"></spring:message></b>
		<jstl:if test="${report.status eq 'ONHOLD'}">
			<spring:message code="report.onhold" />
		</jstl:if> <jstl:if test="${report.status eq 'RESOLVED'}">
			<spring:message code="report.resolved" />
		</jstl:if> <jstl:if test="${report.status eq 'IRRESOLVABLE'}">
			<spring:message code="report.irresolvable" />
		</jstl:if> <jstl:if test="${report.status eq 'WORKING'}">
			<spring:message code="report.working" />
		</jstl:if> <jstl:if test="${report.status eq 'SUSPICIOUS'}">
			<spring:message code="report.suspicious" />
		</jstl:if></li>

	<li><b><spring:message code="report.date"></spring:message></b> <jstl:out
			value="${report.date}" /></li>

	<li><b><spring:message code="report.creator"></spring:message></b>
		<jstl:out value="${report.keybladeWielder.nickname}" /></li>
	<br />

	<jstl:forEach items="${report.photos}" var="photo">
		<img src="${photo}" />
		<br />
	</jstl:forEach>
	
	<jstl:if test="${not empty report.reportUpdates}">
	<spring:message code="report.seeUpdates"/>
	</jstl:if>
	<jstl:forEach items="${report.reportUpdates}" var="reportUpdate">
		<fieldset>
<legend><spring:message code="reportUpdate"></spring:message></legend>
<ul style="list-style-type: disc">

	<li><b><spring:message code="report.creator"></spring:message></b>
		<jstl:out value="${reportUpdate.getCreator().nickname}" /></li>

	<li><b><spring:message code="report.date"></spring:message></b>
		<jstl:out value="${reportUpdate.date}" /></li>
		
	<li><b><spring:message code="report.status"></spring:message></b>
		<jstl:out value="${reportUpdate.status}" /></li>
		
	<li><b><spring:message code="report.content"></spring:message></b>
		<jstl:out value="${reportUpdate.content}" /></li>
			
</ul>
<security:authorize access="hasRole('PLAYER')">
<jstl:if test="${reportUpdate.isSuspicious eq false and report.status ne 'RESOLVED'}">
<a href="reportUpdate/player/markSuspicious.do?reportUpdateId=${reportUpdate.id}&reportId=${report.id}"><spring:message code="reportUpdate.mark.suspicious"/></a>
</jstl:if>
</security:authorize>
</fieldset>
	</jstl:forEach>

	<acme:goback />

</ul>