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
			
			if(hoy<=fechaFin){
			/* 	
				document.getElementById(id).innerHTML ='<a href="built/upgrade.do?builtId='+id+'"><spring:message code="built.finish.cosntruct"></spring:message></a>';	
			}
			else{ */
				document.getElementById(id).innerHTML ='<spring:message code="built.in.cosntruction"></spring:message>';	
			}
			
			var x = setInterval(function() {

				  // Get todays date and time
				  var now = new Date().getTime();

				  // Find the distance between now an the count down date
				  var distance = fechaFin - now;

				  // Time calculations for days, hours, minutes and seconds
				  var days = Math.floor(distance / (1000 * 60 * 60 * 24));
				  var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
				  var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
				  var seconds = Math.floor((distance % (1000 * 60)) / 1000);

				  // Display the result in the element with id="demo"
				  document.getElementById("timer"+id).innerHTML = "0" + minutes + ":" + seconds;

				  // If the count down is finished, write some text 
				  if (distance < 0) {
				    clearInterval(x);
				    document.getElementById("timer"+id).innerHTML = '<a href="built/upgrade.do?builtId='+id+'"><spring:message code="built.finish.cosntruct"></spring:message></a>';
				  }
				}, 1000);
		}
		
</script>

<spring:message code="master.page.actions" var="actionHeader"></spring:message>
<spring:message code="built.recruit" var="recruit"></spring:message>
<spring:message code="built.working" var="working"></spring:message>
<spring:message code="built.start.recruit" var="startRecruit"></spring:message>
<spring:message code="built.collect" var="collect"></spring:message>
<spring:message code="built.start.collect" var="startCollect"></spring:message>
<spring:message code="built.unbuild" var="unbuildHeader"></spring:message>
<spring:message code="building.name" var="nameHeader"></spring:message>
<spring:message code="master.page.display" var="displayHeader"></spring:message>

<div class="row row-width">
<jstl:forEach items="${builts}" var="row">
	<div class="card" style="width: 19rem;color:black;">
		<img class="card-img-top" src="https://vignette.wikia.nocookie.net/kingdomhearts/images/3/3b/Twilight_Town-_Clock_Tower_%28Art%29_KHII.png" alt="Card image cap">
		<div class="card-body">
			<div style="padding:10px;"class="row"><h5 class="card-title">${row.building.name}</h5>
			<h6><span style="margin-left:10px;" class="badge badge-info"> Lvl ${row.lvl}</span></h6></div>
			<p class="card-text">${row.building.description}</p>
				<p>
					<spring:message code="built.state" var="stateHeader"></spring:message>
					<jstl:if test="${row.lvl==0 }">
						<div id="${row.id }">
							<script>
								comparaFechaCreacion(
										'${row.creationDate.getYear()}',
										'${row.creationDate.getMonth()}',
										'${row.creationDate.getDate()}',
										'${row.creationDate.getHours()}',
										'${row.creationDate.getMinutes()}',
										'${row.creationDate.getSeconds()}',
										'${row.building.timeToConstruct}',
										'${row.id }');
							</script>
							<div class="timer row"><i style="font-size:20px;vertical-align:middle;"class="material-icons">timer</i><span id="timer${row.id}"></span></div>
						</div>
					</jstl:if>
				</p>
				
				<div class="btn-group">
					<jstl:choose>
						<jstl:when test="${row.lvl==row.building.maxLvl }">
							<spring:message code="built.max"></spring:message>
						</jstl:when>
						<jstl:otherwise>
							<a href="built/upgrade.do?builtId=${row.id }" class="btn-sm btn btn-success"
								title="${row.building.getTotalMaterials(row.lvl) }"><i style="font-size:20px;vertical-align:middle;" class="material-icons">arrow_upward</i><spring:message
									code="built.upgrade"></spring:message></a>
						</jstl:otherwise>
					</jstl:choose>
					
					
					<jstl:if test="${row.lvl>0 }">
			<a class="btn-sm btn btn-warning" href="built/display.do?builtId=${row.id}"><jstl:out value="${displayHeader}"></jstl:out></a>
		</jstl:if>
		<jstl:choose>
					<jstl:when test="${row.building.getClass()=='class domain.Livelihood' && row.activationDate!=null && row.haTerminado(row.building.getTotalTime(row.lvl))}">
						<a class="btn-sm btn btn-primary" href="built/collect.do?builtId=${row.id}"><jstl:out value="${collect}"></jstl:out></a>
					</jstl:when>
					<jstl:when test="${row.building.getClass()=='class domain.Livelihood' && row.activationDate!=null && !row.haTerminado(row.building.getTotalTime(row.lvl)) }">
						<button type="button" class="btn-sm btn btn-primary" disabled><i style="font-size:20px;vertical-align:middle;" class="material-icons">loop</i><jstl:out value="${working }"></jstl:out></button>
					</jstl:when>
					<jstl:when test="${row.building.getClass()=='class domain.Livelihood' && row.activationDate==null }">
						<a class="collect btn-sm btn btn-primary" href="built/startCollect.do?builtId=${row.id}"><jstl:out value="${startCollect}"></jstl:out></a>
					</jstl:when>
					<jstl:when test="${row.building.getClass()=='class domain.Recruiter' && row.activationDate!=null && row.troop!=null && row.haTerminado(row.troop.timeToRecruit)}">
						<a class="btn-sm btn btn-primary" href="TODO: Enlace para crear los recruited con una tropa pasandole un builtId"><jstl:out value="${recruit}"></jstl:out></a>
					</jstl:when>
					<jstl:when test="${row.building.getClass()=='class domain.Recruiter' && row.activationDate!=null && row.troop!=null && !row.haTerminado(row.troop.timeToRecruit) }">
						<button type="button" class="btn-sm btn btn-primary" disabled><jstl:out value="${working }"></jstl:out></button>
					</jstl:when>
					<jstl:when test="${row.building.getClass()=='class domain.Recruiter' && row.activationDate!=null && row.gummiShip!=null && row.haTerminado(row.gummiShip.timeToRecruit)}">
						<a class="btn-sm btn btn-primary" href="TODO: Enlace para crear los recruited con una nave pasandole un builtId. Si el mismo que el anterior poner el mismo enlace aqui"><jstl:out value="${recruit}"></jstl:out></a>
					</jstl:when>
					<jstl:when test="${row.building.getClass()=='class domain.Recruiter' && row.activationDate!=null && row.gummiShip!=null && !row.haTerminado(row.gummiShip.timeToRecruit) }">
						<button type="button" class="btn-sm btn btn-primary" disabled><jstl:out value="${working }"></jstl:out></button>
					</jstl:when>
					<jstl:when test="${row.building.getClass()=='class domain.Recruiter' && row.activationDate==null }">
						<a class="collect btn-sm btn btn-primary" href="built/startRecruit.do?builtId=${row.id}"><jstl:out value="${startRecruit}"></jstl:out></a>
					</jstl:when>
				</jstl:choose>
				</div><br/><br/>
				<a class="btn-sm btn btn-danger" href="built/delete.do?builtId=${row.id}"><i style="font-size:20px;vertical-align:middle;"class="material-icons">clear</i><jstl:out value="${unbuildHeader }"></jstl:out></a>
		</div>
	</div>
</jstl:forEach>
</div>
<%-- <display:table name="builts" id="row" pagesize="5" requestURI="built/list.do">
 --%>

	<%-- <spring:message code="building.name" var="nameHeader"></spring:message>
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
					<jstl:when test="${row.lvl==0}">
						<spring:message code="built.in.cosntruction"></spring:message>
					</jstl:when>
					<jstl:when test="${row.building.getClass()=='class domain.Livelihood' && row.activationDate!=null && row.haTerminado(row.building.getTotalTime(row.lvl))}">
						<a href="built/collect.do?builtId=${row.id}"><jstl:out value="${collect}"></jstl:out></a>
					</jstl:when>
					<jstl:when test="${row.building.getClass()=='class domain.Livelihood' && row.activationDate!=null && !row.haTerminado(row.building.getTotalTime(row.lvl)) }">
						<jstl:out value="${working }"></jstl:out>
					</jstl:when>
					<jstl:when test="${row.building.getClass()=='class domain.Livelihood' && row.activationDate==null }">
						<a href="built/startCollect.do?builtId=${row.id}"><jstl:out value="${startCollect}"></jstl:out></a>
					</jstl:when>
					<jstl:when test="${row.building.getClass()=='class domain.Recruiter' && row.activationDate!=null && row.troop!=null && row.haTerminado(row.troop.timeToRecruit)}">
						<a href="TODO: Enlace para crear los recruited con una tropa pasandole un builtId"><jstl:out value="${recruit}"></jstl:out></a>
					</jstl:when>
					<jstl:when test="${row.building.getClass()=='class domain.Recruiter' && row.activationDate!=null && row.troop!=null && !row.haTerminado(row.troop.timeToRecruit) }">
						<jstl:out value="${working }"></jstl:out>
					</jstl:when>
					<jstl:when test="${row.building.getClass()=='class domain.Recruiter' && row.activationDate!=null && row.gummiShip!=null && row.haTerminado(row.gummiShip.timeToRecruit)}">
						<a href="TODO: Enlace para crear los recruited con una nave pasandole un builtId. Si el mismo que el anterior poner el mismo enlace aqui"><jstl:out value="${recruit}"></jstl:out></a>
					</jstl:when>
					<jstl:when test="${row.building.getClass()=='class domain.Recruiter' && row.activationDate!=null && row.gummiShip!=null && !row.haTerminado(row.gummiShip.timeToRecruit) }">
						<jstl:out value="${working }"></jstl:out>
					</jstl:when>
					<jstl:when test="${row.building.getClass()=='class domain.Recruiter' && row.activationDate==null }">
						<a href="built/startRecruit.do?builtId=${row.id}"><jstl:out value="${startRecruit}"></jstl:out></a>
					</jstl:when>
					<jstl:otherwise>
							----
					</jstl:otherwise>
				</jstl:choose>
	
	</display:column>
	
	<spring:message code="built.unbuild" var="unbuildHeader"></spring:message>
	<display:column title="${unbuildHeader }">
		<a href="built/delete.do?builtId=${row.id}"><jstl:out value="${unbuildHeader }"></jstl:out></a>
	</display:column> --%>
	
	

<%-- </display:table> --%>

<br/>
<acme:cancel url="built/create.do" code="master.page.create"/>
