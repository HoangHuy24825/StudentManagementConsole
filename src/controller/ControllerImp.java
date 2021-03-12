package controller;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class ControllerImp implements Controller {
	private static ControllerImp instance;

	private ControllerImp() {
	}

	public static ControllerImp getInstance() {
		if (instance == null) {
			instance = new ControllerImp();
		}
		return instance;
	}

	@Override
	public String changeUnicodeToEn(String str) {
		String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("").replace('đ', 'd').replace('Đ', 'D');
	}

}
