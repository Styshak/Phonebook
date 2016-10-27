<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">

    <title>Contact details</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

</head>

<body>

<c:url var="contactDetail" value="/contactDetail"/>

<div class="container">

    <form:form method="POST" modelAttribute="contactForm" class="form-signin" action="${contactDetail}">
        <c:set var="contactId" value="${contactForm.id}"/>
        <h2 class="form-signin-heading">${contactId > 0 ? "Edit contact" : "Add contact"}</h2>

        <spring:bind path="id">
            <form:input type="hidden" path="id"></form:input>
        </spring:bind>
        <spring:bind path="firstName">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="firstName" class="form-control" placeholder="First name"
                            autofocus="true"></form:input>
                <form:errors path="firstName"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="middleName">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="middleName" class="form-control" placeholder="Middle Name"></form:input>
                <form:errors path="middleName"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="lastName">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="lastName" class="form-control"
                            placeholder="Last name"></form:input>
                <form:errors path="lastName"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="mobilePhone">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="mobilePhone" class="form-control"
                            placeholder="Mobile phone"></form:input>
                <form:errors path="mobilePhone"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="phone">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="phone" class="form-control"
                            placeholder="Phone"></form:input>
                <form:errors path="phone"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="address">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="address" class="form-control"
                            placeholder="Address"></form:input>
                <form:errors path="address"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="email">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="email" class="form-control"
                            placeholder="Email"></form:input>
                <form:errors path="email"></form:errors>
            </div>
        </spring:bind>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form:form>

</div>
</body>
</html>