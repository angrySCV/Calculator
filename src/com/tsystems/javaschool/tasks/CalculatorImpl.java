package com.tsystems.javaschool.tasks;

/**
 * Created by AngrySCV on 22.12.14.
 * пример калькулятора - ввод данных через вызов метода evaluate класса CalculatorImpl с передачей всех данных в строке
 * https://github.com/angrySCV
 */

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CalculatorImpl implements Calculator {
	ArrayList<DataNode> allMyData = new ArrayList<DataNode>();
	private static int pointer = 0; // использую для парсера, как указатель на каком элементе сейчас находимся
	private static char operator;
	public CalculatorImpl () {
	}
	@Override
	public String evaluate (String statement) {

		// первое что делаю - парсю страку, через метод parseData()
		// на основе отпарсеных данных строю дерево из объектов (dataNode) -
		// каждый такой датаNоде состоит из 2х ссылк на 2 других дата нода, из одного поля с типом необходимой операцией,
		// и одного поля - result.
		// в качестве листьев дерева - используются датаNоды без ссылок на другие датаNоды только с значением result
		DataNode dataNode1;
		DataNode dataNode2;
		DataNode rootDataNode;
		DataNode saveEntryPoint = null;
		statement = "0+0+" + statement; // небольшой хак чтоб гарантированно правильно начать строить дерево, что решает много проблем с возможным некоректным стартом алгоритма.
		dataNode1 = new DataNode(parseData(statement));
		allMyData.add(dataNode1);
		parseOperator(statement);
		while (pointer < statement.length()) {
			if (statement.charAt(pointer) == '(') {
				// если встречаем открывающиеся скобки - начинаем строить новое дерево, найдя закрывающую скобку
				// подвесим это дерево к старому.
				pointer++;
				saveEntryPoint = dataNode1; // сохраняем месту куда потом вставим новую ветку дерева
				DataNode startNewNode = new DataNode(parseData(statement)); // начало новой независимой ветки дерева
				parseOperator(statement); // оператор в новом дереве
				dataNode1 = startNewNode; // после этого в основном теле цикла дерево будет строится от этого нового элемента
			}
			if ((operator == ')') & !(saveEntryPoint == null)) {
				// заканчили строить новое дерево - подвесим это дерево к старому на точке входа в новое.
				saveEntryPoint.setRightNode(dataNode1);
				dataNode1 = saveEntryPoint; // продолжаем работать со старым деревом
				if (pointer < statement.length()) parseOperator(statement);
			}
			dataNode2 = new DataNode(parseData(statement));
			allMyData.add(dataNode2);
			rootDataNode = new DataNode(dataNode1, operator, dataNode2);
			allMyData.add(rootDataNode);
			if (operator == '*' || operator == '/') { // перевешиваем узел с умножением чтоб он отдельно выполнился
				rootDataNode.setLeftNode(dataNode1.getRightNode());
				dataNode1.setRightNode(rootDataNode);
			} else dataNode1 = rootDataNode;
//			upNodePriority(); // чтоб изменить приоритет операции перевешиваем Nodu
			parseOperator(statement);
		}
		// вызывая result для корневого элемента - (если результат не известен) просходит его рекурсивное вычисление -
		// по всем связанным веткам
		Double result = allMyData.get(allMyData.size() - 1).getResult();

// вывод в требуемом формате с округлением до 4х знаков после запятой
		DecimalFormat df = new DecimalFormat("#.####");
		return df.format(result);
	}

	private double parseData (String statement) {
		// парсим строку
		int dataCount = 0;
		int dataStart = pointer;
		Double myNum = 0.0;
		boolean isData = false; // флаг - который использую для проверки и отделения последовательности чисел от других символов
		while (pointer < statement.length()) {
			// отбираем значения чисел
			if ((statement.charAt(pointer) > 47 & statement.charAt(pointer) < 58) || (statement.charAt(pointer) == '.')) {
				if (isData == false) {
					isData = true;
					dataStart = pointer;
				} dataCount++; // считаем количество десятичных чисел в значении
			} else {
				// добавляем из строки найденное число к обьекту типа дабл
				myNum = Double.parseDouble(statement.substring(dataStart, dataStart + dataCount));
				dataCount = 0;
				isData = false;
				dataStart = pointer;
				return myNum;
			}
			pointer++;
		}
		if (dataCount > 0) myNum = Double.parseDouble(statement.substring(dataStart, dataStart + dataCount));
		return myNum;
	}

	private void parseOperator (String statement) {
		if (pointer<statement.length()) {
		operator = statement.charAt(pointer);
		pointer++;
		}
	}

	private void upNodePriority () {

	}


}