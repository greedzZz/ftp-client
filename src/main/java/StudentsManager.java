import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentsManager {
    private final Map<Integer, String> students;

    public StudentsManager(Map<Integer, String> students) {
        this.students = students;
    }

    public List<Map.Entry<Integer, String>> getNames() {
        return students.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toList());
    }

    public String getNameById(int id) {
        return students.get(id);
    }

    public void add(String name) {
        int id = generateId(students.keySet());
        if (id != 0) students.put(id, name);
        else throw new IllegalStateException();
    }

    public String delete(int id) {
        return students.remove(id);
    }

    private int generateId(Set<Integer> ids) {
        if (ids.size() == Integer.MAX_VALUE) return 0;
        else {
            int id = 1;
            while (id != Integer.MAX_VALUE) {
                if (!ids.contains(id)) break;
                id++;
            }
            return id;
        }
    }
}
