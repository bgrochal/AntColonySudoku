package pl.edu.agh.operationsresearch.algorithm.model;

public class AlgorithmCore {
    private static final int GRID_SIZE = 9;
    private static final int NUMBERS = 10;
    private static final int PHEROMONES_MAX = 1000;
    private static final int CYCLES_NUMBER = 1000;

    private double evaporationRate;
    private int antsNumber;
    private int globalMaxSelected;

    // indicates pheromone value on position (i, j) with number k
    private double pheromoneValue[][][];

    public AlgorithmCore(double evaporationRate, int antsNumber) {
        this.evaporationRate = evaporationRate;
        this.antsNumber = antsNumber;
    }

    public void start() {
        initialize();
        cycleLoop();
    }

    private void initialize() {
        initializePheromones();
        globalMaxSelected = 0;
    }

    private void cycleLoop() {
        int maxSelected;

        for (int cycle = 0; cycle < CYCLES_NUMBER; cycle++) {
            maxSelected = 0;

            for (int ant = 0; ant < antsNumber; ant++) {
                // TODO should ant be a class?

                // TODO how can I achieve sudokuGrid from GridController from
                // here?
            }
        }
    }

    private void initializePheromones() {
        pheromoneValue = new double[GRID_SIZE][GRID_SIZE][NUMBERS];

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                for (int k = 0; k < NUMBERS; k++) {
                    pheromoneValue[i][j][k] = PHEROMONES_MAX;
                }
            }
        }
    }
}
