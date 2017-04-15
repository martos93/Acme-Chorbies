<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<display:table name="banner" id="row" requestURI="${requestUri}" class="displaytag" keepStatus="true" pagesize="5">
    <spring:message code="banner.banners" var="banners"/>
    <display:column title="${banners}">
        <ul>
            <img height="100" width="100" src="${row}">
        </ul>
    </display:column>
    <spring:message code="banner.delete" var="deleteColumn"/>
    <display:column title="${deleteColumn}">
        <ul>
            <input type="button"
               onclick="document.location.href='banner/administrator/delete.do?bannerURL=${row}'"
               value="${deleteColumn }"/>
        </ul>
    </display:column>

</display:table>

<a href="banner/administrator/add.do"> <spring:message code="banner.add" />
</a>
