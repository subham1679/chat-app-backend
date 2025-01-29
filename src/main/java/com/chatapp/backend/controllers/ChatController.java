package com.chatapp.backend.controllers;

import java.time.LocalDateTime;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.backend.entities.Message;
import com.chatapp.backend.entities.Room;
import com.chatapp.backend.payload.MesageRequest;
import com.chatapp.backend.repo.RoomRepository;


@RestController
public class ChatController {
	
	@Autowired
	RoomRepository roomRepository;
	
	
	@MessageMapping("/sendMessage/{roomId}")// /app/sendMessage/roomId
        @SendTo("/topic/room/{roomId}")//subscribe
	public Message sendMessages(  @DestinationVariable String roomId,@RequestBody MesageRequest request)
	{
		Room room = roomRepository.findByRoomId(request.getRoomId());
		
		Message message = new Message();
		if(room!=null)
		{
			
			message.setContent(request.getContent());
			message.setSender(request.getSender());
			message.setTimestamp(LocalDateTime.now());
			room.getMessages().add(message);
			roomRepository.save(room);
		}
		else
		{
			throw new RuntimeException("room not found");
		}
		return message;
	}

}
