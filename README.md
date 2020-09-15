# Tetris 

## Introduction

This project is a Tetris clone that is playable in the browser. The game aims to follow the Tetris Guideline as closely as possible.


## Technologies

The project uses:
* Java version: 11
* Spring Framework version: 2.3.1
* Maven version: 3.6.3


## Launch

Call `mvn spring-boot:run` from the terminal to start the program. Once it is running, point the browser to the local server (eg. localhost:8080).


## Features

### Implemented Tetris Guideline rules

* Rotation and wall-kicking behaviour follows the Super Rotation System. If a tetromino cannot occupy the space after rotating, the game attempts to move it to nearby positions. The piece only fails to rotate if there is no space in any such alternative position.
* Pieces lock down after 0.5 seconds on the stack. Rotating or moving the piece while on the stack resets the lock timer up to 15 times (Extended Placement Lock Down).
* The active piece may be placed in the Hold box, after which it will be greyed-out and unusable until a piece is locked.
* The game displays a ghost piece, which shows the location of the piece if it were hard-dropped.
* Piece generation uses the "7-bag" method. The game replenishes the Next Queue by adding new pieces 7 at a time: one of each of the unique tetromino types, shuffled randomly.
* Pieces spawn on the 21st and 22nd rows, above the field, and move down by one square immediately after spawning.
* Scoring system mimics that of recent Tetris Guideline-adherent games.
* The Next Queue previews the upcoming 5 pieces.
* Level-ups occur every 10 lines cleared. The player may select their starting level on a level-select screen before the start of each game. Pieces fall faster and players earn more points on higher levels. Levels 1-5 are implemented.
* A Game Over occurs when a newly spawned piece overlaps with an existing block on the board. The player may restart with empty board and a score of 0 after each Game Over.
* Follows the speed curve used in Tetris Worlds up to level 5. The time spent per row in milliseconds is 1000 * (0.8 - 0.007 (Level - 1)) ^ (Level - 1), rounded to the nearest integer.


### Other
* Rewards perfect clears, back-to-back Tetrises and T-Spins, and line clear combos with bonuses to the score.
* Replaces soft-drop with sonic-drop. Instead of dropping the piece one square at a time, the piece is immediately teleported to the bottom without locking.
