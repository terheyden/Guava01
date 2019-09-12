package com.terheyden;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.slf4j.LoggerFactory.getLogger;

@ParametersAreNonnullByDefault
public class EventBusTest
{
    private static final Logger LOG = getLogger(EventBusTest.class);

    @Test
    public void test()
    {
        EventBus eventBus = new EventBus();

        ChattyUser user1 = new ChattyUser(eventBus, "Cora");
        ChattyUser user2 = new ChattyUser(eventBus, "Mika");
        DeadListener dead = new DeadListener();

        eventBus.register(user1);
        eventBus.register(user2);
        eventBus.register(dead);

        user1.say("Hello!");
        eventBus.post("Rando string.");
    }

    static class ChatEvent
    {
        private final String from;
        private final String msg;

        ChatEvent(String from, String msg)
        {
            this.from = from;
            this.msg = msg;
        }

        public String from()
        {
            return from;
        }

        public String msg()
        {
            return msg;
        }
    }

    static class ChattyUser
    {
        private final EventBus eventBus;
        private final String name;

        ChattyUser(EventBus eventBus, String name)
        {
            this.eventBus = eventBus;
            this.name = name;
        }

        public void say(String msg)
        {
            LOG.info("{} is saying: {}", name, msg);
                eventBus.post(new ChatEvent(name, msg));
        }

        @Subscribe
        public void handleChat(ChatEvent chat)
        {
            if (name.equalsIgnoreCase(chat.from()))
            {
                return;
            }

            LOG.info("{} heard: '{}: {}'", name, chat.from(), chat.msg());
        }
    }

    static class DeadListener
    {
        @Subscribe
        public void handleDeadEvent(DeadEvent deadEvent)
        {
            LOG.warn("Dead event (from {}): {}", deadEvent.getSource(), deadEvent.getEvent());
        }
    }
}
