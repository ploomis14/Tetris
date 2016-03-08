package tetris_ai;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author Peter Loomis
 */
public class GeneticProblem {

    /*
        Used in the selection process - only the top 1/ELITE_SELECTION Chromosomes are allowed to reproduce
     */
    private static final int ELITE_SELECTION = 4;

    /*
        Minimum feature weight
     */
    private static final double MIN_WEIGHT = -5;

    /*
        Maximum feature weight
     */
    private static final double MAX_WEIGHT = 5;

    /*
        Initial number of chromosomes/individuals
     */
    private static final int POPULATION_SIZE = 100;

    /*
        The probability that a child chromosome will be assigned a random attribute weight that doesn't come from its
        parents
     */
    private static final double MUTATION_RATE = 0.15;

    /*
        The number of iterations of genetic search
     */
    private static final int ITERATIONS = 50;

    public List<Chromosome> getPopulation() {
        List<Chromosome> population = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population.add(new TetrisAI(getRandomWeights()));
        }
        return population;
    }

    private double[] getRandomWeights() {
        double[] weights = new double[Feature.values().length];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = getRandomWeight();
        }
        return weights;
    }

    private double getRandomWeight() {
        Random rand = new Random();
        return MIN_WEIGHT + rand.nextDouble() * (MAX_WEIGHT - MIN_WEIGHT);
    }

    /**
     * Choose a random chromosome from the top 1/ELITE_SELECTION chromosomes in the population
     *
     * @param population a list of chromosomes
     * @return a chromosome randomly selected from the best chromosomes in the population
     */
    private Chromosome select(List<Chromosome> population) {
        Collections.sort(population);
        Random random = new Random();
        int bound = population.size()/ELITE_SELECTION + 1;
        int index = random.nextInt(bound);
        return population.get(index);
    }

    /**
     * Produce an offspring of two parent chromosomes by mixing their features.
     *
     * @param c1 a parent chromosome
     * @param c2 a parent chromosome
     * @return the result of the two parents mating
     */
    private Chromosome reproduce(Chromosome c1, Chromosome c2) {
        double[] childWeights = new double[Feature.values().length];
        Random random = new Random();
        for (int i = 0; i < Feature.values().length; i++) {
            double mutationProb = random.nextDouble();
            if (mutationProb <= MUTATION_RATE) {
                childWeights[i] = getRandomWeight();
            } else {
                int p = random.nextInt(2);
                if (p == 0) {
                    childWeights[i] = c1.getWeights()[i];
                } else {
                    childWeights[i] = c2.getWeights()[i];
                }
            }
        }
        return new TetrisAI(childWeights);
    }

    public Chromosome geneticSearch(List<Chromosome> population) {
        System.out.println("Calculating feature weights...");
        for (int t = 0; t < ITERATIONS; t++) {
            List<Chromosome> newPopulation = new ArrayList<>();
            for (int i = 0; i < population.size(); i++) {
                Chromosome c1 = select(population);
                Chromosome c2 = select(population);
                Chromosome child = reproduce(c1, c2);
                newPopulation.add(child);
            }
            population = newPopulation;
        }

        Collections.sort(population);
        return population.get(0);
    }

    public static void writeWeightsToFile(double[] weights, String filename) {
        try {
            PrintWriter printWriter = new PrintWriter(filename, "UTF-8");
            for (double w : weights) {
                printWriter.println("" + w);
            }
            printWriter.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        GeneticProblem gp = new GeneticProblem();
        List<Chromosome> population = gp.getPopulation();
        Chromosome bestIndividual = gp.geneticSearch(population);
        for (int i = 0; i < bestIndividual.getWeights().length; i++) {
            System.out.println(Feature.values()[i] + " weight: " + bestIndividual.getWeights()[i]);
        }
        writeWeightsToFile(bestIndividual.getWeights(), "weights");
    }
}
