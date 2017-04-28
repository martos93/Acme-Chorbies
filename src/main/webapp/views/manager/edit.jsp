<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${requestUri}" modelAttribute="managerForm">
	<form:hidden path="managerId"/>
	
	<jstl:if test="${edit==false}">
	<fieldset>
	<legend><spring:message code="manager.account"/></legend>
	<acme:textbox code="manager.username" path="username"/>
	<acme:password code="manager.password" path="password"/>
	<acme:password code="manager.confirmPassword" path="confirmPassword"/>
	</fieldset>
	</jstl:if>
	
	<jstl:if test="${edit==true}">
	<fieldset>
	<legend><spring:message code="manager.account"/></legend>
	<acme:textbox readonly="true" code="manager.username" path="username"/>
	<acme:password code="manager.current.password" path="password"/><b><spring:message code="manager.passwordinfo"/></b>
	<acme:password code="manager.newpassword" path="newpassword"/>
	<acme:password code="manager.confirmPassword" path="repeatnewpassword"/>
	<b><spring:message code="manager.passwordinfo2"/></b>	
	
	</fieldset>
	</jstl:if>
	
	<fieldset>
	<legend><spring:message code="manager.personal"/></legend>
	<acme:textbox code="manager.name" path="name"/>
	<acme:textbox code="manager.surname" path="surname"/>
	<acme:textbox code="manager.email" path="email"/>
	<acme:textbox code="manager.phoneNumber" path="phoneNumber"/>
	<acme:textbox code="manager.company" path="company"/>
	<acme:textbox code="manager.vat" path="VAT"/>
	</fieldset>
	
	<fieldset>
	<legend><spring:message code="manager.creditcard"/></legend>
	<acme:textbox code="manager.holderName" path="holderName"/>
	
	<form:label path="brandName">
		<spring:message code="manager.brandName" />
	</form:label>	
	<form:select path="brandName">
		<form:option value="VISA">VISA</form:option>
		<form:option value="MASTERCARD">MASTERCARD</form:option>
		<form:option value="DISCOVER">DISCOVER</form:option>
		<form:option value="DINNERS">DINNERS</form:option>
		<form:option value="AMEX">AMEX</form:option>		
		
	</form:select>
	<form:errors path="brandName" cssClass="error" />
	<br>
	
	
	<acme:textbox code="manager.number" path="number"/>
	<acme:textbox code="manager.expirationMonth" path="expirationMonth"/>
	<acme:textbox code="manager.expirationYear" path="expirationYear"/>
	<acme:textbox code="manager.cvvCode" path="cvvCode"/>
	</fieldset>
	
	<jstl:if test="${edit==false}">
	
	<form:label path="acceptTerms">
		<b><spring:message code="manager.acceptTerms" /></b>
	</form:label>	
	<form:select path="acceptTerms">
		<form:option value="true">Yes</form:option>
		<form:option value="false">No</form:option>
		
		
	</form:select>
	</jstl:if>
	
	<acme:submit code="manager.save" name="save"/>
	
	<acme:cancel url="welcome/index.do" code="manager.cancel"/>

</form:form>