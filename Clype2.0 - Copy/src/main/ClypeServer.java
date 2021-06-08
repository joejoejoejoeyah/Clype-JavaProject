package main;

import Data.ClypeData;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**Creates a ClypeServer Object
 * @author Joseph Tudor
 *
 */
public class ClypeServer {

    private int port;
    private boolean closeConnection; //represents whether server remains open or not
                                        // to handle any client
    private  ArrayList<main.ServerSideClientIO> ServerSideClientIOList;


    /**
     * Constructor to set port number
     *
     * @param port stat
     */
    public ClypeServer( int port ) throws IllegalArgumentException {
        if (port < 1024)
            throw new IllegalArgumentException("port < 1024");
        this.port = port;
        this.closeConnection = false;
        //initialize ArrayList in all relevant constructors to empty array list(NOT NULL)
        this.ServerSideClientIOList = new ArrayList<main.ServerSideClientIO>();
    }

    /**
     * Default Constructor
     * sets port number to 7000
     * calls another constructor
     */
    public ClypeServer() {
        this(7000);

    }
    public ArrayList<ServerSideClientIO> getServerSideClientIOList() {
        return ServerSideClientIOList;
    }

    //creates threads wrapped around ServerSideClientIO objects
    //that each handle receiving and sending data from the cooresponding
    //client. Adds each ServerSideClientIO object to a LIST to use later
    public void start() throws IOException {
        System.out.println("Server Online");
        ServerSocket skt = new ServerSocket(this.port);

        while (!this.closeConnection) {
            skt = new ServerSocket(this.port);
            Socket clientSocket = skt.accept();
            //O- create new thread object by wrapping the Thread class
            //constructor around ServerSideClientIO object, and start
            //the Thread object
            ServerSideClientIO serverSideClientIOMemeber = new ServerSideClientIO(this, clientSocket);//O- accepts clients
            //O- create new ServerSideClientIO object
            //add to ArrayList
            ServerSideClientIOList.add(serverSideClientIOMemeber);
            new Thread(serverSideClientIOMemeber).start();
        }

        skt.close();
    }

    /**
     * uses all ServerSideClientIO object thread to send data to cooresponding clients
     *
     */
    public synchronized void broadcast(ClypeData dataToBroadcastToClients) throws IOException {
        //ClypeData obj iterates through list and calls objects setDataToSendToClient() method
        for (ServerSideClientIO aServerSideClientIOList : this.ServerSideClientIOList){
          aServerSideClientIOList.setDataToSendToClient(dataToBroadcastToClients);
            //then calls the objects sendData() method to force the object to send the data to the
            aServerSideClientIOList.sendData();
            //cooresponding client
        }
    }

    /**
     * removes a particular ServerSideClientIO object from the list once its
     * cooresponding client is closed.
     *
     */
    public synchronized void remove(main.ServerSideClientIO ClientToRemove){
        //removes Object from list 'serverSideClientIOList'
        ServerSideClientIOList.remove(ClientToRemove);
    }
    /**
     *
     * @return port number
     */
    public int getPort() {
        return this.port;
    }

    /**
     * produces unique hashcode for object
     * @return hashcode integer
     */
    @Override
    public int hashCode() {
        int result = 13;
        result = 67*result + port;
        int connection;
        if (closeConnection)
            connection = 1;
        else
            connection = 0;
        result = 67*result + connection;
        result = 67*result + port;
//        result = 67*result + dataToSendToClient.hashCode();
//        result = 67*result + dataToReceiveFromClient.hashCode();
        return result;
    }

    /**
     * Compares ClypeServer objects
     * @param Other start
     * @return boolean value
     */
    @Override
    public boolean equals(Object Other) {
        ClypeServer other = (ClypeServer) Other;
        return this.closeConnection == other.closeConnection
                && this.port == other.port;
 //               && this.dataToReceiveFromClient == other.dataToReceiveFromClient
 //               && this.dataToSendToClient == other.dataToSendToClient;
    }

    /**
     *
     * @return String description of the class
     */
    @Override
    public String toString(){
        return "Connection: " + this.closeConnection + "\n"
                + "Port: " + this.port + "\n";
//                + "Received Data: " + this.dataToReceiveFromClient
//                + "Send Data: " + this.dataToSendToClient;
    }
    public static void main(String[] args) throws IOException {
        ClypeServer testServer = null;
        try{
            testServer = new ClypeServer(Integer.parseInt(args[0]));
        }catch (ArrayIndexOutOfBoundsException aioob) {
            testServer = new ClypeServer();
        }
        testServer.start();
    }

}
