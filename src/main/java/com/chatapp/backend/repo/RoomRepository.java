package com.chatapp.backend.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.chatapp.backend.entities.Room;


@Service
public interface RoomRepository extends MongoRepository<Room, String>{
	
	Room findByRoomId(String roomId);

}
