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
	name="factions" requestURI="${requestURI}" id="row">

	<spring:message code="master.page.name" var="nameH" />
	<display:column property="name" title="${nameh}">
		<jstl:out value="${row.name}"/> 
	</display:column>
	
	<spring:message code="master.page.galaxy" var="galaxyH" />
	<display:column property="galaxy" title="${galaxyH}">
		<jstl:out value="${row.content}"/> 
	</display:column>

</display:table>