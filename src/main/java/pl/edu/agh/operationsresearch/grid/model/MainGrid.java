package pl.edu.agh.operationsresearch.grid.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainGrid extends AbstractGrid<SubGrid> {

    public static final List<Integer> NUMBERS_LIST = ImmutableList.copyOf( IntStream.range(1, 10).boxed().collect(Collectors.toList()) );
    public static final Set<Integer> NUMBERS_SET = ImmutableSet.copyOf( NUMBERS_LIST );

    @Override
    public int getValue(int row, int col) {
        return table.at( row/3, col/3 ).getValue( row%3, col%3 );
    }

    @Override
    public void setValue(int row, int col, int val) {
        table.at( row/3, col/3 ).setValue( row%3, col%3, val);
    }

    @Override
    public Collection<Integer> getRowValues(int index) {
        List<Integer> aggregatedRow = Lists.newArrayList();
        table.row(index/3).values().stream().map( subGrid -> subGrid.getRowValues(index%3) ).forEach( aggregatedRow::addAll );
        return aggregatedRow;
    }

    @Override
    public Collection<Integer> getColumnValues(int index) {
        List<Integer> aggregatedCol = Lists.newArrayList();
        table.column(index/3).values().stream().map( subGrid -> subGrid.getColumnValues(index%3) ).forEach( aggregatedCol::addAll );
        return aggregatedCol;
    }

    @Override
    public boolean isValid() {
        for( SubGrid subGrid : table.values() ) {
            if( !subGrid.isValid() ) {
                return false;
            }
        }
        for( int i = 0; i < 9; i++ ) {
            if( !isRowValid(i) || !isColumnValid(i) ) {
                return false;
            }
        }
        return true;
    }

    public boolean isRowValid(int index) {
        return valuesValid( getRowValues( index ) );
    }

    public boolean isColumnValid(int index) {
        return valuesValid( getColumnValues( index ) );
    }

    @Override
    protected Supplier<SubGrid> initializeExpr() {
        return SubGrid::new;
    }
}
