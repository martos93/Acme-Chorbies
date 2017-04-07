<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="cache">
	<form:hidden path="id"/>

	<acme:textbox code="cache.hours" path="hours" />
	<acme:textbox code="cache.minutes" path="minutes" />
	<acme:textbox code="cache.seconds" path="seconds" />

	<acme:submit code="cache.save" name="save" />


	<acme:cancel url="/welcome/index.do" code="cache.cancel" />

</form:form>
