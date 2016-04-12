package pl.edu.agh.operationsresearch.grid.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GridConstants {

    // Numbers 1-9 list and set
    public static final List<Integer> NUMBERS_LIST = ImmutableList.copyOf( IntStream.range(1, 10).boxed().collect(Collectors.toList()) );
    public static final Set<Integer> NUMBERS_SET = ImmutableSet.copyOf( NUMBERS_LIST );

    // Keys for creating 3x3 ArrayTable
    public static final Iterable<Integer> GRID_KEYS = ImmutableList.copyOf( Arrays.asList( 0, 1, 2 ) );
}
