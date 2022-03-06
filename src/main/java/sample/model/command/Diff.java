package sample.model.command;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;

import sample.model.command.diff.DiffCore;
import sample.model.command.diff.FullOp;
import util.Utils;

public class Diff {

    public void exec(File fileA, File fileB, boolean ignoreSpace) {
        exec(
            readLines(fileA),
            readLines(fileB),
            ignoreSpace
        );
    }

    public void exec(
            List<String> linesA,
            List<String> linesB,
            boolean ignoreSpace
    ) {
        DiffCore core = DiffCore.create(linesA, linesB);
        core.setIgnoreSpace(ignoreSpace);

        List<FullOp> fullOps = core.exec();

        for (FullOp fullOp : fullOps) {
            Utils.puts(fullOp.toStr());
        }
    }

    private List<String> readLines(File file) {
        try {
            return FileUtils.readLines(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
