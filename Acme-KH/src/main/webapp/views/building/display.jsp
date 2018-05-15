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

<spring:message code="building.name"></spring:message>: 
<jstl:out value="${building.name }"></jstl:out><br>

<spring:message code="building.description"></spring:message>: 
<jstl:out value="${building.description }"></jstl:out><br>

<spring:message code="building.cost"></spring:message>: 
<jstl:out value="${building.cost }"></jstl:out><br>

<spring:message code="building.maxLvL"></spring:message>: 
<jstl:out value="${building.maxLvL }"></jstl:out><br>

<spring:message code="building.timeToConstruct"></spring:message>: 
<jstl:out value="${building.timeToConstruct }"></jstl:out><br>


<jstl:if test="${defense}">
	<spring:message code="building.defense.defense"></spring:message>: 
	<jstl:out value="${building.defense }"></jstl:out><br>
	
</jstl:if>

<jstl:if test="${recruiter}">
	<jstl:if test="${troops != null && troops.size()>0 }">
	
		<display:table name="troops" id="row" pagesize="5" requestURI="building/display.do">
	
				<!-- TODO: tabla de troops -->
		
		</display:table>
	
	</jstl:if>
	
	<jstl:if test="${gummiShips != null && gummiShips.size()>0 }">
	
		<display:table name="gummiShips" id="row" pagesize="5" requestURI="building/display.do">
	
				<!-- TODO: tabla de gummi ships -->
		
		</display:table>
	
	</jstl:if>
	
</jstl:if>

<jstl:if test="${warehouse}">
	
	<spring:message code="building.warehouse.materialsSlots"></spring:message>: 
	<jstl:out value="${building.materialsSlots }"></jstl:out><br>
	
	<spring:message code="building.warehouse.troopSlots"></spring:message>: 
	<jstl:out value="${building.troopSlots }"></jstl:out><br>
	
	<spring:message code="building.warehouse.gummiSlots"></spring:message>: 
	<jstl:out value="${building.gummiSlots }"></jstl:out><br>


</jstl:if>

<jstl:if test="${livelihood}">
	
	<spring:message code="building.livelihood.timeToRecollect"></spring:message>: 
	<jstl:out value="${building.timeToRecollect }"></jstl:out><br>
	
	<spring:message code="building.livelihood.materials"></spring:message>: 
	<jstl:out value="${building.materials }"></jstl:out><br>

</jstl:if>


<jstl:if test="${requirements != null && requirements.size()>0 }">
	<spring:message code="building.requirements"></spring:message>
	<display:table name="requirements" id="row" pagesize="5" requestURI="building/display.do">
	
		<spring:message code="building.building" var="buildingHeader"></spring:message>
		<display:column property="requiredBuilding" title="${buildingHeader}"></display:column>
		
		<spring:message code="building.lvl" var="lvlHeader"></spring:message>
		<display:column property="lvl" title="${lvlHeader}"></display:column>
		
		<!-- TODO: ¿SE CUMPLE EL REQUISITO? -->
	
	</display:table>
</jstl:if>