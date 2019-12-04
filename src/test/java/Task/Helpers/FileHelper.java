package Task.Helpers;

import java.io.*;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHelper {
    public String dir = FileSystems.getDefault().getPath(".").toString();
    private static String _fileName = "credentials.cred";

    public static void write(String data) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(_fileName));
        bw.write(data);
        bw.close();
    }
    public static String read()throws Exception
    {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(_fileName)));
        return data;
    }
}
