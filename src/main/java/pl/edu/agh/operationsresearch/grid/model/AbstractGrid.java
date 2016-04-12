package pl.edu.agh.operationsresearch.grid.model;

import com.google.common.collect.ArrayTable;

import java.util.Collection;

/**
 * Abstract 3x3 grid with two implementations - SubGrid & MainGrid
 */
public abstract class AbstractGrid<T> {

    protected final ArrayTable<Integer, Integer, T> table = ArrayTable.create(GridConstants.GRID_KEYS, GridConstants.GRID_KEYS);
    protected final GridSequenceValidator gridSequenceValidator = new GridSequenceValidator();

    public abstract int getValue(int row, int col);
    public abstract void setValue( int row, int col, int val);
    public abstract Collection<Integer> getRowValues(int index );
    public abstract Collection<Integer> getColumnValues( int index );
    public abstract boolean isValid();
    protected abstract T getInitCellValue();

    public AbstractGrid() {
        this.initialize();
    }

    // Initialize grid cells with getInitCellValue()
    public void initialize() {
        for( int row : GridConstants.GRID_KEYS) {
            for( int col : GridConstants.GRID_KEYS) {
                table.set( row, col, getInitCellValue() );
            }
        }
    }

    public void rewrite(AbstractGrid<T> newGrid) {
        table.putAll( newGrid.table );
    }
}
