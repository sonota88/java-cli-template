package sample;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import sample.model.Model;

import static util.Utils.*;
import static sample.Config.setProfile;
import static sample.Config.getProfile;
import static sample.Config.prop;

public class Main {

    public static void main(String[] rawArgs) {
        try {
            _main(rawArgs);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    static void _main(String[] rawArgs) throws Exception {

        String allArgs = rawArgs[0];
        List<String> tempArgs = Arrays.asList(allArgs.split("\u001f"));

        for (int i = 0; i < tempArgs.size(); i++) {
            putsf_e("temp arg %s (%s)", i, tempArgs.get(i));
        }

        Config.setCurrentDir(System.getenv("CURRENT_DIR"));
        Config.setProjectDir(System.getenv("PROJECT_DIR"));
        List<String> mainArgs = tempArgs;

        Options opts = new Options();
        opts.addOption("h", "help", false, "Print help");
        opts.addOption(null, "profile", true, "Profile");

        opts.addOption(Option.builder("v")
                .hasArg()
                .desc("Print version")
                .build());

        CommandLine cl = new DefaultParser().parse(opts, toArray(mainArgs));

        List<String> restArgs = cl.getArgList();
        putskv_e("restArgs size", restArgs.size());

        for(int i=0; i<restArgs.size(); i++){
            putsf_e("rest arg %s (%s)", i, restArgs.get(i));
        }

        debug(cl.hasOption("h"));
        debug(cl.hasOption("v"));

        if (cl.hasOption("help")) {
            new HelpFormatter().printHelp("run_(mvn|gradle).sh", opts);
            System.exit(0);
        }

        String content = readFile("README.md");
        debug(content);

        if (cl.hasOption("profile")) {
            setProfile(cl.getOptionValue("profile"));
        } else {
            setProfile("devel");
        }
        debug(getProfile());

        Config.load();

        debug(prop("foo.bar"));

        createModel().main(restArgs);
    }

    private static Model createModel() {
        return new Model();
    }

}
