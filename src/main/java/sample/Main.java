package sample;

import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import sample.model.Model;

import static util.Utils.*;
import static sample.Config.setEnv;
import static sample.Config.getEnv;
import static sample.Config.prop;

public class Main {

    public static void main(String[] rawArgs) throws Exception {
        String allArgs = rawArgs[0];
        String[] tempArgs = allArgs.split("\u001f");

        for (int i = 0; i < tempArgs.length; i++) {
            putsf("temp arg %s (%s)", i, tempArgs[i]);
        }

        Config.setCurrentDir(tempArgs[0]);
        Config.setProjectDir(tempArgs[1]);
        String[] mainArgs = new String[tempArgs.length - 2];
        for (int i = 2; i < tempArgs.length; i++) {
            mainArgs[i-2] = tempArgs[i];
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
        putskv("restArgs size", restArgs.size());

        for(int i=0; i<restArgs.size(); i++){
            putsf("rest arg %s (%s)", i, restArgs.get(i));
        }

        puts(cl.hasOption("h"));
        puts(cl.hasOption("v"));

        if (cl.hasOption("help")) {
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

        Config.load();

        puts(prop("foo.bar"));

        Model.main(restArgs);
    }

}
