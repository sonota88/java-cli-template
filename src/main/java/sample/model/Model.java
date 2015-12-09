package sample.model;

import java.util.List;

import static util.Utils.*;

public class Model {

    public static void main(List<String> args){
        puts("-->> Model.main");
        puts(args);
        puts(add( Integer.valueOf(args.get(0)), Integer.valueOf(args.get(1)) ));
    }

    static int add(int a, int b){
        return a + b;
    }
}
