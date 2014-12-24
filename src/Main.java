import com.tsystems.javaschool.tasks.Calculator;
import com.tsystems.javaschool.tasks.CalculatorImpl;

public class Main {

	public static void main (String[] args) {

		Calculator calculator = new CalculatorImpl();

		System.out.println(calculator.evaluate("7*6/2+8"));

	}

}
