<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table pagesize="5" class="displaytag" 
	name="battles" requestURI="battle/listBattles.do" id="row">

	<spring:message code="attackerOwner" var="attackerOwnerH" />
	<display:column property="attackerOwner" title="${attackerOwnerH}" >
	</display:column>

	<spring:message code="isWon" var="isWonHeader" />
	<display:column property="isWon" title="${isWonHeader}" />


	<spring:message code="master.page.actions" var="actionsH" />
	<display:column title="${actionsH}">
	<a href="battle/display.do?battleId=${row.id}"><spring:message code="master.page.view"/></a>
	</display:column>
	

</display:table>