
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


<jstl:forEach var="troo" items="${troops}">
   <input type="number" name="${troo.name }" id="${troo.name }"min="1" max="100">
   <br/>
</jstl:forEach>

<jstl:forEach var="gumm" items="${gummiShips}">
   <input type="number" name="${gumm.name }" id="${gumm.name }" min="1" max="100">
   <br/>
</jstl:forEach>



<form:form action="battle/recruited.do" modelAttribute="battleForm" id="form">
	<form:hidden path="troops" id="troops"/>
	<form:hidden path="enemy" id="enemy"/>
	<button onclick="set()">Hopla</button>
	<button onclick="set()" name="save" class="btn btn-primary">
	<spring:message code="master.page.save" />
</button>

</form:form>

<script>
function set() 
{ 
	var tr = [];

    var a = ${nombres}
    var d = a[1];
	var st = "\"";
	var i = 0;
	var j;
    for (var x in a){
    	j = document.getElementById(a[i]).value;
		tr.push(j);
    	i = i+1;
    }
    document.getElementById("troops").value = tr;
    document.getElementById("enemy").value = ${enemy};
    //document.getElementById("form").submit("form");
}    
      
</script> 