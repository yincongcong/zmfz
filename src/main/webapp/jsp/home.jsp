<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap Login Form Template</title>
    <!-- CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/boot/css/bootstrap.min.css">
    <%--引入jqgrid中的主体样式--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <script src="${pageContext.request.contextPath}/boot/js/jquery-3.3.1.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/jquery.backstretch.min.js"></script>
    <script src="${pageContext.request.contextPath}/boot/js/jquery.validate.min.js"></script>
    <script src="${pageContext.request.contextPath}/boot/js/jquery.validate.min.js"></script>
    <%--国际化--%>
    <script src="${pageContext.request.contextPath}/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <%--jqGrid核心js--%>
    <script src="${pageContext.request.contextPath}/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${pageContext.request.contextPath}/boot/js/ajaxfileupload.js"></script>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/kindeditor-all.js"></script>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/lang/zh-CN.js"></script>
    <style>
        .navbar-nav > li > a {
            padding-top: 15px;
            padding-bottom: 15px;
            margin-right: 16px;
        }
    </style>
</head>

<body>
<div class="container">
    <nav class="navbar navbar-default">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">持名法州管理系统</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#">欢迎:</a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                           aria-expanded="false">
                            退出登录
                            <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/poi/adminPoi">管理员导出</a>
                    </li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
</div>
<div class="container">
    <%--一行--%>
    <div class="row">
        <%--管理菜单--%>
        <div class="col-sm-2">
            <div class="panel-group" id="parent">
                <!--
                    用户管理
                -->
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <a href="#first" data-toggle="collapse" data-parent="#parent">
                                用户管理
                            </a>
                        </h3>
                    </div>

                    <div id="first" class="panel-collapse collapse">
                        <div class="panel-body">
                            <%--
                               javascript:void(0)  :   阻止页面提交
                             --%>
                            <a href="" class="btn btn-danger">
                                用户列表
                            </a>
                        </div>
                    </div>
                </div>

                <!--
                    上师管理
                -->
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <a href="#second" data-toggle="collapse" data-parent="#parent">上师管理</a>
                        </h3>
                    </div>

                    <div class="panel-collapse collapse" id="second">
                        <div class="panel-body">
                            <p><a href="">asd</a></p>
                            <p><a href="">asd</a></p>
                            <p><a href="">asd</a></p>
                        </div>
                    </div>
                </div>

                <!--
                    文章管理
                -->
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <a href="#third" data-toggle="collapse" data-parent="#parent">
                                文章管理
                            </a>
                        </h3>
                    </div>
                    <div class="panel-collapse collapse" id="third">
                        <div class="panel-body">
                            <p>
                                <a href="javascript:void(0)" onclick="$('#myContent').load('article.jsp')">图书详情</a>
                            </p>
                        </div>
                    </div>
                </div>
                <%--
                章节管理
                --%>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <a href="#zhangjie" data-toggle="collapse" data-parent="#parent">专辑管理</a>
                        </h3>
                    </div>

                    <div class="panel-collapse collapse" id="zhangjie">
                        <div class="panel-body">
                            <p><a href="javascript:void(0)" onclick="$('#myContent').load('album.jsp')">章节列表</a></p>
                        </div>
                    </div>
                </div>
                <%--
                    轮播图管理
                --%>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <a href="#lunbo" data-toggle="collapse" data-parent="#parent">轮播图管理</a>
                        </h3>
                    </div>

                    <div class="panel-collapse collapse" id="lunbo">
                        <div class="panel-body">
                            <p><a href="javascript:void(0)" onclick="$('#myContent').load('banner.jsp')">大图轮播列表</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%--内容展示--%>
        <div class="col-sm-10" id="myContent">
            <%--巨幕--%>
            <div class="jumbotron">
                <h2>欢迎来到持名法州后台管理系统</h2>
                <%--<p>...</p>
                <p><a class="btn btn-primary btn-lg" href="#" role="button">Learn more</a></p>--%>
            </div>
            <%--大图轮播--%>
            <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                <!-- Indicators -->
                <ol class="carousel-indicators">
                    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                </ol>

                <!-- Wrapper for slides -->
                <div class="carousel-inner" role="listbox">
                    <div class="item active">
                        <img src="${pageContext.request.contextPath}/img/shouye.jpg" alt="...">
                        <div class="carousel-caption">
                            ...
                        </div>
                    </div>
                    <div class="item">
                        <img src="${pageContext.request.contextPath}/img/shouye.jpg" alt="...">
                        <div class="carousel-caption">
                            ...
                        </div>
                    </div>
                    <div class="item">
                        <img src="${pageContext.request.contextPath}/img/shouye.jpg" alt="...">
                        <div class="carousel-caption">
                            ...
                        </div>
                    </div>
                </div>

                <!-- Controls -->
                <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
        </div>
    </div>
</div>
<div class="container">
    <nav class="navbar navbar-default navbar-fixed-bottom">
        <div class="container">
            <p class="navbar-text" style="margin-left: 500px">@百知教育 baizhi @zparkhr.com.cn</p>
        </div>
    </nav>
</div>
</body>

</html>