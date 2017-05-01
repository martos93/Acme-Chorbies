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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${canShow==true }">

<display:table name="chorbiesILike" id="row" requestURI="${requestUri}"
	class="displaytag" keepStatus="true" pagesize="5">

	<security:authorize access="isAuthenticated()">
		<spring:message code="chorbi.name" var="titleColumn" />
		<display:column title="${titleColumn}">
		
			<c:choose>
				<c:when test="${fn:contains(row.name, '@')}">
    ${fn:replace(row.name,row.name,"*******@*****.***")}
  </c:when>

				<c:otherwise>
   ${row.name }
  </c:otherwise>
			</c:choose>
			</display:column>

		<spring:message code="chorbi.surname" var="titleColumn" />
		<display:column title="${titleColumn}">
		
			<c:choose>
				<c:when test="${fn:contains(row.surname, '@')}">
    ${fn:replace(row.surname,row.surname,"*******@*****.***")}
  </c:when>

				<c:otherwise>
   ${row.surname }
  </c:otherwise>
			</c:choose>
		</display:column>

		<spring:message code="chorbi.picture" var="titleColumn" />
		<display:column title="${titleColumn}">
			<img src="${row.picture}">
		</display:column>

		<spring:message code="chorbi.description" var="titleColumn" />
		<display:column title="${titleColumn}">
		
			<c:choose>
				<c:when test="${fn:contains(row.description, '@')}">
    ${fn:replace(row.description,row.description,"*******@*****.***")}
  </c:when>

				<c:otherwise>
   ${row.description }
  </c:otherwise>
			</c:choose>
		</display:column>


		<spring:message code="chorbi.birthDate" var="titleColumn" />
		<display:column property="birthDate" title="${titleColumn}" />

		<spring:message code="chorbi.country" var="titleColumn" />
		<display:column title="${titleColumn}">
		
			<c:choose>
				<c:when test="${fn:contains(row.location.country, '@')}">
    ${fn:replace(row.location.country,row.location.country,"*******@*****.***")}
  </c:when>

				<c:otherwise>
   ${row.location.country }
  </c:otherwise>
			</c:choose>
		</display:column>

		<spring:message code="chorbi.state" var="titleColumn" />
		<display:column title="${titleColumn}">
		
			<c:choose>
				<c:when test="${fn:contains(row.location.state, '@')}">
    ${fn:replace(row.location.state,row.location.state,"*******@*****.***")}
  </c:when>

				<c:otherwise>
   ${row.location.state }
  </c:otherwise>
			</c:choose>
		</display:column>

		<spring:message code="chorbi.province" var="titleColumn"/>	
		<display:column title="${titleColumn}">
			<c:choose>
				<c:when test="${fn:contains(row.location.province, '@')}">
    ${fn:replace(row.location.province,row.location.province,"*******@*****.***")}
  </c:when>

				<c:otherwise>
   ${row.location.province }
  </c:otherwise>
			</c:choose>
		</display:column>

		<spring:message code="chorbi.city" var="titleColumn" />
		<display:column property="location.city" title="${titleColumn}">
			<c:choose>
				<c:when test="${fn:contains(row.location.city, '@')}">
    ${fn:replace(row.location.city,row.location.city,"*******@*****.***")}
  </c:when>

				<c:otherwise>
   ${row.location.province }
  </c:otherwise>
			</c:choose>
		</display:column>

		

		<spring:message code="chorbi.genre" var="titleColumn" />
		<display:column property="genre" title="${titleColumn}" />


	</security:authorize>

</display:table>

<security:authorize access="hasRole('CHORBI')">
	<a href="chirp/chorbi/send.do"> <spring:message code="chirp.send" />
	</a>
</security:authorize>

<br>
<b><spring:message code="chorbi.information"/></b>


</jstl:if>



<jstl:if test="${canShow==false }">
	<div style="color: red;">
		<b><jstl:out value="Your credit card must be a valid credit card!!" /></b>
	</div>
	<div>
		<b>Your credit card will not be charged.</b>
	</div>
</jstl:if>
