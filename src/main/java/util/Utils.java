package util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import sample.Config;

public class Utils {

    static final String LF = "\n";
    static final String BS = "\\";
    static final String DQ = "\"";

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

    public static String escape(String s) {
        return s
                .replace(BS, BS + BS)
                .replace(DQ, BS + DQ)
                .replace("\b", BS + "b")
                .replace("\f", BS + "f")
                .replace("\n", BS + "n")
                .replace("\r", BS + "r")
                .replace("\t", BS + "t")
                ;
    }

    public static String toString(Object obj) {
        // return ReflectionToStringBuilder.toString(obj, ToStringStyle.JSON_STYLE);
        return ReflectionToStringBuilder.toString(obj, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static String[] toArray(List<String> list) {
        return list.toArray(new String[list.size()]);
    }

    public static String inspect(Object o) {
        return Inspector.inspect(o);
    }

}
