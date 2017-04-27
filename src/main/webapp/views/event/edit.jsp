<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="event">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="chorbies" />
	<form:hidden path="manager" />
	<form:hidden path="moment" />

	<acme:textbox code="event.title" path="title" />
	<acme:textarea code="event.description" path="description" />
	<acme:textbox code="event.seatsOffered" path="seatsOffered" />
	<acme:textbox code="event.picture" path="picture" />

	<acme:submit code="event.save" name="save" />

	<acme:cancel url="welcome/index.do" code="event.cancel" />
</form:form>