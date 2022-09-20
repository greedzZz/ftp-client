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
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class FTPUploaderTest {
    private FakeFtpServer server;
    private final Path path = Paths.get("students_test");
    FTPUploader uploader = new FTPUploader();

    @BeforeClass
    public void setUp() throws IOException {
        Files.write(path, "{\n\t\"students\": []\n}".getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
        server = new FakeFtpServer();
        FileSystem fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new DirectoryEntry("/"));
        fileSystem.add(new FileEntry("/students"));
        server.setFileSystem(fileSystem);
        server.addUserAccount(new UserAccount("testuser", "123", "/"));
        server.start();
    }

    @Test
    public void testUpload() throws IOException {
        uploader.upload("testuser", "123", "127.0.0.1", path);
        FileEntry studentsFile = (FileEntry) server.getFileSystem().getEntry("/students");
        Path serverData = Paths.get("students_test_server_data");
        Files.copy(studentsFile.createInputStream(), serverData, StandardCopyOption.REPLACE_EXISTING);
        StringBuilder actual = new StringBuilder();
        for (String line : Files.readAllLines(serverData)) actual.append(line);
        StringBuilder expected = new StringBuilder();
        for (String line : Files.readAllLines(path)) expected.append(line);
        Assert.assertEquals(actual.toString(), expected.toString());
        Files.deleteIfExists(serverData);
    }

    @Test(expectedExceptions = IOException.class)
    public void testUploadWrongParameters() throws IOException {
        uploader.upload("testuser", "asd", "127.0.0.1", path);
    }

    @AfterClass
    private void deletePath() throws IOException {
        server.stop();
        Files.deleteIfExists(path);
    }
}
