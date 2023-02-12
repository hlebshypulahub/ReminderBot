package org.hleb.bot.service;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Comparator;
import java.util.List;

public class ReminderBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasPhoto()) {
            Message message = update.getMessage();
            List<PhotoSize> photoSizes = message.getPhoto();

            // Get the largest photo
            PhotoSize photoSize = photoSizes.stream()
                    .max(Comparator.comparing(PhotoSize::getFileSize))
                    .orElse(null);

            if (photoSize != null) {
                // Get the file ID of the largest photo
                String fileId = photoSize.getFileId();

                // Get the file path of the largest photo
                try {
                    File file = execute(new GetFile().setFileId(fileId));
                    String filePath = file.getFilePath();
                    // You can now download the image using the file path

                    SendPhoto sendPhoto = new SendPhoto();
                    sendPhoto.setChatId(update.getMessage().getChatId());
                    sendPhoto.setPhoto(file.getFileId());
                    try {
                        execute(sendPhoto);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public String getBotUsername() {
        // return the bot's username
        return "Reminder Bot";
    }

    public String getBotToken() {
        // return the bot's API key
        return System.getenv("TELEGRAM_BOT_API_KEY");
    }
}


