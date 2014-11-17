package com.wyl.simpletcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import com.wyl.simpletcp.config.NetworkConfig;
import com.wyl.simpletcp.utils.ByteUtil;

public class TCPClient {
	
	public final static int STATE_DISCONNECTED = 0;
	public final static int STATE_CONNECTED = 1;
	
	private Socket clientSocket = new Socket();
	private int state = TCPClient.STATE_DISCONNECTED;
	
	public void connect() {
		try {
			SocketAddress socketAddress = new InetSocketAddress(NetworkConfig.SERVER_HOST, NetworkConfig.SERVER_PORT);
			clientSocket.connect(socketAddress);
			state = TCPClient.STATE_CONNECTED;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getState() {
		return state;
	}
	
	public void send(String data) {
		try {
			byte[] sizeBytes = ByteUtil.intToBytes(data.length());
			clientSocket.getOutputStream().write(sizeBytes);
			clientSocket.getOutputStream().write(data.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			state = TCPClient.STATE_DISCONNECTED;
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TCPClient client = new TCPClient();
		client.connect();
		if (client.getState() == TCPClient.STATE_CONNECTED) {
			client.send("Hello World!");
			client.send("Hello World!\n");
			client.send("Hello World!");
		}
	}
	
}
