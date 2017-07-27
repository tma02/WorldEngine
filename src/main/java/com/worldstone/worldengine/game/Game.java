package com.worldstone.worldengine.game;

import com.worldstone.worldengine.game.world.World;
import com.worldstone.worldengine.trigger.TriggerController;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Game extends Thread {

    private World world;
    private Queue<PlayerAction> actionQueue;
    private boolean shutdownFlag;

    public Game() {
        this.world = new World("main_world");
        this.actionQueue = new PriorityQueue<>();
        this.shutdownFlag = false;
        this.setName("game");
    }

    public void tick() {
        // Work through the action queue
        while (!actionQueue.isEmpty()) {
            PlayerAction action = actionQueue.poll();
            action.run();
        }

        // Trigger the game tick event
        Map<String, Object> eventAttributes = new HashMap<>();
        eventAttributes.put("time", System.currentTimeMillis());
        TriggerController.triggerEvent("game_tick", eventAttributes);
    }

    public void shutdown() {
        this.shutdownFlag = true;
    }

    public void enqueuePlayerAction(PlayerAction action) {
        this.actionQueue.offer(action);
    }

    @Override
    public void run() {
        for (;;) {
            long tickStartTime = System.currentTimeMillis();
            this.tick();
            long tickEndTime = System.currentTimeMillis();

            // Ticks should occur in 500ms intervals
            long delta = (tickEndTime - tickStartTime);
            if (delta < 500) {
                LoggerFactory.getLogger(this.getClass()).info("Game tick finished in " + delta + "ms (Time Utilization: " + (delta / 5.f) + "%).");
                long timeToSleep = 500 - delta;
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
