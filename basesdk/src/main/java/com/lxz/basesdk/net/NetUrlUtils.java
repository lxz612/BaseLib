package com.lxz.basesdk.net;

public class NetUrlUtils {

    private static String url = "http://192.168.199.235:8080/MHealth/router?";

    public static String getNetUrl(String other) {
        return url + other;
    }

    public static void setUrl(String urll) {
        url = "http:/" + urll + ":8080/MHealth/router?";
    }
}
