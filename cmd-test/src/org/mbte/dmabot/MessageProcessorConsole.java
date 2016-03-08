package org.mbte.dmabot;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class MessageProcessorConsole extends MessageProcessor {
    private final HashMap<Long,Chat> chats = new HashMap<Long, Chat>();
    private long latestId;

    public MessageProcessorConsole(Telegram telegram) {
        super(telegram);
    }

    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) {
            JSONObject updates = telegram.getUpdates(latestId + 1, 0, 10);
            if (updates.optBoolean("ok")) {
                JSONArray result = updates.getJSONArray("result");
                for (int i = 0; i != result.length(); ++i) {
                    process(result.getJSONObject(i));
                }
            }
        }
    }

    @Override
    protected boolean ignoreIfAlreadyProcessedMessage(int update_id) {
        if(update_id > latestId) {
            latestId = update_id;
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    protected Chat getChat(long chatId, JSONObject chatObj) {
        Chat chat = chats.get(chatId);
        if (chat == null) {
            chats.put(chatId, chat = new Chat(telegram, chatObj));
        }
        return chat;
    }
}
