<%--
 * action-2.jsp
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


<display:table pagesize="${pageSize}" class="displaytag" 
	name="membersInvitations" requestURI="${requestURI}" id="row">

	<spring:message code="master.page.name" var="nameH" />
	<display:column title="${nameH}">
		<jstl:out value="${row.keybladeWielder.worldName}"/> 
	</display:column>
	
	<spring:message code="invitation.orgRange" var="orgRangeH" />
	<display:column title="${orgRangeH}" sortable="true">
		<jstl:out value="${row.orgRange}"/> 
	</display:column>
	
	<spring:message code="master.page.actions" var="actionsH" />
	<display:column title="${actionsH}">
		<acme:action code="invitation.changeRange" url="welcome/index"/>
		<acme:action code="master.page.profile"  url="welcome/index"/> 
	</display:column>
	

</display:table>

