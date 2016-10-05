package tests;

import java.util.*;

public class IEJoin_col {

	// Vector<ResultantSet> rsVector;

	// Set<ResultantSet> s;

	int index = 0;

	public Vector<Relation> join(Vector<Relation> tuples1, Vector<Relation> tuples2, int op1, int op2, int operand1,
			int operand2, int operand3, int operand4, Vector<Relation> result) {

		// Vector<Relation> tuples1 = LoadRelations1.ModifiedRel(relation1);
		// Vector<Relation> tuples2 = LoadRelations1.ModifiedRel(relation2);

		// L1 wrt X
		Vector<Relation> tuplesListL1 = new Vector<Relation>(tuples1);

		// L2 wrt Y
		Vector<Relation> tuplesListL2 = new Vector<Relation>(tuples1);

		// L1d wrt Xd
		Vector<Relation> tuplesListL1D = new Vector<Relation>(tuples2);

		// L2d wrt Yd
		Vector<Relation> tuplesListL2D = new Vector<Relation>(tuples2);

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

		if (op1 == 3 || op1 == 4) {
			// Sort list in descending order w.r.t id

			// Very Important
			// Support should be added for various datatypes

			Collections.sort(tuplesListL1, Relation.getComparator(operand1, false));
			// tuplesListL1 = JoinUtil.getSortedVector(relation1, operand1,
			// false, posarray1, null);

			for (int i = 0; i < tuplesListL1.size(); i++) {

				// System.out.print(tuplesListL1.get(i).getAttr(operand1) +
				// "--");
				tuplesListL1.get(i).setPosVal(i);
			}
			// System.out.println();

			Collections.sort(tuplesListL1D, Relation.getComparator(operand2, false));
			// tuplesListL1D = JoinUtil.getSortedVector(relation2, operand2,
			// false, posarray2, null);

			for (int i = 0; i < tuplesListL1D.size(); i++) {

				// System.out.print(tuplesListL1D.get(i).getAttr(operand2) +
				// "--");
				tuplesListL1D.get(i).setPosVal(i);

			}
			// System.out.println();

		} else if (op1 == 1 || op1 == 2) {

			// Sort list in ascending order w.r.t id

			Collections.sort(tuplesListL1, Relation.getComparator(operand1, true));
			// tuplesListL1 = JoinUtil.getSortedVector(relation1, operand1,
			// true, posarray1, null);

			for (int i = 0; i < tuplesListL1.size(); i++) {
				// System.out.print(tuplesListL1.get(i).getAttr(operand1) +
				// "--");
				tuplesListL1.get(i).setPosVal(i);

			}

			Collections.sort(tuplesListL1D, Relation.getComparator(operand2, true));
			// tuplesListL1D = JoinUtil.getSortedVector(relation2, operand2,
			// true, posarray2, null);

			for (int i = 0; i < tuplesListL1D.size(); i++) {
				// System.out.print(tuplesListL1D.get(i).getAttr(operand2) +
				// "--");
				tuplesListL1D.get(i).setPosVal(i);

			}

		}

		// ------------------------------------list`-----
		if (op2 == 3 || op2 == 4) {

			// Sort list in ascending order w.r.t id

			Collections.sort(tuplesListL2, Relation.getComparator(operand3, true));
			// tuplesListL2 = JoinUtil.getSortedVector(relation1, operand3,
			// true, posarray1, finalposarray1);

			// for (int i = 0; i < tuplesListL2.size(); i++) {
			// System.out.print(tuplesListL2.get(i).getAttr(operand3) + "--");
			// }

			Collections.sort(tuplesListL2D, Relation.getComparator(operand4, true));
			// tuplesListL2D = JoinUtil.getSortedVector(relation2, operand4,
			// true, posarray2, finalposarray2);

			// for (int i = 0; i < tuplesListL2D.size(); i++) {
			// System.out.print(tuplesListL2D.get(i).getAttr(operand4) + "--");
			// }

		} else if (op2 == 1 || op2 == 2) {

			// Sort list in descending order w.r.t id

			// Very Important
			// Support should be added for various datatypes

			Collections.sort(tuplesListL2, Relation.getComparator(operand3, false));

			// tuplesListL2 = JoinUtil.getSortedVector(relation1, operand3,
			// false, posarray1, finalposarray1);

			// for (int i = 0; i < tuplesListL2.size(); i++) {
			// System.out.print(tuplesListL2.get(i).getAttr(operand3) + "--");
			// }

			Collections.sort(tuplesListL2D, Relation.getComparator(operand4, false));
			// tuplesListL2D = JoinUtil.getSortedVector(relation2, operand4,
			// false, posarray2, finalposarray2);

			// for (int i = 0; i < tuplesListL2D.size(); i++) {
			// System.out.print(tuplesListL2D.get(i).getAttr(operand4) + "--");
			// }

		}

		// creating permute array

		System.out.println("before permute array");

		int[] permuteArrayP = new int[tuples1.size()];
		int[] permuteArrayPD = new int[tuples2.size()];
		int[] bitArrayD = new int[tuples2.size()];

		// int[] offsetArray1 = new int[tuples1.size()]; // L1 wrt L1D
		// int[] offsetArray2 = new int[tuples1.size()]; // L2 wrt L2D

		ArrayList<Integer> offsetArray1 = new ArrayList<Integer>();
		ArrayList<Integer> offsetArray2 = new ArrayList<Integer>();

		for (int j = 0; j < tuplesListL2.size(); j++) {
			permuteArrayP[j] = tuplesListL2.get(j).getPosVal();
		}

		for (int j = 0; j < tuplesListL2D.size(); j++) {
			permuteArrayPD[j] = tuplesListL2D.get(j).getPosVal();
		}

		// changing size of each n-size array to n-size + 1

		// oldtuplesListL1

		change(tuplesListL1);
		change(tuplesListL2);
		change(tuplesListL1D);
		change(tuplesListL2D);

		change1(permuteArrayP);
		change1(permuteArrayPD);

		int lno = 0;
		for (int i = 0; i < tuplesListL2.size(); i++) {

			if (op2 == 1) {
				lno = 0;
				while (lno < tuplesListL2D.size() && ((Relation) tuplesListL2D.get(lno))
						.getAttr(operand4) > ((Relation) tuplesListL2.get(i)).getAttr(operand3))
					lno++;

			} else if (op2 == 2) {
				lno = 0;
				while (lno < tuplesListL2D.size() && ((Relation) tuplesListL2D.get(lno))
						.getAttr(operand4) >= ((Relation) tuplesListL2.get(i)).getAttr(operand3))
					lno++;

			} else if (op2 == 3) {
				lno = 0;
				while (lno < tuplesListL2D.size() && ((Relation) tuplesListL2D.get(lno))
						.getAttr(operand4) <= ((Relation) tuplesListL2.get(i)).getAttr(operand3))
					lno++;
			} else if (op2 == 4) {
				lno = 0;
				while (lno < tuplesListL2D.size() && ((Relation) tuplesListL2D.get(lno))
						.getAttr(operand4) < ((Relation) tuplesListL2.get(i)).getAttr(operand3))
					lno++;

			}

			offsetArray2.add(lno + 1);

		}

		for (int i = 0; i < tuplesListL1.size(); i++) {

			if (op1 == 1) {
				lno = 0;
				while (lno < tuplesListL1D.size() && ((Relation) tuplesListL1D.get(lno))
						.getAttr(operand2) <= ((Relation) tuplesListL1.get(i)).getAttr(operand1))
					lno++;
			} else if (op1 == 2) {
				lno = 0;
				while (lno < tuplesListL1D.size() && ((Relation) tuplesListL1D.get(lno))
						.getAttr(operand2) < ((Relation) tuplesListL1.get(i)).getAttr(operand1))
					lno++;
			} else if (op1 == 3) {

				lno = 0;
				while (lno < tuplesListL1D.size() && ((Relation) tuplesListL1D.get(lno))
						.getAttr(operand2) > ((Relation) tuplesListL1.get(i)).getAttr(operand1))
					lno++;
			} else if (op1 == 4) {
				lno = 0;
				while (lno < tuplesListL1D.size() && ((Relation) tuplesListL1D.get(lno))
						.getAttr(operand2) >= ((Relation) tuplesListL1.get(i)).getAttr(operand1))
					lno++;
			}

			offsetArray1.add(lno + 1);
		}

		// System.out.println("Offset Array o1 : " +
		// Arrays.asList(offsetArray1));
		// System.out.println("Offset Array o2 : " +
		// Arrays.asList(offsetArray2));
		//
		// System.out.println("permuteArrayP : " +
		// Arrays.toString(permuteArrayP));
		// System.out.println("permuteArrayPD : " +
		// Arrays.toString(permuteArrayPD));

		int eqOff = 0;
		if (oper3List.contains(op1) && oper3List.contains(op2))
			eqOff = 1;
		else
			eqOff = 0;

		int m = tuples1.size();
		int n = tuples2.size();
		// s = new HashSet<ResultantSet>();

		int off1 = 0;
		int off2 = 0;

		int count = 0;

		System.out.println("before for loop in join");
		for (int i = 0; i < tuplesListL2.size(); i++) {
			off2 = offsetArray2.get(i);
			if (off2 >= 2) {
				for (int j = 0; j <= off2 - 2; j++) {
					bitArrayD[permuteArrayPD[j] - 1] = 1;
				}
			}

			off1 = offsetArray1.get(permuteArrayP[i] - 1);
			if (off1 <= tuplesListL1D.size()) {
				for (int j = off1 - 1; j < bitArrayD.length; j++) {
					if (bitArrayD[j] == 1) {
						// System.out.println("Ans:\t\t" + tuplesListL2.get(i) +
						// " " + tuplesListL1D.get(j));

						Relation rs = new Relation();

						rs.attrMap = new HashMap<Integer, Integer>();
						rs.attrMap.putAll(tuplesListL2.get(i).attrMap);
						rs.attrMap.putAll(tuplesListL1D.get(j).attrMap);

						result.add(rs);
						count++;
					}
				}
			}
		}
		System.out.println("join done");
		System.out.println("join done");

		// System.out.println("Total count : " + count);
		return result;

	}

	// public ResultantSet get_next() {
	// if (index >= rsVector.size()) {
	// index = 0;
	// return null;
	// }
	//
	// ResultantSet rs = rsVector.get(index);
	// index++;
	// return rs;
	// }

	// public int getIndexUtil(Vector<Relation> tuplesListL1, Vector<Relation>
	// tuplesListL1D, int x, int operand) {
	//
	// int index = 0;
	//
	// switch (operand) {
	// case 1:
	// int attr1 = tuplesListL1.get(x).getAttr(1);
	// index = Collections.binarySearch(tuplesListL1D, new Relation(attr1, 0, 0,
	// 0),
	// Relation.getComparator(operand, true));
	// break;
	// case 2:
	// int attr2 = tuplesListL1.get(x).getAttr2();
	// index = Collections.binarySearch(tuplesListL1D, new Relation(0, attr2, 0,
	// 0),
	// Relation.getComparator(operand, true));
	// break;
	// case 3:
	// int attr3 = tuplesListL1.get(x).getAttr3();
	// index = Collections.binarySearch(tuplesListL1D, new Relation(0, 0, attr3,
	// 0),
	// Relation.getComparator(operand, true));
	// break;
	// case 4:
	// int attr4 = tuplesListL1.get(x).getAttr4();
	// index = Collections.binarySearch(tuplesListL1D, new Relation(0, 0, 0,
	// attr4),
	// Relation.getComparator(operand, true));
	// break;
	//
	// }
	//
	// return index;
	// }

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

	public static void change(Vector<Relation> tuplesListL1) {
		Vector<Relation> temp = new Vector<Relation>();
		temp.add(null);
		for (int i = 0; i < tuplesListL1.size(); i++) {
			temp.add(tuplesListL1.get(i));
		}
		tuplesListL1 = temp;
	}

	public static void change(int[] tuplesListL1) {
		int[] temp = new int[tuplesListL1.length + 1];

		for (int i = 0; i < tuplesListL1.length; i++) {
			temp[i + 1] = tuplesListL1[i];
		}
		tuplesListL1 = temp;
	}

	public static void change1(int[] tuplesListL1) {

		for (int i = 0; i < tuplesListL1.length; i++) {
			tuplesListL1[i] += 1;
		}
	}
}