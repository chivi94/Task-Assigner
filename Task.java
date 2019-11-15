
/**
 * Tasks to assign to the people.
 * @author ivan
 *
 */
public class Task {
	private String name;
	
	/**
	 * Builds a Task with name.
	 * @param name The name of the Task.
	 */
	public Task(String name) {
		super();
		this.name = name;
	}

	/**
	 * 
	 * @return the name of the Task.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Task name.
	 */
	public String toString(){
		return getName();
	}
	
	
	
	

}
