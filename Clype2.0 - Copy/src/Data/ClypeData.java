 package Data;

 import java.io.Serializable;
 import java.util.Date;

 /**
  *Class to make a ClypeData Object
  *
  * @author Joseph Tudor
  *
  */
 public abstract class ClypeData implements Comparable<ClypeData>, Serializable {

     /**
      *
      */
     private static final long serialVersionUID = 1L;
     private String userName;
     private final int type;
     private Date date;

     /**
      * Constructor to create a ClypeData Object when given a userName and a type
      * with userName and type initialized
      *
      * @param userName  String representing name of client user
      * @param type  integer representing type of data exchange
      */

     public ClypeData(String userName, int type) {
         this.userName = userName;
         this.type = type;
         this.date = new Date();
     }

     /**
      * Constructor to create ClypeData object when given a type
      * @param type  integer representing type of data exchange
      * @param userName
      */

     public ClypeData( int type, String userName ){
         this.type = type;
         this.userName = userName;
         this.date = new Date();
     }

     /**
      * Constructor to create ClypeData object when no type or userName is given
      */
     public ClypeData(){
             this(1, "Anon" );
     }

     /**
      * Method returning the type
      * @return type
      */
     public int getType(){
         return this.type;

     }

     /**
      * Method returning the userName
      * @return userName
      */
     public String getUserName(){
         return this.userName;

     }

     /**
      * Method returning the Date
      * @return Date
      */
     public Date getDate(){
         return this.date;

     }

     /**
      * method to return contents of instant message or file
      *
      */
     public abstract String getData();
         //return subclass information
     public abstract String getData(String key);
         //meant to return original data in an encrypted string
         //needs to be overloaded


     protected String encrypt( String inputStringToEncrypt, String key ) {
         //implement Vignere cipher
         String res = "";
         inputStringToEncrypt = inputStringToEncrypt.toUpperCase();
         for (int i = 0, j = 0; i < inputStringToEncrypt.length(); i++)
         {
             char c = inputStringToEncrypt.charAt(i);
             if (c < 'A' || c > 'Z') continue;
             res += (char)((c + key.charAt(j) - 2 * 'A') % 26 + 'A');
             j = ++j % key.length();

         }
         for (int x = 0; x < inputStringToEncrypt.length();x++){
             if (inputStringToEncrypt.charAt(x) == ' ')
                 res = res.substring(0, x) + " " + res.substring(x);
         }
         return res;

     }

     protected String decrypt( String inputStringToDecrypt, String key ) {
         String res = "";
         inputStringToDecrypt = inputStringToDecrypt.toUpperCase();
         for (int i = 0, j = 0; i < inputStringToDecrypt.length(); i++) {
             char c = inputStringToDecrypt.charAt(i);
             if (c < 'A' || c > 'Z') continue;
             res += (char)((c - key.charAt(j) + 26) % 26 + 'A');
             j = ++j % key.length();
         }
         for(int x = 0;x < inputStringToDecrypt.length();x++) {
             if (inputStringToDecrypt.charAt(x) == ' ')
                 res = res.substring(0, x) + " " + res.substring(x);
         }
         return res;


     }

 }