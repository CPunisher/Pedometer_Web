package com.cpunisher.pedo.util.gzip;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class GZipServletOutputStream extends ServletOutputStream {

    private boolean closed;
    private GZIPOutputStream gzipOutputStream;
    private ByteArrayOutputStream byteArrayOutputStream;
    private HttpServletResponse response;

    public GZipServletOutputStream(HttpServletResponse response) throws IOException {
        this.response = response;
        byteArrayOutputStream = new ByteArrayOutputStream();
        gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        closed = false;
    }

    @Override
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        if (closed) {
            throw new IllegalStateException("Stream has been closed");
        }
        gzipOutputStream.write(b, off, len);
    }

    @Override
    public void write(int b) throws IOException {
        if (closed) {
            throw new IllegalStateException("Stream has been closed");
        }
        gzipOutputStream.write(b);
    }

    @Override
    public void close() throws IOException {
        if (closed) {
            throw new IllegalStateException("Stream has been closed");
        }
        gzipOutputStream.finish();

        byte[] bytes = byteArrayOutputStream.toByteArray();
        OutputStream outputStream = response.getOutputStream();
        response.setHeader("Content-Encoding", "gzip");
        response.setHeader("Content-Length", String.valueOf(bytes.length));
        outputStream.write(bytes, 0, bytes.length);
        byteArrayOutputStream.close();
        gzipOutputStream.close();
        closed = true;
    }

    @Override
    public boolean isReady() {
        return !closed;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {

    }
}
