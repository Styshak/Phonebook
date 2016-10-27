<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:url var="contactDetail" value="/contactDetail/0"/>

<html>
<head>
    <title>Contacts</title>
    <link href="${contextPath}/resources/css/contacts.css" rel="stylesheet">
</head>
<body>

<c:if test="${pageContext.request.userPrincipal.name != null}">
    <h2>Welcome, ${pageContext.request.userPrincipal.name}</h2>
</c:if>

<form name="submitForm" method="POST" action="${contextPath}/logout">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input class="logoutBtn" type="submit" value="Logout"/>
</form>

<br/>
<br/>

<h1 align="center">Contacts</h1>

<div id="searchContainer" align="center">
    <form action="/searchContact", method="POST">
        <input type="text" name="searchValue" width="200px">
        <select name="searchType">
            <option value="First name">First name</option>
            <option value="Mobile phone">Mobile phone</option>
        </select>
        <input type="submit" value="Search" class="searchBtn">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
    <form:form method="GET" modelAttribute="contactForm" class="form-signin" action="${contactDetail}" >
        <input class="addBtn" type="submit" value="Add new contact"/>
    </form:form>
</div>

<c:if test="${!empty contactList}">
    <table align="center" class="tg">
        <tr>
            <th width="120">First name</th>
            <th width="120">Middle name</th>
            <th width="120">Last name</th>
            <th width="120">Mobile phone</th>
            <th width="120">Phone</th>
            <th width="120">Address</th>
            <th width="120">Email</th>
            <th width="80">Edit</th>
            <th width="80">Delete</th>
        </tr>
        <c:forEach items="${contactList}" var="contact">
            <tr>
                <td>${contact.firstName}</td>
                <td>${contact.middleName}</td>
                <td>${contact.lastName}</td>
                <td>${contact.mobilePhone}</td>
                <td>${contact.phone}</td>
                <td>${contact.address}</td>
                <td>${contact.email}</td>
                <td><a href="<c:url value='/contactDetail/${contact.id}'/>">Edit</a></td>
                <td><a href="<c:url value='/remove/${contact.id}'/>">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<c:if test="${empty contactList}">
    <h2 align="center">There are currently no contacts in the list. Please, add a contact.</h2>
</c:if>
</body>
</html>
