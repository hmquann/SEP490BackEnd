package org.example.motorbikerental.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.motorbikerental.entity.Message;
import org.example.motorbikerental.repository.MessageRepository;
import org.example.motorbikerental.service.MessageService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    @Override
    public List<Message> getMessagesByRoom(String room) {
        return messageRepository.findByRoom(room);
    }

    @Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getAllMessagesByUser(String room) {
        return messageRepository.getAllMessageByUser(room);
    }

    @Override
    public Message getLastMessageByUniqueRoom(String uniqueRoom) {
        Pageable pageable = PageRequest.of(0, 1); // Lấy page đầu tiên với kích thước 1 (lấy top 1 record)
        List<Message> messages = messageRepository.getLastMessageByUniqueRoom(uniqueRoom,pageable);

        if (!messages.isEmpty()) {
            return messages.get(0); // Trả về phần tử đầu tiên trong danh sách, tức là top 1 record
        }

        return null;
    }


}
