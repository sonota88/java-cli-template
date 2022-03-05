package sample.model.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sample.model.command.Split.Range;

public class SplitTest {

    Split sut;

    @BeforeEach
    public void setup() {
        sut = new Split();
    }

    @Test
    public void test_calcBlockSize_div2() {
        int div = 2;

        assertEquals(0, sut.calcBlockSize(0, div)); // 0 0
        assertEquals(1, sut.calcBlockSize(1, div)); // 1 0
        assertEquals(1, sut.calcBlockSize(2, div)); // 1 1
        assertEquals(2, sut.calcBlockSize(3, div)); // 2 1
        assertEquals(2, sut.calcBlockSize(4, div)); // 2 2
        assertEquals(3, sut.calcBlockSize(5, div)); // 3 2

        assertEquals(1000, sut.calcBlockSize(1999, div));
        assertEquals(1000, sut.calcBlockSize(2000, div));
        assertEquals(1001, sut.calcBlockSize(2001, div));
    }

    @Test
    public void test_calcBlockSize_div3() {
        int div = 3;

        assertEquals(0, sut.calcBlockSize(0, div)); // 0 0 0
        assertEquals(1, sut.calcBlockSize(1, div)); // 1 0 0
        assertEquals(1, sut.calcBlockSize(2, div)); // 1 1 0
        assertEquals(1, sut.calcBlockSize(3, div)); // 1 1 1
        assertEquals(2, sut.calcBlockSize(4, div)); // 2 2 0
        assertEquals(2, sut.calcBlockSize(5, div)); // 2 2 1
        assertEquals(2, sut.calcBlockSize(6, div)); // 2 2 2
        assertEquals(3, sut.calcBlockSize(7, div)); // 3 3 1

        assertEquals(1000, sut.calcBlockSize(2999, div));
        assertEquals(1000, sut.calcBlockSize(3000, div));
        assertEquals(1001, sut.calcBlockSize(3001, div));
    }

    @Test
    public void test_generateRanges_div1() {
        {
            List<Range> rs = sut.generateRanges(0, 1);
            assertEquals("()", dumpRanges(rs));
        }
    }

    @Test
    public void test_generateRanges_div2() {
        int div = 2;
        {
            List<Range> rs = sut.generateRanges(0, div);
            assertEquals("() ()", dumpRanges(rs));
        }
        {
            List<Range> rs = sut.generateRanges(1, div);
            assertEquals("(1 1) ()", dumpRanges(rs));
        }
        {
            List<Range> rs = sut.generateRanges(2, div);
            assertEquals("(1 1) (2 2)", dumpRanges(rs));
        }
        {
            List<Range> rs = sut.generateRanges(3, div);
            assertEquals("(1 2) (3 3)", dumpRanges(rs));
        }
        {
            List<Range> rs = sut.generateRanges(4, div);
            assertEquals("(1 2) (3 4)", dumpRanges(rs));
        }
        {
            List<Range> rs = sut.generateRanges(5, div);
            assertEquals("(1 3) (4 5)", dumpRanges(rs));
        }

        {
            List<Range> rs = sut.generateRanges(1999, div);
            assertEquals("(1 1000) (1001 1999)", dumpRanges(rs));
        }
        {
            List<Range> rs = sut.generateRanges(2000, div);
            assertEquals("(1 1000) (1001 2000)", dumpRanges(rs));
        }
        {
            List<Range> rs = sut.generateRanges(2001, div);
            assertEquals("(1 1001) (1002 2001)", dumpRanges(rs));
        }
    }

    @Test
    public void test_generateRanges_div3() {
        int div = 3;
        {
            List<Range> rs = sut.generateRanges(0, div);
            assertEquals("() () ()", dumpRanges(rs));
        }
        {
            List<Range> rs = sut.generateRanges(1, div);
            assertEquals("(1 1) () ()", dumpRanges(rs));
        }
        {
            List<Range> rs = sut.generateRanges(2, div);
            assertEquals("(1 1) (2 2) ()", dumpRanges(rs));
        }
        {
            List<Range> rs = sut.generateRanges(3, div);
            assertEquals("(1 1) (2 2) (3 3)", dumpRanges(rs));
        }
        {
            List<Range> rs = sut.generateRanges(4, div);
            assertEquals("(1 2) (3 4) ()", dumpRanges(rs));
        }
        {
            List<Range> rs = sut.generateRanges(5, div);
            assertEquals("(1 2) (3 4) (5 5)", dumpRanges(rs));
        }
        {
            List<Range> rs = sut.generateRanges(6, div);
            assertEquals("(1 2) (3 4) (5 6)", dumpRanges(rs));
        }
        {
            List<Range> rs = sut.generateRanges(7, div);
            assertEquals("(1 3) (4 6) (7 7)", dumpRanges(rs));
        }

        {
            List<Range> rs = sut.generateRanges(2999, div);
            assertEquals("(1 1000) (1001 2000) (2001 2999)", dumpRanges(rs));
        }
        {
            List<Range> rs = sut.generateRanges(3000, div);
            assertEquals("(1 1000) (1001 2000) (2001 3000)", dumpRanges(rs));
        }
        {
            List<Range> rs = sut.generateRanges(3001, div);
            assertEquals("(1 1001) (1002 2002) (2003 3001)", dumpRanges(rs));
        }
    }

    String dumpRanges(List<Range> rs) {
        return rs.stream()
                .map(this::dumpRange)
                .collect(Collectors.joining(" "));
    }

    String dumpRange(Range r) {
        if (r.isValid()) {
            return String.format("(%s %s)", r.from, r.to);
        } else {
            return "()";
        }
    }

}
