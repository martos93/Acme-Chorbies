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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<h2><spring:message code="event.pasts"/></h2>
<display:table name="eventP" id="row" requestURI="${requestURI}"
	pagesize="10" class="displaytag"  style="background-color:grey;">

	<spring:message code="event.title" var="title"/>
	<display:column property="title" title="${title}" style="background-color:grey;"/>

	<spring:message code="event.description" var="description" />
	<display:column property="description" title="${description}" style="background-color:grey;"/>

	<spring:message code="event.picture" var="picture" />
	<display:column property="picture" title="${picture}" style="background-color:grey;"/>

	<spring:message code="event.seatsOffered" var="seatsOffered" />
	<display:column property="seatsOffered" title="${seatsOffered}" style="background-color:grey;"/>
	
	<spring:message code="event.seatsFree" var="seatsFree" />
		<display:column title="${seatsFree}" style="background-color:grey;" sortable="true">
		${row.seatsOffered - fn:length(row.chorbies) }
		</display:column>
	
	<spring:message code="event.moment" var="moment" />
	<display:column property="moment" title="${moment}" style="background-color:grey;"/>

</display:table>

<h2><spring:message code="event.close"/></h2>
<display:table name="event" id="row" requestURI="${requestURI}"
	pagesize="10" class="displaytag" style="background-color:green;">

	<spring:message code="event.title" var="title" />
	<display:column property="title" title="${title}" style="background-color:green;"/>

	<spring:message code="event.description" var="description" />
	<display:column property="description" title="${description}" style="background-color:green;"/>

	<spring:message code="event.picture" var="picture" />
	<display:column property="picture" title="${picture}" style="background-color:green;"/>

	<spring:message code="event.seatsOffered" var="seatsOffered" />
	<display:column property="seatsOffered" title="${seatsOffered}" style="background-color:green;"/>
	
	<spring:message code="event.seatsFree" var="seatsFree"  />
		<display:column title="${seatsFree}"  style="background-color:green;" sortable="true">
		${row.seatsOffered - fn:length(row.chorbies) }
		</display:column>
	
	<spring:message code="event.moment" var="moment" />
	<display:column property="moment" title="${moment}" style="background-color:green;"/>
	<security:authorize access="hasRole('CHORBI')">
		<spring:message code="event.register" var="register" />
		<display:column title="${register}" style="background-color:green;">
		<jstl:if test="${fn:contains(row.chorbies,chorbi)==false && (row.seatsOffered - fn:length(row.chorbies) != 0)}">
			<input
				onclick="javascript: window.location.replace('event/chorbi/register.do?id=${row.id}');"
				value="<spring:message code="event.register" />" type="button"
				name="register" />
		</jstl:if>
		
		<jstl:if test="${fn:contains(row.chorbies,chorbi)==true}">
			<input
				onclick="javascript: window.location.replace('event/chorbi/unregister.do?id=${row.id}');"
				value="<spring:message code="event.unregister" />" type="button"
				name="unregister" />
		</jstl:if>
		</display:column>
	</security:authorize>
</display:table>

<h2><spring:message code="event.future"/></h2>
<display:table name="eventF" id="row" requestURI="${requestURI}"
	pagesize="10" class="displaytag">

	<spring:message code="event.title" var="title" />
	<display:column property="title" title="${title}" />

	<spring:message code="event.description" var="description" />
	<display:column property="description" title="${description}" />

	<spring:message code="event.picture" var="picture" />
	<display:column property="picture" title="${picture}" />

	<spring:message code="event.seatsOffered" var="seatsOffered" />
	<display:column property="seatsOffered" title="${seatsOffered}" />
	
	<spring:message code="event.seatsFree" var="seatsFree" />
		<display:column title="${seatsFree}" sortable="true">
		${row.seatsOffered - fn:length(row.chorbies) }
		</display:column>
	
	
	<spring:message code="event.moment" var="moment" />
	<display:column property="moment" title="${moment}" />
	<security:authorize access="hasRole('CHORBI')">
		<spring:message code="event.register" var="register" />
		<display:column title="${register}">
		<jstl:if test="${fn:contains(row.chorbies,chorbi)==false && (row.seatsOffered - fn:length(row.chorbies) != 0)}">
			<input
				onclick="javascript: window.location.replace('event/chorbi/register.do?id=${row.id}');"
				value="<spring:message code="event.register" />" type="button"
				name="register" />
		</jstl:if>
		
		<jstl:if test="${fn:contains(row.chorbies,chorbi)==true}">
			<input
				onclick="javascript: window.location.replace('event/chorbi/unregister.do?id=${row.id}');"
				value="<spring:message code="event.unregister" />" type="button"
				name="unregister" />
		</jstl:if>
		</display:column>
	</security:authorize>
</display:table>

