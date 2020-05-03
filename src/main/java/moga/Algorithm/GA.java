package moga.Algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import moga.DataStructures.Allele;
import moga.DataStructures.Chromosome;
import moga.DataStructures.Population;
import moga.Utilities.Configuration;
import moga.Utilities.Utilities;

public class GA {
	
	private GA() {
		
	}
	
	private static final Random LOCAL_RANDOM = new Random();
	
	public static Population generatePopulation() {
		
		List<Chromosome> populace = new ArrayList<Chromosome>();
		
		for(int i = 0 ; i < Configuration.POPULATION_SIZE ; i++) {
			Chromosome chromosome = new Chromosome(GA.createRandomTeam(Configuration.TOTAL_PLAYERS));
			populace.add(chromosome);
		}
		
		return new Population(populace);	
		
	}

	private static Allele[] createRandomTeam(int totalPlayers)	 {	

		HashSet<Integer> team = new HashSet<Integer>();
		int iter = 0;
		while(team.size() < 11) {
			team.add(ThreadLocalRandom.current().nextInt(0, totalPlayers));	
			if(team.size() == 11 && !Utilities.isValidSequence(team)) {
				if(iter > 10000) {
					System.out.println("CONSTRAINTS VIOLATED");
					break;
				}else {
					System.out.println("Unable to Generate");
					team.clear();
				}
			}
			iter++;
		}
		Allele geneticCode[] = new Allele[11];
		int index = 0;
		for(Integer i : team) {
			geneticCode[index++] = new Allele(i);	
		}
		return geneticCode;	
	}
	
	public static Population generateChildren(Population parent) {
		List<Chromosome> populace = new ArrayList<Chromosome>();
		
		while(populace.size() < Configuration.POPULATION_SIZE) {
			if((Configuration.POPULATION_SIZE - populace.size()) == 1) {
				populace.add(GA.mutation(GA.crowdedBinaryTournamentSelection(parent)));
			}else {
				for(Chromosome chromosome : GA.crossover(GA.crowdedBinaryTournamentSelection(parent) , GA.crowdedBinaryTournamentSelection(parent))) {
					populace.add(GA.mutation(chromosome));
				}
			}
		}
		return new Population(populace);	
	}
	
	public static Population createCombinedPopulation(final Population parent, final Population child) {
		List<Chromosome> populace = parent.getPopulace();
		for(Chromosome chromosome : child.getPopulace()) {
			populace.add(chromosome);
		}
		return new Population(populace);	
	}
	
	private static Chromosome crowdedBinaryTournamentSelection(final Population population) {
		
        Chromosome participant1 = population.getPopulace().get(ThreadLocalRandom.current().nextInt(0, Configuration.POPULATION_SIZE));
        Chromosome participant2 = population.getPopulace().get(ThreadLocalRandom.current().nextInt(0, Configuration.POPULATION_SIZE));
        
        if(participant1.getRank() < participant2.getRank()) return participant1;
        
        else if(participant1.getRank() == participant2.getRank()) {
        	if(participant1.getCrowdingDistance() > participant2.getCrowdingDistance()) return participant1;
        	else if(participant1.getCrowdingDistance() < participant2.getCrowdingDistance()) return participant2;
        	else return GA.LOCAL_RANDOM.nextBoolean() ? participant1 : participant2;	
        }
        else 
        	return participant2;
        
	}
	
	private static Chromosome mutation(final Chromosome chromosome) {
		return ((GA.LOCAL_RANDOM.nextFloat() <= Configuration.MUTATION_PROBABILITY) ? GA.singlePointMutation(chromosome) : chromosome);
	}
	
	private static Chromosome singlePointMutation(final Chromosome chromosome) {
		HashSet<Integer> team = new HashSet<Integer>();
		for(int i = 0 ; i < 11 ; i++) {
			team.add(chromosome.getGeneticCode()[i].getGene());
		}
		while(team.size() == 11) {
			team.remove(ThreadLocalRandom.current().nextInt(0, Configuration.TOTAL_PLAYERS));
		}
		while(team.size() < 11) {
			team.add(ThreadLocalRandom.current().nextInt(0, Configuration.TOTAL_PLAYERS));
		}
		Allele geneticCode[] = new Allele[11];
		int index = 0;
		for(Integer i : team) {
			geneticCode[index++] = new Allele(i);	
		}
		
		return (GA.isGeneticCodeSimilar(geneticCode, chromosome.getGeneticCode()) ? chromosome.getCopy() : new Chromosome(geneticCode));
		
	}
	
	private static Chromosome[] crossover(final Chromosome chromosome1, final Chromosome chromosome2) {
        
        if(GA.LOCAL_RANDOM.nextFloat() <= Configuration.CROSSOVER_PROBABILITY) 
        	return new Chromosome[] { GA.uniformCrossover(chromosome1, chromosome2), GA.uniformCrossover(chromosome1, chromosome2) };
        else 
        	return new Chromosome[] { chromosome1.getCopy(), chromosome2.getCopy() };
    }
	
	
	private static Chromosome uniformCrossover(final Chromosome chromosome1 , final Chromosome chromosome2) {
		HashSet<Integer> hs = new HashSet<Integer>();
		int iter = 0;
		for(int i = 0 ; hs.size() < 11 ;) {
			iter++;
			switch(GA.LOCAL_RANDOM.nextInt(2)) {
				case 0:
					if(hs.add(chromosome1.getGeneticCode()[i].getGene())) i++;
				break;
				case 1:
					if(hs.add(chromosome2.getGeneticCode()[i].getGene())) i++;
				break;
			}
			if(iter > 10000) {
				System.out.println("CONSTRAINTS VIOLATED");
				return GA.LOCAL_RANDOM.nextBoolean() ? chromosome1 : chromosome2;	
			}
			if(hs.size() == 11 && !Utilities.isValidSequence(hs)) {
				hs.clear();
				i = 0;
				if(iter > 10000) {
					return GA.LOCAL_RANDOM.nextBoolean() ? chromosome1 : chromosome2;	
				}
			}
		}
		
		Allele[] geneticCode = new Allele[11];
		int index = 0;
		for(Integer i : hs) {
			geneticCode[index++] = new Allele(i);
		}
		
		return new Chromosome(geneticCode);	
		
	}
	
	private static boolean isGeneticCodeSimilar(final Allele[] geneticCode1, final Allele[] geneticCode2) {
        for(int i = 0; i < 11 ; i++) 	
        	if(geneticCode1[i].getGene() != geneticCode2[i].getGene()) 
        		return false; 
        return true;
    }
	
}
