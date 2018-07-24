import com.worldstone.worldengine.net.ServerInfo;
import com.worldstone.worldengine.net.listener.ControlListener;
import com.worldstone.worldengine.net.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.scalecube.socketio.Session;
import io.scalecube.socketio.TransportType;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketAddress;

public class RetrieveGameServersTest {

    @Test
    public void testRetrieveGameServers() throws IOException {
        ControlListener controlListener = new ControlListener();
        controlListener.registerPacketActions();
        controlListener.authenticateSession("test", new ServerInfo("game", "Game 1", "localhost", 38193));
        Packet testPacket = new Packet("retrieve_game_servers");
        controlListener.onMessage(new Session() {
            @Override
            public String getSessionId() {
                return "test";
            }

            @Override
            public String getOrigin() {
                return null;
            }

            @Override
            public boolean isUpgradedSession() {
                return false;
            }

            @Override
            public TransportType getUpgradedFromTransportType() {
                return null;
            }

            @Override
            public TransportType getTransportType() {
                return null;
            }

            @Override
            public SocketAddress getRemoteAddress() {
                return null;
            }

            @Override
            public State getState() {
                return null;
            }

            @Override
            public int getLocalPort() {
                return 0;
            }

            @Override
            public void send(ByteBuf byteBuf) {
                ByteBufInputStream stream = new ByteBufInputStream(byteBuf);
                try {
                    String packetString = stream.readUTF();
                    LoggerFactory.getLogger(this.getClass()).info(packetString);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void disconnect() {

            }
        }, testPacket.asByteBuf());
    }

}
