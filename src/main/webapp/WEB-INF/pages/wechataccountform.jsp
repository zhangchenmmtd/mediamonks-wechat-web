<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href='http://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900' rel='stylesheet' type='text/css'>

    <!-- Page title -->
    <title>Mediamonks - WeChat - admin</title>

    <link rel="stylesheet" href="/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<c:if test="${not empty error}">
    <script>
        alert('${error}');
    </script>
</c:if>
<div class="wrapper">
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <div id="mobile-menu">
                    <div class="left-nav-toggle">
                        <a href="#">
                            <i class="stroke-hamburgermenu"></i>
                        </a>
                    </div>
                </div>
                <a class="navbar-brand" href="/">
                    Mediamonks
                </a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <form class="navbar-form navbar-left" action="/">
                    <input type="text" class="form-control" placeholder="Search client" style="width: 175px">
                </form>
                <ul class="nav navbar-nav navbar-right">
                    <li class=" profil-link">
                        <a href="/logout">
                            <span class="profile-address">Logout</span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <aside class="navigation">
        <nav>
            <ul class="nav luna-nav">
                <li class="nav-category">
                    Main
                </li>
                <li class="active">
                    <a href="/">Clients</a>
                </li>
                <li class="nav-info">
                    <i class="pe pe-7s-shield text-accent"></i>
                    <div class="m-t-xs">
                        <span class="c-white">zhang.chen@mediamonks.com</span> contact this guy if u find any issues of
                        this site
                    </div>
                </li>
            </ul>
        </nav>
    </aside>
    <section class="content">
        <div class="container-fluid" id="main-container">
            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-filled">
                        <div class="panel-heading">
                            Wechat form
                        </div>
                        <div class="panel-body">
                            <form action="/client/wechat//update" method="post">
                                <input type="hidden" name="clientGuid" value="${wechatAccount.clientGuid}">
                                <input type="hidden" name="guid" value="${wechatAccount.guid}">
                                <c:if test="${not empty wechatAccount.guid}">
                                    <div class="form-group">
                                        <label>Wechat ID: ${wechatAccount.guid}</label>
                                    </div>
                                </c:if>

                                <div class="form-group">
                                    <label>App ID</label>
                                    <input type="text" class="form-control" placeholder="App ID" name="appId"
                                           value="${wechatAccount.appId}">
                                </div>
                                <div class="form-group">
                                    <label>App Secret</label>
                                    <input type="text" class="form-control" placeholder="App Secret" name="appSecret"
                                           value="${wechatAccount.appSecret}">
                                </div>
                                <div class="form-group">
                                    <label>Account type</label>
                                    <select name="accountType">
                                        <c:forEach items="${accountTypes}" var="accountType">
                                            <option value="${accountType}">${accountType.accountName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <button type="submit" class="btn btn-default">Submit</button>
                                <a href="/client/${wechatAccount.clientGuid}/wechataccounts" class="btn btn-default">Cancel</a>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
<script src="/js/jquery-2.2.4.min.js"></script>
<script src="/js/bootstrap.js"></script>


</body>

</html>


