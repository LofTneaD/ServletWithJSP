package controller;

import dbService.data.UserProfile;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class SessionController {
    private static SessionController Instance = new SessionController();
    private final Map<String, UserProfile> sessionIdToProfile;

    private SessionController() {
        sessionIdToProfile = new HashMap<>();
    }

    static SessionController getInstance() {
        return Instance;
    }

    UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}