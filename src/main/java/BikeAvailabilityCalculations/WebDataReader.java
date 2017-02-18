package BikeAvailabilityCalculations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class WebDataReader {

    public static String readTextFromURL(String address) {
        try {
            return attemptToReadTextFromURL(address);
        } catch (Exception ex) {
            System.out.println("Website text could not be read");
            return "";
        }
    }

    private static String attemptToReadTextFromURL(String address) throws Exception {
        URL dataURL = new URL(address);
        InputStream bikeDataInputStream = null;
        try {
            bikeDataInputStream = dataURL.openStream();
            BufferedReader dataReader = new BufferedReader(new InputStreamReader(bikeDataInputStream));
            return readAllDataFromReader(dataReader);
        } catch (Exception ex) {
            throw new Exception();
        }
        finally {
            bikeDataInputStream.close();
        }
    }

    private static String readAllDataFromReader(BufferedReader dataReader) throws IOException {
        StringBuilder stringBuilderForWebData = new StringBuilder();
        int characterJustRead = dataReader.read();
        while (characterJustRead != -1) {
            stringBuilderForWebData.append((char) characterJustRead);
            characterJustRead = dataReader.read();
        }
        return stringBuilderForWebData.toString();
    }
}
