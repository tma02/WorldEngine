import com.worldstone.worldengine.WorldEngine;
import com.worldstone.worldengine.net.listener.LoginListener;
import com.worldstone.worldengine.net.packet.PacketAction;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.scalecube.socketio.Session;
import io.scalecube.socketio.TransportType;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.Map;

public class LoginListenerTest {

    @Test
    public void testLoginListener() {
        WorldEngine.INSTANCE = new WorldEngine(new File("./config.json"));
        WorldEngine.INSTANCE.init();
        LoggerFactory.getLogger(this.getClass()).info("Simulating packet...");
        LoginListener listener = new LoginListener();
        listener.registerPacketActions();
        /*listener.registerPacketAction(new PacketAction("test_packet") {
            @Override
            public void run(Session session, Map<String, String> attributes) {
                Assert.assertEquals(attributes.get("test_attribute"), "test_value");
            }
        });*/
        ByteBufOutputStream stream = new ByteBufOutputStream(Unpooled.buffer(128));
        try {
            stream.writeUTF("{\"name\": \"login\", \"attributes\": {\"email\":\"test@test.test\",\"password\":\"test\"}}");
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listener.onMessage(new Session() {
            @Override
            public String getSessionId() {
                return "0";
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
                    System.out.println(packetString);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void disconnect() {

            }
        }, stream.buffer());
    }

}
