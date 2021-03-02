/*********************************************
 * Copyright (c) 2019 LI-ECS.
 * All rights reserved.
 * Created on 2019年12月31日
 *
 * Contributors:
 *     ECS - initial implementation
 *********************************************/

package com.dcits.dcwlt.common.pay.util;

public class IntUtils {
	
	  public static byte[] toFourBytes(int value){
	    return toBytes(value, 4);
	  }

	  public static byte[] toTwoBytes(int value) {
	    return toBytes(value, 2);
	  }

	  public static byte[] toBytes(int value, int byteCount) {
	    byte[] bytes = new byte[byteCount];
	    int i = 0; 
	    for (int j = byteCount - 1; i < byteCount; j--,i++) {
	      bytes[i] = ((byte)(value >>> 8 * j & 0xFF));
	    }
	    return bytes;
	  }

	  public static int toInt(byte[] bytes) {
	    int value = 0;
	    int i = 0; 
	    for (int j = bytes.length - 1; i < bytes.length; j--,i++) {
	      value |= (bytes[i] & 0xFF) << 8 * j;
	    }
	    return value;
	  }
	 
}
