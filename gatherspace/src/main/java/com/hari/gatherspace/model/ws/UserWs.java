package com.hari.gatherspace.model.ws;

import org.springframework.web.socket.WebSocketSession;


public class UserWs {

    private String id;
    private String spaceId;
    private Point point;
    private WebSocketSession session;

    public UserWs(String userId, WebSocketSession session) {
        this.id = userId;
        this.session = session;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public void setSession(WebSocketSession session) {
        this.session = session;
    }

    public void setPosition(int x, int y) {
        this.point = new Point(x, y);
    }
}
