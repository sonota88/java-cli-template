package sample.model.command.diff;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class DiffCoreTest {

    @Test
    public void test() {
        List<String> linesA = Arrays.asList("A", "B", "C");
        List<String> linesB = Arrays.asList("A", "x", "C");

        DiffCore core = DiffCore.create(linesA, linesB);
        List<FullOp> fullOps = core.exec();

        assertEquals(4, fullOps.size());

        assertEquals(" A", fullOps.get(0).toStr());
        assertEquals("-B", fullOps.get(1).toStr());
        assertEquals("+x", fullOps.get(2).toStr());
        assertEquals(" C", fullOps.get(3).toStr());
    }

    @Test
    public void test_2_add() {
        List<String> linesA = Arrays.asList();
        List<String> linesB = Arrays.asList("A");

        DiffCore core = DiffCore.create(linesA, linesB);
        List<FullOp> fullOps = core.exec();

        assertEquals(1, fullOps.size());

        assertEquals("+A", fullOps.get(0).toStr());
    }

    @Test
    public void test_3_del() {
        List<String> linesA = Arrays.asList("A");
        List<String> linesB = Arrays.asList();

        DiffCore core = DiffCore.create(linesA, linesB);
        List<FullOp> fullOps = core.exec();

        assertEquals(1, fullOps.size());

        assertEquals("-A", fullOps.get(0).toStr());
    }

    @Test
    public void test_4_nop() {
        List<String> linesA = Arrays.asList("A");
        List<String> linesB = Arrays.asList("A");

        DiffCore core = DiffCore.create(linesA, linesB);
        List<FullOp> fullOps = core.exec();

        assertEquals(1, fullOps.size());

        assertEquals(" A", fullOps.get(0).toStr());
    }

    @Test
    public void test_5() {
        List<String> linesA = Arrays.asList("A", "B", "C");
        List<String> linesB = Arrays.asList("A", " B", "C");

        DiffCore core = DiffCore.create(linesA, linesB);
        core.setIgnoreSpace(true);
        List<FullOp> fullOps = core.exec();

        assertEquals(3, fullOps.size());

        assertEquals(" A", fullOps.get(0).toStr());
        assertEquals(" B", fullOps.get(1).toStr());
        assertEquals(" C", fullOps.get(2).toStr());
    }

}
