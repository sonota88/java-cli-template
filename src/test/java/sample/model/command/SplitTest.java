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
    public void test_calcNumLinesForRange_div2() {
        int div = 2;
        
        assertThat(sut.calcNumLinesForRange(0, div), is(0)); // 0 0
        assertThat(sut.calcNumLinesForRange(1, div), is(1)); // 1 0
        assertThat(sut.calcNumLinesForRange(2, div), is(1)); // 1 1
        assertThat(sut.calcNumLinesForRange(3, div), is(2)); // 2 1
        assertThat(sut.calcNumLinesForRange(4, div), is(2)); // 2 2
        assertThat(sut.calcNumLinesForRange(5, div), is(3)); // 3 2

        assertThat(sut.calcNumLinesForRange(1999, div), is(1000));
        assertThat(sut.calcNumLinesForRange(2000, div), is(1000));
        assertThat(sut.calcNumLinesForRange(2001, div), is(1001));
    }

    @Test
    public void test_calcNumLinesForRange_div3() {
        int div = 3;
        
        assertThat(sut.calcNumLinesForRange(0, div), is(0)); // 0 0 0
        assertThat(sut.calcNumLinesForRange(1, div), is(1)); // 1 0 0
        assertThat(sut.calcNumLinesForRange(2, div), is(1)); // 1 1 0
        assertThat(sut.calcNumLinesForRange(3, div), is(1)); // 1 1 1
        assertThat(sut.calcNumLinesForRange(4, div), is(2)); // 2 2 0
        assertThat(sut.calcNumLinesForRange(5, div), is(2)); // 2 2 1
        assertThat(sut.calcNumLinesForRange(6, div), is(2)); // 2 2 2
        assertThat(sut.calcNumLinesForRange(7, div), is(3)); // 3 3 1

        assertThat(sut.calcNumLinesForRange(2999, div), is(1000));
        assertThat(sut.calcNumLinesForRange(3000, div), is(1000));
        assertThat(sut.calcNumLinesForRange(3001, div), is(1001));
    }

}
