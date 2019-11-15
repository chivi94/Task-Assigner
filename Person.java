import java.util.ArrayList;

/**
 * Person to assign the tasks
 * @author ivan
 *
 */
public class Person {

	private String name;
	private ArrayList<Task> tasks;

	/**
	 * Builds a Person with name.
	 * @param name The name of the Person.
	 */
	public Person(String name) {
		super();
		this.name = name;
		tasks = new ArrayList<Task>();
	}

	/**
	 * 
	 * @return the name of the Person.
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return the list of the Tasks assigned to the Person.
	 */
	public ArrayList<Task> getTasks() {
		return tasks;
	}
	
	/**
	 * Add an task to the Person list.
	 * @param task Task to add to the list.
	 */
	public void addTask(Task task){
		tasks.add(task);
	}
	
	/**
	 * "Person name:" + person name.
	 */
	public String toString(){
		return "Person name:" + getName();
	}
	
	

}
