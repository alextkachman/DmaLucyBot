package org.mbte.dmabot;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.logging.Logger;

public abstract class MessageProcessor {
    static final Logger log = Telegram.log;

    protected final Telegram telegram;

    public MessageProcessor(Telegram telegram) {
        this.telegram = telegram;
    }

    public void process(JSONObject jsonObject) {
        log.info(jsonObject.toString());

        int update_id = jsonObject.optInt("update_id",-239);
        if (update_id != -239) {
            if (ignoreIfAlreadyProcessedMessage(update_id)) {
                JSONObject message = jsonObject.optJSONObject("message");
                if (message != null) {
                    JSONObject chatObj = message.optJSONObject("chat");
                    if (chatObj != null) {
                        long id = chatObj.optLong("id");
                        if (id != 0L) {
                            Chat chat = getChat(id, chatObj);
                            chat.proccess(message);
                        }
                        else {
                            log.severe("Missing chat id");
                        }
                    }
                    else {
                        log.severe("Missing chat");
                    }
                }
                else {
                    log.severe("Missing message");
                }
            }
            else {
                log.info("Ignoring already processed message");
            }
        }
        else {
            log.severe("Missing update_id");
        }
    }


    protected abstract boolean ignoreIfAlreadyProcessedMessage(int update_id);
    protected abstract Chat getChat(long chatId, JSONObject chatObj);
}
