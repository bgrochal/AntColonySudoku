package pl.edu.agh.operationsresearch.algorithm.model;

import pl.edu.agh.operationsresearch.grid.controller.GridController;
import pl.edu.agh.operationsresearch.grid.model.GridCell;

import java.util.Arrays;

public class AlgorithmCore {
    private static final int GRID_SIZE = 9;
    private static final int NUMBERS = 10;
    private static final int PHEROMONES_MAX = 1000;
    private static final int CYCLES_NUMBER = 1000;

    private double evaporationRate;
    private int antsNumber;
    private int globalMaxSelected;

    // indicates pheromone value on position (i, j) with number k
    private double[][][] pheromoneValue;

    private boolean[][] isDigitSelectedInColumn;
    private boolean[][] isDigitSelectedInRow;


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

        isDigitSelectedInColumn = new boolean[GRID_SIZE][NUMBERS];
        isDigitSelectedInRow = new boolean[GRID_SIZE][NUMBERS];

        for(int i=0; i<GRID_SIZE; i++) {
            Arrays.fill(isDigitSelectedInColumn[i], false);
            Arrays.fill(isDigitSelectedInRow[i], false);
        }
    }

    private void cycleLoop() {
        GridCell[][] resultMatrix = GridController.getInstance().getSudokuGrid();
        GridCell[][] workMatrix = new GridCell[GRID_SIZE][GRID_SIZE];

        int currentlySelected;
        int maxSelected;

        boolean canSelect;


        for (int cycle = 0; cycle < CYCLES_NUMBER; cycle++) {
            maxSelected = 0;

            for (int ant = 0; ant < antsNumber; ant++) {
                // TODO should ant be a class? Answer: Probably no.
                // TODO how can I achieve sudokuGrid from GridController from here? Answer: GridController.getInstance()

                rewriteSudokuGrid(resultMatrix, workMatrix);
                currentlySelected = 0;
                canSelect = true;

                // Caution! Variable canSelect is always true now, so algorithm is working infinitely.
                while(canSelect) {
                    currentlySelected = markSelectedPositions(resultMatrix);
                }
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

    private void rewriteSudokuGrid(GridCell[][] source, GridCell[][] destination) {
        for(int i=0; i<GRID_SIZE; i++) {
            destination[i] = Arrays.copyOf(source[i], source[i].length);
        }
    }

    private int markSelectedPositions(GridCell[][] matrix) {
        int selected = 0;

        for(int i=0; i<GRID_SIZE; i++) {
            for(int j=0; j<GRID_SIZE; j++) {
                if(matrix[i][j].getValue() != 0) {
                    int insertedValue = matrix[i][j].getValue();
                    isDigitSelectedInRow[i][insertedValue] = true;
                    isDigitSelectedInColumn[j][insertedValue] = true;

                    selected++;
                }
            }
        }

        return selected;
    }

}
