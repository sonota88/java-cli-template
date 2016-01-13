package sample;

import static util.Utils.readFile;

import java.io.File;
import java.io.StringReader;
import java.util.Properties;

public class Config {

    /**
     * devel | test | prod
     */
    private static String env;

    @SuppressWarnings("unused")
    private static String currentDir;
    private static String projectDir;

    private static Properties props;

    static {
        props = new Properties();
    }

    public static String getEnv() {
        return env;
    }

    public static void setEnv(String env) {
        Config.env = env;
    }

    public static void setCurrentDir(String dir){
        currentDir = dir;
    }
    public static void setProjectDir(String dir){
        projectDir = dir;
    }

    public static void load() {
        String suffix = "";
        if (!env.equals("prod")) {
            suffix = "_" + env;
        }

        String path = projectRelativePath("config" + suffix + ".properties");
        String propsSrc = readFile(path);
        try (StringReader sr = new StringReader(propsSrc)) {
            props.load(sr);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public static String prop(String key){
        return props.getProperty(key);
    }

    /**
     * @param tail
     * @return project dir + tail (full path)
     */
    public static String projectRelativePath(String tail){
        try {
            return new File(projectDir, tail).getCanonicalPath();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
