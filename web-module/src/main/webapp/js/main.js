var tictoe = {};

(function () {

    var GAME_URL = window.location.href;
    var GAME_ID;

    var TIMESTEP = 60;
    var playerTimer = new Timer(TIMESTEP);

    var STAGE_WIDTH = 350;
    var STAGE_HEIGHT = 350;

    var SQUIRE_DIMENSION = 50;   //TODO было 50
    var SQUIRE_BORDER = 4;

    var SIZE_OF_FIELD = 3; //TODO брать из аяксов
    //var SQUIRE_SIZE = SQUIRE_DIMENSION * SIZE_OF_FIELD;

    var START_OFFSET_X = 50;
    var START_OFFSET_Y = 50;

    var GRID_COLOR = '#848B96';

    var PLAYER_COLOR = 'green';
    var ENEMY_COLOR = '#D9534F';

    var playerShape;
    var enemyShape;

    // animation icon
    var animPalyersStep;
    var animEnemiesStep;


    var _stage = new Kinetic.Stage({
        container: 'container',
        width: STAGE_WIDTH,
        height: STAGE_HEIGHT
    });


    var _shapesLayer = new Kinetic.Layer();
    var _labelLayer = new Kinetic.Layer();
    var _statusLayer = new Kinetic.Layer();


    var CellGenerator = {};

    CellGenerator.getGroup = function (name) {
        return new Kinetic.Group({
            name: name,
            x: SQUIRE_DIMENSION,
            y: SQUIRE_DIMENSION,
            rotation: 0
        });
    };

    CellGenerator.getSquire = function () {
        return new Kinetic.Rect({
            x: 0,
            y: 0,
            width: SQUIRE_DIMENSION,
            height: SQUIRE_DIMENSION,
            fill: 'black',
            stroke: GRID_COLOR,
            strokeWidth: SQUIRE_BORDER
        });
    };

    CellGenerator.getCircle = function (color) {
        return new Kinetic.Circle({
            x: SQUIRE_DIMENSION / 2,
            y: SQUIRE_DIMENSION / 2,
            radius: 20,
            fill: 'black',
            stroke: color,
            strokeWidth: SQUIRE_BORDER
        });
    };

    CellGenerator.getCross = function (name, color) {
        var group = this.getGroup(name);

        var line1 = new Kinetic.Line({
            points: [5, 5, 45, 45],
            stroke: color,
            strokeWidth: SQUIRE_BORDER,
            lineCap: 'round',
            lineJoin: 'round'
        });

        var line2 = new Kinetic.Line({
            points: [45, 5, 5, 45],
            stroke: color,
            strokeWidth: SQUIRE_BORDER,
            lineCap: 'round',
            lineJoin: 'round'
        });

        group.add(this.getSquire());

        group.add(line1);
        group.add(line2);

        return group;
    };

    /**
     *
     * @param side "2" = cross or "1" = zero
     * or null - black squire without fill
     */
    CellGenerator.getCell = function (side, name, color) {
        var group = this.getGroup(name);
        group.add(this.getSquire());

        switch (side) {
            case 2:
                group = this.getCross(name, color);
                break;
            case 1:
                group.add(this.getCircle(color));
                break;
            default:
                break;
        }

        return group;
    };


    ///////////// TIMER TEXT FIELD ///////////////////////

    var playerTimeText = new Kinetic.Text({
        x: 10,
        y: STAGE_HEIGHT - 30,
        fontFamily: 'Calibri',
        fontSize: 24,
        text: '',
        fill: 'white'
    });

    // вражеское время = TIMESTEP - время пришедшее из аяксов
    var enemyTimeText = new Kinetic.Text({
        x: STAGE_WIDTH - 30,
        y: 10,
        fontFamily: 'Calibri',
        fontSize: 24,
        text: '',
        fill: 'white'
    });

//    playerTimeText.move({x: -50, y: STAGE_HEIGHT - 80});
//    enemyTimeText.move({x: STAGE_WIDTH - 80, y: -50});

    _statusLayer.add(playerTimeText);
    _statusLayer.add(enemyTimeText);


    _shapesLayer.on('mousedown', function () {
        tictoe.makeStep();
//
//        var mousePos = _stage.getPointerPosition();
//        var x = Math.floor(mousePos.x/SQUIRE_DIMENSION) - 1;
//        var y = Math.floor(mousePos.y/SQUIRE_DIMENSION) - 1;
//        var z = x*SIZE_OF_FIELD + y;
//
//        _writeMessage('mouseX: ' + mousePos.x + ' mouseY: ' + mousePos.y + ' x: ' + x + ', y: ' + y + ', cell' + z);

//         var a = [];
//        a[z] = 2;


        //_clearCell(z);

        //_addCross(z);


        //_addCross(z, ENEMY_COLOR);
        //_addZero(z, PLAYER_COLOR);

        //_clearCell(z);



        //_clearGameField();
        //_createGameField(SIZE_OF_FIELD, a);

    });

    function _writeMessage(message) {
        playerTimeText.setText(message);
        _statusLayer.draw();
    }


    ///////////// TIMER TEXT FIELD ///////////////////////

    function getCellNumber(){
        var mousePos = _stage.getPointerPosition();
        var x = Math.floor((mousePos.x -START_OFFSET_X)/SQUIRE_DIMENSION) - 1;
        var y = Math.floor((mousePos.y - START_OFFSET_Y)/SQUIRE_DIMENSION) - 1;

        var z = x*SIZE_OF_FIELD + y;

        return z;
    }


    /**
     *
     * @param n - size of squire field
     * @param cells - for server side. 0 - nothing, 1 - zero, 2 - cross
     * @private
     *
     */
    var _createGameField = function (n, cells) {
        for (var i = 0; i < n; i++) {
            for (var j = 0; j < n; j++) {
                var cell = CellGenerator.getCell(cells[i * n + j], i*n + j + 'cell', null);
                cell.move({x: i * SQUIRE_DIMENSION, y: j * SQUIRE_DIMENSION});
                _shapesLayer.add(cell);
            }
        }
        _shapesLayer.move({x: START_OFFSET_X,  y: START_OFFSET_Y });
        _stage.add(_shapesLayer);
    };


    function _clearGameField() {
        _shapesLayer.getCanvas().getContext().clear();
    }


    ////////// CELL FUNCTIONS /////////////

    function _convertCellNumberToCoord(cellNumber){
        var x = Math.floor(cellNumber/SIZE_OF_FIELD);
        var y = cellNumber - x*SIZE_OF_FIELD;
        return {x: x, y: y};
    }

    function _convertCellNumberToCoordXY(cellNumber){
        var point = _convertCellNumberToCoord(cellNumber);
        return {x : point.x * SQUIRE_DIMENSION, y : point.y* SQUIRE_DIMENSION};

    }

    function _addCross(cellNumber, color){
        var point =  _convertCellNumberToCoordXY(cellNumber);

        _clearCell(cellNumber);

        var cross = CellGenerator.getCell(2, cellNumber, color);
        cross.move({x: point.x, y: point.y});

        _shapesLayer.add(cross);
        _shapesLayer.draw();
    }

    function _addZero(cellNumber, color){
        var point =  _convertCellNumberToCoordXY(cellNumber);

        _clearCell(cellNumber);

        var zero = CellGenerator.getCell(1, cellNumber, color);
        zero.move({x: point.x, y: point.y});

        _shapesLayer.add(zero);
        _shapesLayer.draw();
    }

    function _clearCell(name){
        var cell = _stage.get('.' + name + 'cell')[0];
        if(cell){
            cell.remove();
            _shapesLayer.draw();
        }2
    }

    ////////// CELL FUNCTIONS /////////////

    /**
     *
     * @param yourName string
     * @param yourShape  1 = zero or 2 = cross
     * @param enemiesName string
     * @param enemyShape 1 = zero or 2 = cross
     * @private
     */
    function _initUsers(yourName, yourShape, enemiesName, enemyShape){

        var enemyLabel = new Kinetic.Label({
            x: STAGE_WIDTH/2,
            y: 40,
            opacity: 0.75
        });

        enemyLabel.add(new Kinetic.Tag({
            fill: ENEMY_COLOR,
            pointerDirection: 'down',
            pointerWidth: 10,
            pointerHeight: 10,
            lineJoin: 'round',
            shadowColor: 'black',
            shadowBlur: 10,
            shadowOffset: {x:0,y:10},
            shadowOpacity: 0.5
        }));

        enemyLabel.add(new Kinetic.Text({
            text: enemiesName,
            fontFamily: 'Calibri',
            fontSize: 18,
            padding: 5,
            fill: 'white'
        }));

        ////////////////////////////////////

        var playerLabel = new Kinetic.Label({
            x: STAGE_WIDTH/2,
            y: STAGE_HEIGHT - 40,
            opacity: 0.75
        });

        playerLabel.add(new Kinetic.Tag({
            fill: PLAYER_COLOR,
            pointerDirection: 'up',
            pointerWidth: 10,
            pointerHeight: 10,
            lineJoin: 'round',
            shadowColor: 'black',
            shadowBlur: 10,
            shadowOffset: {x:0,y:-10},
            shadowOpacity: 0.5
        }));

        playerLabel.add(new Kinetic.Text({
            text: yourName,
            fontFamily: 'Calibri',
            fontSize: 18,
            padding: 5,
            fill: 'white'
        }));

        _labelLayer.add(enemyLabel);
        _labelLayer.add(playerLabel);

        _stage.add(_labelLayer);

        ////////////////////////////////
        _initUsersShapes(yourShape, enemyShape);
    }

    function _initUsersShapes(uShape, eShape){
        playerShape = (uShape == 1)? _getZeroIcon(PLAYER_COLOR) : _getCrossIcon(PLAYER_COLOR);
        enemyShape = (eShape == 1)? _getZeroIcon(ENEMY_COLOR) : _getCrossIcon(ENEMY_COLOR);

        // ANIMATION //

        //parameters
        var amplitude = 50;
        var period = 2000;   // in ms
        var centerY = STAGE_HEIGHT/2;

        animPalyersStep = new Kinetic.Animation(function(frame) {
            playerShape.setY(amplitude * Math.sin(frame.time * 2 * Math.PI / period) + STAGE_HEIGHT - 80);
        }, _statusLayer);

        animEnemiesStep = new Kinetic.Animation(function(frame) {
            enemyShape.setY(amplitude * Math.sin(frame.time * 2 * Math.PI / period) + 50);
        }, _statusLayer);


        // ANIMATION //

        playerShape.move({x: STAGE_WIDTH - 80, y: STAGE_HEIGHT - 80});
        enemyShape.move({x: -50, y: -50});

        _statusLayer.add(playerShape);
        _statusLayer.add(enemyShape);

        _stage.add(_statusLayer);


        //TODO убрать отсюда. Должно вызываться из аяксов. И вообще, сделать рефакторинг, объединив кучу функций в сущности


        _showEnemiesStep();
        _showPlayersStep();

    }

    function _getCrossIcon(color){
        var crossGroup = CellGenerator.getGroup(-2 + '');

        var line1 = new Kinetic.Line({
            points: [5, 5, 25, 25],
            stroke: color,
            strokeWidth: SQUIRE_BORDER,
            lineCap: 'round',
            lineJoin: 'round'
        });

        var line2 = new Kinetic.Line({
            points: [25, 5, 5, 25],
            stroke: color,
            strokeWidth: SQUIRE_BORDER,
            lineCap: 'round',
            lineJoin: 'round'
        });

        crossGroup.add(line1);
        crossGroup.add(line2);

        return crossGroup;
    }

    function _getZeroIcon(color){
        var zeroGroup = CellGenerator.getGroup(-1 + '');
        var zero = new Kinetic.Circle({
            x: 30 / 2,
            y: 30 / 2,
            radius: 12,

            stroke: color,
            strokeWidth: SQUIRE_BORDER
        });

        zeroGroup.add(zero);

        return zeroGroup;
    }

    function _showPlayersStep(){
        animPalyersStep.start();
        animEnemiesStep.stop();
    }

    function _showEnemiesStep(){
        animPalyersStep.stop();
        animEnemiesStep.start();
    }




    tictoe.initGameFiled = function (yourName, yourSide, enemyName, enemySide, fieldSize, whoStep) {
        _createGameField(fieldSize, [0, 0, 0, 0, 0, 0, 0, 0, 0]);  //TODO брать из аяксов
        _initUsers(yourName, yourSide, enemyName, enemySide);

        if(yourSide === whoStep){
            _showPlayersStep();
        } else{
            _showEnemiesStep();
        }

    };

    tictoe.showModalWindow = function(windowId){

        var id = $(windowId);
        var maskHeight = $(document).height();
        var maskWidth = $(window).width();
        $('#mask').css({'width':maskWidth,'height':maskHeight});
        $('#mask').fadeIn(1000);
        $('#mask').fadeTo("slow",0.8);
        var winH = $(window).height();
        var winW = $(window).width();
        $(id).css('top',  winH/2-$(id).height()/2);
        $(id).css('left', winW/2-$(id).width()/2);
        $(id).fadeIn(2000);

    };

    tictoe.hideModalWindow = function(){
        $('#mask').hide();
        $('.window').hide();
    };


    tictoe.waitEnemyStep = function(){
        // показывать/скрывать <div>, закрывающий доску
        // показывать очередность хода

    };

    tictoe.init = function(){
        tictoe.showModalWindow('#dialog');

    };


    var waitId = setInterval(function(){sendReadyStateMessage();}, 3000);


    function sendReadyStateMessage(){
        var callback = function(jsondata){
            //fillGameList(jsondata);
            if(jsondata.gameState === 0){
                // ничего не делаем, ждем 2 игрока
            } else if(jsondata.gameState === 1){
                GAME_ID = jsondata.gameId;
                clearInterval(waitId);
                tictoe.hideModalWindow();
                tictoe.initGameFiled(jsondata.yourName, jsondata.yourSide, jsondata.enemyName, jsondata.enemySide, jsondata.side, jsondata.whoStep);

                if(jsondata.cell === -1){
                    updateIntervalId = setInterval(function(){tictoe.getStep();}, 3000);
                }

            }
        };

        $.get(
            GAME_URL + '/wait',
            callback
        );
    }


    var updateIntervalId;

    tictoe.makeStep = function(){
        var cellNumber = getCellNumber();

        $.ajax({
            type: "POST",
            url: "" + GAME_ID + "/step",
            data: JSON.stringify({
                    gameId: GAME_ID,
                    cell: cellNumber
                }
            ),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (jsondata) {

             _updateGameGrid(jsondata);
              updateIntervalId = setInterval(function(){tictoe.getStep();}, 3000);
            }//,

//            error: function (errormessage) {
//                window.location = errormessage.responseText + "";
//            }
        });
    };



    function _updateGameGrid(jsondata){

        if(jsondata){

            if(jsondata.lastStep === jsondata.yourSide){

                if(jsondata.lastStep == 1){
                    _addZero(jsondata.cell, PLAYER_COLOR);
                } else{
                    _addCross(jsondata.cell, PLAYER_COLOR);
                }

            } else{

                if(jsondata.lastStep == 1){
                    _addZero(jsondata.cell, ENEMY_COLOR);
                } else{
                    _addCross(jsondata.cell, ENEMY_COLOR);
                }

            }

            // TODO отрефакторить, убрать вообще нафиг параметр whoStep
            // нельзя. Надо же как-то первый ход мониторить. Надо было думать перед тем, как все это городить...
            // нет можно. Есть же cell=-1
            if(jsondata.yourSide === jsondata.whoStep){
                _showPlayersStep();
                playerTimer.startTimer();
                tictoe.writeEnemyTime("");
            } else{
                _showEnemiesStep();
                playerTimer.stopTimer();
                tictoe.writeEnemyTime(TIMESTEP - Math.ceil(jsondata.enemyTimeStep/1000));
            }



//            if(jsondata.lastStep === jsondata.yourSide){
//                if(jsondata.gameState === 2){
//                    tictoe.showModalWindowVictory();
//                    _clearIntervals();
//                }
//            } else{
//                if(jsondata.gameState === 2){
//                    tictoe.showModalWindowFail();
//                    _clearIntervals();
//                }
//            }

            if(jsondata.gameState === 2){
                if(jsondata.lastStep === jsondata.yourSide){
                    tictoe.showModalWindowVictory();
                } else{
                    tictoe.showModalWindowFail();
                }
                _clearIntervals();
                playerTimer.stopTimer();

            } else if(jsondata.gameState === 4){
                tictoe.showModalWindowEnemyLeft();
                _clearIntervals();
                playerTimer.stopTimer();
            }


        }
    }

    function _clearIntervals(){
        clearInterval(updateIntervalId);
        clearInterval(waitId);
    }

    tictoe.showModalWindowVictory = function(){
        tictoe.showModalWindow('#victory');

    };

    tictoe.hideModalWindowVictory = function(){
        tictoe.hideModalWindow();
    };

    tictoe.showModalWindowFail = function(){
        tictoe.showModalWindow('#fail');

    };

    tictoe.showModalWindowEnemyLeft = function(){
        tictoe.showModalWindow('#enemyLeft');

    };

    tictoe.hideModalWindowFail = function(){
        tictoe.hideModalWindow();
    };

    tictoe.getStep = function(){
        var callback = function(jsondata){
            if(jsondata.lastStep !== jsondata.yourSide){
                clearInterval(updateIntervalId);

                // ты ходишь первым, запускаем таймер
                if(jsondata.cell === -1){
                    playerTimer.startTimer();
                }

            }

            if(jsondata.cell !== -1 /* || !(jsondata.cell === 0 && jsondata.lastCell === 0) */){
                _updateGameGrid(jsondata);
            }

        };

        $.get(
            "" + GAME_ID + "/step",
            callback
        );
    };

    tictoe.deleteGame = function(){
        var callback = function(jsondata){
            window.location.href = jsondata; //redirect
        };

        $.ajax({
            url: GAME_URL,
            type: 'DELETE',
            success: function(result) {
                callback(result);
            },
            error: function(result){
                window.location = result.responseText + "";
            }
        });

    };

    // таймер на ход

    function Timer(timestep){

        var _timerId;
        var _time;

        this.startTimer = function(){
            _time = timestep;
            _timerId = setInterval( _update, 1000);

        }

        this.stopTimer =  function(){
            _updateView("");
            clearInterval(_timerId);
        }

        function _update(){
            _time--;
            if(_time <= 0){
                tictoe.showModalWindowFail();
                timer.stopTimer();
            } else{
                _updateView(_time);
            }
        }

        function _updateView(time) {

            if(30 < time && time < 60){
                playerTimeText.setAttrs({fill: 'green'});
            } else if(10 < time && time < 30){
                playerTimeText.setAttrs({fill: 'yellow'});
            } else if(0 < time && time < 10){
                playerTimeText.setAttrs({fill: 'red'});
            }

            _writeMessage(time);
           // _shapesLayer.draw();
            //_writeMessage(time, 'color');
        }
    }


    tictoe.writeEnemyTime = function(time){
        // отвлекает и путает
        /*
        if(30 < time && time < 60){
            enemyTimeText.setAttrs({fill: 'green'});
        } else if(10 < time && time < 30){
            enemyTimeText.setAttrs({fill: 'yellow'});
        } else if(0 < time && time < 10){
            enemyTimeText.setAttrs({fill: 'red'});
        }
        */

        enemyTimeText.setAttrs({fill: 'red'});
        enemyTimeText.setText(time);

        _statusLayer.draw();
    }






})();


$(window).resize(function(){
    moveGameToCenter();
});


function moveGameToCenter(){
    $('#dialog').css({
        position:'absolute',
        left: ($(document).width() - $('.center').outerWidth())/2,
        top: ($(document).height() - $('.center').outerHeight())/2
    });

    $('#victory').css({
        position:'absolute',
        left: ($(document).width() - $('.center').outerWidth())/2,
        top: ($(document).height() - $('.center').outerHeight())/2
    });

    $('#fail').css({
        position:'absolute',
        left: ($(document).width() - $('.center').outerWidth())/2,
        top: ($(document).height() - $('.center').outerHeight())/2
    });

    $('#enemyLeft').css({
        position:'absolute',
        left: ($(document).width() - $('.center').outerWidth())/2,
        top: ($(document).height() - $('.center').outerHeight())/2
    });
}


$("#container canvas:last").css( "position", "relative" );
