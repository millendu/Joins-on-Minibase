package tests;

import java.io.BufferedReader;
import java.io.FileReader;

//originally from : joins.C

import java.util.*;

class MergeTables {
	long count;
	public String prevmergetables;
	public String mergetables;
	public int line;
	boolean isFinished = false;

	MergeTables(int l, String m) {
		line = l;
		mergetables = m;
	}

	public String toString() {
		return line + " " + mergetables+" count: "+count;
	}
}

public class JoinTestTask2 {

	public Vector<Relation> Modifiedquery2C(ModifiedQueryParser qp, long[] count, Vector<Relation> preResult,
			MergeTables mt, String prev, boolean sam, boolean sampling) throws Exception {

		int preline = mt.line;
		// String sample = "";
		// if (sam)
		// sample = "sample";

		int start = (preline * 6) + 0;

		String temp = "";

		temp = qp.predicateslist.get(start).charAt(qp.predicateslist.get(start).length() - 1) + "";
		int operand1 = Integer.parseInt(temp);

		int op1 = (qp.predicateslist.get(start + 1).equals("<")) ? 1 : 4;

		temp = qp.predicateslist.get(start + 2).charAt(qp.predicateslist.get(start + 2).length() - 1) + "";
		int operand2 = Integer.parseInt(temp);

		temp = qp.predicateslist.get(start + 3).charAt(qp.predicateslist.get(start + 3).length() - 1) + "";
		int operand3 = Integer.parseInt(temp);

		int op2 = (qp.predicateslist.get(start + 4).equals("<")) ? 1 : 4;

		temp = qp.predicateslist.get(start + 5).charAt(qp.predicateslist.get(start + 5).length() - 1) + "";
		int operand4 = Integer.parseInt(temp);

		temp = qp.predicateslist.get(start).split("\\.")[0];
		String t1 = qp.aliasmap.get(temp);
		String relation1 = t1 + ".txt";
		if (sampling)
			mt.mergetables += t1;

		temp = qp.predicateslist.get(start + 2).split("\\.")[0];
		String t2 = qp.aliasmap.get(temp);
		String relation2 = t2 + ".txt";
		if (sampling)
			mt.mergetables += t2;

		int table1 = 0;

		if (relation1.contains("1"))
			table1 = 0;
		else if (relation1.contains("2"))
			table1 = 1;
		else if (relation1.contains("3"))
			table1 = 2;
		else if (relation1.contains("4"))
			table1 = 3;
		else if (relation1.contains("5"))
			table1 = 4;

		int table2 = 0;

		if (relation2.contains("1"))
			table2 = 0;
		else if (relation2.contains("2"))
			table2 = 1;
		else if (relation2.contains("3"))
			table2 = 2;
		else if (relation2.contains("4"))
			table2 = 3;
		else if (relation2.contains("5"))
			table2 = 4;

		operand1 += table1 * 7;
		operand3 += table1 * 7;

		operand2 += table2 * 7;
		operand4 += table2 * 7;

		//IEJoin_col_bloom thisJoin = new IEJoin_col_bloom();
		IEJoin_col thisJoin = new IEJoin_col();

		Vector<Relation> tuples1 = null;
		Vector<Relation> tuples2 = null;

		// merge relations
		if (!sampling && preResult != null) {
			if (prev.contains(t1) || prev.contains(t2)) {

				if (prev.contains(t1)) {
					tuples1 = preResult;
				} else {
					tuples2 = preResult;
				}

			} else {
				count[0] = -1;
				return preResult;
			}
		}
		if (tuples1 == null || tuples1.size() == 0)
			tuples1 = LoadRelations1.ModifiedRel(relation1, sam, qp);
		if (tuples2 == null || tuples2.size() == 0)
			tuples2 = LoadRelations1.ModifiedRel(relation2, sam, qp);

		Vector<Relation> result = new Vector<Relation>();
		System.out.println("before join");
		thisJoin.join(tuples1, tuples2, op1, op2, operand1, operand2, operand3, operand4, result);

		// ResultantSet rs;
		// int count = 0;
		// while ((rs = thisJoin.get_next()) != null) {
		// rs.print(qp.getFirstSel(), qp.getSecondSel());
		// count++;
		// }
		// qp.printHeader();
		count[0] = result.size();
		return result;
	}

	public boolean runTests(String q) {

		try {

			long start = System.nanoTime();
			Scanner sc = new Scanner(System.in);

			/*
			 * SELECT count(*) FROM F1 r, F2 s, F3 t, F4 v, F5 w WHERE F1.attr1
			 * < F2.attr1 AND F1.attr2 < F2.attr2 AND F2.attr3 < F3.attr3 AND
			 * F2.attr4 < F3.attr4 AND F3.attr5 < F4.attr5 AND F3.attr6 <
			 * F4.attr6 AND F4.attr1 < F5.attr1 AND F4.attr2 < F5.attr2;
			 */

			ModifiedQueryParser qp1 = new ModifiedQueryParser(q);
			System.out.println("Sampling required?: ");
			sc = new Scanner(System.in);
			boolean sample = true;
			long[] count = { 0 };

			List<MergeTables> preCountList = new ArrayList<MergeTables>();

			for (int i = 0; i < qp1.predicateslist.size() / 6; i++) {
				String[] mergetables = { "" };
				MergeTables mt = new MergeTables(i, mergetables[0]);
				Modifiedquery2C(qp1, count, null, mt, null, sample, true);
				System.out.println("\nAfter Sampling, Predicate Line " + i + " : " + count[0] + " records");
				mt.count = count[0];
				preCountList.add(mt);
			}
			
			Collections.sort(preCountList, new Comparator<MergeTables>(){
				public int compare(MergeTables m1 , MergeTables m2){
					return (int) (m1.count - m2.count);
				}
			});

			List<MergeTables> sorted = new ArrayList<MergeTables>();

//			int[] line = {0,2,3,1};
//			for(int l=0; l<4; l++){
//				for (MergeTables mt1 : preCountList) {
//					if(mt1.line==line[l])
//						sorted.add(mt1);
//				}
//			}
			
			for (MergeTables mt1 : preCountList) {
				sorted.add(mt1);
			}

			System.out.println("\nsampling order: " + sorted + "\n");

			Vector<Relation> result = null;
			String prevmergetable = "";

			int sortIndex = 0;
			while (!isEverythingFinished(sorted)) {
				if (sorted.get(sortIndex).isFinished) {
					sortIndex = (sortIndex + 1) % sorted.size();
					continue;
				}
				result = Modifiedquery2C(qp1, count, result, sorted.get(sortIndex), prevmergetable, false, false);
				if (count[0] != -1) {
					prevmergetable += sorted.get(sortIndex).mergetables;
					sorted.get(sortIndex).isFinished = true;
				}
				sortIndex = (sortIndex + 1) % sorted.size();
				System.out.println("prevmergetable " + prevmergetable + " " + count[0]);

			}

			System.out.println("TOTAL RECORDS: " + result.size() + "\n");
			long end = System.nanoTime();
			System.out.println("Total Time: " + (float) ((end - start) / Math.pow(10, 9)) + " secs");

			Runtime runtime = Runtime.getRuntime();
			System.out.println("Memory Used: " + (float) (runtime.totalMemory() / (1024 * 1024)) + " MB");

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.print("Finished joins testing" + "\n");

		return true;
	}

	public boolean isEverythingFinished(List<MergeTables> sorted) {
		for (int i = 0; i < sorted.size(); i++) {
			if (!sorted.get(i).isFinished)
				return false;
		}
		return true;
	}

	public static void main(String argv[]) {
		boolean sortstatus;
		System.out.println("START-----------------");
		System.out.println(Runtime.getRuntime().maxMemory() / (1024 * 1024));
		JoinTestTask2 jjoin = new JoinTestTask2();
		sortstatus = jjoin.runTests("main.txt");
		
		

		if (sortstatus != true) {
			System.out.println("Error ocurred during join tests");
		} else {
			System.out.println("join tests completed successfully");
		}
	}
}
