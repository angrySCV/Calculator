import com.tsystems.javaschool.tasks.Calculator;
import com.tsystems.javaschool.tasks.CalculatorImpl;

public class Main {

	public static void main (String[] args) {

		Calculator calculator = new CalculatorImpl();

		System.out.println(calculator.evaluate("28394.2+475.5-300*100*3+38485.9666666/200*500.3+345.3"));


	}

}
