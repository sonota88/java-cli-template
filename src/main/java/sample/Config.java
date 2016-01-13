package sample;

import static util.Utils.readFile;

import java.io.StringReader;
import java.util.Properties;

public class Config {

    /**
     * devel | test | prod
     */
    private static String env;

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

    public static void load() {
        String suffix = "";
        if (!env.equals("prod")) {
            suffix = "_" + env;
        }

        String propsSrc = readFile("config" + suffix + ".properties");
        try (StringReader sr = new StringReader(propsSrc)) {
            props.load(sr);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public static String prop(String key){
        return props.getProperty(key);
    }

}
