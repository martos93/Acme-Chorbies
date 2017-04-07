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

<form:form action="${requestURI}" modelAttribute="template">
	<form:hidden path="id"/>
	<form:hidden path="moment"/>


	<acme:select items="'ACME''ACME2'" itemLabel="template.kindRelationship" code="message.receiver" path="receiver" />
	<acme:textbox code="template.aproxAge" path="aproxAge" />
	<acme:textbox code="template.keyword" path="keyword" />
	<acme:textbox code="template.location.state" path="location.state" />
	<acme:textbox code="template.location.country" path="location.country" />
	<acme:textbox code="template.location.province" path="location.province" />
	<acme:textbox code="template.location.city" path="location.city" />

	<acme:submit code="template.search" name="search" />


	<acme:cancel url="/welcome/index.do" code="template.cancel" />

</form:form>
