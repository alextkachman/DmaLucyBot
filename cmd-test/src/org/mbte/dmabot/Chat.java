package org.mbte.dmabot;

import org.json.JSONArray;
import org.json.JSONObject;

public class Chat {
    private final Telegram telegram;
    private final JSONObject chatObj;
    private final long chatId;

    public Chat(Telegram telegram, JSONObject chatObj) {
        this.telegram = telegram;
        this.chatObj = chatObj;
        this.chatId = chatObj.getLong("id");
    }

    public void proccess(JSONObject message) {
        telegram.sendChatAction(chatId, "typing");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {  //
        }

        JSONObject toSend = new JSONObject();
        toSend.put("chat_id", chatId);
        toSend.put("text", "<b>Hello!</b> <i>How can I help you?</i> <a href=\"http://site.dialmyapp.com\">Use DilaMyApp, bro!</a>\nUse Menu below to select department");
        toSend.put("parse_mode", "HTML");
        JSONObject keyboard = new JSONObject();
        JSONArray rows = new JSONArray();
        JSONArray row1 = new JSONArray();
        row1.put("Technical Support");
        rows.put(row1);
        JSONArray row2 = new JSONArray();
        row2.put("Sales");
        row2.put("Deals");
        rows.put(row2);
        keyboard.put("keyboard", rows);
        keyboard.put("resize_keyboard",true);
        keyboard.put("one_time_keyboard",true);
        toSend.put("reply_markup", keyboard);
        telegram.sendMessage(toSend);
    }

}
