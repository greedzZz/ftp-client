import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            Map<Integer, String> students = new HashMap<>();
            Path path = Paths.get("students");
            StudentsManager studentsManager = new StudentsManager(students);
            IOManager ioManager = new IOManager(studentsManager);
            FTPDownloader downloader = new FTPDownloader();
            String login = ioManager.readLine("логин");
            String password = ioManager.readLine("пароль");
            String ip = ioManager.readIP();
            downloader.download(login, password, ip, path);
            JSONToStudentParser jsonToStudentParser = new JSONToStudentParser();
            jsonToStudentParser.parse(path, students);
            ioManager.readInput();
            StudentToJSONParser studentToJSONParser = new StudentToJSONParser();
            studentToJSONParser.parse(students, path);
            FTPUploader uploader = new FTPUploader();
            uploader.upload(login, password, ip, path);
        } catch (IllegalArgumentException e) {
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
