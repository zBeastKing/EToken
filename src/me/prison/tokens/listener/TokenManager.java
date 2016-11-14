package me.prison.tokens.listener;

import java.util.HashMap;

public class TokenManager {
	
    @SuppressWarnings("rawtypes")
	public static HashMap players = new HashMap();
    private String name;
    private String uuid;
    private int tokens;
    private int blocksBroken;
	
	@SuppressWarnings("unchecked")
	public TokenManager(String uuid, String name, int tokens) {
	        this.uuid = uuid;
	        this.name = name;
	        this.tokens = tokens;
	        players.put(uuid, this);
	    }

	public String getName() {
	        return name;
	    }
	public String getUUID() {
	        return uuid; 
	    }
	
	public int getTokens() {
	        return tokens;
	    }

	public void setTokens(int tokens) {
	        this.tokens = tokens;
	    }

	public int getBlocksBroken() {
	        return blocksBroken;
	    }

	public void setBlocksBroken(int blocks) {
	        blocksBroken = blocks;
	    }

}
