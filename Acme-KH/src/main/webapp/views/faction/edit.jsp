
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="faction/edit.do" modelAttribute="faction">
<jstl:if test="${faction.id == 0}">
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textbox code="faction.name" path="name"/>
	<acme:textarea code="faction.powerUpDescription" path="powerUpDescription"/>
	<acme:textbox code="faction.extraResources" path="extraResources"/>
	<acme:textbox code="faction.extraAttack" path="extraAttack"/>
	<acme:textbox code="faction.extraDefense" path="extraDefense"/>
	<acme:textbox code="faction.galaxy" path="galaxy"/>
</jstl:if>

<jstl:if test="${faction.id != 0}">
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textbox code="faction.name" path="name"/>
	<acme:textarea code="faction.powerUpDescription" path="powerUpDescription"/>
	<acme:textbox code="faction.extraResources" path="extraResources"/>
	<acme:textbox code="faction.extraAttack" path="extraAttack"/>
	<acme:textbox code="faction.extraDefense" path="extraDefense"/>

</jstl:if>
</form:form>
