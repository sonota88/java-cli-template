package sample.model.command;

import static util.Utils.debug;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import sample.io.BufferedReaderWrapper;

public class Sort {

    public void exec() {
        debug("cmd_sort");

        List<String> lines = readAllLines();
        List<String> sorted = sortLines(lines);

        for (String line : sorted) {
            System.out.print(line);
        }
    }

    private List<String> readAllLines() {
        List<String> lines = new ArrayList<>();

        try (
                Reader r = new InputStreamReader(System.in, StandardCharsets.UTF_8);
                BufferedReaderWrapper brw = new BufferedReaderWrapper(r, '\n');
                )
        {
            String line;
            while (true) {
                line = brw.readLineWithNewline();
                if (line == null) {
                    break;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lines;
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

}
