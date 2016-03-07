package tetris_ai;

/**
 * @author Peter Loomis
 */
public enum Move {
    RIGHT(1, 0),
    LEFT(-1, 0),
    DOWN(0, 1),
    ROTATE(0, 0);

    private int dx, dy;

    Move(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    int[] getValues() {
        return new int[] {dx, dy};
    }

    int getDx() {
        return dx;
    }

    int getDy() {
        return dy;
    }
}
