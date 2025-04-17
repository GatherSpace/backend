package com.hari.gatherspace.features.user;

import java.io.IOException;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

@Data
public class UserWs {
  private String id;
  private String userId; // Corresponds to the authenticated user ID
  private String spaceId;
  private int x;
  private int y;
  private WebSocketSession session;

  public UserWs(WebSocketSession session) {
    this.session = session;
    this.id = generateId(); // Generate a unique ID for the WebSocket user
    this.x = 0;
    this.y = 0;
  }

  private String generateId() {
    // Simple random string generator for WebSocket user ID
    return java.util.UUID.randomUUID().toString();
  }

  public void send(String message) {
    try {
      if (session.isOpen()) {
        session.sendMessage(new org.springframework.web.socket.TextMessage(message));
      }
    } catch (IOException e) {
      System.err.println("Error sending message to user " + id + ": " + e.getMessage());
    }
  }
}
