package tetris_ai;

/**
 * @author Peter Loomis
 */
public final class Grid {

    public static final int WIDTH = 10;
    public static final int HEIGHT = 21;

    public static void placePiece(Tetromino piece, int[][] state) {
        for (int r = 0; r < Tetromino.HEIGHT; r++) {
            for (int c = 0; c < Tetromino.WIDTH; c++) {
                if (piece.getOrientation()[r][c] == 1) {
                    state[piece.getY() + r][piece.getX() + c] = 1;
                }
            }
        }
    }

    public static void dropPiece(Tetromino tetromino, int[][] state) {
        while(isLegalMove(tetromino, state, Move.DOWN.getValues())) {
            tetromino.setCenterY(tetromino.getCenterY() + Move.DOWN.getDy());
        }
        placePiece(tetromino, state);
    }

    public static boolean isLegal(int r, int c, int[][] grid) {
        return r < HEIGHT && r >= 0 && c < WIDTH && c >= 0 && grid[r][c] != 1;
    }

    public static boolean isLegalMove(Tetromino piece, int[][] state, int[] move) {
        for (int r = 0; r < Tetromino.HEIGHT; r++) {
            for (int c = 0; c < Tetromino.WIDTH; c++) {
                if (piece.getOrientation()[r][c] == 1
                        && !isLegal(piece.getY() + r + move[1], piece.getX() + c + move[0], state)) {
                    return false;
                }
            }
        }
        return true;
    }
}
