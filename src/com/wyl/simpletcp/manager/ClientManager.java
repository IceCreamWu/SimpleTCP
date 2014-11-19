package com.wyl.simpletcp.manager;

import java.net.Socket;
import java.util.HashSet;

public class ClientManager {

	private static volatile ClientManager clientManagerInstance;
	private HashSet<Socket> clientSet;
	
	private ClientManager() {
		clientSet = new HashSet<Socket>();
	}
	
	public static ClientManager getInstance() {
		if (clientManagerInstance == null) {
			synchronized (ClientManager.class) {
				if (clientManagerInstance == null) {
					clientManagerInstance = new ClientManager();
				}
			}
		}
		return clientManagerInstance;
	}
	
	public synchronized void addClient(Socket client) {
		if (!clientSet.contains(client)) {
			clientSet.add(client);
		}
	}
	
	public synchronized void removeClient(Socket client) {
		if (clientSet.contains(client)) {
			clientSet.remove(client);
		}
	}
	
	public synchronized int getClientCount() {
		return clientSet.size();
	}
	
}
