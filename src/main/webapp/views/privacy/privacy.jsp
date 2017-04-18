
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="privacy.subtitle" /></p>
<p><spring:message code="privacy.describe" /></p>
<ul>
	<li><spring:message code="privacy.describe_1" /></li>
	<li><spring:message code="privacy.describe_2" /></li>
	<li><spring:message code="privacy.describe_3" /></li>
</ul>
<p><spring:message code="privacy.following" /></p>
<ul>
	<li><spring:message code="privacy.following_1" /></li>
	<li><spring:message code="privacy.following_2" /></li>
	<li><spring:message code="privacy.following_3" /></li>
</ul>
<p><spring:message code="privacy.text" /></p>
<div id="cookies">
	<h1><spring:message code="privacy.cookies" /></h1>
	<p><spring:message code="privacy.purposes" /></p>
	<ul>
		<li><spring:message code="privacy.purposes_1" /></li>
		<li><spring:message code="privacy.purposes_2" /></li>
	</ul>
	<p><spring:message code="privacy.use" /></p>
	<ul>
		<li><spring:message code="privacy.chrome" /><a href="http://support.google.com/chrome/bin/answer.py?hl=es&answer=95647"><spring:message code="privacy.chrome_URL" /></a></li>
		<li><spring:message code="privacy.explorer" /><a href="http://windows.microsoft.com/es-es/windows7/how-to-manage-cookies-in-internet-explorer-9"><spring:message code="privacy.explorer_URL" /></a></li>
		<li><spring:message code="privacy.firefox" /><a href="http://support.mozilla.org/es/kb/habilitar-y-deshabilitar-cookies-que-los-sitios-we"><spring:message code="privacy.firefox_URL" /></a></li>
		<li><spring:message code="privacy.safari" /><a href="http://support.apple.com/kb/ph5042"><spring:message code="privacy.safari_URL" /></a></li>
	</ul>
</div>