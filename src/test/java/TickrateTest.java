import com.worldstone.worldengine.game.Game;
import com.worldstone.worldengine.game.PlayerAction;
import com.worldstone.worldengine.game.player.Player;
import com.worldstone.worldengine.trigger.Trigger;
import com.worldstone.worldengine.trigger.TriggerController;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class TickrateTest {

    private int ticksRan;

    @Test
    public void testTickrate() {
        Game game = new Game();
        this.ticksRan = 0;
        final TickrateTest this_ = this;
        TriggerController.registerTrigger(new Trigger("sentinel_trigger", "game_tick") {
            @Override
            public void resolve(Map<String, Object> attributes) {
                this_.ticksRan++;
            }
        });
        Player player = new Player();
        for (int i = 0; i < 1000; i++) {
            TriggerController.registerTrigger(new Trigger("test_trigger" + i, "game_tick") {
                @Override
                public void resolve(Map<String, Object> attributes) {
                    game.offerPlayerAction(new PlayerAction(player, 1) {
                        @Override
                        public void run() {
                            Assert.assertEquals(this.getPlayer().getArea(), "nowhere");
                        }
                    });
                }
            });
        }
        game.start();
        for (;;) {
            if (this.ticksRan >= 10) {
                game.shutdown();
                break;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
