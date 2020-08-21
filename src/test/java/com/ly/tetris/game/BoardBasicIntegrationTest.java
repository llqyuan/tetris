package com.ly.tetris.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.ListIterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.ly.tetris.infostructs.LocationPosn;
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

    }

    @Test
    public void srsRotationKicksAnObstructedPieceUsingMidSuggestion() {

    }

    @Test
    public void srsRotationKicksAnObstructedPieceUsingLastSuggestion() {
        
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
}