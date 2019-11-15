import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class can be use to assign tasks to people. Two files are needed: The
 * file with the tasks and the file with the people to assign the tasks. The
 * program will write a file with the tasks assigned to people in one file named
 * fileOutputPath.
 * 
 * @author ivan
 *
 */
public class TaskAssigner {

	private ArrayList<Person> people;
	private ArrayList<Task> tasks;
	private String fileOutputPath;

	/**
	 * This constructor needs 3 paths: The path of the file with the Person's
	 * name, the path of the file with the Tasks and the path to create a file
	 * with the tasks assigned to the people. TaskAssigner will create 2 lists:
	 * One list with people and another with tasks.
	 * 
	 * @param peopleFilePath
	 *            The path of the file with the Person's name.
	 * @param tasksFilePath
	 *            The path of the file with the Tasks's name.
	 * @param fileOutputPath
	 *            The path to create a file with tasks assigned to the people.
	 */
	public TaskAssigner(String peopleFilePath, String tasksFilePath, String fileOutputPath) {
		super();
		ArrayList<String> peopleFile = toList(readFile(peopleFilePath));
		ArrayList<String> tasksFile = toList(readFile(tasksFilePath));
		people = new ArrayList<>();
		tasks = new ArrayList<>();

		for (String person : peopleFile) {
			Person p = new Person(person);
			people.add(p);
		}
		for (String task : tasksFile) {
			Task t = new Task(task);
			tasks.add(t);
		}
		this.fileOutputPath = fileOutputPath;
	}

	public ArrayList<Person> getPeople() {
		return people;
	}

	public void setPeople(ArrayList<Person> people) {
		this.people = people;
	}

	public ArrayList<Task> getTasks() {
		return tasks;
	}

	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}

	/**
	 * Creates a new File object
	 * 
	 * @param path
	 *            File's path
	 * @return true if the file is successfully created, false in another case
	 */
	public boolean createFile(String path) {
		File file = null;
		try {
			file = new File(path + ".txt");
			return file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Read plain text file
	 * 
	 * @param file
	 *            File to read
	 * @return a String array without the null values of the file
	 */
	public String[] readFile(String filePath) {
		File file = new File(filePath);
		FileReader fr = null;
		BufferedReader reader = null;
		String txt[] = new String[(int) file.length()];
		try {
			fr = new FileReader(file);
			reader = new BufferedReader(fr);
			for (int i = 0; i < txt.length; i++) {
				txt[i] = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fr != null && reader != null) {
				try {
					fr.close();
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return txt = Arrays.stream(txt).filter(s -> (s != null && s.length() > 0)).toArray(String[]::new);
	}

	/**
	 * Writting a file
	 * 
	 * @param file
	 *            File to write
	 * @param content
	 *            Content of file
	 */
	public void writeFile(String filePath, String content, boolean append) {
		File file = new File(filePath);
		FileWriter fw = null;
		BufferedWriter writer = null;

		try {
			fw = new FileWriter(file.getAbsolutePath(), append);
			writer = new BufferedWriter(fw);
			writer.write(content + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fw != null && writer != null) {
				try {
					writer.close();
					fw.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	/**
	 * Method to write the people with tasks in a file.
	 * 
	 * @param people
	 *            List of people.
	 */
	public void writeTasks(ArrayList<Person> people) {
		for (Person person : people) {
			writeFile(fileOutputPath, "-----------" + person.getName() + "----------", true);
			ArrayList<Task> currenPersonTasks = person.getTasks();
			for (Task task : currenPersonTasks) {
				writeFile(fileOutputPath, task.getName(), true);
			}
		}
	}

	/**
	 * This method use an array to convert it into an ArrayList.
	 * 
	 * @param array
	 *            The array to convert.
	 * @return an ArrayList with the array elements.
	 */
	public ArrayList<String> toList(String[] array) {
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}
		return list;
	}

	/**
	 * Random numbers with max and min
	 * 
	 * @param min
	 * @param max
	 * @return the random number
	 */
	public int random(int min, int max) {
		int range = (max - min) + 1;
		return (int) (Math.random() * range) + min;
	}

	/**
	 * This method assign tasks to people. Two options are available:
	 * <ul>
	 * <li>The size of the tasks list is less or equal than the size of the
	 * people list.
	 * <li>The size of the tasks list is greater than the size of the people
	 * list. In this case, other two options are available:
	 * <ul>
	 * <li>Each person have the same number of tasks.
	 * <li>Some people have more tasks than other.
	 * <ul>
	 * <ul>
	 * 
	 * @param tasks
	 *            List of tasks.
	 * @param people
	 *            List of people.
	 */
	public void assign(ArrayList<Task> tasks, ArrayList<Person> people) {
		double tasksSize = tasks.size();
		double peopleSize = people.size();
		double taskPerPerson = Math.round(tasksSize / peopleSize);
		ArrayList<Person> peopleAssigned = null;
		if (tasksSize <= peopleSize) {
			peopleAssigned = tasksLessOrEqualThanPeople(tasks, people);
			writeTasks(peopleAssigned);
		} else if (tasksSize > peopleSize) {
			double remainder = tasksSize % peopleSize;
			boolean zero = remainder == 0;
			if (zero) {
				remainderZero(tasks, people, taskPerPerson);
				writeTasks(people);
			} else {
				while (tasks.size() >= people.size()) {
					remainderZero(tasks, people, taskPerPerson);
				}
				peopleAssigned = tasksLessOrEqualThanPeople(tasks, people);
				writeTasks(peopleAssigned);
			}
		}

	}

	/**
	 * This method can be used when the size of the tasks list is less or equal
	 * than the size of the people list.
	 * 
	 * @param tasks
	 *            List of tasks.
	 * @param people
	 *            List of people.
	 */
	public void remainderZero(ArrayList<Task> tasks, ArrayList<Person> people, double taskPerPerson) {
		for (int i = 0; i < people.size(); i++) {
			Person currentPerson = people.get(i);
			for (int j = 0; j < taskPerPerson; j++) {
				int r = random(0, tasks.size() - 1);
				currentPerson.addTask(tasks.remove(r));
			}
		}
	}

	/**
	 * This method can be used when each person have the same number of tasks.
	 * 
	 * @param tasks
	 *            List of tasks.
	 * @param people
	 *            List of people.
	 * @param taskPerPerson
	 *            tasksList.size() / peopleList.size().
	 */
	public ArrayList<Person> tasksLessOrEqualThanPeople(ArrayList<Task> tasks, ArrayList<Person> people) {
		int randomTask, randomPerson;
		Task task;
		Person person;
		ArrayList<Person> peopleAssigned = new ArrayList<>();

		while (tasks.size() > 0) {
			randomTask = random(0, tasks.size() - 1);
			task = tasks.remove(randomTask);
			randomPerson = random(0, people.size() - 1);
			person = people.remove(randomPerson);
			person.addTask(task);
			peopleAssigned.add(person);
		}
		peopleAssigned.addAll(people);
		return peopleAssigned;

	}

}
