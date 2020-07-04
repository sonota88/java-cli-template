package sample;

import static util.Utils.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import sample.model.Model;

public class SubcmdMain {

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
            debug(fmt("temp arg %s (%s)", i, tempArgs.get(i)));
        }

        Config.setCurrentDir(System.getenv("CURRENT_DIR"));
        Config.setProjectDir(System.getenv("PROJECT_DIR"));
        String subcmd = tempArgs.get(0);
        debug(fmt("subcmd (%s)", subcmd));

        List<String> mainArgs = new ArrayList<>();
        for (int i = 1; i < tempArgs.size(); i++) {
            mainArgs.add(tempArgs.get(i));
        }

        Options opts = new Options();
        opts.addOption("h", "help", false, "Print help");

        CommandLineParser parser = new DefaultParser();
        CommandLine cl;

        Model model = createModel();

        switch (subcmd) {
        case "cmd_a":
            opts.addOption("f", "foo", false, "Option foo");
            opts.addOption(null, "profile", true, "Profile");
            cl = parser.parse(opts, toArray(mainArgs));

            checkHelp(cl, opts);
            setProfile(cl);
            Config.load();

            model.cmdA(cl.hasOption("f"), cl.getArgList());
            break;

        case "cmd_b":
            opts.addOption("b", "bar", true, "Option bar");
            opts.addOption(null, "profile", true, "Profile");
            cl = parser.parse(opts, toArray(mainArgs));

            checkHelp(cl, opts);
            setProfile(cl);
            Config.load();

            model.cmdB(cl.getOptionValue("b"), cl.getArgList());
            break;

        default:
            throw new IllegalArgumentException("cmd (" + subcmd + ")");
        }
    }

    private static void checkHelp(CommandLine cl, Options opts) {
        if (cl.hasOption("help")) {
            new HelpFormatter().printHelp("run_subcmd_(mvn|gradle).sh", opts);
            System.exit(0);
        }
    }

    private static void setProfile(CommandLine cl) {
        if (cl.hasOption("profile")) {
            Config.setProfile(cl.getOptionValue("profile"));
        } else {
            Config.setProfile("devel");
        }

    }

    private static Model createModel() {
        return new Model();
    }

}
