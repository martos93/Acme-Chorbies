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

	<form:label path="kindRelationship">
		<spring:message code="chorbi.kindRelationship" />
	</form:label>
	<form:select path="kindRelationship">
		<form:option value="LOVE"><spring:message code="chorbi.love2"/></form:option>
		<form:option value="FRIENDSHIP"><spring:message code="chorbi.friendship"/></form:option>
		<form:option value="ACTIVITIES"><spring:message code="chorbi.activities"/></form:option>
	</form:select>
	<form:errors path="kindRelationship" cssClass="error" />
	
	<br>
	<form:label path="genre">
		<spring:message code="chorbi.genre" />
	</form:label>	
	<form:select path="genre">
		<form:option value="MAN"><spring:message code="chorbi.man"/></form:option>
		<form:option value="WOMAN"><spring:message code="chorbi.woman"/></form:option>
	</form:select>
	<form:errors path="genre" cssClass="error" />
	
	<acme:textbox code="template.aproxAge" path="aproxAge" />
	<acme:textbox code="template.keyword" path="keyword" />
	<acme:textbox code="template.location.state" path="location.state" />
	<acme:textbox code="template.location.country" path="location.country" />
	<acme:textbox code="template.location.province" path="location.province" />
	<acme:textbox code="template.location.city" path="location.city" />

	<acme:submit code="template.search" name="search" />


	<acme:cancel url="/welcome/index.do" code="template.cancel" />

</form:form>
