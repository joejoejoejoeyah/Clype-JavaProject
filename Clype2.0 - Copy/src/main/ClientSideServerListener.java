package main;

/**
 *This class takes over responsibility of receiving data
 *from the server and printing it in parallel to the ClypeClient class
 *and the ClypeClient Class only reads data from the user and sends it
 *
 * Implements "Runnable" creates a runnable object in clypeclient.java and a
 * thread wrapped around the Runnable object
 */
public class ClientSideServerListener implements Runnable {
    //STRUCTURE AS FOLLOWS:
    //instance variable "client" that calls this class to make a threaded
        //runnable object

    //ClientSideServerListener( client ): constructor that takes a ClypeClient obj as parameter
    //run(): method from overridden from Runnable interface
        //(a)
        //(b)
    private ClypeClient client;
    public ClientSideServerListener(ClypeClient client ){
        this.client = client;
    }
    public void run() {

        while(!this.client.getCloseConnection()){//while closeConnection
               //need to find out a way to test connection status
            this.client.receiveData();
            this.client.printData();
        }
    }
}
