package parsers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class StudentToJSONParser {
    public void parse(Map<Integer, String> students, Path path) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write("{\n");
            writer.write("\t\"students\": [");
            if (!students.isEmpty()) {
                writer.write("\n");
                long serialNumber = 0;
                long size = students.entrySet().size();
                for (Map.Entry<Integer, String> student : students.entrySet()) {
                    serialNumber++;
                    writer.write("\t\t{\n");
                    writer.write(("\t\t\t\"id\": " + student.getKey() + ",\n"));
                    writer.write(("\t\t\t\"name\": \"" + student.getValue() + "\"\n"));
                    if (serialNumber != size) writer.write("\t\t},\n");
                    else writer.write("\t\t}\n\t");
                }
            }
            writer.write("]\n");
            writer.write("}");
            writer.flush();
        } catch (IOException e) {
            throw new IOException("Не удалось обновить файл");
        }
    }
}
