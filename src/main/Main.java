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
				System.out.println("    |     1. Qu???n l?? th??ng tin.                    |");
				System.out.println("    |     2. Hi???n th??? b???ng ??i???m theo sinh vi??n.    |");
				System.out.println("    |     3. T??m ki???m.                             |");
				System.out.println("    |     0. Tho??t ch????ng tr??nh.                   |");
				System.out.println("    |______________________________________________|");
				System.out.print("L???a ch???n: ");

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
					System.out.println("C???m ??n ???? d??ng ph???n m???m! T???m Bi???t...");
					System.exit(0);
				default:
					System.err.println("Nh???p sai! Vui l??ng nh???p l???i!\n");
					break;
				}

			} catch (NumberFormatException e) {
				chose = -1;
				System.err.println("Sai ki???u d??? li???u! Vui l??ng nh???p s???!\n");
			} catch (NoSuchElementException e) {
				System.out.println("\nC???m ??n ???? d??ng ph???n m???m! T???m Bi???t...");
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
				System.out.println("	|    B???n c?? mu???n l??u nh???ng thay ?????i kh??ng?    |");
				System.out.println("	|         1. C??             2. Kh??ng          |");
				System.out.println("	|_____________________________________________|");
				System.out.print("L???a ch???n: ");

				chose = Integer.parseInt(sc.nextLine());

				switch (chose) {
				case 1:
					checkSaved();
					break;
				case 2:
					System.out.println("D??? li???u kh??ng ???????c l??u!");
					break;
				default:
					System.err.println("L???a ch???n kh??ng h???p l???! Nh???p l???i!\n");
				}
			} catch (NumberFormatException e) {
				chose = -1;
				System.err.println("Sai ki???u d??? li???u! Vui l??ng nh???p s???!\n");
			} catch (NoSuchElementException e) {
				System.out.println("\nC???m ??n ???? d??ng ph???n m???m! T???m Bi???t...");
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
			System.out.println("L??u d??? li???u th??nh c??ng!");
		} else {
			System.err.println("L??u d??? li???u th???t b???i!\n");
		}
	}

	private static void showMenu1() {
		int chose = -1;
		do {
			try {
				System.out.println("     ______________________________________________");
				System.out.println("    |                     MENU 1                   |");
				System.out.println("    |          1. Qu???n l?? sinh vi??n.               |");
				System.out.println("    |          2. Hi???n th??? danh s??ch m??n.          |");
				System.out.println("    |          0. Quay l???i.                        |");
				System.out.println("    |______________________________________________|");
				System.out.print("L???a ch???n: ");

				chose = Integer.parseInt(sc.nextLine());

				switch (chose) {
				case 1:
					showMenu1_1();
					break;
				case 2:
					SubjectManagement.getInstance().showListSubject(subjects);
					break;
				case 0:
					System.out.println("[Quay l???i]");
					break;
				default:
					System.err.println("Nh???p sai! Vui l??ng nh???p l???i!\n");
					break;
				}
			} catch (NumberFormatException e) {
				chose = -1;
				System.err.println("Sai ki???u d??? li???u! Vui l??ng nh???p s???!\n");
			} catch (NoSuchElementException e) {
				System.out.println("\nC???m ??n ???? d??ng ph???n m???m! T???m Bi???t...");
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
				System.out.println("    |            1. Th??m sinh vi??n.                |");
				System.out.println("    |            2. S???a sinh vi??n.                 |");
				System.out.println("    |            3. X??a sinh vi??n.                 |");
				System.out.println("    |            4. Hi???n th??? danh s??ch.            |");
				System.out.println("    |            0. Quay l???i.                      |");
				System.out.println("    |______________________________________________|");
				System.out.print("L???a ch???n: ");

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
					System.out.println("[Quay l???i]");
					break;
				default:
					System.err.println("Nh???p sai! Vui l??ng nh???p l???i!\n");
					break;
				}
			} catch (NumberFormatException e) {
				chose = -1;
				System.err.println("Sai ki???u d??? li???u! Vui l??ng nh???p s???!\n");
			} catch (NoSuchElementException e) {
				System.out.println("\nC???m ??n ???? d??ng ph???n m???m! T???m Bi???t...");
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
				System.out.println("    |     1. T??m ki???m sinh vi??n theo m??.     |");
				System.out.println("    |     2. T??m ki???m sinh vi??n theo t??n.    |");
				System.out.println("    |     0. Quay l???i.                       |");
				System.out.println("    |________________________________________|");
				System.out.print("L???a ch???n: ");

				chose = Integer.parseInt(sc.nextLine());

				switch (chose) {
				case 1:
					SearchManagement.getInstance().searchStudentByID(students);
					break;
				case 2:
					SearchManagement.getInstance().searchStudentByName(students);
					break;
				case 0:
					System.out.println("[Quay l???i]");
					break;
				default:
					System.err.println("Nh???p sai! Vui l??ng nh???p l???i!\n");
					break;
				}
			} catch (NumberFormatException e) {
				chose = -1;
				System.err.println("Sai ki???u d??? li???u! Vui l??ng nh???p s???!\n");
			} catch (NoSuchElementException e) {
				System.out.println("\nC???m ??n ???? d??ng ph???n m???m! T???m Bi???t...");
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
