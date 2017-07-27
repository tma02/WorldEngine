package com.worldstone.worldengine.net.packet;

import io.scalecube.socketio.Session;

import java.util.Map;

public abstract class PacketAction {

    private String packetName;

    public PacketAction(String packetName) {
        this.packetName = packetName;
    }

    public abstract void run(Session session, Map<String, String> attributes);

    public String getPacketName() {
        return this.packetName;
    }

}
