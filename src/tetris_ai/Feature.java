package tetris_ai;

/**
 * @author Peter Loomis
 */
public enum Feature {
    HEIGHT(0),
    HOLES(1),
    LINES(2),
    V_DELTA(3),
    ROUGHNESS(4);

    private int value;

    Feature(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
