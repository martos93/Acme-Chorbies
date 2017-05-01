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

<form:form action="${requestURI}" modelAttribute="loveForm">

	<acme:textbox readonly="true" code="love.loved" path="loved"/>

	<acme:textarea code="love.comment" path="comment" />

	<div class="form-group">
		<form:label path="stars">
			<spring:message code="love.stars" />
		</form:label>
		<select id="stars" name="stars" ng-model="loveForm.stars">
			<option value="0"><spring:message code="love.star0" /> </option>
			<option value="1"><spring:message code="love.star1" /></option>
			<option value="2"><spring:message code="love.star2" /></option>
			<option value="3"><spring:message code="love.star3" /></option>
		</select>
	</div>

	<acme:submit name="save" code="love.save" />

	<acme:cancel url="welcome/index.do" code="love.cancel" />

</form:form>