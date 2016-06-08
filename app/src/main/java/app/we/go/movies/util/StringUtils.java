package app.we.go.movies.util;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

/**
 * TODO Move to personal util lib
 * Created by apapad on 2016-01-14.
 */
public class StringUtils {

    /**
     * Creates a multiline String with the entries of a Set<String>.
     * @param set
     * @return
     */
    public static String convertSetToMultilineString(Set<String> set) {
        StringBuilder sb = new StringBuilder();
        for (String table: set) {
            sb.append("\n").append(table);
        }
        return sb.toString();
    }


    /**
     * TODO: test this, I have just copied it from loadDummyData.
     * @param resourceId
     * @return
     */
    public static String readResourceToString(Context context, int resourceId) {
        InputStream is = context.getResources().openRawResource(resourceId);
        return readInputStreamToString(is);
    }

    @NonNull
    public static String readInputStreamToString(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String readLine = null;
        StringBuilder sb = new StringBuilder();

        try {
            while ((readLine = br.readLine()) != null) {
                sb.append(readLine);
            }

        } catch (IOException e) {
            e.printStackTrace(); //create exception output
        }

        return sb.toString();
    }

}
