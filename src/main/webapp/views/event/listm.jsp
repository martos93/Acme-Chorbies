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

<display:table name="events" id="row" requestURI="${requestURI}"
	pagesize="10" class="displaytag">

	<spring:message code="event.title" var="title" />
	<display:column property="title" title="${title}" />

	<spring:message code="event.description" var="description" />
	<display:column property="description" title="${description}" />

	<spring:message code="event.picture" var="picture" />
	<display:column property="picture" title="${picture}" />

	<spring:message code="event.seatsOffered" var="seatsOffered" />
	<display:column property="seatsOffered" title="${seatsOffered}" />

	<spring:message code="event.moment" var="moment" />
	<display:column property="moment" title="${moment}" />


</display:table>