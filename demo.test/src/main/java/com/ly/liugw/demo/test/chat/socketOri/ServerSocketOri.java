package com.ly.liugw.demo.test.chat.socketOri;

import com.ly.liugw.demo.test.chat.Message;
import com.ly.liugw.demo.test.common.CharsetHelper;
import com.ly.liugw.demo.test.common.ConvertHelper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;

@Slf4j
public class ServerSocketOri {

    public static void start() throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(6666);
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();

        int len = 0;
        byte[] bytes = new byte[1024];
        byte[] dest =  new byte[1024];
        while (true) {
            while ((len = inputStream.available()) > 0) {
                System.out.println(len);
                if (len > 1023) {
                    inputStream.read(bytes, 0, 1023);
                } else {
                    inputStream.read(bytes, 0, len);
                }
                int bytelen = bytes.length > len ? len : bytes.length;
                for (int i=0; i < bytelen; ) {
                    int index = ConvertHelper.Byte2Int(bytes, i);
                    int type = ConvertHelper.Byte2Int(bytes, (i=i+4));
                    int length = ConvertHelper.Byte2Int(bytes, (i=i+4));
                    System.arraycopy(bytes, i=i+4, dest, 0, length);
                    String content = new String(dest, CharsetHelper.UTF8);
//                inputStream.read(bytes, 0, len);
                    log.info("msg:{}", new Message(index, type, content));
                    i=i+length;
                }
            }
            System.out.println("server reading ......");
            Thread.sleep(2000);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketOri.start();
    }

}
