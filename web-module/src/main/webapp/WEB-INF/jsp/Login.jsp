<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
          xmlns:c="http://java.sun.com/jsp/jstl/core" version="2.0"
          xmlns:sf="http://www.springframework.org/tags/form"
        >
    <jsp:directive.page language="java"
                        contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
    <jsp:text>
        <![CDATA[ <?xml version="1.0" encoding="UTF-8" ?> ]]>
    </jsp:text>
    <jsp:text>
        <![CDATA[ <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> ]]>
    </jsp:text>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Tic-tac-toe</title>

    <![CDATA[ <link href="../css/lib/bootstrap.min.css" rel="stylesheet" /> ]]>

    <![CDATA[ <link href="../css/less/main.less" rel="stylesheet/less" type="text/css" />  ]]>

</head>
<body>

<nav class="navbar navbar-inverse" role="navigation">

    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="">Tic-Tac-Toe</a>
    </div>
</nav>


<div id="boxes">
    <div id="dialog" class="window">
        <div class="top"><b>Tic-Tac-Toe</b></div>
        <div class="content">
            <p>Please, wait the 2 player...</p>

            <div class="progress progress-striped active">
                <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="100" aria-valuemin="0"
                     aria-valuemax="100" style="width: 100%">
                    <span class="sr-only">100% Complete</span>
                </div>
            </div>

            <button type="button" class="btn btn-danger" style="margin-left: 127px; width: 100px">Exit</button>


        </div>
    </div>
</div>
<jsp:text>
    <![CDATA[<div id="mask"> </div>   ]]>
</jsp:text>
<![CDATA[
<div class="container" style="width:80%">
    <div class="row">
        <div class="col-2 col-sm-2 col-lg-2">


            <div class="lineBlock">
                <div class="panel panel-danger">
                    <div class="panel-heading ">Active Games</div>
                    <div class="pnl-body">
                        <div class="sidebar-module sidebar-module-inset">
                            <select id="select" size="15">
                            </select>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <div class="col-4 col-sm-4 col-lg-4">
            <div class="input-group">
                <input type="text" class="form-control" id="username" />
                <div class="input-group-btn">
                    <button type="button" class="btn btn-danger dropdown-toggle" data-toggle="dropdown">Action <span class="caret"></span></button>
                    <ul class="dropdown-menu pull-right">
                        <li><input type="button" value="Play" class="dropp_menu_button" onclick="login.joinToGame()" /></li>
                        <li class="divider"></li>
                        <li><input type="button" class="dropp_menu_button" value="Create Game" onclick="login.createGame()" /></li>
                    </ul>
                </div>
            </div>
        </div>


    </div>

</div>
]]>

<![CDATA[<script src="../js/lib/jquery-2.0.3.js"></script>    ]]>
<![CDATA[ <script src="../js/lib/bootstrap.min.js"></script>  ]]>
<![CDATA[<script src="../js/lib/less-1.3.3.min.js"></script>    ]]>
<![CDATA[<script src="../js/login.js"></script>    ]]>

</body>
</html>

</jsp:root>