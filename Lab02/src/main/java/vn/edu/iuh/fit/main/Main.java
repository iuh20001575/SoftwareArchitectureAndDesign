package vn.edu.iuh.fit.main;

import vn.edu.iuh.fit.parser.Account;

import java.io.File;

public class Main {

	public static void main(String[] args) throws Exception {
//		File
//		File file = new File("src/main/java/vn/edu/iuh/fit/parser/Account.java");
//
//		System.out.println(Parser.parserFile(file));

//		Folder
//		File folder = new File("src/main/java/vn/edu/iuh/fit/parser");
//
//		Parser.parserFolder(folder).forEach(System.out::println);

//		Reflection
		System.out.println(Parser.reflection(Account.class));
	};
}
