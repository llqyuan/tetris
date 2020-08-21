package com.ly.tetris.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.ListIterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.ly.tetris.infostructs.LocationPosn;
import com.ly.tetris.infostructs.PieceOrientation;
import com.ly.tetris.infostructs.PieceName;
import com.ly.tetris.infostructs.RotationDirection;

public class BoardIntegrationTest {
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
    // ==============================================

    @Test
    public void pieceRotates() {
        board.spawn(PieceName.I);
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

    }

    @Test
    public void jOccupiesCorrectSpotsAfterRotating() {

    }

    @Test
    public void lOccupiesCorrectSpotsAfterRotating() {

    }

    @Test
    public void oOccupiesCorrectSpotsAfterRotating() {

    }

    @Test
    public void sOccupiesCorrectSpotsAfterRotating() {

    }

    @Test
    public void tOccupiesCorrectSpotsAfterRotating() {

    }

    @Test
    public void zOccupiesCorrectSpotsAfterRotating() {

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
    public void hardDropGhostOnGroundAfterRotatingI() {

    }

    @Test
    public void hardDropGhostOnGroundAfterRotatingJ() {

    }

    @Test
    public void hardDropGhostOnGroundAfterRotatingL() {
        
    }

    @Test
    public void hardDropGhostOnGroundAfterRotatingO() {
        
    }

    @Test
    public void hardDropGhostOnGroundAfterRotatingS() {
        
    }

    @Test
    public void hardDropGhostOnGroundAfterRotatingT() {
        
    }

    @Test
    public void hardDropGhostOnGroundAfterRotatingZ() {
        
    }

    @Test
    public void hardDropGhostOnStackAfterSpawning() {

    }

    @Test
    public void hardDropGhostOnStackAfterRotating() {

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
                       .replaceFirst("xxx", Integer.toString(shouldContain.row)
                       .replaceFirst("yyy", Integer.toString(shouldContain.col))));
        }
    }
}