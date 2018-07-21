package com.worldstone.worldengine.net.listener;

import com.google.gson.Gson;
import com.worldstone.worldengine.net.packet.Packet;
import com.worldstone.worldengine.net.packet.PacketAction;
import io.netty.buffer.ByteBuf;
import io.scalecube.socketio.Session;
import io.scalecube.socketio.SocketIOListener;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class PacketSocketIOListener implements SocketIOListener {

    private Map<String, PacketAction> packetActions = new HashMap<>();

    @Override
    public void onConnect(Session session) {
        LoggerFactory.getLogger(this.getClass()).info("CONN: " + session);
    }

    @Override
    public void onMessage(Session session, ByteBuf buffer) {
        try {
            Packet packet = new Packet(buffer);
            String attributesJson = new Gson().toJson(packet.getAttributes());
            LoggerFactory.getLogger(this.getClass()).info("RECV: #" + packet.getName() + "%" + attributesJson);

            if (this.packetActions.containsKey(packet.getName())) {
                this.packetActions.get(packet.getName()).run(session, packet.getAttributes());
            }
            else {
                LoggerFactory.getLogger(this.getClass()).warn("No packet action registered for packet #" + packet.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            buffer.release();
        }
    }

    @Override
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

    public abstract void registerPacketActions();

}
