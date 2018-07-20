package com.worldstone.worldengine.net.listener;

import com.google.gson.Gson;
import com.worldstone.worldengine.WorldEngine;
import com.worldstone.worldengine.database.User;
import com.worldstone.worldengine.net.packet.Packet;
import com.worldstone.worldengine.net.packet.PacketAction;
import io.scalecube.socketio.Session;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class LoginListener extends UserSocketIOListener {

    @Override
    public void registerPacketActions() {
        LoginListener _this = this;

        PacketAction loginPacketAction = new PacketAction("login") {
            @Override
            public void run(Session session, Map<String, String> attributes) {
                String email = attributes.get("email").toLowerCase();
                String password = attributes.get("password");
                Packet loginResponsePacket = new Packet("login");
                try {
                    User user = WorldEngine.INSTANCE.getDatabase().loadUser(email);
                    if (user.getHashPassword().equals(User.getUserPasswordHash(email, password))) {
                        _this.authenticateSession(session.getSessionId(), user);
                        loginResponsePacket.getAttributes().put("response", "success");
                    }
                    else {
                        loginResponsePacket.getAttributes().put("response", "invalid_password");
                    }
                } catch (Exception e) {
                    loginResponsePacket.getAttributes().put("response", "user_not_found");
                    e.printStackTrace();
                }
                try {
                    session.send(loginResponsePacket.asByteBuf());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        this.registerPacketAction(loginPacketAction);

        PacketAction retrieveCharactersPacketAction = new PacketAction("retrieve_characters") {
            @Override
            public void run(Session session, Map<String, String> attributes) {
                Packet retrieveCharactersResponsePacket = new Packet("retrieve_characters");
                Gson gson = new Gson();
                if (_this.isAuthenticated(session.getSessionId())) {
                    List<String> characterList = _this.getUser(session.getSessionId()).getCharacterList();
                    retrieveCharactersResponsePacket.getAttributes().put("response", "success");
                    retrieveCharactersResponsePacket.getAttributes().put("character_list", gson.toJson(characterList));
                }
                else {
                    retrieveCharactersResponsePacket.getAttributes().put("response", "session_not_authenticated");
                }
                try {
                    session.send(retrieveCharactersResponsePacket.asByteBuf());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        this.registerPacketAction(retrieveCharactersPacketAction);
    }

}
