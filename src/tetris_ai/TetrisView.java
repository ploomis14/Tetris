package tetris_ai;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.List;

/**
 * @author Peter Loomis
 */
public class TetrisView extends Group {

    private static final int PIXELS_PER_TILE = 32;
    private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 21;

    private Rectangle[][] tiles;
    private int[][] occupied;

    public TetrisView() {
        occupied = new int[GRID_HEIGHT][GRID_WIDTH];
        tiles = new Rectangle[GRID_HEIGHT][GRID_WIDTH];
        for (int r = 0; r < GRID_HEIGHT; r++) {
            for (int c = 0; c < GRID_WIDTH; c++) {
                Rectangle tile = new Rectangle(c * PIXELS_PER_TILE, r * PIXELS_PER_TILE,
                        PIXELS_PER_TILE, PIXELS_PER_TILE);
                tile.setStroke(Color.GRAY);
                tile.setFill(Color.LIGHTGRAY);
                tiles[r][c] = tile;
                this.getChildren().add(tile);
            }
        }
    }

    public void clearLines() {
        int r = GRID_HEIGHT - 1;
        while (r > 0) {
            if (fullRow(r)) {
                clearRow(r);
                dropPiecesAboveRow(r);
            } else {
                r--;
            }
        }
    }

    private boolean fullRow(int row) {
        for (int col = 0; col < GRID_WIDTH; col++) {
            if (occupied[row][col] == 0) {
                return false;
            }
        }
        return true;
    }

    private void clearRow(int r) {
        for (int c = 0; c < GRID_WIDTH; c++) {
            occupied[r][c] = 0;
            tiles[r][c].setFill(Color.LIGHTGRAY);
        }
    }

    private void dropPiecesAboveRow(int row) {
        for (int r = row; r > 0; r--) {
            for (int c = 0; c < GRID_WIDTH; c++) {
                tiles[r][c].setFill(tiles[r - 1][c].getFill());
                occupied[r][c] = occupied[r - 1][c];
            }
        }
    }

    public void addPiece(Tetromino piece) {
        drawPiece(piece);
    }

    public void drawPiece(Tetromino piece) {
        drawPiece(piece, piece.getColor());
    }

    public void clearPiece(Tetromino piece) {
        drawPiece(piece, Color.LIGHTGRAY);
    }

    private void drawPiece(Tetromino piece, Color color) {
        for (int r = 0; r < Tetromino.HEIGHT; r++) {
            for (int c = 0; c < Tetromino.WIDTH; c++) {
                int x = piece.getX() + c;
                int y = piece.getY() + r;
                if (piece.getOrientation()[r][c] == 1 && isLegal(y, x)) {
                    tiles[y][x].setFill(color);
                }
            }
        }
    }

    public void rotatePiece(Tetromino piece) {
        clearPiece(piece);
        int oldOrientation = piece.getOrientationIndex();
        piece.rotate();
        if (!isLegal(piece, new int[]{0, 0})) {
            piece.setOrientationIndex(oldOrientation);
        }
        drawPiece(piece);
    }

    public void doMove(Tetromino piece, int[] move) {
        if (isLegal(piece, move)) {
            clearPiece(piece);
            piece.setCenterX(piece.getCenterX() + move[0]);
            piece.setCenterY(piece.getCenterY() + move[1]);
            drawPiece(piece);
        }
    }

    public boolean isLegal(Tetromino piece, int[] move) {
        for (int r = 0; r < Tetromino.HEIGHT; r++) {
            for (int c = 0; c < Tetromino.WIDTH; c++) {
                if (piece.getOrientation()[r][c] == 1
                        && !isLegal(piece.getY() + r + move[1], piece.getX() + c + move[0])) {
                    return false;
                }
            }
        }
        return true;
    }

    public void placePiece(Tetromino piece) {
        for (int r = 0; r < Tetromino.HEIGHT; r++) {
            for (int c = 0; c < Tetromino.WIDTH; c++) {
                if (piece.getOrientation()[r][c] == 1) {
                    occupied[piece.getY() + r][piece.getX() + c] = 1;
                }
            }
        }
    }

    private boolean isLegal(int r, int c) {
        return r < GRID_HEIGHT && r >= 0 && c < GRID_WIDTH && c >= 0 && occupied[r][c] != 1;
    }
}