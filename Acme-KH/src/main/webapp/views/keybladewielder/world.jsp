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
 <%@taglib prefix="display" uri="http://displaytag.sf.net"%><%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
 
<jstl:if test="${puedoEnviarInvitation eq true and  hasOrganization eq false}">
	<acme:action code="invitation.send"  url="/organization/invitation/edit.do?username=${usernameInvitation}"/>
</jstl:if>
<fieldset>
	<legend>Actor</legend>
	<img src="${user.getAvatarImage()}" width="100px" height="100px"/>
	<p>
		<jstl:out value="${user.name}"/>
		<jstl:out value="${user.surname}"/>
		<br/>
		<jstl:out value="${user.nickname}"/> - 
		<jstl:out value="${user.userAccount.username}"/>  - 
		<b>
			<jstl:out value="${user.getActorTypeName()}"/>
		</b>
	</p>
	<jstl:if test="${user.getClass().name  eq 'domain.KeybladeWielder'}">
		<p>
			<br/> Datos de KeybladeWielder (TO DO):

			<br/>
			<b>
				<spring:message code="wins"/>:
			</b>
			<jstl:out value="${user.wins}"/>
			<br/>
			<b>
				<spring:message code="loses"/>:
			</b>
			<jstl:out value="${user.loses}"/>
		</p>
		x:<jstl:out value="${user.worldCoordinates.x}"/>
		y:<jstl:out value="${user.worldCoordinates.y}"/>
		z:<jstl:out value="${user.worldCoordinates.z}"/>
		<jstl:choose>
			<jstl:when test="${user.worldCoordinates.z%2 == 0}">
				<div class="galaxy" style="background-image:url(./images/dark-galaxy.png)">
					<h3 class="title-header">
						<jstl:out value="${user.worldName}"/>
					</h3>
					<div class="world">
						<img src="./images/worlds/(${user.getWorldImage()}).png" alt="Skytsunami" />
					</div>
				</div>
			</jstl:when>
			<jstl:otherwise>
				<div class="galaxy" style="background-image:url(./images/light-galaxy.png)">
					<h3 class="title-header">
						<jstl:out value="${user.worldName}"/>
					</h3>
					<div class="world">
						<img src="./images/worlds/(${user.getWorldImage()}).png"" alt="Skytsunami" />
					</div>
				</div>
			</jstl:otherwise>
		</jstl:choose>
	</jstl:if>
	<jstl:if test="${user.userAccount.getOwner()}">
		<br/>
		<acme:action code="master.page.edit"  url="/profile/actor/edit.do"/>
	</jstl:if>
</fieldset>
