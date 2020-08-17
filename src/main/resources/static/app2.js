var stompClient = null;
var unitSize = null;
var gameActive = false;

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
    }

    // 10x2 spawn field
    if (!( this.y < 2 * unitSize ))
    {
        ctx.fillRect(this.x, this.y, unitSize, unitSize);
        ctx.strokeRect(this.x, this.y, unitSize, unitSize);
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
                y: this.y + unitSize,
                piece: I,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + unitSize,
                y: this.y + unitSize,
                piece: I,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + 2 * unitSize,
                y: this.y + unitSize,
                piece: I,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + 3 * unitSize,
                y: this.y + unitSize,
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
                x: this.x + 2 * unitSize, 
                y: this.y,
                piece: I,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + 2 * unitSize, 
                y: this.y + unitSize,
                piece: I,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + 2 * unitSize, 
                y: this.y + 2 * unitSize,
                piece: I,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + 2 * unitSize, 
                y: this.y + 3 * unitSize,
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
                x: this.x + unitSize, 
                y: this.y, 
                piece: I,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + unitSize,
                piece: I,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + 2 * unitSize,
                piece: I,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + 3 * unitSize,
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
                y: this.y + 2 * unitSize,
                piece: I,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + 2 * unitSize, 
                piece: I,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + 2 * unitSize, 
                y: this.y + 2 * unitSize,
                piece: I,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + 3 * unitSize, 
                y: this.y + 2 * unitSize,
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
                x: this.x + 2 * unitSize, 
                y: this.y,
                piece: L,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x, 
                y: this.y + unitSize,
                piece: L,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + unitSize, 
                piece: L,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + 2 * unitSize, 
                y: this.y + unitSize, 
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
                x: this.x + unitSize, 
                y: this.y,
                piece: L,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + unitSize, 
                piece: L,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + 2 * unitSize, 
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
                x: this.x + unitSize, 
                y: this.y,
                piece: L,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + unitSize, 
                piece: L,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + 2 * unitSize, 
                piece: L,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + 2 * unitSize, 
                y: this.y + 2 * unitSize, 
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
                y: this.y + unitSize, 
                piece: L,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + unitSize, 
                piece: L,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + 2 * unitSize, 
                y: this.y + unitSize, 
                piece: L,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x, 
                y: this.y + 2 * unitSize,
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
                y: this.y + unitSize,
                piece: J,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + unitSize,
                y: this.y + unitSize, 
                piece: J,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + 2 * unitSize, 
                y: this.y + unitSize, 
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
                x: this.x + unitSize, 
                y: this.y,
                piece: J, 
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + unitSize, 
                piece: J,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x, 
                y: this.y + 2 * unitSize,
                piece: J,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + 2 * unitSize, 
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
                x: this.x + unitSize, 
                y: this.y, 
                piece: J, 
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + 2 * unitSize, 
                y: this.y,
                piece: J,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + unitSize, 
                piece: J,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + unitSize,
                y: this.y + 2 * unitSize, 
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
                y: this.y + unitSize, 
                piece: J, 
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + unitSize, 
                piece: J,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + 2 * unitSize,
                y: this.y + unitSize, 
                piece: J,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + 2 * unitSize, 
                y: this.y + 2 * unitSize, 
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
                x: this.x + unitSize, 
                y: this.y,
                piece: T,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x, 
                y: this.y + unitSize, 
                piece: T,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + unitSize, 
                piece: T,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + 2 * unitSize, 
                y: this.y + unitSize, 
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
                x: this.x + unitSize, 
                y: this.y, 
                piece: T,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x, 
                y: this.y + unitSize, 
                piece: T,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + unitSize, 
                piece: T,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + 2 * unitSize, 
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
                x: this.x + unitSize, 
                y: this.y,
                piece: T,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + unitSize, 
                piece: T,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + 2 * unitSize, 
                y: this.y + unitSize, 
                piece: T,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + 2 * unitSize,
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
                y: this.y + unitSize, 
                piece: T,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + unitSize, 
                piece: T,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + 2 * unitSize,
                y: this.y + unitSize, 
                piece: T,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + 2 * unitSize, 
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
                x: this.x + unitSize, 
                y: this.y,
                piece: S,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + 2 * unitSize, 
                y: this.y, 
                piece: S,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x, 
                y: this.y + unitSize, 
                piece: S,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + unitSize, 
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
                y: this.y + unitSize, 
                piece: S,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + unitSize, 
                piece: S,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + 2 * unitSize, 
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
                x: this.x + unitSize, 
                y: this.y, 
                piece: S,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + unitSize, 
                piece: S,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + 2 * unitSize, 
                y: this.y + unitSize, 
                piece: S,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + 2 * unitSize, 
                y: this.y + 2 * unitSize, 
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
                x: this.x + unitSize, 
                y: this.y + unitSize, 
                piece: S,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + 2 * unitSize,
                y: this.y + unitSize, 
                piece: S,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x, 
                y: this.y + 2 * unitSize, 
                piece: S,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + unitSize,
                y: this.y + 2 * unitSize, 
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
                x: this.x + unitSize, 
                y: this.y,
                piece: Z,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + 2 * unitSize, 
                y: this.y + unitSize, 
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
                x: this.x + unitSize, 
                y: this.y, 
                piece: Z,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x, 
                y: this.y + unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x, 
                y: this.y + 2 * unitSize, 
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
                x: this.x + 2 * unitSize, 
                y: this.y, 
                piece: Z,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + 2 * unitSize, 
                y: this.y + unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + 2 * unitSize, 
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
                y: this.y + unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            var square2 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            var square3 = new SingleSquare({
                x: this.x + unitSize, 
                y: this.y + 2 * unitSize, 
                piece: Z,
                ghost: this.ghost
            });
            var square4 = new SingleSquare({
                x: this.x + 2 * unitSize, 
                y: this.y + 2 * unitSize, 
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
        x: this.x + unitSize, 
        y: this.y, 
        piece: O,
        ghost: this.ghost
    });
    var square2 = new SingleSquare({
        x: this.x + 2 * unitSize, 
        y: this.y,
        piece: O,
        ghost: this.ghost
    });
    var square3 = new SingleSquare({
        x: this.x + unitSize, 
        y: this.y + unitSize,
        piece: O,
        ghost: this.ghost
    });
    var square4 = new SingleSquare({
        x: this.x + 2 * unitSize, 
        y: this.y + unitSize,
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
    ctx.strokeText("NEXT", 19 * unitSize, Math.floor(unitSize * 3 / 2));

    ctx.strokeRect(18 * unitSize, 2 * unitSize, 6 * unitSize, 17 * unitSize);
}


// drawPieceInQueuePosition(canvas, piececode, pos) draws the piece 
// represented by piececode (constant I, J, L, T, S, Z, O)
// in the position pos (int: 1, 2, 3, 4, 5) in the next-piece queue.

function drawPieceInQueuePosition(canvas, piececode, pos) {
    var xpix = 19 * unitSize;
    var ypix = 3 * unitSize + 3 * unitSize * (pos - 1);
    var piece;
    
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
    var xpix = 19 * unitSize;
    var ypix = 3 * unitSize + 3 * unitSize * (pos - 1);

    ctx.fillStyle = BACKGROUND;
    ctx.fillRect(xpix - 2, ypix - 2, 4 * unitSize + 4, 3 * unitSize + 2);
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
    ctx.fillStyle = BACKGROUND;
    ctx.fillRect(unitSize - 2, 3 * unitSize - 2, 
        4 * unitSize + 4, 4 * unitSize + 4);
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
    unitSize = Math.floor(canvas.clientHeight / 26);
    canvas.width = canvas.clientWidth;
    canvas.height = canvas.clientHeight;

    initHoldBox(canvas, unitSize);
    initBoard(canvas, unitSize);
    initNextQueue(canvas, unitSize);

}

// ========================================================================
// Page management
// ========================================================================

// connect() connects to the server.

function connect() {
    var socket = new SockJS('/tetris');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/board-update', function (response) {
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
            gameActive = true;
    }
}



// Updates board given a response from the server.

function updateBoard(response) {
    // temp
    // just for acknowledgement; will do more detailed updates 
    // after server logic is more fleshed out
    var canvas = document.getElementById("tetris-board");
    var ctx = canvas.getContext("2d");
    ctx.clearRect(0, 23 * unitSize, 24 * unitSize, unitSize);
    ctx.strokeText("Acknowledged: {}".replace("{}", window.performance.now()),
        0, 24 * unitSize);
}


// Handles the animation loop.

function updateFrame() {
    if (gameActive) {
        window.requestAnimationFrame(updateFrame);
    }
}


// Sends the hard drop command to the server.

function sendDown() {
    stompClient.send(
        "/app/key-event",
        {},
        JSON.stringify({"keyCommand": "HARDDROP"})
    );
}

// Sends the soft drop command to the server.

function sendUp() {
    stompClient.send(
        "/app/key-event",
        {},
        JSON.stringify({"keyCommand": "SOFTDROP"})
    );
}

// Sends the move-left command to the server.

function sendLeft() {
    stompClient.send(
        "/app/key-event",
        {},
        JSON.stringify({"keyCommand": "LEFT"})
    );
}

// Sends the move-right command to the server.

function sendRight() {
    stompClient.send(
        "/app/key-event",
        {},
        JSON.stringify({"keyCommand": "RIGHT"})
    );
}

// Sends the rotate-clockwise command to the server.

function sendClockwise() {
    stompClient.send(
        "/app/key-event",
        {},
        JSON.stringify({"keyCommand": "CLOCKWISE"})
    );
}

// Sends the rotate-counterclockwise command to the server.

function sendCounterClockwise() {
    stompClient.send(
        "/app/key-event",
        {},
        JSON.stringify({"keyCommand": "COUNTERCLOCKWISE"})
    );
}

// Sends the hold command to the server.

function sendHold() {
    stompClient.send(
        "/app/key-event",
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
        if (!gameActive) {
            start();
        }
    });
    $( document ).keydown(function(event) {
        if (!gameActive) {
            start(event.which);

        } else {
            switch(event.which) {
                case 38:
                event.preventDefault();
                var stop = setInterval(function() { sendUp(); }, 50);
                $(window).on("keyup", function(e) { clearInterval(stop); });
                break;
            case 40:
                event.preventDefault();
                sendDown();
                break;
            case 37:
                event.preventDefault();
                sendLeft();
                break;
            case 39:
                event.preventDefault();
                sendRight();
                break;
            case 67:
                event.preventDefault();
                sendClockwise();
                break;
            case 88:
                event.preventDefault();
                sendCounterClockwise();
                break;
            case 90:
                event.preventDefault();
                sendHold();
                break;
            }
        }
    });
    
});
