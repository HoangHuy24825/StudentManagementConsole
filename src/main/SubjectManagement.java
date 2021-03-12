package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.Subject;

public class SubjectManagement {

	private static SubjectManagement instance;
	private Scanner sc = new Scanner(System.in);

	public static SubjectManagement getInstance() {
		if (instance == null) {
			instance = new SubjectManagement();
		}
		return instance;
	}

	private SubjectManagement() {
	}

	public Subject getSubjectByID(int idSubject, ArrayList<Subject> subjects) {
		for (Subject subject : subjects) {
			if (subject.getIdSubject() == idSubject) {
				return subject;
			}
		}
		return null;
	}

	public void showListSubject(ArrayList<Subject> subjects) {
		Collections.sort(subjects);
		int totalPage = 0;
		int currentPage = 1;
		int chose = 0;
		if (subjects.size() % 30 == 0) {
			totalPage = subjects.size() / 30;
		} else {
			totalPage = subjects.size() / 30 + 1;
		}
		if (subjects.size() == 0) {
			System.err.println("Danh sách môn học trống!");
			return;
		}
		showSubjectPage(currentPage, totalPage, subjects);
		do {
			try {
				System.out.print("Lựa chọn: ");
				chose = Integer.parseInt(sc.nextLine());
				switch (chose) {
				case 1:
					if (++currentPage > totalPage) {
						showSubjectPage(totalPage, totalPage, subjects);
					} else {
						showSubjectPage(currentPage, totalPage, subjects);
					}
					break;
				case 2:
					if (--currentPage < 1) {
						showSubjectPage((currentPage = 1), totalPage, subjects);
					} else {
						showSubjectPage(currentPage, totalPage, subjects);
					}
					break;
				case 3:
					showSubjectPage((currentPage = totalPage), totalPage, subjects);
					break;
				case 4:
					showSubjectPage((currentPage = 1), totalPage, subjects);
					break;
				case 5:
					do {
						System.out.println("Nhập số trang cần xem.");
						System.out.println("Nhập exit để quay lại");
						System.out.print("Trang số: ");
						String inputPage = sc.nextLine();
						if (inputPage.equalsIgnoreCase("exit")) {
							System.out.println("[Quay lại]");
							showSubjectPage((currentPage = 1), totalPage, subjects);
							break;
						}
						try {
							currentPage = Integer.parseInt(inputPage);
						} catch (Exception e) {
							System.err.println("Sai kiểu dữ liệu. Vui lòng nhập số!\n");
							System.out.println("Nhập lại");
							currentPage = -1;
							continue;
						}
						if (currentPage > totalPage) {
							System.err.println("Trang cần xem vượt quá số trang đang có!\n");
							System.out.println("Nhập lại!");
							continue;
						}
						if (currentPage < 1) {
							System.err.println("Số trang cần xem phải lớn hơn 0!\n");
							System.out.println("Nhập lại!");
							continue;
						}
						showSubjectPage(currentPage, totalPage, subjects);
					} while (currentPage > totalPage || currentPage < 0);
					break;
				case 0:
					System.out.println("[Quay lại]");
					break;
				default:
					System.err.println("Lựa chọn không hợp lệ!\n");
					System.err.println("Nhập lại!");
					break;
				}
			} catch (NumberFormatException e) {
				System.err.println("Sai kiểu dữ liệu. Vui lòng nhập số!\n");
				System.out.println("Nhập lại");
				chose = -1;
			} catch (NoSuchElementException e) {
				System.out.println("\nCảm ơn đã dùng phần mềm! Tạm Biệt...");
				System.exit(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (chose != 0);
	}

	private void showSubjectPage(int currentPage, int totalPage, ArrayList<Subject> subjects) {
		int startIndex = 0;
		int endIndex = 0;
		if (currentPage == totalPage) {
			startIndex = (currentPage - 1) * 30;
			endIndex = subjects.size() % 30 + startIndex;
		} else {
			startIndex = (currentPage - 1) * 30;
			endIndex = currentPage * 30;
		}
		System.out.println("      =============================Danh sách môn học========================");
		System.out.format("           ===========================Trang %d/%d========================\n", currentPage,
				totalPage);
		for (int i = 0; i < 58; i++) {
			if (i == 0) {
				System.out.print("            _");
			} else {
				System.out.print("_");
			}
		}
		System.out.println();
		System.out.format("           | %10s | %30s | %10s |\n", "Mã    ", "Tên môn học           ", "Hệ số  ");
		for (int i = 0; i <= 59; i++) {
			if (i == 0) {
				System.out.print("           |");
			} else if (i == 59 || i == 13 || i == 46) {
				System.out.print("|");
			} else {
				System.out.print("_");
			}
		}
		System.out.println();
		for (int i = startIndex; i < endIndex; i++) {
			subjects.get(i).showInforToTable();
		}
		for (int i = 0; i <= 59; i++) {
			if (i == 0) {
				System.out.print("           |");
			} else if (i == 59 || i == 13 || i == 46) {
				System.out.print("|");
			} else {
				System.out.print("_");
			}
		}
		System.out.println();
		System.out.format("           ===========================Trang %d/%d========================\n", currentPage,
				totalPage);
		System.out.println("            1. Xem trang tiếp theo      3. Đến trang cuối       5. Xem trang cụ thể");
		System.out.println("            2. Trở lại trang trước      4. Đến trang đầu tiên   0. Trở về menu trước");

	}
}
