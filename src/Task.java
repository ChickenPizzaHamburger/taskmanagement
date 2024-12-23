import java.util.Date;

public class Task { // Bean
	private int id; // id - 고유하게 식별할 수 있는 값
	private String title; // 제목 - 할 일 이름
	private String description; // 설명 - 할 일 설명
	private Date dueDate; // 마감 기한 - Date class 사용
	private Priority taskPriority; // 우선 순위 - 높음, 중간, 낮음으로 구분
	private String keywords; // 키워드 - 여러 개
	private boolean isCompleted; // 완료 여부
	
	public enum Priority{
		HIGH, MEDIUM, LOW;
	}
	
	public Task() {}
	
	public Task(int id, String title, String description, Date dueDate, 
			Task.Priority taskPriority, String keywords, boolean isCompleted) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.taskPriority = taskPriority;
		this.keywords = keywords;
		this.isCompleted = isCompleted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Priority getTaskPriority() {
		return taskPriority;
	}

	public void setTaskPriority(Priority taskPriority) {
		this.taskPriority = taskPriority;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", title=" + title + ", description=" + description + ", dueDate=" + dueDate
				+ ", taskPriority=" + taskPriority + ", keywords=" + keywords + ", isCompleted=" + isCompleted + "]";
	}
}
