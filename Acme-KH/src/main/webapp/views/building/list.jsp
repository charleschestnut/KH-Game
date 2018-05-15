<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="buildings" id="row" pagesize="5" requestURI="${requestURI}">

	<spring:message code="building.name" var="nameHeader"></spring:message>
	<display:column property="name" title="${nameHeader}"></display:column>
						  
	<spring:message code="building.descritpion" var="descriptionHeader"></spring:message>
	<display:column title="${descriptionHeader}">
		<jstl:out value="${row.getReduceDescription()}"></jstl:out>
	</display:column>
	
	<spring:message code="building.cost" var="costHeader"></spring:message>
	<display:column property="cost" title="${costHeader}"></display:column>
	
	<security:authorize access="hasRole('PLAYER')">
		<spring:message code="building.build" var="buildHeader"></spring:message>
		<display:column title="${buildHeader}">
			<a href="built/player/edit.do?buildingId=${row.id}"><jstl:out value="${buildHeader}"></jstl:out></a>
		</display:column>
	</security:authorize>
	
	<spring:message code="master.page.display" var="displayHeader"></spring:message>
	<display:column title="${displayHeader}">
		<a href="building/display.do?buildingId=${row.id}"><jstl:out value="${displayHeader}"></jstl:out></a>
	</display:column>
	
	<security:authorize access="hasRole('MANAGER')">
		<jstl:if test="${requestURI=='building/contentManager/myList.do' && !row.isFinal}">
			<spring:message code="master.page.edit" var="editHeader"></spring:message>
			<display:column title="${editHeader}">
				<a href="building/contentManager/edit.do?buildingId=${row.id}"><jstl:out value="${editHeader}"></jstl:out></a>
			</display:column>
		</jstl:if>
	</security:authorize>


</display:table>

<acme:cancel url="building/contentManager/edit.do" code="master.page.create"/>

