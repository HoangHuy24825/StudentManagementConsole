package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import controller.ControllerImp;

public class Student implements Comparable<Student> {
	public static int maxID;

	private int id;
	private String firstName;
	private String lastName;
	private Date birth;
	private String gender;

	public Student(String firstName, String lastName, Date birth, String gender) {
		super();
		this.id = ++maxID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birth = birth;
		this.gender = gender;
	}

	public Student() {
		this.id = ++maxID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "SV" + String.format("%05d", id) + ";" + firstName + ";" + lastName + ";" + sdf.format(birth) + ";"
				+ gender;
	}

	public static Student createStudentFromText(String row) {
		if (row != null) {
			Student student = new Student();
			String[] arrStr = row.split(";");

			student.setId(Integer.parseInt(arrStr[0].substring(2)));
			student.setFirstName(arrStr[1]);
			student.setLastName(arrStr[2]);
			try {
				student.setBirth(sdf.parse(arrStr[3]));
			} catch (ParseException e) {
				System.out.println("Lỗi định dạng ngày sinh!");
			}

			student.setGender(arrStr[4]);
			return student;
		}
		return null;
	}

	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public static String getTitleToSave() {
		return "# ma sv; ho dem; ten; ngay sinh; gioi tinh";
	}

	public void showInfor() {
		System.out.format("[ %8s; %20s; %10s; %6s]\n", "SV" + String.format("%05d", id), firstName + " " + lastName,
				sdf.format(birth), gender);
	}

	@Override
	public int compareTo(Student o) {
		return ControllerImp.getInstance().changeUnicodeToEn(this.getLastName())
				.compareTo(ControllerImp.getInstance().changeUnicodeToEn(o.getLastName()));
	}

	public void showInforToTable() {
		System.out.format("          | %-10s | %-35s | %-15s | %10s |    %-7s |\n", "SV" + String.format("%05d", id),
				firstName, lastName, sdf.format(birth), gender);
	}
	
	public String getNameAfterChangeToEn() {
		return ControllerImp.getInstance().changeUnicodeToEn(firstName.toLowerCase()+" "+lastName.toLowerCase());
	}

}
