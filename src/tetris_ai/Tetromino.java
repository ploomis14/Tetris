package tetris_ai;

import javafx.scene.paint.Color;

import java.util.Random;

/**
 * @author Peter Loomis
 */
public class Tetromino {

    public static final int WIDTH = 4;
    public static final int HEIGHT = 4;

    // starting coordinates
    private static final int START_X = 6;
    private static final int START_Y = 0;

    // rotation center
    private static final int CENTER_X = 2;
    private static final int CENTER_Y = 1;

    public enum Type {
        I,
        J,
        L,
        O,
        S,
        T,
        Z
    }

    private Type type;
    private int orientationIndex;
    private int startX, startY;

    public Tetromino() {
        Random rand = new Random();
        int i = rand.nextInt(Type.values().length);
        type = Type.values()[i];
        orientationIndex = getRandomOrientation();
        startX = START_X;
        startY = START_Y;
    }

    public int getCenterX() {
        return startX;
    }

    public int getCenterY() {
        return startY;
    }

    public void setCenterX(int x) {
        startX = x;
    }

    public void setCenterY(int y) {
        startY = y;
    }

    public int getY() {
        return startY - CENTER_Y;
    }

    public int getX() {
        return startX - CENTER_X;
    }

    private int getRandomOrientation() {
        Random rand = new Random();
        return rand.nextInt(getOrientations().length);
    }

    private int[][][] getOrientations() {
        switch (type) {
            case I:
                return Orientations.I;

            case J:
                return Orientations.J;

            case L:
                return Orientations.L;

            case O:
                return Orientations.O;

            case S:
                return Orientations.S;

            case T:
                return Orientations.T;

            case Z:
                return Orientations.Z;

            default:
                return Orientations.I;
        }
    }

    public int[][] getOrientation() {
        return getOrientations()[orientationIndex];
    }

    public void setOrientationIndex(int index) {
        orientationIndex = index;
    }

    public int getOrientationIndex() {
        return orientationIndex;
    }

    public void rotate() {
        orientationIndex++;
        orientationIndex %= getOrientations().length;
    }

    public Color getColor() {
        switch (type) {
            case I:
                return Color.RED;

            case J:
                return Color.BLUE;

            case L:
                return Color.YELLOW;

            case O:
                return Color.BROWN;

            case S:
                return Color.GREEN;

            case T:
                return Color.PURPLE;

            case Z:
                return Color.CYAN;

            default:
                return Color.BLACK;
        }
    }
}