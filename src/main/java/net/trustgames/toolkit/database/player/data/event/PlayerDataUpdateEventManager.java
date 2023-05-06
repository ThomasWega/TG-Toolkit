package net.trustgames.toolkit.database.player.data.event;

import net.trustgames.toolkit.database.player.data.config.PlayerDataType;
import net.trustgames.toolkit.managers.rabbit.RabbitManager;
import net.trustgames.toolkit.managers.rabbit.config.RabbitQueues;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerDataUpdateEventManager {
    protected static final Set<PlayerDataUpdateListener> registeredListeners = Collections.synchronizedSet(new HashSet<>());

    private final RabbitManager rabbitManager;

    /**
     * Handles the registration, un-registration of listeners
     * and receiving the events from RabbitMQ and sending them to listeners
     *
     * @param rabbitManager RabbitManager instance
     */
    public PlayerDataUpdateEventManager(RabbitManager rabbitManager) {
        this.rabbitManager = rabbitManager;
    }

    /**
     * Add the listener to the list. In case the receiving of events is on,
     * it will receive events from now on
     *
     * @see PlayerDataUpdateEventManager#receiveEvents()
     */
    public static void register(PlayerDataUpdateListener listener) {
        registeredListeners.add(listener);
    }

    /**
     * Remove the listener from the list. It will no longer receive events
     */
    public static void unregister(PlayerDataUpdateListener listener) {
        registeredListeners.remove(listener);
    }

    /**
     * Receive the messages from RabbitMQ and transfer them to PlayerDataUpdateEvent,
     * which are then sent to the listeners
     *
     * @see PlayerDataUpdateEvent
     */
    public void receiveEvents() {
        rabbitManager.onDelivery(RabbitQueues.PLAYER_DATA_UPDATE.getName(), jsonObject -> {
            System.out.println("RABBITMQ RECEIVED");
            PlayerDataUpdateEvent event = new PlayerDataUpdateEvent(rabbitManager,
                    UUID.fromString(jsonObject.getString("uuid")),
                    jsonObject.getEnum(PlayerDataType.class, "data-type")
            );
            for (PlayerDataUpdateListener listener : registeredListeners){
                System.out.println("LISTENER -" + listener.toString());
                listener.onPlayerDataUpdate(event);
            }
        });
    }
}
