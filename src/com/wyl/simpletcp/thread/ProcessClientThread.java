package com.wyl.simpletcp.thread;

import java.io.IOException;
import java.net.Socket;
import com.wyl.simpletcp.utils.ByteUtil;

public class ProcessClientThread extends Thread{

	private Socket clientSocket;
	private boolean isConnect;

	public ProcessClientThread(Socket socket) {
		clientSocket = socket;
		isConnect = true;
	}
	
	@Override
	public void run() {
		while (isConnect) {
			try {
				byte[] sizeBytes = new byte[4];
				clientSocket.getInputStream().read(sizeBytes, 0, 4);
				int dataSize = ByteUtil.bytesToInt(sizeBytes);
				byte[] dataBytes = new byte[dataSize];
				clientSocket.getInputStream().read(dataBytes, 0, dataSize);
				System.out.println(new String(dataBytes));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				isConnect = false;
				e.printStackTrace();
			}
		}
	}
	
}
