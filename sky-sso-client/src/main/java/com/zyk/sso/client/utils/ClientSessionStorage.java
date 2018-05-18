package com.zyk.sso.client.utils;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientSessionStorage {

    public Map<String,HttpSession> MANAGED_SESSIONS = new ConcurrentHashMap<>();    //token session

    public Map<String,String> ID_TO_SESSION_KEY_MAPPING = new ConcurrentHashMap<>();//sessionId token

    private static ClientSessionStorage storage = new ClientSessionStorage();


    private ClientSessionStorage() {}

    public static ClientSessionStorage getInstance() {
        return storage;
    }


}
