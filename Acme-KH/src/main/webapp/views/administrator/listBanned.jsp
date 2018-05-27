<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table pagesize="5" class="displaytag" 
	name="users" requestURI="administrator/banned/list.do" id="row">

	<spring:message code="username" var="usernameH" />
	<display:column property="userAccount.username" title="${usernameH}" />
	
	<spring:message code="master.page.actions" var="actionsH" />
	<display:column title="${actionsH}">
	<a href="profile/actor/display.do?username=${row.userAccount.username}"><spring:message code="master.page.view"/></a>
	</display:column>
</display:table>