package moga.Utilities;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import moga.Data.Players;
import moga.DataStructures.Allele;
import moga.DataStructures.Chromosome;

public class Utilities {
	
	private Utilities() {
			
	}

	public static Double getBattingFitness(Allele[] geneticCode) {		
		double fitness = 0.0;		
		for(int i = 0 ; i < geneticCode.length ; i++) {
			int playerIndex = geneticCode[i].getGene();
			Players p = Configuration.PLAYERS.get(playerIndex);
			if(p.isBatsman == 1) {
				fitness += calculateBatsmanFitness(p);
			}else {
				continue;
			}
		}
		//return (fitness/1100)*100;
		return (1100/(fitness+1));
	}

	private static double calculateBatsmanFitness(Players p) {
		// TODO Auto-generated method stub
		double battingFitness = 0.0;
		// each criteria will score marks out of maximum 20
		int batAvgFitness = 0;
		int batSRFitness = 0;
		int batRunsFitness = 0;
		int bat100sFitness = 0;
		int bat50sFitness = 0;
		
		//batting average fitness criteria
		if (p.battingAvg > 50) batAvgFitness = 100;
		else if (p.battingAvg <= 50 && p.battingAvg > 45) batAvgFitness = 90; 	
		else if (p.battingAvg <= 45 && p.battingAvg > 40) batAvgFitness = 85;
		else if (p.battingAvg <= 40 && p.battingAvg > 35) batAvgFitness = 75;
		else if (p.battingAvg <= 35 && p.battingAvg > 30) batAvgFitness = 60;
		else if (p.battingAvg <= 30 && p.battingAvg > 25) batAvgFitness = 50;
		else if (p.battingAvg <= 25 && p.battingAvg > 20) batAvgFitness = 40;
		else if (p.battingAvg <= 20 && p.battingAvg > 15) batAvgFitness = 30;
		else if (p.battingAvg <= 15 && p.battingAvg > 10) batAvgFitness = 20;
		else if (p.battingAvg <= 10) batAvgFitness = 10;
		
		//batting SR fitness criteria
		if (p.battingStrikeRate > 100) batSRFitness = 20; 	
		else if (p.battingStrikeRate <= 100 && p.battingStrikeRate > 95) batSRFitness = 18;
		else if (p.battingStrikeRate <= 95 && p.battingStrikeRate > 90) batSRFitness = 16;
		else if (p.battingStrikeRate <= 90 && p.battingStrikeRate > 85) batSRFitness = 14;
		else if (p.battingStrikeRate <= 85 && p.battingStrikeRate > 80) batSRFitness = 12;
		else if (p.battingStrikeRate <= 80 && p.battingStrikeRate > 75) batSRFitness = 10;
		else if (p.battingStrikeRate <= 75 && p.battingStrikeRate > 70) batSRFitness = 8;
		else if (p.battingStrikeRate <= 70 && p.battingStrikeRate > 65) batSRFitness = 6;
		else if (p.battingStrikeRate <= 65 && p.battingStrikeRate > 60) batSRFitness = 4;
		else if (p.battingStrikeRate <= 60) batSRFitness = 2;
		
		//batting Runs fitness criteria
		// total Runs will be estimated for 30 matches to reduce unfairness to new batsmen
		double estimatedTotal = (p.totalRuns/ p.matchesPlayed) * 30;
		
		if (estimatedTotal > 1000) batRunsFitness = 20; 	
		else if (estimatedTotal <= 1000 && estimatedTotal > 900) batRunsFitness = 18;
		else if (estimatedTotal <= 900 && estimatedTotal > 800) batRunsFitness = 16;
		else if (estimatedTotal <= 800 && estimatedTotal > 700) batRunsFitness = 14;
		else if (estimatedTotal <= 700 && estimatedTotal > 600) batRunsFitness = 12;
		else if (estimatedTotal <= 600 && estimatedTotal > 500) batRunsFitness = 10;
		else if (estimatedTotal <= 500 && estimatedTotal > 400) batRunsFitness = 8;
		else if (estimatedTotal <= 400 && estimatedTotal > 300) batRunsFitness = 6;
		else if (estimatedTotal <= 300 && estimatedTotal > 200) batRunsFitness = 4;
		else if (estimatedTotal <= 200) batRunsFitness = 2;
		
		//batting Runs fitness criteria
		if (p.hundredScored > 6) bat100sFitness = 20; 	
		else if (p.hundredScored <= 6 && p.hundredScored > 4) bat100sFitness = 15;
		else if (p.hundredScored <= 4 && p.hundredScored > 2) bat100sFitness = 10;
		else if (p.hundredScored <= 2 && p.hundredScored > 0) bat100sFitness = 5;
		
		//batting Runs fitness criteria
		if (p.fiftiesScored > 10) bat50sFitness = 20 * 6/6; 	
		else if (p.fiftiesScored <= 10 && p.fiftiesScored > 8) bat50sFitness = 20 * 5/6;
		else if (p.fiftiesScored <= 8 && p.fiftiesScored > 6) bat50sFitness = 20 * 4/6;
		else if (p.fiftiesScored <= 6 && p.fiftiesScored > 4) bat50sFitness = 20 * 3/6;
		else if (p.fiftiesScored <= 4 && p.fiftiesScored > 2) bat50sFitness = 20 * 2/6;
		else if (p.fiftiesScored <= 2 && p.fiftiesScored > 0) bat50sFitness = 20 * 1/6;
		
		battingFitness = batAvgFitness + batSRFitness + batRunsFitness + bat100sFitness + bat50sFitness;
		
		return battingFitness;
	}

	public static Double getBowlingFitness(Allele[] geneticCode) {
		// TODO Auto-generated method stub
		double fitness = 0.0;		
		for(int i = 0 ; i < geneticCode.length ; i++) {
			int playerIndex = geneticCode[i].getGene();
			Players p = Configuration.PLAYERS.get(playerIndex);
			if(p.isBaller == 1) {
				fitness += calculateBallerFitness(p);
			}else {
				continue;
			}
		}	
		//return (fitness/1100)*100;
		return (1100/(fitness+1));
	}
	
	private static double calculateBallerFitness(Players p) {
		// TODO Auto-generated method stub
		double bowlingFitness = 0.0;
		// each criteria will score marks out of maximum 20
		int bowlAvgFitness = 0;
		int bowlSRFitness = 0;
		int bowlEconFitness = 0;
		int bowlWicketsFitness = 0;
		int oversBowledFitness = 0;
		
		//bowling average (runs conceded per wicket) fitness criteria
		if (p.bowlingAvg > 50) bowlAvgFitness = 100 * 1/7; 	
		else if (p.bowlingAvg <= 50 && p.bowlingAvg > 45) bowlAvgFitness = 100 * 2/7;
		else if (p.bowlingAvg <= 45 && p.bowlingAvg > 40) bowlAvgFitness = 100 * 3/7;
		else if (p.bowlingAvg <= 40 && p.bowlingAvg > 35) bowlAvgFitness = 100 * 4/7;
		else if (p.bowlingAvg <= 35 && p.bowlingAvg > 30) bowlAvgFitness = 100 * 5/7;
		else if (p.bowlingAvg <= 30 && p.bowlingAvg > 25) bowlAvgFitness = 100 * 6/7;
		else if (p.bowlingAvg <= 25 && p.bowlingAvg > 0) bowlAvgFitness = 100 * 7/7;
		
		//bowling strike rate (balls bowled per wicket) fitness criteria
		if (p.bowlingStrikeRate > 50) bowlSRFitness = 20 * 1/7; 	
		else if (p.bowlingStrikeRate <= 50 && p.bowlingStrikeRate > 45) bowlSRFitness = 20 * 2/7;
		else if (p.bowlingStrikeRate <= 45 && p.bowlingStrikeRate > 40) bowlSRFitness = 20 * 3/7;
		else if (p.bowlingStrikeRate <= 40 && p.bowlingStrikeRate > 35) bowlSRFitness = 20 * 4/7;
		else if (p.bowlingStrikeRate <= 35 && p.bowlingStrikeRate > 30) bowlSRFitness = 20 * 5/7;
		else if (p.bowlingStrikeRate <= 30 && p.bowlingStrikeRate > 25) bowlSRFitness = 20 * 6/7;
		else if (p.bowlingStrikeRate <= 25 && p.bowlingStrikeRate > 0) bowlSRFitness = 20 * 7/7;
		
		//bowling economy rate (runs conceded per over) fitness criteria
		if (p.bowlingEconomyRate > 8) bowlEconFitness = 20 * 1/6; 	
		else if (p.bowlingEconomyRate <= 8 && p.bowlingEconomyRate > 7) bowlEconFitness = 20 * 2/6; 	
		else if (p.bowlingEconomyRate <= 7 && p.bowlingEconomyRate > 6) bowlEconFitness = 20 * 3/6; 	
		else if (p.bowlingEconomyRate <= 6 && p.bowlingEconomyRate > 5) bowlEconFitness = 20 * 4/6; 	
		else if (p.bowlingEconomyRate <= 5 && p.bowlingEconomyRate > 4) bowlEconFitness = 20 * 5/6; 	
		else if (p.bowlingEconomyRate <= 4 && p.bowlingEconomyRate > 0) bowlEconFitness = 20 * 6/6; 	

		//wickets fitness criteria
		if (p.wicketsTaken > 15) bowlWicketsFitness = 20 * 6/6; 	
		else if (p.wicketsTaken <= 15 && p.wicketsTaken > 12) bowlWicketsFitness = 20 * 5/6; 	
		else if (p.wicketsTaken <= 12 && p.wicketsTaken > 9) bowlWicketsFitness = 20 * 3/6; 	
		else if (p.wicketsTaken <= 9 && p.wicketsTaken > 6) bowlWicketsFitness = 20 * 3/6; 	
		else if (p.wicketsTaken <= 6 && p.wicketsTaken > 3) bowlWicketsFitness = 20 * 2/6; 	
		else if (p.wicketsTaken <= 3 && p.wicketsTaken > 0) bowlWicketsFitness = 20 * 1/6;
		
		// overs bowled per an inning
		// this criteria would be high for a regular well performing bowler 
		double oversPerInning = p.oversBowled / p.bowlingInnings;
		 	
		if (oversPerInning <= 10 && oversPerInning > 8) oversBowledFitness = 20; 	
		else if (oversPerInning <= 8 && oversPerInning > 6) oversBowledFitness = 16; 	
		else if (oversPerInning <= 6 && oversPerInning > 4) oversBowledFitness = 12; 	
		else if (oversPerInning <= 4 && oversPerInning > 2) oversBowledFitness = 8; 	
		else if (oversPerInning <= 2 && oversPerInning > 0) oversBowledFitness = 4;
		
		bowlingFitness = bowlAvgFitness + bowlSRFitness + bowlEconFitness + bowlWicketsFitness + oversBowledFitness;
		return bowlingFitness;
	}

	public static boolean isValidSequence(HashSet<Integer> team) {
		int outSider = 0;
		boolean captainFound = false;
		boolean wicketKeeperFound = false;
		long cost = 0;
		boolean ballerFound = false;
		boolean batsmanFound = false;
		for(Integer i : team) {
			Players p = Configuration.PLAYERS.get(i);
			if(p.isBatsman == 1) {
				batsmanFound = true;
			}
			if(p.isBaller == 1) {
				ballerFound = true;
			}
			if(p.isCaptain == 1) {
				captainFound = true;
			}
			if(p.isWicketKeeper == 1) {
				wicketKeeperFound = true;
			}
			if(p.isOutsider == 1) {
				outSider++;
			}
			cost += p.cost;
		}
		return captainFound && wicketKeeperFound && ballerFound && batsmanFound && (cost <= Configuration.BUDGET) && (outSider <= Configuration.OUTSIDERS);	
	}
	
	public static void randomizedQuickSortForRank(final List<Chromosome> populace, final int head, final int tail) {
        
        if(head < tail) {
            
            int pivot = Utilities.randomizedPartitionForRank(populace, head, tail);
            
            Utilities.randomizedQuickSortForRank(populace, head, pivot - 1);
            Utilities.randomizedQuickSortForRank(populace, pivot + 1, tail);
        }
    }
	
	public static void sortForCrowdingDistance(final List<Chromosome> populace, final int lastNonDominatedSetRank) {
        
        int rankStartIndex = -1;
        int rankEndIndex = -1;
        
        for(int i = 0; i < populace.size(); i++)
            if((rankStartIndex < 0) && (populace.get(i).getRank() == lastNonDominatedSetRank)) rankStartIndex = i;
            else if((rankStartIndex >= 0) && (populace.get(i).getRank() == lastNonDominatedSetRank)) rankEndIndex = i;
        
        Utilities.randomizedQuickSortForCrowdingDistance(populace, rankStartIndex, rankEndIndex);
    }
	
	public static void sortAgainstObjective(final Chromosome[] populace, int objectiveIndex) {
		Utilities.randomizedQuickSortAgainstObjective(populace, 0, populace.length - 1, objectiveIndex);
    }
	
	public static double selectMaximumObjectiveValue(final Chromosome[] populace, int objectiveIndex) {
        
        double result = populace[0].getObjectiveValues().get(objectiveIndex);
        
        for(Chromosome chromosome : populace) if(chromosome.getObjectiveValues().get(objectiveIndex) > result) result = chromosome.getObjectiveValues().get(objectiveIndex);
        
        return result;
    }
	
	public static double selectMinimumObjectiveValue(final Chromosome[] populace, int objectiveIndex) {
        
        double result = populace[0].getObjectiveValues().get(objectiveIndex);
        
        for(Chromosome chromosome : populace) if(chromosome.getObjectiveValues().get(objectiveIndex) < result) result = chromosome.getObjectiveValues().get(objectiveIndex);
        
        return result;
    }
	
	private static int randomizedPartitionForRank(final List<Chromosome> populace, final int head, final int tail) {
        
		Utilities.swapForRank(populace, head, ThreadLocalRandom.current().nextInt(head, tail + 1));
        
        return Utilities.partitionForRank(populace, head, tail);
    }
    
    private static void swapForRank(final List<Chromosome> populace, final int firstIndex, final int secondIndex) {
        
        Chromosome temporary = populace.get(firstIndex);
        
        populace.set(firstIndex, populace.get(secondIndex));
        populace.set(secondIndex, temporary);
    }
    
    private static int partitionForRank(final List<Chromosome> populace, final int head, final int tail) {
        
        int pivot = populace.get(tail).getRank();
        int pivotIndex = head;
        
        for(int j = head; j < tail; j++) {
            
            if(populace.get(j).getRank() <= pivot) {
                
            	Utilities.swapForRank(populace, pivotIndex, j);
                ++pivotIndex;
            }
        }
        
        Utilities.swapForRank(populace, pivotIndex, tail);
        
        return pivotIndex;
    }
    
    private static void randomizedQuickSortForCrowdingDistance(final List<Chromosome> populace, final int head, final int tail) {
        
        if(head < tail) {
            
            int pivot = Utilities.randomizedPartitionForCrowdingDistance(populace, head, tail);
            
            Utilities.randomizedQuickSortForCrowdingDistance(populace, head, pivot - 1);
            Utilities.randomizedQuickSortForCrowdingDistance(populace, pivot + 1, tail);
        }
    }
    
    private static int randomizedPartitionForCrowdingDistance(final List<Chromosome> populace, final int head, final int tail) {
        
    	Utilities.swapForCrowdingDistance(populace, head, ThreadLocalRandom.current().nextInt(head, tail + 1));
        
        return Utilities.partitionForCrowdingDistance(populace, head, tail);
    }
    
    private static void swapForCrowdingDistance(final List<Chromosome> populace, final int firstIndex, final int secondIndex) {
        
        Chromosome temporary = populace.get(firstIndex);
        
        populace.set(firstIndex, populace.get(secondIndex));
        populace.set(secondIndex, temporary);
    }
    
    private static int partitionForCrowdingDistance(final List<Chromosome> populace, final int head, final int tail) {
        
        double pivot = populace.get(tail).getCrowdingDistance();
        int pivotIndex = head;
        
        for(int j = head; j < tail; j++) {
            
            if(populace.get(j).getCrowdingDistance() >= pivot) {
                
            	Utilities.swapForRank(populace, pivotIndex, j);
                ++pivotIndex;
            }
        }
        
        Utilities.swapForRank(populace, pivotIndex, tail);
        
        return pivotIndex;
    }
    
    private static void randomizedQuickSortAgainstObjective(final Chromosome[] populace, final int head, final int tail, final int objectiveIndex) {
        
        if(head < tail) {
            
            int pivot = Utilities.randomizedPartitionAgainstObjective(populace, head, tail, objectiveIndex);
            
            Utilities.randomizedQuickSortAgainstObjective(populace, head, pivot - 1, objectiveIndex);
            Utilities.randomizedQuickSortAgainstObjective(populace, pivot + 1, tail, objectiveIndex);
        }
    }
    
    private static int randomizedPartitionAgainstObjective(final Chromosome[] populace, final int head, final int tail, final int objectiveIndex) {
        
    	Utilities.swapAgainstObjective(populace, head, ThreadLocalRandom.current().nextInt(head, tail + 1));
        
        return Utilities.partitionAgainstObjective(populace, head, tail, objectiveIndex);
    }
    
    private static void swapAgainstObjective(final Chromosome[] populace, final int firstIndex, final int secondIndex) {
        
        Chromosome temporary = populace[firstIndex];
        populace[firstIndex] = populace[secondIndex];
        populace[secondIndex] = temporary;
    }
    
    private static int partitionAgainstObjective(final Chromosome[] populace, final int head, final int tail, final int objectiveIndex) {
        
        double pivot = populace[tail].getObjectiveValues().get(objectiveIndex);
        int pivotIndex = head;
        
        for(int j = head; j < tail; j++) {
            
            if(populace[j].getObjectiveValues().get(objectiveIndex) <= pivot) {
                
            	Utilities.swapAgainstObjective(populace, pivotIndex, j);
                ++pivotIndex;
            }
        }
        
        Utilities.swapAgainstObjective(populace, pivotIndex, tail);
        
        return pivotIndex;
    }
}
