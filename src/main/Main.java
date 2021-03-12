package main;

import java.io.File;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import io.FileFactory;
import model.Score;
import model.Student;
import model.Subject;

public class Main {

	public static ArrayList<Student> students = new ArrayList<Student>();
	public static ArrayList<Score> scores = new ArrayList<Score>();
	public static ArrayList<Subject> subjects = new ArrayList<Subject>();
	public static String studentFilePath = "sinhvien.txt";
	public static String scoreFilePath = "diem.txt";
	public static String subjectFilePath = "monhoc.txt";
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		initProgram(args);
		showMenu();
	}

	private static void initProgram(String[] args) {
		try {
			String dataPath = "";
			String templeFilePath = "";
			if (args != null) {
				for (String string : args) {
					if (string.startsWith("-dir=")) {
						dataPath = string.split("=")[1];
					}
				}
				if (dataPath.length() != 0) {
					studentFilePath = dataPath + File.separator + studentFilePath;
					scoreFilePath = dataPath + File.separator + scoreFilePath;
					subjectFilePath = dataPath + File.separator + subjectFilePath;
				} else {
					templeFilePath = System.getProperty("user.dir") + File.separator + "data";
				}
			}
			if (templeFilePath.length() != 0) {
				File templeFile = new File(templeFilePath);
				templeFile.mkdirs();
				studentFilePath = templeFilePath + File.separator + studentFilePath;
				scoreFilePath = templeFilePath + File.separator + scoreFilePath;
				subjectFilePath = templeFilePath + File.separator + subjectFilePath;
			}
			File studentFile = new File(studentFilePath);
			File scoreFile = new File(scoreFilePath);
			File subjectFile = new File(subjectFilePath);
			if (!studentFile.exists()) {
				studentFile.createNewFile();
			}
			if (!scoreFile.exists()) {
				scoreFile.createNewFile();
			}
			if (!subjectFile.exists()) {
				subjectFile.createNewFile();
			}
			students = FileFactory.getInstance().readFileStudent(studentFilePath);
			scores = FileFactory.getInstance().readFileScore(scoreFilePath);
			subjects = FileFactory.getInstance().readFileSubject(subjectFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void showMenu() {
		int chose = -1;
		do {
			try {

				System.out.println("     ______________________________________________");
				System.out.println("    |                     MENU                     |");
				System.out.println("    |     1. Quản lý thông tin.                    |");
				System.out.println("    |     2. Hiển thị bảng điểm theo sinh viên.    |");
				System.out.println("    |     3. Tìm kiếm.                             |");
				System.out.println("    |     0. Thoát chương trình.                   |");
				System.out.println("    |______________________________________________|");
				System.out.print("Lựa chọn: ");

				chose = Integer.parseInt(sc.nextLine());

				switch (chose) {
				case 1:
					showMenu1();
					break;
				case 2:
					ScoreManagement.getInstance().showAllScoreByStudent(scores, students);
					break;
				case 3:
					showMenu3();
					break;
				case 0:
					saveAllData();
					System.out.println("Cảm ơn đã dùng phần mềm! Tạm Biệt...");
					System.exit(0);
				default:
					System.err.println("Nhập sai! Vui lòng nhập lại!\n");
					break;
				}

			} catch (NumberFormatException e) {
				chose = -1;
				System.err.println("Sai kiểu dữ liệu! Vui lòng nhập số!\n");
			} catch (NoSuchElementException e) {
				System.out.println("\nCảm ơn đã dùng phần mềm! Tạm Biệt...");
				System.exit(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (chose < 0 || chose > 3);
		if (chose!=0) {
			showMenu();
		}
	}

	private static void saveAllData() {
		int chose = -1;
		do {
			try {
				System.out.println("	 _____________________________________________");
				System.out.println("	|    Bạn có muốn lưu những thay đổi không?    |");
				System.out.println("	|         1. Có             2. Không          |");
				System.out.println("	|_____________________________________________|");
				System.out.print("Lựa chọn: ");

				chose = Integer.parseInt(sc.nextLine());

				switch (chose) {
				case 1:
					checkSaved();
					break;
				case 2:
					System.out.println("Dữ liệu không được lưu!");
					break;
				default:
					System.err.println("Lựa chọn không hợp lệ! Nhập lại!\n");
				}
			} catch (NumberFormatException e) {
				chose = -1;
				System.err.println("Sai kiểu dữ liệu! Vui lòng nhập số!\n");
			} catch (NoSuchElementException e) {
				System.out.println("\nCảm ơn đã dùng phần mềm! Tạm Biệt...");
				System.exit(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (chose < 0 || chose > 2);
	}

	private static void checkSaved() {
		boolean isSaved = true;
		if (!FileFactory.getInstance().saveFileStudent(students, studentFilePath)
				|| !FileFactory.getInstance().saveFileScore(scores, scoreFilePath)
				|| !FileFactory.getInstance().saveFileSubject(subjects, subjectFilePath)) {
			isSaved = false;
		}
		if (isSaved) {
			System.out.println("Lưu dữ liệu thành công!");
		} else {
			System.err.println("Lưu dữ liệu thất bại!\n");
		}
	}

	private static void showMenu1() {
		int chose = -1;
		do {
			try {
				System.out.println("     ______________________________________________");
				System.out.println("    |                     MENU 1                   |");
				System.out.println("    |          1. Quản lý sinh viên.               |");
				System.out.println("    |          2. Hiển thị danh sách môn.          |");
				System.out.println("    |          0. Quay lại.                        |");
				System.out.println("    |______________________________________________|");
				System.out.print("Lựa chọn: ");

				chose = Integer.parseInt(sc.nextLine());

				switch (chose) {
				case 1:
					showMenu1_1();
					break;
				case 2:
					SubjectManagement.getInstance().showListSubject(subjects);
					break;
				case 0:
					System.out.println("[Quay lại]");
					break;
				default:
					System.err.println("Nhập sai! Vui lòng nhập lại!\n");
					break;
				}
			} catch (NumberFormatException e) {
				chose = -1;
				System.err.println("Sai kiểu dữ liệu! Vui lòng nhập số!\n");
			} catch (NoSuchElementException e) {
				System.out.println("\nCảm ơn đã dùng phần mềm! Tạm Biệt...");
				System.exit(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (chose < 0 || chose > 3);
		if (chose!=0) {
			showMenu1();
		}
	}

	private static void showMenu1_1() {
		int chose = -1;
		do {
			try {
				System.out.println("     ______________________________________________");
				System.out.println("    |                   MENU 1_1                   |");
				System.out.println("    |            1. Thêm sinh viên.                |");
				System.out.println("    |            2. Sửa sinh viên.                 |");
				System.out.println("    |            3. Xóa sinh viên.                 |");
				System.out.println("    |            4. Hiển thị danh sách.            |");
				System.out.println("    |            0. Quay lại.                      |");
				System.out.println("    |______________________________________________|");
				System.out.print("Lựa chọn: ");

				chose = Integer.parseInt(sc.nextLine());

				switch (chose) {
				case 1:
					StudentManagement.getInstance().addStudent(students);
					break;
				case 2:
					StudentManagement.getInstance().editStudent(students);
					break;
				case 3:
					StudentManagement.getInstance().deleteStudent(students);
					break;
				case 4:
					StudentManagement.getInstance().showListStudent(students);
					break;
				case 0:
					System.out.println("[Quay lại]");
					break;
				default:
					System.err.println("Nhập sai! Vui lòng nhập lại!\n");
					break;
				}
			} catch (NumberFormatException e) {
				chose = -1;
				System.err.println("Sai kiểu dữ liệu! Vui lòng nhập số!\n");
			} catch (NoSuchElementException e) {
				System.out.println("\nCảm ơn đã dùng phần mềm! Tạm Biệt...");
				System.exit(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (chose < 0 || chose > 4);
		if (chose != 0) {
			showMenu1_1();
		}
	}

	private static void showMenu3() {
		int chose = -1;
		do {
			try {
				System.out.println("     ________________________________________");
				System.out.println("    |                 MENU 3                 |");
				System.out.println("    |     1. Tìm kiếm sinh viên theo mã.     |");
				System.out.println("    |     2. Tìm kiếm sinh viên theo tên.    |");
				System.out.println("    |     0. Quay lại.                       |");
				System.out.println("    |________________________________________|");
				System.out.print("Lựa chọn: ");

				chose = Integer.parseInt(sc.nextLine());

				switch (chose) {
				case 1:
					SearchManagement.getInstance().searchStudentByID(students);
					break;
				case 2:
					SearchManagement.getInstance().searchStudentByName(students);
					break;
				case 0:
					System.out.println("[Quay lại]");
					break;
				default:
					System.err.println("Nhập sai! Vui lòng nhập lại!\n");
					break;
				}
			} catch (NumberFormatException e) {
				chose = -1;
				System.err.println("Sai kiểu dữ liệu! Vui lòng nhập số!\n");
			} catch (NoSuchElementException e) {
				System.out.println("\nCảm ơn đã dùng phần mềm! Tạm Biệt...");
				System.exit(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (chose < 0 || chose > 2);
		if (chose != 0) {
			showMenu3();
		}
	}

}
