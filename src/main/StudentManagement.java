package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;

import io.FileFactory;
import model.Score;
import model.Student;

public class StudentManagement {
	private static StudentManagement instance;
	private Scanner sc = new Scanner(System.in);
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private StudentManagement() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static StudentManagement getInstance() {
		if (instance == null) {
			instance = new StudentManagement();
		}
		return StudentManagement.instance;
	}

	public void addStudent(ArrayList<Student> students) {
		Student student = new Student();
		boolean isChecked;
		String dataEnter = "";
		try {
			do {
				isChecked = true;
				System.out.println("\nNhập thông tin sinh viên theo dạng: ");
				System.out.println("[Họ và tên];[Ngày sinh (dd/MM/yyyy)];[Giới tính (Nam/nữ)]");
				System.out.println("Ví dụ: Nguyễn Văn A;14/06/2000;Nam");
				System.out.println("Nhập exit để quay lại");
				System.out.print("Nhập: ");

				dataEnter = sc.nextLine().trim();
				if (dataEnter.equalsIgnoreCase("exit")) {
					System.out.println("[Quay lại]");
					return;
				}
				if (dataEnter.length() == 0) {
					System.err.println("Dữ liệu vào không được rỗng!\n");
					System.out.println("Nhập lại thông tin sinh viên!");
					isChecked = false;
					continue;
				}

				String[] dataEnters = dataEnter.split(";");
				if (dataEnters.length != 3) {
					System.err.println("Dữ liệu vào thiếu thông tin!\n");
					System.out.println("Nhập lại thông tin sinh viên!");
					isChecked = false;
					continue;
				}

				String[] names = dataEnters[0].trim().split(" ");
				if (names.length < 2) {
					System.err.println("Tên phải bao gồm họ đệm và tên!\n");
					System.out.println("Nhập lại thông tin sinh viên!");
					isChecked = false;
					continue;
				}
				int indexOfLastSpace = dataEnters[0].lastIndexOf(" ");
				student.setFirstName(dataEnters[0].trim().substring(0, indexOfLastSpace));
				student.setLastName(dataEnters[0].trim().substring(indexOfLastSpace + 1));

				try {
					student.setBirth(sdf.parse(dataEnters[1].trim()));
				} catch (ParseException e) {
					System.err.println("Ngày sinh sai dịnh dạng!\n");
					System.out.println("Nhập lại đúng định dạng thông tin sinh viên!");
					isChecked = false;
					continue;
				}

				if (!dataEnters[2].trim().equalsIgnoreCase("Nam") && !dataEnters[2].trim().equalsIgnoreCase("Nữ")) {
					System.err.println("Giới tính phải là Nam hoặc Nữ!\n");
					System.out.println("Nhập lại thông tin sinh viên!");
					isChecked = false;
					continue;
				}
				student.setGender(dataEnters[2].trim());

				if (isChecked) {
					students.add(student);
					FileFactory.getInstance().saveAllData();
					System.out.println("Sinh viên được thêm thành công. Thông tin sinh viên vừa thêm: ");
					student.showInfor();
				}
			} while (dataEnter.length() == 0 || !isChecked);
		} catch (NoSuchElementException e) {
			System.out.println("\nCảm ơn đã dùng phần mềm! Tạm Biệt...");
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void editStudent(ArrayList<Student> students) {
		try {
			String idStr = "";

			do {
				System.out.println("Nhập mã sinh viên cần sửa: ");
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
					System.err.println("Mã sinh viên không tồn tại!\n");
					return;
				}
				int id;
				try {
					id = Integer.parseInt(idStr.substring(2));
				} catch (NumberFormatException e) {
					System.err.println("Mã sinh viên không tồn tại!\n");
					return;
				}
				Student student = checkExistStudent(id, students);
				if (student == null) {
					System.err.println("Sinh viên không tồn tại!\n");
					return;
				} else {
					System.out.println("Tìm thấy sinh viên: ");
					student.showInfor();
					boolean isChecked;
					do {
						isChecked = true;
						System.out.println("\nNhập thông tin sinh viên cần sửa theo dạng: ");
						System.out.println("[Họ và tên];[Ngày sinh (dd/MM/yyyy)];[Giới tính (Nam/nữ)]");
						System.out.println("Nếu không muốn sửa mục nào thì bỏ qua mục đó");
						System.out.println("Ví dụ: Nguyễn Văn A;;Nam");
						System.out.println("Nhập exit để quay lại");
						System.out.print("Nhập: ");
						String dataEnter = sc.nextLine().trim();
						if (dataEnter.equalsIgnoreCase("exit")) {
							System.out.println("[Quay lại]");
							return;
						}
						if (dataEnter.length() == 0) {
							System.out.println("Thông tin sinh viên sau khi sửa");
							student.showInfor();
							return;
						}
						if (!dataEnter.contains(";")) {
							System.err.println("Nhập thông tin sai định dạng!\n");
							System.out.println("Nhập lại thông tin sinh viên cẩn sửa!");
							isChecked = false;
							continue;
						}
						int firstIndexOfSemicolon = dataEnter.indexOf(";");
						int lastIndexOfSemicolon = dataEnter.lastIndexOf(";");
						String name = dataEnter.substring(0, firstIndexOfSemicolon);
						String birth = dataEnter.substring(firstIndexOfSemicolon + 1, lastIndexOfSemicolon);
						String gender = dataEnter.substring(lastIndexOfSemicolon + 1);
						if (name.length() != 0) {
							String[] names = name.trim().split(" ");
							if (names.length < 2) {
								System.err.println("Tên phải bao gồm họ đệm và tên!\n");
								System.out.println("Nhập lại thông tin sinh viên!");
								isChecked = false;
								continue;
							}
							int indexOfLastSpace = name.lastIndexOf(" ");
							student.setFirstName(name.trim().substring(0, indexOfLastSpace));
							student.setLastName(name.trim().substring(indexOfLastSpace + 1));
						}
						if (birth.length() != 0) {
							try {
								student.setBirth(sdf.parse(birth.trim()));
							} catch (ParseException e) {
								System.err.println("Ngày sinh sai dịnh dạng!\n");
								System.out.println("Nhập lại đúng định dạng thông tin sinh viên!");
								isChecked = false;
								continue;
							}
						}
						if (gender.length() != 0) {
							if (!gender.trim().equalsIgnoreCase("Nam") && !gender.trim().equalsIgnoreCase("Nữ")) {
								System.err.println("Giới tính phải là Nam hoặc Nữ!\n");
								System.out.println("Nhập lại thông tin sinh viên!");
								isChecked = false;
								continue;
							}
							student.setGender(gender.trim());
						}
						if (isChecked) {
							System.out.println("Sửa thành công!");
							FileFactory.getInstance().saveAllData();
							System.out.println("Thông tin sinh viên sau khi sửa: ");
							student.showInfor();
						}
					} while (!isChecked);
				}
			} while (idStr.equalsIgnoreCase(""));

		} catch (NoSuchElementException e) {
			System.out.println("\nCảm ơn đã dùng phần mềm! Tạm Biệt...");
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Student checkExistStudent(int id, ArrayList<Student> students) {
		for (Student student1 : students) {
			if (id == student1.getId()) {
				return student1;
			}
		}
		return null;
	}

	public void showListStudent(ArrayList<Student> students) {
		Collections.sort(students);
		int totalPage = 0;
		int currentPage = 1;
		int chose = 0;
		if (students.size() % 30 == 0) {
			totalPage = students.size() / 30;
		} else {
			totalPage = students.size() / 30 + 1;
		}
		if (students.size()==0) {
			System.out.println("Danh sách sinh viên trống!");
			return;
		}
		showListStudentPage(currentPage, totalPage, students);
		do {
			try {
				System.out.print("Lựa chọn: ");
				chose = Integer.parseInt(sc.nextLine());
				switch (chose) {
				case 1:
					if (++currentPage > totalPage) {
						showListStudentPage(totalPage, totalPage, students);
					} else {
						showListStudentPage(currentPage, totalPage, students);
					}
					break;
				case 2:
					if (--currentPage < 1) {
						showListStudentPage((currentPage = 1), totalPage, students);
					} else {
						showListStudentPage(currentPage, totalPage, students);
					}
					break;
				case 3:
					showListStudentPage((currentPage = totalPage), totalPage, students);
					break;
				case 4:
					showListStudentPage((currentPage = 1), totalPage, students);
					break;
				case 5:
					do {
						System.out.println("Nhập số trang cần xem.");
						System.out.println("Nhập exit để quay lại");
						System.out.print("Trang số: ");
						String inputPage = sc.nextLine();
						if (inputPage.equalsIgnoreCase("exit")) {
							System.out.println("[Quay lại]");
							showListStudentPage(currentPage = 1, totalPage, students);
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
						showListStudentPage(currentPage, totalPage, students);
					} while (currentPage > totalPage || currentPage < 0);
					break;
				case 6:
					SearchManagement.getInstance().searchStudentByID(students);
					showListStudentPage(currentPage, totalPage, students);
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

	private void showListStudentPage(int currentPage, int totalPage, ArrayList<Student> students) {
		int startIndex = 0;
		int endIndex = 0;
		if (currentPage != totalPage) {
			startIndex = (currentPage - 1) * 30;
			endIndex = currentPage * 30;
		} else {
			startIndex = (currentPage - 1) * 30;
			endIndex = students.size() % 30 + startIndex;
		}
		System.out.println(
				"      =============================================Danh sách sinh viên======================================");
		System.out.format(
				"\n           ==========================================Trang %d/%d==========================================\n",
				currentPage, totalPage);
		for (int i = 0; i < 94; i++) {
			if (i == 0) {
				System.out.print("           _");
			} else {
				System.out.print("_");
			}
		}
		System.out.println();
		System.out.format("          | %10s | %35s | %15s | %10s | %10s |\n", "Mã    ", "Họ đệm               ",
				"Tên    ", "Ngày sinh", "Giới tính");
		for (int i = 0; i <= 95; i++) {
			if (i == 0) {
				System.out.print("          |");
			} else if (i == 95 || i == 13 || i == 51 || i == 69 || i == 82) {
				System.out.print("|");
			} else {
				System.out.print("_");
			}
		}
		System.out.println();
		for (int i = startIndex; i < endIndex; i++) {
			students.get(i).showInforToTable();
		}
		for (int i = 0; i <= 95; i++) {
			if (i == 0) {
				System.out.print("          |");
			} else if (i == 95 || i == 13 || i == 51 || i == 69 || i == 82) {
				System.out.print("|");
			} else {
				System.out.print("_");
			}
		}

		System.out.format(
				"\n           ==========================================Trang %d/%d==========================================\n",
				currentPage, totalPage);
		System.out
				.println("            1. Xem trang tiếp theo           3. Đến trang cuối          5. Xem trang cụ thể");
		System.out.println(
				"            2. Trở lại trang trước           4. Đến trang đầu tiên      0. Trở về menu trước");
		System.out.println("            6. Xem chi tiết bảng điểm");
	}

	public void deleteStudent(ArrayList<Student> students) {
		try {
			String idStr = "";
			do {
				System.out.println("Nhập mã sinh viên muốn xóa ");
				System.out.println("Nhập exit để quay lại.");
				System.out.print("Mã sinh viên: ");
				idStr = sc.nextLine().trim();
				if (idStr.equalsIgnoreCase("")) {
					System.err.println("Mã sinh viên không được rỗng!\n");
					continue;
				}
				if (idStr.equalsIgnoreCase("exit")) {
					System.err.println("[Quay lại]");
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
				Student student = checkExistStudent(id, students);
				if (student == null) {
					System.err.println("Sinh viên không tồn tại!\n");
					return;
				} else {
					System.out.println("Tìm thấy sinh viên: ");
					student.showInfor();
					int chose = 0;
					do {
						System.out.println("     ____________________________________________________");
						System.out.println("    |  Bạn có thực sự muốn xóa sinh viên có mã SV"
								+ String.format("%05d", student.getId()) + "?  |");
						System.out.println("    |        1. Có            2. Không                   |");
						System.out.println("    |____________________________________________________|");
						try {
							System.out.print("Lựa chọn: ");
							chose = Integer.parseInt(sc.nextLine());
						} catch (NumberFormatException e) {
							System.err.println("Vui lòng nhập số!\n");
							chose = -1;
							continue;
						}
						if (chose == 1) {
							if (!Main.scores.contains(new Score(student.getId()))) {
								students.remove(student);
								System.out.println("xóa thành công!");
								FileFactory.getInstance().saveAllData();
							} else {
								System.err.println("Sinh viên đã có điểm! Không thể xóa.\n");
								return;
							}
						} else {
							return;
						}
					} while (chose != 1 && chose != 2);
				}
			} while (idStr.equalsIgnoreCase(""));
			;
		} catch (NoSuchElementException e) {
			System.out.println("\nCảm ơn đã dùng phần mềm! Tạm Biệt...");
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
