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

<form:form action="${requestUri}" modelAttribute="chorbiForm">
	<form:hidden path="chorbiId"/>
	
	<jstl:if test="${edit==false}">
	<fieldset>
	<legend><spring:message code="chorbi.account"/></legend>
	<acme:textbox code="chorbi.username" path="username"/>
	<acme:password code="chorbi.password" path="password"/>
	<acme:password code="chorbi.confirmPassword" path="confirmPassword"/>
	</fieldset>
	</jstl:if>
	
	<jstl:if test="${edit==true}">
	<fieldset>
	<legend><spring:message code="chorbi.account"/></legend>
	<acme:textbox readonly="true" code="chorbi.username" path="username"/>
	<acme:password code="chorbi.current.password" path="password"/><b><spring:message code="chorbi.passwordinfo"/></b>
	<acme:password code="chorbi.newpassword" path="newpassword"/>
	<acme:password code="chorbi.confirmPassword" path="repeatnewpassword"/>
	<b><spring:message code="chorbi.passwordinfo2"/></b>	
	
	</fieldset>
	</jstl:if>
	
	<fieldset>
	<legend><spring:message code="chorbi.personal"/></legend>
	<acme:textbox code="chorbi.name" path="name"/>
	<acme:textbox code="chorbi.surname" path="surname"/>
	<acme:textbox code="chorbi.email" path="email"/>
	<acme:textbox code="chorbi.phoneNumber" path="phoneNumber"/>
	<acme:textbox code="chorbi.picture" path="picture"/>
	<acme:textbox code="chorbi.description" path="description"/>
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
	
	<acme:textbox code="chorbi.birthDate" path="birthDate"/>
	
	</fieldset>
	
	<fieldset>
	<legend><spring:message code="chorbi.location"/></legend>
	<acme:textbox code="chorbi.country" path="country"/>
	<acme:textbox code="chorbi.state" path="state"/>
	<acme:textbox code="chorbi.province" path="province"/>
	<acme:textbox code="chorbi.city" path="city"/>
	
	</fieldset>
	
	<fieldset>
	<legend><spring:message code="chorbi.creditcard"/></legend>
	<acme:textbox code="chorbi.holderName" path="holderName"/>
	
	<form:label path="brandName">
		<spring:message code="chorbi.brandName" />
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
	
	
	<acme:textbox code="chorbi.number" path="number"/>
	<acme:textbox code="chorbi.expirationMonth" path="expirationMonth"/>
	<acme:textbox code="chorbi.expirationYear" path="expirationYear"/>
	<acme:textbox code="chorbi.cvvCode" path="cvvCode"/>
	</fieldset>
	
	<jstl:if test="${edit==false}">
	
	<form:label path="acceptTerms">
		<b><spring:message code="chorbi.acceptTerms" /></b>
	</form:label>	
	<form:select path="acceptTerms">
		<form:option value="true">Yes</form:option>
		<form:option value="false">No</form:option>
		
		
	</form:select>
	<form:errors path="genre" cssClass="error" />
	<br>
	</jstl:if>
	
	<acme:submit code="chorbi.save" name="save"/>
	
	<acme:cancel url="welcome/index.do" code="chorbi.cancel"/>

</form:form>