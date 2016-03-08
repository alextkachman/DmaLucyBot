package org.mbte.dmabot;

import java.io.UnsupportedEncodingException;

public class Main {

    private static final String DMA_TOKEN = "197282174:AAH3eM9aNxM9DESgqjnKdIoPEWX3gB24zok";

    public static void main(String[] _args) throws UnsupportedEncodingException {
        Telegram telegram = new Telegram(DMA_TOKEN);
//        System.out.println(Telegram.setWebhook("https://dmabot-1209.appspot.com/bot"));
        Telegram.log.info(telegram.setWebhook(null));
        new MessageProcessorConsole(telegram).run();
    }

}
