package model;

import controller.ControllerImp;

public class Subject implements Comparable<Subject> {
	public static int maxID;
	private int idSubject;
	private String name;
	private float coefficient;

	public Subject(String name, float coefficient) {
		super();
		this.idSubject = ++maxID;
		this.name = name;
		this.coefficient = coefficient;
	}

	public Subject() {
		this.idSubject = ++maxID;
	}

	public int getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(int idSubject) {
		this.idSubject = idSubject;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(float coefficient) {
		this.coefficient = coefficient;
	}

	@Override
	public String toString() {
		return String.format("%03d", idSubject) + ";" + name + ";" + coefficient;
	}

	public static String getTitleToSave() {
		return "#Ma ; Ten mon hoc; he so diem";
	}

	@Override
	public int compareTo(Subject o) {
		return ControllerImp.getInstance().changeUnicodeToEn(name)
				.compareTo(ControllerImp.getInstance().changeUnicodeToEn(o.getName()));
	}

	public void showInforToTable() {
		System.out.format("           |     %-6s |   %-28s |     %-6.1f |\n", String.format("%03d", idSubject), name,
				coefficient);
	}

	public static Subject createSubjectFromText(String row) {
		if (row != null) {
			Subject subject = new Subject();
			String[] arrStr = row.split(";");
			subject.setIdSubject(Integer.parseInt(arrStr[0]));
			subject.setName(arrStr[1]);
			subject.setCoefficient(Float.parseFloat(arrStr[2]));
			return subject;
		}
		return null;
	}

}
