package pl.edu.agh.operationsresearch.algorithm.model;

import java.util.Arrays;
import java.util.Random;

import pl.edu.agh.operationsresearch.algorithm.utils.CannotSelectException;
import pl.edu.agh.operationsresearch.grid.controller.GridController;
import pl.edu.agh.operationsresearch.grid.model.Grid;

public class AlgorithmCore {
    private static final int SUB_GRIDS = 3;
    private static final int NUMBERS = 10;
    private static final int PHEROMONES_MAX = 1000;
    private static final int CYCLES_NUMBER = 1000;

    private double evaporationRate;
    private int antsNumber;

    private Random randomGenerator;

    // indicates pheromone value on position (i, j) with number k
    private double[][][] pheromoneValue;
    private double[][][] probability;
    private double[][][] weights;

    private int[][][] availableSubGridPlaces;
    private int[][] availableGridCellDigits;

    private GridController gridCtrl;
    private Grid result;

    public AlgorithmCore(double evaporationRate, int antsNumber,
            GridController gridCtrl) {
        this.evaporationRate = evaporationRate;
        this.antsNumber = antsNumber;
        this.gridCtrl = gridCtrl;

        initialize();
        cycleLoop();
    }

    private void initialize() {
        initializePheromones();
        initializeArrays();

        randomGenerator = new Random();
    }

    private void initializePheromones() {
        pheromoneValue = new double[Grid.GRID_SIZE][Grid.GRID_SIZE][NUMBERS];

        for (int i = 0; i < Grid.GRID_SIZE; i++) {
            for (int j = 0; j < Grid.GRID_SIZE; j++) {
                for (int k = 0; k < NUMBERS; k++) {
                    pheromoneValue[i][j][k] = PHEROMONES_MAX;
                }
            }
        }
    }

    private void initializeArrays() {
        probability = new double[Grid.GRID_SIZE][Grid.GRID_SIZE][NUMBERS];
        weights = new double[Grid.GRID_SIZE][Grid.GRID_SIZE][NUMBERS];

        availableSubGridPlaces = new int[SUB_GRIDS][SUB_GRIDS][NUMBERS];
        availableGridCellDigits = new int[Grid.GRID_SIZE][Grid.GRID_SIZE];

        for (int i = 0; i < SUB_GRIDS; i++) {
            for (int j = 0; j < SUB_GRIDS; j++) {
                Arrays.fill(availableSubGridPlaces[i][j], 9);
            }
        }
    }

    private void singleCycle(Grid best) {
        boolean canSelect;

        Grid tmp = new Grid();

        for (int ant = 0; ant < antsNumber; ant++) {
            tmp.set(result.values());
            canSelect = true;

            while (canSelect) {
                try {
                    manageSubGrids(tmp);
                } catch (CannotSelectException e) {
                    canSelect = false;
                }

                manageWholeGrid(tmp);
                calculateProbability();

                // TODO: Not sure if it is correct.
                double p = randomGenerator.nextDouble();
                for (int i = 0; i < Grid.GRID_SIZE; i++) {
                    for (int j = 0; j < Grid.GRID_SIZE; j++) {
                        if (tmp.isset(i, j)) {
                            continue;
                        }

                        double probabilitiesSum = 0;
                        for (int k = 1; k < NUMBERS; k++) {
                            probabilitiesSum += probability[i][j][k];
                            if (probabilitiesSum > p) {
                                tmp.set(i, j, k);

                                // TODO: Should we now check, whether this
                                // selection hasn't caused a conflict with
                                // previously selected items?

                                break;
                            }
                        }
                    }
                }

                if (tmp.selected() == 81) {
                    canSelect = false;
                }
            }

            if (tmp.selected() > best.selected()) {
                best.set(tmp.values());
            }
        }

        evaporatePheromones();
        updatePheromones(best);

        if (best.selected() > result.selected()) {
            result.set(best.values());
        }
    }

    private void updatePheromones(Grid best) {
        double pheromoneLeft = best.selected() / 81;

        for (int i = 0; i < Grid.GRID_SIZE; i++) {
            for (int j = 0; j < Grid.GRID_SIZE; j++) {
                if (best.isset(i, j)) {
                    pheromoneValue[i][j][best.get(i, j)] += pheromoneLeft;
                }
            }
        }
    }

    private void cycleLoop() {
        Grid best = new Grid();
        result = gridCtrl.getGrid();

        for (int cycle = 0; cycle < CYCLES_NUMBER; cycle++) {
            System.out.println("Cycle " + cycle + ".");
            singleCycle(best);
        }

        System.out.println("Finished.");

        gridCtrl.setGrid(result);
    }

    private void evaporatePheromones() {
        for (int i = 0; i < Grid.GRID_SIZE; i++) {
            for (int j = 0; j < Grid.GRID_SIZE; j++) {
                for (int k = 1; k < NUMBERS; k++) {
                    pheromoneValue[i][j][k] *= evaporationRate;
                }
            }
        }
    }

    private void manageSubGrids(Grid matrix) throws CannotSelectException {
        for (int i = 0; i < SUB_GRIDS; i++) {
            for (int j = 0; j < SUB_GRIDS; j++) {
                for (int digit = 1; digit < NUMBERS; digit++) {
                    int possiblePlaces = 0;

                    duplicateInSubGridBreak: for (int row = i * 3; row < (i + 1) * 3; row++) {
                        for (int column = j * 3; column < (j + 1) * 3; column++) {
                            if (matrix.get(row, column) == digit) {
                                possiblePlaces = 0;
                                break duplicateInSubGridBreak;
                            }

                            if (matrix.isset(row, column)
                                    || matrix.digitInRow(row, digit)
                                    || matrix.digitInColumn(column, digit)) {
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

        selectBreak: for (int i = 0; i < SUB_GRIDS; i++) {
            for (int j = 0; j < SUB_GRIDS; j++) {
                for (int digit = 1; digit < NUMBERS; digit++) {
                    if (availableSubGridPlaces[i][j][digit] > 0) {
                        canSelect = true;
                        break selectBreak;
                    }
                }
            }
        }

        if (!canSelect) {
            throw new CannotSelectException();
        }

        for (int i = 0; i < SUB_GRIDS; i++) {
            for (int j = 0; j < SUB_GRIDS; j++) {
                for (int digit = 1; digit < NUMBERS; digit++) {

                    if (availableSubGridPlaces[i][j][digit] == 1) {
                        for (int row = i * 3; row < (i + 1) * 3; row++) {
                            for (int column = j * 3; column < (j + 1) * 3; column++) {
                                if (!matrix.isset(row, column)
                                        && !matrix.digitInRow(row, digit)
                                        && !matrix.digitInColumn(column, digit)) {
                                    // TODO: Probably there should be a
                                    // validation with some exception thrown:
                                    // if(matrix[row][column].getValue() != 0) {
                                    // ...
                                    // }

                                    matrix.set(row, column, digit);
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    // TODO: Shouldn't this method declare exception as manageSubGrids does?
    private void manageWholeGrid(Grid matrix) {
        updateAvailableDigits(matrix);
        fillRemainingDigits(matrix);
    }

    private void updateAvailableDigits(Grid matrix) {
        for (int i = 0; i < Grid.GRID_SIZE; i++) {
            for (int j = 0; j < Grid.GRID_SIZE; j++) {
                int possibleDigits = 0;

                for (int digit = 1; digit < NUMBERS; digit++) {
                    if (matrix.digitInRow(i, digit)
                            || matrix.digitInColumn(j, digit)
                            || matrix.digitInSubgrid(i, j, digit)) {
                        continue;
                    }

                    possibleDigits++;
                }

                availableGridCellDigits[i][j] = possibleDigits;
            }
        }
    }

    private void fillRemainingDigits(Grid matrix) {
        for (int i = 0; i < Grid.GRID_SIZE; i++) {
            for (int j = 0; j < Grid.GRID_SIZE; j++) {
                if (availableGridCellDigits[i][j] == 1) {
                    for (int digit = 1; digit < NUMBERS; digit++) {
                        if (!matrix.isset(i, j) && !matrix.digitInRow(i, digit)
                                && !matrix.digitInColumn(j, digit)
                                && !matrix.digitInSubgrid(i, j, digit)) {
                            // TODO: As above, probably there should be a
                            // validation with some exception thrown:
                            // if(matrix[row][column].getValue() != 0) {
                            // ...
                            // }

                            matrix.set(i, j, digit);
                        }
                    }

                }
            }
        }
    }

    private double calculateWeights() {
        double weightsSum = 0;

        for (int i = 0; i < Grid.GRID_SIZE; i++) {
            for (int j = 0; j < Grid.GRID_SIZE; j++) {
                for (int k = 1; k < NUMBERS; k++) {
                    weights[i][j][k] = pheromoneValue[i][j][k]
                            * (10 - availableSubGridPlaces[i / 3][j / 3][k])
                            * (10 - availableGridCellDigits[i / 3][j / 3]);
                    weightsSum += weights[i][j][k];
                }
            }
        }

        return weightsSum;
    }

    private void calculateProbability() {
        double weightsSum = calculateWeights();

        for (int i = 0; i < Grid.GRID_SIZE; i++) {
            for (int j = 0; j < Grid.GRID_SIZE; j++) {
                for (int k = 1; k < NUMBERS; k++) {
                    probability[i][j][k] = weights[i][j][k] / weightsSum;
                }
            }
        }
    }
}
