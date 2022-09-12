package parsers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class JSONToStudentParser {
    public void parse(Path path, Map<Integer, String> students) throws IOException {
        try {
            List<String> lines = Files.readAllLines(path);
            if (lines.size() >= 8) {
                for (int i = 3; i < lines.size() - 3; i += 4) {
                    String[] idLine = lines.get(i).trim().split(": ");
                    String[] nameLine = lines.get(i + 1).split(": \"");
                    students.put(Integer.parseInt(idLine[1].substring(0, idLine[1].length() - 1)),
                            nameLine[1].substring(0, nameLine[1].length() - 1));
                }
            }
        } catch (IOException e) {
            throw new IOException("Не удалось прочитать файл");
        }
    }
}
