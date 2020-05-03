package moga.Algorithm;

import java.util.ArrayList;
import java.util.List;

import moga.DataStructures.Chromosome;
import moga.DataStructures.Population;
import moga.Utilities.Configuration;
import moga.Utilities.Utilities;

public class NSGA {
	
	private NSGA() {
		
	}
	
    private static final int DOMINANT = 1;
    private static final int INFERIOR = 2;
    private static final int NON_DOMINATED = 3;
    
    public static Population preparePopulation(final Population population) {
        
        Chromosome[] populace = population.getPopulace().toArray(new Chromosome[population.getPopulace().size()]);
        
        NSGA.fastNonDominatedSort(populace);
        NSGA.crowdingDistanceAssignment(populace);
        
        Utilities.randomizedQuickSortForRank(population.getPopulace(), 0, populace.length - 1);
        
        return population;
    }
    
    public static Population getChildFromCombinedPopulation(final Population combinedPopulation) {
        
        int lastNonDominatedSetRank = combinedPopulation.getPopulace().get(Configuration.POPULATION_SIZE - 1).getRank();
        List<Chromosome> populace = new ArrayList<Chromosome>();
        
        Utilities.sortForCrowdingDistance(combinedPopulation.getPopulace(), lastNonDominatedSetRank);
        
        for(int i = 0; i < Configuration.POPULATION_SIZE; i++) 
        	populace.add(combinedPopulation.getPopulace().get(i));
        
        return new Population(populace);
    }
    
    private static void fastNonDominatedSort(final Chromosome[] populace) {
        
        for(Chromosome chromosome : populace) 
        	chromosome.reset();
        
        for(int i = 0; i < populace.length - 1; i++) {
            
            for(int j = i + 1; j < populace.length; j++) {
                
                switch(NSGA.dominates(populace[i], populace[j])) {
                    
                    case NSGA.DOMINANT:
                        
                        populace[i].setDominatedChromosome(populace[j]);
                        populace[j].incrementDominationCount(1);
                        
                        break;
                        
                    case NSGA.INFERIOR:
                        
                        populace[i].incrementDominationCount(1);
                        populace[j].setDominatedChromosome(populace[i]);
                        
                        break;
                        
                    case NSGA.NON_DOMINATED: break;
                }
            }
            
            if(populace[i].getDominationCount() == 0) populace[i].setRank(1);
        }
        
        if(populace[populace.length - 1].getDominationCount() == 0) populace[populace.length - 1].setRank(1);
        
        for(int i = 0; i < populace.length; i++) {
            
            for(Chromosome chromosome : populace[i].getDominatedChromosomes()) {
                
                chromosome.incrementDominationCount(-1);
                
                if(chromosome.getDominationCount() == 0) chromosome.setRank(populace[i].getRank() + 1);
            }
        }
    }
    
    private static void crowdingDistanceAssignment(final Chromosome[] nondominatedChromosomes) {
        
        int size = nondominatedChromosomes.length;
        
        for(int i = 0; i < 2; i++) {
            
        	Utilities.sortAgainstObjective(nondominatedChromosomes, i);
            
            nondominatedChromosomes[0].setCrowdingDistance(Double.MAX_VALUE);
            nondominatedChromosomes[size - 1].setCrowdingDistance(Double.MAX_VALUE);
            
            double maxObjectiveValue = Utilities.selectMaximumObjectiveValue(nondominatedChromosomes, i);
            double minObjectiveValue = Utilities.selectMinimumObjectiveValue(nondominatedChromosomes, i);
            
            for(int j = 1; j < size - 1; j++) if(nondominatedChromosomes[j].getCrowdingDistance() < Double.MAX_VALUE) nondominatedChromosomes[j].setCrowdingDistance(
                    nondominatedChromosomes[j].getCrowdingDistance() + (
                            (nondominatedChromosomes[j + 1].getObjectiveValues().get(i) - nondominatedChromosomes[j - 1].getObjectiveValues().get(i)) / (maxObjectiveValue - minObjectiveValue)
                    )
            );
            
        }
    }
    
    private static int dominates(final Chromosome chromosome1, final Chromosome chromosome2) {
        
        if(NSGA.isDominant(chromosome1, chromosome2)) return NSGA.DOMINANT;
        else if(NSGA.isDominant(chromosome2, chromosome1)) return NSGA.INFERIOR;
        else return NSGA.NON_DOMINATED;
    }
    
    private static boolean isDominant(final Chromosome chromosome1, final Chromosome chromosome2) {
        
        boolean isDominant = true;
        boolean atleastOneIsLarger = false;
        
        for(int i = 0; i < 2 ; i++) {
            
            if(chromosome1.getObjectiveValues().get(i) < chromosome2.getObjectiveValues().get(i)) {
                
                isDominant = false;
                break;
            } else if(!atleastOneIsLarger && (chromosome1.getObjectiveValues().get(i) > chromosome2.getObjectiveValues().get(i))) atleastOneIsLarger = true;
        }
        
        return isDominant && atleastOneIsLarger;
    }
}	
