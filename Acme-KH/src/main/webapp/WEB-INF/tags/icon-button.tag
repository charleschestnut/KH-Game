<%--
 * textbox.tag
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" body-content="empty"%>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%-- Attributes --%>

<%@ attribute name="icon" required="true"%>
<%@ attribute name="href" required="false"%>
<%@ attribute name="color" required="true"%>
<%@ attribute name="title" required="false"%>
<%@ attribute name="message" required="false"%>
<%@ attribute name="iconColor" required="false"%>

<jstl:if test="${empty iconColor}">
<jstl:set var="iconColor" value="white"/>
</jstl:if>
<%-- Definition --%>
<jstl:if test="${not empty href}">
	<a href="${href}" class="btn-effect btn-sm btn" style="background-color:${color}"
		title='<spring:message code="${title}"/>'><i 
		style="font-size: 20px; vertical-align: middle; color:${iconColor}"
		class="material-icons">${icon}</i> <jstl:if
			test="${not empty message}">
			<spring:message code="${message}"></spring:message>
		</jstl:if> </a>
</jstl:if>
<jstl:if test="${empty href}">
	<button type="button" class="btn-sm btn"
		style="background-color:${color}" disabled>
		<i style="font-size: 20px; vertical-align: middle;"
			class="material-icons">${icon}</i>
		<jstl:if test="${not empty message}">
			<jstl:out value="${working }"></jstl:out>
		</jstl:if>
	</button>
</jstl:if>