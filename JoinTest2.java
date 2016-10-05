package tests;

//originally from : joins.C

import iterator.*;
import iterator.Iterator;
import heap.*;
import global.*;
import index.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import diskmgr.*;
import bufmgr.*;
import btree.*;
import catalog.*;

/**
 * Here is the implementation for the tests. There are N tests performed. We
 * start off by showing that each operator works on its own. Then more
 * complicated trees are constructed. As a nice feature, we allow the user to
 * specify a selection condition. We also allow the user to hardwire trees
 * together.
 */

class JoinsDriver2 implements GlobalConst {

	private boolean OK = true;
	private boolean FAIL = false;
	private Vector<Relation> relQVector;
	private Vector<Relation> relRVector;
	private Vector<Relation> relSVector;
	private static AttrType[] Stypes = new AttrType[5];

	static {

		Stypes[0] = new AttrType(AttrType.attrInteger);
		Stypes[1] = new AttrType(AttrType.attrInteger);
		Stypes[2] = new AttrType(AttrType.attrInteger);
		Stypes[3] = new AttrType(AttrType.attrInteger);
		Stypes[4] = new AttrType(AttrType.attrInteger);
	}

	/**
	 * Constructor
	 */
	public JoinsDriver2() {

		// build Sailor, Boats, Reserves table
		relQVector = LoadRelations1.getQ(); // sailors
		relRVector = LoadRelations1.getR("RelR.txt"); // boats
		relSVector = LoadRelations1.getS(); // reserves

		Vector<Vector<Relation>> QRS = new Vector<Vector<Relation>>();
		QRS.addElement(relQVector);
		QRS.addElement(relRVector);
		QRS.addElement(relSVector);

		Vector<String> QRSHeapNames = new Vector<String>();
		QRSHeapNames.addElement("RelQ.in");
		QRSHeapNames.addElement("RelR.in");
		QRSHeapNames.addElement("RelS.in");

		boolean status = OK;

		String dbpath = "/tmp/" + System.getProperty("user.name") + ".minibase.jointestdb";
		String logpath = "/tmp/" + System.getProperty("user.name") + ".joinlog";

		String remove_cmd = "/bin/rm -rf ";
		String remove_logcmd = remove_cmd + logpath;
		String remove_dbcmd = remove_cmd + dbpath;
		String remove_joincmd = remove_cmd + dbpath;

		try {
			Runtime.getRuntime().exec(remove_logcmd);
			Runtime.getRuntime().exec(remove_dbcmd);
			Runtime.getRuntime().exec(remove_joincmd);
		} catch (IOException e) {
			System.err.println("" + e);
		}

		/*
		 * ExtendedSystemDefs extSysDef = new ExtendedSystemDefs(
		 * "/tmp/minibase.jointestdb", "/tmp/joinlog", 1000,500,200,"Clock");
		 */

		SystemDefs sysdef = new SystemDefs(dbpath, 1000, NUMBUF, "Clock");

		int relIndex = 0;
		for (Vector<Relation> rel : QRS) {
			// creating the sailors relation
			// AttrType[] Stypes = new AttrType[5];
			// Stypes[0] = new AttrType(AttrType.attrInteger);
			// Stypes[1] = new AttrType(AttrType.attrInteger);
			// Stypes[2] = new AttrType(AttrType.attrInteger);
			// Stypes[3] = new AttrType(AttrType.attrInteger);
			// Stypes[4] = new AttrType(AttrType.attrInteger);

			// SOS
			short[] Ssizes = new short[1];
			Ssizes[0] = 0; // first elt. is 30

			Tuple t = new Tuple();
			try {
				t.setHdr((short) Stypes.length, Stypes, Ssizes);
			} catch (Exception e) {
				System.err.println("*** error in Tuple.setHdr() ***");
				status = FAIL;
				e.printStackTrace();
			}

			int size = t.size();

			// inserting the tuple into file "sailors"
			RID rid;
			Heapfile f = null;
			try {
				f = new Heapfile(QRSHeapNames.get(relIndex));
			} catch (Exception e) {
				System.err.println("*** error in Heapfile constructor ***");
				status = FAIL;
				e.printStackTrace();
			}

			t = new Tuple(size);
			try {
				t.setHdr((short) Stypes.length, Stypes, Ssizes);
			} catch (Exception e) {
				System.err.println("*** error in Tuple.setHdr() ***");
				status = FAIL;
				e.printStackTrace();
			}

			for (int i = 0; i < rel.size(); i++) {
				try {
					t.setIntFld(1, ((Relation) rel.elementAt(i)).getAttr1());
					t.setIntFld(2, ((Relation) rel.elementAt(i)).getAttr2());
					t.setIntFld(3, ((Relation) rel.elementAt(i)).getAttr3());
					t.setIntFld(4, ((Relation) rel.elementAt(i)).getAttr4());
					t.setIntFld(5, i);
				} catch (Exception e) {
					System.err.println("*** Heapfile error in Tuple.setStrFld() ***");
					status = FAIL;
					e.printStackTrace();
				}

				try {
					rid = f.insertRecord(t.returnTupleByteArray());
				} catch (Exception e) {
					System.err.println("*** error in Heapfile.insertRecord() ***");
					status = FAIL;
					e.printStackTrace();
				}
			}
			if (status != OK) {
				// bail out
				System.err.println("*** Error creating relation for sailors");
				Runtime.getRuntime().exit(1);
			}

			relIndex++;
		}

	}

	public void query2A(QueryParser qp) throws Exception {

		// select count(*) from RelR r, RelR s WHERE r.attr1 < s.attr1;

		// WHERE operand1 op1 operand1;

		// change the table input
		String relation = "Rel" + qp.relations.get(0) + ".in";
		// 2a is single predicate
		Integer op1 = Integer.parseInt(qp.predicates.get(0).split(" ")[1]);
		int operand1 = Integer.parseInt(qp.predicates.get(0).split(" ")[0].split("_")[1]); // attr1

		// String relation = "RelR.in";
		// String op1 = ">";
		// int operand1 = 1; // attr1

		// SelfJoinSinglePredicateFinal.join(relation, op1, operand1);

		SelfJoinSinglePredicateFinal thisJoin = new SelfJoinSinglePredicateFinal();
		thisJoin.join(relation, op1, operand1, qp);
		ResultantSet rs;
		int count = 0;
		while ((rs = thisJoin.get_next()) != null) {
			rs.print(qp.getFirstSel(), qp.getSecondSel());
			count++;
		}
		qp.printHeader();
		System.out.println("total count: " + count);
	}

	public void query2B(QueryParser qp) {

		String relation = "Rel" + qp.relations.get(0) + ".in";

		Integer op1 = Integer.parseInt(qp.predicates.get(0).split(" ")[1]);
		int operand1 = Integer.parseInt(qp.predicates.get(0).split(" ")[0].split("_")[1]); // attr1

		Integer op2 = Integer.parseInt(qp.predicates.get(1).split(" ")[1]);
		int operand2 = Integer.parseInt(qp.predicates.get(1).split(" ")[0].split("_")[1]); // attr1

		// String op1 = "<";
		// String op2 = "<";
		// int operand1 = 1;// attr1
		// int operand2 = 3;// attr3

		// SelfJoinTwoPredicateFinal.join(relation, op1, op2, operand1,
		// operand2,qp);

		SelfJoinTwoPredicateFinal thisJoin = new SelfJoinTwoPredicateFinal();
		thisJoin.join(relation, op1, op2, operand1, operand2, qp);

		ResultantSet rs;
		int count = 0;
		while ((rs = thisJoin.get_next()) != null) {
			rs.print(qp.getFirstSel(), qp.getSecondSel());
			count++;
		}
		qp.printHeader();
		System.out.println("total count: " + count);

	}

	public void query2Db(QueryParser qp) throws Exception {

		String relation = "Rel" + qp.relations.get(0) + ".in";

		Integer op1 = Integer.parseInt(qp.predicates.get(0).split(" ")[1]);
		int operand1 = Integer.parseInt(qp.predicates.get(0).split(" ")[0].split("_")[1]); // attr1

		Integer op2 = Integer.parseInt(qp.predicates.get(1).split(" ")[1]);
		int operand2 = Integer.parseInt(qp.predicates.get(1).split(" ")[0].split("_")[1]); // attr1

		// SelfJoinTwoPredicateBloomFinal.join(relation, op1, op2,
		// operand1,operand2);

		SelfJoinTwoPredicateBloomFinal thisJoin = new SelfJoinTwoPredicateBloomFinal();
		thisJoin.join(relation, op1, op2, operand1, operand2);
		ResultantSet rs;
		int count = 0;
		while ((rs = thisJoin.get_next()) != null) {
			rs.print(qp.getFirstSel(), qp.getSecondSel());
			count++;
		}
		qp.printHeader();
		System.out.println("total count: " + count);

	}

	public void query2Dc(QueryParser qp) throws Exception {

		// // -------------------
		// // WHERE
		// String relation1 = "RelR.in";
		// String relation2 = "RelS.in";
		//
		// int operand1 = 1;// attr1
		// String op1 = "<";
		// int operand2 = 1;// attr3
		//
		// // AND
		//
		// int operand3 = 2;// attr3
		// String op2 = ">";
		// int operand4 = 2;// attr3

		String relation1 = "Rel" + qp.relations.get(0) + ".in";

		// String relation2 = "RelS.in";
		String relation2 = "Rel" + qp.relations.get(1) + ".in";

		// int operand1 = 1;// attr1
		// String op1 = "<";
		// int operand2 = 1;// attr3

		int operand1 = Integer.parseInt(qp.predicates.get(0).split(" ")[0].split("_")[1]);// attr1
		int op1 = Integer.parseInt((qp.predicates.get(0).split(" ")[1]));
		int operand2 = Integer.parseInt(qp.predicates.get(0).split(" ")[2].split("_")[1]);
		;// attr3

		// AND

		// int operand3 = 2;// attr3
		// int op2 = Integer.parseInt((qp.predicates.get(1).split(" ")[1]));
		// int operand4 = 2;// attr3

		int operand3 = Integer.parseInt(qp.predicates.get(1).split(" ")[0].split("_")[1]);// attr1
		int op2 = Integer.parseInt((qp.predicates.get(1).split(" ")[1]));
		int operand4 = Integer.parseInt(qp.predicates.get(1).split(" ")[2].split("_")[1]);

		IEJoinBloomFinal thisJoin = new IEJoinBloomFinal();
		thisJoin.join(relation1, relation2, op1, op2, operand1, operand2, operand3, operand4);
		ResultantSet rs;
		int count = 0;
		while ((rs = thisJoin.get_next()) != null) {
			rs.print(qp.getFirstSel(), qp.getSecondSel());
			count++;
		}
		qp.printHeader();
		System.out.println("total count: " + count);
	}

	public void query2C(QueryParser qp) throws Exception {
		String relation1 = "Rel" + qp.relations.get(0) + ".in";
		String relation2 = "Rel" + qp.relations.get(1) + ".in";

		// int operand1 = 1;// attr1
		// String op1 = "<";
		// int operand2 = 1;// attr3

		int operand1 = Integer.parseInt(qp.predicates.get(0).split(" ")[0].split("_")[1]);// attr1
		int op1 = Integer.parseInt((qp.predicates.get(0).split(" ")[1]));
		int operand2 = Integer.parseInt(qp.predicates.get(0).split(" ")[2].split("_")[1]);

		// AND

		// int operand3 = 2;// attr3
		// int op2 = Integer.parseInt((qp.predicates.get(1).split(" ")[1]));
		// int operand4 = 2;// attr3

		int operand3 = Integer.parseInt(qp.predicates.get(1).split(" ")[0].split("_")[1]);// attr1
		int op2 = Integer.parseInt((qp.predicates.get(1).split(" ")[1]));
		int operand4 = Integer.parseInt(qp.predicates.get(1).split(" ")[2].split("_")[1]);

		IEJoinFinal thisJoin = new IEJoinFinal();
		thisJoin.join(relation1, relation2, op1, op2, operand1, operand2, operand3, operand4);
		ResultantSet rs;
		int count = 0;
		while ((rs = thisJoin.get_next()) != null) {
			rs.print(qp.getFirstSel(), qp.getSecondSel());
			count++;
		}
		qp.printHeader();
		System.out.println("total count: " + count);
	}

	public boolean runTests() {

		try {

			long start = 0;
			Scanner sc = new Scanner(System.in);

			QueryParser qp = new QueryParser("query_2c.txt");
			boolean selfjoin = qp.relations.size() == 1 ? true : false;

			if (qp.predicates.size() == 1) {
				start = System.nanoTime();
				query2A(qp);
			} else if (qp.predicates.size() > 1) {

				System.out.println(
						"Optimized or not?\nPress 1 for Bloom implementation\nPress 2 for Non Bloom implementation\n");
				int option = sc.nextInt();
				start = System.nanoTime();
				if (selfjoin && option == 2)
					query2B(qp);
				else if (selfjoin && option == 1)
					query2Db(qp);
				else if (!selfjoin && option == 2)
					query2C(qp);
				else if (!selfjoin && option == 1)
					query2Dc(qp);

			}

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
}

public class JoinTest2 {
	public static void main(String argv[]) {
		boolean sortstatus;

		JoinsDriver2 jjoin = new JoinsDriver2();
		sortstatus = jjoin.runTests();

		if (sortstatus != true) {
			System.out.println("Error ocurred during join tests");
		} else {
			System.out.println("join tests completed successfully");
		}
	}
}
