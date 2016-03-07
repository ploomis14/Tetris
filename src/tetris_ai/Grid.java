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

    public static int getHeight(int[][] state) {
        int maxHeight = 0;
        for (int c = 0; c < WIDTH; c++) {
            int h = getColumnHeight(state, c);
            if (h > maxHeight) maxHeight = h;
        }
        return maxHeight;
    }

    private static int getColumnHeight(int[][] state, int c) {
        for (int r = 0; r < HEIGHT; r++) {
            if (state[r][c] == 1) {
                return HEIGHT - r;
            }
        }
        return 0;
    }

    public static int getHoles(int[][] state) {
        int holes = 0;
        for (int r = 1; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                if (state[r][c] == 0 && state[r - 1][c] == 1) holes++;
            }
        }
        return holes;
    }

    public static int getCompleteLines(int[][] state) {
        int l = 0;
        for (int r = 0; r < HEIGHT; r++) {
            boolean complete = true;
            for (int c = 0; c < WIDTH; c++) {
                if (state[r][c] == 0) {
                    complete = false;
                    break;
                }
            }
            if (complete) l++;
        }
        return l;
    }
}
