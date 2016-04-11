package pl.edu.agh.operationsresearch.algorithm.model;

import pl.edu.agh.operationsresearch.algorithm.utils.CannotSelectException;
import pl.edu.agh.operationsresearch.grid.controller.GridController;
import pl.edu.agh.operationsresearch.grid.model.GridCell;

import java.util.Arrays;

public class AlgorithmCore {
    private static final int SUB_GRIDS = 3;
    private static final int GRID_SIZE = 9;
    private static final int NUMBERS = 10;
    private static final int PHEROMONES_MAX = 1000;
    private static final int CYCLES_NUMBER = 1000;

    private double evaporationRate;

    private int antsNumber;
    private int globalMaxSelected;

    // indicates pheromone value on position (i, j) with number k
    private double[][][] pheromoneValue;

    private int[][][] availableSubGridPlaces;

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

        availableSubGridPlaces = new int[SUB_GRIDS][SUB_GRIDS][NUMBERS];

        for(int i=0; i<SUB_GRIDS; i++) {
            for(int j=0; j<SUB_GRIDS; j++) {
                Arrays.fill(availableSubGridPlaces[i][j], 9);
            }
        }

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
                    currentlySelected = markSelectedPositions(currentlySelected, resultMatrix);

                    try {
                        currentlySelected = manageSubGrids(currentlySelected, workMatrix);
                    } catch (CannotSelectException e) {
                        canSelect = false;
                    }

                    // ...
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

    private int markSelectedPositions(int selected, GridCell[][] matrix) {
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

    private int manageSubGrids(int selected, GridCell[][] matrix) throws CannotSelectException {
        for(int i=0; i<SUB_GRIDS; i++) {
            for(int j=0; j<SUB_GRIDS; j++) {
                for(int digit=1; digit<NUMBERS; digit++) {
                    int possiblePlaces = 0;

                    duplicateInSubGridBreak:
                    for(int row=i*3; row<(i+1)*3; row++) {
                        for(int column = j*3; column<(j+1)*3; column++) {
                            if(matrix[row][column].getValue() == digit) {
                                possiblePlaces = 0;
                                break duplicateInSubGridBreak;
                            }

                            if(isDigitSelectedInRow(row, digit, matrix) || isDigitSelectedInColumn(column, digit, matrix)) {
                                continue;
                            }

                            possiblePlaces++;
                        }
                    }

                    availableSubGridPlaces[i][j][digit] = possiblePlaces;
                }
            }
        }

        boolean canSelect = false;
        
        selectBreak:
        for(int i=0; i<SUB_GRIDS; i++) {
            for (int j = 0; j < SUB_GRIDS; j++) {
                for (int digit = 1; digit < NUMBERS; digit++) {
                    if(availableSubGridPlaces[i][j][digit] > 0) {
                        canSelect = true;
                        break selectBreak;
                    }
                }
            }
        }

        if(!canSelect) {
            throw new CannotSelectException();
        }

        for(int i=0; i<SUB_GRIDS; i++) {
            for (int j = 0; j < SUB_GRIDS; j++) {
                for (int digit = 1; digit < NUMBERS; digit++) {

                    if(availableSubGridPlaces[i][j][digit] == 1) {
                        for(int row=i*3; row<(i+1)*3; row++) {
                            for(int column = j*3; column<(j+1)*3; column++) {
                                if(!isDigitSelectedInRow(row, digit, matrix) && !isDigitSelectedInColumn(column, digit, matrix)) {
                                    // TODO: Probably there should be a validation with some exception thrown:
                                    // if(matrix[row][column].getValue() != 0) {
                                    //     ...
                                    // }

                                    matrix[row][column].setValue(digit);
                                    selected++;
                                }
                            }
                        }
                    }

                }
            }
        }

        return selected;
    }

    private boolean isDigitSelectedInRow(int row, int digit, GridCell[][] matrix) {
        for(int i=0; i<GRID_SIZE; i++) {
            if(matrix[row][i].getValue() == digit) {
                return true;
            }
        }

        return false;
    }

    private boolean isDigitSelectedInColumn(int column, int digit, GridCell[][] matrix) {
        for(int i=0; i<GRID_SIZE; i++) {
            if(matrix[i][column].getValue() == digit) {
                return true;
            }
        }

        return false;
    }

}
