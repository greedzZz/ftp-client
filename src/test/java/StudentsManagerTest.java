import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentsManagerTest {
    private StudentsManager manager;

    @Test
    public void testGetNameById() {
        Map<Integer, String> students = new HashMap<>();
        students.put(1, "Max");
        students.put(2, "Alex");
        students.put(3, "Zoe");
        manager = new StudentsManager(students);
        Assert.assertEquals(manager.getNameById(1), "Max");
        Assert.assertEquals(manager.getNameById(2), "Alex");
        Assert.assertEquals(manager.getNameById(3), "Zoe");
    }

    @Test
    public void testGetNames() {
        Map<Integer, String> students = new HashMap<>();
        students.put(1, "Max");
        students.put(2, "Alex");
        students.put(3, "Zoe");
        manager = new StudentsManager(students);
        List<Map.Entry<Integer, String>> names = manager.getNames();
        Assert.assertEquals(names.get(0).getValue(), "Alex");
        Assert.assertEquals(names.get(1).getValue(), "Max");
        Assert.assertEquals(names.get(2).getValue(), "Zoe");
    }

    @Test
    public void testAdd() {
        Map<Integer, String> students = new HashMap<>();
        students.put(2, "Alex");
        students.put(3, "Zoe");
        manager = new StudentsManager(students);
        manager.add("Max");
        manager.add("John");
        Assert.assertEquals(students.get(1), "Max");
        Assert.assertEquals(students.get(4), "John");
    }

    @Test
    public void testDelete() {
        Map<Integer, String> students = new HashMap<>();
        students.put(1, "Max");
        students.put(2, "Alex");
        students.put(3, "Zoe");
        manager = new StudentsManager(students);
        manager.delete(2);
        Assert.assertEquals(students.size(), 2);
        Assert.assertNull(students.get(2));
    }
}
