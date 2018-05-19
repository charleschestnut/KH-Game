<%--
 * edit.jsp
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<script type="text/javascript">

$( document ).ready(function() {

var i = 0;
var txt = 'Acme-Battle - Command Prompt#-------------------------------------------------------------';
var speed = 25;
var commands = [];

typeWriter();

var prompt = document.getElementById("prompt");
var count = -1;
prompt.addEventListener("keydown", function (e) {
    if (e.keyCode === 13) {  //checks whether the pressed key is "Enter"
    	var command = document.getElementById('commandLine').innerText;
    	interpret(command);
    	e.preventDefault();
    }else if(e.keyCode === 38){
    	arrayUp();
    	e.preventDefault();
    	return false;
    }else if(e.keyCode === 40){
    	arrayDown();
    	e.preventDefault();
    	return false;
    }
});

function arrayUp() {
	count++;
	if(count >= commands.length){
		count = 0;
	}
	$('#commandLine').text(commands[count]);
	document.getElementById('commandLine').focus();
}

function arrayDown() {
	count--;
	if(count < 0){
		count = commands.length-1;
	}
	$('#commandLine').text(commands[count]);
	document.getElementById('commandLine').focus();
}

function typeWriter() {
  if (i < txt.length) {
	  if(txt.charAt(i) === "#"){
		  document.getElementById("demo").innerHTML += "</br>";
	  }else{
    	  document.getElementById("demo").innerHTML += txt.charAt(i);
	  }
    i++;
    setTimeout(typeWriter, speed);
  }else{
	  document.getElementById('commandLine').focus();
  }
}

function interpret(command) {
	commands.push(command);
	if(command === 'clear'){
		$('.parent').empty();
		$('.parent').append('<span class="arrow">></span><div id="commandLine" class="commandLine" contenteditable="true"></div>');
    	document.getElementById('commandLine').focus();
	}else{
		var div = $('#commandLine');
		$.ajax({
	        url: 'prompt/interpret.do',
	        type: 'post',
	        data: {command: command},
	        success: function( data, textStatus, jQxhr ){
	        	div.removeAttr('id');
	        	$('.parent').append('<br/><span class="answer">'+data+'</span>');
	        	$('.parent').append('<br/><br/><span class="arrow">></span><div id="commandLine" class="commandLine" contenteditable="true"></div>');
	        	document.getElementById('commandLine').focus();
	        },
	        error: function( jqXhr, textStatus, errorThrown ){
	            console.log( errorThrown );
	        }
	    });
	}
}

});
</script>

<div id="prompt">
<div contenteditable="false" id="default"><span id="demo"></span><br/><br/></div>
<div class="parent"><span class="arrow">></span><div contenteditable="true" class="commandLine" id="commandLine"></div></div>
</div>
