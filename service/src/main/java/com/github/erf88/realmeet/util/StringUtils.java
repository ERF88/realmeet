package com.github.erf88.realmeet.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtils {

   public static String join(List<String> list) {
       return Strings.join(list, ',');
   }
}
