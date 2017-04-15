<%@taglib prefix="jstl"    uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${requestURI}" modelAttribute="bannerForm">

    <acme:textbox code="bannerForm.url" path="url"/>
    <acme:submit code="bannerForm.save" name="save"/>
    <acme:cancel code="bannerForm.cancel" url="/banner/administrator/list.do"/>

</form:form>
