package sample;

import static util.Utils.readFile;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Config {

    /**
     * devel | test | prod
     */
    private static String profile;

    @SuppressWarnings("unused")
    private static String currentDir;
    private static String projectDir;

    private static Properties props;

    static {
        props = new Properties();
    }

    public static String getProfile() {
        return profile;
    }

    public static void setProfile(String profile) {
        Config.profile = profile;
    }

    public static void setCurrentDir(String dir){
        currentDir = normalizePath(System.getProperty("os.name"), dir);
    }
    public static void setProjectDir(String dir){
        projectDir = normalizePath(System.getProperty("os.name"), dir);
    }

    /**
     * "/c/path/to/..." => "C:/path/to/..."
     */
    private static String normalizePath(String osName, String path) {
        if (osName.toLowerCase().startsWith("windows")) {
            List<String> m = re_match("^/([a-zA-Z])/", path);
            if (m.isEmpty()) {
                return path;
            } else {
                return m.get(1).toUpperCase() + ":" + path.substring(2);
            }
        } else {
            return path;
        }
    }

    private static List<String> re_match(String restr, String target) {
        Pattern p = Pattern.compile(restr);
        Matcher m = p.matcher(target);
        if (m.find()) {
            List<String> groups = new ArrayList<>();
            for (int i = 0; i <= m.groupCount(); i++) {
                groups.add(m.group(i));
            }
            return groups;
        } else {
            return Collections.emptyList();
        }
    }

    public static void load() {
        String suffix = "";
        if (!profile.equals("prod")) {
            suffix = "_" + profile;
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
