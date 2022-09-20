package parsers;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class JSONToStudentsParserTest {
    private final Path path = Paths.get("students_test");
    private final JSONToStudentParser parser = new JSONToStudentParser();

    @Test
    public void testParseNotEmpty() throws IOException {
        String content = "{\n\t\"students\": [" +
                "\n\t\t{\n\t\t\t\"id\": 1,\n\t\t\t\"name\": \"Max\"\n\t\t},\n" +
                "\t\t{\n\t\t\t\"id\": 2,\n\t\t\t\"name\": \"Alex\"\n\t\t},\n" +
                "\t\t{\n\t\t\t\"id\": 3,\n\t\t\t\"name\": \"Zoe\"\n\t\t}\n\t" +
                "]\n}";
        Files.write(path, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
        Map<Integer, String> students = new HashMap<>();
        parser.parse(path, students);
        Assert.assertEquals(students.size(), 3);
        Assert.assertEquals(students.get(1), "Max");
        Assert.assertEquals(students.get(2), "Alex");
        Assert.assertEquals(students.get(3), "Zoe");
    }

    @Test
    public void testParseEmpty() throws IOException {
        String content = "{\n\t\"students\": [" +
                "]\n}";
        Files.write(path, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
        Map<Integer, String> students = new HashMap<>();
        parser.parse(path, students);
        Assert.assertTrue(students.isEmpty());
    }

    @AfterClass
    private void deletePath() throws IOException {
        Files.deleteIfExists(path);
    }
}
