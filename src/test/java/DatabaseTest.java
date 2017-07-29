import com.google.gson.Gson;
import com.worldstone.worldengine.Config;
import com.worldstone.worldengine.database.Database;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.List;

public class DatabaseTest {

    @Test
    public void testDatabase() {
        Config config = null;
        try {
            config = new Gson().fromJson(new FileReader("config.json"), Config.class);
        } catch (FileNotFoundException e) {
            LoggerFactory.getLogger(this.getClass()).error("Could not load config file: ");
            e.printStackTrace();
            System.exit(1);
        }
        Database db = new Database((String) config.getDatabase().get("host"),
                (int) (double) config.getDatabase().get("port"),
                (String) config.getDatabase().get("database"),
                (String) config.getDatabase().get("username"),
                (String) config.getDatabase().get("password"));
        db.testConnection();
        try {
            List<String> list = db.loadCharacterList("test@test.test");
            LoggerFactory.getLogger(this.getClass()).info("Found characters for test@test.test:");
            for (String displayName : list) {
                LoggerFactory.getLogger(this.getClass()).info(displayName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

}
