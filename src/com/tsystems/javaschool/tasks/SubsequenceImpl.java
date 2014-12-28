package com.tsystems.javaschool.tasks;

import java.util.List;

/**
 * Created by AngrySCV on 28.12.14.
 * https://github.com/angrySCV
 */
public class SubsequenceImpl implements Subsequence {
	private int kolichestvoSovpadeniy = 0;
	private int startElement = 0;

	@Override
	public boolean find (List x, List y) {
		int i = 0;
		while (i < x.size()) { // здесь простой перебор совпадений в строках
			kolichestvoSovpadeniy = 0;
//			startElement = i;
			for (int j = 0; j < y.size(); j++) {

				if (x.get(i).equals(y.get(j))) {
					i++;
					kolichestvoSovpadeniy++;
				}
			}
		if (kolichestvoSovpadeniy == x.size()) return true;
			else return false; // если при первом же элементе нет совпадения то дальше не проверяем - совпадения строки и не будет
		}
		return false;
	}

}