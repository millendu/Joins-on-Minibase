package tests;

import java.util.*;

public class SelfJoinTwoPredicateBloomFinal {

	Vector<ResultantSet> rsVector;
	int index = 0;
	// static Vector<Relation> tuples = new Vector<Relation>();
	// static Vector<Object> tuplesObjects = new Vector<Object>();

	public void join(String relation, Integer op1, Integer op2, int operand1, int operand2) {

		Vector<Relation> tuplesList = JoinUtil.getScanedVector(relation);

		Vector<Relation> tuplesList1 = null;// new Vector<Relation>(tuples);

		Vector<Relation> tuplesList2 = null;// new
											// Vector<Relation>(tuplesList1);

		Vector<Integer> oper1List = new Vector<Integer>();
		oper1List.add(4);
		oper1List.add(3);

		Vector<Integer> oper2List = new Vector<Integer>();
		oper2List.add(1);
		oper2List.add(2);

		// less than or equal to and greater than equal to
		Vector<Integer> oper3List = new Vector<Integer>();
		oper3List.add(2);
		oper3List.add(3);
		// System.out.println(oper2.contains(pred1));

		//// a.id < a.id and a.age > a.age

		// handleOper1Sort();

		// Handling operator1 value

		// step 2 and step3

		// int[][] posarray = new int[2][tuplesList.size()];
		int[] posarray = new int[tuplesList.size()];
		int[] finalposarray = new int[tuplesList.size()];

		// *******************************************
		if (oper1List.contains(op1)) {
			// Sort list in ascending order w.r.t id

			// Collections.sort(tuplesList1, Relation.getComparator(operand1,
			// true));
			tuplesList1 = JoinUtil.getSortedVector(relation, operand1, true, posarray, null);

			for (int i = 0; i < tuplesList1.size(); i++) {
				System.out.println(tuplesList1.get(i).getAttr1());
				tuplesList1.get(i).setPosVal(i);
			}

		} else if (oper2List.contains(op1)) {

			// Sort list in descending order w.r.t id

			// Very Important
			// Support should be added for various datatypes

			// Collections.sort(tuplesList1, Relation.getComparator(operand1,
			// false));
			tuplesList1 = JoinUtil.getSortedVector(relation, operand1, false, posarray, null);

			for (int i = 0; i < tuplesList1.size(); i++) {

				System.out.println(tuplesList1.get(i).getAttr1());
				tuplesList1.get(i).setPosVal(i);
			}

		}
		// **************************************************

		// step 4 and step 5
		// ********************************************************
		if (oper1List.contains(op2)) {

			// Sort list in descending order w.r.t age

			// Collections.sort(tuplesList2, Relation.getComparator(operand2,
			// false));
			tuplesList2 = JoinUtil.getSortedVector(relation, operand2, false, posarray, finalposarray);

			for (int i = 0; i < tuplesList2.size(); i++)
				System.out.println(tuplesList2.get(i).getAttr2());

		} else if (oper2List.contains(op2)) {

			// Sort list in ascending order w.r.t age

			// Collections.sort(tuplesList2, Relation.getComparator(operand2,
			// true));
			tuplesList2 = JoinUtil.getSortedVector(relation, operand2, true, posarray, finalposarray);

			for (int i = 0; i < tuplesList2.size(); i++)
				System.out.println(tuplesList2.get(i).getAttr2());

		}

		// ********************************************************
		// Step 6
		// **************************************************
		int[] permuteArray = new int[tuplesList.size()];

		for (int j = 0; j < tuplesList2.size(); j++) {

			permuteArray[j] = finalposarray[j];// tuplesList2.get(j).getPosVal();
		}

		for (int k = 0; k < permuteArray.length; k++)
			System.out.print(permuteArray[k] + " -");

		//
		// ******************************************

		// Step 7
		// *****************************************************
		int[] bitArray = new int[tuplesList.size()];
		int[] bloomArray = new int[(tuplesList.size() / 4) + 1];

		// **********************************************

		// Step 8
		// *****************************************************
		Vector<Relation> resultArray = new Vector<Relation>();

		// *****************************************************

		// Step 9 and Step 10
		// **********************************************
		int eqOff = 0;
		if (oper3List.contains(op1) && oper3List.contains(op2))
			eqOff = 0;
		else
			eqOff = 1;
		// ***************************************

		System.out.println();
		int chunkSize = 32;

		// Step 11 to Step 16
		// **************************************************
		rsVector = new Vector<ResultantSet>();

		for (int i = 0; i < tuplesList.size(); i++) {

			int pos = permuteArray[i];

			bitArray[pos] = 1;
			int bloomPos = pos / chunkSize;
			bloomArray[bloomPos] = 1;

			for (int bloomIndex = bloomPos; bloomIndex < bloomArray.length; bloomIndex++) {
				if (bloomArray[bloomIndex] == 1) {

					int bloomIndexEnd = (bloomIndex + 1) * chunkSize;
					if (tuplesList.size() < bloomIndexEnd)
						bloomIndexEnd = tuplesList.size();

					int bloomIndexStart = (bloomIndex) * chunkSize;
					if (pos + eqOff > bloomIndexStart)
						bloomIndexStart = pos + eqOff;

					for (int j = bloomIndexStart; j < bloomIndexEnd; j++) {
						if (j > tuplesList.size() - 1)
							break;
						if (bitArray[j] == 1) {
							ResultantSet rs = new ResultantSet(tuplesList1.get(j), tuplesList1.get(permuteArray[i]));
							rsVector.add(rs);
							// System.out.print(tuplesList1.get(j) + "-" +
							// tuplesList1.get(permuteArray[i]));

						}
					}
				}
			}

		}
//		Set<ResultantSet> set = new HashSet<ResultantSet>(rsVector);
//		rsVector = new Vector<ResultantSet>(set);
		// *******************************************************

	}

	public ResultantSet get_next() {
		if (index >= rsVector.size()) {
			index = 0;
			return null;
		}

		ResultantSet rs = rsVector.get(index);
		index++;
		return rs;
	}

	public static void main(String args[]) {
		// Vector<Relation> tuples = new Vector<Relation>();
		// tuples.add(new Relation(100, 6, 0, 0));
		// tuples.add(new Relation(140, 11, 0, 0));
		// tuples.add(new Relation(80, 10, 0, 0));
		// tuples.add(new Relation(90, 5, 0, 0));

		// Vector<Relation> tuples = LoadRelations.getR();

		// WHERE operand1 op1 operand1 AND operand2 op2 operand2;

		String relation = "RelR.in";

		String op1 = "<";
		String op2 = "<";
		int operand1 = 1;// attr1
		int operand2 = 3;// attr3

		// join(relation, op1, op2, operand1, operand2);

	}

}
