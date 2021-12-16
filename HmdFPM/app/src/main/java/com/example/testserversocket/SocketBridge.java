package com.example.testserversocket;

import android.util.Log;

import com.dto.DTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketBridge {

    private static final int PORT = 21248;
    private ServerSocket serverSocket;
    private Socket socket;
    private Socket outSocket;

    ObjectOutputStream objectOutputStream;


    private DTO dto = null;

    public DTO getDto(){
        return dto;
    };

    public void launchAndStream(String ip){
        new Thread(()
                -> {

            try {
                String newIP = ip.substring(1);

                outSocket = new Socket();
                outSocket.connect(new InetSocketAddress(newIP, PORT));
                objectOutputStream = new ObjectOutputStream(outSocket.getOutputStream());

                objectOutputStream.write(1);
                objectOutputStream.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }

    public void launchServer() {

        new Thread(()
        -> {

            try {
                serverSocket = new ServerSocket(PORT);
                Log.d("SocketBridge", "Socket Waiting1");

                while (true) {

                    Log.d("SocketBridge", "Socket Waiting");
                    socket = serverSocket.accept();
                    Log.d("SocketBridge", "accepted");
                    new Thread(()
                    -> {
                        try {

                            Log.d("SocketBridge", "Stream Thread");
                            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

                            while(true) {

                                if (objectInputStream.available() == 0) {
                                    dto = (DTO) objectInputStream.readObject();
                                    Log.d("SocketBridge", "Get DTO");
                                }
                                else {

                                    Log.d("SocketBridge", "no input");
                                }
                            }

                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }).start();
                    Log.d("SocketBridge", "read");

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
