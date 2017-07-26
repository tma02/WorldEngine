import com.worldstone.worldengine.net.listener.LoginListener;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoginListenerTest {

    @Test
    public void testLoginListener() {
        LoggerFactory.getLogger(this.getClass()).info("Simulating packet...");
        LoginListener listener = new LoginListener();
        ByteBufOutputStream stream = new ByteBufOutputStream(Unpooled.buffer(128));
        try {
            stream.writeUTF("{\"name\": \"test_packet\", \"attributes\": {\"hello\":\"world\"}}");
        } catch (IOException e) {
            e.printStackTrace();
        }
        listener.onMessage(null, stream.buffer());
    }

}
