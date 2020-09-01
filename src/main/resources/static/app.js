var tetris = {};
tetris.stompClient = null;
tetris.unitSize = null;
tetris.gameActive = false;
tetris.timer = null;
tetris.previousSquaresOfPieceInPlay = null;
tetris.previousSquaresOfHardDropGhost = null;
tetris.message = {};

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

/* =========================================================================

Sections:

1. Drawing pieces
2. Drawing things on the board
3. Page management:
  a. General
  b. Updating the drawing of the board
  c. Sending commands to the server

========================================================================= */

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

class SingleSquare {
    constructor(params) {
        this.x = params.x || 0;
        this.y = params.y || 0;
        this.piece = params.piece || -1;
        this.ghost = params.ghost || false;
        this.unusableHoldPiece = params.unusableHoldPiece || false; 
        this.erase = params.erase || false;
    }
}

class IPiece {
    constructor(params) {
        this.x = params.x || 0;
        this.y = params.y || 0;
        this.fillStyle = ICOLOUR;
        this.strokeStyle = IOUTLINE;
        this.orientation = params.orientation || UPRIGHT;
        this.ghost = params.ghost || false;
        this.unusableHoldPiece = params.unusableHoldPiece || false; 
    }
}

class LPiece {
    constructor(params) {
        this.x = params.x || 0;
        this.y = params.y || 0;
        this.fillStyle = LCOLOUR;
        this.strokeStyle = LOUTLINE;
        this.orientation = params.orientation || UPRIGHT;
        this.ghost = params.ghost || false;
        this.unusableHoldPiece = params.unusableHoldPiece || false; 
    }
}

class JPiece {
    constructor(params) {
        this.x = params.x || 0;
        this.y = params.y || 0;
        this.fillStyle = JCOLOUR;
        this.strokeStyle = JOUTLINE;
        this.orientation = params.orientation || UPRIGHT;
        this.ghost = params.ghost || false;
        this.unusableHoldPiece = params.unusableHoldPiece || false; 
    }
}

class TPiece {
    constructor(params) {
        this.x = params.x || 0;
        this.y = params.y || 0;
        this.fillStyle = TCOLOUR;
        this.strokeStyle = TOUTLINE;
        this.orientation = params.orientation || UPRIGHT;
        this.ghost = params.ghost || false;
        this.unusableHoldPiece = params.unusableHoldPiece || false; 
    }
}

class SPiece {
    constructor(params) {
        this.x = params.x || 0;
        this.y = params.y || 0;
        this.fillStyle = SCOLOUR;
        this.strokeStyle = SOUTLINE;
        this.orientation = params.orientation || UPRIGHT;
        this.ghost = params.ghost || false;
        this.unusableHoldPiece = params.unusableHoldPiece || false; 
    }
}

class ZPiece {
    constructor(params) {
        this.x = params.x || 0;
        this.y = params.y || 0;
        this.fillStyle = ZCOLOUR;
        this.strokeStyle = ZOUTLINE;
        this.orientation = params.orientation || UPRIGHT;
        this.ghost = params.ghost || false;
        this.unusableHoldPiece = params.unusableHoldPiece || false; 
    }
}

class OPiece {
    constructor(params) {
        this.x = params.x || 0;
        this.y = params.y || 0;
        this.fillStyle = OCOLOUR;
        this.strokeStyle = OOUTLINE;
        this.orientation = params.orientation || UPRIGHT;
        this.ghost = params.ghost || false;
        this.unusableHoldPiece = params.unusableHoldPiece || false; 
    }
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

IPiece.prototype.draw = function(canvas) {
    var square1;
    var square2;
    var square3;
    var square4;
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
            square1 = new SingleSquare({
                x: this.x,
                y: this.y + tetris.unitSize,
                piece: I,
                ghost: this.ghost,
                unusableHoldPiece: this.unusableHoldPiece
            });
            square2 = new SingleSquare({
                x: this.x + tetris.unitSize,
                y: this.y + tetris.unitSize,
                piece: I,
                ghost: this.ghost,
                unusableHoldPiece: this.unusableHoldPiece
            });
            square3 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize,
                y: this.y + tetris.unitSize,
                piece: I,
                ghost: this.ghost,
                unusableHoldPiece: this.unusableHoldPiece
            });
            square4 = new SingleSquare({
                x: this.x + 3 * tetris.unitSize,
                y: this.y + tetris.unitSize,
                piece: I,
                ghost: this.ghost,
                unusableHoldPiece: this.unusableHoldPiece
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
        case RIGHT:
            square1 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y,
                piece: I,
                ghost: this.ghost
            });
            square2 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + tetris.unitSize,
                piece: I,
                ghost: this.ghost
            });
            square3 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + 2 * tetris.unitSize,
                piece: I,
                ghost: this.ghost
            });
            square4 = new SingleSquare({
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
            square1 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y, 
                piece: I,
                ghost: this.ghost
            });
            square2 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize,
                piece: I,
                ghost: this.ghost
            });
            square3 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + 2 * tetris.unitSize,
                piece: I,
                ghost: this.ghost
            });
            square4 = new SingleSquare({
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
            square1 = new SingleSquare({
                x: this.x, 
                y: this.y + 2 * tetris.unitSize,
                piece: I,
                ghost: this.ghost
            });
            square2 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + 2 * tetris.unitSize, 
                piece: I,
                ghost: this.ghost
            });
            square3 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + 2 * tetris.unitSize,
                piece: I,
                ghost: this.ghost
            });
            square4 = new SingleSquare({
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
    var square1;
    var square2;
    var square3;
    var square4;
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
            square1 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y,
                piece: L,
                ghost: this.ghost,
                unusableHoldPiece: this.unusableHoldPiece
            });
            square2 = new SingleSquare({
                x: this.x, 
                y: this.y + tetris.unitSize,
                piece: L,
                ghost: this.ghost,
                unusableHoldPiece: this.unusableHoldPiece
            });
            square3 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: L,
                ghost: this.ghost,
                unusableHoldPiece: this.unusableHoldPiece
            });
            square4 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: L,
                ghost: this.ghost,
                unusableHoldPiece: this.unusableHoldPiece
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
        case LEFT:
            square1 = new SingleSquare({
                x: this.x,
                y: this.y,
                piece: L,
                ghost: this.ghost
            });
            square2 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y,
                piece: L,
                ghost: this.ghost
            });
            square3 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: L,
                ghost: this.ghost
            });
            square4 = new SingleSquare({
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
            square1 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y,
                piece: L,
                ghost: this.ghost
            });
            square2 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: L,
                ghost: this.ghost
            });
            square3 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + 2 * tetris.unitSize, 
                piece: L,
                ghost: this.ghost
            });
            square4 = new SingleSquare({
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
            square1 = new SingleSquare({
                x: this.x, 
                y: this.y + tetris.unitSize, 
                piece: L,
                ghost: this.ghost
            });
            square2 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: L,
                ghost: this.ghost
            });
            square3 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: L,
                ghost: this.ghost
            });
            square4 = new SingleSquare({
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
    var square1;
    var square2;
    var square3;
    var square4;
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
            square1 = new SingleSquare({
                x: this.x, 
                y: this.y, 
                piece: J, 
                ghost: this.ghost,
                unusableHoldPiece: this.unusableHoldPiece
            });
            square2 = new SingleSquare({
                x: this.x, 
                y: this.y + tetris.unitSize,
                piece: J,
                ghost: this.ghost,
                unusableHoldPiece: this.unusableHoldPiece
            });
            square3 = new SingleSquare({
                x: this.x + tetris.unitSize,
                y: this.y + tetris.unitSize, 
                piece: J,
                ghost: this.ghost,
                unusableHoldPiece: this.unusableHoldPiece
            });
            square4 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: J,
                ghost: this.ghost,
                unusableHoldPiece: this.unusableHoldPiece
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
        case LEFT:
            square1 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y,
                piece: J, 
                ghost: this.ghost
            });
            square2 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: J,
                ghost: this.ghost
            });
            square3 = new SingleSquare({
                x: this.x, 
                y: this.y + 2 * tetris.unitSize,
                piece: J,
                ghost: this.ghost
            });
            square4 = new SingleSquare({
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
            square1 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y, 
                piece: J, 
                ghost: this.ghost
            });
            square2 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y,
                piece: J,
                ghost: this.ghost
            });
            square3 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: J,
                ghost: this.ghost
            });
            square4 = new SingleSquare({
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
            square1 = new SingleSquare({
                x: this.x, 
                y: this.y + tetris.unitSize, 
                piece: J, 
                ghost: this.ghost
            });
            square2 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: J,
                ghost: this.ghost
            });
            square3 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize,
                y: this.y + tetris.unitSize, 
                piece: J,
                ghost: this.ghost
            });
            square4 = new SingleSquare({
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
    var square1;
    var square2;
    var square3;
    var square4;
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
            square1 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y,
                piece: T,
                ghost: this.ghost,
                unusableHoldPiece: this.unusableHoldPiece
            });
            square2 = new SingleSquare({
                x: this.x, 
                y: this.y + tetris.unitSize, 
                piece: T,
                ghost: this.ghost,
                unusableHoldPiece: this.unusableHoldPiece
            });
            square3 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: T,
                ghost: this.ghost,
                unusableHoldPiece: this.unusableHoldPiece
            });
            square4 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: T,
                ghost: this.ghost,
                unusableHoldPiece: this.unusableHoldPiece
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
        case LEFT:
            square1 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y, 
                piece: T,
                ghost: this.ghost
            });
            square2 = new SingleSquare({
                x: this.x, 
                y: this.y + tetris.unitSize, 
                piece: T,
                ghost: this.ghost
            });
            square3 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: T,
                ghost: this.ghost
            });
            square4 = new SingleSquare({
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
            square1 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y,
                piece: T,
                ghost: this.ghost
            });
            square2 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: T,
                ghost: this.ghost
            });
            square3 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: T,
                ghost: this.ghost
            });
            square4 = new SingleSquare({
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
            square1 = new SingleSquare({
                x: this.x, 
                y: this.y + tetris.unitSize, 
                piece: T,
                ghost: this.ghost
            });
            square2 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: T,
                ghost: this.ghost
            });
            square3 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize,
                y: this.y + tetris.unitSize, 
                piece: T,
                ghost: this.ghost
            });
            square4 = new SingleSquare({
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
    var square1;
    var square2;
    var square3;
    var square4;
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
            square1 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y,
                piece: S,
                ghost: this.ghost,
                unusableHoldPiece: this.unusableHoldPiece
            });
            square2 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y, 
                piece: S,
                ghost: this.ghost,
                unusableHoldPiece: this.unusableHoldPiece
            });
            square3 = new SingleSquare({
                x: this.x, 
                y: this.y + tetris.unitSize, 
                piece: S,
                ghost: this.ghost,
                unusableHoldPiece: this.unusableHoldPiece
            });
            square4 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: S,
                ghost: this.ghost,
                unusableHoldPiece: this.unusableHoldPiece
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
        case LEFT:
            square1 = new SingleSquare({
                x: this.x,
                y: this.y,
                piece: S,
                ghost: this.ghost
            });
            square2 = new SingleSquare({
                x: this.x, 
                y: this.y + tetris.unitSize, 
                piece: S,
                ghost: this.ghost
            });
            square3 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: S,
                ghost: this.ghost
            });
            square4 = new SingleSquare({
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
            square1 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y, 
                piece: S,
                ghost: this.ghost
            });
            square2 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: S,
                ghost: this.ghost
            });
            square3 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: S,
                ghost: this.ghost
            });
            square4 = new SingleSquare({
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
            square1 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: S,
                ghost: this.ghost
            });
            square2 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize,
                y: this.y + tetris.unitSize, 
                piece: S,
                ghost: this.ghost
            });
            square3 = new SingleSquare({
                x: this.x, 
                y: this.y + 2 * tetris.unitSize, 
                piece: S,
                ghost: this.ghost
            });
            square4 = new SingleSquare({
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
    var square1;
    var square2;
    var square3;
    var square4;
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
            square1 = new SingleSquare({
                x: this.x, 
                y: this.y, 
                piece: Z,
                ghost: this.ghost,
                unusableHoldPiece: this.unusableHoldPiece
            });
            square2 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y,
                piece: Z,
                ghost: this.ghost,
                unusableHoldPiece: this.unusableHoldPiece
            });
            square3 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: Z,
                ghost: this.ghost,
                unusableHoldPiece: this.unusableHoldPiece
            });
            square4 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: Z,
                ghost: this.ghost,
                unusableHoldPiece: this.unusableHoldPiece
            });
            square1.draw(canvas);
            square2.draw(canvas);
            square3.draw(canvas);
            square4.draw(canvas);
            break;
        case LEFT:
            square1 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y, 
                piece: Z,
                ghost: this.ghost
            });
            square2 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            square3 = new SingleSquare({
                x: this.x, 
                y: this.y + tetris.unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            square4 = new SingleSquare({
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
            square1 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y, 
                piece: Z,
                ghost: this.ghost
            });
            square2 = new SingleSquare({
                x: this.x + 2 * tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            square3 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            square4 = new SingleSquare({
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
            square1 = new SingleSquare({
                x: this.x, 
                y: this.y + tetris.unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            square2 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + tetris.unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            square3 = new SingleSquare({
                x: this.x + tetris.unitSize, 
                y: this.y + 2 * tetris.unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            square4 = new SingleSquare({
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
    var square1;
    var square2;
    var square3;
    var square4;
    var ctx = canvas.getContext("2d");

    if (this.ghost) {
        ctx.fillStyle = GHOSTCOLOUR;
        ctx.strokeStyle = GHOSTCOLOUR;
    } else {
        ctx.fillStyle = this.fillStyle;
        ctx.strokeStyle = this.strokeStyle;
    }
    square1 = new SingleSquare({
        x: this.x + tetris.unitSize, 
        y: this.y, 
        piece: O,
        ghost: this.ghost,
        unusableHoldPiece: this.unusableHoldPiece
    });
    square2 = new SingleSquare({
        x: this.x + 2 * tetris.unitSize, 
        y: this.y,
        piece: O,
        ghost: this.ghost,
        unusableHoldPiece: this.unusableHoldPiece
    });
    square3 = new SingleSquare({
        x: this.x + tetris.unitSize, 
        y: this.y + tetris.unitSize,
        piece: O,
        ghost: this.ghost,
        unusableHoldPiece: this.unusableHoldPiece
    });
    square4 = new SingleSquare({
        x: this.x + 2 * tetris.unitSize, 
        y: this.y + tetris.unitSize,
        piece: O,
        ghost: this.ghost,
        unusableHoldPiece: this.unusableHoldPiece
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


// drawPieceInHold(canvas, piececode, cannotSpawnHeldPiece) 
// draws the corresponding piece in the hold box.

function drawPieceInHold(canvas, piececode, cannotSpawnHeldPiece) {
    var piece;
    clearPieceInHold();
    switch (piececode) {
        case I:
            piece = new IPiece({
                x: tetris.unitSize, 
                y: 3 * tetris.unitSize,
                unusableHoldPiece: cannotSpawnHeldPiece
            });
            break;
        case L:
            piece = new LPiece({
                x: tetris.unitSize, 
                y: 3 * tetris.unitSize,
                unusableHoldPiece: cannotSpawnHeldPiece
            });
            break;
        case J:
            piece = new JPiece({
                x: tetris.unitSize, 
                y: 3 * tetris.unitSize,
                unusableHoldPiece: cannotSpawnHeldPiece
            });
            break;
        case T:
            piece = new TPiece({
                x: tetris.unitSize, 
                y: 3 * tetris.unitSize,
                unusableHoldPiece: cannotSpawnHeldPiece
            });
            break;
        case S:
            piece = new SPiece({
                x: tetris.unitSize, 
                y: 3 * tetris.unitSize,
                unusableHoldPiece: cannotSpawnHeldPiece
            });
            break;
        case Z:
            piece = new ZPiece({
                x: tetris.unitSize, 
                y: 3 * tetris.unitSize,
                unusableHoldPiece: cannotSpawnHeldPiece
            });
            break;
        case O:
            piece = new OPiece({
                x: tetris.unitSize, 
                y: 3 * tetris.unitSize,
                unusableHoldPiece: cannotSpawnHeldPiece
            });
            break;
    }
    piece.draw(canvas);
}


// clearPieceInHold() erases the piece in the hold box.

function clearPieceInHold() {
    var canvas = document.getElementById("tetris-board");
    var ctx = canvas.getContext("2d");
    ctx.fillStyle = BACKGROUND;
    ctx.fillRect(tetris.unitSize - 2, 3 * tetris.unitSize - 2, 
        4 * tetris.unitSize + 4, 4 * tetris.unitSize + 4);
}


// clearBoard() clears the entire board.
function clearBoard() {
    var canvas = document.getElementById("tetris-board");
    var ctx = canvas.getContext("2d");
    ctx.fillStyle = BACKGROUND;
    ctx.fillRect(
        7 * tetris.unitSize, 
        2 * tetris.unitSize, 
        10 * tetris.unitSize, 
        20 * tetris.unitSize);
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
        default:
            document.getElementById("start-overlay").style.display = "none";
            document.getElementById("game-over-overlay").style.display = "none";
            $('#tetris-theme').trigger("play");
            clearBoard();
            clearPieceInHold();
            sendGameStart();
            tetris.gameActive = true;
            updateFrame();
    }
}

// Update the page after the game has ended. finalScore is the 
// score at the end of the game.
function end(finalScore) {
    document.getElementById("game-over-overlay").style.display = "block";
    $('#tetris-theme').trigger("pause");
    tetris.gameActive = false;
    if (tetris.timer != null) {
        clearTimeout(tetris.timer);
    }
    tetris.timer = null;
    $("#final-score").empty();
    $("#final-score").html("<p> Final score: " + String(finalScore) + "</p>");
}


// =================================================================
// Page management: Updating the drawing of the board
// =================================================================

// (Helper function) Updates the timer for timed drops and delayed locks.
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

// (Helper function) Draw the new piece in the hold box.
// body is the parsed body of the response from the server.
function updateHoldPiece(canvas, body) {
    var hold = String(body.hold);
    var cannotSpawnHeldPiece = body.sealHoldPiece;
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


// (Helper function) Draws the new pieces in the next queue.
// body is the parsed body of the response from the server.
function updateNextQueue(canvas, body) {
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
}

// (Helper function) Erases the previous hard drop ghost and piece in 
// play. 
function erasePreviousGhostAndCopyOfFallingPiece(canvas) {
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
}

/* 
(Helper function) Draws the squares of the Tetris 
stack detailed in body.drawOnStack.
Comments:
  Two ways to update the display of the Tetris 
  stack were considered: drawing only the changes, 
  and redrawing the entire board. Drawing only the 
  changes results in significantly smaller messages 
  sent to the browser. Redrawing the entire board
  enables recovery from/correction of graphical glitches 
  resulting from messages to the browser being received 
  out of order. (Aside: problems caused by messages being 
  received out of order aren't restricted 
  to graphics. For instance, if piece B is spawned following 
  piece A, delayed messaging could lead to a command from the 
  browser intended for piece A instead being received 
  by the program while piece B is in play).
*/
function updateTetrisStack(canvas, body) {
    var redraw = body.drawOnStack;
    var ypix;
    var xpix;
    var sq;
    for (var i = 0; i < redraw.length; i++) {
        ypix = (redraw[i].row - 18) * tetris.unitSize;
        xpix = (redraw[i].col + 7) * tetris.unitSize;
        switch(String(redraw[i].occupiedBy)) {
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
}

// (Helper function) (Re)draws the new hard drop ghost.
// body is the parsed body of the response from the server.
// Also updates tetris.previousSquaresOfHardDropGhost.
function updateNewHardDropGhost(canvas, body) {
    var ghost = body.squaresOfHardDropGhost;
    var ypix;
    var xpix;
    var sq;
    for (var i = 0; i < ghost.length; i++) {
        ypix = (ghost[i].row - 18) * tetris.unitSize;
        xpix = (ghost[i].col + 7) * tetris.unitSize;
        sq = new SingleSquare({x: xpix, y: ypix, ghost: true});
        sq.draw(canvas);
    }
    tetris.previousSquaresOfHardDropGhost = ghost;
}


// (Helper function) Draws the new copy of the piece in play.
// body is the parsed body of the response from the server.
// Also updates tetris.previousSquaresOfPieceInPlay.
function updateNewCopyOfPieceInPlay(canvas, body) {
    var name = String(body.pieceInPlay);
    var pieceSquares = body.squaresOfPieceInPlay;
    var pieceColour;
    var ypix;
    var xpix;
    var sq;
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
        ypix = (pieceSquares[i].row - 18) * tetris.unitSize;
        xpix = (pieceSquares[i].col + 7) * tetris.unitSize;
        sq = new SingleSquare({x: xpix, y: ypix, piece: pieceColour});
        sq.draw(canvas);
    }
    tetris.previousSquaresOfPieceInPlay = pieceSquares;
}


/*
(Helper function for drawMessages) Erases the message on row r.
*/
function eraseMessageOnRow(canvas, r) {
    var ctx = canvas.getContext("2d");
    ctx.fillStyle = BACKGROUND;
    ctx.fillRect(
        tetris.unitSize - 2, 
        (9 + r) * tetris.unitSize + Math.floor(tetris.unitSize / 3), 
        5 * tetris.unitSize + 4,
        tetris.unitSize);
}


/* 
(Helper function for drawMessages) Draws the message on row r.
The message disappears after 1000 milliseconds.
*/
function drawMessageOnRow(canvas, message, r) {
    var ctx = canvas.getContext("2d");

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
    }
}

/*
(Helper function) Draws messages. Informs the player of
* tspins and back-to-back tspins
* tetrises and back-to-back tetrises
* perfect clears
* line clear combos
*/
function drawMessages(canvas, body) {
    var tspinMessage = "T-SPIN";
    var singleMessage = "SINGLE";
    var doubleMessage = "DOUBLE";
    var tripleMessage = "TRIPLE";
    var tetrisMessage = "TETRIS";
    var backtobackMessage = "B2B";
    var allClearMessage = "ALL CLEAR"
    var comboMessage = "COMBO";
    var tspinRow = 0;
    var linesRow = 1;
    var backtobackRow = 2;
    var allClearRow = 3;
    var comboRow = 4;

    var isTSpin;
    var linesCleared;
    var backtobacks;
    var isPerfectClear;
    var combo;
    
    if (body.lineClearInfo != null) {

        isTSpin = body.lineClearInfo.tspin;
        linesCleared = body.lineClearInfo.linesCleared;
        backtobacks = body.lineClearInfo.consecTetrisOrTSpin;
        isPerfectClear = body.lineClearInfo.perfectClear;
        combo = body.lineClearInfo.combo;
        
        if (isTSpin) {
            drawMessageOnRow(canvas, tspinMessage, tspinRow);
            switch(linesCleared) {
                case 1:
                    drawMessageOnRow(canvas, singleMessage, linesRow);
                    break;
                case 2:
                    drawMessageOnRow(canvas, doubleMessage, linesRow);
                    break;
                case 3:
                    drawMessageOnRow(canvas, tripleMessage, linesRow);
                    break;
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
    }
}
/*
(Helper function) Updates the score.
*/
function showMostRecentScore(body) {
    $("#score").empty();
    $("#score").html("<p> Score: " + String(body.score) + "</p>");
}


// Updates the board given a response from the server.
function updateBoard(response) {
    var canvas = document.getElementById("tetris-board");
    var body = JSON.parse(response.body);

    if (body.spawnUnsuccessful) {
        end(body.score);

    } else {
        updateTimer(body);
        updateHoldPiece(canvas, body);
        updateNextQueue(canvas, body);
        erasePreviousGhostAndCopyOfFallingPiece(canvas);
        updateTetrisStack(canvas, body);
        updateNewHardDropGhost(canvas, body);
        updateNewCopyOfPieceInPlay(canvas, body);
        drawMessages(canvas, body);
        showMostRecentScore(body);
    }
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


// Sends the automatic-timed-fall command to the server.
function sendTimedFall() {
    tetris.stompClient.send(
        "/app/timed-fall",
        {},
        JSON.stringify({"keyCommand": "NOTHING"})
    );
}


// Sends the automatic-delayed-lock command to the server.
function sendTimedLock() {
    tetris.stompClient.send(
        "/app/timed-lock",
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

// Sends the sonic drop command to the server.
function sendSonicDrop() {
    tetris.stompClient.send(
        "/app/sonic-drop",
        {},
        JSON.stringify({"keyCommand": "SONICDROP"})
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
                    sendSonicDrop();
                    /*
                    var stop = setInterval(
                        function() { 
                            sendSoftDrop(); 
                        }, 
                        250);
                    $(window).on(
                        "keyup", 
                        function(e) { 
                            clearInterval(stop); 
                        });
                        */
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
