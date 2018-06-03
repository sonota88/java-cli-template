package util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import sample.Config;

public class Utils {

    private static final String LF = "\n";

    public static void print(Object... os) {
        for (Object o : os) {
            System.out.print(o);
        }
    }

    public static void puts(Object... os) {
        for (Object o : os) {
            System.out.print(o + LF);
        }
    }

    public static void puts_e(Object... os) {
        for (Object o : os) {
            System.err.print(o + LF);
        }
    }

    public static void putskv(Object k, Object v) {
        putsf("%s (%s)", k, v);
    }

    public static void putskv_e(Object k, Object v) {
        putsf_e("%s (%s)", k, v);
    }

    public static void putsf(String format, Object... args) {
        puts(fmt(format, args));
    }

    public static void putsf_e(String format, Object... args) {
        puts_e(fmt(format, args));
    }

    public static void debug(Object... os) {
        if (Config.isDebug()) {
            puts_e(os);
        }
    }

    public static String readFile(String path) {
        try {
            return FileUtils.readFileToString(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String fmt(String format, Object... args) {
        return String.format(format, args);
    }

}
