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


<form:form action="organization/edit.do" modelAttribute="organization">
	
	<form:hidden path="version" />
	<form:hidden path="id" />
	<form:hidden path="creationDate" />
	
	
	<acme:textbox code="master.page.name" path="name" />
	<br>
	<acme:textbox code="master.page.description" path="description" />
	<br>
		
	
	
	<acme:submit name="save" code="master.page.save"/>
	<acme:cancel url="building/contentManager/myList.do" code="master.page.cancel"/>

</form:form>