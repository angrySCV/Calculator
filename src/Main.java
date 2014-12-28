import com.tsystems.javaschool.tasks.Subsequence;

import java.util.Arrays;

public class Main {

	public static void main (String[] args) {

//		Calculator calculator = new CalculatorImpl();

//		System.out.println(calculator.evaluate("(1+38)*4-5"));
        // простая проверка примера - ожидаемый результат 151

		Subsequence s = new SubsequenceImpl();
		boolean b = s.find(Arrays.asList("A", "B", "C", "D"), Arrays.asList("BD", "A", "ABC", "B", "M", "D", "M", "C", "DC", "D"));
		System.out.println(b); // ожидаемый результат: true
	}

}
