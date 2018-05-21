package com.zyk.sso.client.utils;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientSessionStorage {

    public Map<String, HttpSession> MANAGED_SESSIONS = new ConcurrentHashMap<>();    //token session

    public Map<String, String> ID_TO_SESSION_KEY_MAPPING = new ConcurrentHashMap<>();//sessionId token

    private static ClientSessionStorage storage = new ClientSessionStorage();


    private ClientSessionStorage() {
    }

    public static ClientSessionStorage getInstance() {
        return storage;
    }

    public void setManagedSessions(String st, HttpSession session) {
        MANAGED_SESSIONS.put(st, session);
    }

    public HttpSession getManagedSessions(String st) {
        return MANAGED_SESSIONS.get(st);
    }

    public void removeManagedSessions(String st) {
        MANAGED_SESSIONS.remove(st);
    }

    public void setIdToSessionKeyMapping(String sessionId, String st) {
        ID_TO_SESSION_KEY_MAPPING.put(sessionId, st);
    }

    public String getIdToSessionKeyMapping(String sessionId) {
        return ID_TO_SESSION_KEY_MAPPING.get(sessionId);
    }

    public void removeIdToSessionKeyMapping(String sessionId) {
        ID_TO_SESSION_KEY_MAPPING.remove(sessionId);
    }

}
