package dao;

import models.Message;
import java.util.List;

public interface MessageDAO {
    boolean createMessage(Message message);
    List<Message> getMessagesByUserId(int userId);
    List<Message> getUnreadMessagesByUserId(int userId);
    boolean updateMessageReadStatus(int messageId, boolean isRead);
    boolean deleteMessage(int messageId);
    int getUnreadMessageCount(int userId);
}