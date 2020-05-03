package moga.Utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.HashMap;

import moga.Data.Players;

public class Configuration {
	public static int POPULATION_SIZE = 200;
	public static int GENERATIONS = 25;
	public static int TOTAL_PLAYERS;	
	
	public static final float CROSSOVER_PROBABILITY = 0.7f;
    public static final float MUTATION_PROBABILITY = 0.03f;
	public static final long BUDGET = 6000000;
	public static final int OUTSIDERS = 5;
	
	public static final HashMap<Integer , Players> PLAYERS = new HashMap<Integer , Players>();
	
	public static void generatePlayers()throws IOException {
		
		String csvFile = "/home/fancyarrow/Downloads/IPL.csv";
        String line = "";      
        BufferedReader br = null;
        
        try {
            br = new BufferedReader(new FileReader(csvFile));
            int index = 0;
            while ((line = br.readLine()) != null) {
            	PLAYERS.put(index , new Players(index , line));
            	index++;
            }
            TOTAL_PLAYERS = PLAYERS.size();	            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}	
}	
