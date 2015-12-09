package sample;

import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import static util.Utils.*;
import static sample.Config.setEnv;
import static sample.Config.getEnv;

public class Main {

    public static void main(String[] rawArgs) throws Exception {
        for(int i=0; i<rawArgs.length; i++){
            putsf("raw arg %s (%s)", i, rawArgs[i]);
        }

        String currentDir = rawArgs[0];
        String projectDir = rawArgs[1];
        String[] mainArgs = new String[rawArgs.length - 2];
        for(int i=2; i<rawArgs.length; i++){
            mainArgs[i-2] = rawArgs[i];
        }

        Options opts = new Options();
        opts.addOption("h", "help", false, "Print help");
        opts.addOption(null, "env", true, "Environment");

        opts.addOption(Option.builder("v")
                .hasArg()
                .desc("Print version")
                .build());

        CommandLine cl = new DefaultParser().parse(opts, mainArgs);

        List<String> restArgs = cl.getArgList();
        putskv("currentDir", currentDir);
        putskv("projectDir", projectDir);
        putskv("restArgs size", restArgs.size());

        for(int i=0; i<restArgs.size(); i++){
            putsf("rest arg %s (%s)", i, restArgs.get(i));
        }

        puts(cl.hasOption("h"));
        puts(cl.hasOption("v"));

        if(cl.hasOption("help")){
            new HelpFormatter().printHelp("run_(mvn|gradle).sh", opts);
            System.exit(0);
        }

        String content = readFile("README.md");
        puts(content);

        if (cl.hasOption("env")) {
            setEnv(cl.getOptionValue("env"));
        } else {
            setEnv("devel");
        }
        puts(getEnv());
    }

}
