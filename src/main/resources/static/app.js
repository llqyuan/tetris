let tetris = {};

const I = 1;
const L = 2;
const J = 3;
const T = 4;
const S = 5;
const Z = 6;
const O = 7;

const UPRIGHT = 1;
const LEFT = 2;
const RIGHT = 3;
const UPSIDEDOWN = 4;

/* 
=========================================================================

Sections:

1. Drawing pieces
2. Drawing things on the board
3. Page management:
  a. General
  b. Updating the drawing of the board
  c. Sending commands to the server

========================================================================= 
*/

// ========================================================================
// Drawing pieces
// ========================================================================

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
const BACKGROUND = "#efefef";


// Piece classes

/** Class for single square on the board. */
class SingleSquare {
    /**
     * 
     * @param {int} x x-coordinate
     * @param {int} y y-coordinate
     * @param {int} piece one of the piece constants
     * @param {bool} ghost true if a ghost, false otherwise
     * @param {bool} unusableHoldPiece true if unusable and in hold box, false otherwise
     * @param {bool} erase true if this square should be erased, false otherwise
     */
    constructor(x, y, piece, ghost=false, unusableHoldPiece=false, erase=false) {
        this.x = x;
        this.y = y;
        this.piece = piece;
        this.ghost = ghost;
        this.unusableHoldPiece = unusableHoldPiece; 
        this.erase = erase;
    }

    /** 
    @param canvas {canvas DOM object} 
    @return {nothing}
    */
    draw(canvas) {
        let ctx = canvas.getContext("2d");
    
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
                ctx.fillStyle = BACKGROUND;
                ctx.strokeStyle = BACKGROUND;
        }
        if (this.ghost || this.unusableHoldPiece) {
            ctx.fillStyle = GHOSTCOLOUR;
            ctx.strokeStyle = GHOSTCOLOUR;
        } if (this.erase) {
            ctx.fillStyle = BACKGROUND;
            ctx.strokeStyle = BACKGROUND;
        }
    
        // 10x2 spawn field
        if (this.y >= 2 * tetris.unitSize  && !this.erase)
        {
            ctx.lineWidth = 2;
            ctx.fillRect(this.x + 1, this.y + 1, tetris.unitSize - 2, tetris.unitSize - 2);
            ctx.strokeRect(this.x + 1, this.y + 1, tetris.unitSize - 2, tetris.unitSize - 2);
        } else if (this.y >= 2 * tetris.unitSize) {
            ctx.fillRect(this.x, this.y, tetris.unitSize, tetris.unitSize);
        }
    }
}

/** @abstract */
class Piece {
    /**
     * @param {int} x x-coordinate of top left corner, in pixels
     * @param {int} y y-coordinate of top left corner, in pixels
     * @param {array} sq1 coordinates of first square, relative to top left
     * @param {array} sq2 relative coordinates of second square
     * @param {array} sq3 relative coordinates of third square
     * @param {array} sq4 relative coordinates of fourth square
     * @param {int} piece one of the piece constants
     * @param {bool} ghost true if a ghost piece, false otherwise
     * @param {bool} unusableHoldPiece true if a sealed hold piece, false otherwise
     */
    constructor(x, y, sq1, sq2, sq3, sq4, piece, ghost, unusableHoldPiece) {
        this.sq1 = new SingleSquare(
            x + tetris.unitSize * sq1[0], 
            y + tetris.unitSize * sq1[1], 
            piece, ghost, unusableHoldPiece);
        this.sq2 = new SingleSquare(
            x + tetris.unitSize * sq2[0], 
            y + tetris.unitSize * sq2[1], 
            piece, ghost, unusableHoldPiece);
        this.sq3 = new SingleSquare(
            x + tetris.unitSize * sq3[0], 
            y + tetris.unitSize * sq3[1], 
            piece, ghost, unusableHoldPiece);
        this.sq4 = new SingleSquare(
            x + tetris.unitSize * sq4[0], 
            y + tetris.unitSize * sq4[1], 
            piece, ghost, unusableHoldPiece);
            
    }

    /**
     * Draws the piece.
     * @param canvas canvas DOM element 
     */
    draw(canvas){
        this.sq1.draw(canvas);
        this.sq2.draw(canvas);
        this.sq3.draw(canvas);
        this.sq4.draw(canvas);
    }
}

/** Class for I piece. */
class IPiece extends Piece {
    /**
     * @param {int} x x-coordinate of top-left corner, in pixels. (pos x right, pos y down)
     * @param {int} y y-coordinate of top-left corner, in pixels. (pos x right, pos y down)
     * @param {int} orientation one of the orientation constants
     * @param {bool} ghost true if a ghost, false otherwise
     * @param {bool} unusableHoldPiece true if a sealed hold piece, false otherwise
     */
    constructor(x, y, orientation=UPRIGHT, ghost=false, unusableHoldPiece=false) {
        let sq1;
        let sq2;
        let sq3;
        let sq4;
        switch (orientation) {
            case UPRIGHT:
                sq1 = [0, 1];
                sq2 = [1, 1];
                sq3 = [2, 1];
                sq4 = [3,1];
                break;
            case RIGHT:
                sq1 = [2, 0];
                sq2 = [2, 1];
                sq3 = [2, 2];
                sq4 = [2, 3];
                break;
            case LEFT:
                sq1 = [1, 0];
                sq2 = [1, 1];
                sq3 = [1, 2];
                sq4 = [1, 3];
                break;
            default:
                sq1 = [0, 2];
                sq2 = [1, 2];
                sq3 = [2, 2];
                sq4 = [3, 2];
                break;
        }
        super(x, y, sq1, sq2, sq3, sq4, I, ghost, unusableHoldPiece);
    }
}

class LPiece extends Piece {
    /**
     * @param {int} x x-coordinate of top-left corner, in pixels. (pos x right, pos y down)
     * @param {int} y y-coordinate of top-left corner, in pixels. (pos x right, pos y down)
     * @param {int} orientation one of the orientation constants
     * @param {bool} ghost true if a ghost, false otherwise
     * @param {bool} unusableHoldPiece true if a sealed hold piece, false otherwise
     */
    constructor(x, y, orientation=UPRIGHT, ghost=false, unusableHoldPiece=false) {
        let sq1;
        let sq2;
        let sq3;
        let sq4;
        switch (orientation) {
            case UPRIGHT:
                sq1 = [2, 0];
                sq2 = [0, 1];
                sq3 = [1, 1];
                sq4 = [2, 1];
                break;
            case RIGHT:
                sq1 = [1, 0];
                sq2 = [1, 1];
                sq3 = [1, 2];
                sq4 = [2, 2];
                break;
            case LEFT:
                sq1 = [0, 0];
                sq2 = [1, 0];
                sq3 = [1, 1];
                sq4 = [1, 2];
                break;
            default:
                sq1 = [0, 1];
                sq2 = [1, 1];
                sq3 = [2, 1];
                sq4 = [0, 2];
                break;
        }
        super(x, y, sq1, sq2, sq3, sq4, L, ghost, unusableHoldPiece);
    }
}

class JPiece extends Piece{
    /**
     * @param {int} x x-coordinate of top-left corner, in pixels. (pos x right, pos y down)
     * @param {int} y y-coordinate of top-left corner, in pixels. (pos x right, pos y down)
     * @param {int} orientation one of the orientation constants
     * @param {bool} ghost true if a ghost, false otherwise
     * @param {bool} unusableHoldPiece true if a sealed hold piece, false otherwise
     */
    constructor(x, y, orientation=UPRIGHT, ghost=false, unusableHoldPiece=false) {
        let sq1;
        let sq2;
        let sq3;
        let sq4;
        switch (orientation) {
            case UPRIGHT:
                sq1 = [0, 0];
                sq2 = [0, 1];
                sq3 = [1, 1];
                sq4 = [2, 1];
                break;
            case LEFT:
                sq1 = [1, 0];
                sq2 = [1, 1];
                sq3 = [0, 2];
                sq4 = [1, 2];
                break;
            case RIGHT:
                sq1 = [1, 0];
                sq2 = [2, 0];
                sq3 = [1, 1];
                sq4 = [1, 2];
                break;
            default:
                sq1 = [0, 1];
                sq2 = [1, 1];
                sq3 = [2, 1];
                sq4 = [2, 2];
                break;
        }
        super(x, y, sq1, sq2, sq3, sq4, J, ghost, unusableHoldPiece);
    }
}

class TPiece extends Piece{
    /**
     * @param {int} x x-coordinate of top-left corner, in pixels. (pos x right, pos y down)
     * @param {int} y y-coordinate of top-left corner, in pixels. (pos x right, pos y down)
     * @param {int} orientation one of the orientation constants
     * @param {bool} ghost true if a ghost, false otherwise
     * @param {bool} unusableHoldPiece true if a sealed hold piece, false otherwise
     */
    constructor(x, y, orientation=UPRIGHT, ghost=false, unusableHoldPiece=false) {
        let sq1;
        let sq2;
        let sq3;
        let sq4;
        switch (orientation) {
            case UPRIGHT:
                sq1 = [1, 0];
                sq2 = [0, 1];
                sq3 = [1, 1];
                sq4 = [2, 1];
                break;
            case LEFT:
                sq1 = [1, 0];
                sq2 = [0, 1];
                sq3 = [1, 1];
                sq4 = [1, 2];
                break;
            case RIGHT:
                sq1 = [1, 0];
                sq2 = [1, 1];
                sq3 = [2, 1];
                sq4 = [1, 2];
                break;
            default:
                sq1 = [0, 1];
                sq2 = [1, 1];
                sq3 = [2, 1];
                sq4 = [1, 2];
                break;
        }
        super(x, y, sq1, sq2, sq3, sq4, T, ghost, unusableHoldPiece);
    }
}

class SPiece extends Piece{
    /**
     * @param {int} x x-coordinate of top-left corner, in pixels. (pos x right, pos y down)
     * @param {int} y y-coordinate of top-left corner, in pixels. (pos x right, pos y down)
     * @param {int} orientation one of the orientation constants
     * @param {bool} ghost true if a ghost, false otherwise
     * @param {bool} unusableHoldPiece true if a sealed hold piece, false otherwise
     */
    constructor(x, y, orientation=UPRIGHT, ghost=false, unusableHoldPiece=false) {
        let sq1;
        let sq2;
        let sq3;
        let sq4;
        switch (orientation) {
            case UPRIGHT:
                sq1 = [1, 0];
                sq2 = [2, 0];
                sq3 = [0, 1];
                sq4 = [1, 1];
                break;
            case LEFT:
                sq1 = [0, 0];
                sq2 = [0, 1];
                sq3 = [1, 1];
                sq4 = [1, 2];
                break;
            case RIGHT:
                sq1 = [1, 0];
                sq2 = [1, 1];
                sq3 = [2, 1];
                sq4 = [2, 2];
                break;
            default:
                sq1 = [1, 1];
                sq2 = [2, 1];
                sq3 = [0, 2];
                sq4 = [1, 2];
                break;
        }
        super(x, y, sq1, sq2, sq3, sq4, S, ghost, unusableHoldPiece);
    }
}

class ZPiece extends Piece {
    /**
     * @param {int} x x-coordinate of top-left corner, in pixels. (pos x right, pos y down)
     * @param {int} y y-coordinate of top-left corner, in pixels. (pos x right, pos y down)
     * @param {int} orientation one of the orientation constants
     * @param {bool} ghost true if a ghost, false otherwise
     * @param {bool} unusableHoldPiece true if a sealed hold piece, false otherwise
     */
    constructor(x, y, orientation=UPRIGHT, ghost=false, unusableHoldPiece=false) {
        let sq1;
        let sq2;
        let sq3;
        let sq4;
        switch (orientation) {
            case UPRIGHT:
                sq1 = [0, 0];
                sq2 = [1, 0];
                sq3 = [1, 1];
                sq4 = [2, 1];
                break;
            case LEFT:
                sq1 = [1, 0];
                sq2 = [1, 1];
                sq3 = [0, 1];
                sq4 = [0, 2];
                break;
            case RIGHT:
                sq1 = [2, 0];
                sq2 = [2, 1];
                sq3 = [1, 1];
                sq4 = [1, 2];
                break;
            default:
                sq1 = [1, 0];
                sq2 = [2, 0];
                sq3 = [1, 1];
                sq4 = [2, 1];
                break;
        }
        super(x, y, sq1, sq2, sq3, sq4, Z, ghost, unusableHoldPiece);
    }
}

class OPiece extends Piece {
    /**
     * @param {int} x x-coordinate of top-left corner, in pixels. (pos x right, pos y down)
     * @param {int} y y-coordinate of top-left corner, in pixels. (pos x right, pos y down)
     * @param {int} orientation one of the orientation constants
     * @param {bool} ghost true if a ghost, false otherwise
     * @param {bool} unusableHoldPiece true if a sealed hold piece, false otherwise
     */
    constructor(x, y, orientation=UPRIGHT, ghost=false, unusableHoldPiece=false) {
        super(x, y, [1, 0], [2, 0], [1, 1], [2, 1], O, ghost, unusableHoldPiece);
    }
}


// ========================================================================
// Drawing things on the board
// ========================================================================

/** Draws the hold box.
 * @param canvas canvas DOM element
 * @param unitSize {int} width of single square
 */
function initHoldBox(canvas, unitSize) {
    let ctx = canvas.getContext("2d");
    ctx.font = "{}px Arial".replace("{}", Math.floor(unitSize / 2));
    ctx.strokeText("HOLD", 0, Math.floor(unitSize * 3 / 2));
    ctx.strokeRect(0, 2 * unitSize, 6 * unitSize, 6 * unitSize);
}


/** Draws the board with no Tetris pieces in it.
 * @param canvas canvas DOM element
 * @param unitSize {int} width of single square
 */
function initBoard(canvas, unitSize) {
    let ctx = canvas.getContext("2d");
    ctx.strokeRect(7 * unitSize - 2, 2 * unitSize - 2, 
        10 * unitSize + 4, 20 * unitSize + 4);
}


/** Draws the next-pieces queue with no Tetris pieces in it.
 * @param canvas canvas DOM elemet
 * @param unitSize {int} width of single square
 */
function initNextQueue(canvas, unitSize) {
    let ctx = canvas.getContext("2d");

    ctx.font = "{}px Arial".replace("{}", Math.floor(unitSize / 2));
    ctx.strokeText("NEXT", 18 * unitSize, Math.floor(unitSize * 3 / 2));

    ctx.strokeRect(18 * unitSize, 2 * unitSize, 6 * unitSize, 17 * unitSize);
}


// drawPieceInQueuePosition(canvas, piececode, pos) draws the piece 
// represented by piececode (constant I, J, L, T, S, Z, O)
// in the position pos (int: 1, 2, 3, 4, 5) in the next-piece queue.

function drawPieceInQueuePosition(canvas, piececode, pos) {
    let xpix = 19 * tetris.unitSize;
    let ypix = 3 * tetris.unitSize + 3 * tetris.unitSize * (pos - 1);
    let piece;

    clearPieceInQueuePosition(canvas, pos);

    switch(piececode) {
        case I:
            piece = new IPiece(xpix, ypix);
            break;
        case J:
            piece = new JPiece(xpix, ypix);
            break;
        case L:
            piece = new LPiece(xpix, ypix);
            break;
        case T:
            piece = new TPiece(xpix, ypix);
            break;
        case S:
            piece = new SPiece(xpix, ypix);
            break;
        case Z:
            piece = new ZPiece(xpix, ypix);
            break;
        case O:
            piece = new OPiece(xpix, ypix);
            break;
    }
    piece.draw(canvas);
}


/** Erases the piece at position pos.
 * @param canvas canvas DOM element
 * @param pos {int} 1, 2, 3, 4, or 5
 */
function clearPieceInQueuePosition(canvas, pos) {
    let ctx = canvas.getContext("2d");
    let xpix = 19 * tetris.unitSize;
    let ypix = 3 * tetris.unitSize + 3 * tetris.unitSize * (pos - 1);

    ctx.fillStyle = BACKGROUND;
    ctx.fillRect(xpix - 2, ypix - 2, 4 * tetris.unitSize + 4, 3 * tetris.unitSize + 2);
}


// drawPieceInHold(canvas, piececode, cannotSpawnHeldPiece) 
// draws the corresponding piece in the hold box.

function drawPieceInHold(canvas, piececode, cannotSpawnHeldPiece) {
    let piece;
    clearPieceInHold();
    switch (piececode) {
        case I:
            piece = new IPiece(
                tetris.unitSize, 
                3 * tetris.unitSize, 
                UPRIGHT, false, cannotSpawnHeldPiece);
            break;
        case L:
            piece = new LPiece(
                tetris.unitSize, 
                3 * tetris.unitSize, 
                UPRIGHT, false, cannotSpawnHeldPiece);
            break;
        case J:
            piece = new JPiece(
                tetris.unitSize, 
                3 * tetris.unitSize, 
                UPRIGHT, false, cannotSpawnHeldPiece);
            break;
        case T:
            piece = new TPiece(
                tetris.unitSize, 
                3 * tetris.unitSize, 
                UPRIGHT, false, cannotSpawnHeldPiece);
            break;
        case S:
            piece = new SPiece(
                tetris.unitSize, 
                3 * tetris.unitSize, 
                UPRIGHT, false, cannotSpawnHeldPiece);
            break;
        case Z:
            piece = new ZPiece(
                tetris.unitSize, 
                3 * tetris.unitSize, 
                UPRIGHT, false, cannotSpawnHeldPiece);
            break;
        case O:
            piece = new OPiece(
                tetris.unitSize, 
                3 * tetris.unitSize, 
                UPRIGHT, false, cannotSpawnHeldPiece);
            break;
    }
    piece.draw(canvas);
}


/** Erases the piece in the hold box.
 */
function clearPieceInHold() {
    let canvas = document.getElementById("tetris-board");
    let ctx = canvas.getContext("2d");
    ctx.fillStyle = BACKGROUND;
    ctx.fillRect(tetris.unitSize - 2, 3 * tetris.unitSize - 2, 
        4 * tetris.unitSize + 4, 4 * tetris.unitSize + 4);
}


/** Clears the entire board.
 * @return {nothing}
 */
function clearBoard() {
    let canvas = document.getElementById("tetris-board");
    let ctx = canvas.getContext("2d");
    ctx.fillStyle = BACKGROUND;
    ctx.fillRect(
        7 * tetris.unitSize, 
        2 * tetris.unitSize, 
        10 * tetris.unitSize, 
        20 * tetris.unitSize);
}


/** Draws the barebones tetris board: hold box, board, and next-queue
 * with no Tetris pieces.
 * @return {nothing}
 */
function initCanvas() {
    // The playing field is 24 units wide and 23 units tall.

    // From top to bottom: 2 units (padding), 
    // 20 units (board), 1 unit (padding)

    // From left to right: 6 units (Hold box), 1 unit (padding),
    // 10 units (board), 1 unit (padding), 6 units (next-pieces 
    // queue)

    canvas = document.getElementById("tetris-board");
    tetris.unitSize = Math.floor(canvas.clientHeight / 26);
    canvas.width = canvas.clientWidth;
    canvas.height = canvas.clientHeight;

    initHoldBox(canvas, tetris.unitSize);
    initBoard(canvas, tetris.unitSize);
    initNextQueue(canvas, tetris.unitSize);

}

// ========================================================================
// Page management: General
// ========================================================================

/** Connects to the server.
 * @return Promise
 */
function connect() {
    let socket = new SockJS("/tetris");
    if (tetris.stompClient !== null) {
        tetris.stompClient.disconnect();
        console.log("Disconnected from existing connection.");
        tetris.stompClient = null;
    }
    tetris.stompClient = Stomp.over(socket);
    return new Promise(function(resolve) {
        tetris.stompClient.connect({}, function (frame) {
            console.log("Connected: " + frame);
            tetris.stompClient.subscribe(
                "/topic/board-update", 
                function (response) {
                    updateBoard(response);
                });
            resolve("Connected.");
        });
    })
}


/** 
Disconnects from the server.
@return {nothing}
*/
function disconnect() {
    if (tetris.stompClient !== null) {
        tetris.stompClient.disconnect();
        tetris.stompClient = null;
    }
}

/** 
Update the page after the game has started.
@param level {int} level that the player starts at
@return {nothing}
*/
async function start(level) {
    await connect();
    document.getElementById("start-overlay").style.display = "none";
    document.getElementById("game-over-overlay").style.display = "none";
    $('#tetris-theme').trigger("play");
    clearBoard();
    clearPieceInHold();
    sendGameStart(level);
    tetris.gameActive = true;
    updateFrame();
}

/** 
Update the page after the game has ended.
@param finalScore {int} score at the end of the game
@return {nothing}
*/
function end(finalScore) {
    document.getElementById("game-over-overlay").style.display = "block";
    $('#tetris-theme').trigger("pause");
    tetris.gameActive = false;
    clearTimeout(tetris.timer);
    $("#final-score").empty();
    $("#final-score").html("<p> Final score: " + String(finalScore) + "</p>");
    disconnect();
}


// =================================================================
// Page management: Updating the drawing of the board
// =================================================================

/** 
(Helper function) Updates the timer for timed drops and delayed locks.
@param body body of response from server
*/
function updateTimer(body) {
    if (body.timerUpdate.updateFallTimer) {
        if (tetris.timer != null) {
            clearTimeout(tetris.timer);
            tetris.timer = null;
        }
        tetris.timer = setTimeout(
            sendTimedFall, body.timerUpdate.requestNewUpdateIn);

    } else if (body.timerUpdate.updateLockTimer) {
        if (tetris.timer != null) {
            clearTimeout(tetris.timer);
            tetris.timer = null;
        }
        tetris.timer = setTimeout(
            sendTimedLock, body.timerUpdate.requestNewUpdateIn);
    }
}

/** 
(Helper function) Draw the new piece in the hold box.
body is the parsed body of the response from the server.
@param canvas canvas DOM element
@param body body of response from server
@return {nothing}
*/
function updateHoldPiece(canvas, body) {
    let hold = String(body.hold);
    let cannotSpawnHeldPiece = body.sealHoldPiece;
    if (body.spawnedPiece) {    
        switch(hold) {
            case "I":
                drawPieceInHold(canvas, I, cannotSpawnHeldPiece);
                break;
            case "J":
                drawPieceInHold(canvas, J, cannotSpawnHeldPiece);
                break;
            case "L":
                drawPieceInHold(canvas, L, cannotSpawnHeldPiece);
                break;
            case "O":
                drawPieceInHold(canvas, O, cannotSpawnHeldPiece);
                break;
            case "S":
                drawPieceInHold(canvas, S, cannotSpawnHeldPiece);
                break;
            case "T":
                drawPieceInHold(canvas, T, cannotSpawnHeldPiece);
                break;
            case "Z":
                drawPieceInHold(canvas, Z, cannotSpawnHeldPiece);
                break;
        }
    }
}

/** 
(Helper function) Draws the new pieces in the next queue.
body is the parsed body of the response from the server.
@param canvas canvas DOM element
@param body body of response from server
@return {nothing}
*/
function updateNextQueue(canvas, body) {
    let next = body.nextFivePieces;
    if (body.spawnedPiece) {
        for (let i = 0; i < next.length; i++) {
            switch(String(next[i])) {
                case "I":
                    drawPieceInQueuePosition(canvas, I, i + 1);
                    break;
                case "J":
                    drawPieceInQueuePosition(canvas, J, i + 1);
                    break;
                case "L":
                    drawPieceInQueuePosition(canvas, L, i + 1);
                    break;
                case "O":
                    drawPieceInQueuePosition(canvas, O, i + 1);
                    break;
                case "S":
                    drawPieceInQueuePosition(canvas, S, i + 1);
                    break;
                case "T":
                    drawPieceInQueuePosition(canvas, T, i + 1);
                    break;
                case "Z":
                    drawPieceInQueuePosition(canvas, Z, i + 1);
                    break;
            }
        }
    }
}

/** 
(Helper function) Erases the previous hard drop ghost and piece in 
play. 
@param canvas canvas DOM element
@return {nothing}
*/
function erasePreviousGhostAndCopyOfFallingPiece(canvas) {
    if (tetris.previousSquaresOfHardDropGhost != null) {
        for (let i = 0; i < tetris.previousSquaresOfHardDropGhost.length; i++) {
            let ypix = 
                (tetris.previousSquaresOfHardDropGhost[i].row - 18) 
                * tetris.unitSize;
            let xpix = 
                (tetris.previousSquaresOfHardDropGhost[i].col + 7) 
                * tetris.unitSize;
            let sq = new SingleSquare(xpix, ypix, I, false, false, true);
            sq.draw(canvas);
        }

    } if (tetris.previousSquaresOfPieceInPlay != null) {
        for (let i = 0; i < tetris.previousSquaresOfPieceInPlay.length; i++) {
            let ypix = 
                (tetris.previousSquaresOfPieceInPlay[i].row - 18) 
                * tetris.unitSize;
            let xpix = 
                (tetris.previousSquaresOfPieceInPlay[i].col + 7) 
                * tetris.unitSize;
            let sq = new SingleSquare(xpix, ypix, I, false, false, true);
            sq.draw(canvas);
        }
    }
}

/** 
(Helper function) Draws the squares of the Tetris 
stack detailed in body.drawOnStack.
@param canvas canvas DOM element
@param body body of response from server
@return {nothing}
*/
function updateTetrisStack(canvas, body) {
    let redraw = body.drawOnStack;
    let ypix;
    let xpix;
    let sq;
    for (let i = 0; i < redraw.length; i++) {
        ypix = (redraw[i].row - 18) * tetris.unitSize;
        xpix = (redraw[i].col + 7) * tetris.unitSize;
        switch(String(redraw[i].occupiedBy)) {
            case "I":
                sq = new SingleSquare(xpix, ypix, I);
                sq.draw(canvas);
                break;
            case "J":
                sq = new SingleSquare(xpix, ypix, J);
                sq.draw(canvas);
                break;
            case "L":
                sq = new SingleSquare(xpix, ypix, L);
                sq.draw(canvas);
                break;
            case "O":
                sq = new SingleSquare(xpix, ypix, O);
                sq.draw(canvas);
                break;
            case "S":
                sq = new SingleSquare(xpix, ypix, S);
                sq.draw(canvas);
                break;
            case "T":
                sq = new SingleSquare(xpix, ypix, T);
                sq.draw(canvas);
                break;
            case "Z":
                sq = new SingleSquare(xpix, ypix, Z);
                sq.draw(canvas);
                break;
            default:
                sq = new SingleSquare(xpix, ypix, I, false, false, true);
                sq.draw(canvas);
                break;
        }
    }
}

/** 
(Helper function) (Re)draws the new hard drop ghost.
body is the parsed body of the response from the server.
Also updates tetris.previousSquaresOfHardDropGhost.
@param canvas canvas DOM element
@param body body of response from server
@return {nothing}
*/
function drawNewHardDropGhost(canvas, body) {
    let ghost = body.squaresOfHardDropGhost;
    let ypix;
    let xpix;
    let sq;
    for (let i = 0; i < ghost.length; i++) {
        ypix = (ghost[i].row - 18) * tetris.unitSize;
        xpix = (ghost[i].col + 7) * tetris.unitSize;
        sq = new SingleSquare(xpix, ypix, I, true);
        sq.draw(canvas);
    }
    tetris.previousSquaresOfHardDropGhost = ghost;
}

/** 
(Helper function) Draws the new copy of the piece in play.
body is the parsed body of the response from the server.
Also updates tetris.previousSquaresOfPieceInPlay.
@param canvas canvas DOM element
@param body body of response from server
@return {nothing}
*/
function drawNewCopyOfPieceInPlay(canvas, body) {
    let name = String(body.pieceInPlay);
    let pieceSquares = body.squaresOfPieceInPlay;
    let pieceColour;
    let ypix;
    let xpix;
    let sq;
    switch(name) {
        case "I":
            pieceColour = I;
            break;
        case "J":
            pieceColour = J;
            break;
        case "L":
            pieceColour = L;
            break;
        case "O":
            pieceColour = O;
            break;
        case "S":
            pieceColour = S;
            break;
        case "T":
            pieceColour = T;
            break;
        case "Z":
            pieceColour = Z;
            break;
    }
    for (let i = 0; i < pieceSquares.length; i++) {
        ypix = (pieceSquares[i].row - 18) * tetris.unitSize;
        xpix = (pieceSquares[i].col + 7) * tetris.unitSize;
        sq = new SingleSquare(xpix, ypix, pieceColour);
        sq.draw(canvas);
    }
    tetris.previousSquaresOfPieceInPlay = pieceSquares;
}


/** *
(Helper function for drawMessages) Erases the message on row r.
@param canvas canvas DOM element
@param r {int} row to erase the message from
*/
function eraseMessageOnRow(canvas, r) {
    let ctx = canvas.getContext("2d");
    ctx.fillStyle = BACKGROUND;
    ctx.fillRect(
        tetris.unitSize - 2, 
        (9 + r) * tetris.unitSize + Math.floor(tetris.unitSize / 3), 
        5 * tetris.unitSize + 4,
        tetris.unitSize);
}


/** (Helper function for drawMessages) Draws the message on row r.
 * The message disappears after 1000 milliseconds.
 * @param canvas canvas DOM element
 * @param message {string} message to draw
 * @param r {int} row to draw the message on
 * @return {nothing}
 */
function drawMessageOnRow(canvas, message, r) {
    let ctx = canvas.getContext("2d");

    switch(r) {
        case 0:
            clearTimeout(tetris.message.row0);
            break;
        case 1:
            clearTimeout(tetris.message.row1);
            break;
        case 2:
            clearTimeout(tetris.message.row2);
            break;
        case 3:
            clearTimeout(tetris.message.row3);
            break;
        case 4:
            clearTimeout(tetris.message.row4);
            break;
        case 5:
            clearTimeout(tetris.message.row5);
            break;
    }

    eraseMessageOnRow(canvas, r);
    ctx.strokeStyle = "#000000";
    ctx.lineWidth = 1;
    ctx.font = "{}px Arial".replace("{}", Math.floor((2 * tetris.unitSize) / 3));
    ctx.strokeText(
        message, 
        tetris.unitSize,
        (10 + r) * tetris.unitSize);
    
    switch(r) {
        case 0:
            tetris.message.row0 = setTimeout(
                function() {
                    eraseMessageOnRow(canvas, 0);
                },
                1000);
            break;
        case 1:
            tetris.message.row1 = setTimeout(
                function() {
                    eraseMessageOnRow(canvas, 1);
                },
                1000);
            break;
        case 2:
            tetris.message.row2 = setTimeout(
                function() {
                    eraseMessageOnRow(canvas, 2);
                },
                1000);
            break;
        case 3:
            tetris.message.row3 = setTimeout(
                function() {
                    eraseMessageOnRow(canvas, 3);
                },
                1000);
            break;
        case 4:
            tetris.message.row4 = setTimeout(
                function() {
                    eraseMessageOnRow(canvas, 4);
                },
                1000);
            break;
        case 5:
            tetris.message.row5 = setTimeout(
                function() {
                    eraseMessageOnRow(canvas, 5);
                },
                1000);
            break;
    }
}

/** Draws messages. Informs the player of
 * tspins and back-to-back tspins,
 * tetrises and back-to-back tetrises,
 * perfect clears, and
 * line clear combos.
 * @param canvas canvas DOM element
 * @param body body of response from server
 * @return {nothing}
 */
function drawMessages(canvas, body) {
    let tspinMessage = "T-SPIN ";
    let singleMessage = "SINGLE";
    let doubleMessage = "DOUBLE";
    let tripleMessage = "TRIPLE";
    let tetrisMessage = "TETRIS";
    let backtobackMessage = "B2B";
    let allClearMessage = "ALL CLEAR"
    let comboMessage = "COMBO";
    let levelUpMessage = "LEVEL UP!";
    let tspinRow = 0;
    let linesRow = 1;
    let backtobackRow = 2;
    let allClearRow = 3;
    let comboRow = 4;
    let levelUpRow = 5;

    let isTSpin;
    let linesCleared;
    let backtobacks;
    let isPerfectClear;
    let combo;
    let levelUp;
    
    if (body.lineClearInfo != null) {

        isTSpin = body.lineClearInfo.tspin;
        linesCleared = body.lineClearInfo.linesCleared;
        backtobacks = body.lineClearInfo.consecTetrisOrTSpin;
        isPerfectClear = body.lineClearInfo.perfectClear;
        combo = body.lineClearInfo.combo;
        levelUp = body.lineClearInfo.levelUp;
        
        if (isTSpin) {
            switch(linesCleared) {
                case 1:
                    drawMessageOnRow(
                        canvas, tspinMessage + singleMessage, tspinRow);
                    break;
                case 2:
                    drawMessageOnRow(
                        canvas, tspinMessage + doubleMessage, tspinRow);
                    break;
                case 3:
                    drawMessageOnRow(
                        canvas, tspinMessage + tripleMessage, tspinRow);
                    break;
                default:
                    drawMessageOnRow(canvas, tspinMessage, tspinRow);
            }

        } else if (linesCleared == 4) {
            drawMessageOnRow(canvas, tetrisMessage, linesRow);
        }

        if (linesCleared > 0 && backtobacks >= 1) {
            drawMessageOnRow(
                canvas, 
                backtobackMessage + " x" + String(backtobacks),
                backtobackRow);
        }

        if (isPerfectClear) {
            drawMessageOnRow(canvas, allClearMessage, allClearRow);
        }

        if (combo >= 1) {
            drawMessageOnRow(
                canvas, 
                String(combo) + " " + comboMessage, 
                comboRow);
        }

        if (levelUp) {
            drawMessageOnRow(canvas, levelUpMessage, levelUpRow);
        }
    }
}

/** Updates the score.
 * @param body body of response from server
 */
function showMostRecentScore(body) {
    $("#score").empty();
    $("#score").html("<p> Score: " + String(body.score) + "</p>");
}


/** Updates the board given a response from the server.
 * @param response response body from the server
 * @return {nothing}
 */
function updateBoard(response) {
    let canvas = document.getElementById("tetris-board");
    let body = JSON.parse(response.body);

    if (body.spawnUnsuccessful) {
        end(body.score);

    } else {
        updateTimer(body);
        updateHoldPiece(canvas, body);
        updateNextQueue(canvas, body);
        erasePreviousGhostAndCopyOfFallingPiece(canvas);
        updateTetrisStack(canvas, body);
        drawNewHardDropGhost(canvas, body);
        drawNewCopyOfPieceInPlay(canvas, body);
        drawMessages(canvas, body);
        showMostRecentScore(body);
    }
}

/** Handles the animation loop.
 * @return {nothing}
 */
function updateFrame() {
    if (tetris.gameActive) {
        window.requestAnimationFrame(updateFrame);
    }
}

// ==========================================================================
// Page management: Sending commands to the server
// ========================================================================

/** Tells the server to start the game.
 * @param level {integer} level that player starts at
 * @return {nothing}
 */
function sendGameStart(level) {
    let startWithLevel = level;
    if (level == null) {
        startWithLevel = 1;
    }
    tetris.stompClient.send(
        "/app/start-new-game",
        {},
        JSON.stringify({"level": startWithLevel})
    );
}


/** Sends the automatic-timed-fall command to the server.
 * @return {nothing}
 */
function sendTimedFall() {
    tetris.stompClient.send(
        "/app/timed-fall",
        {},
        JSON.stringify({"keyCommand": "NOTHING"})
    );
}


/** Sends the automatic-delayed-lock command to the server.
 * @return {nothing}
 */
function sendTimedLock() {
    tetris.stompClient.send(
        "/app/timed-lock",
        {},
        JSON.stringify({"keyCommand": "NOTHING"})
    );
}


/** Sends the hard drop command to the server.
 * @return {nothing}
 */
function sendHardDrop() {
    tetris.stompClient.send(
        "/app/hard-drop",
        {},
        JSON.stringify({"keyCommand": "HARDDROP"})
    );
}

/** Sends the sonic drop command to the server.
 @return {nothing}
 */
function sendSonicDrop() {
    tetris.stompClient.send(
        "/app/sonic-drop",
        {},
        JSON.stringify({"keyCommand": "SONICDROP"})
    );
}

/** Sends the move-leftcommand to the server.
 * @return {nothing}
 */
function sendLeft() {
    tetris.stompClient.send(
        "/app/move",
        {},
        JSON.stringify({"keyCommand": "LEFT"})
    );
}

/** 
Sends the move-right command to the server.
@return {nothing}
*/
function sendRight() {
    tetris.stompClient.send(
        "/app/move",
        {},
        JSON.stringify({"keyCommand": "RIGHT"})
    );
}

/**
 Sends the rotate-clockwise command to the server.
 * @return {nothing}
*/
function sendClockwise() {
    tetris.stompClient.send(
        "/app/rotate",
        {},
        JSON.stringify({"keyCommand": "CLOCKWISE"})
    );
}

/** Sends the rotate-counterclockwise command to the server.
@return {nothing}
 */
function sendCounterClockwise() {
    tetris.stompClient.send(
        "/app/rotate",
        {},
        JSON.stringify({"keyCommand": "COUNTERCLOCKWISE"})
    );
}

/** Sends the hold command to the server.
 * @return {nothing}
 */
function sendHold() {
    tetris.stompClient.send(
        "/app/hold",
        {},
        JSON.stringify({"keyCommand": "HOLD"})
    );
}

$(function() {
    tetris.stompClient = null;
    tetris.unitSize = null;
    tetris.gameActive = false;
    tetris.timer = null;
    tetris.previousSquaresOfPieceInPlay = null;
    tetris.previousSquaresOfHardDropGhost = null;
    tetris.message = {};

    initCanvas();
    $( "#start" ).on("click", function() {
        let selectedLevel = document.getElementById("level-select").value;

        if (!tetris.gameActive) {
            switch(String(selectedLevel)) {
                case "1":
                    start(1);
                    break;
                case "2":
                    start(2);
                    break;
                case "3":
                    start(3);
                    break;
                case "4":
                    start(4);
                    break;
                case "5":
                    start(5);
                    break;
                default:
                    start(1);
            }
        }
    });
    $( "#restart" ).on("click", function() {
        let selectedLevel = document.getElementById("restart-level-select").value;

        if (!tetris.gameActive) {
            switch(String(selectedLevel)) {
                case "1":
                    start(1);
                    break;
                case "2":
                    start(2);
                    break;
                case "3":
                    start(3);
                    break;
                case "4":
                    start(4);
                    break;
                case "5":
                    start(5);
                    break;
                default:
                    start(1);
            }
        }
    });
    $( document ).on("keydown", function(event) {
        if (tetris.gameActive) {
            console.log("Registered key down while game active.");
            console.log(event);
            switch(String(event.key)) {
                case "ArrowDown":
                    event.preventDefault();
                    sendSonicDrop();
                    break;
                case " ":
                    event.preventDefault();
                    sendHardDrop();
                    break;
                case "ArrowLeft":
                    event.preventDefault();
                    sendLeft();
                    break;
                case "ArrowRight":
                    event.preventDefault();
                    sendRight();
                    break;
                case "ArrowUp":
                case "x":
                case "X":
                    event.preventDefault();
                    sendClockwise();
                    break;
                case "z":
                case "Z":
                    event.preventDefault();
                    sendCounterClockwise();
                    break;
                case "Shift":
                case "c":
                case "C":
                    event.preventDefault();
                    sendHold();
                    break;
            }
        }
    });
    
});
