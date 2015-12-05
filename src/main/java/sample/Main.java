package sample;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Main {

    public static void main(String[] args) throws Exception {
        for(int i=0; i<args.length; i++){
            System.err.println(String.format("arg[%s] (%s)", i, args[i]));
        }

        Options opts = new Options();
        opts.addOption("h", "help", false, "Print help");

        opts.addOption(Option.builder("v")
                .hasArg()
                .desc("Print version")
                .build());

        CommandLine cl = new DefaultParser().parse(opts, args);

        for(int i=0; i<cl.getArgList().size(); i++){
            System.err.println(String.format("arg[%s] (%s)", i, cl.getArgList().get(i)));
        }
        System.err.println(cl.hasOption("h"));
        System.err.println(cl.hasOption("v"));
    }
}
