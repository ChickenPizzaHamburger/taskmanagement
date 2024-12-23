import java.util.Date;
import java.util.List;

public interface TaskManager {
	void addTask(Task task);
	void deleteTask(int id);
	void editTask(int id, Task task);
	
	Task getTaskById(int id);
	List<Task> getTaskByDate(Date date);
	List<Task> getTaskByTitle(String title);
	List<Task> getTaskByKeyword(String keyword);
	List<Task> listTasks();
	
	void markAsComplete(int id);
	void saveTasksToFile();
	void loadTasksFromFile(String fileName);
}