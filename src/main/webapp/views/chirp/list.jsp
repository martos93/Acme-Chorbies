<%--
 * index.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<spring:message code="chirp.sure" var="sure" />

<script>
	function myFunction(id,t) {
	   
	    var r = confirm(t);
	    if (r == true) {
	    	document.location.href='chirp/chorbi/delete.do?chirpId='+id;
	    } else {
	        
	    }
	    
	}
	</script>

<b><spring:message code="chirp.received" /></b>
<br>
<display:table name="received" id="row" requestURI="${requestUri}"
	class="displaytag" keepStatus="true" pagesize="5">


	<spring:message code="chirp.subject" var="subjectColumn" />
	<display:column property="subject" title="${subjectColumn}" />


	<spring:message code="chirp.text" var="textColumn" />
	<display:column property="text" title="${textColumn}" />

	<spring:message code="chirp.moment" var="momentColumn" />
	<display:column property="moment" title="${momentColumn}" />

	<spring:message code="chirp.attachments" var="atchColumn" />
	<display:column title="${atchColumn}">
		<ul>
			<jstl:forEach items="${row.attachments }" var="attach">
				<li>${attach}</li>
			</jstl:forEach>
		</ul>
	</display:column>

	<spring:message code="chirp.delete" var="deleteColumn" />
	<display:column title="${deleteColumn}">

		<input type="button" onclick="myFunction(${row.id},'${sure}')"
			value="${deleteColumn }" />

	</display:column>


	<spring:message code="chirp.reply" var="replyColumn" />
	<display:column title="${replyColumn}">
		<jstl:if test="${row.senderM == null}">
		<input type="button"
			onclick="document.location.href='chirp/chorbi/reply.do?senderName=${row.senderC.userAccount.username}'"
			value="${replyColumn }" />
		</jstl:if>
	</display:column>

</display:table>

<br>
<br>
<b><spring:message code="chirp.sended" /></b>
<br>
<display:table name="sended" id="row" requestURI="${requestUri}"
	class="displaytag" keepStatus="true" pagesize="5">

	<spring:message code="chirp.subject" var="subjectColumn" />
	<display:column property="subject" title="${subjectColumn}" />

	<spring:message code="chirp.text" var="textColumn" />
	<display:column property="text" title="${textColumn}" />

	<spring:message code="chirp.moment" var="momentColumn" />
	<display:column property="moment" title="${momentColumn}" />

	<spring:message code="chirp.attachments" var="atchColumn" />
	<display:column title="${atchColumn}">
		<ul>
			<jstl:forEach items="${row.attachments }" var="attach">
				<li>${attach}</li>
			</jstl:forEach>
		</ul>
	</display:column>

	<spring:message code="chirp.delete" var="deleteColumn" />
	<display:column title="${deleteColumn}">

		<input type="button" onclick="myFunction(${row.id},'${sure}')"
			value="${deleteColumn }" />
	</display:column>

	<spring:message code="chirp.forward" var="forwardColumn" />
	<display:column title="${forwardColumn}">
		<input type="button"
			onclick="document.location.href='chirp/chorbi/forward.do?chirpId=${row.id}'"
			value="${forwardColumn }" />
	</display:column>

</display:table>

<a href="chirp/chorbi/send.do"> <spring:message code="chirp.send" />
</a>


