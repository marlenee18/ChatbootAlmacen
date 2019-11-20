package com.asismed.asistentem.bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.List;


public class MainBot extends TelegramLongPollingBot{

    public static void main (String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try { telegramBotsApi.registerBot(new MainBot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }


    public void sendMsg(Message message, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
       try {
          setButtons(sendMessage);
           // sendMessage(sendMessage);
      }catch (TelegramApiException e){
           e.printStackTrace();
        }
   }

    public void onUpdateReceived(Update update) {
        //  Model model = new Model();
      //  Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/inicio":

                    sendMsg(message, "hola soy el asistente automatico                 " +
                            "En que te puedo ayudar?"
                    );
                    break;
                case "/ReservarCitaMedica":
                    sendMsg(message, "Porfavor seleccione una especialidad       " );
                    sendMsg(message,      "/MedicinaGeneral      " +
                            "/Pediatria         " +
                            "/Traumatologia     ");
                    break;

                case "/VerEspecialidades":
                    sendMsg(message, "/MedicinaGeneral");
                    sendMsg(message, "/Pediatria");
                    sendMsg(message, "/Traumatologia");
                default:
            }
        }

    }

    public void setButtons(SendMessage sendMessage){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowsList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        //keyboardFirstRow.add(new KeyboardButton( "/inicio"));
        keyboardFirstRow.add(new KeyboardButton( "/ReservarCitaMedica"));
        keyboardFirstRow.add(new KeyboardButton( "/VerEspecialidades"));

        keyboardRowsList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowsList);
    }


    public String getBotUsername() {
        return "AsisMedBot";
    }

    public String getBotToken() {
        return "965898434:AAFYisxZkAsAWykdChxs9DNy1ceCADAmogo";
    }
}