package tetris_ai;

/**
 * @author Peter Loomis
 */
public abstract class Chromosome implements Comparable<Chromosome> {

    protected double fitness;

    protected double[] weights;

    public Chromosome(double[] weights) {
        this.weights = weights;
        this.fitness = evaluate();
    }

    protected abstract double evaluate();

    public double[] getWeights() {
        return weights;
    }

    public double getFitness() {
        return fitness;
    }

    @Override
    public int compareTo(Chromosome o) {
        return o.getFitness() > this.fitness ? 1 : (o.getFitness() < this.fitness ? - 1 : 0);
    }
}
