<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<jstl:if test="${not empty reportUpdate.administrator}">
	<jstl:set value="blueText" var="blueText" />
</jstl:if>

<div class="card" style="color: #212529;">
  <div class="card-body">
  <div class="row">
  <div class="col-9">
  	<ul class="list-group list-group-flush">
  	  <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="report.type"/>:</b></div>	<div class="col-6">
  	  <jstl:if test="${report.isBug eq true}">
  	  <jstl:out value="Bug"/>
  	  </jstl:if>
  	   <jstl:if test="${report.isBug eq false}">
  	   <spring:message code="report.asistence" />
  	  </jstl:if>
  	  </div></div></li>
  	  <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="content"/>:</b></div>	<div class="col-6"><jstl:out value="${reportUpdate.content}"/></div></div></li>
  	  <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="date"/>:</b></div>		<div class="col-6"><jstl:out value="${reportUpdate.date}"/></div></div></li>
  	  <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="report.creator"/>:</b></div>		<div class="col-6"><jstl:out value="${reportUpdate.getCreator().nickname}"/></div></div></li>
      <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="status"/>:</b></div>
  	 <div class="col-6"> <jstl:if test="${reportUpdate.status eq 'ONHOLD'}">
			<div class="row"><div class="col-6"><spring:message code="report.onhold" /></div></div></li>
		</jstl:if> 
		<jstl:if test="${reportUpdate.status eq 'RESOLVED'}">
			<div class="row"><div class="col-6"><spring:message code="report.resolved" />
		</jstl:if> 
		<jstl:if test="${reportUpdate.status eq 'IRRESOLVABLE'}">
			<div class="row"><div class="col-6"><spring:message code="report.irresolvable" />
		</jstl:if> 
		<jstl:if test="${reportUpdate.status eq 'WORKING'}">
			<div class="row"><div class="col-6"><spring:message code="report.working" />
		</jstl:if> <jstl:if test="${reportUpdate.status eq 'SUSPICIOUS'}">
			<div class="row"><div class="col-6"><spring:message code="report.suspicious" />
		</jstl:if>
		</div></div></li>
	</ul>
  </div>
  </div>
  </div>
  <div class="card-footer text-muted row">
 <security:authorize access="hasRole('PLAYER')">
	<jstl:if
		test="${reportUpdate.isSuspicious eq false and myReport eq true}">
				<acme:action color="warning" code="reportUpdate.mark.suspicious" url="reportUpdate/player/markSuspicious.do?reportUpdateId=${reportUpdate.id}&reportId=${report.id}&reportDisplay=false"/>&nbsp;
	</jstl:if>
</security:authorize>

<br />

<jstl:if test="${report.status ne 'RESOLVED' and ownUpdate eq true}">
	<acme:action url="reportUpdate/edit.do?reportUpdateId=${reportUpdate.id}&reportId=${report.id}" code="master.page.edit" color="pink"/>
</jstl:if>
<security:authorize access="hasRole('ADMIN')">
	<jstl:set var="admin" value="true"/>
</security:authorize>
<jstl:if test="${report.status ne 'RESOLVED' or (reportUpdate.isSuspicious eq true and admin eq true)}">
	<acme:action color="success" url="reportUpdate/create.do?reportId=${report.id}" code="reportUpdate.create"/>
</jstl:if>

<acme:goback />
<jstl:if test="${reportUpdate.isSuspicious eq true}">
	&nbsp;&nbsp;<spring:message code="reportUpdate.marked.suspicious" />
	<br />
</jstl:if>
  </div>
</div>

<%-- <fieldset>
	<legend>
		<spring:message code="report"></spring:message>
	</legend>
	<ul style="list-style-type: disc">

		<li><b><spring:message code="report.title"></spring:message></b>
			<jstl:out value="${report.title}" /></li>

		<li><b><spring:message code="report.content"></spring:message></b>
			<jstl:out value="${report.content}" /></li>

		<li><b><spring:message code="report.status"></spring:message></b>
			<jstl:out value="${report.status}" /></li>

		<li><b><spring:message code="report.date"></spring:message></b> <jstl:out
				value="${report.date}" /></li>

		<li><b><spring:message code="report.creator"></spring:message></b>
			<jstl:out value="${report.keybladeWielder.nickname}" /></li>

	</ul>
</fieldset>

<fieldset>
	<legend>
		<spring:message code="report.updates"></spring:message>
	</legend>
	<ul style="list-style-type: disc">

		<li><b><spring:message code="report.creator"></spring:message></b>
			<span class="${blueText}"> <jstl:out
					value="${reportUpdate.getCreator().nickname}" /></span></li>

		<li><b><spring:message code="report.date"></spring:message></b> <span
			class="${blueText}"> <jstl:out value="${reportUpdate.date}" /></span></li>

		<li><b><spring:message code="report.status"></spring:message></b>
			<span class="${blueText}"> <jstl:out
					value="${reportUpdate.status}" /></span></li>

		<li><b><spring:message code="report.content"></spring:message></b>
			<span class="${blueText}"> <jstl:out
					value="${reportUpdate.content}" /></span></li>

	</ul>
</fieldset>

<security:authorize access="hasRole('PLAYER')">
	<jstl:if
		test="${reportUpdate.isSuspicious eq false and report.status ne 'RESOLVED'}">
		<a
			href="reportUpdate/player/markSuspicious.do?reportUpdateId=${reportUpdate.id}&reportId=${report.id}&reportDisplay=false"><spring:message
				code="reportUpdate.mark.suspicious" /></a>
	</jstl:if>
</security:authorize>

<br />
<jstl:if test="${reportUpdate.isSuspicious eq true}">
	<spring:message code="reportUpdate.marked.suspicious" />
	<br />
</jstl:if>

<jstl:if test="${report.status ne 'RESOLVED' and ownUpdate eq true}">
	<br />
	<br />
	<a
		href="reportUpdate/edit.do?reportUpdateId=<jstl:out value="${reportUpdate.id}" />&reportId=<jstl:out value="${report.id}" />"><spring:message
			code="master.page.edit" /></a>
</jstl:if>

<acme:goback /> --%>