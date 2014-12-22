package com.tsystems.javaschool.tasks;

/**
 * Created by AngrySCV on 22.12.14.
 * пример калькулятора - ввод данных через вызов метода evaluate класса CalculatorImpl с передачей всех данных в строке
 * https://github.com/angrySCV
 */

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CalculatorImpl implements Calculator {
	ArrayList<DataNode> myData     = new ArrayList<DataNode>();
	private static int ukazatel = 0; // использую для парсера, как указатель на каком элементе сейчас находимся
	public CalculatorImpl () {
	}


	@Override
	public String evaluate (String statement) {

		// первое что делаю - парсю страку, через метод parseData()
		// на основе отпарсеных данных строю дерево из объектов (dataNode) -
		// каждый такой датаNоде состоит из 2х ссылк на 2 других дата нода, из одного поля с типом необходимой операцией,
		// и одного поля - result.
		// в качестве листьев дерева - используются датаNоды без ссылок на другие датаNоды только с значением result


		DataNode dataNode1 = new DataNode(parseData(statement));
		myData.add(dataNode1);
		while (ukazatel < statement.length()) {
			char operator = parseOperator(statement);
			DataNode dataNode2 = new DataNode(parseData(statement));
			myData.add(dataNode2);
			DataNode rootDataNode = new DataNode(dataNode1, operator, dataNode2);
			myData.add(rootDataNode);
			dataNode1 = rootDataNode;
		}
		// вызывая result для корневого элемента - (если результат не известен) просходит его рекурсивное вычисление -
		// по всем связанным веткам

Double result = myData.get(myData.size() - 1).getResult();

// вывод в требуемом формате с округлением до 4х знаков после запятой
		DecimalFormat df = new DecimalFormat("#.####");
		return df.format(result);
	}

	private double parseData (String statement) {
		// парсим строку
		int dataCount = 0;
		int dataStart = ukazatel;
		Double myNum = 0.0;
		boolean isData = false; // флаг - который использую для проверки и отделения последовательности чисел от других символов
		while (ukazatel < statement.length()) {
			// отбираем значения чисел
			if ((statement.charAt(ukazatel) > 46 & statement.charAt(ukazatel) < 58) || (statement.charAt(ukazatel) == '.')) {
				if (isData == false) {
					isData = true;
					dataStart = ukazatel;
				} dataCount++; // считаем количество десятичных чисел в значении
			} else {
				// добавляем из строки найденное число к обьекту типа дабл
				myNum = Double.parseDouble(statement.substring(dataStart, dataStart + dataCount));
				dataCount = 0;
				isData = false;
				dataStart = ukazatel;
				return myNum;
			}
			ukazatel++;
		}
		if (dataCount > 0) myNum = Double.parseDouble(statement.substring(dataStart, dataStart + dataCount));
		return myNum;
	}

	private char parseOperator (String statement) {
		char operator = statement.charAt(ukazatel);
		ukazatel++;
		return operator;
	}


}