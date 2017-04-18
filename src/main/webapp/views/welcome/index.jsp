<%--
 * index.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<img src="${url}"/>
<p><spring:message code="welcome.greeting.prefix" /> ${name}<spring:message code="welcome.greeting.suffix" /></p>

<p><spring:message code="welcome.greeting.current.time" /> ${moment}</p> 

<jstl:if test="${!existCookie}">
<table class="cookiesStyle">
		<tr>
			<th>
				<spring:message code="cookie.politics" />&nbsp;
				<a class = "cookies" href="welcome/privacy.do#cookies"><spring:message code = "cookie.consult" /></a>
				<br />
				<spring:message code="privacity.politics" />&nbsp;
				<a class = "cookies" href="welcome/privacy.do"><spring:message code = "privacity.consult" /></a>&nbsp;
				<spring:message code="term.politics" />&nbsp;
				<a class = "cookies" href="welcome/terms.do"><spring:message code = "term.consult" /></a>
			</th>
		</tr>
</table>
</jstl:if>