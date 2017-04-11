<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${requestURI}" modelAttribute="chirpForm">
	
	<jstl:if test="${forward==true}">
	<acme:textbox  readonly="true" code="chirp.subject" path="subject"/>
	<acme:textarea readonly="true" code="chirp.text" path="text"/>
	<acme:textbox readonly="true" code="chirp.attachments" path="attachments"/> (<spring:message code="chirp.attachments.advice"></spring:message>)
	</jstl:if>
	
	<jstl:if test="${forward==false}">
	<acme:textbox  code="chirp.subject" path="subject"/>
	<acme:textarea code="chirp.text" path="text"/>
	<acme:textbox code="chirp.attachments" path="attachments"/>
	</jstl:if>
	
	
	<jstl:if test="${reply == false}">
	<acme:select items="${users}" itemLabel="userAccount.username" code="chirp.receiver" path="receiver" />
	</jstl:if>
	
	<jstl:if test="${reply == true}">
	<form:hidden path="receiver"/>
	<form:label path="receiver">
		<spring:message code="chirp.receiver" />
	</form:label>	
	<form:input disabled="true" path="receiver.userAccount.username" readonly="true" />	<br>
	</jstl:if>
	
	<acme:submit code="chirp.save" name="save"/>
	
	<acme:cancel code="chirp.cancel" url="/chirp/chorbi/list.do"/>
	
</form:form>