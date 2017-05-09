<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${requestURI}" modelAttribute="chirpManagerForm">
	
    <acme:textbox  code="chirp.subject" path="subject"/>
    <acme:textarea code="chirp.text" path="text"/>
    <acme:textbox code="chirp.attachments" path="attachments"/>

    <acme:submit code="chirp.save" name="save"/>

    <acme:cancel code="chirp.cancel" url="/event/manager/listMyEvents.do"/>
</form:form>
