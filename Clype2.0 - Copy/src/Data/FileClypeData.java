package Data;

import java.io.*;
import java.util.Scanner;

/**
 * Class to make FileClypeData object
 * inherits from ClypeData
 *
 * @author Joseph Tudor
 *
 */

public class FileClypeData extends ClypeData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String fileName;
    String fileContents;

    /**
     * Constructor for when creating FileClypeData
     * @param userName String to identify user
     * @param fileName String representing name of file
     * @param type String representing contents of file
     */
    public FileClypeData(String userName, String fileName, int type) {

        super();
        this.fileName = fileName;

    }

    /**
     * Default constructor
     *
     */
    public FileClypeData() {
        this("","",0);

    }

    /**
     * sets the fileName in the FileClypeData object
     * @param fileName String representing file name
     */
    public void setFileName( String fileName ) {
        this.fileName = fileName;
    }

    /**
     * returns the file name
     * @return fileName
     */
    public String getFileName(){
        return this.fileName;

    }

    /**
     * returns file contents
     * @return fileContents
     */
    @Override
    public String getData() {
        return this.fileContents;
    }

    @Override
    public String getData( String key ) {
        //decrypt the string in 'fileContents' and return the decrypted string
        return decrypt(this.fileContents, key);
    }


    /**
     * returns a hashcode for the FileClypeData object
     * @return result(hashcode)
     */
    @Override
    public int hashCode(){
        int result = 13;
        result = 67*result + fileName.hashCode();
        result = 67*result + fileContents.hashCode();
        result = 67*result + getType();
        result = 67*result + getDate().hashCode();
        result = 67*result + getUserName().hashCode();
        return result;

    }

    /**
     * Compares objects
     * @param OtherIN
     * @return boolean value
     */
    @Override
    public boolean equals( Object OtherIN ){
        FileClypeData other = (FileClypeData) OtherIN;
        return this.fileName == other.fileName
                && this.fileContents == other.fileContents
                && this.getType() == other.getType()
                && this.getDate() == other.getDate()
                && this.getUserName() == other.getUserName();
    }

    /**
     * returns a description of class as a string
     * @return String
     */
    @Override
    public String toString(){
        return "File: " + this.fileName + "\n"
                + "Contents: " + this.fileContents + "\n"
                + "Date: " + getDate() + "\n"
                + "User: " + getUserName();

    }

    public void readFileContents() {
        //open file pointed to by fileName
        //read contents of fileContents
        //close file
        //CATCH relevant exceptions
        //print messages about standard error
        //must throw and IOException
        try {
            Scanner scan = new Scanner(new File(fileName));
            while (scan.hasNext()) {
                fileContents += scan.next();
            }
            scan.close();
        } catch (FileNotFoundException fnfe ) {
            System.err.println( "File does not exist" );
        }

    }
    public void readFileContents( String key ) {
        //overloaded method
        //does SECURE file reads
        //open file pointed to by fileName
        //read contents
        //encrypt contents of file using 'key'
        //close file
        //CATCH relevant exceptions
        //print messages about standard error
        //must throw an IOException
        try {
            Scanner scan = new Scanner(new File(fileName));
            while (scan.hasNext()) {
                fileContents += scan.next();
            } encrypt(fileContents, key);
            scan.close();
        } catch (FileNotFoundException fnfe ) {
            System.err.println( "File does not exist" );
        }
    }
    public void writeFileContents() {
        //open file pointed to by 'fileName'
        //write contents of instance variable 'fileContents'
        //close file
        //CATCH relevant exceptions
        //print messages about standard error
     try {
         BufferedReader bufferedReader = new BufferedReader
                 (new FileReader(fileName));
         String nextLine;
         while ((nextLine = bufferedReader.readLine()) != null)  {
             System.out.println(nextLine);
         }
         bufferedReader.close();
     } catch ( IOException ioe ) {
         System.err.println( "IO error\n" );
     }

    }
    public void writeFileContents( String key ) {
        //overloaded method
        //does secure file writes
        //open file pointed to by 'fileName'
        //decrypt contents of instance variable 'fileContents'
        //write contents to file
        //close file
        //CATCH relevant exceptions
        //print messages about standard error
        try {
            BufferedReader bufferedReader = new BufferedReader
                    (new FileReader(fileName));
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                System.out.println(decrypt(nextLine, key));
            }
            bufferedReader.close();
        } catch ( IOException ioe ) {
            System.err.println( "IO error\n" );
        }

    }


    @Override
    public int compareTo(ClypeData o) {
        return 0;
    }

}
