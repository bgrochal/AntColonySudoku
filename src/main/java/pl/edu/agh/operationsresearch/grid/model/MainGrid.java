package pl.edu.agh.operationsresearch.grid.model;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 9x9 integer grid that delegates operations to its subgrids
 * 0 <= row <= 8
 * 0 <= col <= 8
 */
public class MainGrid extends AbstractGrid<SubGrid> {

    // Find proper subgrid and delegate getValue to it
    @Override
    public int getValue(int row, int col) {
        return table.at( row/3, col/3 ).getValue( row%3, col%3 );
    }

    // Find proper subgrid and delegate setValue to it
    @Override
    public void setValue(int row, int col, int val) {
        table.at( row/3, col/3 ).setValue( row%3, col%3, val);
    }

    // Find subgrids and join their 3-digit rows to get full 9-digit row
    @Override
    public Collection<Integer> getRowValues(int row) {
        return table.row(row/3).values().stream()
            .flatMap( subGrid -> subGrid.getRowValues(row%3).stream() )
            .collect(Collectors.toList());
    }

    // Find subgrids and join their 3-digit columns to get full 9-digit column
    @Override
    public Collection<Integer> getColumnValues(int col) {
        return table.column(col/3).values().stream()
            .flatMap( subGrid -> subGrid.getColumnValues(col%3).stream() )
            .collect(Collectors.toList());
    }

    // First check if every subgrid is valid and then check every full row and column
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

    @Override
    protected SubGrid getInitCellValue() {
        return new SubGrid();
    }

    public boolean isRowValid(int row) {
        return gridSequenceValidator.test( getRowValues( row ) );
    }

    public boolean isColumnValid(int col) {
        return gridSequenceValidator.test( getColumnValues( col ) );
    }

    // 0 <= row < 2
    // 0 <= col < 2
    public SubGrid getSubgrid(int row, int col) {
        return table.at(row, col);
    }
}
