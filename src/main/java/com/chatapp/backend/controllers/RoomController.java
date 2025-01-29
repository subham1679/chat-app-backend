package com.chatapp.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.chatapp.backend.entities.Message;
import com.chatapp.backend.entities.Room;
import com.chatapp.backend.repo.RoomRepository;

@RestController
@RequestMapping("/api/room")
public class RoomController {

	@Autowired
	RoomRepository roomRepository;

	@PostMapping
	public ResponseEntity<?> createRoom(@RequestBody String roomId) {
		Room roomid = roomRepository.findByRoomId(roomId);
		if (roomid != null)

		{
			return ResponseEntity.badRequest().body("room already exist");
		}

		Room room = new Room();
		room.setRoomId(roomId);
		Room savedRoom = roomRepository.save(room);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);

	}

	@GetMapping("/{roomId}")
	public ResponseEntity<?> joinRoom(@PathVariable String roomId) {
		Room roomid = roomRepository.findByRoomId(roomId);
		if (roomid == null)

		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("room does not exist");
		}

		return ResponseEntity.ok(roomid);

	}
	
	@GetMapping("/{roomId}/messages")
	public ResponseEntity<?> getMessages(@PathVariable String roomId)
	{
		if(roomRepository.findByRoomId(roomId)==null)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no messages found");
		}
		
		Room room = roomRepository.findByRoomId(roomId);
		
		List<Message> messages = room.getMessages();
		
		return ResponseEntity.ok().body(messages);
		
	}

}
