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
                            <div class="panel-tools">
                                <a class="panel-toggle"><i class="fa fa-chevron-up"></i></a>
                                <a class="panel-close"><i class="fa fa-times"></i></a>
                            </div>
                            ${client.name}
                        </div>
                        <div class="panel-body">

                            <div class="table-responsive">
                                <div class="dataTables_paginate paging_simple_numbers">
                                    <ul class="pagination">
                                        <li class="paginate_button next"><a href="/client/${client.guid}/wechat/-1/edit" >Add WeChat account</a>
                                        </li>
                                    </ul>
                                </div>
                                <div id="tableExample3_wrapper"
                                     class="dataTables_wrapper form-inline dt-bootstrap no-footer">
                                    <table id="tableExample3"
                                           class="table table-striped table-hover dataTable no-footer" role="grid">
                                        <thead>
                                        <tr role="row">
                                            <th>wechatID</th>
                                            <th>Type
                                            </th>
                                            <th>AppId
                                            </th>
                                            <th>AppSecret
                                            </th>
                                            <th>Availability
                                            </th>
                                        </tr>
                                        </thead>
                                        <tbody>

                                        <c:forEach items="${client.wechatAccountCommands}" var="wechatAccount">
                                            <tr role="row" class="odd">
                                                <td>${wechatAccount.guid}</td>
                                                <td>
                                                        ${wechatAccount.accountType}
                                                </td>
                                                <td>${wechatAccount.appId}</td>
                                                <td>*******</td>
                                                <td class="${wechatAccount.enabled? 'c-white' :''} ">
                                                    <c:if test="${wechatAccount.enabled}">Available</c:if>
                                                    <c:if test="${not wechatAccount.enabled}">Not available</c:if>
                                                </td>
                                                <td>
                                                    <ul class="pagination" style="margin: 0">
                                                        <li class="paginate_button next"><a href="/client/${client.guid}/wechat/${wechatAccount.guid}/edit">Edit</a></li>
                                                        <li class="paginate_button">
                                                            <c:if test="${wechatAccount.enabled}"><a
                                                                    href="/client/${client.guid}/wechat/${wechatAccount.guid}/updatestatus">Disable</a></c:if>
                                                            <c:if test="${not wechatAccount.enabled}"><a
                                                                    href="/client/${client.guid}/wechat/${wechatAccount.guid}/updatestatus">Enable</a></c:if>
                                                        </li>
                                                    </ul>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                    <div class="dataTables_paginate paging_simple_numbers" id="tableExample3_paginate">
                                        <ul class="pagination">
                                            <li class="paginate_button next"><a href="/client/${client.guid}/wechat/-1/edit" >Add WeChat account</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>

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







