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


<script>
		
		function comparaFechaCreacion(year,month,day,hour,minutes,seconds,time,id){
			var hoy= new Date();
			var anho= parseInt(year) + 1900;
			var min= parseInt(minutes)+ parseInt(time);
			var fechaFin= new Date(anho,month,day,hour,min,seconds);
			
			
			//document.getElementById(id).innerHTML =hoy + ' ================= '+ fecha +' ========='+ res;	
			
			if(hoy>fechaFin){
				
				document.getElementById(id).innerHTML ='<a href="built/upgrade.do?builtId='+id+'"><spring:message code="built.finish.cosntruct"></spring:message></a>';	
			}
			else{
				document.getElementById(id).innerHTML ='<spring:message code="built.in.cosntruction"></spring:message>';	
			}
		
			
		}
		
		
		
	
	</script>

<display:table name="builts" id="row" pagesize="5" requestURI="built/list.do">

	<spring:message code="building.name" var="nameHeader"></spring:message>
	<display:column property="building.name" title="${nameHeader}"></display:column>
	
	<spring:message code="built.lvl" var="lvlHeader"></spring:message>
	<display:column property="lvl" title="${lvlHeader}"></display:column>
	
	<spring:message code="built.state" var="stateHeader"></spring:message>
	<display:column title="${stateHeader }" >
	<jstl:if test="${row.lvl==0 }">
		<div id="${row.id }">
			<script>
				comparaFechaCreacion('${row.creationDate.getYear()}','${row.creationDate.getMonth()}','${row.creationDate.getDate()}','${row.creationDate.getHours()}','${row.creationDate.getMinutes()}', '${row.creationDate.getSeconds()}','${row.building.timeToConstruct}','${row.id }');
			</script>
		</div>
	</jstl:if>
	<jstl:if test="${row.lvl>0 }">
		<spring:message code="built.constructed"></spring:message>
		
	</jstl:if>
		
		
	
	</display:column>
	
	<display:column  title="probando">
		<jstl:if test="${row.building.getClass()=='class domain.Livelihood'}">
		${row.haTerminado(row.building.getTotalTime(row.lvl))} <br> =====
		<br>
		${row.building.getTotalTime(row.lvl)}
		<br>
		${row.lvl}
		</jstl:if>
		</display:column>
	
	<spring:message code="built.upgrade" var="upgradeHeader"></spring:message>	
	<display:column title="${upgradeHeader }">
		<jstl:choose >
			<jstl:when test="${row.lvl==0 }">
				<spring:message code="built.in.cosntruction"></spring:message>
			</jstl:when>
			<jstl:when test="${row.lvl==row.building.maxLvl }">
				<spring:message code="built.max"></spring:message>
			</jstl:when>
			<jstl:otherwise>
				<a href="built/upgrade.do?builtId=${row.id }" title="${row.building.getTotalMaterials(row.lvl) }"><spring:message code="built.upgrade"></spring:message></a>
			</jstl:otherwise>
		</jstl:choose>	
	</display:column>
	
	<spring:message code="master.page.display" var="displayHeader"></spring:message>	
	<display:column title="${displayHeader}">
		<jstl:if test="${row.lvl>0 }">
			<a href="built/display.do?builtId=${row.id}"><jstl:out value="${displayHeader }"></jstl:out></a>
		</jstl:if>
	</display:column>
	
	<spring:message code="master.page.actions" var="actionHeader"></spring:message>
	<spring:message code="built.recruit" var="recruit"></spring:message>
	<spring:message code="built.working" var="working"></spring:message>
	<spring:message code="built.start.recruit" var="startRecruit"></spring:message>
	<spring:message code="built.collect" var="collect"></spring:message>
	<spring:message code="built.start.collect" var="startCollect"></spring:message>
	<display:column title="${actionHeader}">
	
		<jstl:choose>
					<jstl:when test="${row.building.getClass()=='class domain.Livelihood' && row.activationDate!=null && row.haTerminado(row.building.getTotalTime(row.lvl))}">
						<a href="built/collect.do?builtId=${row.id}"><jstl:out value="${collect}"></jstl:out></a>
					</jstl:when>
					<jstl:when test="${row.building.getClass()=='class domain.Livelihood' && row.activationDate!=null && !row.haTerminado(row.building.getTotalTime(row.lvl)) }">
						<jstl:out value="${working }"></jstl:out>
					</jstl:when>
					<jstl:when test="${row.building.getClass()=='class domain.Livelihood' && row.activationDate==null }">
						<a href="built/collect.do?builtId=${row.id}"><jstl:out value="${startCollect}"></jstl:out></a>
					</jstl:when>
					<jstl:when test="${row.building.getClass()=='class domain.Recruiter' && row.activationDate!=null && row.haTerminado(row.building.getTotalTime(row.lvl))}">
						<a href="TODO: Enlace para crear los recruited pasandole un builtId"><jstl:out value="${recruit}"></jstl:out></a>
					</jstl:when>
					<jstl:when test="${row.building.getClass()=='class domain.Recruiter' && row.activationDate!=null && !row.haTerminado(row.building.getTotalTime(row.lvl)) }">
						<jstl:out value="${working }"></jstl:out>
					</jstl:when>
					<jstl:when test="${row.building.getClass()=='class domain.Recruiter' && row.activationDate==null }">
						<a href="built/recruit.do?builtId=${row.id}"><jstl:out value="${startRecruit}"></jstl:out></a>
					</jstl:when>
					<jstl:otherwise>
							----
					</jstl:otherwise>
				</jstl:choose>
	
	</display:column>
	
	<spring:message code="built.unbuild" var="unbuildHeader"></spring:message>
	<display:column title="${unbuildHeader }">
		<a href="built/delete.do?builtId=${row.id}"><jstl:out value="${unbuildHeader }"></jstl:out></a>
	</display:column>
	
	

</display:table>

<acme:cancel url="built/create.do" code="master.page.create"/>
