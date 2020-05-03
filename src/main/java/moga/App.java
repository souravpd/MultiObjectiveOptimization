package moga;

import java.io.IOException;

import org.jfree.ui.RefineryUtilities;

import moga.Algorithm.GA;
import moga.Algorithm.NSGA;
import moga.DataStructures.Population;
import moga.Utilities.Configuration;
import moga.Utilities.GraphPlot;
import moga.Utilities.Printer;

public class App {
	public static void main(String args[])throws IOException{
		
		Configuration.generatePlayers();	
		GraphPlot multiPlotGraph = new GraphPlot();		

		
		Printer.printInitialParentPopulationGeneration();
		Printer.printGeneration(1);
		
		Population parent = NSGA.preparePopulation(GA.generatePopulation());
		Population child = GA.generateChildren(parent);
		
		Population combinedPopulation;
		
		for(int generation = 1; generation <= Configuration.GENERATIONS; generation++) {
            
            Printer.printGeneration(generation + 1);
            
            combinedPopulation = NSGA.preparePopulation(GA.createCombinedPopulation(parent, child));
            parent = NSGA.getChildFromCombinedPopulation(combinedPopulation);
            child = GA.generateChildren(parent);
            
            multiPlotGraph.prepareMultipleDataset(child, generation, "gen. " + generation);
            
        }
		
		Printer.printGraphPlotAlert();
	    Printer.render2DGraph(child);
	    
	    multiPlotGraph.configureMultiplePlotter("Batting Fitness ", "Balling Fitness ", "All Pareto");
        multiPlotGraph.pack();
        RefineryUtilities.centerFrameOnScreen(multiPlotGraph);
        multiPlotGraph.setVisible(true);
        
        
		
		Printer.printAlgorithmEnd();
		
	}
}
