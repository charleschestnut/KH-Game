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

<div class="card" style="color: #212529;">
  <div class="card-header font-weight-bold text-primary" style="background-color:#D8ECF6;">
    <jstl:out value="${report.title}"/>
  </div>
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
  	  <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="content"/>:</b></div>	<div class="col-6"><jstl:out value="${report.content}"/></div></div></li>
  	  <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="date"/>:</b></div>		<div class="col-6"><jstl:out value="${report.date}"/></div></div></li>
  	  <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="report.creator"/>:</b></div>		<div class="col-6"><jstl:out value="${report.keybladeWielder.userAccount.username}"/></div></div></li>
      <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="status"/>:</b></div>
  	 <div class="col-6"> <jstl:if test="${report.status eq 'ONHOLD'}">
			<div class="row"><div class="col-6"><spring:message code="report.onhold" /></div></div></li>
		</jstl:if> 
		<jstl:if test="${report.status eq 'RESOLVED'}">
			<div class="row"><div class="col-6"><spring:message code="report.resolved" />
		</jstl:if> 
		<jstl:if test="${report.status eq 'IRRESOLVABLE'}">
			<div class="row"><div class="col-6"><spring:message code="report.irresolvable" />
		</jstl:if> 
		<jstl:if test="${report.status eq 'WORKING'}">
			<div class="row"><div class="col-6"><spring:message code="report.working" />
		</jstl:if> <jstl:if test="${report.status eq 'SUSPICIOUS'}">
			<div class="row"><div class="col-6"><spring:message code="report.suspicious" />
		</jstl:if>
		</div></div></li>
	</ul>
	<div class="row">
	<jstl:forEach items="${report.photos}" var="photo">
		<img src="${photo}" />
		<br />
	</jstl:forEach>
	</div>
  
  </div>
  </div>
  </div>
  <div class="card-footer text-muted">
  <acme:goback />
  <jstl:if test="${not empty report.reportUpdates}">
		<acme:action code="report.seeUpdates" color="pink" url="reportUpdate/list.do?reportId=${report.id}"/>
	</jstl:if>
  </div>
</div>

<%-- <ul style="list-style-type: disc">

	<li><b><spring:message code="report.title"></spring:message></b> <jstl:out
			value="${report.title}" /></li>

	<li><b><spring:message code="report.content"></spring:message></b>
		<jstl:out value="${report.content}" /></li>

	<li><b><spring:message code="report.status"></spring:message></b>
		<jstl:if test="${report.status eq 'ONHOLD'}">
			<spring:message code="report.onhold" />
		</jstl:if> <jstl:if test="${report.status eq 'RESOLVED'}">
			<spring:message code="report.resolved" />
		</jstl:if> <jstl:if test="${report.status eq 'IRRESOLVABLE'}">
			<spring:message code="report.irresolvable" />
		</jstl:if> <jstl:if test="${report.status eq 'WORKING'}">
			<spring:message code="report.working" />
		</jstl:if> <jstl:if test="${report.status eq 'SUSPICIOUS'}">
			<spring:message code="report.suspicious" />
		</jstl:if></li>

	<li><b><spring:message code="report.date"></spring:message></b> <jstl:out
			value="${report.date}" /></li>

	<li><b><spring:message code="report.creator"></spring:message></b>
		<jstl:out value="${report.keybladeWielder.nickname}" /></li>
	<br />

	<jstl:forEach items="${report.photos}" var="photo">
		<img src="${photo}" />
		<br />
	</jstl:forEach>

	<jstl:if test="${not empty report.reportUpdates}">
		<spring:message code="report.seeUpdates" />
	</jstl:if>
	<jstl:forEach items="${report.reportUpdates}" var="reportUpdate">

		<jstl:if test="${not empty reportUpdate.administrator}">
			<jstl:set value="blueText" var="blueText" />
		</jstl:if>

		<fieldset>
			<legend>
				<spring:message code="reportUpdate"></spring:message>
			</legend>
			<ul style="list-style-type: disc">

				<li><b><spring:message code="report.creator"></spring:message></b>
					<span class="${blueText}"> <jstl:out
							value="${reportUpdate.getCreator().nickname}" /></span></li>

				<li><b><spring:message code="report.date"></spring:message></b>
					<span class="${blueText}"> <jstl:out
							value="${reportUpdate.date}" /></span></li>

				<li><b><spring:message code="report.status"></spring:message></b>
					<span class="${blueText}"> <jstl:out
							value="${reportUpdate.status}" /></span></li>

				<li><b><spring:message code="report.content"></spring:message></b>
					<span class="${blueText}"> <jstl:out
							value="${reportUpdate.content}" /></span></li>

			</ul>
			<security:authorize access="hasRole('PLAYER')">
				<jstl:if
					test="${reportUpdate.isSuspicious eq false and report.status ne 'RESOLVED'}">
					<a
						href="reportUpdate/player/markSuspicious.do?reportUpdateId=${reportUpdate.id}&reportId=${report.id}&reportDisplay=true"><spring:message
							code="reportUpdate.mark.suspicious" /></a>
				</jstl:if>
			</security:authorize>
			<br />
			<jstl:if test="${reportUpdate.isSuspicious eq true}">
				<spring:message code="reportUpdate.marked.suspicious" />
				<br />
			</jstl:if>
		</fieldset>
	</jstl:forEach>
	<br />
	<acme:goback />

</ul> --%>