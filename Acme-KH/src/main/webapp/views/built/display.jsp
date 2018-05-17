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
<jstl:out value="${built.building.name }"></jstl:out><br>

<spring:message code="building.descritpion"></spring:message>: 
<jstl:out value="${built.building.description }"></jstl:out><br>

<spring:message code="built.cost"></spring:message>: 
<jstl:out value="${built.building.getTotalMaterials(built.lvl)}"></jstl:out><br>

<spring:message code="built.lvl"></spring:message>: 
<jstl:out value="${built.lvl }"></jstl:out>/<jstl:out value="${built.building.maxLvl }"></jstl:out><br>

<jstl:if test="${defense}">
	<spring:message code="building.defense.defense"></spring:message>: 
	<jstl:out value="${built.building.getTotalDefense(built.lvl) }"></jstl:out><br>
	
</jstl:if>

<jstl:if test="${recruiter}">
	<jstl:if test="${troops != null && troops.size()>0 }">
	
		<display:table name="troops" id="row" pagesize="5" requestURI="built/display.do">
	
				<!-- TODO: tabla de troops disponibles para crear-->
		
		</display:table>
	
	</jstl:if>
	
	<jstl:if test="${gummiShips != null && gummiShips.size()>0 }">
	
		<display:table name="gummiShips" id="row" pagesize="5" requestURI="built/display.do">
	
				<!-- TODO: tabla de gummi ships  disponibles para crear-->
		
		</display:table>
	
	</jstl:if>
	
</jstl:if>

<jstl:if test="${warehouse}">
	
	<spring:message code="building.warehouse.materialsSlots"></spring:message>: 
	<jstl:out value="${built.building.getTotalMaterials(built.lvl)}"></jstl:out><br>
	
	<spring:message code="building.warehouse.troopSlots"></spring:message>: 
	<jstl:out value="${built.building.getTotalTroopSlots(built.lvl)}"></jstl:out><br>
	
	<spring:message code="building.warehouse.gummiSlots"></spring:message>: 
	<jstl:out value="${built.building.getTotalGummiSlots(built.lvl) }"></jstl:out><br>


</jstl:if>


<jstl:if test="${livelihood}">
	
	<spring:message code="building.livelihood.timeToRecollect"></spring:message>: 
	<jstl:out value="${built.building.getTotalTime(built.lvl) }"></jstl:out><br>
	
	<spring:message code="building.livelihood.materials"></spring:message>: 
	<jstl:out value="${built.building.getTotalMaterials(built.lvl) }"></jstl:out><br>

</jstl:if>