package main;

import Data.ClypeData;
import Data.MessageClypeData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerSideClientIO implements Runnable {
    private boolean closeConnection;
    private ClypeData dataToReceiveFromClient;
    private ClypeData dataToSendToClient;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;
    private ClypeServer server;
    private Socket clientSocket;
    private String usernames;

    public ServerSideClientIO (ClypeServer server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
        this.closeConnection = false;
        this.dataToReceiveFromClient = null;
        this.dataToSendToClient = null;
        this.inFromClient = null;
        this.outToClient = null;
        this.usernames = "\n";
    }

    /**
     * gets obj output and input streams of the client from clientSocket
     * contains while loop running till connection is open
     * In loop: receiveData();
     */
    @Override
    public void run() {
        //get obj output and input streams of the client
        try {
            this.outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
            this.inFromClient  = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(closeConnection){
            this.receiveData();
            if (this.dataToReceiveFromClient != null) {
                try {
                    this.server.broadcast(this.dataToReceiveFromClient);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    this.outToClient.writeObject(new MessageClypeData("",this.usernames,-1));
                } catch (IOException e) {
                    this.closeConnection = true;
                }
            }
        }

    }

    /**
     * receiveData() gets in a ClypeData object and reads in the data the sets the appropriate variables
     * @throws IOException
     */
    public void receiveData() {
        try {
            Object BUFFER = this.inFromClient.readObject();
            try {
                this.dataToReceiveFromClient = (ClypeData) this.inFromClient.readObject();
            } catch (Exception e) {
                if(BUFFER.toString().equals("DONE")) {
                    this.server.remove(this);
                    this.closeConnection = true;
                }
                else if(BUFFER.toString().equals("LISTUSERS")) {
                    this.usernames = "\n";
                    int count = 1;
                    for (ServerSideClientIO aServerSideClientIO : this.server.getServerSideClientIOList()) {
                        usernames+=String.format("User %d : %s\n", count, aServerSideClientIO.clientSocket.getInetAddress().getHostName());
                        count += 1;
                    }
                    this.dataToReceiveFromClient = null;
                }
            }

        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * sendData() writes data to dataToSendToClient
     * @throws IOException
     */
    public void sendData ()throws IOException {
        try {
            this.outToClient.writeObject(dataToSendToClient);
        }catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

    /**
     * sets dataToSendToClient
     * @param dataToSendToClient
     */
    public void setDataToSendToClient(ClypeData dataToSendToClient) {
        this.dataToSendToClient = dataToSendToClient;
    }
}
