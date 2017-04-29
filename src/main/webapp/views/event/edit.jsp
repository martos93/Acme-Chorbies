<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${requestUri}" modelAttribute="event">
	<form:hidden path="id"/>
	<form:hidden path="manager"/>
	<form:hidden path="moment"/>
	<form:hidden path="chorbies"/>
	
	<acme:textbox code="event.title" path="title"/>
	<acme:textbox code="event.description" path="description"/>
	<acme:textbox code="event.picture" path="picture"/>
	<acme:textbox code="event.seatsOffered" path="seatsOffered"/>
	
	<acme:submit code="event.save" name="save"/>
	
	<acme:cancel url="welcome/index.do" code="event.cancel"/>

</form:form>