package org.hleb.bot;

import org.hleb.bot.service.ReminderBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class ReminderBotApplication {

    public static void main(String[] args) {
        // Initialize the Telegram Bots API
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();

        // Register the bot
        try {
            botsApi.registerBot(new ReminderBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
