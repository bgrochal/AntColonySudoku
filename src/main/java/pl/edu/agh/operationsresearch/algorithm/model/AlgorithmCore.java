package pl.edu.agh.operationsresearch.algorithm.model;

import java.util.Random;

import pl.edu.agh.operationsresearch.algorithm.utils.CannotSelectException;
import pl.edu.agh.operationsresearch.grid.controller.GridController;
import pl.edu.agh.operationsresearch.grid.model.Grid;
import pl.edu.agh.operationsresearch.grid.model.GridConstants;

public class AlgorithmCore {
    private static final int PHEROMONES_MAX = 1000;
    private static final int CYCLES_NUMBER = 1000;

    private double evaporationRate;
    private int antsNumber;

    private Random randomGenerator;

    // indicates pheromone value on position (i, j) with number k
    private double[][][] pheromoneValue;
    private double[][][] probability;
    private double[][][] weights;

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
        pheromoneValue = new double[GridConstants.GRID_SIZE][GridConstants.GRID_SIZE][GridConstants.NUMBERS];

        for (int i = 0; i < GridConstants.GRID_SIZE; i++) {
            for (int j = 0; j < GridConstants.GRID_SIZE; j++) {
                for (int k = 0; k < GridConstants.NUMBERS; k++) {
                    pheromoneValue[i][j][k] = PHEROMONES_MAX;
                }
            }
        }
    }

    private void initializeArrays() {
        probability = new double[GridConstants.GRID_SIZE][GridConstants.GRID_SIZE][GridConstants.NUMBERS];
        weights = new double[GridConstants.GRID_SIZE][GridConstants.GRID_SIZE][GridConstants.NUMBERS];
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
                calculateProbability(tmp);

                // TODO: Not sure if it is correct.
                double p = randomGenerator.nextDouble();
                for (int i = 0; i < GridConstants.GRID_SIZE; i++) {
                    for (int j = 0; j < GridConstants.GRID_SIZE; j++) {
                        if (tmp.isset(i, j)) {
                            continue;
                        }

                        double probabilitiesSum = 0;
                        for (int k = 1; k < GridConstants.NUMBERS; k++) {
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

        for (int row = 0; row < GridConstants.GRID_SIZE; row++) {
            for (int col = 0; col < GridConstants.GRID_SIZE; col++) {
                if (best.isset(row, col)) {
                    pheromoneValue[row][col][best.get(row, col)] += pheromoneLeft;
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
        for (int row = 0; row < GridConstants.GRID_SIZE; row++) {
            for (int col = 0; col < GridConstants.GRID_SIZE; col++) {
                for (int k = 1; k < GridConstants.NUMBERS; k++) {
                    pheromoneValue[row][col][k] *= evaporationRate;
                }
            }
        }
    }

    private void manageSubGrids(Grid matrix) throws CannotSelectException {
        matrix.calculatePossiblePlaces();

        if (!matrix.canSelect()) {
            throw new CannotSelectException();
        }

        matrix.fillRemainingDigitsInSubgrid();
    }

    // TODO: Shouldn't this method declare exception as manageSubGrids does?
    private void manageWholeGrid(Grid matrix) {
        matrix.updateAvailableDigits();
        matrix.fillRemainingDigits();
    }

    private double calculateWeights(Grid matrix) {
        double weightsSum = 0;

        for (int row = 0; row < GridConstants.GRID_SIZE; row++) {
            for (int col = 0; col < GridConstants.GRID_SIZE; col++) {
                for (int k = 1; k < GridConstants.NUMBERS; k++) {
                    weights[row][col][k] = pheromoneValue[row][col][k]
                            * (GridConstants.NUMBERS - matrix.subGridPlaces(
                                    row / 3, col / 3, k))
                            * (GridConstants.NUMBERS - matrix.gridCellDigits(
                                    row / 3, col / 3));
                    weightsSum += weights[row][col][k];
                }
            }
        }

        return weightsSum;
    }

    private void calculateProbability(Grid matrix) {
        double weightsSum = calculateWeights(matrix);

        for (int row = 0; row < GridConstants.GRID_SIZE; row++) {
            for (int col = 0; col < GridConstants.GRID_SIZE; col++) {
                for (int k = 1; k < GridConstants.NUMBERS; k++) {
                    probability[row][col][k] = weights[row][col][k]
                            / weightsSum;
                }
            }
        }
    }
}
