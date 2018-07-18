import com.worldstone.worldengine.game.item.Item;
import com.worldstone.worldengine.game.item.ItemFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ItemAttributePresetTest {

    @Test
    public void testItemAttributePresets() {
        Map<String, Object> testAttributePreset = new HashMap<>();
        testAttributePreset.put("test_attribute", "test_value");
        ItemFactory.registerPreset("test_item_preset", testAttributePreset);

        Item testItem = ItemFactory.getItem(Item.ItemType.BORING, "test_item_preset");
        Assert.assertEquals(testItem.getAttributes().get("test_attribute"), "test_value");
    }

}
