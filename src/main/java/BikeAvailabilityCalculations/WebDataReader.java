package BikeAvailabilityCalculations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WebDataReader extends WebDataReaderSuperClass {

    public static String readTextFromURL(String address) {
        try {
            return attemptToReadTextFromURL(address);
        } catch (Exception ex) {
            return "";
        }
    }

    private static String attemptToReadTextFromURL(String address) throws Exception {
        URL dataURL = new URL(address);
        InputStream bikeDataInputStream = null;
        try {
            URLConnection dataConnection = dataURL.openConnection();
            dataConnection.setReadTimeout(1000);
            bikeDataInputStream = dataConnection.getInputStream();
            BufferedReader dataReader = new BufferedReader(new InputStreamReader(bikeDataInputStream));
            return readAllDataFromReader(dataReader);
        } catch (Exception ex) {
            throw new RuntimeException();
        } finally {
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
