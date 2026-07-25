package com.haole.task.utils;

import org.slf4j.Logger;
import org.slf4j.event.Level;
import org.springframework.util.ObjectUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日志工具类
 */
public class LogUtils {

    private final static Pattern pattern = Pattern.compile("(\\{)(\\})");

    public static String dumpThrowable(Throwable ex) {
        if (ex == null) {
            return null;
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        return writer.toString();
    }

    public static void logWarn(Logger logger, String event, Throwable throwable) {
        String message = null;
        if (throwable != null) {
            message = dumpThrowable(throwable).replaceAll("\n", "|").replaceAll("\t", "    ");
        }

        logKit(logger, Level.WARN, event, null, message);
    }

    public static void logWarn(Logger logger, String event, Object... objs) {
        logKit(logger, Level.WARN, event, null, objs);
    }

    public static void logPlace(Logger logger, String event, String placeholder, Object... objs) {
        logKit(logger, Level.INFO, event, placeholder, objs);
    }

    public static void log(Logger logger, String event, Object... objs) {
        logKit(logger, Level.INFO, event, null, objs);
    }


    private static void logKit(Logger logger, Level logLevel, String event, String placeholder, Object... objs) {
        StringBuilder builder = new StringBuilder();
        builder.append("e=").append(event);
        if (objs != null) {
            int i = 0;
            if (!ObjectUtils.isEmpty(placeholder)) {
                builder.append(" ");
                Matcher m = pattern.matcher(placeholder);
                while (m.find() && objs.length > i) {
                    if (objs[i] == null) {
                        m.appendReplacement(builder, "null");
                    } else {
                        m.appendReplacement(builder, "".equals(objs[i].toString()) ? "-" : objs[i].toString());
                    }
                    i++;
                }
                m.appendTail(builder);
            }
            int j = 0;
            for (; i < objs.length; ++i) {
                builder.append(" ").append("v").append(j++).append("=");
                if (objs[i] == null) {
                    builder.append("null");
                } else {
                    builder.append(objs[i]);
                }
            }
        }
        String message = builder.toString();
        switch (logLevel) {
            case Level.DEBUG:
                logger.debug(message);
                break;
            case Level.INFO:
                logger.info(message);
                break;
            case Level.WARN:
                logger.warn(message);
                break;
            default:
                logger.error(message);
                break;
        }
    }
}
