package org.mbte.dmabot;

import org.json.JSONObject;

import java.util.HashMap;

public class MessageProcessor {
    private int latestId = 0;

    private HashMap<Integer,Chat> chats = new HashMap<Integer, Chat>();

    public void process(JSONObject jsonObject) {
        System.out.println(jsonObject);

        int update_id = jsonObject.getInt("update_id");
        if (update_id > latestId) {
            latestId = update_id;

            JSONObject message = jsonObject.getJSONObject("message");
            JSONObject chatObj = message.getJSONObject("chat");
            int id = chatObj.getInt("id");
            Chat chat = chats.get(id);
            if (chat == null) {
                chats.put(id, chat = new Chat(chatObj));
            }
            chat.proccess(message);
        }
    }

    public int getLatestId() {
        return latestId;
    }
}
