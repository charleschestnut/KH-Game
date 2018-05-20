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
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

 <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark justify-content-between">
      <div class="headerbar-logo navbar-brand">
    	<a href="https://square-enix-games.com">
      		<img class="headerbar-logo-img" src="https://cdn.sqexeu.com/headerbar/images/SE_Logo_White.svg" width="155px">
    	</a>
 	 </div>
 	 <span class="btn-group btn-group-sm" role="group" aria-label="language">
<jstl:set var="lang" value="es" />
<jstl:choose>
	<jstl:when test="${ pageContext.response.locale == lang }">
    <button type="button" onClick="javascript:preventRedirect('language=en')" class="btn btn-secondary-info"><img src="http://www.iraqposting.com/shipping/images/england32.png" width="16px"/></button> 
   <button type="button" class="btn btn-secondary"><img src="http://e04-marca.uecdn.es/iconos/v1.x/v1.0/banderas-todo/32/s/Spain.png" width="16px"/></button>
  </jstl:when>
	<jstl:otherwise>
     <button type="button" class="btn btn-secondary"><img src="http://www.iraqposting.com/shipping/images/england32.png" width="16px"/></button> 
   <button type="button" onClick="javascript:preventRedirect('language=es')" class="btn btn-secondary-info"><img src="http://e04-marca.uecdn.es/iconos/v1.x/v1.0/banderas-todo/32/s/Spain.png" width="16px"/></button>
  </jstl:otherwise>
</jstl:choose>
</span>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav ml-auto" >
         
         <security:authorize access="isAnonymous()">
			<li class="nav-item"><a class="nav-link" href="profile/actor/register.do"><spring:message code="master.page.signup" /></a></li>
			<li class="nav-item"><a class="nav-link" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		</security:authorize>
          
        </ul>
      </div>
    </nav>
    <!----------------->
    
     <div class="section home">
        
              <div class="video-home">
                <img src="https://kingdomhearts.com/img/header/kingdom-hearts-header-still.jpg" width="100%" class="header-still">
              </div>
        <img class="header-logo" src="images/acme_battle.png" alt="KINGDOM HEARTS">
        </div>
        <nav class="navbar navbar-expand-lg navbar-dark " style="background-color: #1a1e3d;  border-bottom: 2px solid #fff; padding: 0px;">
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNavDropdown">
    <ul class="navbar-nav mx-auto">
      <li class="nav-item">
        <a class="nav-link" href="#">
        <img src="https://kingdomhearts.com/img/header/icons/home-icon.png" align="center" class="nav-icon" alt="home">
        Home</a>
      </li>
      
      	<security:authorize access="hasRole('ADMIN')">
			<li class="nav-item dropdown navbar-dark">
			<a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			<img src="https://kingdomhearts.com/img/header/icons/games-icon.png" align="center" class="nav-icon">
			<spring:message	code="master.page.administrator" /></a>
				<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<a class="dropdown-item" href="report/list.do"><spring:message code="report.list" /></a>
					<a class="dropdown-item" href="reportUpdate/admin/listSuspicious.do"><spring:message code="reportUpdate.list.suspicious" /></a>
					<a class="dropdown-item" href="legaltext/index.do"><spring:message code="master.page.terms" /></a>
					<a class="dropdown-item" href="administrator/action-2.do"><spring:message code="master.page.administrator.action.2" /></a>				
				</div>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('PLAYER')">
			<li class="nav-item dropdown">
			<a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			<img src="https://kingdomhearts.com/img/header/icons/characters-icon.png" align="center" class="nav-icon" >
			<spring:message	code="master.page.player" /></a>
				<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<a class="dropdown-item" href="report/player/list.do"><spring:message code="report.list" /></a>
					<a class="dropdown-item" href="report/player/create.do"><spring:message code="report.create" /></a>
					<a class="dropdown-item" href="built/list.do"><spring:message code="master.page.myBuilding" /></a>
					<a class="dropdown-item" href="item/player/shopItemsList.do"><spring:message code="master.page.shop" /></a>
					<a class="dropdown-item" href="item/player/ownedItemsList.do"><spring:message code="master.page.myItems" /></a>
					<a class="dropdown-item" href="item/player/activeItemsList.do"><spring:message code="master.page.activeItems" /></a>
				</div>
			</li>
		</security:authorize>

		<security:authorize access="hasRole('PLAYER')">
			<li class="nav-item dropdown">
			<a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			<img src="https://kingdomhearts.com/img/header/icons/characters-icon.png" align="center" class="nav-icon">
			<spring:message	code="master.page.organization" /></a>
				<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">	
					<a class="dropdown-item" href="organization/list.do"><spring:message code="organization.list" /></a>
				</div>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('MANAGER')">
			<li class="nav-item dropdown">
			<a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			<img src="https://kingdomhearts.com/img/header/icons/connect-icon.png" align="center" class="nav-icon">
			<spring:message	code="master.page.manager" /></a>
				<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">	
					<a class="dropdown-item" href="building/contentManager/myList.do"><spring:message code="master.page.myBuilding" /></a>
					<a class="dropdown-item" href="item/manager/create.do"><spring:message code="master.page.createItem" /></a>
				</div>
			</li>
		</security:authorize>	
		
		<security:authorize access="hasRole('GM')">
		      <li class="nav-item"><a class="nav-link" href="report/list.do">
		      <img src="https://kingdomhearts.com/img/header/icons/connect-icon.png" align="center" class="nav-icon">
		      <spring:message code="report.list" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li class="nav-item dropdown">
				<a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					<img src="https://kingdomhearts.com/img/header/icons/characters-icon.png" align="center" class="nav-icon">
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<a class="dropdown-item" href="profile/actor/display.do"> <spring:message code="master.page.myprofile"></spring:message></a>
					<a class="dropdown-item" href="building/list.do"> <spring:message code="master.page.availableBuilding"></spring:message></a>
					<a class="dropdown-item" href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a>
				</div>
			</li>
		</security:authorize>
    </ul>
  </div>
</nav>


<script type="text/javascript">
function preventRedirect(path){
	var currentUrl = window.location.href;
	
	if(currentUrl.indexOf("language=en") > 0){
		currentUrl = currentUrl.replace("language=en", path);
	}else if(currentUrl.indexOf("language=es") > 0){
		currentUrl = currentUrl.replace("language=es", path);
	}else if(currentUrl.indexOf("?")>0){
		currentUrl += "&" + path;
	}else{
		currentUrl += "?" + path;
	}
	window.location.replace(currentUrl);
}
</script>

