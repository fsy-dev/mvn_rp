var login = {};

(function(){


    function getGameList(){

        var callback = function(jsondata){
            fillGameList(jsondata);
        };

        $.get(
            "games/list",
//            {
//                param1: "param1",
//                param2: 2
//            },
            callback
        );

    }

    function clearGameList(){
        $('#select').empty();
    }

    function fillGameList(gamesArray){

        //сохраняем выбранный элемент
        var value = $("#select").val();

        clearGameList();

        for(var i=0; i < gamesArray.length; i++){
            $('#select').append( $('<option value=' + gamesArray[i].id + '>' + gamesArray[i].creator.name + '</option>'));
        }

        //выбираем выбранный элемент, если он был выбран
        if(value){
            var s =  "#select [value='" + value +"']";
            $(s).attr("selected", "selected");
        }

    }



    function getUserName(){
        return $('#username').val();
    }

    login.createGame = function(){
        $.ajax({
            type: "POST",
            url: "games",
            data: JSON.stringify({
                    name: getUserName(),
                    side: 0
                }
            ),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (jsondata) {
                if(jsondata){
                    alert(jsondata.toString());
                    window.location = jsondata;
                }

            },

            error: function (errormessage) {
                window.location = errormessage.responseText + "";
                //alert(errormessage.toString());
            }
        });

    };

    login.getGameList = function(){
        getGameList();
    };


    login.joinToGame = function(){
        var gameId = $('#select :selected').val();
        var name = getUserName();

        $.ajax({
            type: "POST",
            url: "games/game/" + gameId,
            data: JSON.stringify({
                    name: getUserName(),
                    side: 0
                }
            ),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (jsondata) {
                if(jsondata){
                    alert(jsondata.toString());
                    window.location = jsondata;
                }

            },

            error: function (errormessage) {
                window.location = errormessage.responseText + "";
                //alert(errormessage.toString());
            }
        });


    };




    var timeoutId = setInterval(function(){login.getGameList()}, 2000);



})();