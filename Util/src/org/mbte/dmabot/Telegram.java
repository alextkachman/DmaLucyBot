package org.mbte.dmabot;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import static org.mbte.dmabot.StreamUtils.post;

public class Telegram {
    public static final String TOKEN = "197282174:AAH3eM9aNxM9DESgqjnKdIoPEWX3gB24zok";

    public static String call(String method, JSONObject args) {
        byte[] bytes;
        try {
            bytes = args.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            bytes = args.toString().getBytes();
        }
        return post("https://api.telegram.org/bot" + TOKEN + "/" + method, bytes, null);
    }


    public static String setWebhook(String hookUrl) {
        JSONObject args = new JSONObject();
        if (hookUrl != null) {
            args.put("url", hookUrl);
        }
        return call("setWebhook", args);
    }

    public static JSONObject getUpdates(int offset, int limit, int timeout) {
        JSONObject args = new JSONObject();
        if (offset > 0) {
            args.put("offset", offset);
        }
        if (limit > 0) {
            args.put("limit", limit);
        }
        if (timeout > 0) {
            args.put("timeout", timeout);
        }
        return new JSONObject(call("getUpdates", args));
    }

    public static JSONObject sendChatAction(int chat_id, String action) {
        JSONObject args = new JSONObject();
        args.put("chat_id",chat_id);
        args.put("action",action);
        return new JSONObject(call("sendChatAction", args));
    }
}
