package sample.model;

import static util.Utils.puts;

import java.util.List;

public class Model {

    public static void main(List<String> args){
        puts("-->> Model.main");
        puts(args);
        puts(add( Integer.valueOf(args.get(0)), Integer.valueOf(args.get(1)) ));
    }

    static int add(int a, int b){
        return a + b;
    }

    public static void cmdA(Boolean hasOptF, List<String> args) {

        puts("cmd_1");
        puts(hasOptF);
        puts(args);
    }

    public static void cmdB(String optValB, List<String> args) {

        puts("cmd_2");
        puts(optValB);
        puts(args);
    }
}
