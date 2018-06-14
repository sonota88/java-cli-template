package sample.model.command;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import sample.model.command.Split.Range;

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

    @Test
    public void test_generateRanges_div1() {
        {
            List<Range> rs = sut.generateRanges(0, 1);
            assertThat(dumpRanges(rs), is("()"));
        }
    }

    @Test
    public void test_generateRanges_div2() {
        int div = 2;
        {
            List<Range> rs = sut.generateRanges(0, div);
            assertThat(dumpRanges(rs), is("() ()"));
        }
        {
            List<Range> rs = sut.generateRanges(1, div);
            assertThat(dumpRanges(rs), is("(1 1) ()"));
        }
        {
            List<Range> rs = sut.generateRanges(2, div);
            assertThat(dumpRanges(rs), is("(1 1) (2 2)"));
        }
        {
            List<Range> rs = sut.generateRanges(3, div);
            assertThat(dumpRanges(rs), is("(1 2) (3 3)"));
        }
        {
            List<Range> rs = sut.generateRanges(4, div);
            assertThat(dumpRanges(rs), is("(1 2) (3 4)"));
        }
        {
            List<Range> rs = sut.generateRanges(5, div);
            assertThat(dumpRanges(rs), is("(1 3) (4 5)"));
        }

        {
            List<Range> rs = sut.generateRanges(1999, div);
            assertThat(dumpRanges(rs), is("(1 1000) (1001 1999)"));
        }
        {
            List<Range> rs = sut.generateRanges(2000, div);
            assertThat(dumpRanges(rs), is("(1 1000) (1001 2000)"));
        }
        {
            List<Range> rs = sut.generateRanges(2001, div);
            assertThat(dumpRanges(rs), is("(1 1001) (1002 2001)"));
        }
    }

    @Test
    public void test_generateRanges_div3() {
        int div = 3;
        {
            List<Range> rs = sut.generateRanges(0, div);
            assertThat(dumpRanges(rs), is("() () ()"));
        }
        {
            List<Range> rs = sut.generateRanges(1, div);
            assertThat(dumpRanges(rs), is("(1 1) () ()"));
        }
        {
            List<Range> rs = sut.generateRanges(2, div);
            assertThat(dumpRanges(rs), is("(1 1) (2 2) ()"));
        }
        {
            List<Range> rs = sut.generateRanges(3, div);
            assertThat(dumpRanges(rs), is("(1 1) (2 2) (3 3)"));
        }
        {
            List<Range> rs = sut.generateRanges(4, div);
            assertThat(dumpRanges(rs), is("(1 2) (3 4) ()"));
        }
        {
            List<Range> rs = sut.generateRanges(5, div);
            assertThat(dumpRanges(rs), is("(1 2) (3 4) (5 5)"));
        }
        {
            List<Range> rs = sut.generateRanges(6, div);
            assertThat(dumpRanges(rs), is("(1 2) (3 4) (5 6)"));
        }
        {
            List<Range> rs = sut.generateRanges(7, div);
            assertThat(dumpRanges(rs), is("(1 3) (4 6) (7 7)"));
        }

        {
            List<Range> rs = sut.generateRanges(2999, div);
            assertThat(dumpRanges(rs), is("(1 1000) (1001 2000) (2001 2999)"));
        }
        {
            List<Range> rs = sut.generateRanges(3000, div);
            assertThat(dumpRanges(rs), is("(1 1000) (1001 2000) (2001 3000)"));
        }
        {
            List<Range> rs = sut.generateRanges(3001, div);
            assertThat(dumpRanges(rs), is("(1 1001) (1002 2002) (2003 3001)"));
        }
    }

    String dumpRanges(List<Range> rs) {
        return rs.stream()
                .map(this::dumpRange)
                .collect(Collectors.joining(" "));
    }

    String dumpRange(Range r) {
        if (r.isInvalid()) {
            return "()";
        } else {
            return String.format("(%s %s)", r.from, r.to);
        }
    }

}
