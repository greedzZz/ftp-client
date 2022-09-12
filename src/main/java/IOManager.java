import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class IOManager {
    private final Scanner scanner = new Scanner(System.in);
    private final StudentsManager studentsManager;

    public IOManager(StudentsManager studentsManager) {
        this.studentsManager = studentsManager;
    }

    public void readInput() {
        System.out.println("Выберите действие:");
        System.out.println("[1] - получить список студентов по имени");
        System.out.println("[2] - получить информацию о студенте по id");
        System.out.println("[3] - добавить студента");
        System.out.println("[4] - удалить студента по id");
        System.out.println("[5] - завершение работы");
        while (scanner.hasNextLine()) {
            String studentName;
            int id;
            String input = scanner.nextLine().trim();
            try {
                switch (input) {
                    case "1":
                        List<Map.Entry<Integer, String>> names = studentsManager.getNames();
                        if (names.size() > 0) {
                            System.out.println("id\tname");
                            for (Map.Entry<Integer, String> name : names)
                                System.out.println(name.getKey() + "\t" + name.getValue());
                        } else System.out.println("Список студентов пуст");
                        break;
                    case "2":
                        id = readId();
                        studentName = studentsManager.getNameById(id);
                        if (null != studentName) System.out.println(studentName);
                        else System.out.println("Студента с таким id не существует");
                        break;
                    case "3":
                        studentName = readLine("имя");
                        studentsManager.add(studentName);
                        System.out.println("Студент успешно добавлен");
                        break;
                    case "4":
                        id = readId();
                        String result = studentsManager.delete(id);
                        if (null != result) System.out.println("Студент успешно удалён");
                        else System.out.println("Студента с таким id не существовало");
                        break;
                    case "5":
                        return;
                    default:
                        System.out.println("Некорректный ввод");
                }
            } catch (IllegalArgumentException e) {
                return;
            } catch (IllegalStateException e) {
                System.out.println("К сожалению, нельзя добавить новых студентов без удаления старых");
            }
            System.out.println("\nВыберите действие:");
            System.out.println("[1] - получить список студентов по имени");
            System.out.println("[2] - получить информацию о студенте по id");
            System.out.println("[3] - добавить студента");
            System.out.println("[4] - удалить студента по id");
            System.out.println("[5] - завершение работы");
        }
        scanner.close();
    }

    public String readLine(String name) {
        System.out.println("Введите " + name + ":");
        while (scanner.hasNextLine()) {
            String answer = scanner.nextLine().trim();
            if (!answer.equals("")) {
                return answer;
            }
            System.out.println(name + " не может быть пустым словом");
        }
        throw new IllegalArgumentException();
    }

    public String readIP() {
        System.out.println("Введите IP адрес:");
        while (scanner.hasNextLine()) {
            String answer = scanner.nextLine().trim();
            if (answer.matches("^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$")) {
                return answer;
            }
            System.out.println("IP адрес должен соответствовать шаблону: [0-255].[0-255].[0-255].[0-255]");
        }
        throw new IllegalArgumentException();
    }

    private int readId() {
        System.out.println("Введите id студента:");
        while (scanner.hasNextLine()) {
            try {
                int id = Integer.parseInt(scanner.nextLine().trim());
                if (id > 0) return id;
                System.out.println("Id должен быть положительным целым числом");
            } catch (NumberFormatException e) {
                System.out.println("Id должен быть положительным целым числом");
            }
        }
        throw new IllegalArgumentException();
    }
}
