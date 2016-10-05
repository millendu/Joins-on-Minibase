package tests;

import java.util.Vector;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import java.util.Vector;

public class IEJoinFinal {

	Vector<ResultantSet> rsVector;

	int index = 0;

	public void join(String relation1, String relation2, int op1, int op2, int operand1, int operand2, int operand3,
			int operand4) {

		Vector<Relation> tuples1 = JoinUtil.getScanedVector(relation1);
		Vector<Relation> tuples2 = JoinUtil.getScanedVector(relation2);

		// L1 wrt X
		Vector<Relation> tuplesListL1 = null;// new Vector<Relation>(tuples1);

		// L2 wrt Y
		Vector<Relation> tuplesListL2 = null;// new Vector<Relation>(tuples1);

		// L1d wrt Xd
		Vector<Relation> tuplesListL1D = null;// new Vector<Relation>(tuples2);

		// L2d wrt Yd
		Vector<Relation> tuplesListL2D = null;// new Vector<Relation>(tuples2);

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

		int[] posarray1 = new int[tuples1.size()];
		int[] finalposarray1 = new int[tuples1.size()];

		int[] posarray2 = new int[tuples2.size()];
		int[] finalposarray2 = new int[tuples2.size()];

		if (oper1List.contains(op1)) {
			// Sort list in descending order w.r.t id

			// Very Important
			// Support should be added for various datatypes

			// Collections.sort(tuplesListL1, Relation.getComparator(operand1,
			// false));
			tuplesListL1 = JoinUtil.getSortedVector(relation1, operand1, false, posarray1, null);

			/*
			 * for (int i = 0; i < tuplesListL1.size(); i++) {
			 * 
			 * System.out.print(tuplesListL1.get(i).getAttr1() + "-");
			 * tuplesListL1.get(i).setPosVal(i); } System.out.println();
			 */

			// Collections.sort(tuplesListL1D, Relation.getComparator(operand2,
			// false));
			tuplesListL1D = JoinUtil.getSortedVector(relation2, operand2, false, posarray2, null);

			// for (int i = 0; i < tuplesListL1D.size(); i++) {
			//
			// System.out.print(tuplesListL1D.get(i).getAttr1() + "-");
			// tuplesListL1D.get(i).setPosVal(i);
			//
			// }
			// System.out.println();
		} else if (oper2List.contains(op1)) {

			// Sort list in ascending order w.r.t id

			// Collections.sort(tuplesListL1, Relation.getComparator(operand1,
			// true));
			tuplesListL1 = JoinUtil.getSortedVector(relation1, operand1, true, posarray1, null);

			// Collections.sort(tuplesListL1D, Relation.getComparator(operand2,
			// true));
			tuplesListL1D = JoinUtil.getSortedVector(relation2, operand2, true, posarray2, null);

		}

		// ------------------------------------list`-----
		if (oper1List.contains(op2)) {

			// Sort list in ascending order w.r.t id

			// Collections.sort(tuplesListL2, Relation.getComparator(operand3,
			// true));
			tuplesListL2 = JoinUtil.getSortedVector(relation1, operand3, true, posarray1, finalposarray1);

			// Collections.sort(tuplesListL2D, Relation.getComparator(operand4,
			// true));
			tuplesListL2D = JoinUtil.getSortedVector(relation2, operand4, true, posarray2, finalposarray2);

		} else if (oper2List.contains(op2)) {

			// Sort list in descending order w.r.t id

			// Very Important
			// Support should be added for various datatypes

			// Collections.sort(tuplesListL2, Relation.getComparator(operand3,
			// false));
			tuplesListL2 = JoinUtil.getSortedVector(relation1, operand3, false, posarray1, finalposarray1);


			// Collections.sort(tuplesListL2D, Relation.getComparator(operand4,
			// false));
			tuplesListL2D = JoinUtil.getSortedVector(relation2, operand4, false, posarray2, finalposarray2);

		}

		// creating permute array

		int[] permuteArrayP = new int[tuples1.size()];
		int[] permuteArrayPD = new int[tuples2.size()];
		int[] bitArrayD = new int[tuples2.size()];

		int[] offsetArray1 = new int[tuples1.size()]; // L1 wrt L1D
		int[] offsetArray2 = new int[tuples1.size()]; // L2 wrt L2D

		for (int j = 0; j < tuplesListL2.size(); j++) {
			permuteArrayP[j] = finalposarray1[j];// tuplesListL2.get(j).getPosVal();
		}

		for (int j = 0; j < tuplesListL2D.size(); j++) {
			permuteArrayPD[j] = finalposarray2[j];// tuplesListL2D.get(j).getPosVal();
		}

		for (int x = 0; x < tuplesListL1.size(); x++) {
//			int attr1 = tuplesListL1.get(x).getAttr1();
//			int index = Collections.binarySearch(tuplesListL1D, new Relation(attr1, 0, 0, 0),
//					Relation.getComparator(operand1, true));
			
			int index = getIndexUtil(tuplesListL1, tuplesListL1D, x, operand1);

			if (index < 0) {
				index = -1 * (index + 1);
			}
			offsetArray1[x] = index;
		}

		for (int y = 0; y < tuplesListL2.size(); y++) {
//			int attr2 = tuplesListL2.get(y).getAttr2();
//			int index = Collections.binarySearch(tuplesListL2D, new Relation(0, attr2, 0, 0),
//					Relation.getComparator(operand3, true));
			
			int index = getIndexUtil(tuplesListL2, tuplesListL2D, y, operand3);

			if (index < 0) {
				index = -1 * (index + 1);
			}
			offsetArray2[y] = index;
		}

//		System.out.println("offsetArray1 " + Arrays.toString(offsetArray1));
//		System.out.println("offsetArray2 " + Arrays.toString(offsetArray2));

		int eqOff = 0;
		if (oper3List.contains(op1) && oper3List.contains(op2))
			eqOff = 0;
		else
			eqOff = 1;

		int m = tuples1.size();
		int n = tuples2.size();
		rsVector = new Vector<ResultantSet>();
		for (int i = 0; i < m; i++) {
			int off2 = offsetArray2[i];

			for (int j = 0; j <= off2; j++) {
				if (j >= bitArrayD.length)
					break;
				bitArrayD[permuteArrayPD[j]] = 1;
			}

			int off1 = offsetArray1[permuteArrayP[i]];
			for (int k = off1 + eqOff; k < n; k++) {
				if (bitArrayD[k] == 1) {
					ResultantSet rs = new ResultantSet(tuplesListL2.get(i), tuplesListL2D.get(k));
					rsVector.add(rs);
					// System.out.println(tuplesListL2.get(i).getAttr1() + "," +
					// tuplesListL2.get(i).getAttr2() + "----"
					// + tuplesListL2D.get(k).getAttr1() + "," +
					// tuplesListL2D.get(k).getAttr2());
				}
			}
		}

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

	public int getIndexUtil(Vector<Relation> tuplesListL1, Vector<Relation> tuplesListL1D, int x,
			int operand) {
		
		int index = 0;
		
		switch (operand) {
		case 1:
			int attr1 = tuplesListL1.get(x).getAttr1();
			index = Collections.binarySearch(tuplesListL1D, new Relation(attr1, 0, 0, 0),
					Relation.getComparator(operand, true));
			break;
		case 2:
			int attr2 = tuplesListL1.get(x).getAttr2();
			index = Collections.binarySearch(tuplesListL1D, new Relation(0, attr2, 0, 0),
					Relation.getComparator(operand, true));
			break;
		case 3:
			int attr3 = tuplesListL1.get(x).getAttr3();
			index = Collections.binarySearch(tuplesListL1D, new Relation(0, 0, attr3, 0),
					Relation.getComparator(operand, true));
			break;
		case 4:
			int attr4 = tuplesListL1.get(x).getAttr4();
			index = Collections.binarySearch(tuplesListL1D, new Relation(0, 0, 0, attr4),
					Relation.getComparator(operand, true));
			break;

		}

		return index;
	}

	public static void main(String args[]) {
		//
		// // (id,age)
		// tuples1.add(new Relation(20, 20));
		// tuples1.add(new Relation(10, 30));
		// tuples1.add(new Relation(40, 10));
		//
		// // new Object(20,20);
		//
		// tuples2.add(new Relation(50, 60, 70, 80));
		// tuples2.add(new Relation(30, 51, 40, 75));
		//
		//
		// (id,age)

		//
		// Vector<Relation> tuples1 = new Vector<Relation>();
		// Vector<Relation> tuples2 = new Vector<Relation>();
		// tuples1.add(new Relation(140, 9, 0, 0));
		// tuples1.add(new Relation(100, 12, 0, 0));
		// tuples1.add(new Relation(90, 5, 0, 0));
		//// tuples1.add(new Relation(150, 8, 0, 0));
		//
		// // new Object(20,20);
		//
		// tuples2.add(new Relation(160, 6, 0, 0));
		// tuples2.add(new Relation(140, 11, 0, 0));
		// tuples2.add(new Relation(80, 10, 0, 0));
		// tuples2.add(new Relation(90, 5, 0, 0));

		//
		// IEJoin();

		// Vector<Relation> tuples1 = LoadRelations1.getR("RelR.txt");
		// Vector<Relation> tuples2 = LoadRelations1.getS();

		// WHERE operand1 op1 operand2 AND operand3 op2 operand4;
		// WHERE R.attr1 < S.attr2 AND R.attr1 < S.attr2;

		// -------------------
		// WHERE

		String relation1 = "RelR.in";
		String relation2 = "RelS.in";
		int operand1 = 1;// attr1
		String op1 = "<";
		int operand2 = 1;// attr3

		// AND

		int operand3 = 2;// attr3
		String op2 = ">";
		int operand4 = 2;// attr3

		// join(relation1, relation2, op1, op2, operand1, operand2, operand3,
		// operand4);

	}
}
