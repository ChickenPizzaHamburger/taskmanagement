import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TaskMain {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		TaskManagerImpl taskManager = new TaskManagerImpl();
		
		System.out.println("┌───────────┐");
		System.out.println("│ 과제 프로그램 │");
		System.out.println("└───────────┘");
		
		do {
			System.out.println("♣ 기능 소개 ♣");
			System.out.println("1. 과제 추가");
			System.out.println("2. 과제 삭제");
			System.out.println("3. 과제 변경");
			System.out.println("4. 과제 조회");
			System.out.println("5. 과제 완료 확인");
			System.out.println("6. 파일 저장 및 불러오기");
			System.out.println("7. 프로그램 종료하기");
			
			System.out.print("필요한 기능을 입력해주세요. > ");
			int choice = sc.nextInt();
			
			if (choice == 1) {
				Task task = new Task();
				
				System.out.print("일정의 제목을 입력해주세요 (20자 이하) > ");
				sc.nextLine();
				String title = sc.nextLine();
				while(true) {
					if(title.length() > 20) {
						System.out.println("제목이 너무 깁니다. 다시 입력해주세요.");
					} else break;
				}
				task.setTitle(title);
				task.setKeywords(title);
				
				System.out.print("일정의 내용을 입력해주세요 (255자 이하) > ");
				String description = sc.nextLine();
				while (true) {
					if(description.length() > 255) {
						System.out.println("내용이 너무 깁니다. 다시 입력해주세요.");
					} else break;
				}
				task.setDescription(description);
				
				Date dueDate = null;
				do {
					System.out.print("마감 기한을 입력해주세요 (연-월-일 형식, 예: 2024-12-31) > ");
					String date = sc.next();
					try {
						dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
						task.setDueDate(dueDate);
						
						Date currentDate = new Date();
						boolean isCompleted = currentDate.compareTo(dueDate) > 0;
						task.setCompleted(isCompleted);
						
						break;
					} catch (Exception e) {
						System.out.println("마감 기한을 잘못 입력했습니다. 다시 입력해주세요.");
					} 
				} while(true);
				
				boolean isOuter = true;
				do {
				System.out.print("일정의 우선 순위를 입력해주세요 (높음, 중간, 낮음) > ");
				String priority = sc.next().trim();
				switch(priority) {
					case "높음":{
						task.setTaskPriority(Task.Priority.HIGH); 
						isOuter = false;
						break;
						}
					case "중간":{
						task.setTaskPriority(Task.Priority.MEDIUM); 
						isOuter = false;
						break;
						}
					case "낮음":{
						task.setTaskPriority(Task.Priority.LOW); 
						isOuter = false;
						break;
						}
					default:{
						System.out.println("키를 잘못 입력했습니다.");
						continue;
						} 
					} 
				} while(isOuter);
				
				taskManager.addTask(task);
			} else
			if (choice == 2) {
				System.out.print("삭제할 과제의 ID를 입력해주세요 > ");
				int id = sc.nextInt();
				taskManager.deleteTask(id);
			} else
			if (choice == 3) {
				System.out.print("편집할 과제의 ID를 입력해주세요 > ");
				int id = sc.nextInt();
				
				Task task = taskManager.getTaskById(id);
				
				if (task == null) {
					System.out.println("해당 ID의 과제가 존재하지 않습니다.");
					return;
				}
				
				while (true) {
					System.out.println("편집할 항목을 선택하세요.");
					System.out.println("1. 제목");
					System.out.println("2. 내용");
					System.out.println("3. 마감 기한");
					System.out.println("4. 우선 순위");
					System.out.println("5. 전체 편집");
					System.out.print("> ");
					
					int subChoice = sc.nextInt();
					sc.nextLine();
					
					switch(subChoice) {
						case 1: // 제목
						{
							System.out.print("변경할 제목을 입력해주세요 (20자 이하, 현재 제목: " + task.getTitle() + ") > ");
							String title;
							while (true) {
								title = sc.nextLine();
								if (title.length() > 20) {
									System.out.println("제목이 너무 깁니다. 다시 입력해주세요.");
								} else {
									task.setTitle(title);
									task.setKeywords(title);
									System.out.println("제목이 수정되었습니다.");
									break;
								}
							}
							break;
						}
						case 2: // 내용
						{
							System.out.print("변경할 내용을 입력해주세요 (255자 이하) > ");
							String description;
							while (true) {
								description = sc.nextLine();
								if (description.length() > 255) {
									System.out.println("내용이 너무 깁니다. 다시 입력해주세요.");
								} else {
									task.setDescription(description);
									System.out.println("내용이 수정되었습니다.");
									break;
								}
							}
							break;
						}
						case 3: // 마감 기한
						{
							Date dueDate = null;
							do {
								System.out.print("변경할 마감 기한을 입력해주세요 (연-월-일 형식, 예: 2024-12-31) 현재 마감 기한: "
										+ new SimpleDateFormat("yyyy-MM-dd").format(task.getDueDate()) + ") > ");
								String date = sc.next();
								try {
									dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
									task.setDueDate(dueDate);
									
									Date currentDate = new Date();
									boolean isCompleted = currentDate.compareTo(dueDate) > 0;
									task.setCompleted(isCompleted);
									
									System.out.println("마감 기한이 수정되었습니다.");
									break;
								} catch(Exception e) {
									System.out.println("마감 기한을 잘못 입력했습니다. 다시 입력해주세요.");
								}
							} while (true);
							break;
						}
						case 4: // 우선 순위
						{
							boolean isOuter = true;
							do {
								System.out.print("변경할 우선 순위를 입력해주세요 (높음, 중간, 낮음) 현재 우선 순위: "
										+ ((task.getTaskPriority().name() == "HIGH")? 
												((task.getTaskPriority().name() == "MEDIUM") ? "높음" : "중간") : "낮음") + ") > ");
								String priority = sc.next().trim();
								switch (priority) {
									case "높음":
									{
										task.setTaskPriority(Task.Priority.HIGH);
										isOuter = false;
										System.out.println("우선 순위가 수정되었습니다.");
										break;
									}
									case "중간":
									{
										task.setTaskPriority(Task.Priority.MEDIUM);
										isOuter = false;
										System.out.println("우선 순위가 수정되었습니다.");
										break;
									}
									case "낮음":
									{
										task.setTaskPriority(Task.Priority.LOW);
										isOuter = false;
										System.out.println("우선 순위가 수정되었습니다.");
										break;
									}
									default:
									{
										System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
									}
								}
							} while (isOuter);
							break;
						}
						case 5: // 전체 편집
						{
							// 제목 편집
							System.out.print("변경할 제목을 입력해주세요 (20자 이하, 현재 제목: " + task.getTitle() + ") > ");
							String title;
							while (true) {
								title = sc.nextLine();
								if (title.length() > 20) {
									System.out.println("제목이 너무 깁니다. 다시 입력해주세요.");
								} else {
									task.setTitle(title);
									task.setKeywords(title);
									System.out.println("제목이 수정되었습니다.");
									break;
								}
							}
							
							// 내용 편집
							System.out.print("변경할 내용을 입력해주세요 (255자 이하) > ");
							String description;
							while (true) {
								description = sc.nextLine();
								if (description.length() > 255) {
									System.out.println("내용이 너무 깁니다. 다시 입력해주세요.");
								} else {
									task.setDescription(description);
									System.out.println("내용이 수정되었습니다.");
									break;
								}
							}
							
							// 마감 기한 편집
							Date dueDate = null;
							do {
								System.out.print("변경할 마감 기한을 입력해주세요 (연-월-일 형식, 예: 2024-12-31) 현재 마감 기한: "
										+ new SimpleDateFormat("yyyy-MM-dd").format(task.getDueDate()) + ") > ");
								String date = sc.next();
								try {
									dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
									task.setDueDate(dueDate);
									
									Date currentDate = new Date();
									boolean isCompleted = currentDate.compareTo(dueDate) > 0;
									task.setCompleted(isCompleted);
									
									System.out.println("마감 기한이 수정되었습니다.");
									break;
								} catch(Exception e) {
									System.out.println("마감 기한을 잘못 입력했습니다. 다시 입력해주세요.");
								}
							} while (true);
							
							// 우선 순위 편집
							boolean isOuter = true;
							do {
								System.out.print("변경할 우선 순위를 입력해주세요 (높음, 중간, 낮음) 현재 우선 순위: "
										+ ((task.getTaskPriority().name() == "HIGH")? 
												((task.getTaskPriority().name() == "MEDIUM") ? "높음" : "중간") : "낮음") + ") > ");
								String priority = sc.next().trim();
								switch (priority) {
									case "높음":
									{
										task.setTaskPriority(Task.Priority.HIGH);
										isOuter = false;
										System.out.println("우선 순위가 수정되었습니다.");
										break;
									}
									case "중간":
									{
										task.setTaskPriority(Task.Priority.MEDIUM);
										isOuter = false;
										System.out.println("우선 순위가 수정되었습니다.");
										break;
									}
									case "낮음":
									{
										task.setTaskPriority(Task.Priority.LOW);
										isOuter = false;
										System.out.println("우선 순위가 수정되었습니다.");
										break;
									}
									default:
									{
										System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
									}
								}
							} while (isOuter);
							System.out.println("전체 항목이 수정되었습니다.");
							break;
						}
						default: System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
					}
				}
			} else
			if (choice == 4) {
				System.out.println("조회할 항목을 선택하세요.");
				System.out.println("1. ID로 조회");
				System.out.println("2. 날짜로 조회");
				System.out.println("3. 제목으로 조회");
				System.out.println("4. 키워드로 조회");
				System.out.println("5. 전체 목록 조회");
				System.out.print("> ");
				
				int subChoice = sc.nextInt();
				sc.nextLine();
				
				switch (subChoice) {
					case 1: // ID로 조회
					{
						System.out.print("조회할 ID를 입력하세요 > ");
						int id = sc.nextInt();
						Task task = taskManager.getTaskById(id);
						if (task != null) {
							taskManager.getDeskData(task);
						}
						break;
					}
					case 2: // 날짜로 조회
					{
						System.out.print("조회할 날짜를 입력하세요 (연-월-일 형식, 예: 2024-12-31) > ");
						String dateInput = sc.next();
						
						try {
							Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateInput);
							List<Task> taskList = taskManager.getTaskByDate(date);
							if (taskList != null && !taskList.isEmpty()) {
								taskManager.getDeskData(taskList);
							}
						} catch (Exception e) {
							System.out.println("잘못된 날짜 형식입니다.");
						}
						break;
					}
					case 3: // 제목으로 조회
					{
						System.out.print("조회할 제목을 입력하세요 > ");
						String title = sc.nextLine();
						List<Task> taskList = taskManager.getTaskByTitle(title);
						if (taskList != null && !taskList.isEmpty()) {
							taskManager.getDeskData(taskList);
						}
						break;
					}
					case 4: // 키워드로 조회
					{
						System.out.println("조회할 키워드를 입력하세요 > ");
						String keyword = sc.nextLine();
						List<Task> taskList = taskManager.getTaskByKeyword(keyword);
						if (taskList != null && !taskList.isEmpty()) {
							taskManager.getDeskData(taskList);
						}
						break;
					}
					case 5: // 전체 목록 조회
					{
						List<Task> taskList = taskManager.listTasks();
						if (taskList != null && !taskList.isEmpty()) {
							taskManager.getDeskData(taskList);
						}
						break;
					}
					default: System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
				}
			} else
			if (choice == 5) {
				System.out.print("완료 여부를 확인할 과제 ID를 입력해주세요 > ");
				int id = sc.nextInt();
				
				taskManager.markAsComplete(id);
			} else
			if (choice == 6) {
				System.out.println("원하는 항목을 선택하세요.");
				System.out.println("1. JSON으로 저장");
				System.out.println("2. JSON에서 불러오기");
				System.out.print("> ");
				int subChoice = sc.nextInt();
				if (subChoice == 1) {
					taskManager.saveTasksToFile();
				} else if (subChoice == 2) {
					System.out.print("불러올 파일명을 입력해주세요 > ");
					String fileName = sc.next();
					taskManager.loadTasksFromFile(fileName);
				}
			} else
			if (choice == 7) {
				System.out.println("프로그램을 종료합니다.");
				sc.close();
				System.exit(0);
			} 
			else {
				System.out.println("숫자를 잘못 입력했습니다.");
			}
		} while(true);
	}
}
