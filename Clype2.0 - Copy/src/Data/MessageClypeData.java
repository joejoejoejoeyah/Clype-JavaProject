package Data;

/**
 * Class to create MessageClypeData object
 * @author Joseph Tudor
 */
public class MessageClypeData extends ClypeData {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String message;

    /**
     * Constructor to set up username, message and type
     * @param userName identifies user
     * @param message String representing instant message
     * @param type represents kind of data exchange
     */
    public MessageClypeData( String userName, String message, int type ) {

        super(userName, type);
        this.message = message;

    }

    public MessageClypeData( String userName, String message, String key, int type ) {
        //should immediately encrypt the message using the key
        //do not save the key as an INSTANCE VARIABLE
        super(userName, type);
        this.message = encrypt(message, key);
    }
    /**
     * Default constructor
     * calls other constructor
     *
     */
    public MessageClypeData() {
        super();
        this.message = "";
    }

    /**
     *returns  instant message
     * @return
     */
    @Override
    public String getData(){
        return this.message;
    }

    @Override
    public String getData( String key ) {
        //decrypt the string in 'message' and return the decrypted string
        return super.decrypt(this.message, key);
    }

    /**
     * returns unique hashcode for object
     * @return
     */
    @Override
    public int hashCode() {
        int result = 13;
        result = 67*result + message.hashCode();
        result = 67*result + getDate().hashCode();
        result = 67*result + getUserName().hashCode();
        result = 67*result + getType();
        return result;
    }

    /**
     * compares two MessageClypeData objects
     * @param otherIN variable
     * @return boolean value
     */
    @Override
    public boolean equals(Object otherIN) {
        MessageClypeData other = (MessageClypeData) otherIN;
            return this.message == other.message
                    && this.getDate() == other.getDate()
                    && this.getUserName() == other.getUserName()
                    && this.getType() == other.getType();
    }

    /**
     * @return string description of the class
     */
    @Override
    public String toString() {
        return "Message: " + message + "\n" + "Date: " + getDate()
                + "\n" + "User: " + getUserName() + "\n"
                + "Type: " + getType();
    }

    @Override
    public int compareTo(ClypeData o) {
        return 0;
    }
}
