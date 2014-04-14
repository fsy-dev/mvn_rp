<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
          xmlns:c="http://java.sun.com/jsp/jstl/core" version="2.0"
          xmlns:sf="http://www.springframework.org/tags/form"
        >
    <jsp:directive.page language="java"
                        contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"/>
    <jsp:text>
        <![CDATA[ <?xml version="1.0" encoding="UTF-8" ?> ]]>
    </jsp:text>
    <jsp:text>
        <![CDATA[ <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> ]]>
    </jsp:text>
    <html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Tic-tac-toe</title>

        <![CDATA[
        <link href="../../../css/lib/bootstrap.min.css" rel="stylesheet"/>
        ]]>

        <![CDATA[ <link href="../../../css/less/main.less" rel="stylesheet/less" type="text/css" />  ]]>
    </head>

    <body onload="tictoe.init();">

    <nav class="navbar navbar-inverse" role="navigation">
        <!-- Brand and toggle get grouped for better mobile display -->
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

    <!-- Само модальное окно -->
    <div id="boxes">
        <div id="dialog" class="window">
            <div class="top"><b>Tic-Tac-Toe</b></div>
            <div class="content">
                <p>Please, wait the 2 player...</p>

                <div class="progress progress-striped active">
                    <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="100"
                         aria-valuemin="0" aria-valuemax="100" style="width: 100%">
                        <span class="sr-only">100% Complete</span>
                    </div>
                </div>

                <button type="button" class="btn btn-danger" style="margin-left: 127px; width: 100px" onclick="tictoe.deleteGame();">Exit</button>
                <!-- (375-100)/2 = 137 и минус 10 на паддинг дива -->

            </div>
        </div>

        <div id="victory" class="window">
            <div class="top v"><b>Tic-Tac-Toe</b></div>
            <div class="content">
                <p>You win</p>

                <button type="button" class="btn btn-success" style="margin-left: 127px; width: 100px" onclick="tictoe.deleteGame();">Exit</button>
                <!-- (375-100)/2 = 137 и минус 10 на паддинг дива -->

            </div>
        </div>

        <div id="fail" class="window">
            <div class="top"><b>Tic-Tac-Toe</b></div>
            <div class="content">
                <p>You loose</p>

                <button type="button" class="btn btn-danger" style="margin-left: 127px; width: 100px" onclick="tictoe.deleteGame();">Exit</button>
                <!-- (375-100)/2 = 137 и минус 10 на паддинг дива -->

            </div>
        </div>

        <div id="enemyLeft" class="window">
            <div class="top"><b>Tic-Tac-Toe</b></div>
            <div class="content">
                <p>Enemy left the game</p>

                <button type="button" class="btn btn-danger" style="margin-left: 127px; width: 100px" onclick="tictoe.deleteGame();">Exit</button>
                <!-- (375-100)/2 = 137 и минус 10 на паддинг дива -->

            </div>
        </div>
    </div>

    <!-- Маска, затемняющая фон -->
    <jsp:text>
        <![CDATA[<div id="mask"></div> ]]>
    </jsp:text>

    <![CDATA[
    <div class="center">
        <div id="container"></div>

        <button type="button" class="btn btn-danger btn-block btn-lg" style="padding-top: 20" onclick="tictoe.deleteGame();">Exit</button>
    </div>
    ]]>

    <![CDATA[<script src="../../../js/lib/kinetic-v5.0.1.min.js"></script> ]]>
    <![CDATA[<script src="../../../js/lib/jquery-2.0.3.js"></script>       ]]>
    <![CDATA[<script src="../../../js/lib/bootstrap.min.js"></script>      ]]>
    <![CDATA[<script src="../../../js/lib/less-1.3.3.min.js"></script>     ]]>
    <![CDATA[<script src="../../../js/main.js"></script>                   ]]>

    </body>
    </html>

</jsp:root>