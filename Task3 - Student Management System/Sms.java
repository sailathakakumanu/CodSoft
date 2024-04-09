import java.sql.*;
import java.util.*;

class Student {
	private String name;
	private String rollNumber;
	private String grade;

	public  Student(String name, String rollNumber, String grade) {
		this.name = name;
		this.rollNumber = rollNumber;
		this.grade = grade;
	}

	public String getName() {
		return name;
	}

	public String getRollNumber() {
		return rollNumber;
	}

	public String getGrade() {
		return grade;
	}
}

class StudentManagementSystem {
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	private PreparedStatement preparedStatement;

	public StudentManagementSystem() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");  
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdb", "root", "Omkar@2007");
			stmt = con.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS students (name VARCHAR(255) NOT NULL, roll_number VARCHAR(255) PRIMARY KEY, grade VARCHAR(255) NOT NULL);");
		}
		catch (ClassNotFoundException e) {
            System.out.println("Error: MySQL JDBC driver not found.");
            e.printStackTrace();
        } 
		catch(Exception e)
		{
			System.out.println("Error: "+e.getMessage());
		}
	}

	public void addStudent(Student student) 
	{
		try 
		{
			if (student.getName() == null ||student.getName().isEmpty() || student.getRollNumber() == null || student.getRollNumber().isEmpty())
			{
	            System.out.println("Error: Name or roll number cannot be null.");
	            return;
	        }
			preparedStatement = con.prepareStatement("INSERT INTO students (name, roll_number, grade) VALUES (?, ?, ?)");
			preparedStatement.setString(1, student.getName());
			preparedStatement.setString(2, student.getRollNumber());
			preparedStatement.setString(3, student.getGrade());
			preparedStatement.executeUpdate();
			System.out.println("Student added successfully.");
		} 
		catch (SQLException e) 
		{
	        if (e.getSQLState().equals("23000")) {
	            System.out.println("Error adding student: Roll number already exists.");
	        } 
	        else 
	            System.out.println("Error adding student: " + e.getMessage());
	    }
		catch(Exception e)
		{
			System.out.println("Error: "+e.getMessage());
		}
	}

	public void removeStudent(String rollNumber) 
	{
		try {
			preparedStatement = con.prepareStatement("DELETE FROM students where roll_number = ?;");
			preparedStatement.setString(1, rollNumber);
			int rowsDeleted = preparedStatement.executeUpdate();
			if (rowsDeleted > 0) {
				System.out.println("Student with roll number " + rollNumber + " removed successfully.");
			} else {
				System.out.println("No student found with roll number " + rollNumber + ".");
			}
		} catch (SQLException e) {
			System.out.println("Error removing student: " + e.getMessage());
		}
		catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
	}

	public void searchStudent(String rollNumber) {
		try {
			preparedStatement = con.prepareStatement("SELECT * FROM students where roll_number = ?");
			preparedStatement.setString(1, rollNumber);
			 rs = preparedStatement.executeQuery();
			if (rs.next()) {
				String name = rs.getString("name");
				String grade = rs.getString("grade");
				System.out.println("Student details are: ");
				System.out.println("Name: " + name + ", Roll Number: " + rollNumber + ", Grade: " + grade);
			} else {
				System.out.println("No student found with roll number - " + rollNumber);
			}
		} catch (SQLException e) {
			System.out.println("Error searching for student: " + e.getMessage());
		}
		catch(Exception e)
		{
			System.out.println("Error: "+e.getMessage());
		}
	}
	public void editName(String rollNumber, String newName) {
		try {
			
			preparedStatement = con.prepareStatement("UPDATE students SET name = ? WHERE roll_number = ?");
			preparedStatement.setString(1, newName);
			preparedStatement.setString(2, rollNumber);
			int rowsUpdated = preparedStatement.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Student data updated successfully.");
			} else {
				System.out.println("No student found with roll number " + rollNumber + ".");
			}
		} catch (SQLException e) {
			System.out.println("Error editing student data: " + e.getMessage());
		}
		catch(Exception e)
		{
			System.out.println("Error: "+e.getMessage());
		}
	}
	public void editGrade(String rollNumber, String grade) {
		try {
			
			preparedStatement = con.prepareStatement("UPDATE students SET grade = ? WHERE roll_number = ?");
			preparedStatement.setString(1, grade);
			preparedStatement.setString(2, rollNumber);
			int rowsUpdated = preparedStatement.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Student data updated successfully.");
			} else {
				System.out.println("No student found with roll number " + rollNumber + ".");
			}
		} catch (SQLException e) {
			System.out.println("Error editing student data: " + e.getMessage());
		}
		catch(Exception e)
		{
			System.out.println("Error: "+e.getMessage());
		}
	}
	public void displayAllStudents() {
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM students");
			if (!rs.isBeforeFirst()) {
	            System.out.println("No students found.");
	            return;
	        }
			System.out.println("The details of all students are as follows: ");
			while (rs.next()) {
				String name = rs.getString("name");
				String rollNumber = rs.getString("roll_number");
				String grade = rs.getString("grade");
				System.out.println("Name: " + name + ", Roll Number: " + rollNumber + ", Grade: " + grade);
			}
		} catch (SQLException e) {
			System.out.println("Error retrieving students: " + e.getMessage());
		}
		catch(Exception e)
		{
			System.out.println("Error: "+e.getMessage());
		}
	}

	public void closeConnection() {
		try {
			if (con!= null) {
				con.close();
			}
		} catch (SQLException e) {
			System.out.println("Error closing database connection: " + e.getMessage());
		}
		catch(Exception e)
		{
			System.out.println("Error: "+e.getMessage());
		}
	}
}

public class Sms {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		StudentManagementSystem sms = new StudentManagementSystem();
		int choice;
		while(true){
			try {
			System.out.println("Student Management System Menu: ");
			System.out.println(
					"1. Add student \n2. Remove student \n3. Modify student data \n4. Search student \n5. Display all students \n6.Exit");
			System.out.print("Enter your choice: ");
			String choiceInput = scanner.nextLine();
            if (!choiceInput.matches("\\d+")) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            choice = Integer.parseInt(choiceInput);
			String rollNumber;
			switch (choice) {
				case 1:
					
					System.out.print("Enter student's name: ");
					String name = scanner.nextLine();
					System.out.print("Enter student's roll number: ");
					rollNumber = scanner.nextLine();
					System.out.print("Enter student's grade: ");
					String grade = scanner.nextLine();
					Student student = new Student(name, rollNumber, grade);
					sms.addStudent(student);
					break;
				case 2:
					System.out.print("Enter the roll number of the student to remove: ");
					 rollNumber = scanner.nextLine();
					sms.removeStudent(rollNumber);
					break;
				case 3: System.out.print("Enter the roll number of the student to edit: ");
				String rollNumberToEdit = scanner.nextLine();
				System.out.print("Enter \n1 - change Name\n2 - change Grade: \n");
				int input = scanner.nextInt();
				scanner.nextLine();
				
				switch(input)
				{
				case 1: System.out.println("Enter the new name for the student: ");
				String newName = scanner.nextLine();
				sms.editName(rollNumberToEdit, newName);
				break;
				case 2 :  System.out.println("Enter the new grade for the student: ");
				String newGrade = scanner.nextLine();
				sms.editGrade(rollNumberToEdit, newGrade);
				break;
				default : System.out.println("Invalid Choice.");
				}
				break;
				case 4: System.out.println("Enter the roll number of the student to search: ");
						rollNumber = scanner.nextLine();
						sms.searchStudent(rollNumber);
						break;
				case 5: sms.displayAllStudents();
						break;
				case 6: System.out.println("Exiting Student Management System. Thank you!");
						sms.closeConnection();
						scanner.close();
						System.exit(0);
				default: System.out.println("Invalid choice. Please try again.");
			}
			}
			catch(Exception e)
			{
				System.out.println("Error: "+e.getMessage());
			}
			}
		}
	
}