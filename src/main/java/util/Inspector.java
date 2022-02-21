package util;

import static util.Utils.DQ;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

class Inspector {

    private enum Type {
        PRIMITIVE,
        STRING,
        LIST,
        MAP,
        LOCAL_DATE_TIME,
        LOCAL_DATE,
        OTHER,
        ;

        private static final Set<Class> PRIMITIVES;

        static {
            Set<Class> set = new HashSet<>();
            set.add(Integer.class);
            set.add(Long.class);
            PRIMITIVES = set;
        }

        static Type of(Object o) {
            // Utils.puts_e(o.getClass());
            Class<?> cls = o.getClass();

            if (PRIMITIVES.contains(cls)) {
                return PRIMITIVE;
            } else if (Objects.equals(cls, String.class)) {
                return STRING;
            } else if (isList(o)) {
                return LIST;
            } else if (isMap(o)) {
                return MAP;
            } else if (Objects.equals(cls, LocalDateTime.class)) {
                return LOCAL_DATE_TIME;
            } else if (Objects.equals(cls, LocalDate.class)) {
                return LOCAL_DATE;
            } else {
                return OTHER;
            }
        }

        private static boolean isList(Object o) {
            return
                Arrays.stream(
                        o.getClass().getInterfaces()
                )
                    .anyMatch(intf -> Objects.equals(intf, java.util.List.class))
            ;
        }

        private static boolean isMap(Object o) {
//            Utils.putskv_e(151, o);
//            Utils.putskv_e("  152", o.getClass().getSimpleName());
//            Utils.putskv_e("  152", ReflectionToStringBuilder.toString(o.getClass().getInterfaces()));
            return
                    Arrays.stream(
                            o.getClass().getInterfaces()
                    )
                        .anyMatch(intf -> Objects.equals(intf, java.util.Map.class))
                ;
        }

    }

    public static String inspect(Object o) {
        return new Inspector().__inspect(o);
    }

    public String __inspect(Object o) {
        if (o == null) {
            return "null";
        }

        switch (Type.of(o)) {
        case PRIMITIVE:
            return String.valueOf(o);
        case STRING:
            return DQ + o + DQ; // TODO escape
        case LIST:
            return inspectList((List<Object>) o);
        case MAP:
            return inspectMap((Map<Object, Object>) o);
        case LOCAL_DATE_TIME:
            return inspect(toMap((LocalDateTime) o));
        case LOCAL_DATE:
            return inspect(toMap((LocalDate) o));
        case OTHER:
            return inspect(obj2map(o));
        default:
            throw new RuntimeException("must not happen");
        }
    }

    public String _prettyInspect(Object o, int lv) {
        if (o == null) {
            return "null";
        }

        switch (Type.of(o)) {
        case PRIMITIVE:
            return String.valueOf(o);
        case STRING:
            return DQ + o + DQ;
        case LIST:
            return prettyInspectList((List<Object>)o);
        case MAP:
            return prettyInspectMap((Map<Object, Object>)o);
        case LOCAL_DATE_TIME:
            return prettyInspect(toMap((LocalDateTime) o));
        case OTHER:
            return prettyInspect(obj2map(o));
        default:
            throw new RuntimeException("must not happen");
        }
    }

    public static String prettyInspect(Object o) {
        return new Inspector().__prettyInspect(o);
    }

    public String __prettyInspect(Object o) {
        return _prettyInspect(o, 0);
    }

    private String inspectList(List<Object> xs) {
        return "(" +
                xs.stream().map(Inspector::inspect).collect(listJoiner())
            + ")";
    }

    private String prettyInspectList(List<Object> xs) {
        String tempStr = __inspect(xs);
        if (tempStr.length() < 20) {
            return tempStr;
        }

        return "(\n" +
                xs.stream()
                    .map(x -> indent(prettyInspect(x), 1) + "\n")
                    .collect(Collectors.joining())
            + ")";
    }

    private String inspectMap(Map<Object, Object> map) {
        String s = "{";
        
        List<Object> xs = new ArrayList<>();

        List<Object> ks = sortKeys(map.keySet());
        for (Object k : ks) {
            xs.add(k);
            xs.add(map.get(k));
        }
        
        s += xs.stream().map(Inspector::inspect).collect(listJoiner());
        s += "}";
        return s;
    }

    private String prettyInspectMap(Map<Object, Object> map) {
        String s = "";
        // s += indent(lv);
        s += "{\n";
        
        List<Object> ks = sortKeys(map.keySet());

        String tempStr;

        if (ks.size() == 1) {
            tempStr = __inspect(map);
            if (tempStr.length() < 20) {
                return tempStr;
            }
        }
        
        for (Object k : ks) {
            Object v = map.get(k);
            s += indent(1);
            s += inspect(k);

            // Utils.puts_e(String.format("  -->> (%s) (%s : %s)", k, v, Type.of(v)));

            switch (Type.of(v)) {
            case MAP:
                tempStr = prettyInspectMap((Map<Object, Object>) v);
                s += "\n" + indent(tempStr, 2);
                break;
            case OTHER:
                tempStr = _prettyInspect(obj2map(v), 0);
                s += "\n" + indent(tempStr, 2);
                break;
            default:
                s += " " + _prettyInspect(v, 1);
                break;
            }

            s += "\n";
        }
        
        // s += indent(lv);
        s += "}";
        return s;
    }

    private String indent(int lv) {
        String s = "";
        for (int i = 0; i < lv; i++) {
            s += "  ";
        }
        return s;
    }

    private String indent(String str, int lv) {
        List<String> lines = Arrays.asList(StringUtils.split(str, "\n"));

        return lines.stream()
                .map(line -> indent(lv) + line)
                .collect(Collectors.joining("\n"));
    }

    private List<Object> sortKeys(Collection<Object> keys) {
        List<String> skeys = keys.stream()
                .map(String::valueOf) // TODO
                .collect(Collectors.toList());
        Collections.sort(skeys);

        List<Object> newKeys =
            skeys.stream()
                .map(sk -> {
                    for (Object k : keys) {
                        if (Objects.equals(String.valueOf(k), sk)) {
                            return k;
                        }
                    }
                    throw new RuntimeException("must not happen");
                })
                .collect(Collectors.toList());

        return newKeys;
    }

    private Map<Object, Object> obj2map(Object o) {
        Map<Object, Object> map = new HashMap<>();

        map.put("__class__", o.getClass().getCanonicalName());
        
        for (Field f : o.getClass().getDeclaredFields()) {
            Object val = getFieldVal(o, f);
            map.put(f.getName(), val);
        }
        
        return map;
    }

    private Map <Object, Object> toMap(LocalDateTime x) {
        Map <Object, Object> map = new HashMap<>();
        map.put("__class__", LocalDateTime.class.getCanonicalName());
        map.put("value", x.toString()); // TODO format
        return map;
    }

    private Map <Object, Object> toMap(LocalDate x) {
        Map <Object, Object> map = new HashMap<>();
        map.put("__class__", LocalDate.class.getCanonicalName());
        map.put("value", x.toString()); // TODO format
        return map;
    }

    private Object getFieldVal(Object o, Field f) {
        f.setAccessible(true);
        try {
            return f.get(o);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Collector listJoiner() {
        return Collectors.joining(" ");
    }

}
