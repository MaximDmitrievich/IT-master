package highlevellangs.code.hw.repos.simple.entity;

public class Project {
    private String projectName;
	private String projectDuration;
	private String projectBegin;
	private String projectDone;

	private int ProjectId;

	public Project(){
		super();
		ProjectId = -1;
	}

	public Project(String PName){
		this();
		this.projectName = PName;
	}

	public Project(String PName, String PDure, String PDStart){
		this.projectName = PName;
		this.projectDuration = PDure;
		this.projectBegin = PDStart;
	}

	public Project(int PId, String PName, String PDure, String PDStart, String PDComp){
		this.ProjectId = PId;
		this.projectName = PName;
		this.projectDuration = PDure;
		this.projectBegin = PDStart;
		this.projectDone = PDComp;
	}

	public int getProjectId() {
		return ProjectId;
	}

	public void setProjectId(int projectId) {
		ProjectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDuration() {
		return projectDuration;
	}

	public void setProjectDuration(String projectDuration) {
		this.projectDuration = projectDuration;
	}

	public String getProjectBegin() {
		return projectBegin;
	}

	public void setProjectBegin(String projectBegin) {
		this.projectBegin = projectBegin;
	}

	public String getProjectDone() {
		return projectDone;
	}

	public void setProjectDone(String projectDone) {
		this.projectDone = projectDone;
	}
}