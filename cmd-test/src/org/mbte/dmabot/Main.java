package org.mbte.dmabot;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Main {
    public static void main(String[] _args) throws UnsupportedEncodingException {
//        System.out.println(Telegram.setWebhook("https://dmabot-1209.appspot.com/bot"));
        System.out.println(Telegram.setWebhook(null));
        MessageProcessor messageProcessor = new MessageProcessor();
        while(true) {
            JSONObject updates = Telegram.getUpdates(messageProcessor.getLatestId()+1, 0, 10);
            if (updates.optBoolean("ok")) {
                JSONArray result = updates.getJSONArray("result");
                for (int i = 0; i != result.length(); ++i) {
                    messageProcessor.process(result.getJSONObject(i));
                }
            }
        }
    }
}
