import javax.xml.crypto.Data;
import java.io.*;
import java.util.BitSet;

public class WriteAndReadDataSet {
    public static void main(String[] args) throws IOException {
        // three example data sets
        String sensorName = "MyGoodOldSensor"; // does not change

        long timeStamps[] = new long[3];
        timeStamps[0] = System.currentTimeMillis();
        timeStamps[1] = timeStamps[0] + 1; // milli sec later
        timeStamps[2] = timeStamps[1] + 1000; // second later

        float[][] values = new float[3][];
        // 1st measure .. just one value
        float[] valueSet = new float[1];
        values[0] = valueSet;
        valueSet[0] = (float) 1.5; // example value 1.5 degrees

        // 2nd measure .. just three values
        valueSet = new float[3];
        values[1] = valueSet;
        valueSet[0] = (float) 0.7;
        valueSet[1] = (float) 1.2;
        valueSet[2] = (float) 2.1;

        // 3rd measure .. two values
        valueSet = new float[2];
        values[2] = valueSet;
        valueSet[0] = (float) 0.7;
        valueSet[1] = (float) 1.2;

        // write three data set into a file
        // TODO: your job. use DataOutputStream / FileOutputStream
        //File erstellen
        DataOutputStream dos = null;
        String filename = null;
        try {
            filename = "testFile.txt";
            OutputStream os = new FileOutputStream(filename);
            dos = new DataOutputStream(os);
        } catch (FileNotFoundException ex) {
            System.err.println("couldnt open file - fatal");
            System.exit(0);
        }
        //Messwerte eintragen
        try {
            dos.writeLong(timeStamps[0]); //current time
            //alle messwerte erfassen
            for (int i= 0;i<values.length;i++)
                for (int j = 0; j < values[i].length; j++)
                    dos.writeFloat(values[i][j]);

            dos.writeFloat(values[1][2]);//beispiel temperatur/ datensatz!

        } catch (IOException ex) {
            System.err.println("couldnt write data (fatal)");
            System.exit(0);
        }

        // read data from file and print to System.out
        // TODO: your job use DataInputStream / FileInputStream
        DataInputStream dis = null;
        try {
            InputStream is = new FileInputStream(filename);
            dis = new DataInputStream(is); // mit bytes
            long readLongValue = dis.readLong();
            float readFloatValue = dis.readFloat();

            PrintStream out = new PrintStream(dos);
            System.setOut(out); //read from/ in file //reassign standard outputstream
            out.println(sensorName);

            //Sys.out
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);  //kennt zeilenende
            String readString = br.readLine(); //Objekt von typ bufferedReader liest char solange bis zeilenende
            System.out.println("Dataset");
            System.out.println("Sensorname: " + sensorName); //oder readString
            System.out.println("Date: " + readLongValue);
            System.out.println("Temperatur: " + readFloatValue);
        } catch (FileNotFoundException ex) {
            System.err.println("couldnt open file -fatal");
            System.exit(0);
        } catch (IOException ex) {
            System.err.println("coudlnt read data (fatal");
            System.exit(0);
        } finally {
            try {
                dos.close();
                dis.close();
            } catch (IOException ex) {
                System.err.println("couldnt read data (fatal)");
                System.exit(0);
            }
        }

    }
}

