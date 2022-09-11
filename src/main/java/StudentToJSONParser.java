import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class StudentToJSONParser {
    public void parse(Map<Integer, String> students, File file) {
        try (FileOutputStream fos = new FileOutputStream(file, false);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            bos.write("{\n".getBytes(StandardCharsets.UTF_8));
            bos.write("\t\"students\": [".getBytes(StandardCharsets.UTF_8));
            if (!students.isEmpty()) {
                bos.write("\n".getBytes(StandardCharsets.UTF_8));
                long serialNumber = 0;
                long size = students.entrySet().size();
                for (Map.Entry<Integer, String> student : students.entrySet()) {
                    serialNumber++;
                    bos.write("\t\t{\n".getBytes(StandardCharsets.UTF_8));
                    bos.write(("\t\t\t\"id\": " + student.getKey() + ",\n").getBytes(StandardCharsets.UTF_8));
                    bos.write(("\t\t\t\"name\": \"" + student.getValue() + "\"\n").getBytes(StandardCharsets.UTF_8));
                    if (serialNumber != size) bos.write("\t\t},\n".getBytes(StandardCharsets.UTF_8));
                    else bos.write("\t\t}\n\t".getBytes(StandardCharsets.UTF_8));
                }
            }
            bos.write("]\n".getBytes(StandardCharsets.UTF_8));
            bos.write("}".getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println("No permission to write to the file");
        }
    }
}
