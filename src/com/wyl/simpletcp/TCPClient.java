package com.wyl.simpletcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.wyl.simpletcp.config.NetworkConfig;
import com.wyl.simpletcp.utils.SocketUtil;

public class TCPClient {
	
	public final static int STATE_DISCONNECTED = 0;
	public final static int STATE_CONNECTED = 1;
	
	private Socket clientSocket = new Socket();
	private int state = TCPClient.STATE_DISCONNECTED;
	
	public void connect() {
		if (state == TCPClient.STATE_DISCONNECTED) {
			new Thread() {
				@Override
				public void run() {
					try {
						SocketAddress socketAddress = new InetSocketAddress(NetworkConfig.SERVER_HOST, NetworkConfig.SERVER_PORT);
						clientSocket.connect(socketAddress);
						state = TCPClient.STATE_CONNECTED;
						processLoop();
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
		}
	}
	
	private void processLoop() {
		while (state == TCPClient.STATE_CONNECTED) {
			try {
				byte[] data = SocketUtil.readData(clientSocket);
				process(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				state = TCPClient.STATE_DISCONNECTED;
				e.printStackTrace();
			}
		}
	}

	private void process(byte[] data) {
		System.out.println("Server: " + new String(data));
	}

	public void sendData(byte[] data) {
		try {
			SocketUtil.sendData(clientSocket, data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			state = TCPClient.STATE_DISCONNECTED;
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		TCPClient client = new TCPClient();
		client.connect();
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String dataString = scanner.nextLine();
			client.sendData(dataString.getBytes());
		}
	}
	
}
