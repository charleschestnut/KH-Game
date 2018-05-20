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

<display:table name="prizes" id="row" pagesize="5" requestURI="prize/list.do">

	<spring:message code="prize.description" var="descriptionHeader"></spring:message>
	<display:column  title="${descriptionHeader}">
	
		<jstl:if test="${row.description.contains('defaultDescription') }">
			<spring:message code="${row.description}"></spring:message>
		</jstl:if>
		<jstl:if test="${!row.description.contains('defaultDescription')}">
			${row.description }
		
		</jstl:if>
	
	</display:column>
						  
	<spring:message code="master.page.munny" var="munnyHeader"></spring:message>
	<display:column property="materials.munny" title="${munnyHeader}"></display:column>
	
	<spring:message code="master.page.mytrhil" var="mytrhilHeader"></spring:message>
	<display:column property="materials.mytrhil" title="${mytrhilHeader}"></display:column>
	
	<spring:message code="master.page.gummyCoal" var="gummyCoalHeader"></spring:message>
	<display:column property="materials.gummiCoal" title="${gummyCoalHeader}"></display:column>
	
	<spring:message code="prize.expirationDate" var="expirationDateHeader"></spring:message>
	<display:column property="date" title="${expirationDateHeader}"></display:column>
	
	<spring:message code="prize.open" var="openHeader"></spring:message>
	<display:column  title="${openHeader}">
	
		<a href="prize/open.do?prizeId=${row.id}"><jstl:out value="${openHeader}"></jstl:out></a>
	
	</display:column>
						 
	


</display:table>



