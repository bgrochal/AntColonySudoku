package pl.edu.agh.operationsresearch.grid.model;

import java.util.Collection;
import java.util.function.Supplier;

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
    public Collection<Integer> getRowValues(int index) {
        return table.row( index ).values();
    }

    @Override
    public Collection<Integer> getColumnValues(int index) {
        return table.column( index ).values();
    }

    @Override
    public boolean isValid() {
        return valuesValid( table.values() );
    }

    @Override
    protected Supplier<Integer> initializeExpr() {
        return () -> 0;
    }
}
