package com.kelsey.NumberTreeWS.websocket;



import com.kelsey.NumberTreeWS.models.Factory;
import com.kelsey.NumberTreeWS.models.RootNode;
import com.kelsey.NumberTreeWS.services.FactoryRepository;
import com.kelsey.NumberTreeWS.services.RootNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScope
@ServerEndpoint("/actions")
public class WebSocketServer {
    @Autowired
    RootNodeRepository rootNodeRepo;

    @Autowired
    FactoryRepository factories;

    @PostConstruct
    public void init() {
        if (rootNodeRepo.count() == 0) {
            RootNode rootNode = new RootNode(1);
            rootNodeRepo.save(rootNode);
            //ToDo: remove once root node can be viewed in UI
            Factory factory = new Factory(rootNode, "Tommy", 15, 55);
            List<Factory> rootFactories = new ArrayList<>();
            rootFactories.add(factory);
            rootNode.setFactories(rootFactories);
            factories.save(factory);

        }
    }

    @Inject
    private SessionHandler sessionHandler;

    @OnOpen
    public void open(Session session) {
        sessionHandler.addSession(session);
    }

    @OnClose
    public void close(Session session) {
        sessionHandler.removeSession(session);
    }

    @OnError
    public void onError(Throwable error) {
        Logger.getLogger(WebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();

            if ("add".equals(jsonMessage.getString("action"))) {
                Factory factory = new Factory();
                factory.setName(jsonMessage.getString("name"));
                factory.setRangeLow(jsonMessage.getInt("rangeLow"));
                factory.setRangeHigh(jsonMessage.getInt("rangeHigh"));
                sessionHandler.addFactory(factory);
            }

            if ("remove".equals(jsonMessage.getString("action"))) {
                int id = (int) jsonMessage.getInt("id");
                sessionHandler.removeFactory(id);
            }
        }
    }
}
