package ftp;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FTPDownloader {
    public void download(String login, String password, String ip, Path path) throws IOException {
        String ftpUrl = String.format("ftp://%s:%s@%s/students;type=i", login, password, ip);
        try {
            URLConnection urlConnection = new URL(ftpUrl).openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            inputStream.close();
        } catch (IOException e) {
            throw new IOException("Не удалось скачать файл с FTP сервера");
        }
    }
}
