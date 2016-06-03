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
                            Clients
                        </div>
                        <div class="panel-body">

                            <div class="table-responsive">
                                <div class="dataTables_paginate paging_simple_numbers" id="tableExample3_paginate">
                                    <ul class="pagination">
                                        <li class="paginate_button next"><a href="/client/-1/edit">Add
                                            client</a>
                                        </li>
                                        <c:forEach step="1" begin="1" end="${clients.pages}" var="step">
                                            <li class="${clients.currentpage eq step-1?'active':''}">
                                                <a href="/?pageNumber=${step-1}">${step}</a>
                                            </li>
                                        </c:forEach>


                                    </ul>
                                </div>
                                <div id="tableExample3_wrapper"
                                     class="dataTables_wrapper form-inline dt-bootstrap no-footer">
                                    <table id="tableExample3"
                                           class="table table-striped table-hover dataTable no-footer" role="grid">
                                        <thead>
                                        <tr role="row">
                                            <th>Name</th>
                                            <th>Availability
                                            </th>
                                            <th>Wechat accounts
                                            </th>
                                            <th>Action
                                            </th>

                                        </tr>
                                        </thead>
                                        <tbody>

                                        <c:forEach items="${clients.clientCommands}" var="client">
                                            <tr role="row" class="odd">
                                                <td>${client.name}</td>
                                                <td class="${client.enabled? 'c-white' :''}">
                                                    <c:if test="${client.enabled}">Available</c:if>
                                                    <c:if test="${not client.enabled}">Not available</c:if>
                                                </td>
                                                <td>${client.wechatAccounts}</td>
                                                <td>
                                                    <ul class="pagination" style="margin: 0">
                                                        <li class="paginate_button next"><a href="/client/${client.guid}/edit">Edit</a>
                                                        </li>
                                                        <li class="paginate_button"><a href="/client/${client.guid}/wechataccounts">Detail</a>
                                                        </li>
                                                        <li class="paginate_button">
                                                            <c:if test="${client.enabled}"><a
                                                                    href="/client/${client.guid}/updatestatus">Disable</a></c:if>
                                                            <c:if test="${not client.enabled}"><a
                                                                    href="/client/${client.guid}/updatestatus">Enable</a></c:if>
                                                        </li>
                                                    </ul>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                    <div class="dataTables_paginate paging_simple_numbers">
                                        <ul class="pagination">
                                            <li class="paginate_button next"><a href="/client/-1/edit">Add
                                                client</a>
                                            </li>
                                            <c:forEach step="1" begin="1" end="${clients.pages}" var="step">
                                                <li class="${clients.currentpage eq step-1?'active':''}">
                                                    <a href="/?pageNumber=${step-1}">${step}</a>
                                                </li>
                                            </c:forEach>


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