package com.github.erf88.realmeet.util;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtils {

    public static String join(List<String> list) {
        return Strings.join(list, ',');
    }
}
