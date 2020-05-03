package moga.Data;

public class Players {
	
	public int playerId;
	public String playerName;
	
	public int isBatsman;
	public int isBaller;
	public int matchesPlayed;
	
	//Batting statistics
	public int totalRuns;
	public Double battingAvg;
	public Double battingStrikeRate;
	public int hundredScored;
	public int fiftiesScored;
	
	//Bowling statistics	
	public Double bowlingAvg;
	public Double bowlingStrikeRate;
	public Double bowlingEconomyRate;
	public int wicketsTaken;
	public Double oversBowled;
	public int bowlingInnings;

	public long cost;
	
	public int isCaptain;
	public int isWicketKeeper;
	public int isOutsider;
	
	public Players(int id , String s) {
		this.playerId = id;
		
		String vals[] = s.split(",");

		this.playerName = vals[0];
		
		this.isBatsman = Integer.parseInt(vals[1]);
		this.isBaller = Integer.parseInt(vals[2]);
		
		this.matchesPlayed = Integer.parseInt(vals[3]);
		
		this.totalRuns = Integer.parseInt(vals[4]);
		this.battingAvg = Double.parseDouble(vals[5]);
		this.battingStrikeRate = Double.parseDouble(vals[6]);
		this.hundredScored = Integer.parseInt(vals[7]);
		this.fiftiesScored = Integer.parseInt(vals[8]);	
		
		this.bowlingInnings = Integer.parseInt(vals[9]);
		this.oversBowled = Double.parseDouble(vals[10])/6.0;
		this.wicketsTaken = Integer.parseInt(vals[11]);
		this.bowlingAvg = Double.parseDouble(vals[12]);
		this.bowlingEconomyRate = Double.parseDouble(vals[13]);
		this.bowlingStrikeRate = Double.parseDouble(vals[14]);
		
		this.cost = Long.parseLong(vals[15]);
		this.isCaptain = Integer.parseInt(vals[16]);
		this.isWicketKeeper = Integer.parseInt(vals[17]);
		this.isOutsider = Integer.parseInt(vals[18]);	
		
	}
}
/**
 * Name,         0
 * 
 * Is batsman,   1
 * 
 * Is bowler?,   2
 * 
 * matches,      3
 * 
 * runs scored, 4 
 * 
 * Batting avg, 5
 * 
 * Strike rate, 6
 * 
 * 100 runs made, 7
 * 
 * 50 runs made, 8
 * 
 * Bowl_Inns, 9
 * 
 * Number of balls bowled, 10
 * 
 * wkts taken, 11
 * 
 * Bowling average, 12
 * 
 * Bowling econ, 13
 * 
 * sr, 14
 * 
 * Player cost USD, 15
 * 
 * Is Captain(1=yes), 16
 * 
 * Is Wktkeeper(1=Yes), 17
 * 
 * Nationality(1=Overseas) 18
 */
