<%--
 * action-1.jsp
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


<display:table name="chorbi" id="row" requestURI="${requestUri}"
	class="displaytag" keepStatus="true" pagesize="5" >

	<spring:message code="chorbi.name" var="titleColumn" />
	<display:column property="name" title="${titleColumn}" />

	<spring:message code="chorbi.email" var="titleColumn"/>
	<display:column property="email" title="${titleColumn}"/>
	
	<spring:message code="chorbi.genre" var="titleColumn"/>
	<display:column property="genre" title="${titleColumn}"/>
	
	<spring:message code="chorbi.love" var="titleColumn"/>
	<display:column title="${titleColumn}">
	<input
				onclick="javascript: window.location.replace('chorbi/listByLikes.do?chorbi=${row.id}');"
				value="<spring:message code="chorbi.love" />"
				type="button" />
	</display:column>

	
</display:table>