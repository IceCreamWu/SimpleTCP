package com.wyl.simpletcp.utils;

public class ByteUtil {

	/**
	  * 将int类型的数据转换为byte数组
	  * 原理：将int数据中的四个byte取出，分别存储
	  * @param n int数据
	  * @return 生成的byte数组
	  */
	public static byte[] intToBytes(int n){
	    byte[] b = new byte[4];
	    b[3] = (byte) (n >> 24);    
	    b[2] = (byte) (n >> 16);    
	    b[1] = (byte) (n >> 8);    
	    b[0] = (byte) (n >> 0);
	    return b;
	}
	
	/**
	  * 将byte数组转换为int数据
	  * @param b 字节数组
	  * @return 生成的int数据
	  */
	  public static int bytesToInt(byte[] b){
		  int n = (int) ((((b[3] & 0xff) << 24) 
				  | ((b[2] & 0xff) << 16) 
				  | ((b[1] & 0xff) << 8) 
				  | ((b[0] & 0xff) << 0)));
		  return n;
	  }
	
}
