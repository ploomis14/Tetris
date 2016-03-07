package tetris_ai;

import javafx.scene.paint.Color;

import java.util.Random;

/**
 * @author Peter Loomis
 */
public class Tetromino implements Cloneable {

    public static final int WIDTH = 4;
    public static final int HEIGHT = 4;

    // starting coordinates
    public static final int START_X = 4;
    public static final int START_Y = 0;

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

    public Tetromino(Type type) {
        this.type = type;
        this.orientationIndex = getRandomOrientation();
        startX = START_X;
        startY = START_Y;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public int getCenterX() {
        return startX + CENTER_X;
    }

    public int getCenterY() {
        return startY + CENTER_Y;
    }

    public void setCenterX(int x) {
        startX = x - CENTER_X;
    }

    public void setCenterY(int y) {
        startY = y - CENTER_Y;
    }

    public int getY() {
        return startY;
    }

    public int getX() {
        return startX;
    }

    private int getRandomOrientation() {
        Random rand = new Random();
        return rand.nextInt(getOrientations().length);
    }

    public int[][][] getOrientations() {
        switch (type) {
            case I:
                return Orientation.I;

            case J:
                return Orientation.J;

            case L:
                return Orientation.L;

            case O:
                return Orientation.O;

            case S:
                return Orientation.S;

            case T:
                return Orientation.T;

            case Z:
                return Orientation.Z;

            default:
                return Orientation.I;
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

    @Override
    protected Tetromino clone() throws CloneNotSupportedException {
        Tetromino copy = (Tetromino) super.clone();
        copy.setType(type);
        copy.setOrientationIndex(orientationIndex);
        return copy;
    }
}