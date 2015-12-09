package sample;

public class Config {

    /**
     * devel | test | prod
     */
    private static String env;

    public static String getEnv() {
        return env;
    }

    public static void setEnv(String env) {
        Config.env = env;
    }
}
