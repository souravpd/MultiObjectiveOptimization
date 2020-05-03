package moga.Utilities;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.util.Random;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import moga.DataStructures.Allele;
import moga.DataStructures.Population;

public class GraphPlot extends ApplicationFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -7480629328018652407L;
	
	
	private final static XYSeriesCollection DATASET = new XYSeriesCollection();
    private final static XYSeriesCollection MULTIPLE_DATASET = new XYSeriesCollection();
    private final static XYLineAndShapeRenderer MULTIPLE_RENDERER = new XYLineAndShapeRenderer();
    
    private final static String APPLICATION_TITLE = "Cricket Team Selection";
    private static String KEY = "Pareto Front";
    private static String GRAPH_TITLE = "PARETO FRONT";
    private static float STROKE_THICKNESS = 2.0f;
    private static int DIMENSION_X = 800;
    private static int DIMENSION_Y = 600;
    private static Paint COLOR = Color.RED;
    
    private static final Random RANDOM = new Random();
    
    public GraphPlot() {
        super(GraphPlot.APPLICATION_TITLE);
    }
    
    public GraphPlot(Population population) {
        
        super(GraphPlot.APPLICATION_TITLE);
        this.createDataset(population);
    }
    
    public void prepareMultipleDataset(final Population population, final int datasetIndex, final String key) {
        
        this.createDataset(population, key, GraphPlot.MULTIPLE_DATASET);
        
        GraphPlot.MULTIPLE_RENDERER.setSeriesPaint(datasetIndex, new Color(GraphPlot.RANDOM.nextFloat(), GraphPlot.RANDOM.nextFloat(), GraphPlot.RANDOM.nextFloat()));
        GraphPlot.MULTIPLE_RENDERER.setSeriesStroke(datasetIndex, new BasicStroke(GraphPlot.STROKE_THICKNESS));
    }
    
    public void configureMultiplePlotter(final String x_axis, final String y_axis, final String graphTitle) {
        
        JFreeChart xyLineChart = ChartFactory.createXYLineChart(graphTitle, x_axis, y_axis, GraphPlot.MULTIPLE_DATASET, PlotOrientation.VERTICAL, true, true, false);
        ChartPanel chartPanel = new ChartPanel(xyLineChart);
        
        chartPanel.setPreferredSize(new java.awt.Dimension(GraphPlot.DIMENSION_X, GraphPlot.DIMENSION_Y));
        
        final XYPlot plot = xyLineChart.getXYPlot();
        
        plot.setRenderer(GraphPlot.MULTIPLE_RENDERER);
        setContentPane(chartPanel);
    }
    
    public void configurePlotter(final String x_axis, final String y_axis) {
        
        JFreeChart xyLineChart = ChartFactory.createXYLineChart(GraphPlot.GRAPH_TITLE, x_axis, y_axis, GraphPlot.DATASET, PlotOrientation.VERTICAL, true, true, false);
        ChartPanel chartPanel = new ChartPanel(xyLineChart);
        
        chartPanel.setPreferredSize(new java.awt.Dimension(GraphPlot.DIMENSION_X, GraphPlot.DIMENSION_Y));
        
        final XYPlot plot = xyLineChart.getXYPlot();
        
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        
        renderer.setSeriesPaint(0, GraphPlot.COLOR);
        renderer.setSeriesStroke(0, new BasicStroke(GraphPlot.STROKE_THICKNESS));
        
        plot.setRenderer(renderer);
        setContentPane(chartPanel);
    }
    
    private void createDataset(final Population population, String key, XYSeriesCollection dataset) {
        
        final XYSeries paretoFront = new XYSeries(key);
        
        population.getPopulace().stream().forEach((chromosome) -> {
            System.out.println("Batting Fitness" + " : " + chromosome.getObjectiveValues().get(0) + " | "
                                + "Balling Fitness" + " : " + chromosome.getObjectiveValues().get(1));
            System.out.print("{ ");
            for(Allele a : chromosome.getGeneticCode()) {
            	System.out.print(Configuration.PLAYERS.get(a.getGene()).playerName + " , ");
            }
            System.out.print(" }");
            System.out.println();
        });
        
        population.getPopulace().stream().forEach((chromosome) -> { paretoFront.add(chromosome.getObjectiveValues().get(0), chromosome.getObjectiveValues().get(1)); });
        
        dataset.addSeries(paretoFront);
    }
    
    private void createDataset(final Population population) {
        this.createDataset(population, KEY);
    }
    
    private void createDataset(final Population population, String key) {
        this.createDataset(population, key, DATASET);
    }
}