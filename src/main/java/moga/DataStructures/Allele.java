package moga.DataStructures;

public class Allele {
	
	private final int gene;
	
	public Allele(final int gene) {
		this.gene = gene;
	}
	
	public int getGene() {
		return this.gene;	
	}
	
	@Override
	public String toString() {
		return Integer.toString(this.gene);		
	}
}
