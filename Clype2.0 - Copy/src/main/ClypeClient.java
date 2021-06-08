package main;

import Data.ClypeData;
import Data.FileClypeData;
import Data.MessageClypeData;
import application.Main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


/**
 * Class representing client user
 * creates ClypeClient object
 */

//need 'inFromServer' as ObjectInputStream and 'outToServer' as ObjectOutputStream (set null)(
public class ClypeClient {

    private String userName;
    private String hostName;
    private int port;
    private boolean closeConnection;
    private static ClypeData dataToSendToServer;
    private ClypeData dataToReceiveFromServer;
    private Scanner inFromStd;
    private ObjectInputStream inFromServer;
    private static ObjectOutputStream outToServer;


    /**
     * Constructor for userName, hostName, and port
     *
     * @param userName identifies client user
     * @param hostName represents name of computer representing server
     * @param port     integer representing port number on server connected to
     */

    public ClypeClient(String userName, String hostName, int port) throws IllegalArgumentException {
        if (userName.equals(null) || hostName.equals(null) || port < 1024)
            throw new IllegalArgumentException();
        this.userName = userName;
        this.hostName = hostName;
        this.port = port;
        this.dataToSendToServer = null;
        this.dataToReceiveFromServer = null;
        this.closeConnection = true;
        this.inFromServer = null;
        this.outToServer = null;
    }

    /**
     * Constructor for userName and hostName
     * sets port to 7000
     *
     * @param userName vaiable
     * @param hostName variable
     */
    public ClypeClient(String userName, String hostName) {
        this(userName, hostName, 7000);

    }

    /**
     * constructor for userName
     * sets hostName to localhost
     *
     * @param userName variable
     */
    public ClypeClient(String userName) {
        this(userName, "localhost");

    }

    /**
     * Default Constructor
     * sets userName to anonymous
     */
    public ClypeClient() {
        this("anon");

    }

    /**
     * Reads data from the client and prints the data out
     * starts a new thread, runs the listener by a
     */
    public void start(){
        Socket skt = null;
        try {
            this.inFromStd = new Scanner(System.in);
            skt = new Socket(this.hostName, this.port);
            this.inFromServer  = new ObjectInputStream(skt.getInputStream());
            this.outToServer = new ObjectOutputStream(skt.getOutputStream());
            System.out.println("Connected");

            ClientSideServerListener clientSideServerListener = new ClientSideServerListener(this);
            Thread clientSideServerThread =  new Thread(clientSideServerListener);
            clientSideServerThread.start();


            System.out.println("\nType in message or command: ");
            while (!this.closeConnection) {
                //readClientData();
                readGuiMessage();
                sendData();
                if (this.dataToSendToServer == null)
                    this.closeConnection = true; //break loop
            }
        } catch(UnknownHostException uhe)
        {
            System.err.println(uhe.getMessage());
        }
        catch(IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
        try
        {
            this.dataToSendToServer = null;
            sendData(); //close server
            this.inFromServer.close();
            this.outToServer.close();
            skt.close();
        }
        catch(IOException | NullPointerException ioe)
        {
            System.err.println(ioe.getMessage());
        }
    }


    /*
    public void start() {

        inFromStd = new Scanner(System.in);

        //start new thread that runs the listener by creating a runnable object
        //and starting a new thread with the runnable object
        Thread listener = new Thread(new Runnable() {
            @Override
            public void run() {
                //not sure if this is correct
            }
        });
            listener.start();

        while (this.closeConnection) {
            try {
                Socket skt = new Socket(this.hostName, this.port);
                this.inFromServer = new ObjectInputStream(skt.getInputStream());
                this.outToServer = new ObjectOutputStream(skt.getOutputStream());

                readClientData();
                sendData();

                skt.close();
                inFromServer.close();
                outToServer.close();
            } catch (UnknownHostException uhe) {
                System.err.println("Unknown Host");
            } catch (NoRouteToHostException nrhe) {
                System.err.println("Server unreachable");
            } catch (ConnectException ce) {
                System.err.println("Connection Refused.");
            } catch (IOException ioe) {
                System.err.println(ioe.getMessage());
            }
        }


    }
*/


    /**
     * Replaces readClientData
     * creates a MessageClypeData object
     * with the gui message text
     */
    public void readGuiMessage() {
        while (this.closeConnection) {
            //String message = this.inFromStd.next();
            MessageClypeData messageData = new MessageClypeData("JOSE",Main.getMessage(),3);
            dataToSendToServer = messageData;
         //   if (in.equals("DONE")) {
         //       this.closeConnection = false;
         //       this.dataToSendToServer = new FileClypeData();
         //   } else if (in.equals("SENDFILE")) {
         //       this.dataToSendToServer = new FileClypeData(this.userName, this.inFromStd.next(), 3);
         //       ((FileClypeData) this.dataToSendToServer).readFileContents();
         //   } else if (in.equals("LISTUSERS")) {

         //   }
            break;
        }
    }

    /**
     * Writes out the ClypeData object in 'dataToSendToServer'
     * to the ObjectOutputStream 'outToServer'
     * catches all exceptions and prints to standard error
     */
    public static void sendData() throws IOException {
        try {
            outToServer.writeObject(dataToSendToServer);

        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

    /**
     * receives data from the server, does not return anything
     * just a declaration for now
     */
    public void receiveData() {
        try {
            this.dataToReceiveFromServer = (ClypeData) this.inFromServer.readObject();
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * should print text to the receiving text field
     *
     */
    public void printData()
    {
        //should set append to textfield in the Gui class.
       // System.out.println(this.dataToSendToServer.toString());

    }


    /**
     * @return userName
     */
    public String getUserName() {
        return userName;

    }

    /**
     * @return hostName
     */
    public String getHostName() {
        return hostName;

    }

    /**
     * @return port number
     */
    public int getPort() {
        return port;

    }
    public boolean getCloseConnection() {
        return closeConnection;
    }

    /**
     * returns unique hashCode for object
     */
    @Override
    public int hashCode() {
        int result = 13;
        result = 67 * result + userName.hashCode();
        result = 67 * result + hostName.hashCode();
        result = 67 * result + port;
        result = 67 * result + dataToSendToServer.hashCode();
        result = 67 * result + dataToReceiveFromServer.hashCode();
        return result;

    }

    /**
     * Compares two ClypeClient objects
     *
     * @param other variable
     * @return boolean value
     */
    @Override
    public boolean equals(Object other) {
        ClypeClient otherTest = (ClypeClient) other;
        return this.userName == otherTest.userName
                && this.hostName == otherTest.hostName
                && this.port == otherTest.port
                && this.dataToSendToServer == otherTest.dataToSendToServer
                && this.dataToReceiveFromServer == otherTest.dataToReceiveFromServer;

    }

    /**
     * @return String giving full description of the class
     */
    @Override
    public String toString() {
        return "User " + userName + "\nHost " + hostName + "\nPort " + port
                + "\nSend " + dataToSendToServer + "\nMessage Received " + dataToReceiveFromServer;

    }

    public void main(String[] args) throws IOException {
        ClypeClient testClient = null;
        String input = null;
        try {
            input = args[0];
            String[] userArgs = input.split("@");
            if (userArgs.length == 1) {
                testClient = new ClypeClient(userArgs[0]);
            } else {
                String[] hostArgs = userArgs[1].split(":");
                if (hostArgs.length == 1)
                    testClient = new ClypeClient(userArgs[0], hostArgs[0]);
                else
                    testClient = new ClypeClient(userArgs[0], hostArgs[0], Integer.parseInt(hostArgs[1]));
            }
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            testClient = new ClypeClient();
        }

        testClient.start();
    }
}
