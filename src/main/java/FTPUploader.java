import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

public class FTPUploader {
    public void upload(String login, String password, String ip, Path path) throws IOException {
        String ftpUrl = String.format("ftp://%s:%s@%s/students;type=i", login, password, ip);
        try {
            URLConnection urlConnection = new URL(ftpUrl).openConnection();
            OutputStream outputStream = urlConnection.getOutputStream();
            Files.copy(path, outputStream);
            outputStream.close();
        } catch (IOException e) {
            throw new IOException("Не удалось загрузить файл на FTP сервер");
        }
    }
}
