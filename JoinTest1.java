package tests;
//originally from : joins.C

import iterator.*;
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

class JoinsDriver1 implements GlobalConst {

	private boolean OK = true;
	private boolean FAIL = false;
	private Vector<Relation> relQVector;
	private Vector<Relation> relRVector;
	private Vector<Relation> relSVector;

	/**
	 * Constructor
	 */
	public JoinsDriver1() {

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
			AttrType[] Stypes = new AttrType[4];
			Stypes[0] = new AttrType(AttrType.attrInteger);
			Stypes[1] = new AttrType(AttrType.attrInteger);
			Stypes[2] = new AttrType(AttrType.attrInteger);
			Stypes[3] = new AttrType(AttrType.attrInteger);

			// SOS
			short[] Ssizes = new short[1];
			Ssizes[0] = 0; // first elt. is 30

			Tuple t = new Tuple();
			try {
				t.setHdr((short) 4, Stypes, Ssizes);
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
				t.setHdr((short) 4, Stypes, Ssizes);
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
	
//edited by aditya***
	public void Query1(QueryParser qp) {
		String table1heap = "Rel"+ qp.relations.get(0)+".in";
		String table2heap = "Rel"+ qp.relations.get(1)+".in";

		boolean status = OK;

		IndexType b_index = new IndexType(IndexType.B_Index);

		
		Tuple t = new Tuple();
		t = null;

		AttrType[] Stypes = { new AttrType(AttrType.attrInteger), new AttrType(AttrType.attrInteger),
				new AttrType(AttrType.attrInteger), new AttrType(AttrType.attrInteger) };

		AttrType[] Stypes2 = { new AttrType(AttrType.attrInteger), new AttrType(AttrType.attrInteger),
				new AttrType(AttrType.attrInteger), new AttrType(AttrType.attrInteger) };
		short[] Ssizes = new short[1];
		Ssizes[0] = 0;

		AttrType[] Rtypes = { new AttrType(AttrType.attrInteger), new AttrType(AttrType.attrInteger),
				new AttrType(AttrType.attrInteger), new AttrType(AttrType.attrInteger) };

		short[] Rsizes = new short[1];
		Rsizes[0] = 0;


		FldSpec[] Sprojection = { new FldSpec(new RelSpec(RelSpec.outer), 1),
				new FldSpec(new RelSpec(RelSpec.outer), 2),
				 new FldSpec(new RelSpec(RelSpec.outer), 3),
				 new FldSpec(new RelSpec(RelSpec.outer), 4)
		};
		
		//get from file
		AttrType[] Jtypes = { new AttrType(AttrType.attrInteger), new AttrType(AttrType.attrInteger), };
		short[] Jsizes = new short[1];
		Jsizes[0] = 0;
		
		
		FldSpec[] proj1 = { new FldSpec(new RelSpec(RelSpec.innerRel), Integer.parseInt(qp.selections.get(0).split("_")[1])) , new FldSpec(new RelSpec(RelSpec.innerRel), Integer.parseInt(qp.selections.get(1).split("_")[1]))}; // S.sname,R.bid
		
		
		CondExpr[] selects = new CondExpr[1];
		selects[0] = null;

		// IndexType b_index = new IndexType(IndexType.B_Index);
		iterator.Iterator am = null;

		// _______________________________________________________________
		// *******************create an scan on the heapfile**************
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// create a tuple of appropriate size
		Tuple tt = new Tuple();
		try {
			tt.setHdr((short) 4, Stypes, Ssizes);
		} catch (Exception e) {
			status = FAIL;
			e.printStackTrace();
		}

		int sizett = tt.size();
		tt = new Tuple(sizett);
		try {
			tt.setHdr((short) 4, Stypes, Ssizes);
		} catch (Exception e) {
			status = FAIL;
			e.printStackTrace();
		}
		Heapfile f = null;
		try {
			f = new Heapfile(table1heap);
		} catch (Exception e) {
			status = FAIL;
			e.printStackTrace();
		}

		Scan scan = null;
		try {
			scan = new Scan(f);
		} catch (Exception e) {
			status = FAIL;
			e.printStackTrace();
			Runtime.getRuntime().exit(1);
		}

		// create the index file
		BTreeFile btf = null;
		try {
			btf = new BTreeFile("BTreeIndex", AttrType.attrInteger, 4, 1);
		} catch (Exception e) {
			status = FAIL;
			e.printStackTrace();
			Runtime.getRuntime().exit(1);
		}

		RID rid = new RID();
		int key = 0;
		Tuple temp = null;

		try {
			temp = scan.getNext(rid);
		} catch (Exception e) {
			status = FAIL;
			e.printStackTrace();
		}
		while (temp != null) {
			tt.tupleCopy(temp);

			try {
				key = tt.getIntFld(1);
			} catch (Exception e) {
				status = FAIL;
				e.printStackTrace();
			}

			try {
				btf.insert(new IntegerKey(key), rid);
			} catch (Exception e) {
				status = FAIL;
				e.printStackTrace();
			}

			try {
				temp = scan.getNext(rid);
			} catch (Exception e) {
				status = FAIL;
				e.printStackTrace();
			}
		}

		// close the file scan
		scan.closescan();

		// _______________________________________________________________
		// *******************close an scan on the heapfile**************
		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		System.out.print("After Building btree index on sailors.sid.\n\n");
		try {
			am = new IndexScan(b_index, table1heap, "BTreeIndex", Stypes, Ssizes, Stypes.length, Sprojection.length, Sprojection, null,
					1, false);
		}

		catch (Exception e) {
			System.err.println("*** Error creating scan for Index scan");
			System.err.println("" + e);
			Runtime.getRuntime().exit(1);
		}

		NestedLoopsJoins nlj = null;
		try {
			
			nlj = new NestedLoopsJoins(Stypes2, Stypes2.length, Ssizes, Rtypes, Rtypes.length, Rsizes, 10, am,
					table2heap, qp.outFilter, null, proj1, proj1.length);
		} catch (Exception e) {
			System.err.println("*** Error preparing for nested_loop_join");
			System.err.println("" + e);
			e.printStackTrace();
			Runtime.getRuntime().exit(1);
		}

		t = null;
		int count=0;
		try {
			while ((t = nlj.get_next()) != null) {
				t.print(Jtypes);
				count++;
				// qcheck2.Check(t);
			}
		} catch (Exception e) {
			System.err.println("" + e);
			e.printStackTrace();
			Runtime.getRuntime().exit(1);
		}

		// qcheck2.report(2);
		qp.printHeader();
		System.out.println("\n");
		System.out.println("total count: "+count);
		
		try {
			nlj.close();
		} catch (Exception e) {
			status = FAIL;
			e.printStackTrace();
		}

		if (status != OK) {
			// bail out

			Runtime.getRuntime().exit(1);
		}
	}


	public boolean runTests() {
		long start = System.nanoTime();

		QueryParser q1 = new QueryParser("query_1b.txt");
		Query1(q1);
		long end = System.nanoTime();
		System.out.println("Toatl Time: "+ (float)((end-start)/Math.pow(10, 9)) +" secs");
		Runtime runtime = Runtime.getRuntime();
		System.out.println("Memory Used: " + (float)(runtime.totalMemory() / (1024 * 1024)) + " MB");
		
		return true;
	}
}

public class JoinTest1 {
	public static void main(String argv[]) {
		boolean sortstatus;

		JoinsDriver1 jjoin = new JoinsDriver1();
		sortstatus = jjoin.runTests();
		
		
		if (sortstatus != true) {
			System.out.println("Error ocurred during join tests");
		} else {
			System.out.println("Join tests completed successfully");
		}
	}
}
