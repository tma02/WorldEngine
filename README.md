# WorldEngine
Highly configurable Socket.IO MMO game engine.

## Scripting
Ideally, all game logic is written in JavaScript scripts to be interpreted at launch. Scripts can be used to make and register: item attribute presets, skills, packet actions, etc. Scripts can also hook on to event triggers to handle game events.

### Game Ticks
The game thread handles timing of game ticks and is in charge of triggering the ```game_tick``` event. Ticks are timed to occur every 500±1ms. At the start of each tick, the game thread will attempt to execute and clear the action queue, then trigger the ```game_tick``` event. Game logic should ideally populate the action queue instead of executing the action directly. 

**```game_tick``` Trigger Hook Example**
```JavaScript
// Writing your own Trigger
var Trigger = Java.type('com.worldstone.worldengine.trigger.Trigger');
var MyGameTickTrigger = Java.extend(Trigger, {
    resolve: function(attributes) {
        print(attributes);
    }
});

// Register MyTrigger with the TriggerController
var TriggerController = Java.type('com.worldstone.worldengine.trigger.TriggerController');
TriggerController.registerTrigger(new MyGameTickTrigger('my_game_tick_trigger', 'game_tick'));
```

### Items
A unique object with attributes that represents an object that can interact with inventories, character equipment, and other ItemContainers.

**Item Script Usage Example**
```JavaScript
// Registering an attribute preset
var HashMap = Java.type('java.util.HashMap');
var ItemFactory = Java.type('com.worldstone.worldengine.game.item.ItemFactory');
var presetMap = new HashMap();
presetMap.put('name', 'my_item');
presetMap.put('display_name', 'My Item');
ItemFactory.registerPreset('my_item_preset', presetMap);

// Getting an Item instance with an attribute preset
var ItemType = Java.type('com.worldstone.worldengine.game.item.ItemFactory.ItemType');
var myItemInstance = ItemFactory.getItem(ItemType.BORING, 'my_item_preset');
```

### Triggers
Triggers are used to pass events between WorldEngine and scripts. There are some events that are triggered by WorldEngine itself, but events can also be triggered by scripts.

**Trigger Script Usage Example**
```JavaScript
// Writing your own Trigger
var Trigger = Java.type('com.worldstone.worldengine.trigger.Trigger');
var MyTrigger = Java.extend(Trigger, {
    resolve: function(attributes) {
        print(attributes);
    }
});

// Register MyTrigger with the TriggerController
var TriggerController = Java.type('com.worldstone.worldengine.trigger.TriggerController');
TriggerController.registerTrigger(new MyTrigger('my_trigger', 'some_event'));

// Trigger an event
var HashMap = Java.type('java.util.HashMap');
TriggerController.triggerEvent('some_event', new HashMap());
```
