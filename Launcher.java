
public class Launcher {
	
	public static void main(String[] args) {
		TaskAssigner taskAssigner = new TaskAssigner("people.txt","tasks.txt","assign.txt");
		taskAssigner.assign(taskAssigner.getTasks(), taskAssigner.getPeople());
	}

}
