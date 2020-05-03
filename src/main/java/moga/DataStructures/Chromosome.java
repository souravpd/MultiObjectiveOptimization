package moga.DataStructures;

import java.util.ArrayList;
import java.util.List;

import moga.Utilities.Utilities;

public class Chromosome {
    
    private List<Chromosome> dominatedChromosomes = new ArrayList<Chromosome>();
    private final List<Double> objectiveValues = new ArrayList<Double>();	
    private double crowdingDistance = 0;
    private final Allele[] geneticCode;
    private int dominationCount = 0;
    private int rank;
    
    private Chromosome(final Chromosome chromosome) {
        
        this.geneticCode = new Allele[11];
        
        for(int i = 0; i < 11; i++) 
        	this.geneticCode[i] = new Allele(chromosome.getGeneticCode()[i].getGene());
        this.objectiveValues.add(chromosome.getObjectiveValues().get(0));
        this.objectiveValues.add(chromosome.getObjectiveValues().get(1));	
    }
    
    public Chromosome(final Allele[] geneticCode) {
        
        this.geneticCode = geneticCode;
        this.objectiveValues.add(0 , Utilities.getBattingFitness(geneticCode));
        this.objectiveValues.add(0 , Utilities.getBowlingFitness(geneticCode));	
    }
    
    public void reset() {
        
        this.dominationCount = 0;
        this.rank = Integer.MAX_VALUE;
        this.dominatedChromosomes = new ArrayList<Chromosome>();
    }
    
    public Chromosome getCopy() {
        return new Chromosome(this);
    }
    
    public void setDominatedChromosome(final Chromosome chromosome) {
        this.dominatedChromosomes.add(chromosome);
    }
    
    public void incrementDominationCount(int incrementValue) {
        this.dominationCount += incrementValue;
    }

    public Allele[] getGeneticCode() {
        return geneticCode;
    }

    public List<Double> getObjectiveValues() {
        return objectiveValues;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getCrowdingDistance() {
        return crowdingDistance;
    }

    public void setCrowdingDistance(double crowdingDistance) {
        this.crowdingDistance = crowdingDistance;
    }

    public int getDominationCount() {
        return dominationCount;
    }

    public List<Chromosome> getDominatedChromosomes() {
        return dominatedChromosomes;
    }
    
    @Override
    public String toString() {
        return null;
    }
}