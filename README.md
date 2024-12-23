# Task 관리 프로그램

|  **이름** |              **설명**             |
|:---------:|:---------------------------------:|
|     id    |    고유하게 식별할 수 있는 값     |
|    제목   |             할 일 이름            |
|    설명   |             할 일 설명            |
| 마감 기한 |          Date class 사용          |
| 우선 순위 | 높음, 중간, 낮음 으로 구분 (enum) |
|   키워드  |              여러 개              |
| 완료 여부 |                                   |

## 기능

```java
public interface TaskManager {
	void addTask(Task task);
	void deleteTask(int id);
	void editTask(int id, Task task);
	
	// 조회
	Task getTaskById(int id);
	List<Task> getTaskByDate(Date date);
	List<Task> getTaskByTitle(String title);
	List<Task> getTaskByKeyword(String keyword);
	List<Task> listTasks();
		
	void markAsComplete(int id);
	void saveTasksToFile();
	void loadTasksFromFile();
}
```

## View
\[ID: 1\] \[미완료\] 보고서 작성 | 마감일: 2024-12-20 (D-2) | 우선순위: 높음  
\[ID: 2\] \[완료\]    프로젝트 계획 수립 | 자감일: 2025-01-25 (D-X) | 우선순위: 낮음  

<span style="color:red">마감 일이 임박할수록, 같은 마감 일인 경우 우선순위가 높은 순으로 보여준다.</span>

## 파일 저장 및 불러오기
프로그램 종료 시 파일로 저장하고 프로그램이 시작될 때 파일을 불러온다.  
파일에 저장 시 JSON 형태로 데이터를 저장하도록 한다.  
JSON 데이터 처리 라이브러리는 [Gson](https://mvnrepository.com/artifact/com.google.code.gson/gson)을 사용한다. 

## 목표
* GPT 등 ai봇 쓰지 않고 만들기 (구글 검색은 허용)
* 완성이 아니라도 만들 수 있는만큼 붙이기
* 주요 기능만 구성되면 가능이 추가되도 상관없다.

### 제작 기간 : 12월 18일 ~ 12월 23일 (스터디 프로젝트)
