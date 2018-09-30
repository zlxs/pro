package com.youyu.platform.gitlab.util;

import java.util.HashSet;
import java.util.Set;

/**
 * @author dongdong
 * @date 2018/6/26
 */
public class ArrayUtils {

    public static Long[] removeDuplicates(Long[] array) {

        if (array == null || array.length <= 1) {
            return array;
        }

        Set<Long> set = new HashSet<>();
        for (int i = 0; i < array.length; i++) {
            set.add(array[i]);
        }
        return set.toArray(new Long[set.size()]);
    }

}
