<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<!-- Listing grid -->

<display:table name="reportUpdates" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<%-- <spring:message code="report" var="reportH" />
	<display:column title="${reportH}">
	<jstl:out value="${report.title}"/>
	</display:column>
	
	<spring:message code="report.status" var="status" />
	<display:column title="${report.status}" property="status"/> --%>

	<spring:message code="report.status" var="status" />
	<display:column title="${status}" property="status" />

	<spring:message code="report.content" var="title" />
	<display:column title="${title}" property="content" />

	<spring:message code="report.date" var="date" />
	<display:column title="${date}" property="date" />

	<spring:message code="master.page.display" var="display" />
	<display:column title="${display}">
		<a
			href="reportUpdate/display.do?reportUpdateId=${row.id}&reportId=${reportId}">${display}</a>
	</display:column>

	<%-- <spring:message code="report.creator" var="status" />
	<display:column title="${row.getCreator()}" property="status"/> --%>

	<%-- <security:authorize access="hasRole('GM')">
	<spring:message code="report.seeUpdates" var="seeUpdates" />
	<display:column title="${seeUpdates}">
	<a href="reportUpdate/list.do?reportId=<jstl:out value="${row.id}" />" ><jstl:out value="${seeUpdates}" /></a>
	</display:column>
	</security:authorize> --%>



</display:table>

<!-- Action links -->

<div>
	<security:authorize access="hasRole('GM')">
		<a href="reportUpdate/create.do?reportId=${reportId}"> <spring:message
				code="reportUpdate.create" />
		</a>
		<br />
	</security:authorize>
	<acme:goback />
</div>
