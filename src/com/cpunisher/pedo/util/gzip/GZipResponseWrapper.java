package com.cpunisher.pedo.util.gzip;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class GZipResponseWrapper extends HttpServletResponseWrapper {

    private GZipServletOutputStream gZipServletOutputStream;
    private PrintWriter printWriter;
    private HttpServletResponse response;

    public void finish() throws IOException {
        if (printWriter != null) {
            printWriter.close();
        }
        if (gZipServletOutputStream != null) {
            gZipServletOutputStream.close();
        }
    }

    public GZipResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
        this.response = response;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (printWriter != null) {
            return printWriter;
        }
        if (gZipServletOutputStream != null) {
            throw new IllegalStateException("Output Stream has been created");
        }
        printWriter = new PrintWriter(new OutputStreamWriter(new GZipServletOutputStream(response), "utf-8"));
        return printWriter;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (gZipServletOutputStream != null) {
            return gZipServletOutputStream;
        }
        if (printWriter != null) {
            throw new IllegalStateException("Print Writer has been created");
        }
        gZipServletOutputStream = new GZipServletOutputStream(response);
        return gZipServletOutputStream;
    }
}
