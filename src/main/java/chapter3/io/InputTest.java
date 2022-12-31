package main.java.chapter3.io;

import java.io.*;

public class InputTest {
	public static void main(String[] args) throws IOException {
		int c;

		try (InputStream in2 = 
				new LowerCaseInputStream(
					new BufferedInputStream(
						new FileInputStream("test.txt")))) 
		{
			while((c = in2.read()) >= 0) {
				System.out.print((char)c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
