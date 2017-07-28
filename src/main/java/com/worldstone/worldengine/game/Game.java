package com.worldstone.worldengine.game;

import com.worldstone.worldengine.game.world.World;
import com.worldstone.worldengine.trigger.TriggerController;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

public class Game extends Thread {

    private World world;
    private Queue<PlayerAction> actionQueue;
    private List<PlayerAction> futureActions;
    private boolean shutdownFlag;

    public Game() {
        this.world = new World("main_world");
        this.actionQueue = new PriorityBlockingQueue<>();
        this.futureActions = new ArrayList<>();
        this.shutdownFlag = false;
        this.setName("game");
    }

    public void tick() {
        // Work through the action queue
        while (!this.actionQueue.isEmpty()) {
            PlayerAction action = this.actionQueue.poll();
            if (action != null) {
                action.run();
            }
        }

        // Populate action queue with actions
        this.futureActions.removeIf(Objects::isNull);
        for (PlayerAction action : this.futureActions) {
            action.tick();
            if (action.getDelayTicks() <= 0) {
                this.actionQueue.offer(action);
            }
        }
        this.futureActions.removeIf(action -> action.getDelayTicks() <= 0);

        // Trigger the game tick event
        Map<String, Object> eventAttributes = new HashMap<>();
        eventAttributes.put("time", System.currentTimeMillis());
        TriggerController.triggerEvent("game_tick", eventAttributes);
    }

    public void shutdown() {
        this.shutdownFlag = true;
    }

    public void offerPlayerAction(PlayerAction action) {
        if (action.getDelayTicks() <= 0) {
            this.actionQueue.offer(action);
        }
        else {
            this.futureActions.add(action);
        }
    }

    @Override
    public void run() {
        for (;;) {
            long tickStartTime = System.currentTimeMillis();
            this.tick();
            long tickEndTime = System.currentTimeMillis();

            // Ticks should occur in 500ms intervals
            long delta = (tickEndTime - tickStartTime);
            LoggerFactory.getLogger(this.getClass()).info("Game tick finished in " + delta + "ms (Time Utilization: " + (delta / 5.f) + "%).");
            if (delta < 500) {
                long timeToSleep = 500 - (System.currentTimeMillis() % 500);
                try {
                    Thread.sleep(timeToSleep);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            else {
                LoggerFactory.getLogger(this.getClass()).warn("Game tick took longer than 500ms (" + delta + ")!");
            }
            if (this.shutdownFlag) {
                break;
            }
        }
        LoggerFactory.getLogger(this.getClass()).info("Game thread shut down.");
    }

}
