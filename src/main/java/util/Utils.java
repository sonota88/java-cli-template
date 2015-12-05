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

    public static String readFile(String path){
        try {
            return FileUtils.readFileToString(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
