<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="loves" id="row" requestURI="${requestURI}"
	pagesize="10" class="displaytag">

	<spring:message code="love.lover" var="lover" />
	<display:column property="lover.name" title="${lover}" />

	<spring:message code="love.loved" var="loved" />
	<display:column property="loved.name" title="${loved}" />

	<spring:message code="love.moment" var="moment" />
	<display:column property="moment" title="${moment}" />

	<spring:message code="love.comment" var="comment" />
	<display:column property="comment" title="${comment}" />

	<spring:message code="love.stars" var="stars" />
	<jstl:if test="${row.stars == 0}">
		<display:column title="${stars}">
			<img src="images/star0.png">
			<img src="images/star0.png">
			<img src="images/star0.png">
		</display:column>
	</jstl:if>
	<jstl:if test="${row.stars == 1}">
		<display:column title="${stars}">
			<img src="images/star0.png">
			<img src="images/star0.png">
			<img src="images/star.png">
		</display:column>
	</jstl:if>
	<jstl:if test="${row.stars == 2}">
		<display:column title="${stars}">
			<img src="images/star0.png">
			<img src="images/star.png">
			<img src="images/star.png">
		</display:column>
	</jstl:if>
	<jstl:if test="${row.stars == 3}">
		<display:column title="${stars}">
			<img src="images/star.png">
			<img src="images/star.png">
			<img src="images/star.png">
		</display:column>
	</jstl:if>


	<display:column>
		<input
			onclick="javascript: window.location.replace('like/chorbi/delete.do?id=${row.id}');"
			value="<spring:message code="love.delete" />" type="button"
			name="delete" />
	</display:column>



</display:table>

