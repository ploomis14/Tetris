package tetris_ai;

import java.util.List;

/**
 * @author Peter Loomis
 */
public abstract class SearchProblem {



    public interface SearchNode {
        public List<SearchNode> getSuccessors();

        public double getScore();
    }
}
