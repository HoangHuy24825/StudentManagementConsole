package main;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import controller.ControllerImp;
import model.Student;

public class SearchManagement {
	private static SearchManagement instance;
	private Scanner sc = new Scanner(System.in);

	public static SearchManagement getInstance() {
		if (instance == null) {
			instance = new SearchManagement();
		}
		return instance;
	}

	private SearchManagement() {
	}

	public void searchStudentByID(ArrayList<Student> students) {
		try {
			String idStr = "";
			do {
				System.out.println("Nhập mã sinh viên cần xem: ");
				System.out.println("Nhập exit để quay lại.");
				System.out.print("Mã sinh viên: ");
				idStr = sc.nextLine().trim();
				if (idStr.equalsIgnoreCase("")) {
					System.err.println("Mã sinh viên không được rỗng!\n");
					continue;
				}
				if (idStr.equalsIgnoreCase("exit")) {
					System.out.println("[Quay lại]");
					return;
				}
				if (idStr.length() <= 2) {
					System.err.println("Mã sinh viên không tồn tại\n");
					return;
				}
				int id;
				try {
					id = Integer.parseInt(idStr.substring(2));
				} catch (NumberFormatException e) {
					System.err.println("Mã sinh viên không tồn tại!\n");
					return;
				}
				Student student = StudentManagement.getInstance().checkExistStudent(id, students);
				if (student == null) {
					System.err.println("Sinh viên không tồn tại!\n");
					return;
				} else {
					ScoreManagement.getInstance().showScoreOfStudent(student, Main.scores);
					String input = "";
					do {
						System.out.println("Nhập back để quay lại: ");
						input = sc.nextLine();
						if (input.equalsIgnoreCase("back"))
							System.out.println("[Quay lại]");
					} while (!input.equalsIgnoreCase("back"));
				}
			} while (idStr.equalsIgnoreCase(""));
		} catch (NoSuchElementException e) {
			System.out.println("\nCảm ơn đã dùng phần mềm! Tạm Biệt...");
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void searchStudentByName(ArrayList<Student> students) {
		try {
			System.out.println("Nhập từ khóa tìm kiếm");
			System.out.println("Nhập back để quay lại");
			System.out.print("Từ khóa tìm kiếm: ");
			String keySearch = sc.nextLine();
			if (keySearch.equalsIgnoreCase("back")) {
				System.out.println("[Quay lại]");
				return;
			}
			ArrayList<Student> listResult = new ArrayList<Student>();
			keySearch = ControllerImp.getInstance().changeUnicodeToEn(keySearch.toLowerCase());
			for (Student student : students) {
				if (student.getNameAfterChangeToEn().contains(keySearch)) {
					listResult.add(student);
				}
			}
			if (listResult.size() == 0) {
				System.out.println("Không tìm thấy sinh viên có tên: " + keySearch);
				return;
			} else {
				StudentManagement.getInstance().showListStudent(listResult);
			}
		} catch (NoSuchElementException e) {
			System.out.println("Cảm ơn đã dùng phần mềm! Tạm Biệt...");
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
