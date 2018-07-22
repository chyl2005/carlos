package com.github.carlos.app.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/17 13:56
 * @description: TODO
 */
public class AccessHttpServletResponseWrapper extends HttpServletResponseWrapper {

    private PrintWriter tmpWriter;
    private ByteArrayOutputStream output;
    private ServletOutputStream servletOutput;

    private int sc;
    private String msg;
    private String location;

    public AccessHttpServletResponseWrapper(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
        output = new ByteArrayOutputStream();
        tmpWriter = new PrintWriter(output);
        servletOutput = new ServletOutputStream() {

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {

            }

            @Override
            public void write(int i) throws IOException {
                output.write(i);
            }

        };
    }

    public byte[] getContent() throws IOException {
        return output.toByteArray();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return tmpWriter;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return servletOutput;
    }

    @Override
    public void flushBuffer() throws IOException {
        tmpWriter.flush();
        servletOutput.flush();
    }

    @Override
    public void reset() {
        output.reset();
    }

    public void destory() throws IOException {
        servletOutput.close();
        output.close();
        tmpWriter.close();
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        super.sendError(sc, msg);
        this.sc = sc;
        this.msg = msg;
    }

    @Override
    public void sendError(int sc) throws IOException {
        super.sendError(sc);
        this.sc = sc;
    }

    @Override
    public void sendRedirect(String location) throws IOException {
        super.sendRedirect(location);
        this.location = location;
    }

    public int getSc() {
        return sc;
    }

    public String getMsg() {
        return msg;
    }

    public String getLocation() {
        return location;
    }

}
