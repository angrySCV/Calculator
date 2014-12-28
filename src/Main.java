import com.tsystems.javaschool.tasks.*;

import java.io.File;
import java.util.Arrays;

public class Main {

	public static void main (String[] args) {

		System.out.println("-----   задача 1    -------");
		Calculator c = new CalculatorImpl();
		System.out.println(c.evaluate("(1+38)*4-5")); // Результат: 151
		c = new CalculatorImpl();
		System.out.println(c.evaluate("7*6/2+8")); // Результат: 29
		c = new CalculatorImpl();
		System.out.println(c.evaluate("-12)1//(")); // Результат: null
        // простая проверка примера - ожидаемый результат 151

		System.out.println("-----   задача 2    -------");
		Subsequence s = new SubsequenceImpl();
		boolean b = s.find(Arrays.asList("A", "B", "C", "D"), Arrays.asList("BD", "A", "ABC", "B", "M", "D", "M", "C", "DC", "D"));
		System.out.println(b); // ожидаемый результат: true
		System.out.println("-----   задача 3    -------");
		DuplicateFinder d = new DuplicateFinderImpl();
		d.process(new File("a.txt"), new File("b.txt"));
	}

}
