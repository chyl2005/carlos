package com.github.carlos.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/17 14:00
 * @description: TODO
 */
public class StreamUtils {

    public static final int DEFAULT_BUFFER_SIZE = 2048;

    private StreamUtils() {
    }

    public static void copy(InputStream input, OutputStream output) throws IOException {
        copy(input, output, 2048);
    }

    public static void copy(InputStream input, OutputStream output, int bufferSize) throws IOException {
        byte[] buf = new byte[bufferSize];

        for (int bytesRead = input.read(buf); bytesRead != -1; bytesRead = input.read(buf)) {
            output.write(buf, 0, bytesRead);
        }

        output.flush();
    }

    public static void copyThenClose(InputStream input, OutputStream output) throws IOException {
        copy(input, output);
        input.close();
        output.close();
    }

    public static byte[] getBytes(InputStream input) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        copy(input, result);
        result.close();
        return result.toByteArray();
    }
}
