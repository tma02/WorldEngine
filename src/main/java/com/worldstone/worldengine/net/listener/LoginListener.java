package com.worldstone.worldengine.net.listener;

import com.google.gson.Gson;
import com.worldstone.worldengine.net.packet.Packet;
import com.worldstone.worldengine.net.packet.PacketAction;
import io.netty.buffer.ByteBuf;
import io.scalecube.socketio.Session;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginListener extends UserSocketIOListener {

    private Map<String, PacketAction> packetActions = new HashMap<>();

    public void onConnect(Session session) {
        LoggerFactory.getLogger(this.getClass()).info("CONN: " + session);
    }

    public void onMessage(Session session, ByteBuf buffer) {
        try {
            Packet packet = new Packet(buffer);
            String attributesJson = new Gson().toJson(packet.getAttributes());
            LoggerFactory.getLogger(this.getClass()).info("RECV: #" + packet.getName() + "%" + attributesJson);

            if (this.packetActions.containsKey(packet.getName())) {
                this.packetActions.get(packet.getName()).run(session, packet.getAttributes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            buffer.release();
        }
    }

    public void onDisconnect(Session session) {
        LoggerFactory.getLogger(this.getClass()).info("DISC: " + session);
    }

    public void registerPacketAction(PacketAction packetAction) {
        if (this.packetActions.containsKey(packetAction.getPacketName())) {
            LoggerFactory.getLogger(this.getClass()).warn("Overriding PacketAction for #" + packetAction.getPacketName() + ".");
        }
        this.packetActions.put(packetAction.getPacketName(), packetAction);
        LoggerFactory.getLogger(this.getClass()).info("Registered PacketAction for #" + packetAction.getPacketName() + ".");
    }

    public void registerPacketActions() {
        LoginListener _this = this;

        PacketAction loginPacketAction = new PacketAction("login") {
            @Override
            public void run(Session session, Map<String, String> attributes) {
                String email = attributes.get("email");
                String password = attributes.get("password");
                // TODO
            }
        };
        this.registerPacketAction(loginPacketAction);

        PacketAction retrieveCharactersPacketAction = new PacketAction("retrieve_characters") {
            @Override
            public void run(Session session, Map<String, String> attributes) {
                _this.getUser(session.getSessionId()).getCharacterList();
                // TODO
            }
        };
        this.registerPacketAction(retrieveCharactersPacketAction);
    }

}
