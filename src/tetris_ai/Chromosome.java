package tetris_ai;

/**
 * @author Peter Loomis
 */
public interface Chromosome {

    public double[] getWeights();

    public double getFitness();
}
