package controller;

import dbService.dataSets.UsersDataSet;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class SessionController {
    private static SessionController Instance;
    private Map<String, UsersDataSet> sessionIdToProfile;
    private static final String FILE_PATH = "sessions.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private SessionController() {
        sessionIdToProfile = new HashMap<>();
        loadSessions();
    }

    static SessionController getInstance() {
        if (Instance == null) {
            Instance = new SessionController();
        }
        return Instance;
    }

    UsersDataSet getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    void addSession(String sessionId, UsersDataSet userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
        saveSessions();
    }

    void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
        saveSessions();
    }

    private void saveSessions() {
        try {
            objectMapper.writeValue(new File(FILE_PATH), sessionIdToProfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSessions() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try {
                sessionIdToProfile = objectMapper.readValue(file, new TypeReference<Map<String, UsersDataSet>>() {});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}