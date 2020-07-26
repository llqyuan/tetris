var stompClient = null;
var unitSize = null;

const I = 0;
const L = 1;
const J = 2;
const T = 3;
const S = 4;
const Z = 5;
const O = 6;

const UPRIGHT = 0;
const LEFT = 1;
const RIGHT = 2;
const UPSIDEDOWN = 3;

// ========================================================================
// Drawing pieces

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

const GHOSTCOLOUR = "#91919177";


// classes for tetris pieces/singleton squares?

function SingleSquare(params) {
    this.x = params.x || 0;
    this.y = params.y || 0;
    this.piece = params.piece || -1;
}

function IPiece(params) {
    this.x = params.x || 0;
    this.y = params.y || 0;
    this.fillStyle = ICOLOUR;
    this.strokeStyle = IOUTLINE;
    this.orientation = params.orientation || UPRIGHT;
    this.ghost = params.ghost || false;
}

function LPiece(params) {
    this.x = params.x || 0;
    this.y = params.y || 0;
    this.fillStyle = LCOLOUR;
    this.strokeStyle = LOUTLINE;
    this.orientation = params.orientation || UPRIGHT;
    this.ghost = params.ghost || false;
}

function JPiece(params) {
    this.x = params.x || 0;
    this.y = params.y || 0;
    this.fillStyle = JCOLOUR;
    this.strokeStyle = JOUTLINE;
    this.orientation = params.orientation || UPRIGHT;
    this.ghost = params.ghost || false;
}

function TPiece(params) {
    this.x = params.x || 0;
    this.y = params.y || 0;
    this.fillStyle = TCOLOUR;
    this.strokeStyle = TOUTLINE;
    this.orientation = params.orientation || UPRIGHT;
    this.ghost = params.ghost || false;
}

function SPiece(params) {
    this.x = params.x || 0;
    this.y = params.y || 0;
    this.fillStyle = SCOLOUR;
    this.strokeStyle = SOUTLINE;
    this.orientation = params.orientation || UPRIGHT;
    this.ghost = params.ghost || false;
}

function ZPiece(params) {
    this.x = params.x || 0;
    this.y = params.y || 0;
    this.fillStyle = ZCOLOUR;
    this.strokeStyle = ZOUTLINE;
    this.orientation = params.orientation || UPRIGHT;
    this.ghost = params.ghost || false;
}

function OPiece(params) {
    this.x = params.x || 0;
    this.y = params.y || 0;
    this.fillStyle = OCOLOUR;
    this.strokeStyle = OOUTLINE;
    this.orientation = params.orientation || UPRIGHT;
    this.ghost = params.ghost || false;
}

SingleSquare.prototype.draw = function(canvas) {
    var ctx = canvas.getContext("2d");

    switch (this.piece) {
        case I:
            ctx.fillStyle = ICOLOUR;
            ctx.strokeStyle = IOUTLINE;
            break;
        case L:
            ctx.fillStyle = LCOLOUR;
            ctx.strokeStyle = LOUTLINE;
            break;
        case J:
            ctx.fillStyle = JCOLOUR;
            ctx.strokeStyle = JOUTLINE;
            break;
        case T:
            ctx.fillStyle = TCOLOUR;
            ctx.strokeStyle = TOUTLINE;
            break;
        case S:
            ctx.fillStyle = SCOLOUR;
            ctx.strokeStyle = SOUTLINE;
            break;
        case Z:
            ctx.fillStyle = ZCOLOUR;
            ctx.strokeStyle = ZOUTLINE;
            break;
        case O:
            ctx.fillStyle = OCOLOUR;
            ctx.strokeStyle = OOUTLINE;
            break;
        default:
            ctx.fillStyle = "#efefef";
            ctx.strokeStyle = "#efefef";
    }
    ctx.fillRect(this.x, this.y, unitSize, unitSize);
    ctx.strokeRect(this.x, this.y, unitSize, unitSize);
}

IPiece.prototype.draw = function(canvas) {
    var ctx = canvas.getContext("2d");
    if (this.ghost) {
        ctx.fillStyle = GHOSTCOLOUR;
        ctx.strokeStyle = GHOSTCOLOUR;
    } else {
        ctx.fillStyle = this.fillStyle;
        ctx.strokeStyle = this.strokeStyle;
    }
    switch (this.orientation) {
        case UPRIGHT:
            // what's the spawning position of an i piece?
            // todo: I'll just assume first and check later, and if I'm wrong about 
            // spawning positions, I'll just move the cases around
            ctx.fillRect(this.x, this.y + unitSize, unitSize, unitSize);
            ctx.fillRect(this.x + unitSize, this.y + unitSize, 
                unitSize, unitSize);
            ctx.fillRect(this.x + 2 * unitSize, this.y + unitSize,
                unitSize, unitSize);
            ctx.fillRect(this.x + 3 * unitSize, this.y + unitSize,
                unitSize, unitSize);
            ctx.strokeRect(this.x, this.y + unitSize, unitSize, unitSize);
            ctx.strokeRect(this.x + unitSize, this.y + unitSize, 
                unitSize, unitSize);
            ctx.strokeRect(this.x + 2 * unitSize, this.y + unitSize,
                unitSize, unitSize);
            ctx.strokeRect(this.x + 3 * unitSize, this.y + unitSize,
                unitSize, unitSize);
            break;
        case RIGHT:
            ctx.fillRect(this.x + 2 * unitSize, this.y, 
                unitSize, unitSize);
            ctx.fillRect(this.x + 2 * unitSize, this.y + unitSize,
                unitSize, unitSize);
            ctx.fillRect(this.x + 2 * unitSize, this.y + 2 * unitSize,
                unitSize, unitSize);
            ctx.fillRect(this.x + 2 * unitSize, this.y + 3 * unitSize,
                unitSize, unitSize);
            ctx.strokeRect(this.x + 2 * unitSize, this.y, 
                unitSize, unitSize);
            ctx.strokeRect(this.x + 2 * unitSize, this.y + unitSize,
                unitSize, unitSize);
            ctx.strokeRect(this.x + 2 * unitSize, this.y + 2 * unitSize,
                unitSize, unitSize);
            ctx.strokeRect(this.x + 2 * unitSize, this.y + 3 * unitSize,
                unitSize, unitSize);
            break;
        case LEFT:
            ctx.fillRect(this.x + unitSize, this.y, 
                unitSize, unitSize);
            ctx.fillRect(this.x + unitSize, this.y + unitSize,
                unitSize, unitSize);
            ctx.fillRect(this.x + unitSize, this.y + 2 * unitSize,
                unitSize, unitSize);
            ctx.fillRect(this.x + unitSize, this.y + 3 * unitSize,
                unitSize, unitSize);
            ctx.strokeRect(this.x + unitSize, this.y, 
                unitSize, unitSize);
            ctx.strokeRect(this.x + unitSize, this.y + unitSize,
                unitSize, unitSize);
            ctx.strokeRect(this.x + unitSize, this.y + 2 * unitSize,
                unitSize, unitSize);
            ctx.strokeRect(this.x + unitSize, this.y + 3 * unitSize,
                unitSize, unitSize);
            break;
        case UPSIDEDOWN:
            ctx.fillRect(this.x, this.y + 2 * unitSize, unitSize, unitSize);
            ctx.fillRect(this.x + unitSize, this.y + 2 * unitSize, 
                unitSize, unitSize);
            ctx.fillRect(this.x + 2 * unitSize, this.y + 2 * unitSize,
                unitSize, unitSize);
            ctx.fillRect(this.x + 3 * unitSize, this.y + 2 * unitSize,
                unitSize, unitSize);
            ctx.strokeRect(this.x, this.y + 2 * unitSize, unitSize, unitSize);
            ctx.strokeRect(this.x + unitSize, this.y + 2 * unitSize, 
                unitSize, unitSize);
            ctx.strokeRect(this.x + 2 * unitSize, this.y + 2 *unitSize,
                unitSize, unitSize);
            ctx.strokeRect(this.x + 3 * unitSize, this.y + 2 * unitSize,
                unitSize, unitSize);
            break;
    }
}

// todo
LPiece.prototype.draw = function(canvas) {
    var ctx = canvas.getContext("2d");

    if (this.ghost) {
        ctx.fillStyle = GHOSTCOLOUR;
        ctx.strokeStyle = GHOSTCOLOUR;
    } else {
        ctx.fillStyle = this.fillStyle;
        ctx.strokeStyle = this.strokeStyle;
    }
    switch(this.orientation) {
        case UPRIGHT:
            break;
        case LEFT:
            break;
        case RIGHT:
            break;
        case UPSIDEDOWN:
            break;
    }
}

// todo
JPiece.prototype.draw = function(canvas) {
    var ctx = canvas.getContext("2d");

    if (this.ghost) {
        ctx.fillStyle = GHOSTCOLOUR;
        ctx.strokeStyle = GHOSTCOLOUR;
    } else {
        ctx.fillStyle = this.fillStyle;
        ctx.strokeStyle = this.strokeStyle;
    }
    switch(this.orientation) {
        case UPRIGHT:
            break;
        case LEFT:
            break;
        case RIGHT:
            break;
        case UPSIDEDOWN:
            break;
    }
}

// todo
TPiece.prototype.draw = function(canvas) {
    var ctx = canvas.getContext("2d");

    if (this.ghost) {
        ctx.fillStyle = GHOSTCOLOUR;
        ctx.strokeStyle = GHOSTCOLOUR;
    } else {
        ctx.fillStyle = this.fillStyle;
        ctx.strokeStyle = this.strokeStyle;
    }
    switch(this.orientation) {
        case UPRIGHT:
            break;
        case LEFT:
            break;
        case RIGHT:
            break;
        case UPSIDEDOWN:
            break;
    }
}

// todo
SPiece.prototype.draw = function(canvas) {
    var ctx = canvas.getContext("2d");

    if (this.ghost) {
        ctx.fillStyle = GHOSTCOLOUR;
        ctx.strokeStyle = GHOSTCOLOUR;
    } else {
        ctx.fillStyle = this.fillStyle;
        ctx.strokeStyle = this.strokeStyle;
    }
    switch(this.orientation) {
        case UPRIGHT:
            break;
        case LEFT:
            break;
        case RIGHT:
            break;
        case UPSIDEDOWN:
            break;
    }
}

// todo
ZPiece.prototype.draw = function(canvas) {
    var ctx = canvas.getContext("2d");

    if (this.ghost) {
        ctx.fillStyle = GHOSTCOLOUR;
        ctx.strokeStyle = GHOSTCOLOUR;
    } else {
        ctx.fillStyle = this.fillStyle;
        ctx.strokeStyle = this.strokeStyle;
    }
    switch(this.orientation) {
        case UPRIGHT:
            break;
        case LEFT:
            break;
        case RIGHT:
            break;
        case UPSIDEDOWN:
            break;
    }
}

OPiece.prototype.draw = function(canvas) {
    var ctx = canvas.getContext("2d");

    if (this.ghost) {
        ctx.fillStyle = GHOSTCOLOUR;
        ctx.strokeStyle = GHOSTCOLOUR;
    } else {
        ctx.fillStyle = this.fillStyle;
        ctx.strokeStyle = this.strokeStyle;
    }
    ctx.fillRect(this.x + unitSize, this.y + unitSize, 
        unitSize, unitSize);
    ctx.fillRect(this.x + 2 * unitSize, this.y + unitSize,
        unitSize, unitSize);
    ctx.fillRect(this.x + unitSize, this.y + 2 * unitSize,
        unitSize, unitSize);
    ctx.fillRect(this.x + 2 * unitSize, this.y + 2 * unitSize,
        unitSize, unitSize);
    ctx.strokeRect(this.x + unitSize, this.y + unitSize, 
        unitSize, unitSize);
    ctx.strokeRect(this.x + 2 * unitSize, this.y + unitSize,
        unitSize, unitSize);
    ctx.strokeRect(this.x + unitSize, this.y + 2 * unitSize,
        unitSize, unitSize);
    ctx.strokeRect(this.x + 2 * unitSize, this.y + 2 * unitSize,
        unitSize, unitSize);
}

// ========================================================================
// Drawing things on the board

// initHoldBox() draws the hold box.

function initHoldBox(canvas, unitSize) {
    var ctx = canvas.getContext("2d");
    ctx.font = "{}px Arial".replace("{}", Math.floor(unitSize / 2));
    ctx.strokeText("HOLD", 0, Math.floor(unitSize * 3 / 2));
    ctx.strokeRect(0, 2 * unitSize, 6 * unitSize, 6 * unitSize);
}


// initBoard() draws the board with no Tetris pieces in it.

function initBoard(canvas, unitSize) {
    var ctx = canvas.getContext("2d");
    ctx.strokeRect(6 * unitSize - 2, 9 * unitSize - 2, 
        10 * unitSize + 4, 20 * unitSize + 4);
}


// initNextQueue() draws the next-pieces queue with no Tetris 
// pieces in it.

function initNextQueue(canvas, unitSize) {
    var ctx = canvas.getContext("2d");

    ctx.font = "{}px Arial".replace("{}", Math.floor(unitSize / 2));
    ctx.strokeText("NEXT", 12 * unitSize, Math.floor(unitSize * 3 / 2));

    ctx.moveTo(12 * unitSize, 2 * unitSize);
    ctx.lineTo(23 * unitSize, 2 * unitSize);
    ctx.stroke();

    ctx.moveTo(12 * unitSize, 2 * unitSize);
    ctx.lineTo(12 * unitSize, 8 * unitSize);
    ctx.stroke();

    ctx.moveTo(12 * unitSize, 8 * unitSize);
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


// drawPieceInQueuePosition(canvas, piececode, pos) draws the piece 
// represented by piececode (constant I, J, L, T, S, Z, O)
// in the position pos (int: 1, 2, 3, 4, 5) in the next-piece queue.

function drawPieceInQueuePosition(canvas, piececode, pos) {
    var xpix;
    var ypix;
    var piece;
    switch (pos) {
        case 1:
            xpix = 13 * unitSize;
            ypix = 3 * unitSize;
            break;
        case 2:
            xpix = 18 * unitSize;
            ypix = 3 * unitSize;
            break;
        case 3:
            xpix = 18 * unitSize;
            ypix = 8 * unitSize;
            break;
        case 4:
            xpix = 18 * unitSize;
            ypix = 13 * unitSize;
            break;
        case 5:
            xpix = 18 * unitSize;
            ypix = 18 * unitSize;
            break;
    }
    switch(piececode) {
        case I:
            piece = new IPiece({x: xpix, y: ypix});
            break;
        case J:
            piece = new JPiece({x: xpix, y:ypix});
            break;
        case L:
            piece = new LPiece({x: xpix, y: ypix});
            break;
        case T:
            piece = new TPiece({x: xpix, y:ypix});
            break;
        case S:
            piece = new SPiece({x: xpix, y: ypix});
            break;
        case Z:
            piece = new ZPiece({x: xpix, y: ypix});
            break;
        case O:
            piece = new OPiece({x: xpix, y:ypix});
            break;
    }
    piece.draw(canvas);
}


// clearPieceInQueuePosition(canvas, pos) erases the piece in 
// position pos (int: 1, 2, 3, 4, 5)

function clearPieceInQueuePosition(canvas, pos) {
    var ctx = canvas.getContext("2d");
    var xpix;
    var ypix;
    switch (pos) {
        case 1:
            xpix = 13 * unitSize;
            ypix = 3 * unitSize;
            break;
        case 2:
            xpix = 18 * unitSize;
            ypix = 3 * unitSize;
            break;
        case 3:
            xpix = 18 * unitSize;
            ypix = 8 * unitSize;
            break;
        case 4:
            xpix = 18 * unitSize;
            ypix = 13 * unitSize;
            break;
        case 5:
            xpix = 18 * unitSize;
            ypix = 18 * unitSize;
            break;
    }
    ctx.fillStyle = "#efefef";
    ctx.fillRect(xpix - 2, ypix - 2, 4 * unitSize + 4, 4 * unitSize + 4);
}


// drawPieceInHold(canvas, piececode) draws the corresponding 
// piece in the hold box.

function drawPieceInHold(canvas, piececode) {
    var piece;
    switch (piececode) {
        case I:
            piece = new IPiece({x: unitSize, y: 3 * unitSize});
            break;
        case L:
            piece = new LPiece({x: unitSize, y: 3 * unitSize});
            break;
        case J:
            piece = new JPiece({x: unitSize, y: 3 * unitSize});
            break;
        case T:
            piece = new TPiece({x: unitSize, y: 3 * unitSize});
            break;
        case S:
            piece = new SPiece({x: unitSize, y: 3 * unitSize});
            break;
        case Z:
            piece = new ZPiece({x: unitSize, y: 3 * unitSize});
            break;
        case O:
            piece = new OPiece({x: unitSize, y: 3 * unitSize});
            break;
    }
    piece.draw(canvas);
}


// clearPieceInHold(canvas) erases the piece in the hold box.

function clearPieceInHold(canvas) {
    var ctx = canvas.getContext("2d");
    ctx.fillStyle = "#efefef";
    ctx.fillRect(unitSize - 2, 3 * unitSize - 2, 
        4 * unitSize + 4, 4 * unitSize + 4);
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

    initHoldBox(canvas, unitSize);
    initBoard(canvas, unitSize);
    initNextQueue(canvas, unitSize);

    // todo: remove. testing, temporary
    // i piece drawing tested
    // o piece drawing tested
    drawPieceInQueuePosition(canvas, I, 1);
    drawPieceInQueuePosition(canvas, I, 2);
    drawPieceInQueuePosition(canvas, O, 3);
    drawPieceInQueuePosition(canvas, I, 4);
    drawPieceInQueuePosition(canvas, O, 5);
    clearPieceInQueuePosition(canvas, 1);
    drawPieceInHold(canvas, I);
    clearPieceInHold(canvas);
    drawPieceInHold(canvas, O);
}

// ========================================================================
// Other

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

$(function() {
    $( "#tetris-display" ).ready(function() { initCanvas(); });
    $( "#tetris-theme" ).ready(function() { connect(); });
});
