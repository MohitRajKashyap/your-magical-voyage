package services;

import dao.MessageDAO;
import dao.MessageDAOImpl;
import models.Message;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        this.messageDAO = new MessageDAOImpl();
    }

    public boolean sendMessage(Message message) {
        return messageDAO.createMessage(message);
    }

    public List<Message> getMessagesForUser(int userId) {
        return messageDAO.getMessagesByUserId(userId);
    }

    public List<Message> getUnreadMessages(int userId) {
        return messageDAO.getUnreadMessagesByUserId(userId);
    }

    public boolean markMessageAsRead(int messageId) {
        return messageDAO.updateMessageReadStatus(messageId, true);
    }

    public boolean deleteMessage(int messageId) {
        return messageDAO.deleteMessage(messageId);
    }

    public int getUnreadMessageCount(int userId) {
        return messageDAO.getUnreadMessageCount(userId);
    }
}