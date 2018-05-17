<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Sample Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="report/list.do"><spring:message code="report.list" /></a></li>
					<li><a href="reportUpdate/admin/listSuspicious.do"><spring:message code="reportUpdate.list.suspicious" /></a></li>
					<li><a href="legaltext/index.do"><spring:message code="master.page.terms" /></a></li>
					<li><a href="administrator/action-2.do"><spring:message code="master.page.administrator.action.2" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('PLAYER')">
			<li><a class="fNiv"><spring:message	code="master.page.player" /></a>
				<ul>	
					<li><a href="report/player/list.do"><spring:message code="report.list" /></a></li>
					<li><a href="report/player/create.do"><spring:message code="report.create" /></a></li>
					<li><a href="built/list.do"><spring:message code="master.page.myBuilding" /></a></li>
					<li><a href="item/player/shopItemsList.do"><spring:message code="master.page.shop" /></a></li>
					<li><a href="item/player/ownedItemsList.do"><spring:message code="master.page.myItems" /></a></li>
					<li><a href="item/player/activeItemsList.do"><spring:message code="master.page.activeItems" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('PLAYER')">
			<li><a class="fNiv"><spring:message	code="master.page.organization" /></a>
				<ul>	
					<li><a href="organization/list.do"><spring:message code="organization.list" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('MANAGER')">
			<li><a class="fNiv"><spring:message	code="master.page.manager" /></a>
				<ul>
					<li><a href="building/contentManager/myList.do"><spring:message code="master.page.myBuilding" /></a></li>
					<li><a href="item/manager/create.do"><spring:message code="master.page.createItem" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('GM')">
					<li><a href="report/list.do"><spring:message code="report.list" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="profile/actor/display.do"> <spring:message code="master.page.myprofile"></spring:message></a>  </li>
					<li><a href="building/list.do"> <spring:message code="master.page.availableBuilding"></spring:message></a>  </li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

