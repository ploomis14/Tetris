package tetris_ai;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Peter Loomis
 */
public class TetrisAI {

    private int searchDepth;

    public TetrisAI(int lookAhead) {
        searchDepth = lookAhead;
    }

    public Queue<Move> getMoveSequence(int[][] startState, Tetromino tetromino) {
        TetrisNode node = bfs(new TetrisNode(tetromino, startState, 0));
        return node.getMoves();
    }

    private TetrisNode backchain(TetrisNode n) {
        if (n == null) return null;
        TetrisNode current = n;
        TetrisNode p = current.getParent();
        while (p.getParent() != null) {
            current = p;
            p = p.getParent();
        }
        return current;
    }

    public TetrisNode bfs(TetrisNode startNode) {
        List<TetrisNode> nodes = startNode.getSuccessors();
        return nodes.get(0);
    }

    private class TetrisNode {

        private TetrisNode parent;

        private int depth;

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
            moves = new LinkedList<>();
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
                tetromino.setCenterY(Tetromino.START_Y);
                tetromino.setCenterX(Tetromino.START_X);

                for (int x = 0; x < Grid.WIDTH; x++) {
                    int numRotations = Math.abs(tetromino.getOrientationIndex() - index);
                    tetromino.setOrientationIndex(index);

                    int dx = x - tetromino.getCenterX();
                    tetromino.setCenterX(x);
                    int[][] newState = new int[state.length][];
                    for (int i = 0; i < state.length; i++) {
                        newState[i] = state[i].clone();
                    }

                    int y = tetromino.getCenterY();
                    if (Grid.isLegalMove(tetromino, newState, new int[]{0, 0})) {
                        Grid.dropPiece(tetromino, newState);
                    }
                    int dy = tetromino.getCenterY() - y;

                    TetrisNode successor = new TetrisNode(tetromino, newState, depth + 1);
                    successor.addHorizontalMoves(dx);
                    successor.addVerticalMoves(dy);
                    successor.addRotations(numRotations);

                    successors.add(successor);
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

        /**
         * Get the average score of all the current state's successors.
         * 
         * @return the average score
         */
        public double getHeuristic() {
            return 0;
        }

        public double getScore() {
            return 0;
        }

        public void setParent(TetrisNode p) {
            this.parent = p;
        }

        public TetrisNode getParent() {
            return parent;
        }
    }
}
