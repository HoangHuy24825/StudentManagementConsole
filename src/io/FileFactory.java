package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import main.Main;
import model.Score;
import model.Student;
import model.Subject;

public class FileFactory {
	private static FileFactory instance;

	public static FileFactory getInstance() {
		if (instance == null) {
			instance = new FileFactory();
		}
		return FileFactory.instance;
	}

	private FileFactory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean saveFileStudent(ArrayList<Student> students, String path) {
		try {
			FileWriter fw = new FileWriter(path);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(Student.getTitleToSave());
			bw.newLine();
			for (Student student : students) {
				bw.write(student.toString());
				bw.newLine();
			}
			bw.close();
			fw.close();
			return true;
		} catch (IOException e) {
			System.out.println("Lỗi ghi file!");
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	public boolean saveFileScore(ArrayList<Score> scores, String path) {
		try {
			FileWriter fw = new FileWriter(path);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(Score.getTitleToSave());
			bw.newLine();
			for (Score score : scores) {
				bw.write(score.toString());
				bw.newLine();
			}
			bw.close();
			fw.close();
			return true;
		} catch (IOException e) {
			System.out.println("Lỗi ghi file!");
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	public boolean saveFileSubject(ArrayList<Subject> subjects, String path) {
		try {
			FileWriter fw = new FileWriter(path);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(Subject.getTitleToSave());
			bw.newLine();
			for (Subject subject : subjects) {
				bw.write(subject.toString());
				bw.newLine();
			}
			bw.close();
			fw.close();
			return true;
		} catch (IOException e) {
			System.out.println("Lỗi ghi file!");
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	public ArrayList<Student> readFileStudent(String path) {
		try {
			ArrayList<Student> students = new ArrayList<Student>();
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			String row = "";
			while ((row = br.readLine()) != null) {
				if (row.startsWith("#")) {
					continue;
				}
				Student student = Student.createStudentFromText(row);
				Student.maxID = student.getId();
				students.add(student);
			}
			br.close();
			fr.close();
			return students;
		} catch (IOException e) {
			System.out.println("Lỗi đọc file");
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public ArrayList<Score> readFileScore(String path) {
		try {
			ArrayList<Score> scores = new ArrayList<Score>();
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			String row = "";
			while ((row = br.readLine()) != null) {
				if (row.startsWith("#")) {
					continue;
				}
				Score score = Score.createScoreFromText(row);
				scores.add(score);
			}
			br.close();
			fr.close();
			return scores;
		} catch (IOException e) {
			System.out.println("Lỗi đọc file");
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public ArrayList<Subject> readFileSubject(String path) {
		try {
			ArrayList<Subject> subjects = new ArrayList<Subject>();
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			String row = "";
			while ((row = br.readLine()) != null) {
				if (row.startsWith("#")) {
					continue;
				}
				Subject subject = Subject.createSubjectFromText(row);
				Subject.maxID = subject.getIdSubject();
				subjects.add(subject);
			}
			br.close();
			fr.close();
			return subjects;
		} catch (IOException e) {
			System.out.println("Lỗi đọc file");
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public void saveAllData() {
		saveFileScore(Main.scores, Main.scoreFilePath);
		saveFileStudent(Main.students, Main.studentFilePath);
		saveFileSubject(Main.subjects, Main.subjectFilePath);
	}

	public void readAllData() {
		Main.scores = readFileScore(Main.scoreFilePath);
		Main.students = readFileStudent(Main.studentFilePath);
		Main.subjects = readFileSubject(Main.subjectFilePath);
	}

}
