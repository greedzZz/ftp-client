package parsers;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class StudentToJSONParserTest {
    private final Path path = Paths.get("students_test");
    private final StudentToJSONParser parser = new StudentToJSONParser();

    @Test
    public void testParseNotEmpty() throws IOException {
        Map<Integer, String> students = new HashMap<>();
        students.put(1, "Max");
        students.put(2, "Alex");
        students.put(3, "Zoe");
        parser.parse(students, path);
        StringBuilder actual = new StringBuilder();
        for (String line : Files.readAllLines(path)) actual.append(line).append("\n");
        String expected = "{\n\t\"students\": [" +
                "\n\t\t{\n\t\t\t\"id\": 1,\n\t\t\t\"name\": \"Max\"\n\t\t},\n" +
                "\t\t{\n\t\t\t\"id\": 2,\n\t\t\t\"name\": \"Alex\"\n\t\t},\n" +
                "\t\t{\n\t\t\t\"id\": 3,\n\t\t\t\"name\": \"Zoe\"\n\t\t}\n\t" +
                "]\n}";
        Assert.assertEquals(actual.substring(0, actual.length() - 1), expected);
    }

    @Test
    public void testParseEmpty() throws IOException {
        parser.parse(new HashMap<>(), path);
        StringBuilder actual = new StringBuilder();
        for (String line : Files.readAllLines(path)) actual.append(line).append("\n");
        String expected = "{\n\t\"students\": [" +
                "]\n}";
        Assert.assertEquals(actual.substring(0, actual.length() - 1), expected);
    }

    @AfterClass
    private void deletePath() throws IOException {
        Files.deleteIfExists(path);
    }
}
