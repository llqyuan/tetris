package com.ly.tetris;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jdk.internal.jline.internal.TestAccessible;

import com.ly.tetris.infostructs.Posn;
import com.ly.tetris.game.pieces.IPiece;

public class IPieceTest {
    IPiece piece;

    @BeforeEach
    public void initialize() {
        piece = new IPiece();
    }

    @Test
    public void spawnLocationIsCorrect() {

    }

    @Test
    public void rotateClockwiseCorrectly() {

    }

    @Test
    public void rotateCounterclockwiseCorrectly() {

    }

    @Test
    public void 
    occupiedIfRotatedClockwiseShouldReturnIdenticalResult() {

    }

    @Test
    public void 
    occupiedIfRotatedCounterclockShouldReturnIdenticalResult() {

    }
}