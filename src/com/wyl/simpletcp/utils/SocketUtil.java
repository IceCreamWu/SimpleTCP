package com.wyl.simpletcp.utils;

import java.io.IOException;
import java.net.Socket;

public class SocketUtil {

	/**
	 * 向socket中发送包装过的数据（添加表示数据长度的4字节header）
	 * @param socket
	 * @param data
	 * 	要传送的数据字节
	 * @throws IOException
	 * 	发送失败时（比如socket意外关闭时）抛出异常
	 */
	public static void sendData(Socket socket, byte[] data) throws IOException {
		int dataSize = data.length;
		byte[] sizeBytes = ByteUtil.intToBytes(dataSize);
		socket.getOutputStream().write(sizeBytes);
		if (dataSize != 0) {
			socket.getOutputStream().write(data);
		}
	}
	
	/**
	 * 从socket中读取包装过的数据获取实际数据
	 * @param socket
	 * 	
	 * @return
	 * 	返回去除包装的有效数据字节，如果是心跳（数据长度为0）则返回null
	 * @throws IOException
	 * 	读取失败时（比如socket意外关闭时）抛出异常
	 */
	public static byte[] readData(Socket socket) throws IOException {
		// 前四个字节表示实际传送数据大小
		byte[] sizeBytes = new byte[4];
		socket.getInputStream().read(sizeBytes, 0, 4);
		// 读取实际传送数据
		int dataSize = ByteUtil.bytesToInt(sizeBytes);
		if (dataSize == 0) {
			// 心跳
			return null;
		} else {
			byte[] dataBytes = new byte[dataSize];
			socket.getInputStream().read(dataBytes, 0, dataSize);
			return dataBytes;
		}
	}
	
}
