package pl.edu.agh.operationsresearch.algorithm.model;

public class AlgorithmCore {

    private double evaporationRate;
    private int antsNumber;

    private int global_max_selected;

    private static final int GRID_SIZE = 9;
    private static final int NUMBERS = 10;
    private static final int PHEROMONES_MAX = 1000;

    // TODO: ??? I am not sure what a cycle is :D
    private static final int CYCLES_NUMBER = 1000;

    // indicates pheromone value on position (i, j) with number k
    private double pheromone_value[][][];

    public AlgorithmCore(double evaporationRate, int antsNumber) {
        this.evaporationRate = evaporationRate;
        this.antsNumber = antsNumber;
    }

    private void initialize() {
        pheromone_value = new double[GRID_SIZE][GRID_SIZE][NUMBERS];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                for (int k = 0; k < NUMBERS; k++) {
                    pheromone_value[i][j][k] = PHEROMONES_MAX;
                }
            }
        }
        global_max_selected = 0;
    }

    public void start() {
        initialize();

        int cycle = 0, max_selected;

        while (cycle++ < CYCLES_NUMBER) {
            max_selected = 0;

            for (int ant = 0; ant < antsNumber; ant++) {
                // TODO should ant be a class?

                // TODO how can I achieve sudokuGrid from GridController from here?
            }
        }
    }


}
