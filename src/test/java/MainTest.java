import org.testng.TestNG;
import org.testng.xml.internal.Parser;

import java.io.IOException;

public class MainTest {
    public static void main(String[] args) throws IOException {
        TestNG testNG = new TestNG(true);
        final Parser parser = new Parser("testng.xml");
        testNG.setXmlSuites(parser.parseToList());
        testNG.run();
    }
}
