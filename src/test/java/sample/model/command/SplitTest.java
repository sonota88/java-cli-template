package sample.model.command;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class SplitTest {

    Split sut;
    
    @Before
    public void setup() {
        sut = new Split();
    }

    @Test
    public void test_calcNumLinesForRange() {
        int div = 2;
        
        assertThat(sut.calcNumLinesForRange(0, div), is(0));
    }

}
