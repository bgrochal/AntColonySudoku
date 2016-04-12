package pl.edu.agh.operationsresearch.grid.model;

import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;


/**
 * Takes 9-element int collection and validates if
 * every number from 1-9 range occurs at most once
 */
public class GridSequenceValidator implements Predicate<Collection<Integer>> {

    @Override
    public boolean test(Collection<Integer> values) {
        if( values.size() != 9 ) {
            return false;
        }
        Set<Integer> numbers = Sets.newHashSet( GridConstants.NUMBERS_SET );
        for( int value : values ) {
            if( value != 0 && !numbers.remove( value ) ) {
                return false;
            }
        }
        return true;
    }
}
