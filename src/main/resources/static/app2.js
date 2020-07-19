var stompClient = null;
var unitSize = null;

const I = 0;
const L = 1;
const J = 2;
const T = 3;
const S = 4;
const Z = 5;
const O = 6;
const GHOST = 7;

const UPRIGHT = 0;
const LEFT = 1;
const RIGHT = 2;
const UPSIDEDOWN = 3;

// ========================================================================
// temporary
const ICOLOUR = "#00eeff";
const IOUTLINE = "#038992";

const LCOLOUR = "#ff7300";
const LOUTLINE = "#bd6706";

const JCOLOUR = "#001aff";
const JOUTLINE = "#0604a5";

const TCOLOUR = "#ff00dd";
const TOUTLINE = "#8b0475";

const SCOLOUR = "#00ff55";
const SOUTLINE = "#149103";

const ZCOLOUR = "#ff0000";
const ZOUTLINE = "#aa0000";

const OCOLOUR = "#ffee00";
const OOUTLINE = "#afa402";

const GHOSTCOLOUR = "#919191";


// classes for tetris pieces/singleton squares?

function SingleSquare(params) {
    this.x = params.x || 0;
    this.y = params.y || 0;
    this.piece = params.piece || null;
}

function IPiece(params) {
    this.x = params.x || 0;
    this.y = params.y || 0;
    this.fillStyle = ICOLOUR;
    this.strokeStyle = IOUTLINE;
    this.orientation = params.orientation || UPRIGHT;
}

function LPiece(params) {
    this.x = params.x || 0;
    this.y = params.y || 0;
    this.fillStyle = LCOLOUR;
    this.strokeStyle = LOUTLINE;
    this.orientation = params.orientation || UPRIGHT;
}

function JPiece(params) {
    this.x = params.x || 0;
    this.y = params.y || 0;
    this.fillStyle = JCOLOUR;
    this.strokeStyle = JOUTLINE;
    this.orientation = params.orientation || UPRIGHT;
}

function TPiece(params) {
    this.x = params.x || 0;
    this.y = params.y || 0;
    this.fillStyle = TCOLOUR;
    this.strokeStyle = TOUTLINE;
    this.orientation = params.orientation || UPRIGHT;
}

function SPiece(params) {
    this.x = params.x || 0;
    this.y = params.y || 0;
    this.fillStyle = SCOLOUR;
    this.strokeStyle = SOUTLINE;
    this.orientation = params.orientation || UPRIGHT;
}

function ZPiece(params) {
    this.x = params.x || 0;
    this.y = params.y || 0;
    this.fillStyle = ZCOLOUR;
    this.strokeStyle = ZOUTLINE;
    this.orientation = params.orientation || UPRIGHT;
}

function OPiece(params) {
    this.x = params.x || 0;
    this.y = params.y || 0;
    this.fillStyle = OCOLOUR;
    this.strokeStyle = OOUTLINE;
    this.orientaiton = params.orientation || UPRIGHT;
}

SingleSquare.prototype.draw = function(canvas) {
    var ctx = canvas.getContext("2d");

    switch (this.piece) {
        case I:
            ctx.fillStyle = ICOLOUR;
            ctx.strokeStyle = IOUTLINE;
            ctx.fillRect(this.x, this.y, unitSize, unitSize);
            ctx.strokeRect(this.x, this.y, unitSize, unitSize);
            break;
        case L:
            ctx.fillStyle = LCOLOUR;
            ctx.strokeStyle = LOUTLINE;
            ctx.fillRect(this.x, this.y, unitSize, unitSize);
            ctx.strokeRect(this.x, this.y, unitSize, unitSize);
            break;
        default:
            ctx.fillStyle = "#ffffff";
            ctx.strokeStyle = "#fffffff";
            ctx.fillRect(this.x, this.y, unitSize, unitSize);
            ctx.strokeRect(this.x, this.y, unitSize, unitSize);
    }
}

// ========================================================================


// connect() connects to the server.

function connect() {
    var socket = new SockJS('/tetris');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/drop-piece', function (response) {
            
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