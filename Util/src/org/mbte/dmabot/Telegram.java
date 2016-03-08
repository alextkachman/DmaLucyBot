package org.mbte.dmabot;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import static org.mbte.dmabot.StreamUtils.post;

public class Telegram {
    public static final Logger log = Logger.getLogger(Telegram.class.getSimpleName());

    public final String token;

    public Telegram(String token) {
        this.token = token;
    }

    public String call(String method, JSONObject args) {
        byte[] bytes;
        try {
            bytes = args.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            bytes = args.toString().getBytes();
        }
        return post("https://api.telegram.org/bot" + token + "/" + method, bytes, null);
    }


    public String setWebhook(String hookUrl) {
        JSONObject args = new JSONObject();
        if (hookUrl != null) {
            args.put("url", hookUrl);
        }
        return call("setWebhook", args);
    }

    public JSONObject getUpdates(long offset, int limit, int timeout) {
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

    public JSONObject sendChatAction(long chat_id, String action) {
        JSONObject args = new JSONObject();
        args.put("chat_id",chat_id);
        args.put("action",action);
        return new JSONObject(call("sendChatAction", args));
    }

    public String sendMessage(JSONObject toSend) {
        return call("sendMessage", toSend);
    }
}
