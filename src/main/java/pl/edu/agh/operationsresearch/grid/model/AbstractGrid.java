package pl.edu.agh.operationsresearch.grid.model;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.function.Supplier;

public abstract class AbstractGrid<T> {

    public static final Iterable<Integer> TAB_KEYS = ImmutableList.copyOf( Arrays.asList( 0, 1, 2 ) );

    protected final ArrayTable<Integer, Integer, T> table;

    public abstract int getValue(int row, int col );
    public abstract void setValue( int row, int col, int val );
    public abstract Collection<Integer> getRowValues(int index );
    public abstract Collection<Integer> getColumnValues( int index );
    public abstract boolean isValid();
    protected abstract Supplier<T> initializeExpr();

    public AbstractGrid() {
        this.table = ArrayTable.create(TAB_KEYS, TAB_KEYS);
        this.initialize();
    }

    public void initialize() {
        for( int row : TAB_KEYS) {
            for( int col : TAB_KEYS) {
                table.set( row, col, initializeExpr().get() );
            }
        }
    }

    protected boolean valuesValid( Collection<Integer> values ) {
        Set<Integer> numbers = Sets.newHashSet( MainGrid.NUMBERS_SET );
        for( int value : values ) {
            if( value != 0 && !numbers.remove( value ) ) {
                return false;
            }
        }
        return true;
    }
}
