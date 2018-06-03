package sample.model;

import static util.Utils.puts;
import static util.Utils.debug;

import java.util.List;

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
}
