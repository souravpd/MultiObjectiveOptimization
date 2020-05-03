package moga.Utilities;

import org.jfree.ui.RefineryUtilities;

import moga.DataStructures.Population;

public class Printer {
	
	public static void printInitialParentPopulationGeneration() {        
        System.out.println("\n\n=============================================================");
        System.out.println("CREATING INITIAL PARENT POPULATION");
        System.out.println("=============================================================\n\n");
    }
	
	public static void printGeneration(int generation) {        
        System.out.println("\n\n=============================================================");
        System.out.println("GENERATION : " + generation);
        System.out.println("=============================================================\n\n");
        
    }
	
	public static void printAlgorithmEnd() {
        
        System.out.println("\n\n=============================================================");
        System.out.println("ALGORITHM ENDED SUCCESSFULLY");
        System.out.println("=============================================================\n\n");
    }
	
	public static void printGraphPlotAlert() {
        System.out.println("\n\n=============================================================");
        System.out.println("CHECK PARETO FRONT OUTPUT");
        System.out.println("=============================================================\n\n");
    }
    
    public static void render2DGraph(final Population population) {
        
        GraphPlot graph = new GraphPlot(population);
                
        graph.configurePlotter("Batting Fitness ", "Balling Fitness ");
        graph.pack();

        RefineryUtilities.centerFrameOnScreen(graph);

        graph.setVisible(true);
    }
}
