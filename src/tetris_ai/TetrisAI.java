package tetris_ai;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Peter Loomis
 */
public class TetrisAI {

    private int lookAhead;

    public TetrisAI(int lookAhead) {
        this.lookAhead = lookAhead;
    }

    public Queue<Move> getMoveSequence(int[][] startState, Tetromino tetromino) {
        TetrisNode node = greedySearch(new TetrisNode(tetromino, startState, 0));
        return node.getMoves();
    }

    public TetrisNode greedySearch(TetrisNode startNode) {
        double bestScore = Integer.MIN_VALUE;
        TetrisNode localMax = null;
        List<TetrisNode> successors = startNode.getSuccessors();
        for (TetrisNode n : successors) {
            if (n.getScore() > bestScore) {
                bestScore = n.getScore();
                localMax = n;
            }
        }
        return localMax;
    }



    private class TetrisNode {

        private TetrisNode parent;

        private int depth;

        private double score;

        /*
            The move sequence that was performed to place the previous tetromino.
         */
        private Queue<Move> moves;

        /*
            The tiles in the game grid in row-major order. A bit is set to '1' if the tile at its index is occupied by
            a tetromino, '0' otherwise.
         */
        private int[][] state;

        /*
            The next tetromino to place.
         */
        private Tetromino tetromino;

        public TetrisNode(Tetromino tetromino, int[][] state, int depth) {
            this.state = state;
            this.tetromino = tetromino;
            this.depth = depth;
            this.score = computeScore();
            this.moves = new LinkedList<>();
        }

        /**
         * Find all possible successors of the current state. A successor will have some combination of a placement for
         * the current tetromino and a next tetromino to place.
         *
         * @return a list of successor nodes
         */
        public List<TetrisNode> getSuccessors() {
            List<TetrisNode> successors = new LinkedList<>();

            for (int index = 0; index < tetromino.getOrientations().length; index++) {
                for (int x = 0; x < Grid.WIDTH; x++) {
                    try {
                        Tetromino newPiece = tetromino.clone();
                        newPiece.setOrientationIndex(index);
                        newPiece.setCenterX(x);

                        int[][] newState = new int[Grid.HEIGHT][Grid.WIDTH];
                        for (int i = 0; i < Grid.HEIGHT; i++) {
                            System.arraycopy(state[i], 0, newState[i], 0, Grid.WIDTH);
                        }

                        if (Grid.isLegalMove(newPiece, newState, new int[]{0, 0})) {
                            Grid.dropPiece(newPiece, newState);
                            int dx = newPiece.getCenterX() - tetromino.getCenterX();
                            int dy = newPiece.getCenterY() - tetromino.getCenterY();
                            int rotations = Math.abs(newPiece.getOrientationIndex() - tetromino.getOrientationIndex());

                            for (Tetromino.Type type : Tetromino.Type.values()) {
                                TetrisNode successor = new TetrisNode(new Tetromino(type), newState, depth + 1);
                                successor.addRotations(rotations);
                                successor.addHorizontalMoves(dx);
                                successor.addVerticalMoves(dy);
                                successors.add(successor);
                            }
                        }
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return successors;
        }

        public void addMove(Move move) {
            moves.add(move);
        }

        public void addHorizontalMoves(int dx) {
            for (int lx = 0; lx > dx ; lx--) {
                addMove(Move.LEFT);
            }
            for (int rx = 0; rx < dx; rx++) {
                addMove(Move.RIGHT);
            }
        }

        public void addVerticalMoves(int dy) {
            for (int i = 0; i < dy; i++) {
                addMove(Move.DOWN);
            }
        }

        public void addRotations(int n) {
            for (int r = 0; r < n; r++) {
                addMove(Move.ROTATE);
            }
        }

        public Queue<Move> getMoves() {
            return moves;
        }

        public double getScore() {
            return score;
        }

        private double computeScore() {
            int height = Grid.getHeight(state);
            int holes = Grid.getHoles(state);
            int lines = Grid.getCompleteLines(state);

            return lines - holes - height;
        }

        public void setParent(TetrisNode p) {
            this.parent = p;
        }

        public TetrisNode getParent() {
            return parent;
        }
    }
}
