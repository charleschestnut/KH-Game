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



<spring:message code="faction.name"></spring:message>: 
<jstl:out value="${faction.name }"></jstl:out><br>

<spring:message code="faction.descritpion"></spring:message>: 
<jstl:out value="${faction.powerUpDescription }"></jstl:out><br>

<spring:message code="faction.cost"></spring:message>: 
<jstl:out value="${faction.extraResources}"></jstl:out><br>

<spring:message code="faction.lvl"></spring:message>: 
<jstl:out value="${faction.extraAttack }"></jstl:out>/

<spring:message code="faction.cost"></spring:message>: 
<jstl:out value="${faction.extraDefense}"></jstl:out><br>

<spring:message code="faction.lvl"></spring:message>: 
<jstl:out value="${faction.galaxy }"></jstl:out>/
