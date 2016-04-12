package pl.edu.agh.operationsresearch.grid.model;

import java.util.Collection;

/**
 * 3x3 integer grid
 * 0 <= row <= 2
 * 0 <= col <= 2
 */
public class SubGrid extends AbstractGrid<Integer> {

    @Override
    public int getValue( int row, int col ) {
        return table.at( row, col );
    }

    @Override
    public void setValue(int row, int col, int value) {
        table.set( row, col, value );
    }

    @Override
    public Collection<Integer> getRowValues(int row) {
        return table.row( row ).values();
    }

    @Override
    public Collection<Integer> getColumnValues(int col) {
        return table.column( col ).values();
    }

    @Override
    public boolean isValid() {
        return gridSequenceValidator.test( table.values() );
    }

    @Override
    protected Integer getInitCellValue() {
        return 0;
    }
}
