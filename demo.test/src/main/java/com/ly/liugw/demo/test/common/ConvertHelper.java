package com.ly.liugw.demo.test.common;

public class ConvertHelper {


    /**
     * byte数组转int类型的对象
     * @param bytes  bytes
     * @return int
     */
    public static int Byte2Int(byte[] bytes, int from) {
        return (bytes[from]&0xff)<<24
                | (bytes[from + 1]&0xff)<<16
                | (bytes[from + 2]&0xff)<<8
                | (bytes[from + 3]&0xff);
    }


    /**
     * int转byte数组
     * @param num num
     * @return byte[]
     */
    public static byte[] IntToByte(int num){
        byte[]bytes=new byte[4];
        bytes[0]=(byte) ((num>>24)&0xff);
        bytes[1]=(byte) ((num>>16)&0xff);
        bytes[2]=(byte) ((num>>8)&0xff);
        bytes[3]=(byte) (num&0xff);
        return bytes;
    }
}
