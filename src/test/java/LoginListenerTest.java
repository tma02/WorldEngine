import com.worldstone.worldengine.net.listener.LoginListener;
import com.worldstone.worldengine.net.packet.PacketAction;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.scalecube.socketio.Session;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class LoginListenerTest {

    @Test
    public void testLoginListener() {
        LoggerFactory.getLogger(this.getClass()).info("Simulating packet...");
        LoginListener listener = new LoginListener();
        listener.registerPacketAction(new PacketAction("test_packet") {
            @Override
            public void run(Session session, Map<String, String> attributes) {
                Assert.assertEquals(attributes.get("test_attribute"), "test_value");
            }
        });
        ByteBufOutputStream stream = new ByteBufOutputStream(Unpooled.buffer(128));
        try {
            stream.writeUTF("{\"name\": \"test_packet\", \"attributes\": {\"test_attribute\":\"test_value\"}}");
        } catch (IOException e) {
            e.printStackTrace();
        }
        listener.onMessage(null, stream.buffer());
    }

}
