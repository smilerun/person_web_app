package com.bnaqica.person.util;

import java.io.*;

public class IoUtil {
	  /**
     * @return the bytes of an input stream, in the java default charset, returning a string (up to about 48K in length).
     */
    public static String readInputStream(InputStream content) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(content));
        final StringBuilder sb = new StringBuilder();
        final char[] buffer = new char[512];

        int count;
        while (sb.length() < 49_152 && (count = reader.read(buffer)) != -1) {
            sb.append(buffer, 0, count);
        }

        return sb.toString();
    }

    /**
     * @return the given StringBuilder, with the entire input content appended to it.
     */
    public static StringBuilder readFully(StringBuilder out, Reader content) throws IOException {
        final char[] buffer = new char[4198];

        int count;
        while ((count = content.read(buffer)) != -1) {
            out.append(buffer, 0, count);
        }

        return out;
    }

}
