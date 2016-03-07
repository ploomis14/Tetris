package tetris_ai;

import java.util.BitSet;
import java.util.List;

/**
 * @author Peter Loomis
 */
public class TetrisAI extends SearchProblem {

    private class TetrisNode implements SearchNode {

        /*
            The tiles in the game grid in row-major order. A bit is set to '1' if the tile at its index is occupied by
            a tetromino, '0' otherwise.
         */
        private BitSet state;

        /*
            The current tetromino to place.
         */
        private Tetromino tetromino;

        public TetrisNode() {

        }

        @Override
        public List<SearchNode> getSuccessors() {
            return null;
        }

        @Override
        public double getScore() {
            return 0;
        }
    }
}
