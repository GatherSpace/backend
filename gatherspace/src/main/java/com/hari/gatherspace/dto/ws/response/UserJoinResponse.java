package com.hari.gatherspace.dto.ws.response;

import com.hari.gatherspace.model.ws.Point;

public class UserJoinResponse {

    private String userId;
    private Point point;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
