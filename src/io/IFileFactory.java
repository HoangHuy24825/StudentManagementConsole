package io;

import java.util.ArrayList;

public interface IFileFactory {
	<T> ArrayList<T> readFile(String path);
}
