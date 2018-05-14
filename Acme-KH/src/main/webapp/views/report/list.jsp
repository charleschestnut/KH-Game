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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->

<display:table name="report" id="row" 
    requestURI="report/gm/list.do" 
    pagesize="5" class="displaytag">
	
	<display:column>
		<jstl:out value="${row.isBug}" /> <br />
		<jstl:out value="${row.author}" />, <fmt:formatDate value="${row.moment}" pattern="dd/MM/yy HH:mm" /> <br /> 
		<jstl:out value="${row.text}" /> <br />					
		<a href="bulletin/customer/edit.do?bulletinId=${row.id}">
			<spring:message	code="bulletin.edit" />
		</a>		
	</display:column>

</display:table>

<!-- Action links -->

<div>
	<a href="bulletin/customer/create.do"> 
		<spring:message code="bulletin.create" />
	</a>
</div>
