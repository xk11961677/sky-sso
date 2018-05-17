package com.zyk.sso.client.utils;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapBackedSessionMappingStorage {

    public Map<String,HttpSession> MANAGED_SESSIONS = new ConcurrentHashMap<>();

    public Map<String,String> ID_TO_SESSION_KEY_MAPPING = new ConcurrentHashMap<>();

    private static HashMapBackedSessionMappingStorage storage = new HashMapBackedSessionMappingStorage();


    private HashMapBackedSessionMappingStorage() {}

    public static HashMapBackedSessionMappingStorage getInstance() {
        return storage;
    }


}
