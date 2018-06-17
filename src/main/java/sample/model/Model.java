package sample.model;

import static util.Utils.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import sample.model.command.Hexdump;

public class Model {

    public void main(List<String> args) {
        debug("-->> Model.main");
        puts(args);
        puts(add( Integer.valueOf(args.get(0)), Integer.valueOf(args.get(1)) ));
    }

    int add(int a, int b) {
        return a + b;
    }

    public void cmdA(Boolean hasOptF, List<String> args) {

        puts("cmd_1");
        puts(hasOptF);
        puts(args);
    }

    public void cmdB(String optValB, List<String> args) {

        puts("cmd_2");
        puts(optValB);
        puts(args);
    }

    public void cat(boolean isInspect) {
        try (
                Reader r = new InputStreamReader(System.in, "UTF-8");
                Writer w = new OutputStreamWriter(System.out, "UTF-8");
                )
        {
            int c;

            while (true) {
                c = r.read();
                if (c < 0) {
                    break;
                }
                if (isInspect) {
                    if (c == '\t') {
                        w.write("^I");
                    } else if (c == '\r') {
                        w.write("^M");
                    } else if (c == '\n') {
                        w.write("$\n");
                    } else {
                        w.write(c);
                    }
                } else {
                    w.write(c);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sort() {
        debug("cmd_sort");

        List<String> lines = readAllLines(System.in);
        List<String> sorted = sortLines(lines);

        for (String line : sorted) {
            System.out.print(line);
        }
    }

    private List<String> sortLines(List<String> lines){
        String pivot = lines.get(0);
        List<String> left = new ArrayList<>();
        List<String> center = new ArrayList<>();
        List<String> right = new ArrayList<>();

        for (String line : lines) {
            int cmp = line.compareTo(pivot);
            if (cmp < 0) {
                left.add(line);
            } else if (cmp > 0) {
                right.add(line);
            } else {
                center.add(line);
            }
        }

        List<String> leftSorted = null;
        if (left.size() >= 2) {
            leftSorted = sortLines(left);
        } else {
            leftSorted = left;
        }

        List<String> rightSorted = null;
        if (right.size() >= 2) {
            rightSorted = sortLines(right);
        } else {
            rightSorted = right;
        }

        List<String> result = new ArrayList<>();
        result.addAll(leftSorted);
        result.addAll(center);
        result.addAll(rightSorted);

        return result;
    }

    public void hexdump(String path) {
        Hexdump cmd = new Hexdump();
        cmd.exec(path);
    }

}
