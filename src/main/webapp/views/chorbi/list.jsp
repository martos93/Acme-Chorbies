<%--
 * action-1.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table name="chorbi" id="row" requestURI="${requestUri}"
	class="displaytag" keepStatus="true" pagesize="5">

	<security:authorize access="isAuthenticated()">
		<spring:message code="chorbi.name" var="titleColumn" />
		<display:column property="name" title="${titleColumn}" />

		<spring:message code="chorbi.surname" var="titleColumn" />
		<display:column property="surname" title="${titleColumn}" />

		<spring:message code="chorbi.phoneNumber" var="titleColumn" />
		<display:column property="phoneNumber" title="${titleColumn}" />

		<spring:message code="chorbi.picture" var="titleColumn" />
		<display:column title="${titleColumn}">
			<img src="${row.picture}">
		</display:column>

		<spring:message code="chorbi.description" var="titleColumn" />
		<display:column property="description" title="${titleColumn}" />


		<spring:message code="chorbi.birthDate" var="titleColumn" />
		<display:column property="birthDate" title="${titleColumn}" />

		<spring:message code="chorbi.country" var="titleColumn" />
		<display:column property="location.country" title="${titleColumn}" />

		<spring:message code="chorbi.state" var="titleColumn" />
		<display:column property="location.state" title="${titleColumn}" />

		<spring:message code="chorbi.province" var="titleColumn" />
		<display:column property="location.province" title="${titleColumn}" />

		<spring:message code="chorbi.city" var="titleColumn" />
		<display:column property="location.city" title="${titleColumn}" />

		<spring:message code="chorbi.email" var="titleColumn" />
		<display:column property="email" title="${titleColumn}" />

		<spring:message code="chorbi.genre" var="titleColumn" />
		<display:column property="genre" title="${titleColumn}" />

		<spring:message code="chorbi.love" var="titleColumn" />
		<display:column title="${titleColumn}">
			<input
				onclick="javascript: window.location.replace('chorbi/listByLikes.do?chorbi=${row.id}');"
				value="<spring:message code="chorbi.love" />" type="button" />
		</display:column>




	</security:authorize>

	<security:authorize access="hasRole('CHORBI')">

		<spring:message code="chorbi.like" var="titleColumn" />
		<display:column title="${titleColumn}">
			<input
				onclick="javascript: window.location.replace('like/chorbi/create.do?id=${row.id}');"
				value="<spring:message code="chorbi.like" />" type="button" />
		</display:column>


	</security:authorize>

	<security:authorize access="hasRole('ADMIN')">
		<spring:message code="chorbi.username" var="titleColumn" />
		<display:column property="userAccount.username" title="${titleColumn}" />

		<jstl:if test="${ban }">
			<spring:message code="chorbi.ban" var="titleColumn" />
			<display:column title="${titleColumn}">
				<input
					onclick="javascript: window.location.replace('administrator/banChorbi.do?chorbi=${row.id}');"
					value="<spring:message code="chorbi.ban" />" type="button" />
			</display:column>
		</jstl:if>

		<jstl:if test="${unban }">
			<spring:message code="chorbi.unban" var="titleColumn" />
			<display:column title="${titleColumn}">
				<input
					onclick="javascript: window.location.replace('administrator/unBanChorbi.do?chorbi=${row.id}');"
					value="<spring:message code="chorbi.unban" />" type="button" />
			</display:column>
		</jstl:if>
	</security:authorize>


</display:table>

<security:authorize access="hasRole('CHORBI')">
	<a href="chirp/chorbi/send.do"> <spring:message code="chirp.send" />
	</a>
</security:authorize>