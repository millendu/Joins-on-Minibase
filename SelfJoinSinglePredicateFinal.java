package tests;

import iterator.QueryParser;

import java.util.Arrays;
import java.util.Vector;

/*To do:
 * 
 * Implement self join on Single Predicate
 * 
 * 1)Take the tuples into an array
 * 2)if given operator is > or >= sort in ascending order
 * 	else
 *  if given operator is < or <= sort in descending order
 *  put in new array let's say sorted array
 * 3)Set positions of each tuple in sorted array during sorting 
 * 4)Calculate the offset required here depending upon the input operator
 * 5)Iterate through original array and do the following
 * 	1)Get position of each tuple at given index in original array in the sorted array
 *  2)For each position below the found position, print the pair with (present tuple,tuple at below position)
 * 
 */

public class SelfJoinSinglePredicateFinal{

	 Vector<ResultantSet> rsVector;
	 int index = 0;

	public void join(String relation, Integer op1, int operand1, QueryParser qp) {

		// Vector<Relation> tuplesList = inputTuples;
		Vector<Relation> tuplesList = JoinUtil.getScanedVector(relation);

		Vector<Relation> tuplesListDash = null;// new
												// Vector<Relation>(inputTuples);

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

		//// a.id < a.id and a.age > a.age

		// Handling operator1 value

		// step 2 and step3

		int[] posarray = new int[tuplesList.size()];

		// *******************************************
		if (oper1List.contains(op1)) {
			// Sort list in ascending order w.r.t id

			// Collections.sort(tuplesListDash, Relation.getComparator(operand1,
			// true));
			tuplesListDash = JoinUtil.getSortedVector(relation, operand1, true, posarray, null);

			for (int i = 0; i < tuplesListDash.size(); i++) {
				tuplesListDash.get(i).setPosVal(i);
			}

		} else if (oper2List.contains(op1)) {

			// Sort list in descending order w.r.t id

			// Very Important
			// Support should be added for various datatypes
			// No needed can assume that all are integers

			// Collections.sort(tuplesListDash, Relation.getComparator(operand1,
			// false));
			tuplesListDash = JoinUtil.getSortedVector(relation, operand1, true, posarray, null);

			System.out.println();
			for (int i = 0; i < tuplesListDash.size(); i++) {

				tuplesListDash.get(i).setPosVal(i);
			}

		}
		// **************************************************

		// Step 4
		// **********************************************
		int eqOff = 0;
		if (oper3List.contains(op1))
			eqOff = 1;
		else
			eqOff = 0;
		// ***************************************

		System.out.println();

		// Step 5
		// **************************************************

//		System.out.println(Arrays.asList(tuplesListDash));
//		System.out.println(Arrays.asList(tuplesList));

		rsVector = new Vector<ResultantSet>();
		for (int i = 0; i < tuplesList.size(); i++) {

			// int pos = tuplesList.get(i).getPosVal();
			int pos = posarray[i];
			
			for (int j = 0; j < (pos + eqOff); j++) {

				ResultantSet rs = new ResultantSet(tuplesListDash.get(pos), tuplesListDash.get(j));
				rsVector.add(rs);
			}

			

		}
		
		// *******************************************************

	}
	
	public ResultantSet get_next(){
		if(index >= rsVector.size()){
			index=0;
			return null;
		}
		
		ResultantSet rs =  rsVector.get(index);
		index++;
		return rs;
	}
	
	public static void main(String[] args) {
		// Vector<Relation> tuples = new
		// Vector<Relation>(LoadRelations1.getR("RelR.txt"));

		// select count(*) from RelR r, RelR s WHERE r.attr1 < s.attr1;

		// WHERE operand1 op1 operand1;
		// String relation = "RelR.in";
		// String op1 = ">";
		// int operand1 = 1; //attr1
		//
		// join(relation, op1, operand1);
	}

}
