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
		// считываю строки
		try {
			InputStream reader = new FileInputStream(sourceFile);
			Scanner sc = new Scanner(reader);
			while (sc.hasNext()) {
				String word = sc.nextLine();
				words.add(word);
			}
			// сортирую строки
			Collections.sort(words);
			Writer writer = new FileWriter(targetFile, true);
			// считаю сколько раз подряд идущие строки повторяется, сама строка уже и есть 1 повтор
			int povtor = 1;
			int kolichestvoStrok = words.size();
			for (int i = 0; i < kolichestvoStrok - 1; i++) {
				if (words.get(i).equals(words.get(i + 1))) {
					povtor++;
				} else {
					//если строка не повторилясь, и вывожу эту строку вместе с количеством повторов
					writer.write(words.get(i) + " [" + povtor + "]" + "\n");
					povtor = 1; //обнуляю счетчик повторов (первый элемент всегда еденица)
				}
			}
			//отдельно проверяю последнюю строку
			if (!words.get(kolichestvoStrok - 1).equals(words.get(kolichestvoStrok - 2))) {
				writer.write(words.get(kolichestvoStrok - 1) + " [1]" + "\n");
			} else writer.write(words.get(kolichestvoStrok - 1) + " [" + povtor + "]" + "\n");
			// записываю результат из буфера
			writer.flush();
			writer.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
