package combat;

import com.worldstone.worldengine.game.combat.CombatSession;
import com.worldstone.worldengine.game.combat.CombatTeam;
import com.worldstone.worldengine.game.npc.CombatNPC;
import org.junit.Assert;
import org.junit.Test;

public class CombatTurnTest {

    @Test
    public void testCombatTurn() {
        CombatSession session = new CombatSession();
        CombatTeam team1 = new CombatTeam();
        CombatTeam team2 = new CombatTeam();
        session.addTeam(team1);
        session.addTeam(team2);

        session.addParticipant(team1, new CombatNPC(10, 0) {
            @Override
            public void tickCombat() {

            }
        });
        session.addParticipant(team1, new CombatNPC(10, 0) {
            @Override
            public void tickCombat() {

            }
        });

        session.addParticipant(team2, new CombatNPC(10, 10) {
            @Override
            public void tickCombat() {

            }
        });
        session.addParticipant(team2, new CombatNPC(10, 10) {
            @Override
            public void tickCombat() {

            }
        });

        session.start();

        Assert.assertEquals(team2.getUuid(), session.getActiveTeam().getUuid());

        session.newTurn();
        Assert.assertEquals(team1.getUuid(), session.getActiveTeam().getUuid());

        session.newTurn();
        Assert.assertEquals(team2.getUuid(), session.getActiveTeam().getUuid());
    }

}
