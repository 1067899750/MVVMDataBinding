package com.example.z_lib_base.utils.log;

import android.text.TextUtils;
import android.util.Log;



public class XLog {

    public static boolean LOG ;
    public static String TAG_ROOT = "--XLog-->";

    public static void init(boolean show, String tag){
        LOG = show;
        TAG_ROOT = tag;
    }

    public static void json(String json) {
        json(Log.DEBUG, null, json);
    }

    public static void json(int logLevel, String tag, String json) {
        if (LOG) {
            String formatJson = LogFormat.formatBorder(new String[]{LogFormat.formatJson(json)});
            XPrinter.println(logLevel, TextUtils.isEmpty(tag) ? TAG_ROOT : tag, formatJson);
        }
    }

    public static void xml(String xml) {
        xml(Log.DEBUG, null, xml);
    }


    public static void xml(int logLevel, String tag, String xml) {
        if (LOG) {
            String formatXml = LogFormat.formatBorder(new String[]{LogFormat.formatXml(xml)});
            XPrinter.println(logLevel, TextUtils.isEmpty(tag) ? TAG_ROOT : tag, formatXml);
        }
    }

    public static void error(Throwable throwable) {
        error(null, throwable);
    }

    public static void error(String tag, Throwable throwable) {
        if (LOG) {
            String formatError = LogFormat.formatBorder(new String[]{LogFormat.formatThrowable(throwable)});
            XPrinter.println(Log.ERROR, TextUtils.isEmpty(tag) ? TAG_ROOT : tag, formatError);
        }
    }

    private static void msg(int logLevel, String tag, String format, Object... args) {
        if (LOG) {
            String formatMsg = LogFormat.formatBorder(new String[]{LogFormat.formatArgs(format, args)});
            XPrinter.println(logLevel, TextUtils.isEmpty(tag) ? TAG_ROOT : tag, formatMsg);
        }
    }

    public static void d(String msg, Object... args) {
        msg(Log.DEBUG, null, msg, args);
    }

    public static void d(String tag, String msg, Object... args) {
        msg(Log.DEBUG, tag, msg, args);
    }

    public static void e(String msg, Object... args) {
        msg(Log.ERROR, null, msg, args);
    }

    public static void e(String tag, String msg, Object... args) {
        msg(Log.ERROR, tag, msg, args);
    }
    public static void i(String msg, Object... args) {
        msg(Log.INFO, null, msg, args);
    }

    public static void i(String tag, String msg, Object... args) {
        msg(Log.INFO, tag, msg, args);
    }
    public static void v(String msg, Object... args) {
        msg(Log.VERBOSE, null, msg, args);
    }

    public static void v(String tag, String msg, Object... args) {
        msg(Log.VERBOSE, tag, msg, args);
    }

    public static void w(String msg, Object... args) {
        msg(Log.WARN, null, msg, args);
    }

    public static void w(String tag, String msg, Object... args) {
        msg(Log.WARN, tag, msg, args);
    }
}









