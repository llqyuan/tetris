var stompClient = null;
var unitSize = null;


// connect() connects to the server.

function connect() {
    var socket = new SockJS('/tetris');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/drop-piece', function (greeting) {
            
        });
    });
    $('#tetris-theme').trigger("play");
}


// drawBlankHoldBox() draws the hold box.

function drawBlankHoldBox(canvas, unitSize) {
    var ctx = canvas.getContext("2d");
    ctx.font = "{}px Arial".replace("{}", Math.floor(unitSize / 2));
    ctx.strokeText("HOLD", 0, Math.floor(unitSize * 3 / 2));
    ctx.strokeRect(0, 2 * unitSize, 6 * unitSize, 6 * unitSize);
}


// drawBlankBoard() draws the board with no Tetris pieces in it.

function drawBlankBoard(canvas, unitSize) {
    var ctx = canvas.getContext("2d");
    ctx.strokeRect(6 * unitSize, 9 * unitSize, 10 * unitSize, 20 * unitSize);
}


// drawBlankNextQueue draws the next-pieces queue with no Tetris 
// pieces in it.

function drawBlankNextQueue(canvas, unitSize) {
    var ctx = canvas.getContext("2d");

    ctx.font = "{}px Arial".replace("{}", Math.floor(unitSize / 2));
    ctx.strokeText("NEXT", 11 * unitSize, Math.floor(unitSize * 3 / 2));

    ctx.moveTo(11 * unitSize, 2 * unitSize);
    ctx.lineTo(23 * unitSize, 2 * unitSize);
    ctx.stroke();

    ctx.moveTo(11 * unitSize, 2 * unitSize);
    ctx.lineTo(11 * unitSize, 8 * unitSize);
    ctx.stroke();

    ctx.moveTo(11 * unitSize, 8 * unitSize);
    ctx.lineTo(17 * unitSize, 8 * unitSize);
    ctx.stroke();

    ctx.moveTo(17 * unitSize, 8 * unitSize);
    ctx.lineTo(17 * unitSize, 23 * unitSize);
    ctx.stroke();

    ctx.moveTo(17 * unitSize, 23 * unitSize);
    ctx.lineTo(23 * unitSize, 23 * unitSize);
    ctx.stroke();

    ctx.moveTo(23 * unitSize, 23 * unitSize);
    ctx.lineTo(23 * unitSize, 2 * unitSize);
    ctx.stroke();
}

// initCanvas draws the barebones Tetris board: hold box, board, and 
// next-queue with no Tetris pieces.

function initCanvas() {
    // The playing field is 24 units by 30 units.

    // From top to bottom: 8 units (Hold box), 1 unit (padding), 
    // 20 units (board), 1 unit (padding)

    // From left to right: 6 units (Hold box), 1 unit (padding), 
    // 10 units (board), 1 unit (padding), 6 units (next-pieces 
    // queue)

    canvas = document.getElementById("tetris-display");
    unitSize = Math.floor(canvas.clientHeight / 30);
    canvas.width = canvas.clientWidth;
    canvas.height = canvas.clientHeight;

    drawBlankHoldBox(canvas, unitSize);
    drawBlankBoard(canvas, unitSize);
    drawBlankNextQueue(canvas, unitSize);
}

$(function() {
    $( "#tetris-display" ).ready(function() { initCanvas(); });
    $( "#tetris-display" ).ready(function() { connect(); });
});