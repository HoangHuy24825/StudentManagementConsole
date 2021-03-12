package model;

import main.Main;
import main.SubjectManagement;

public class Score implements Comparable<Score>{
	private int idStudent;
	private int idSubject;
	private float score;

	public Score(int idStudent, int idSubject, float score) {
		super();
		this.idStudent = idStudent;
		this.idStudent = idStudent;
		this.score = score;
	}

	public Score() {
	}
	
	public Score(int idStudent) {
		this.idStudent=idStudent;
	}

	public int getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(int idStudent) {
		this.idStudent = idStudent;
	}

	public int getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(int idSubject) {
		this.idSubject = idSubject;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "SV" + String.format("%05d", idStudent) + ";" + String.format("%03d", idSubject) + ";" + score;
	}

	public static Score createScoreFromText(String row) {
		if (row != null) {
			Score score = new Score();
			String[] arrStr = row.split(";");
			score.setIdStudent(Integer.parseInt(arrStr[0].substring(2)));
			score.setIdSubject(Integer.parseInt(arrStr[1]));
			score.setScore(Float.parseFloat(arrStr[2]));
			return score;
		}
		return null;
	}

	public static String getTitleToSave() {
		return "# ma sinh vien; ma mon hoc; diem";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idStudent;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Score other = (Score) obj;
		if (idStudent != other.idStudent)
			return false;
		return true;
	}

	public void showInforToTable() {
		System.out.format("        |  %-5s     %-30s             %-5.2f      |\n",String.format("%03d", idSubject),
				SubjectManagement.getInstance().getSubjectByID(idSubject, Main.subjects).getName(),score);
	}

	@Override
	public int compareTo(Score o) {
		return Integer.compare(idStudent, o.getIdStudent());
	}
}
