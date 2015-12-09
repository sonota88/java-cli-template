package util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Utils {

    public static void puts(Object ... os){
        for(Object o : os){
            System.out.println(o);
        }
    }

    public static void putskv(Object k, Object v){
        putsf("%s (%s)", k, v);
    }

    public static void putsf(String format, Object ... args){
        puts(fmt(format, args));
    }

    public static String readFile(String path){
        try {
            return FileUtils.readFileToString(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String fmt(String format, Object ... args){
        return String.format(format, args);
    }

}
