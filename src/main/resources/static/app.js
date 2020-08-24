var tetris = {};
tetris.stompClient = null;
tetris.unitSize = null;
tetris.gameActive = false;
tetris.timer = null;
tetris.previousSquaresOfPieceInPlay = null;
tetris.previousSquaresOfHardDropGhost = null;

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

function SingleSquare(params) {
    this.x = params.x || 0;
    this.y = params.y || 0;
    this.piece = params.piece || -1;
    this.ghost = params.ghost || false;
    this.erase = params.erase || false;
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

// Drawing

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
            ctx.fillStyle = BACKGROUND;
            ctx.strokeStyle = BACKGROUND;
    }
    if (this.ghost) {
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
            var square1 = new SingleSquare({
                x: this.x,
                y: this.y + tetris.unitSize,
                piece: I,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + tetris.unitSize,
                y: this.y + tetris.unitSize,
                piece: I,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize,
                y: this.y + tetris.unitSize,
                piece: I,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + 3 * tetris.unitSize,
                y: this.y + tetris.unitSize,
                piece: I,
                ghost: this.ghost
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
        case RIGHT:
            var square1 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y,
                piece: I,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + tetris.unitSize,
                piece: I,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + 2 * tetris.unitSize,
                piece: I,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + 3 * tetris.unitSize,
                piece: I,
                ghost: this.ghost
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
        case LEFT:
            var square1 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y, 
                piece: I,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize,
                piece: I,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + 2 * tetris.unitSize,
                piece: I,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + 3 * tetris.unitSize,
                piece: I,
                ghost: this.ghost
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
        case UPSIDEDOWN:
            var square1 = new SingleSquare({
                x: this.x, 
                y: this.y + 2 * tetris.unitSize,
                piece: I,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + 2 * tetris.unitSize, 
                piece: I,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + 2 * tetris.unitSize,
                piece: I,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + 3 * tetris.unitSize, 
                y: this.y + 2 * tetris.unitSize,
                piece: I,
                ghost: this.ghost
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
    }
}

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
            var square1 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y,
                piece: L,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x, 
                y: this.y + tetris.unitSize,
                piece: L,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: L,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: L,
                ghost: this.ghost
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
        case LEFT:
            var square1 = new SingleSquare({
                x: this.x,
                y: this.y,
                piece: L,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y,
                piece: L,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: L,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + 2 * tetris.unitSize, 
                piece: L,
                ghost: this.ghost
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
        case RIGHT:
            var square1 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y,
                piece: L,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: L,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + 2 * tetris.unitSize, 
                piece: L,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + 2 * tetris.unitSize, 
                piece: L,
                ghost: this.ghost
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
        case UPSIDEDOWN:
            var square1 = new SingleSquare({
                x: this.x, 
                y: this.y + tetris.unitSize, 
                piece: L,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: L,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: L,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x, 
                y: this.y + 2 * tetris.unitSize,
                piece: L,
                ghost: this.ghost
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
    }
}

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
            var square1 = new SingleSquare({
                x: this.x, 
                y: this.y, 
                piece: J, 
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x, 
                y: this.y + tetris.unitSize,
                piece: J,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + tetris.unitSize,
                y: this.y + tetris.unitSize, 
                piece: J,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: J,
                ghost: this.ghost
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
        case LEFT:
            var square1 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y,
                piece: J, 
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: J,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x, 
                y: this.y + 2 * tetris.unitSize,
                piece: J,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + 2 * tetris.unitSize, 
                piece: J,
                ghost: this.ghost
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
        case RIGHT:
            var square1 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y, 
                piece: J, 
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y,
                piece: J,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: J,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + tetris.unitSize,
                y: this.y + 2 * tetris.unitSize, 
                piece: J,
                ghost: this.ghost
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
        case UPSIDEDOWN:
            var square1 = new SingleSquare({
                x: this.x, 
                y: this.y + tetris.unitSize, 
                piece: J, 
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: J,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize,
                y: this.y + tetris.unitSize, 
                piece: J,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + 2 * tetris.unitSize, 
                piece: J,
                ghost: this.ghost
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
    }
}

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
            var square1 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y,
                piece: T,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x, 
                y: this.y + tetris.unitSize, 
                piece: T,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: T,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: T,
                ghost: this.ghost
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
        case LEFT:
            var square1 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y, 
                piece: T,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x, 
                y: this.y + tetris.unitSize, 
                piece: T,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: T,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + 2 * tetris.unitSize, 
                piece: T,
                ghost: this.ghost
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
        case RIGHT:
            var square1 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y,
                piece: T,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: T,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: T,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + 2 * tetris.unitSize,
                piece: T,
                ghost: this.ghost
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
        case UPSIDEDOWN:
            var square1 = new SingleSquare({
                x: this.x, 
                y: this.y + tetris.unitSize, 
                piece: T,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: T,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize,
                y: this.y + tetris.unitSize, 
                piece: T,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + 2 * tetris.unitSize, 
                piece: T,
                ghost: this.ghost
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
    }
}

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
            var square1 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y,
                piece: S,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y, 
                piece: S,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x, 
                y: this.y + tetris.unitSize, 
                piece: S,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: S,
                ghost: this.ghost
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
        case LEFT:
            var square1 = new SingleSquare({
                x: this.x,
                y: this.y,
                piece: S,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x, 
                y: this.y + tetris.unitSize, 
                piece: S,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: S,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + 2 * tetris.unitSize, 
                piece: S,
                ghost: this.ghost
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
        case RIGHT:
            var square1 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y, 
                piece: S,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: S,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: S,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + 2 * tetris.unitSize, 
                piece: S,
                ghost: this.ghost
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
        case UPSIDEDOWN:
            var square1 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: S,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize,
                y: this.y + tetris.unitSize, 
                piece: S,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x, 
                y: this.y + 2 * tetris.unitSize, 
                piece: S,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + tetris.unitSize,
                y: this.y + 2 * tetris.unitSize, 
                piece: S,
                ghost: this.ghost
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
    }
}

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
            var square1 = new SingleSquare({
                x: this.x, 
                y: this.y, 
                piece: Z,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y,
                piece: Z,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
        case LEFT:
            var square1 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y, 
                piece: Z,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x, 
                y: this.y + tetris.unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x, 
                y: this.y + 2 * tetris.unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
        case RIGHT:
            var square1 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y, 
                piece: Z,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + 2 * tetris.unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
        case UPSIDEDOWN:
            var square1 = new SingleSquare({
                x: this.x, 
                y: this.y + tetris.unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + 2 * tetris.unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + 2 * tetris.unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
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
    var square1 = new SingleSquare({
        x: this.x + tetris.unitSize, 
        y: this.y, 
        piece: O,
        ghost: this.ghost
    });
    var square2 = new SingleSquare({
        x: this.x + 2 * tetris.unitSize, 
        y: this.y,
        piece: O,
        ghost: this.ghost
    });
    var square3 = new SingleSquare({
        x: this.x + tetris.unitSize, 
        y: this.y + tetris.unitSize,
        piece: O,
        ghost: this.ghost
    });
    var square4 = new SingleSquare({
        x: this.x + 2 * tetris.unitSize, 
        y: this.y + tetris.unitSize,
        piece: O,
        ghost: this.ghost
    });
    square1.draw(canvas);
    square2.draw(canvas);
    square3.draw(canvas);
    square4.draw(canvas);
}

// ========================================================================
// Drawing things on the board
// ========================================================================

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
    ctx.strokeRect(7 * unitSize - 2, 2 * unitSize - 2, 
        10 * unitSize + 4, 20 * unitSize + 4);
}


// initNextQueue() draws the next-pieces queue with no Tetris 
// pieces in it.

function initNextQueue(canvas, unitSize) {
    var ctx = canvas.getContext("2d");

    ctx.font = "{}px Arial".replace("{}", Math.floor(unitSize / 2));
    ctx.strokeText("NEXT", 18 * unitSize, Math.floor(unitSize * 3 / 2));

    ctx.strokeRect(18 * unitSize, 2 * unitSize, 6 * unitSize, 17 * unitSize);
}


// drawPieceInQueuePosition(canvas, piececode, pos) draws the piece 
// represented by piececode (constant I, J, L, T, S, Z, O)
// in the position pos (int: 1, 2, 3, 4, 5) in the next-piece queue.

function drawPieceInQueuePosition(canvas, piececode, pos) {
    var xpix = 19 * tetris.unitSize;
    var ypix = 3 * tetris.unitSize + 3 * tetris.unitSize * (pos - 1);
    var piece;

    clearPieceInQueuePosition(canvas, pos);

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
    var xpix = 19 * tetris.unitSize;
    var ypix = 3 * tetris.unitSize + 3 * tetris.unitSize * (pos - 1);

    ctx.fillStyle = BACKGROUND;
    ctx.fillRect(xpix - 2, ypix - 2, 4 * tetris.unitSize + 4, 3 * tetris.unitSize + 2);
}


// drawPieceInHold(canvas, piececode) draws the corresponding 
// piece in the hold box.

function drawPieceInHold(canvas, piececode) {
    var piece;
    clearPieceInHold(canvas);
    switch (piececode) {
        case I:
            piece = new IPiece({x: tetris.unitSize, y: 3 * tetris.unitSize});
            break;
        case L:
            piece = new LPiece({x: tetris.unitSize, y: 3 * tetris.unitSize});
            break;
        case J:
            piece = new JPiece({x: tetris.unitSize, y: 3 * tetris.unitSize});
            break;
        case T:
            piece = new TPiece({x: tetris.unitSize, y: 3 * tetris.unitSize});
            break;
        case S:
            piece = new SPiece({x: tetris.unitSize, y: 3 * tetris.unitSize});
            break;
        case Z:
            piece = new ZPiece({x: tetris.unitSize, y: 3 * tetris.unitSize});
            break;
        case O:
            piece = new OPiece({x: tetris.unitSize, y: 3 * tetris.unitSize});
            break;
    }
    piece.draw(canvas);
}


// clearPieceInHold(canvas) erases the piece in the hold box.

function clearPieceInHold(canvas) {
    var ctx = canvas.getContext("2d");
    ctx.fillStyle = BACKGROUND;
    ctx.fillRect(tetris.unitSize - 2, 3 * tetris.unitSize - 2, 
        4 * tetris.unitSize + 4, 4 * tetris.unitSize + 4);
}

// initCanvas draws the barebones Tetris board: hold box, board, and 
// next-queue with no Tetris pieces.

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

// connect() connects to the server.

function connect() {
    var socket = new SockJS("/tetris");
    tetris.stompClient = Stomp.over(socket);
    tetris.stompClient.connect({}, function (frame) {
        console.log("Connected: " + frame);
        tetris.stompClient.subscribe(
            "/topic/board-update", 
            function (response) {
                updateBoard(response);
            });
    });
}


// Update the page after the game has started

function start(key) {
    switch (key) {
        case 68:
            alert("Debug mode");
            break;
        default:
            document.getElementById("start-overlay").style.display = "none";
            $('#tetris-theme').trigger("play");
            tetris.gameActive = true;
            sendGameStart();
            updateFrame();
    }
}



// Updates the board given a response from the server.

function updateBoard(response) {
    var canvas = document.getElementById("tetris-board");

    var body = JSON.parse(response.body);

    // Update the timer
    // Draw the new piece in hold
    var hold = String(body.hold);
    if (body.spawnedPiece) {    
        switch(hold) {
            case "I":
                drawPieceInHold(canvas, I);
                break;
            case "J":
                drawPieceInHold(canvas, J);
                break;
            case "L":
                drawPieceInHold(canvas, L);
                break;
            case "O":
                drawPieceInHold(canvas, O);
                break;
            case "S":
                drawPieceInHold(canvas, S);
                break;
            case "T":
                drawPieceInHold(canvas, T);
                break;
            case "Z":
                drawPieceInHold(canvas, Z);
                break;
        }
    }
    // Draw the new pieces in the next queue
    var next = body.nextFivePieces;
    if (body.spawnedPiece) {
        for (var i = 0; i < next.length; i++) {
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
    
    // Erase the previous hard drop ghost and piece in play
    if (tetris.previousSquaresOfHardDropGhost != null) {
        for (var i = 0; i < tetris.previousSquaresOfHardDropGhost.length; i++) {
            var ypix = 
                (tetris.previousSquaresOfHardDropGhost[i].row - 18) 
                * tetris.unitSize;
            var xpix = 
                (tetris.previousSquaresOfHardDropGhost[i].col + 7) 
                * tetris.unitSize;
            var sq = new SingleSquare({x: xpix, y: ypix, erase: true});
            sq.draw(canvas);
        }

    } if (tetris.previousSquaresOfPieceInPlay != null) {
        for (var i = 0; i < tetris.previousSquaresOfPieceInPlay.length; i++) {
            var ypix = 
                (tetris.previousSquaresOfPieceInPlay[i].row - 18) 
                * tetris.unitSize;
            var xpix = 
                (tetris.previousSquaresOfPieceInPlay[i].col + 7) 
                * tetris.unitSize;
            var sq = new SingleSquare({x: xpix, y: ypix, erase: true});
            sq.draw(canvas);
        }
    }

    // Draw the changes to the stack
    var changes = body.changesToStack;
    for (var i = 0; i < changes.length; i++) {
        var ypix = (changes[i].row - 18) * tetris.unitSize;
        var xpix = (changes[i].col + 7) * tetris.unitSize;
        var sq;
        switch(String(changes[i].occupiedBy)) {
            case "I":
                sq = new SingleSquare({x: xpix, y: ypix, piece: I});
                sq.draw(canvas);
                break;
            case "J":
                sq = new SingleSquare({x: xpix, y: ypix, piece: J});
                sq.draw(canvas);
                break;
            case "L":
                sq = new SingleSquare({x: xpix, y: ypix, piece: L});
                sq.draw(canvas);
                break;
            case "O":
                sq = new SingleSquare({x: xpix, y: ypix, piece: O});
                sq.draw(canvas);
                break;
            case "S":
                sq = new SingleSquare({x: xpix, y: ypix, piece: S});
                sq.draw(canvas);
                break;
            case "T":
                sq = new SingleSquare({x: xpix, y: ypix, piece: T});
                sq.draw(canvas);
                break;
            case "Z":
                sq = new SingleSquare({x: xpix, y: ypix, piece: Z});
                sq.draw(canvas);
                break;
            default:
                sq = new SingleSquare({x: xpix, y: ypix, erase: true});
                sq.draw(canvas);
                break;
        }
    }

    // Draw the hard drop ghost
    var ghost = body.squaresOfHardDropGhost;
    for (var i = 0; i < ghost.length; i++) {
        var ypix = (ghost[i].row - 18) * tetris.unitSize;
        var xpix = (ghost[i].col + 7) * tetris.unitSize;
        var sq = new SingleSquare({x: xpix, y: ypix, ghost: true});
        sq.draw(canvas);
    }
    tetris.previousSquaresOfHardDropGhost = ghost;

    // Draw the piece in play
    var name = String(body.pieceInPlay);
    var pieceSquares = body.squaresOfPieceInPlay;
    var pieceColour;
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
    for (var i = 0; i < pieceSquares.length; i++) {
        var ypix = (pieceSquares[i].row - 18) * tetris.unitSize;
        var xpix = (pieceSquares[i].col + 7) * tetris.unitSize;
        var sq = new SingleSquare({x: xpix, y: ypix, piece: pieceColour});
        sq.draw(canvas);
    }
    tetris.previousSquaresOfPieceInPlay = pieceSquares;
}


// Handles the animation loop.

function updateFrame() {
    if (tetris.gameActive) {
        window.requestAnimationFrame(updateFrame);
    }
}

// ==========================================================================
// Page management: Sending commands to the server
// ========================================================================

// Tells the server to start the game.

function sendGameStart() {
    tetris.stompClient.send(
        "/app/start-new-game",
        {},
        JSON.stringify({"keyCommand": "NOTHING"})
    );
}


// Sends the hard drop command to the server.

function sendHardDrop() {
    tetris.stompClient.send(
        "/app/hard-drop",
        {},
        JSON.stringify({"keyCommand": "HARDDROP"})
    );
}

// Sends the soft drop command to the server.

function sendSoftDrop() {
    tetris.stompClient.send(
        "/app/soft-drop",
        {},
        JSON.stringify({"keyCommand": "SOFTDROP"})
    );
}

// Sends the move-left command to the server.

function sendLeft() {
    tetris.stompClient.send(
        "/app/move",
        {},
        JSON.stringify({"keyCommand": "LEFT"})
    );
}

// Sends the move-right command to the server.

function sendRight() {
    tetris.stompClient.send(
        "/app/move",
        {},
        JSON.stringify({"keyCommand": "RIGHT"})
    );
}

// Sends the rotate-clockwise command to the server.

function sendClockwise() {
    tetris.stompClient.send(
        "/app/rotate",
        {},
        JSON.stringify({"keyCommand": "CLOCKWISE"})
    );
}

// Sends the rotate-counterclockwise command to the server.

function sendCounterClockwise() {
    tetris.stompClient.send(
        "/app/rotate",
        {},
        JSON.stringify({"keyCommand": "COUNTERCLOCKWISE"})
    );
}

// Sends the hold command to the server.

function sendHold() {
    tetris.stompClient.send(
        "/app/hold",
        {},
        JSON.stringify({"keyCommand": "HOLD"})
    );
}

$(function() {
    $( "#tetris-board" ).ready(function() { 
        connect(); 
        initCanvas(); 
    });
    $( document ).on("click", function() {
        if (!tetris.gameActive) {
            start();
        }
    });
    $( document ).keydown(function(event) {
        if (!tetris.gameActive) {
            start(event.which);

        } else {
            switch(event.which) {
                case 40:
                    event.preventDefault();
                    var stop = setInterval(
                        function() { 
                            sendSoftDrop(); 
                        }, 
                        100);
                    $(window).on(
                        "keyup", 
                        function(e) { 
                            clearInterval(stop); 
                        });
                    break;
                case 32:
                    event.preventDefault();
                    sendHardDrop();
                    break;
                case 37:
                    event.preventDefault();
                    sendLeft();
                    break;
                case 39:
                    event.preventDefault();
                    sendRight();
                    break;
                case 88:
                    event.preventDefault();
                    sendClockwise();
                    break;
                case 90:
                    event.preventDefault();
                    sendCounterClockwise();
                    break;
                case 67:
                    event.preventDefault();
                    sendHold();
                    break;
            }
        }
    });
    
});
