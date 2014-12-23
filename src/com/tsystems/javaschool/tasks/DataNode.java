package com.tsystems.javaschool.tasks;

/**
 * Created by AngrySCV on 22.12.14.
 * https://github.com/angrySCV
 */
public class DataNode {
	public char     operator;
	public DataNode leftNode;
	public DataNode rightNode;
	public Double   result;
	public int priority;

	public DataNode (DataNode leftNode, char operator, DataNode rightNode) {
		this.operator = operator;
		this.leftNode = leftNode;
		this.rightNode = rightNode;

	}

	DataNode (double result) {
		this.result = result;
	}

	public double getResult (char operator, double left, double right) {
		if (result == null) {
			switch (operator) {
				case '-':
					result = Subtract(left, right);
					break;
				case '+':
					result = Add(left, right);
					break;
				case '*':
					result = Multiply(left, right);
					break;
				case '/':
					result = Divide(left, right);
					break;
				default:
			}
		}

		return result;
	}

	public static double Add (double num1, double num2) { return num1 + num2; }

	public static double Subtract (double num1, double num2) {
		return num1 - num2;
	}

	public static double Multiply (double num1, double num2) {
		return num1 * num2;
	}

	public static double Divide (double num1, double num2) {
		return num1 / num2;
	}

	public double getResult () {
		if (result == null) {
			getResult(operator, leftNode.getResult(), rightNode.getResult());
		}
		return result;
	}

	public void setResult (double result) {
		this.result = result;
	}

	public DataNode getRightNode () {
		return rightNode;
	}

	public void setRightNode (DataNode rightNode) {
		this.rightNode = rightNode;
	}

	public DataNode getLeftNode () {
		return leftNode;
	}

	public void setLeftNode (DataNode leftNode) {
		this.leftNode = leftNode;
	}

	public char getOperator () {
		return operator;
	}

	public void setOperator (char operator) {
		this.operator = operator;
	}
}
