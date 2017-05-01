<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.jpg" alt="Acme-Chorbi" />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
		<li><a href="manager/administrator/register.do"><spring:message
								code="master.page.managerRegister" /></a></li>
			<li><a class="fNiv"><spring:message
						code="master.page.chorbi.management" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/listChorbiNotBanned.do"><spring:message
								code="master.page.chorbiBann" /></a></li>
					<li><a href="administrator/listChorbiBanned.do"><spring:message
								code="master.page.chorbiUnban" /></a></li>
					<li><a href="chorbi/administrator/updateFees.do"><spring:message
								code="master.page.updateFees" /></a></li>
				</ul></li>


			<li><a class="fNiv" href="cache/administrator/edit.do"><spring:message
						code="master.page.cache" /></a></li>
			<li><a class="fNiv" href="fee/administrator/edit.do"><spring:message
						code="master.page.fee" /></a></li>
			<li><a class="fNiv" href="dashboard/administrator.do"><spring:message
						code="master.page.dashboard" /></a></li>
			<li><a class="fNiv" href="banner/administrator/list.do"><spring:message
						code="master.page.banner" /></a></li>
		</security:authorize>

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message
						code="master.page.listEvents" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="event/list.do"><spring:message
						code="master.page.listEvents" /></a></li>
					<li><a href="event/close.do"><spring:message
						code="master.page.closeEvents" /></a></li>
				</ul></li>
			<li><a class="fNiv" href="chorbi/register.do"><spring:message
						code="master.page.register" /></a></li>
		</security:authorize>

		<security:authorize access="isAuthenticated()">

			<li><a class="fNiv"><spring:message
						code="master.page.chorbi" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="chorbi/list.do"><spring:message
								code="master.page.chorbiList" /></a></li>
					<security:authorize access="hasRole('CHORBI')">
						<li><a href="like/chorbi/listLikes.do"><spring:message
									code="master.page.chorbi.likes" /></a></li>
						<li><a href="like/chorbi/listLikedBy.do"><spring:message
									code="master.page.chorbi.likedBy" /></a></li>
					</security:authorize>
				</ul></li>
		</security:authorize>
		<security:authorize access="hasRole('CHORBI')">
		<li><a class="fNiv"><spring:message
						code="master.page.listEvents" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="event/chorbi/listMyEvents.do"><spring:message
								code="master.page.myevents" /></a></li>
					<li><a href="event/list.do"><spring:message
								code="master.page.allevents" /></a></li>
					<li><a href="event/close.do"><spring:message
						code="master.page.closeEvents" /></a></li>
				</ul></li>
			<li><a href="chirp/chorbi/list.do"><spring:message
						code="master.page.chirps" /></a></li>
			<li><a href="chorbi/listILike.do"><spring:message
						code="master.page.chorbisILike" /></a></li>
			<li><a class="fNiv" href="template/chorbi/search.do"><spring:message
						code="master.page.search" /></a></li>
		</security:authorize>


		<security:authorize access="hasRole('MANAGER')">
			<li><a class="fNiv"><spring:message
						code="master.page.listEvents" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="event/list.do"><spring:message
						code="master.page.listEvents" /></a></li>
					<li><a href="event/manager/listMyEvents.do"><spring:message
						code="master.page.myevents" /></a></li>
					<li><a href="event/manager/create.do"><spring:message
								code="master.page.createEvent" /></a></li>
					<li><a href="event/close.do"><spring:message
						code="master.page.closeEvents" /></a></li>
				</ul></li>
		</security:authorize>


		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('CHORBI')">
						<li><a href="chorbi/edit.do"><spring:message
									code="master.page.profile.edit" /></a></li>
					</security:authorize>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

