<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="profile/actor/register.do" modelAttribute="actor" class="form-signin">
	<acme:textbox code="username" path="userAccount.username" />
	<acme:password code="password" path="userAccount.password" />
	<form:hidden path="userAccount.authorities" />
	<acme:textbox code="nickname" path="nickname" />
	<acme:textbox code="name" path="name" />
	<acme:textbox  code="surname" path="surname" />
	<acme:textbox  code="email" path="email"/>
	<acme:textbox  code="phone" path="phone"/><br/>
	
	<form:checkbox path="hasConfirmedTerms" value="true" />
	<spring:message code="master.page.readTermsAndConditions" />
	<form:errors cssClass="error" path="hasConfirmedTerms" /><br/><br/>
	
	<%-- <acme:submit code="user.register"  name="register"/> --%>
	<input type="submit" name="register" class="btn btn-pink"
		value="<spring:message code="master.page.signup"/>" />
</form:form>