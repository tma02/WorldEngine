package com.worldstone.worldengine.net.listener;

import com.google.gson.Gson;
import com.worldstone.worldengine.net.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import io.scalecube.socketio.Session;
import io.scalecube.socketio.SocketIOListener;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoginListener implements SocketIOListener {

    public void onConnect(Session session) {
        LoggerFactory.getLogger(this.getClass()).info("CONN: " + session);
    }

    public void onMessage(Session session, ByteBuf buffer) {
        try {
            Packet packet = new Packet(buffer);
            String attributesJson = new Gson().toJson(packet.getAttributes());
            LoggerFactory.getLogger(this.getClass()).info("RECV: #" + packet.getName() + "%" + attributesJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer.release();
    }

    public void onDisconnect(Session session) {
        LoggerFactory.getLogger(this.getClass()).info("DISC: " + session);
    }

}
