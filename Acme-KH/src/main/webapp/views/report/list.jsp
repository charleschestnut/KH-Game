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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->

<display:table name="reports" id="row" 
    requestURI="${requestURI}" 
    pagesize="5" class="displaytag">
	
	<spring:message code="report.title" var="title" />
	<display:column title="${title}" property="title" />
	
	<spring:message code="report.date" var="date" />
	<display:column title="${date}" property="date" />
	
	<spring:message code="report.status" var="status" />
	<display:column title="${status}" property="status"/>
	
	<spring:message code="master.page.view" var="display" />
	<display:column title="${display}">
	<a href="report/display.do?reportId=<jstl:out value="${row.id}" />" ><jstl:out value="${display}" /></a>
	</display:column>
	
	
	<spring:message code="report.seeUpdates" var="seeUpdates" />
	<display:column title="${seeUpdates}">
	<a href="reportUpdate/list.do?reportId=<jstl:out value="${row.id}" />" ><jstl:out value="${seeUpdates}" /></a>
	</display:column>
	
	<security:authorize access="hasRole('GM')">
	<spring:message code="reportUpdate.create" var="createUpdate" />
	<display:column title="${createUpdate}">
	<jstl:if test="${row.status == 'RESOLVED'}">
	<spring:message code="reportUpdate.noUpdate"/>	
	</jstl:if>
	<jstl:if test="${row.status != 'RESOLVED'}">
	<a href="reportUpdate/create.do?reportId=<jstl:out value="${row.id}" />" ><jstl:out value="${createUpdate}" /></a>
	</jstl:if>
	</display:column>
	</security:authorize>


</display:table>

<!-- Action links -->
<security:authorize access="hasRole('PLAYER')">
<div>
	<a href="report/player/create.do"> 
		<spring:message code="report.create" />
	</a>
</div>
</security:authorize>