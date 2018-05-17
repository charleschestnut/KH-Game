<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<!-- Listing grid -->

<script type="text/javascript">
	function reloadTable(event, reportId) {
		var type = $('select#type').val();
		var div = $('#container');
		var path = "reportUpdate/listByType.do?reportId=" + reportId + "&type=" + type;
		event.preventDefault();
		div.load(path);
		return false;
	}
</script>

<select id="type" onchange="javascript: reloadTable(event, ${report.id})">
	<option value="all"><spring:message code="reportUpdate.showAll"/></option>
	<option value="mine"><spring:message code="reportUpdate.showMine"/></option>
	<option value="resolved"><spring:message code="reportUpdate.showResolved"/></option>
</select>

<div id="container">
<display:table name="reportUpdates" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<spring:message code="report.status" var="status" />
	<display:column title="${status}" property="status" />

	<spring:message code="report.content" var="title" />
	<display:column title="${title}" property="content" />

	<spring:message code="report.date" var="date" />
	<display:column title="${date}" property="date" />

	<spring:message code="master.page.display" var="display" />
	<display:column title="${display}">
		<a
			href="reportUpdate/display.do?reportUpdateId=${row.id}">${display}</a>
	</display:column>
</display:table>
</div>
<!-- Action links -->

<div>
	<security:authorize access="hasRole('GM')">
	<jstl:if test="${report.status ne 'RESOLVED'}">
		<a href="reportUpdate/create.do?reportId=${report.id}"> <spring:message
				code="reportUpdate.create" />
		</a>
		<br />
	</jstl:if>
	</security:authorize>
	<acme:goback />
</div>
