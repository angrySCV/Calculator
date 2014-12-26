import com.tsystems.javaschool.tasks.Calculator;
import com.tsystems.javaschool.tasks.CalculatorImpl;

public class Main {

	public static void main (String[] args) {

		Calculator calculator = new CalculatorImpl();

		System.out.println(calculator.evaluate("(1+38)*4-5"));
// простая проверка примера - ожидаемый результат 151
	}

}
