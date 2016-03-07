package org.mbte.dmabot;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class HookServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ServletInputStream inputStream = request.getInputStream();
            String streamContents = StreamUtils.getStreamContents(inputStream);
            if (streamContents != null) {
                JSONObject jsonObject = new JSONObject(streamContents);
                Logger.getAnonymousLogger().info(jsonObject.toString());
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
