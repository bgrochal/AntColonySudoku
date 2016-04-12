package pl.edu.agh.operationsresearch.grid.model;

import com.google.common.collect.Lists;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collection;

public class GridSequenceValidatorTest {

    private final static GridSequenceValidator validator = new GridSequenceValidator();

    @DataProvider(name = "sequences")
    public static Object[][] sequences() {
        return new Object[][] {
            {Lists.newArrayList(1,2,3,4,5,6,7,8,9), true},
            {Lists.newArrayList(1,2,3,4,5,6,0,8,9), true},
            {Lists.newArrayList(7,2,3,9,5,6,1,8,4), true},
            {Lists.newArrayList(7,0,3,9,0,6,1,0,4), true},
            {Lists.newArrayList(0,0,0,0,0,0,0,0,0), true},
            {Lists.newArrayList(1,1,3,4,5,6,7,8,9), false},
            {Lists.newArrayList(1,0,0,0,5,0,5,8,9), false},
            {Lists.newArrayList(10,0,0,0,5,0,1,8,9), false}
        };
    }

    @Test(dataProvider = "sequences")
    public void testSequences(Collection<Integer> sequence, boolean expectedResult) {
        Assert.assertEquals(expectedResult, validator.test(sequence));
    }

}
