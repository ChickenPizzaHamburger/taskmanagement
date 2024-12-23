import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class TaskManagerImpl implements TaskManager { // DAO

	/**
	 @List & Set
	 taskList.add(task);
	 System.out.println("[과제 추가 완료] 일정이 추가되었습니다.");
	 break;
	 
	 @Array
	 for (int i=0;i<taskList.length;i++){
	 	if (taskList[i] == null){
	 		taskList[i] = task;
	 		System.out.println("[과제 추가 완료] 일정이 추가되었습니다. (ID: " + i + ")");
	 		break;
	 	}
	 */
	
	HashMap<Integer, Task> taskList = new HashMap<>();
	
	// 한 묶음
	@Override
	public void addTask(Task task) { // 과제 추가
		if (taskList.isEmpty()) {
			task.setId(0);
			
			taskList.put(0, task);
			taskList.put(1, null);
			System.out.println("[과제 추가 완료] 일정이 추가되었습니다. (ID : 0)");
		} else {
			int lastKey = taskList.size() - 1;
			if (taskList.get(lastKey) == null) {
				task.setId(lastKey);
				
				taskList.put(lastKey, task);
				taskList.put(lastKey + 1, null);
				System.out.println("과제 추가 완료] 일정이 추가되었습니다. (ID : " + lastKey + ")");
			}
		}
	}

	@Override
	public void deleteTask(int id) { // 과제 삭제
		if(!taskList.containsKey(id) || taskList.get(id) == null) {
			System.out.println("[삭제 실패] 입력하신 ID(" + id + ")의 일정은 존재하지 않습니다.");
			return;
		}
		
		taskList.remove(id);
		System.out.println("[삭제 성공] 입력하신 ID(" + id + ")의 일정이 삭제되었습니다.");
	}

	@Override
	public void editTask(int id, Task task) { // 과제 편집
		if(!taskList.containsKey(id) || taskList.get(id) == null) {
			System.out.println("[변경 실패] 입력하신 ID(" + id + ")의 일정은 존재하지 않습니다.");
			return;
		}
		
		taskList.put(id, task);
		System.out.println("[변경 성공] 입력하신 ID(" + id + ")의 일정이 변경되었습니다.");
	}

	// 한 묶음
	@Override
	public Task getTaskById(int id) { // id로 가져오기
		if(taskList.isEmpty()) {
			System.out.println("[조회 실패] 일정이 없습니다.");
			return null;
		}
		
		if (!taskList.containsKey(id) || taskList.get(id) == null) {
			System.out.println("[조회 실패] 입력하신 ID와 일치하는 일정이 없습니다.");
			return null;
		}
		
		Task task = taskList.get(id);
		updateCompletion(task);
		return taskList.get(id); // Task 가져오기
	}

	@Override
	public List<Task> getTaskByDate(Date date) { // 마감 날짜로 가져오기
		if(taskList.isEmpty()) {
			System.out.println("[조회 실패] 일정이 없습니다.");
			return null;
		}
		
		List<Task> taskData = new ArrayList<>();
		for (Task task : taskList.values()) {
			if (task == null) continue;
			updateCompletion(task);
			if (task.getDueDate().equals(date)) {
				taskData.add(task);
			}
		}
		
		if (taskData.isEmpty()) {
			System.out.println("[조회 실패] 입력하신 마감 일정에 해당하는 일정이 없습니다.");
		}
		
		return sortTasks(taskData); // 마감날짜에 맞는 것을 리스트 형식으로 가져오기
	}

	@Override
	public List<Task> getTaskByTitle(String title) { // 제목으로 가져오기
		if(taskList.isEmpty()) {
			System.out.println("[조회 실패] 일정이 없습니다.");
			return null;
		}
		
		List<Task> taskData = new ArrayList<>();
		for (Task task : taskList.values()) {
			if (task == null) continue;
			updateCompletion(task);
			if (task.getTitle().equals(title)) {
				taskData.add(task);
			}
		}
		
		if (taskData.isEmpty()) {
			System.out.println("[조회 실패] 입력하신 제목에 해당하는 일정이 없습니다.");
		}
		
		return sortTasks(taskData); // 제목에 맞는 것을 리스트 형식으로 가져오기
	}

	@Override
	public List<Task> getTaskByKeyword(String keyword) { // 키워드로 가져오기
		if(taskList.isEmpty()) {
			System.out.println("[조회 실패] 일정이 없습니다.");
			return null;
		}
		
		List<Task> taskData = new ArrayList<>();
		for (Task task : taskList.values()) {
			if (task == null) continue;
			updateCompletion(task);
			
			String[] taskKeywords = task.getKeywords().split(" ");
			
			for (String taskKeyword : taskKeywords) {
				if (taskKeyword.contains(keyword)) {
					if(!taskData.contains(task)) {
						taskData.add(task);
					}
					break;
				}
			}
		}
		
		if (taskData.isEmpty()) {
			System.out.println("[조회 실패] 입력하신 키워드에 해당하는 일정이 없습니다.");
		}
		
		return sortTasks(taskData); // 키워드에 맞는 것을 리스트 형식으로 가져오기
	}

	@Override
	public List<Task> listTasks() { // 전체를 가져오기
		List<Task> taskData = new ArrayList<>();
		for (Task task : taskList.values()) {
			if (task == null) continue;
			updateCompletion(task);
			taskData.add(task);
		}
		
		if (taskList.isEmpty() || taskData.isEmpty()) {
			System.out.println("[조회 실패] 일정이 없습니다.");
			return null;
		}
		
		return sortTasks(taskData); // 리스트를 전체 가져오기
	}
	
	// Task 객체를 정렬하는 메서드
	private List<Task> sortTasks(List<Task> taskData){
		taskData.sort(new Comparator<Task>(){
			@Override
			public int compare(Task t1, Task t2) { // 미완료 > 완료
				if(t1.isCompleted() && !t2.isCompleted()) {
					return 1;
				}
				if(!t1.isCompleted() && t2.isCompleted()) {
					return -1;
				}
				
				int dueDateComparison = t1.getDueDate().compareTo(t2.getDueDate()); // 날짜
				if (dueDateComparison != 0) {
					return dueDateComparison;
				}
				
				int priorityComparison = t1.getTaskPriority().compareTo(t2.getTaskPriority()); // 우선 순위
				if (priorityComparison != 0) {
					return priorityComparison;
				}
				
				return t1.getTitle().compareTo(t2.getTitle()); // 가나다순
			}
		});
		return taskData;
	}

	// 한 묶음
	@Override
	public void markAsComplete(int id) { // 완료 표시
		Task task = taskList.get(id);
		
		if (task != null) {
			if (task.isCompleted()) {
				System.out.println("[완료 여부 확인]" + id + "에 해당하는 일정은 완료된 상태입니다.");
			} else {
				System.out.println("[완료 여부 확인]" + id + "에 해당하는 일정은 완료되지 않은 상태입니다.");
			}
		} else {
			System.out.println("[완료 여부 확인]" + id + "에 해당하는 과제가 없습니다.");
		}
	}

	@Override
	public void saveTasksToFile() { // 파일 저장
		Gson gson = new Gson();
		Map<Integer, Task> taskData = new HashMap<>(taskList);
		
		for (Task task : taskList.values()) {
			updateCompletion(task);
		}
		
		String timeName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
		String fileName = timeName + ".json";
		
		int fileCount = 1;
		File file = new File(fileName);
		while (file.exists()) {
			fileName = timeName + "(" + fileCount + ").json";
			file = new File(fileName);
			fileCount++;
		}
		
		try (FileWriter fileWriter = new FileWriter(fileName);
				BufferedWriter writer = new BufferedWriter(fileWriter)){
			gson.toJson(taskData, writer);
			System.out.println("[저장 성공] 일정이 JSON 형태로 저장되었습니다.");
		} catch (IOException e) {
			System.out.print("[저장 실패] 저장 중 오류가 발생했습니다.");
			System.err.println("(오류 : " + e + ")");
		}
		
	}

	@Override
	public void loadTasksFromFile(String fileName) { // 파일 불러오기
		Gson gson = new Gson();
		try (FileReader fileReader = new FileReader(fileName);
				BufferedReader reader = new BufferedReader(fileReader)){
			Map<Integer, Task> fileLoadData = gson.fromJson(reader, new TypeToken<Map<Integer, Task>>(){}.getType());
			
			if (fileLoadData != null) {
				taskList.clear();
				taskList.putAll(fileLoadData);
				
				for (Task task : taskList.values()) {
					updateCompletion(task);
				}
				
				System.out.println("[불러오기 성공] JSON 파일이 과제로 불러왔습니다.");
			}
		} catch (IOException e) {
			System.out.print("[불러오기 실패] 불러오기 중 오류가 발생했습니다.");
			System.err.println("(오류 : " + e + ")");
		}
	}
	
	// 완료 및 미완료 최신 갱신
	private void updateCompletion(Task task) {
		if (task != null)
		{
			Date currentDate = new Date();
			Date dueDate = task.getDueDate();
			 
			boolean isCompleted = currentDate.compareTo(dueDate) > 0;	 

			task.setCompleted(isCompleted);
		}
		 
	 } 
	
	// 찾을 때 출력
	public void getDeskData(Task task) { // Task 1개 들어올 때
		System.out.print("[ID: " + task.getId() + "]");
		System.out.print("[" + (task.isCompleted() ? "완료" : "미완료") + "]");
		System.out.print(" " + task.getTitle() + " | ");
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		System.out.print("마감일: " + dateFormat.format(task.getDueDate()));
		
		long difference = (task.getDueDate().getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24);
		
		System.out.print(" (D" + (difference >= 0 ? "-" + difference : "+" + Math.abs(difference)) + ")");
		
		if (!task.isCompleted() && difference <= 3) {
			System.out.print(" (마감 임박!)");
		}
		
		
		switch(task.getTaskPriority()) {
		case HIGH: System.out.println(" | 우선순위: 높음"); break;
		case MEDIUM: System.out.println(" | 우선순위: 중간"); break;
		case LOW: System.out.println(" | 우선순위: 낮음"); break;
		}
		
		System.out.println("내용: " + task.getDescription());
	}
	
	public void getDeskData(List<Task> taskList) { // Task가 List로 넣어진 형태로 들어올 때
		for(Task task: taskList) getDeskData(task);
	}
}
