package com.wyl.simpletcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.wyl.simpletcp.config.NetworkConfig;
import com.wyl.simpletcp.manager.ClientManager;
import com.wyl.simpletcp.thread.ProcessClientThread;

public class TCPServer {

	public static void main(String[] args) {
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(NetworkConfig.SERVER_PORT);
			while (true) {
				Socket clientSocket = serverSocket.accept();
				ClientManager.getInstance().addClient(clientSocket);
				new ProcessClientThread(clientSocket).start();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}
