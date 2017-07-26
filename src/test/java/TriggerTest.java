import com.worldstone.worldengine.trigger.Trigger;
import com.worldstone.worldengine.trigger.TriggerController;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TriggerTest {

    @Test
    public void testTriggers() {
        Trigger trigger = new Trigger("test_event", "test_event");
        TriggerController.registerTrigger(trigger);

        Map<String, Object> triggerAttributes = new HashMap<>();
        triggerAttributes.put("test_attribute", "test_value");
        TriggerController.triggerEvent("test_event", triggerAttributes);
    }

}
