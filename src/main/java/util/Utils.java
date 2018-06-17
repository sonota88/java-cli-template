package util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import sample.Config;
import sample.io.BufferedReaderWrapper;

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

    public static String toString(Object obj) {
        // return ReflectionToStringBuilder.toString(obj, ToStringStyle.JSON_STYLE);
        return ReflectionToStringBuilder.toString(obj, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static List<String> readAllLines(InputStream is) {
        List<String> lines = new ArrayList<>();

        try (
                Reader r = new InputStreamReader(is, "UTF-8");
                BufferedReaderWrapper brw = new BufferedReaderWrapper(r, '\n');
                )
        {
            String line;
            while (true) {
                line = brw.readLineWithNewline();
                if (line == null) {
                    break;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lines;
    }

}
