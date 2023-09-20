package g58112.atlg3.boulderDash.model;

import java.io.IOException;
import java.io.InputStream;

public class File {
    private String str;

    /**
     * constructor for read the file
     * @param in the path of the file
     * @throws Throwable
     */
    public File(InputStream in){
        this.str = "";

        int charCode;
        try {
            while((charCode = in.read()) != -1) {
                this.str += Character.toString((char)charCode);
            }
        }
        catch (Throwable e) {
            if (in != null) {
                try {
                    in.close();
                }
                catch (Throwable e2) {
                    e.addSuppressed(e2);
                }
            }

            try {
                throw new IOException("Erreur lors de la lecture du fichier");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * get the Height of the map on the file
     * @return the height of the map
     */
    public int getHeight(){
        int nbLines = 0;
        int column = 0;

        for (int i = 0; i < str.length(); i++) {
            if (column == 0) nbLines++;
            if (str.charAt(i) == '\n') {
                column = 0;
            }
            else {
                column++;
            }

        }

        return nbLines;
    }
    /**
     * get the length of the map on the file
     * @return the length of the map
     */
    public int getLength(){
        int nbChars = 0;

        for (int i = 0; i < str.length(); i++) {
            if ((str.charAt(i) != '\n') && (str.charAt(i) != '\r')) {
                nbChars++;
            }
            else {
                return nbChars;
            }
        }

        return nbChars;
    }

    /**
     * get the string
     * @return the string map
     */
    public String toString() {
        return this.str;
    }
}
