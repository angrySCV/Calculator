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
	private static String   statement;
	private static DataNode mainTreeRoot;

	public CalculatorImpl () {
	}

	@Override
	public String evaluate (String inputData) {
		try {
		statement = "0+0+" + inputData;
		// небольшой хак чтоб гарантированно правильно начать строить дерево,
		// что решает проблемы с возможным некоректным стартом алгоритма

			DataNode dataNode = new DataNode(parseData());
			// первое что делаю - парсю число, через метод parseData()

			getNextCommand(dataNode);
			// вызываю комманду парсинга оператора, и дальше рекурсивно опять вызываю построение нод
			// постепенно строю дерево из объектов (dataNode) -

			// каждый такой датаNоде состоит из 2х ссылк на 2 других дата нода, из одного поля с типом необходимой операцией,
			// и одного поля - result.
			// в качестве листьев дерева - используются теже самые датаNоды у которых ссылоки на другие датаNоды - null но есть значение result
			Double result = mainTreeRoot.getResult();

			// вызывая result для корневого элемента - (если результат в этом Nоде не известен) просходит его рекурсивное вычисление -
			// по всем связанным веткам
		DecimalFormat df = new DecimalFormat("#.####");
		// вывод в требуемом формате с округлением до 4х знаков после запятой
		return df.format(result);
		} catch (Exception e) {
			return "null"; // при некорректных данных возвращает null
		}
	}

	public char getNextCommand (DataNode leftNode) {
		DataNode rootDataNode;
		DataNode rightNode;
		DataNode saveEntryPoint = null;
		char operator = parseOperator();
		switch (operator) {
			case '(':
				// сюда алгоритм не заходит, перехватывают в другом месте
				// нужно сделать - очередь с приоритетами. но чую не успею нормально все реализовать - так что пока без глубокой раздачи приоритетов, со скобками может не вполне корректно работать
				if (hasNextOpenBracked()) {
					pointer++;
					rootDataNode = startNewNode();
					getNextCommand(rootDataNode);
				} else {
					rootDataNode = addingNodeTо(leftNode, operator);
					getNextCommand(rootDataNode);
				}
				break;
			case ')':
				mainTreeRoot = leftNode;
				break;
			case '*':
			case '/':
				if (hasNextOpenBracked()) {
					pointer++;
					saveEntryPoint = mainTreeRoot;
					rightNode = new DataNode(parseData());
					getNextCommand(rightNode);   // запустили парсить новое дерево с новым главным корнем
					mainTreeRoot = new DataNode(saveEntryPoint, operator, mainTreeRoot); // связываем корни старого и нового дерева
					if (hasNextOperator()) getNextCommand(mainTreeRoot);
				} else {
					rootDataNode = addingNodeTо(leftNode, operator);
					mainTreeRoot = changeTree(rootDataNode);
					getNextCommand(mainTreeRoot);
				}
				break;
			case '+':
			case '-':
				if (hasNextOpenBracked()) {
					pointer++;
					saveEntryPoint = mainTreeRoot;
					rightNode = new DataNode(parseData());
					getNextCommand(rightNode);   // запустили парситься новое дерево с новым главным корнем
					mainTreeRoot = new DataNode(saveEntryPoint, operator, mainTreeRoot);
					if (hasNextOperator()) getNextCommand(mainTreeRoot);
				} else {
					rootDataNode = addingNodeTо(leftNode, operator);
					mainTreeRoot = rootDataNode;
					getNextCommand(mainTreeRoot);
				}
				break;
			case 'e': //сигнал конеца строки
				closeNode();
				break;
			default:
				System.out.println("null"); //при некоректном операторе или комманде - выводим нул
		}
		return operator;
	}

	public DataNode startNewNode () {
		DataNode newDataNode = new DataNode(parseData());
		DataNode rootDataNode = new DataNode(newDataNode, getNextCommand(newDataNode), new DataNode(parseData()));
		return rootDataNode;
	}

	public void closeNode () {

	}

	public boolean hasNextOperator () {
		if (pointer < statement.length()) {
			switch (statement.charAt(pointer)) {
				case '+':
				case '-':
				case '*':
				case '/':
					return true;
				default:
					return false;
			}
		}
		return false;
	}

	public DataNode addingNodeTо (DataNode leftNode, char operator) {
		return new DataNode(leftNode, operator, new DataNode(parseData()));
	}

	public boolean hasNextOpenBracked () {
		if ((pointer < statement.length()) && (statement.charAt(pointer) == '(')) return true;
		else return false;
	}

	public double parseData () {
		// получение следующего числа из строки
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

	public char parseOperator () {
		//получение следующего оператора из строки
		char operator;
		if (pointer < statement.length()) {
			operator = statement.charAt(pointer);
			pointer++;
		} else operator = 'e';
		return operator;
	}

	public DataNode changeTree (DataNode mainTreeRoot) {
		//перевешиваем ветки дерева чтоб изменить очерёдность операций
		DataNode tempTree = mainTreeRoot.getLeftNode();
		mainTreeRoot.setLeftNode(mainTreeRoot.getLeftNode().getRightNode());
		tempTree.setRightNode(mainTreeRoot);
		return tempTree;
	}


}