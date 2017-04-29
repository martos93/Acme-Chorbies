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

<form:form action="${requestURI}" modelAttribute="fee">
	<form:hidden path="id"/>

	<acme:textbox code="fee.managerAmount" path="managerAmount.amount" />
	<acme:textbox code="fee.managerCurrency" path="managerAmount.currency" />
	<acme:textbox code="fee.chorbiAmount" path="chorbiAmount.amount" />
	<acme:textbox code="fee.chorbiCurrency" path="chorbiAmount.currency" />

	<acme:submit code="fee.save" name="save" />


	<acme:cancel url="/welcome/index.do" code="fee.cancel" />

</form:form>
