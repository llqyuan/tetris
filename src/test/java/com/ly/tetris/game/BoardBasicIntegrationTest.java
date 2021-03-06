package com.ly.tetris.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.ListIterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.ly.tetris.infostructs.LocationPosn;
import com.ly.tetris.infostructs.OffsetPosn;
import com.ly.tetris.infostructs.PieceOrientation;
import com.ly.tetris.infostructs.PieceName;
import com.ly.tetris.infostructs.RotationDirection;

public class BoardBasicIntegrationTest {
    Board board;

    @BeforeEach
    public void initialize() {
        this.board = new Board();
    }

    // ==========================================
    // Spawning
    // ==========================================

    @Test
    public void noPieceInPlayBeforeSpawning() {
        ArrayList<LocationPosn> expected = new ArrayList<LocationPosn>();
        assertEquals(PieceName.NOTHING, board.pieceInPlay(),
            "Piece in play is not NOTHING before spawning.");
        this.assertIsPermutation(
            expected, board.squaresOccupiedByPieceInPlay());
    }

    @Test
    public void noHardDropGhostBeforeSpawning() {
        ArrayList<LocationPosn> expected = new ArrayList<LocationPosn>();
        this.assertIsPermutation(
            expected, board.squaresOccupiedByHardDropGhost());
    }

    @Test
    public void spawningUnsuccessfulIfAndOnlyIfNoRoom() {
        ArrayList<LocationPosn> occupy = new ArrayList<LocationPosn>();
        occupy.add(new LocationPosn(19, 5));
        board = new Board(occupy);
        assertFalse(board.spawn(PieceName.I),
                    "Spawned I piece on a board where (19, 5) was occupied.");
        assertEquals(PieceName.NOTHING, board.pieceInPlay());
        board = new Board(occupy);
        assertTrue(board.spawn(PieceName.S));
    }

    @Test
    public void pieceInPlayIsIAfterSpawningI() {
        board.spawn(PieceName.I);
        assertEquals(PieceName.I, board.pieceInPlay(),
            "Piece in play is not I when an I piece was spawned.");
    }

    @Test
    public void pieceInPlayIsJAfterSpawningJ() {
        board.spawn(PieceName.J);
        assertEquals(PieceName.J, board.pieceInPlay(),
            "Piece in play is not J when a J piece was spawned.");
    }

    @Test
    public void pieceInPlayIsLAfterSpawningL() {
        board.spawn(PieceName.L);
        assertEquals(PieceName.L, board.pieceInPlay(), 
            "Piece in play is not L when an L piece was spawned.");
    }

    @Test
    public void pieceInPlayIsOAfterSpawningO() {
        board.spawn(PieceName.O);
        assertEquals(PieceName.O, board.pieceInPlay(),
            "Piece in play is not O when an O piece was spawned.");
    }

    @Test
    public void pieceInPlayIsSAfterSpawningS() {
        board.spawn(PieceName.S);
        assertEquals(PieceName.S, board.pieceInPlay(),
            "Piece in play is not S when an S piece was spawned.");
    }
       
    @Test
    public void pieceInPlayIsTAfterSpawningT() {
        board.spawn(PieceName.T);
        assertEquals(PieceName.T, board.pieceInPlay(),
            "Piece in play is not T when a T piece was spawned.");
    }

    @Test
    public void pieceInPlayIsZAfterSpawningZ() {
        board.spawn(PieceName.Z);
        assertEquals(PieceName.Z, board.pieceInPlay(),
            "Piece in play is not Z when a Z piece was spawned.");
    }

    @Test
    public void iPieceOccupiesCorrectSpotsAfterSpawning() {
        ArrayList<LocationPosn> expected = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> actual;

        board.spawn(PieceName.I);
        actual = board.squaresOccupiedByPieceInPlay();
        expected.add(new LocationPosn(19, 3));
        expected.add(new LocationPosn(19, 4));
        expected.add(new LocationPosn(19, 5));
        expected.add(new LocationPosn(19, 6));
        this.assertIsPermutation(expected, actual);
    }

    @Test
    public void lPieceOccupiesCorrectSpotsAfterSpawning() {
        ArrayList<LocationPosn> expected = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> actual;

        board.spawn(PieceName.L);
        actual = board.squaresOccupiedByPieceInPlay();
        expected.add(new LocationPosn(18, 5));
        expected.add(new LocationPosn(19, 3));
        expected.add(new LocationPosn(19, 4));
        expected.add(new LocationPosn(19, 5));
        this.assertIsPermutation(expected, actual);
    }

    @Test
    public void jPieceOccupiesCorrectSpotsAfterSpawning() {
        ArrayList<LocationPosn> expected = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> actual;

        board.spawn(PieceName.J);
        actual = board.squaresOccupiedByPieceInPlay();
        expected.add(new LocationPosn(18, 3));
        expected.add(new LocationPosn(19, 3));
        expected.add(new LocationPosn(19, 4));
        expected.add(new LocationPosn(19, 5));
        this.assertIsPermutation(expected, actual);
    }

    @Test
    public void oPieceOccupiesCorrectSpotsAfterSpawning() {
        ArrayList<LocationPosn> expected = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> actual;

        board.spawn(PieceName.O);
        actual = board.squaresOccupiedByPieceInPlay();
        expected.add(new LocationPosn(18, 4));
        expected.add(new LocationPosn(18, 5));
        expected.add(new LocationPosn(19, 4));
        expected.add(new LocationPosn(19, 5));
        this.assertIsPermutation(expected, actual);
    }

    @Test
    public void sPieceOccupiesCorrectSpotsAfterSpawning() {
        ArrayList<LocationPosn> expected = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> actual;

        board.spawn(PieceName.S);
        actual = board.squaresOccupiedByPieceInPlay();
        expected.add(new LocationPosn(18, 4));
        expected.add(new LocationPosn(18, 5));
        expected.add(new LocationPosn(19, 3));
        expected.add(new LocationPosn(19, 4));
        this.assertIsPermutation(expected, actual);
    }

    @Test
    public void tPieceOccupiesCorrectSpotsAfterSpawning() {
        ArrayList<LocationPosn> expected = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> actual;

        board.spawn(PieceName.T);
        actual = board.squaresOccupiedByPieceInPlay();
        expected.add(new LocationPosn(18, 4));
        expected.add(new LocationPosn(19, 3));
        expected.add(new LocationPosn(19, 4));
        expected.add(new LocationPosn(19, 5));
        this.assertIsPermutation(expected, actual);
    }

    @Test
    public void zPieceOccupiesCorrectSpotsAfterSpawning() {
        ArrayList<LocationPosn> expected = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> actual;

        board.spawn(PieceName.Z);
        actual = board.squaresOccupiedByPieceInPlay();
        expected.add(new LocationPosn(18, 3));
        expected.add(new LocationPosn(18, 4));
        expected.add(new LocationPosn(19, 4));
        expected.add(new LocationPosn(19, 5));
        this.assertIsPermutation(expected, actual);
    }

    // ==========================================
    // Dropping
    // ==========================================

    @Test
    public void droppingRemovesPieceFromPlay() {
        board.spawn(PieceName.I);
        board.hardDrop();
        assertEquals(PieceName.NOTHING, board.pieceInPlay(),
            "Piece in play is not NOTHING after dropping first piece.");
        board.spawn(PieceName.O);
        board.hardDrop();
        assertEquals(PieceName.NOTHING, board.pieceInPlay(),
            "Piece in play is not NOTHING after dropping second piece.");
    }

    @Test
    public void 
    droppingIInEmptyBoardOccupiesHardDropGhostSquares() {
        ArrayList<LocationPosn> expected;
        ArrayList<LocationPosn> actual;

        board.spawn(PieceName.I);
        expected = board.squaresOccupiedByHardDropGhost();
        board.hardDrop();
        actual = board.squaresOccupiedByStack();
        this.assertIsPermutation(expected, actual);
    }

    @Test
    public void 
    droppingJInEmptyBoardOccupiesHardDropGhostSquares() {
        ArrayList<LocationPosn> expected;
        ArrayList<LocationPosn> actual;

        board.spawn(PieceName.J);
        expected = board.squaresOccupiedByHardDropGhost();
        board.hardDrop();
        actual = board.squaresOccupiedByStack();
        this.assertIsPermutation(expected, actual);
    }

    @Test
    public void 
    droppingLInEmptyBoardOccupiesHardDropGhostSquares() {
        ArrayList<LocationPosn> expected;
        ArrayList<LocationPosn> actual;

        board.spawn(PieceName.L);
        expected = board.squaresOccupiedByHardDropGhost();
        board.hardDrop();
        actual = board.squaresOccupiedByStack();
        this.assertIsPermutation(expected, actual);
    }

    @Test
    public void 
    droppingOInEmptyBoardOccupiesHardDropGhostSquares() {
        ArrayList<LocationPosn> expected;
        ArrayList<LocationPosn> actual;

        board.spawn(PieceName.O);
        expected = board.squaresOccupiedByHardDropGhost();
        board.hardDrop();
        actual = board.squaresOccupiedByStack();
        this.assertIsPermutation(expected, actual);
    }

    @Test
    public void 
    droppingSInEmptyBoardOccupiesHardDropGhostSquares() {
        ArrayList<LocationPosn> expected;
        ArrayList<LocationPosn> actual;

        board.spawn(PieceName.S);
        expected = board.squaresOccupiedByHardDropGhost();
        board.hardDrop();
        actual = board.squaresOccupiedByStack();
        this.assertIsPermutation(expected, actual);
    }

    @Test
    public void 
    droppingTInEmptyBoardOccupiesHardDropGhostSquares() {
        ArrayList<LocationPosn> expected;
        ArrayList<LocationPosn> actual;

        board.spawn(PieceName.T);
        expected = board.squaresOccupiedByHardDropGhost();
        board.hardDrop();
        actual = board.squaresOccupiedByStack();
        this.assertIsPermutation(expected, actual);
    }

    @Test
    public void 
    droppingZInEmptyBoardOccupiesHardDropGhostSquares() {
        ArrayList<LocationPosn> expected;
        ArrayList<LocationPosn> actual;

        board.spawn(PieceName.Z);
        expected = board.squaresOccupiedByHardDropGhost();
        board.hardDrop();
        actual = board.squaresOccupiedByStack();
        this.assertIsPermutation(expected, actual);
    }

    @Test
    public void droppingInNonemptyBoardOccupiesHardDropGhostSquares() {
        ArrayList<LocationPosn> expected;
        ArrayList<LocationPosn> actual;

        board.spawn(PieceName.O);
        expected = board.squaresOccupiedByHardDropGhost();
        board.hardDrop();
        board.spawn(PieceName.J);
        expected.addAll(board.squaresOccupiedByHardDropGhost());
        board.hardDrop();
        actual = board.squaresOccupiedByStack();
        this.assertIsPermutation(expected, actual);

    }

    // =============================================
    // Rotating on the spot in empty board 

    // (sanity check; the bulk of the testing was already done
    // in the piece classes)
    // ==============================================

    @Test
    public void pieceRotates() {
        board.spawn(PieceName.T);
        assertEquals(
            PieceOrientation.UPRIGHT, board.orientationOfPieceInPlay(),
            "Orientation of spawning piece isn't upright.");
        board.rotate(RotationDirection.CLOCKWISE);
        assertEquals(
            PieceOrientation.RIGHT, board.orientationOfPieceInPlay(),
            "Orientation isn't RIGHT after rotating clockwise once.");
        board.rotate(RotationDirection.CLOCKWISE);
        assertEquals(
            PieceOrientation.UPSIDEDOWN, board.orientationOfPieceInPlay(),
            "Orientation isn't UPSIDEDOWN after rotating clockwise twice.");
        board.rotate(RotationDirection.CLOCKWISE);
        assertEquals(
            PieceOrientation.LEFT, board.orientationOfPieceInPlay(),
            "Orientation isn't LEFT after rotating clockwise three times.");
        board.rotate(RotationDirection.CLOCKWISE);
        assertEquals(
            PieceOrientation.UPRIGHT, board.orientationOfPieceInPlay(), 
            "Orientation isn't UPRIGHT after rotating clockwise four times.");
        board.rotate(RotationDirection.COUNTERCLOCKWISE);
        assertEquals(
            PieceOrientation.LEFT, board.orientationOfPieceInPlay(),
            "Orientation isn't LEFT after rotating counterclockwise once.");
        board.rotate(RotationDirection.COUNTERCLOCKWISE);
        assertEquals(
            PieceOrientation.UPSIDEDOWN, board.orientationOfPieceInPlay(),
            "Orientation isn't UPSIDEDOWN after rotating counterclockwise " + 
            "twice.");
        board.rotate(RotationDirection.COUNTERCLOCKWISE);
        assertEquals(
            PieceOrientation.RIGHT, board.orientationOfPieceInPlay(), 
            "Orientation isn't RIGHT after rotating counterclockwise " + 
            "three times.");
        board.rotate(RotationDirection.COUNTERCLOCKWISE);
        assertEquals(
            PieceOrientation.UPRIGHT, board.orientationOfPieceInPlay(),
            "Orientation isn't UPRIGHT after rotating counterclockwise " + 
            "four times.");
    }

    @Test
    public void iOccupiesCorrectSpotsAfterRotating() {
        ArrayList<LocationPosn> upright = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> right = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> upsidedown = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> left = new ArrayList<LocationPosn>();

        upright.add(new LocationPosn(18 + 1, 3 + 0));
        upright.add(new LocationPosn(18 + 1, 3 + 1));
        upright.add(new LocationPosn(18 + 1, 3 + 2));
        upright.add(new LocationPosn(18 + 1, 3 + 3));

        right.add(new LocationPosn(18 + 0, 3 + 2));
        right.add(new LocationPosn(18 + 1, 3 + 2));
        right.add(new LocationPosn(18 + 2, 3 + 2));
        right.add(new LocationPosn(18 + 3, 3 + 2));

        upsidedown.add(new LocationPosn(18 + 2, 3 + 0));
        upsidedown.add(new LocationPosn(18 + 2, 3 + 1));
        upsidedown.add(new LocationPosn(18 + 2, 3 + 2));
        upsidedown.add(new LocationPosn(18 + 2, 3 + 3));

        left.add(new LocationPosn(18 + 0, 3 + 1));
        left.add(new LocationPosn(18 + 1, 3 + 1));
        left.add(new LocationPosn(18 + 2, 3 + 1));
        left.add(new LocationPosn(18 + 3, 3 + 1));

        board.spawn(PieceName.I);
        board.rotate(RotationDirection.CLOCKWISE);
        this.assertIsPermutation(right, board.squaresOccupiedByPieceInPlay());

        board.rotate(RotationDirection.CLOCKWISE);
        this.assertIsPermutation(upsidedown, board.squaresOccupiedByPieceInPlay());

        board.rotate(RotationDirection.CLOCKWISE);
        this.assertIsPermutation(left, board.squaresOccupiedByPieceInPlay());

        board.rotate(RotationDirection.CLOCKWISE);
        this.assertIsPermutation(upright, board.squaresOccupiedByPieceInPlay());
    }


    // ==============================================
    // Basic SRS kick tests
    // ==============================================

    @Test
    public void srsRotationKicksAnOutOfRangePieceBackInRange() {
        ArrayList<LocationPosn> expectedSpawn = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> expectedRotated = new ArrayList<LocationPosn>();
        expectedSpawn.add(new LocationPosn(39, 3));
        expectedSpawn.add(new LocationPosn(39, 4));
        expectedSpawn.add(new LocationPosn(39, 5));
        expectedSpawn.add(new LocationPosn(39, 6));
        expectedRotated.add(new LocationPosn(39, 6));
        expectedRotated.add(new LocationPosn(38, 6));
        expectedRotated.add(new LocationPosn(37, 6));
        expectedRotated.add(new LocationPosn(36, 6));

        assertTrue(board.spawn(PieceName.I, 38, 3));
        this.assertIsPermutation(
            expectedSpawn, board.squaresOccupiedByPieceInPlay());
        assertTrue(board.rotate(RotationDirection.CLOCKWISE));
        this.assertIsPermutation(
            expectedRotated, board.squaresOccupiedByPieceInPlay());

    }

    @Test
    public void srsRotationKicksAnObstructedPieceUsingMidSuggestion() {
        ArrayList<LocationPosn> preoccupy = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> expected = new ArrayList<LocationPosn>();
        preoccupy.add(new LocationPosn(20, 3));
        preoccupy.add(new LocationPosn(20, 4));
        expected.add(new LocationPosn(19, 3));
        expected.add(new LocationPosn(18, 3));
        expected.add(new LocationPosn(18, 4));
        expected.add(new LocationPosn(17, 3));

        board = new Board(preoccupy);
        this.assertIsPermutation(preoccupy, board.squaresOccupiedByStack());
        assertTrue(board.spawn(PieceName.T));
        assertTrue(board.rotate(RotationDirection.CLOCKWISE));
        this.assertIsPermutation(expected, board.squaresOccupiedByPieceInPlay());
    }

    @Test
    public void srsRotationKicksAnObstructedPieceUsingLastSuggestion() {
        ArrayList<LocationPosn> preoccupy = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> expected = new ArrayList<LocationPosn>();
        preoccupy.add(new LocationPosn(18, 2));
        preoccupy.add(new LocationPosn(18, 3));
        preoccupy.add(new LocationPosn(19, 2));
        preoccupy.add(new LocationPosn(20, 2));
        preoccupy.add(new LocationPosn(20, 4));
        preoccupy.add(new LocationPosn(20, 5));
        preoccupy.add(new LocationPosn(21, 2));
        preoccupy.add(new LocationPosn(21, 5));
        preoccupy.add(new LocationPosn(22, 2));
        preoccupy.add(new LocationPosn(22, 4));
        preoccupy.add(new LocationPosn(22, 5));
        expected.add(new LocationPosn(20, 3));
        expected.add(new LocationPosn(21, 3));
        expected.add(new LocationPosn(21, 4));
        expected.add(new LocationPosn(22, 3));

        board = new Board(preoccupy);
        this.assertIsPermutation(preoccupy, board.squaresOccupiedByStack());
        assertTrue(board.spawn(PieceName.T));
        assertTrue(board.rotate(RotationDirection.CLOCKWISE));
        this.assertIsPermutation(expected, board.squaresOccupiedByPieceInPlay());
    }

    // ==============================================
    // Moving left, right, or down (softdropping)
    // ==============================================

    @Test
    public void movingLeftUpdatesPieceInPlay() {
        assertTrue(board.spawn(PieceName.S));
        ArrayList<LocationPosn> occupiedBeforeMove = 
            board.squaresOccupiedByPieceInPlay();
        OffsetPosn leftOneSquare = new OffsetPosn(0, -1);
        ArrayList<LocationPosn> expectedAfterMove = 
            this.offsetList(occupiedBeforeMove, leftOneSquare);
        assertTrue(board.moveLeft());
        this.assertIsPermutation(
            expectedAfterMove, board.squaresOccupiedByPieceInPlay());
    }

    @Test
    public void movingRightUpdatesPieceInPlay() {
        assertTrue(board.spawn(PieceName.Z));
        ArrayList<LocationPosn> occupiedBeforeMove = 
            board.squaresOccupiedByPieceInPlay();
        OffsetPosn rightOneSquare = new OffsetPosn(0, 1);
        ArrayList<LocationPosn> expectedAfterMove = 
            this.offsetList(occupiedBeforeMove, rightOneSquare);
        assertTrue(board.moveRight());
        this.assertIsPermutation(
            expectedAfterMove, board.squaresOccupiedByPieceInPlay());
    }

    @Test
    public void moveLeftToBounds() {
        assertTrue(board.spawn(PieceName.L));
        ArrayList<LocationPosn> occupiedBeforeMove = 
            board.squaresOccupiedByPieceInPlay();
        OffsetPosn leftThree = new OffsetPosn(0, -3);
        ArrayList<LocationPosn> expectedAfterMoves = 
            this.offsetList(occupiedBeforeMove, leftThree);
        int countTimesLeft = 0;
        while (board.moveLeft() && countTimesLeft < 11) {
            countTimesLeft += 1;
        }
        assertTrue(
            countTimesLeft < 11, 
            "Able to move left 11+ times without hitting a wall.");
        assertEquals(3, countTimesLeft, 
            ("Newly spawned L piece moved left xxx times before no longer " + 
            "being able to move, expected 3.")
            .replaceFirst("xxx", Integer.toString(countTimesLeft)));
        this.assertIsPermutation(
            expectedAfterMoves, board.squaresOccupiedByPieceInPlay());
    }

    @Test
    public void moveRightToBounds() {
        assertTrue(board.spawn(PieceName.S));
        ArrayList<LocationPosn> occupiedBeforeMove = 
            board.squaresOccupiedByPieceInPlay();
        OffsetPosn rightFour = new OffsetPosn(0, 4);
        ArrayList<LocationPosn> expectedAfterMoves = 
            this.offsetList(occupiedBeforeMove, rightFour);
        int countTimesRight = 0;
        while (board.moveRight() && countTimesRight < 11) {
            countTimesRight += 1;
        }
        assertTrue(
            countTimesRight < 11, 
            "Able to move right 11+ times without hitting a wall.");
        assertEquals(4, countTimesRight, 
            ("Newly spawned S piece moved right xxx times before no longer " + 
            "being able to move, expected 4.")
            .replaceFirst("xxx", Integer.toString(countTimesRight)));
        this.assertIsPermutation(
            expectedAfterMoves, board.squaresOccupiedByPieceInPlay());
    }

    @Test
    public void moveLeftRightToStack() {
        ArrayList<LocationPosn> preoccupy = new ArrayList<LocationPosn>();
        preoccupy.add(new LocationPosn(19, 0));
        preoccupy.add(new LocationPosn(19, 8));
        preoccupy.add(new LocationPosn(18, 1));
        preoccupy.add(new LocationPosn(20, 7));

        board = new Board(preoccupy);
        assertTrue(board.spawn(PieceName.I));
        ArrayList<LocationPosn> occupiedBeforeMove = 
            board.squaresOccupiedByPieceInPlay();
        OffsetPosn leftTwo = new OffsetPosn(0, -2);
        OffsetPosn rightThree = new OffsetPosn(0, 3);

        ArrayList<LocationPosn> expected =
            this.offsetList(occupiedBeforeMove, leftTwo);
        int countMoved = 0;
        while (board.moveLeft() && countMoved < 11) {
            countMoved += 1;
        }
        assertTrue(
            countMoved < 11,
            "Able to move left 11+ times without hitting a wall.");
        assertEquals(2, countMoved,
            "Able to move I piece xxx times left, expected 2."
            .replaceFirst("xxx", Integer.toString(countMoved)));
        this.assertIsPermutation(
            expected, board.squaresOccupiedByPieceInPlay());

        expected = this.offsetList(expected, rightThree);
        countMoved = 0;
        while (board.moveRight() && countMoved < 11) {
            countMoved += 1;
        }
        assertTrue(
            countMoved < 11,
            "Able to move right 11+ times without hitting a wall.");
        assertEquals(3, countMoved,
            "Able to move I piece xxx times right, expected 3."
            .replaceFirst("xxx", Integer.toString(countMoved)));
        this.assertIsPermutation(
            expected, board.squaresOccupiedByPieceInPlay());
    }

    @Test
    public void movingDownUpdatesPieceInPlay() {
        assertTrue(board.spawn(PieceName.O));
        ArrayList<LocationPosn> occupiedBeforeMove = 
            board.squaresOccupiedByPieceInPlay();
        OffsetPosn downOne = new OffsetPosn(1, 0);
        ArrayList<LocationPosn> expectedAfterMove = 
            this.offsetList(occupiedBeforeMove, downOne);
        assertTrue(board.moveDown());
        this.assertIsPermutation(
            expectedAfterMove, board.squaresOccupiedByPieceInPlay());
    }

    @Test
    public void moveDownToBounds() {
        assertTrue(board.spawn(PieceName.Z));
        ArrayList<LocationPosn> occupiedBeforeMove =
            board.squaresOccupiedByPieceInPlay();
        OffsetPosn downTwenty = new OffsetPosn(20, 0);
        ArrayList<LocationPosn> expectedAfter = 
            this.offsetList(occupiedBeforeMove, downTwenty);
        
        int countTimes = 0;
        while (board.moveDown() && countTimes < 41) {
            countTimes += 1;
        }
        assertTrue(
            countTimes < 41, 
            "Able to move down 41+ times without reaching the ground.");
        assertEquals(
            20, countTimes, 
            "Moved Z piece xxx times down, expected 20."
            .replaceFirst("xxx", Integer.toString(countTimes)));
        this.assertIsPermutation(
            expectedAfter, board.squaresOccupiedByPieceInPlay());
    }

    @Test
    public void moveDownToStack() {
        ArrayList<LocationPosn> preoccupy = new ArrayList<LocationPosn>();
        preoccupy.add(new LocationPosn(38, 3));
        preoccupy.add(new LocationPosn(39, 3));
        preoccupy.add(new LocationPosn(39, 4));
        preoccupy.add(new LocationPosn(39, 5));
        board = new Board(preoccupy);

        assertTrue(board.spawn(PieceName.I));
        ArrayList<LocationPosn> occupiedBeforeMove =
            board.squaresOccupiedByPieceInPlay();
        OffsetPosn downEighteen = new OffsetPosn(18, 0);
        ArrayList<LocationPosn> expectedAfter = 
            this.offsetList(occupiedBeforeMove, downEighteen);
        
        int countTimes = 0;
        while (board.moveDown() && countTimes < 41) {
            countTimes += 1;
        }
        assertTrue(
            countTimes < 41, 
            "Able to move down 41+ times without reaching the ground.");
        assertEquals(
            18, countTimes, 
            "Moved I piece xxx times down, expected 18."
            .replaceFirst("xxx", Integer.toString(countTimes)));
        this.assertIsPermutation(
            expectedAfter, board.squaresOccupiedByPieceInPlay());
    }

    // ==============================================
    // Hard drop ghost updated correctly
    // ==============================================

    @Test
    public void hardDropGhostOnGroundAfterSpawningI() {
        ArrayList<LocationPosn> expected = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> actual;

        board.spawn(PieceName.I);
        actual = board.squaresOccupiedByHardDropGhost();
        expected.add(new LocationPosn(39, 3));
        expected.add(new LocationPosn(39, 4));
        expected.add(new LocationPosn(39, 5));
        expected.add(new LocationPosn(39, 6));
        this.assertIsPermutation(expected, actual);
    }

    @Test
    public void hardDropGhostOnGroundAfterSpawningL() {
        ArrayList<LocationPosn> expected = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> actual;

        board.spawn(PieceName.L);
        actual = board.squaresOccupiedByHardDropGhost();
        expected.add(new LocationPosn(38, 5));
        expected.add(new LocationPosn(39, 3));
        expected.add(new LocationPosn(39, 4));
        expected.add(new LocationPosn(39, 5));
        this.assertIsPermutation(expected, actual);
    }

    @Test
    public void hardDropGhostOnGroundAfterSpawningJ() {
        ArrayList<LocationPosn> expected = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> actual;

        board.spawn(PieceName.J);
        actual = board.squaresOccupiedByHardDropGhost();
        expected.add(new LocationPosn(38, 3));
        expected.add(new LocationPosn(39, 3));
        expected.add(new LocationPosn(39, 4));
        expected.add(new LocationPosn(39, 5));
        this.assertIsPermutation(expected, actual);
    }

    @Test
    public void hardDropGhostOnGroundAfterSpawningO() {
        ArrayList<LocationPosn> expected = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> actual;

        board.spawn(PieceName.O);
        actual = board.squaresOccupiedByHardDropGhost();
        expected.add(new LocationPosn(38, 4));
        expected.add(new LocationPosn(38, 5));
        expected.add(new LocationPosn(39, 4));
        expected.add(new LocationPosn(39, 5));
        this.assertIsPermutation(expected, actual);
    }

    @Test
    public void hardDropGhostOnGroundAfterSpawningS() {
        ArrayList<LocationPosn> expected = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> actual;

        board.spawn(PieceName.S);
        actual = board.squaresOccupiedByHardDropGhost();
        expected.add(new LocationPosn(38, 4));
        expected.add(new LocationPosn(38, 5));
        expected.add(new LocationPosn(39, 3));
        expected.add(new LocationPosn(39, 4));
        this.assertIsPermutation(expected, actual);
    }

    @Test
    public void hardDropGhostOnGroundAfterSpawningT() {
        ArrayList<LocationPosn> expected = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> actual;

        board.spawn(PieceName.T);
        actual = board.squaresOccupiedByHardDropGhost();
        expected.add(new LocationPosn(38, 4));
        expected.add(new LocationPosn(39, 3));
        expected.add(new LocationPosn(39, 4));
        expected.add(new LocationPosn(39, 5));
        this.assertIsPermutation(expected, actual);
    }

    @Test
    public void hardDropGhostOnGroundAfterSpawningZ() {
        ArrayList<LocationPosn> expected = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> actual;

        board.spawn(PieceName.Z);
        actual = board.squaresOccupiedByHardDropGhost();
        expected.add(new LocationPosn(38, 3));
        expected.add(new LocationPosn(38, 4));
        expected.add(new LocationPosn(39, 4));
        expected.add(new LocationPosn(39, 5));
        this.assertIsPermutation(expected, actual);
    }

    @Test
    public void hardDropGhostOnGroundAfterRotating() {
        ArrayList<LocationPosn> upright = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> left = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> right = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> down = new ArrayList<LocationPosn>();
        upright.add(new LocationPosn(38, 3));
        upright.add(new LocationPosn(39, 3));
        upright.add(new LocationPosn(39, 4));
        upright.add(new LocationPosn(39, 5));
        left.add(new LocationPosn(37, 4));
        left.add(new LocationPosn(38, 4));
        left.add(new LocationPosn(39, 4));
        left.add(new LocationPosn(39, 3));
        down.add(new LocationPosn(38, 3));
        down.add(new LocationPosn(38, 4));
        down.add(new LocationPosn(38, 5));
        down.add(new LocationPosn(39, 5));
        right.add(new LocationPosn(37, 4));
        right.add(new LocationPosn(37, 5));
        right.add(new LocationPosn(38, 4));
        right.add(new LocationPosn(39, 4));

        board.spawn(PieceName.J);
        assertTrue(board.rotate(RotationDirection.CLOCKWISE));
        this.assertIsPermutation(right, board.squaresOccupiedByHardDropGhost());

        assertTrue(board.rotate(RotationDirection.CLOCKWISE));
        this.assertIsPermutation(down, board.squaresOccupiedByHardDropGhost());

        assertTrue(board.rotate(RotationDirection.CLOCKWISE));
        this.assertIsPermutation(left, board.squaresOccupiedByHardDropGhost());

        assertTrue(board.rotate(RotationDirection.CLOCKWISE));
        this.assertIsPermutation(upright, board.squaresOccupiedByHardDropGhost());

        assertTrue(board.rotate(RotationDirection.COUNTERCLOCKWISE));
        this.assertIsPermutation(left, board.squaresOccupiedByHardDropGhost());

        assertTrue(board.rotate(RotationDirection.COUNTERCLOCKWISE));
        this.assertIsPermutation(down, board.squaresOccupiedByHardDropGhost());

        assertTrue(board.rotate(RotationDirection.COUNTERCLOCKWISE));
        this.assertIsPermutation(right, board.squaresOccupiedByHardDropGhost());

        assertTrue(board.rotate(RotationDirection.COUNTERCLOCKWISE));
        this.assertIsPermutation(upright, board.squaresOccupiedByHardDropGhost());
    }

    @Test
    public void hardDropGhostOnStackAfterSpawning() {
        ArrayList<LocationPosn> preoccupy = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> expected = new ArrayList<LocationPosn>();
        preoccupy.add(new LocationPosn(37, 4));
        preoccupy.add(new LocationPosn(38, 4));
        preoccupy.add(new LocationPosn(39, 4));
        preoccupy.add(new LocationPosn(39, 5));
        expected.add(new LocationPosn(36, 3));
        expected.add(new LocationPosn(36, 4));
        expected.add(new LocationPosn(36, 5));
        expected.add(new LocationPosn(35, 4));

        board = new Board(preoccupy);
        this.assertIsPermutation(preoccupy, board.squaresOccupiedByStack());
        board.spawn(PieceName.T);
        this.assertIsPermutation(expected, board.squaresOccupiedByHardDropGhost());
    }

    @Test
    public void hardDropGhostOnStackAfterRotating() {
        ArrayList<LocationPosn> preoccupy = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> upright = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> right = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> down = new ArrayList<LocationPosn>();
        ArrayList<LocationPosn> left = new ArrayList<LocationPosn>();
        preoccupy.add(new LocationPosn(38, 3));
        preoccupy.add(new LocationPosn(39, 3));
        preoccupy.add(new LocationPosn(39, 4));
        preoccupy.add(new LocationPosn(39, 5));
        upright.add(new LocationPosn(37, 3));
        upright.add(new LocationPosn(37, 4));
        upright.add(new LocationPosn(37, 5));
        upright.add(new LocationPosn(36, 5));
        right.add(new LocationPosn(38, 4));
        right.add(new LocationPosn(38, 5));
        right.add(new LocationPosn(37, 4));
        right.add(new LocationPosn(36, 4));
        down.add(new LocationPosn(37, 3));
        down.add(new LocationPosn(36, 3));
        down.add(new LocationPosn(36, 4));
        down.add(new LocationPosn(36, 5));
        left.add(new LocationPosn(38, 4));
        left.add(new LocationPosn(37, 4));
        left.add(new LocationPosn(36, 4));
        left.add(new LocationPosn(36, 3));

        board = new Board(preoccupy);
        this.assertIsPermutation(preoccupy, board.squaresOccupiedByStack());
        board.spawn(PieceName.L);

        assertTrue(board.rotate(RotationDirection.CLOCKWISE));
        this.assertIsPermutation(right, board.squaresOccupiedByHardDropGhost());
        assertTrue(board.rotate(RotationDirection.CLOCKWISE));
        this.assertIsPermutation(down, board.squaresOccupiedByHardDropGhost());
        assertTrue(board.rotate(RotationDirection.CLOCKWISE));
        this.assertIsPermutation(left, board.squaresOccupiedByHardDropGhost());
        assertTrue(board.rotate(RotationDirection.CLOCKWISE));
        this.assertIsPermutation(upright, board.squaresOccupiedByHardDropGhost());

        assertTrue(board.rotate(RotationDirection.COUNTERCLOCKWISE));
        this.assertIsPermutation(left, board.squaresOccupiedByHardDropGhost());
        assertTrue(board.rotate(RotationDirection.COUNTERCLOCKWISE));
        this.assertIsPermutation(down, board.squaresOccupiedByHardDropGhost());
        assertTrue(board.rotate(RotationDirection.COUNTERCLOCKWISE));
        this.assertIsPermutation(right, board.squaresOccupiedByHardDropGhost());
        assertTrue(board.rotate(RotationDirection.COUNTERCLOCKWISE));
        this.assertIsPermutation(upright, board.squaresOccupiedByHardDropGhost());
    }

    @Test
    public void hardDropGhostOnGroundAfterMoving() {

    }

    @Test
    public void hardDropGhostOnStackAfterMoving() {

    }


    // =====================
    // Private helper methods
    // =====================

    // Asserts that expected is a permutation of actual.
    private void 
    assertIsPermutation(
        ArrayList<LocationPosn> expected, ArrayList<LocationPosn> actual) 
    {
        assertEquals(expected.size(), actual.size(),
                     "Not of right length: ");

        ListIterator<LocationPosn> it = expected.listIterator();
        while (it.hasNext()) {
            LocationPosn shouldContain = it.next();
            int searched = actual.indexOf(shouldContain);
            assertTrue(searched >= 0, 
                       "Doesn't contain expected posn (xxx, yyy). "
                       .replaceFirst("xxx", Integer.toString(shouldContain.row))
                       .replaceFirst("yyy", Integer.toString(shouldContain.col)));
        }
    }

    // Returns a list obtained from original by applying an offset 
    // of offsetBy to each entry.
    private ArrayList<LocationPosn> 
    offsetList(ArrayList<LocationPosn> original, OffsetPosn offsetBy) {
        ArrayList<LocationPosn> offset  = new ArrayList<LocationPosn>();
        ListIterator<LocationPosn> iterOriginal = original.listIterator();
        while (iterOriginal.hasNext()) {
            offset.add(iterOriginal.next().add(offsetBy));
        }
        return offset;
    }
}