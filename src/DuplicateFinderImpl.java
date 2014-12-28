import com.tsystems.javaschool.tasks.DuplicateFinder;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by AngrySCV on 28.12.14.
 * https://github.com/angrySCV
 */
public class DuplicateFinderImpl implements DuplicateFinder {
	ArrayList<String> words = new ArrayList<String>();

	@Override
	public boolean process (File sourceFile, File targetFile) {
		try {
			InputStream reader = new FileInputStream(sourceFile);
			Scanner sc = new Scanner(reader);
			while (sc.hasNext()) {
				String word = sc.nextLine();
				words.add(word);
			}
			Collections.sort(words);
			for (int i = 0; i < words.size(); i++) {
				System.out.println(words.get(i));
			}
			Writer writer = new FileWriter(targetFile, true);
			for (int i = 0; i < words.size(); i++) {
				writer.write(words.get(i) + "\n");
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
