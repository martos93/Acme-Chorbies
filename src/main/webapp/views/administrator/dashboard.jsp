<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">

    <!-- A listing with the number of chorbies per country and city: -->
    <h2><spring:message code = "administrator.dashboard.1.1"/></h2>
    <jstl:forEach items="${chorbiesByCity}" var="cbc">
        <jstl:if test="${cbc!=null }">
            <li>${cbc}</li>
        </jstl:if>
    </jstl:forEach>
    <h2><spring:message code = "administrator.dashboard.1.2"/></h2>
    <jstl:forEach items="${chorbiesByCountry}" var="cbc">
        <jstl:if test="${cbc!=null }">
            <li>${cbc}</li>
        </jstl:if>
    </jstl:forEach>

    <!-- The minimum, the maximum, and the average ages of the chorbies: -->
    <h2><spring:message code = "administrator.dashboard.2"/></h2>
    <jstl:forEach items="${minMaxAvgAgesChorbies}" var="mma">
        <jstl:if test="${mma!=null}">
            <li>${mma}</li>
        </jstl:if>
    </jstl:forEach>

    <!-- The ratio of chorbies who have not registered a credit card or have registered an invalid credit card: -->
    <h2><spring:message code = "administrator.dashboard.3"/></h2>
    <jstl:out value="${ratioChorbiesInvalidCreditCard}" />

    <!-- The ratios of chorbies who search for ?activities?, ?friendship?, and ?love?: -->
    <h2><spring:message code = "administrator.dashboard.4.1"/></h2>
    <jstl:out value="${ratioChorbiesSearchActivites}" />

    <h2><spring:message code = "administrator.dashboard.4.2"/></h2>
    <jstl:out value="${ratioChorbiesSearchFriendShip}" />

    <h2><spring:message code = "administrator.dashboard.4.3"/></h2>
    <jstl:out value="${ratioChorbiesSearchLove}" />

    <!-- The list of chorbies, sorted by the number of likes they have got: -->
    <h2><spring:message code = "administrator.dashboard.5"/></h2>
    <jstl:forEach items="${chorbiesSortedByLikes}" var="csl">
        <jstl:if test="${csl!=null }">
            <li><jstl:out value="${csl.userAccount.username}"/></li>
        </jstl:if>
    </jstl:forEach>

    <!-- The minimum, the maximum, and the average number of likes per chorbi: -->
    <h2><spring:message code = "administrator.dashboard.6"/></h2>
    <jstl:forEach items="${minMaxAvgLikesPerChorbi}" var="mml">
        <jstl:if test="${mml!=null}">
            <li>${mml}</li>
        </jstl:if>
    </jstl:forEach>

    <!-- The minimum, the maximum, and the average number of chirps that a chorbi receives from other chorbies: -->
    <h2><spring:message code = "administrator.dashboard.7"/></h2>
    <jstl:forEach items="${minMaxAvgChirpsRecieved}" var="mmc">
        <jstl:if test="${mmc!=null}">
            <li>${mmc}</li>
        </jstl:if>
    </jstl:forEach>

    <!-- The minimum, the maximum, and the average number of chirps that a chorbi sends to other chorbies: -->
    <h2><spring:message code = "administrator.dashboard.8"/></h2>
    <jstl:forEach items="${minMaxAvgChirpsSended}" var="mmc">
        <jstl:if test="${mmc!=null}">
            <li>${mmc}</li>
        </jstl:if>
    </jstl:forEach>

    <!-- The chorbies who have got more chirps: -->
    <h2><spring:message code = "administrator.dashboard.9"/></h2>
    <jstl:forEach items="${chorbiMoreChirpsRecieved}" var="cmc">
        <jstl:if test="${cmc!=null }">
            <li><jstl:out value="${cmc.userAccount.username}"/></li>
        </jstl:if>
    </jstl:forEach>

    <!-- The chorbies who have sent more chirps:-->
    <h2><spring:message code = "administrator.dashboard.10"/></h2>
    <jstl:forEach items="${chorbiMoreChirpsSended}" var="cmc">
        <jstl:if test="${cmc!=null }">
            <li><jstl:out value="${cmc.userAccount.username}"/></li>
        </jstl:if>
    </jstl:forEach>

</security:authorize>