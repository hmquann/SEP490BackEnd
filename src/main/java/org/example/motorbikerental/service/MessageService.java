package org.example.motorbikerental.service;


import org.example.motorbikerental.entity.Message;

import java.util.List;

public interface MessageService {
    List<Message> getMessagesByRoom(String room);
    Message saveMessage(Message message);
    List<Message> getAllMessagesByUser(String room);

    Message getLastMessageByUniqueRoom(String uniqueRoom);
}
