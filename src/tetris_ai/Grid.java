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
                if (isHole(r, c, state)) holes += r;
            }
        }
        return holes;
    }

    private static boolean isHole(int r, int c, int[][] state) {
        if (state[r][c] != 0) return false;
        for (int row = r - 1; row >= 0 ; row--) {
            if (state[row][c] == 1) return true;
        }
        return false;
    }

    public static int getCompleteLines(int[][] state) {
        int l = 0;
        for (int r = 0; r < HEIGHT; r++) {
            if (fullRow(r, state)) l++;
        }
        return l;
    }

    public static boolean fullRow(int row, int[][] state) {
        for (int col = 0; col < WIDTH; col++) {
            if (state[row][col] == 0) {
                return false;
            }
        }
        return true;
    }

    public static void clearRow(int r, int[][] state) {
        for (int c = 0; c < WIDTH; c++) {
            state[r][c] = 0;
        }
    }

    public static void dropPiecesAboveRow(int row, int[][] state) {
        for (int r = row; r > 0; r--) {
            System.arraycopy(state[r - 1], 0, state[r], 0, WIDTH);
        }
    }

    public static boolean gameOver(int[][] state) {
        for (int c = 0; c < WIDTH; c++) {
            if (state[0][c] == 1) {
                return true;
            }
        }
        return false;
    }

    public static int getAltitudeDelta(int[][] state) {
        int highestColumn = 0;
        int lowestColumn = HEIGHT - 1;
        for (int c = 0; c < WIDTH; c++) {
            int h = getColumnHeight(state, c);
            if (h > lowestColumn) lowestColumn = h;
            if (h < lowestColumn) lowestColumn = h;
        }
        return highestColumn - lowestColumn;
    }

    public static int clearLines(int[][] state) {
        int linesCleared = 0;
        int r = Grid.HEIGHT - 1;
        while (r > 0) {
            if (Grid.fullRow(r, state)) {
                linesCleared++;
                clearRow(r, state);
                dropPiecesAboveRow(r, state);
            } else {
                r--;
            }
        }
        return linesCleared;
    }

    public static int getRoughness(int[][] state) {
        int roughness = 0;
        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH - 1; c++) {
                if (state[r][c] != state[r][c + 1]) roughness++;
            }
        }
        return roughness;
    }
}
