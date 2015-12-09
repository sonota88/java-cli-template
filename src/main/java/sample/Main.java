package sample;

import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import static util.Utils.*;

public class Main {

    public static void main(String[] args) throws Exception {
        for(int i=0; i<args.length; i++){
            putsf("arg[%s] (%s)", i, args[i]);
        }

        String currentDir = args[0];
        String projectDir = args[1];
        String[] mainArgs = new String[args.length - 2];
        for(int i=2; i<args.length; i++){
            mainArgs[i-2] = args[i];
        }

        Options opts = new Options();
        opts.addOption("h", "help", false, "Print help");

        opts.addOption(Option.builder("v")
                .hasArg()
                .desc("Print version")
                .build());

        CommandLine cl = new DefaultParser().parse(opts, mainArgs);

        List<String> restArgs = cl.getArgList();
        putsf("currentDir (%s)", currentDir);
        putsf("projectDir (%s)", projectDir);
        putsf("restArgs size (%s)", restArgs.size());

        for(int i=0; i<restArgs.size(); i++){
            putsf("arg[%s] (%s)", i, restArgs.get(i));
        }

        puts(cl.hasOption("h"));
        puts(cl.hasOption("v"));

        String content = readFile("README.md");
        puts(content);
    }

}
