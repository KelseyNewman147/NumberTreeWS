package com.kelsey.NumberTreeWS.websocket;

import com.kelsey.NumberTreeWS.models.Factory;
import com.kelsey.NumberTreeWS.models.RootNode;
import com.kelsey.NumberTreeWS.services.ChildNodeRepository;
import com.kelsey.NumberTreeWS.services.FactoryRepository;
import com.kelsey.NumberTreeWS.services.RootNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@ApplicationScope
public class SessionHandler {
    private final Set<Session> sessions = new HashSet<>();

    @Autowired
    RootNodeRepository rootNodeRepo;

    @Autowired
    FactoryRepository factories;

    @Autowired
    ChildNodeRepository childNodes;

    public void addSession(Session session) {
        sessions.add(session);
        List<Factory> factoryList = rootNodeRepo.findById(1).getFactories();
        for (Factory factory : factoryList) {
            JsonObject addMessage = createAddMessage(factory);
            sendToSession(session, addMessage);
        }
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    private JsonObject createAddMessage(Factory factory) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("id", factory.getId())
                .add("name", factory.getName())
                .add("rangeLow", factory.getRangeLow())
                .add("rangeLow", factory.getRangeHigh())
                .build();
        return addMessage;

    }

    public RootNode getRootNode() {
        return rootNodeRepo.findById(1);
    }

    public void addFactory(Factory factory) {
        RootNode rootNode = rootNodeRepo.findById(1);
        factory.setRootNode(rootNode);
        if (factory.getRangeHigh() > factory.getRangeLow()) {
            factories.save(factory);
            JsonObject addMessage = createAddMessage(factory);
            sendToAllConnectedSessions(addMessage);
        } else {
            throw new java.lang.RuntimeException("Upper bound should be higher than the lower bound.");
        }
    }

    public void removeFactory(int id) {
        Factory factory = factories.findFactoryById(id);
        if (factory != null) {
            factory.getChildNodes().forEach(c -> childNodes.delete(c));
            factories.delete(factory);
            JsonProvider provider = JsonProvider.provider();
            JsonObject removeMessage = provider.createObjectBuilder()
                    .add("action", "remove")
                    .add("id", id)
                    .build();
            sendToAllConnectedSessions(removeMessage);
        }
    }

    private void sendToAllConnectedSessions(JsonObject message) {
        for (Session session : sessions) {
            sendToSession(session, message);
        }
    }

    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
