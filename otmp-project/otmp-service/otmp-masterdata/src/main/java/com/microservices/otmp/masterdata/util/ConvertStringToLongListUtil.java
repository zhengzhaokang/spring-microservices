package com.microservices.otmp.masterdata.util;

import com.microservices.otmp.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConvertStringToLongListUtil {
    private ConvertStringToLongListUtil() {
    }

    public static List<Long>splitToLongList(String ids){
        if(StringUtils.isNotEmpty(ids)){
            List<String> idsList = Arrays.stream(ids.split(",")).collect(Collectors.toList());
            return idsList.stream().map(Long::parseLong).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
