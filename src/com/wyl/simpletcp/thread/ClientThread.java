package com.wyl.simpletcp.thread;

import java.io.IOException;
import java.net.Socket;

import com.wyl.simpletcp.manager.ClientManager;
import com.wyl.simpletcp.utils.SocketUtil;

public class ClientThread extends Thread{

	private Socket clientSocket;
	private boolean isConnect;

	public ClientThread(Socket socket) {
		clientSocket = socket;
		isConnect = true;
	}
	
	@Override
	public void run() {
		while (isConnect) {
			byte[] data = null;
			try {
				data = SocketUtil.readData(clientSocket);
				if (data != null) {
					process(data);
				}
			} catch (IOException e) {
				// 连接意外关闭
				e.printStackTrace();
				isConnect = false;
				System.out.println("Out!");
				ClientManager.getInstance().removeClient(clientSocket);
			}
		}
	}

	private void process(byte[] data) {
		String dataString = new String(data);
		System.out.println(dataString);
		try {
			SocketUtil.sendData(clientSocket, data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Out!");
		}
	}
	
}
