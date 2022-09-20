package ftp;

import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FTPDownloaderTest {
    private FakeFtpServer server;
    private final Path path = Paths.get("students_test");
    FTPDownloader downloader = new FTPDownloader();

    @BeforeClass
    public void setUp() {
        server = new FakeFtpServer();
        FileSystem fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new DirectoryEntry("/"));
        fileSystem.add(new FileEntry("/students", "{\n\t\"students\": []\n}"));
        server.setFileSystem(fileSystem);
        server.addUserAccount(new UserAccount("testuser", "123", "/"));
        server.start();
    }

    @Test
    public void testDownload() throws IOException {
        downloader.download("testuser", "123", "127.0.0.1", path);
        StringBuilder actual = new StringBuilder();
        for (String line : Files.readAllLines(path)) actual.append(line).append("\n");
        String expected = "{\n\t\"students\": [" +
                "]\n}";
        Assert.assertEquals(actual.substring(0, actual.length() - 1), expected);
    }

    @Test(expectedExceptions = IOException.class)
    public void testDownloadWrongParameters() throws IOException {
        downloader.download("testuser", "asd", "127.0.0.1", path);
    }

    @AfterClass
    private void deletePath() throws IOException {
        server.stop();
        Files.deleteIfExists(path);
    }
}
