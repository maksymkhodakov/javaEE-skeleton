<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ page import="com.mainacad.service.ItemService" %>
<!DOCTYPE html>
<html lang="en" class="mdl-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Store</title>
>
</head>

<c:set var="user" value="${user}" scope="session" />
<c:set var="items" value="${items}" scope="request" />

<body class="bg-light">
<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <a class="navbar-brand" href="#">Our store</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarCollapse">
        <ul class="navbar-nav mr-auto">
        </ul>
        <c:if test = "${user != null}">
            <form class="form mt-2 mt-md-0">
                    <span class="navbar-text hello-user-text">
                        Hello, <b><c:out value="${user.firstName}" /> <c:out value="${user.login}" /></b>
                    </span>
                <a class="btn btn-sm btn-outline-secondary" href="#" type="button">Cart</a>
<%--     Logout button            --%>
                <a class="btn btn-sm btn-outline-secondary" href="<c:url value = '/user?action=logout'/>" type="button">Logout</a>
            </form>
        </c:if>
        <c:if test = "${user == null}">
            <form class="form mt-2 mt-md-0">
                <a class="btn btn-sm btn-outline-secondary" href="<c:url value = '/jsp/registration.jsp'/>" type="submit">Register</a>
                <a class="btn btn-sm btn-outline-secondary" href="<c:url value = '/'/>" type="submit">Login</a>
            </form>
        </c:if>
        <form class="form-inline mt-2 mt-md-0 d-none">
            <input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
    </div>
</nav>

<sql:setDataSource
        var="shop_db_ma"
        driver="org.postgresql.Driver"
        url="jdbc:postgresql://localhost:5432/shop_db_ma"
        user="postgres" password="Max05012004"
/>

<sql:query var="list_items" sql="SELECT * FROM items" dataSource="${shop_db_ma}"/>


<main role="main" class="container-fluid bg-light">
    <div class="row">
        <c:forEach items="${list_items.rows}" var="items">
            <div class="col-xl-2 col-md-4 col-sm-6 col-12">
                <div class="card mb-4 shadow-sm">
                    <div class="card-body">
                        <h5 class="card-text">${items.name}</h5>
                        <p class="card-text"><b>Price: </b>${items.price}<span  class="float-right"><b>code: </b>${items.item_code}</span></p>
                        <div class="d-flex justify-content-between align-items-center">
                            <div class="btn-group">
                                <button type="button" class="btn btn-sm btn-outline-secondary">View</button>
                                <c:if test = "${user != null}">
                                    <a href=<c:url value = '/jsp/Cart.jsp'/>>Add to cart</a>
                                </c:if>
                            </div>
                            <small class="text-muted d-none">9 mins</small>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</main>

</body>
</html>