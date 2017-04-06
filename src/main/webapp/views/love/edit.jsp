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

<form:form action="${requestURI}" modelAttribute="love">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="lover" />
	<form:hidden path="moment" />


	<acme:textbox code="love.loved" path="loved.name" readonly="true" />
	<acme:textarea code="love.comment" path="comment" />

	<acme:submit name="save" code="love.save" />

	<acme:cancel url="welcome/index.do" code="love.cancel" />

</form:form>