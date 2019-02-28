package com.lry.xxs.config;

import java.util.HashMap;

    public class CacheExpiresMap {
        private static HashMap<String, Integer> map = new HashMap<>();
        static {
            map.put("dic", 60);
        }
        public static HashMap<String, Integer> get() {
            return map;
        }
    }
