package moga.DataStructures;

import java.util.List;

public class Population {
	
	public final List<Chromosome> populace;	
	
	public Population(final List<Chromosome> populace) {
		this.populace = populace;	
	}
	
	public List<Chromosome> getPopulace(){
		return this.populace;					
	}
}
