package com.worldstone.worldengine.net.packet;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Packet {

    private String name;
    private Map<String, String> attributes;

    public Packet(String name) {
        this.name = name;
        this.attributes = new HashMap<>();
    }

    public Packet(ByteBuf buffer) throws IOException {
        // Read buffer to strings
        ByteBufInputStream stream = new ByteBufInputStream(buffer);
        String packetString = stream.readUTF();

        // Deserialize string to JSON object
        Gson gson = new Gson();
        Packet packet = gson.fromJson(packetString, Packet.class);
        this.name = packet.getName();
        this.attributes = packet.getAttributes();
    }

    public String getName() {
        return this.name;
    }

    public Map<String, String> getAttributes() {
        return this.attributes;
    }

}
