package com.example.accesskeybackend.template;

import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;

public class Base64Util {
    public static String toBase64(final String str) {
        return Base64Utils.encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

    public static String fromBase64(final String str) {
        final byte[] bytes = Base64Utils.decodeFromString(str);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
