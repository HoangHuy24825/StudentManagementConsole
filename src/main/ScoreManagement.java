package main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.Score;
import model.Student;

public class ScoreManagement {
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private Scanner sc = new Scanner(System.in);
	private static ScoreManagement instance;

	public static ScoreManagement getInstance() {
		if (instance == null) {
			instance = new ScoreManagement();
		}
		return instance;
	}

	private ScoreManagement() {
	}

	public void showScoreOfStudent(Student student, ArrayList<Score> scores) {
		System.out.println("         __________________________________________________________________");
		System.out.format("        |  Mã: %-8s  | %-34s | %10s |\n", "SV" + String.format("%05d", student.getId()),
				student.getFirstName() + " " + student.getLastName(), sdf.format(student.getBirth()));
		System.out.println("       	|________________|____________________________________|____________|");
		System.out.format("        |  Điểm tổng kết                                        %-5.2f      |\n",
				calculateAverageScore(student, scores));
		System.out.println("        |__________________________________________________________________|");
		if (scores.contains(new Score(student.getId()))) {
			for (Score score : scores) {
				if (score.getIdStudent() == student.getId()) {
					score.showInforToTable();
				}
			}
		} else {
			System.out.println("        |  Sinh viên chưa có điểm môn nào                                  |");
		}
		System.out.println("        |__________________________________________________________________|");
	}

	public float calculateAverageScore(Student student, ArrayList<Score> scores) {
		float totalScore = 0;
		int numberSubject = 0;
		if (scores.contains(new Score(student.getId()))) {
			for (Score score : scores) {
				if (score.getIdStudent() == student.getId()) {
					numberSubject++;
					totalScore += score.getScore();
				}
			}
			return totalScore / numberSubject;
		}
		return 0;
	}

	public void showAllScoreByStudent(ArrayList<Score> scores, ArrayList<Student> students) {
		Collections.sort(students, (o1, o2) -> Integer.compare(o1.getId(), o2.getId()));
		int totalPage = 0;
		int currentPage = 1;
		int chose = 0;
		if (students.size() % 30 == 0) {
			totalPage = students.size() / 30;
		} else {
			totalPage = students.size() / 30 + 1;
		}
		if (students.size()==0) {
			System.err.println("Danh sách sinh viên trống!");
			return;
		}
		showListScorePageByStudent(currentPage, totalPage, students, scores);
		do {
			try {
				System.out.print("Lựa chọn: ");
				chose = Integer.parseInt(sc.nextLine());
				switch (chose) {
				case 1:
					if (++currentPage > totalPage) {
						showListScorePageByStudent(totalPage, totalPage, students, scores);
					} else {
						showListScorePageByStudent(currentPage, totalPage, students, scores);
					}
					break;
				case 2:
					if (--currentPage < 1) {
						showListScorePageByStudent((currentPage = 1), totalPage, students, scores);
					} else {
						showListScorePageByStudent(currentPage, totalPage, students, scores);
					}
					break;
				case 3:
					showListScorePageByStudent((currentPage = totalPage), totalPage, students, scores);
					break;
				case 4:
					showListScorePageByStudent((currentPage = 1), totalPage, students, scores);
					break;
				case 5:
					do {
						System.out.println("Nhập số trang cần xem.");
						System.out.println("Nhập exit để quay lại");
						System.out.print("Trang số: ");
						String inputPage = sc.nextLine();
						if (inputPage.equalsIgnoreCase("exit")) {
							System.out.println("[Quay lại]");
							showListScorePageByStudent(currentPage = 1, totalPage, students, scores);
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
						showListScorePageByStudent(currentPage, totalPage, students, scores);
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

	private void showListScorePageByStudent(int currentPage, int totalPage, ArrayList<Student> students,
			ArrayList<Score> scores) {
		int startIndex = 0;
		int endIndex = 0;
		if (currentPage != totalPage) {
			startIndex = (currentPage - 1) * 30;
			endIndex = currentPage * 30;
		} else {
			startIndex = (currentPage - 1) * 30;
			endIndex = students.size() % 30 + startIndex;
		}
		System.out.println("  =============================Danh sách điểm theo sinh viên=========================");
		System.out.format("      ================================Trang %d/%d================================\n",
				currentPage, totalPage);
		for (int i = startIndex; i < endIndex; i++) {
			showScoreOfStudent(students.get(i), scores);
		}
		System.out.format("      ================================Trang %d/%d================================\n",
				currentPage, totalPage);
		System.out.println("     1. Xem trang tiếp theo           3. Đến trang cuối          5. Xem trang cụ thể");
		System.out.println("     2. Trở lại trang trước           4. Đến trang đầu tiên      0. Trở về menu trước");
	}
}
