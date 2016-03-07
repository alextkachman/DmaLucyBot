package org.mbte.dmabot;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class StreamUtils
{
    public static String post(String url, byte[] content, Map<String,String> headers) {
        try {
            final HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            try {
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Length", String.valueOf(content.length));
                if (headers == null) {
                    conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                }
                else {
                    for (Map.Entry<String, String> e : headers.entrySet()) {
                        conn.setRequestProperty(e.getKey(), e.getValue());
                    }
                }

                conn.setDoOutput(true);
                final OutputStream outputStream = conn.getOutputStream();
                try {
                    outputStream.write(content);
                    final InputStream inputStream = conn.getInputStream();
                    try {
                        return StreamUtils.getStreamContents(inputStream);
                    }
                    finally {
                        inputStream.close();
                    }
                }
                finally {
                    outputStream.close();
                }
            }
            finally {
                conn.disconnect();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getStreamContents(InputStream is) {
        try
        {
            final char[] buffer = new char[0x10000];
            StringBuilder out = new StringBuilder();
            Reader in = new InputStreamReader(is, "UTF-8");
            int read;
            do {
                read = in.read(buffer, 0, buffer.length);
                if (read > 0)
                {
                    out.append(buffer, 0, read);
                }
            } while (read >= 0);
            in.close();
            return out.toString();
        } catch (IOException ioe) {
            return null;
        }
    }


    public static byte[] getInputStreamAsByteArray(InputStream inputStream) {
        if (inputStream == null)
            return null;

        try {
            final byte[] buffer = new byte[128 * 1024];
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            int read;
            do {
                read = inputStream.read(buffer, 0, buffer.length);
                if (read > 0) {
                    byteArrayOutputStream.write(buffer, 0, read);
                }
            } while (read >= 0);
            inputStream.close();

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            return null;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) { //
            }
        }
    }
}
