package com.github.carlos.app.filter;




import com.github.carlos.common.utils.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/17 13:53
 * @description: TODO
 */
public class AccessHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public AccessHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = StreamUtils.getBytes(request.getInputStream());
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }

        };
    }

    public String readBody() throws IOException {
        String body = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(this.body);
            body = byteArrayOutputStream.toString("utf-8");
        } finally {
            byteArrayOutputStream.close();
        }
        return body;
    }

}
