var stompClient = null;
var squareWidth = null;

function connect() {
    var socket = new SockJS('/tetris');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/drop-piece', function (greeting) {
            
        });
    });
    $('#tetris-theme').trigger("play");
}

function drawHoldBox(canvas, unitSize) {
    var ctx = canvas.getContext("2d");
    ctx.font = "{}px Arial".replace("{}", unitSize / 2);
    alert("Font: {}".replace("{}", ctx.font));
    ctx.strokeText("HOLD", 0, 0);
    ctx.strokeRect(0, 2 * unitSize, 4 * unitSize, 4 * unitSize);
}

function initCanvas() {
    // The playing field is 24 units by 30 units.

    // From top to bottom: 8 units (Hold box), 1 unit (padding), 
    // 20 units (board), 1 unit (padding)

    // From left to right: 6 units (Hold box), 1 unit (padding), 
    // 10 units (board), 1 unit (padding), 6 units (next-pieces 
    // queue)

    canvas = document.getElementById("tetris-display");
    squareWidth = canvas.clientHeight / 30;

    alert(squareWidth);

    drawHoldBox(canvas, squareWidth);
}


$(function() {
    $( document ).on("load", initCanvas);
    $( document ).on("load", connect)
});