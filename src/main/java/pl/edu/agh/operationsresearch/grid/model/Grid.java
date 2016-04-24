package pl.edu.agh.operationsresearch.grid.model;

import java.util.Arrays;

public class Grid {
    private int[][] values;
    private int selected;

    private int[][][] availableSubGridPlaces;
    private int[][] availableGridCellDigits;

    public Grid() {
        selected = 0;
        values = new int[GridConstants.GRID_SIZE][GridConstants.GRID_SIZE];

        availableSubGridPlaces = new int[GridConstants.SUB_GRIDS][GridConstants.SUB_GRIDS][GridConstants.NUMBERS];
        availableGridCellDigits = new int[GridConstants.GRID_SIZE][GridConstants.GRID_SIZE];

        for (int i = 0; i < GridConstants.SUB_GRIDS; i++) {
            for (int j = 0; j < GridConstants.SUB_GRIDS; j++) {
                Arrays.fill(availableSubGridPlaces[i][j], 9);
            }
        }
    }

    public Grid(int[][] grid) {
        set(grid);
    }

    public int selected() {
        return selected;
    }

    public void set(int[][] grid) {
        values = grid;
        selected = 0;
        for (int row = 0; row < GridConstants.GRID_SIZE; row++) {
            for (int col = 0; col < GridConstants.GRID_SIZE; col++) {
                if (isset(row, col)) {
                    selected++;
                }
            }
        }
    }

    public boolean set(int row, int col, int value) {
        if (digitInRow(row, value) || digitInColumn(col, value)
                || digitInSubgrid(row, col, value)) {
            return false;
        }

        if (!isset(row, col)) {
            selected++;
        }
        values[row][col] = value;
        return true;
    }

    public int get(int row, int col) {
        return values[row][col];
    }

    public boolean isset(int row, int col) {
        return get(row, col) != 0;
    }

    public boolean digitInColumn(int col, int digit) {
        for (int row = 0; row < GridConstants.GRID_SIZE; row++) {
            if (values[row][col] == digit) {
                return true;
            }
        }
        return false;
    }

    public boolean digitInRow(int row, int digit) {
        for (int col = 0; col < GridConstants.GRID_SIZE; col++) {
            if (values[row][col] == digit) {
                return true;
            }
        }
        return false;
    }

    public boolean digitInSubgrid(int row, int col, int digit) {
        row /= 3;
        col /= 3;

        for (int i = row * 3; i < (row + 1) * 3; i++) {
            for (int j = col * 3; j < (col + 1) * 3; j++) {
                if (get(i, j) == digit) {
                    return true;
                }
            }
        }
        return false;
    }

    public int[][] values() {
        int[][] copy = new int[GridConstants.GRID_SIZE][GridConstants.GRID_SIZE];

        for (int i = 0; i < GridConstants.GRID_SIZE; i++) {
            copy[i] = Arrays.copyOf(values[i], values[i].length);
        }

        return copy;
    }

    public void fillRemainingDigitsInSubgrid() {
        for (int i = 0; i < GridConstants.SUB_GRIDS; i++) {
            for (int j = 0; j < GridConstants.SUB_GRIDS; j++) {
                for (int digit = 1; digit < GridConstants.NUMBERS; digit++) {

                    if (availableSubGridPlaces[i][j][digit] == 1) {
                        for (int row = i * 3; row < (i + 1) * 3; row++) {
                            for (int column = j * 3; column < (j + 1) * 3; column++) {
                                if (!isset(row, column)
                                        && !digitInRow(row, digit)
                                        && !digitInColumn(column, digit)) {
                                    // TODO: Probably there should be a
                                    // validation with some exception thrown:
                                    // if(matrix[row][column].getValue() != 0) {
                                    // ...
                                    // }

                                    set(row, column, digit);
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    public boolean canSelect() {
        for (int i = 0; i < GridConstants.SUB_GRIDS; i++) {
            for (int j = 0; j < GridConstants.SUB_GRIDS; j++) {
                for (int digit = 1; digit < GridConstants.NUMBERS; digit++) {
                    if (availableSubGridPlaces[i][j][digit] > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void calculatePossiblePlaces() {
        for (int i = 0; i < GridConstants.SUB_GRIDS; i++) {
            for (int j = 0; j < GridConstants.SUB_GRIDS; j++) {
                for (int digit = 1; digit < GridConstants.NUMBERS; digit++) {
                    int possiblePlaces = 0;

                    duplicateInSubGridBreak: for (int row = i * 3; row < (i + 1) * 3; row++) {
                        for (int column = j * 3; column < (j + 1) * 3; column++) {
                            if (get(row, column) == digit) {
                                possiblePlaces = 0;
                                break duplicateInSubGridBreak;
                            }

                            if (isset(row, column) || digitInRow(row, digit)
                                    || digitInColumn(column, digit)) {
                                continue;
                            }

                            possiblePlaces++;
                        }
                    }

                    availableSubGridPlaces[i][j][digit] = possiblePlaces;
                }
            }
        }
    }

    public void updateAvailableDigits() {
        for (int i = 0; i < GridConstants.GRID_SIZE; i++) {
            for (int j = 0; j < GridConstants.GRID_SIZE; j++) {
                int possibleDigits = 0;

                for (int digit = 1; digit < GridConstants.NUMBERS; digit++) {
                    if (digitInRow(i, digit) || digitInColumn(j, digit)
                            || digitInSubgrid(i, j, digit)) {
                        continue;
                    }

                    possibleDigits++;
                }

                availableGridCellDigits[i][j] = possibleDigits;
            }
        }
    }

    public void fillRemainingDigits() {
        for (int row = 0; row < GridConstants.GRID_SIZE; row++) {
            for (int col = 0; col < GridConstants.GRID_SIZE; col++) {
                if (availableGridCellDigits[row][col] == 1) {
                    for (int digit = 1; digit < GridConstants.NUMBERS; digit++) {
                        if (!isset(row, col) && !digitInRow(row, digit)
                                && !digitInColumn(col, digit)
                                && !digitInSubgrid(row, col, digit)) {
                            // TODO: As above, probably there should be a
                            // validation with some exception thrown:
                            // if(matrix[row][column].getValue() != 0) {
                            // ...
                            // }

                            set(row, col, digit);
                        }
                    }

                }
            }
        }
    }

    public int subGridPlaces(int i, int j, int k) {
        return availableSubGridPlaces[i][j][k];
    }

    public int gridCellDigits(int i, int j) {
        return availableGridCellDigits[i][j];
    }
}
