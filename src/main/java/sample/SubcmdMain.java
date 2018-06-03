package sample;

import static util.Utils.*;

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
        String[] tempArgs = allArgs.split("\u001f");

        for (int i = 0; i < tempArgs.length; i++) {
            debug(fmt("temp arg %s (%s)", i, tempArgs[i]));
        }

        Config.setCurrentDir(tempArgs[0]);
        Config.setProjectDir(tempArgs[1]);
        String subcmd = tempArgs[2];
        debug(fmt("subcmd (%s)", subcmd));

        String[] mainArgs = new String[tempArgs.length - 3];
        for (int i = 3; i < tempArgs.length; i++) {
            mainArgs[i - 3] = tempArgs[i];
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
            cl = parser.parse(opts, mainArgs);

            checkHelp(cl, opts);
            setProfile(cl);
            Config.load();

            model.cmdA(cl.hasOption("f"), cl.getArgList());
            break;

        case "cmd_b":
            opts.addOption("b", "bar", true, "Option bar");
            opts.addOption(null, "profile", true, "Profile");
            cl = parser.parse(opts, mainArgs);

            checkHelp(cl, opts);
            setProfile(cl);
            Config.load();

            model.cmdB(cl.getOptionValue("b"), cl.getArgList());
            break;

        case "cat":
            opts.addOption("A", null, false, "Inspect");
            cl = parser.parse(opts, mainArgs);

            model.cat(cl.hasOption("A"));
            break;

        case "sort":
            model.sort();
            break;

        case "hexdump":
            model.hexdump(mainArgs[0]);
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
