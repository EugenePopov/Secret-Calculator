package com.example.eugene.secretcalculator.MailClient;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class Client extends AsyncTask<String, Void, String>{

    private static final String SERVER_IP = "192.168.55.40";
    private static final int SERVER_PORT = 6789;
    private static final String CONNECTION_ERROR = "Error! Server is not available!";

    @Override
    protected String doInBackground(String... params) {

        try {
            return dialogServer(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return CONNECTION_ERROR;
        }

    }

    private static boolean isHostAvailable() {
        try {
            Socket socket = new Socket();
            SocketAddress socketAddress = new InetSocketAddress(SERVER_IP, SERVER_PORT);
            socket.connect(socketAddress, 20000);
            socket.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private String dialogServer(String dataToServer) throws IOException {

        String resultFromServer;

        if(isHostAvailable()) {
            Socket clientSocket = new Socket(SERVER_IP, SERVER_PORT);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outToServer.writeBytes(dataToServer + '\n');
            resultFromServer = inFromServer.readLine();
            clientSocket.close();
            return resultFromServer;
        }
        else return CONNECTION_ERROR;
    }


}