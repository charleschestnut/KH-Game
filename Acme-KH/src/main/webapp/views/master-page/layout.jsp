<%--
 * layout.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!DOCTYPE html>
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<base
	href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />



 <link rel="apple-touch-icon" sizes="57x57" href="https://kingdomhearts.com/apple-icon-57x57.png">
    <link rel="apple-touch-icon" sizes="60x60" href="https://kingdomhearts.com/apple-icon-60x60.png">
    <link rel="apple-touch-icon" sizes="72x72" href="https://kingdomhearts.com/apple-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="76x76" href="https://kingdomhearts.com/apple-icon-76x76.png">
    <link rel="apple-touch-icon" sizes="114x114" href="https://kingdomhearts.com/apple-icon-114x114.png">
    <link rel="apple-touch-icon" sizes="120x120" href="https://kingdomhearts.com/apple-icon-120x120.png">
    <link rel="apple-touch-icon" sizes="144x144" href="https://kingdomhearts.com/apple-icon-144x144.png">
    <link rel="apple-touch-icon" sizes="152x152" href="https://kingdomhearts.com/apple-icon-152x152.png">
    <link rel="apple-touch-icon" sizes="180x180" href="https://kingdomhearts.com/apple-icon-180x180.png">
    <link rel="icon" type="image/png" sizes="192x192" href="https://kingdomhearts.com/android-icon-192x192.png">
    <link rel="icon" type="image/png" sizes="32x32" href="https://kingdomhearts.com/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="96x96" href="https://kingdomhearts.com/favicon-96x96.png">
    <link rel="icon" type="image/png" sizes="16x16" href="https://kingdomhearts.com/favicon-16x16.png">
<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="scripts/jquery-ui.js"></script>
<script type="text/javascript" src="scripts/jmenu.js"></script>

<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

<!-- Custom styles for this template -->
    <link href="https://getbootstrap.com/docs/4.0/examples/jumbotron/jumbotron.css" rel="stylesheet">
<link rel="stylesheet" href="styles/common.css" type="text/css">
<link rel="stylesheet" href="styles/jmenu.css" media="screen" type="text/css" />
<link rel="stylesheet" href="styles/displaytag.css" type="text/css">

<link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Dancing+Script" rel="stylesheet">

<title><tiles:insertAttribute name="title" ignore="true" /></title>

<script type="text/javascript">
function getUrlParameter(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    var results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
};

function showErrorMessage(){
	var msg = getUrlParameter("message");
	var div = document.getElementById("alertMessage");
	if(msg.includes("error.message.")){
		div.style.display = '';
		div.getElementsByTagName("span")[0].innerHTML = msg;
	}
}
 

	$(document).ready(function() {
	});

	function askSubmission(msg, form) {
		if (confirm(msg))
			form.submit();
	}
	
	function relativeRedir(loc) {	
		var b = document.getElementsByTagName('base');
		if (b && b[0] && b[0].href) {
  			if (b[0].href.substr(b[0].href.length - 1) == '/' && loc.charAt(0) == '/')
    		loc = loc.substr(1);
  			loc = b[0].href + loc;
		}
		window.location.replace(loc);
	}
</script>

<link rel="stylesheet" type="text/css" href="//cdnjs.cloudflare.com/ajax/libs/cookieconsent2/3.0.3/cookieconsent.min.css" />
      <script src="//cdnjs.cloudflare.com/ajax/libs/cookieconsent2/3.0.3/cookieconsent.min.js"></script>
      <script>
         window.addEventListener("load", function(){
         window.cookieconsent.initialise({
           "palette": {
             "popup": {
               "background":"rgba(0, 0, 0, 0.8)",
               "text": "#ffffff"
             },
             "button": {
               "background": "#b8d8ff"
             }
           },
           "theme": "classic",
           "content": {
             "message": "<spring:message code='master.page.cookiesMessage'/>",
             "dismiss": "<spring:message code='master.page.cookiesOk'/>",
             "link": "<spring:message code='master.page.cookiesPage'/>",
             "href":"legaltext/index.do"
           }
         })});
      </script>

</head>

<body>
<script>
$('.btn-material').popover(options)
document.body.onload = function() {showErrorMessage()};
</script>
<div class="alert alert-danger alert-dismissible fade show" id="alertMessage" style="display:none;" role="alert" >
  <span></span>
  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
    <span aria-hidden="true">&times;</span>
  </button>
</div>
	<tiles:insertAttribute name="header" />
	
    <main role="main">
		
      <!-- Main jumbotron for a primary marketing message or call to action -->
       

      <div class="container layout">
        <h1 class="title-header">
			<tiles:insertAttribute name="title" />
			<hr />
		</h1>
		<div  class="body-center">
		<tiles:insertAttribute name="body"/>	
		<jstl:if test="${message != null}">
			<br />
			<span class="message"><spring:message code="${message}" /></span>
		</jstl:if>	
		</div>
      </div> <!-- /container -->

    </main>

    <footer >
      <tiles:insertAttribute name="footer" />
    </footer>
    
    
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>