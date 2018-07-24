package com.worldstone.worldengine.net.listener;

import com.worldstone.worldengine.net.ServerInfo;
import com.worldstone.worldengine.net.packet.Packet;
import com.worldstone.worldengine.net.packet.PacketAction;
import io.scalecube.socketio.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ControlListener extends InterserverSocketIOListener {

    @Override
    public void registerPacketActions() {
        ControlListener _this = this;

        this.registerPacketAction(new PacketAction("auth") {
            @Override
            public void run(Session session, Map<String, String> attributes) {
                String type = attributes.get("type");
                String name = attributes.get("name");
                String key = attributes.get("key");
                String ip = session.getRemoteAddress().toString().split(":")[0];
                int port = Integer.parseInt(attributes.get("port"));
                // TODO: auth
            }
        });

        this.registerPacketAction(new PacketAction("retrieve_game_servers") {
            @Override
            public void run(Session session, Map<String, String> attributes) {
                Packet responsePacket = new Packet("retrieve_game_servers");
                if (_this.isAuthenticated(session.getSessionId())) {
                    List<ServerInfo> gameServerList = _this.getServerInfoCollection().stream().filter(s -> s.getType()
                            .equals("game")).collect(Collectors.toList());
                    responsePacket.getAttributes().put("response", "success");
                    responsePacket.getAttributes().put("game_servers", gson.toJson(gameServerList));
                }
                else {
                    responsePacket.getAttributes().put("response", "session_not_authenticated");
                }
                try {
                    session.send(responsePacket.asByteBuf());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
