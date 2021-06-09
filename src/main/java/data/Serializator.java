package data;

import java.io.*;

/**
 * @author Tirlea Maria Cristina
 * class used to implement the methods for serializing and deserializing data
 */
public class Serializator implements Serializable{
    private static FileOutputStream fileOut;
    private static ObjectOutputStream out;
    private static FileInputStream fileIn;
    private static ObjectInputStream in;

    public static void serialize(Object o, String fileOutName){
        try {
            fileOut = new FileOutputStream(fileOutName);
            out = new ObjectOutputStream(fileOut);
            out.writeObject(o);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in " + fileOutName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object deserialize(String fileInName){
        try {
            fileIn = new FileInputStream(fileInName);
            in = new ObjectInputStream(fileIn);
            Object o = in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Deserialized data: " + o.getClass() + "from "+ fileInName);
            return o;
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Object class not found");
            c.printStackTrace();
            return null;
        }
    }
}
