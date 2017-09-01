package com.example.kingsoft.v2ex.Util;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by kingsoft on 2017/8/18.
 */

public class ValidateUtil {

    public static boolean isEmpty(Object o) {

        if (null == o) {
            return true;
        }

        if (o instanceof String) {
            return "".equals(o) ? true : false;
        }

        if (o instanceof List) {
            return ((List) o).size() == 0 ? true : false;
        }

        if (o instanceof Map) {
            return ((Map) o).size() == 0 ? true : false;
        }

        if (o instanceof String[])
            return ((String[]) o).length == 0 ? true : false;

        if (o instanceof int[])
            return ((int[]) o).length == 0 ? true : false;

        if (o instanceof Set)
            return ((Set) o).size() == 0 ? true : false;

        return false;
    }

    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

}
