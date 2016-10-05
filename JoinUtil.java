package tests;

import java.util.Vector;

import global.AttrType;
import global.TupleOrder;
import heap.Tuple;
import iterator.FileScan;
import iterator.FldSpec;
import iterator.Iterator;
import iterator.RelSpec;
import iterator.Sort;

public class JoinUtil {
	public static Vector<Relation> getSortedVector(String relation, int col, boolean asc, int[] posarray, int[] finalposarray) {

		AttrType[] Rtypes = new AttrType[5];
		Rtypes[0] = new AttrType(AttrType.attrInteger);
		Rtypes[1] = new AttrType(AttrType.attrInteger);
		Rtypes[2] = new AttrType(AttrType.attrInteger);
		Rtypes[3] = new AttrType(AttrType.attrInteger);
		Rtypes[4] = new AttrType(AttrType.attrInteger);

		short[] Rsizes = new short[1];
		Rsizes[0] = 0;
		FldSpec[] Rprojection = new FldSpec[5];
		Rprojection[0] = new FldSpec(new RelSpec(RelSpec.outer), 1);
		Rprojection[1] = new FldSpec(new RelSpec(RelSpec.outer), 2);
		Rprojection[2] = new FldSpec(new RelSpec(RelSpec.outer), 3);
		Rprojection[3] = new FldSpec(new RelSpec(RelSpec.outer), 4);
		Rprojection[4] = new FldSpec(new RelSpec(RelSpec.outer), 5);

		short[] Ssizes = new short[1];
		Ssizes[0] = 0; // first elt. is 30
		FileScan am = null;

		TupleOrder order = null;

		if (asc)
			order = new TupleOrder(TupleOrder.Ascending);
		else
			order = new TupleOrder(TupleOrder.Descending);

		Vector<Relation> r = new Vector<Relation>();

		try {
			am = new FileScan(relation, Rtypes, Rsizes, (short) Rtypes.length, (short) Rprojection.length, Rprojection,
					null);
			Iterator temp = new Sort(Rtypes, (short) Rtypes.length, Ssizes, am, col, order, 4, 10 / 2);

			Tuple t = new Tuple();
			int attr1, attr2, attr3, attr4, attr5;
			int c = 0;
			while (true) {
				t = temp.get_next();
				if (t == null)
					break;
				attr1 = t.getIntFld(1);
				attr2 = t.getIntFld(2);
				attr3 = t.getIntFld(3);
				attr4 = t.getIntFld(4);
				attr5 = t.getIntFld(5);

				r.addElement(new Relation(attr1, attr2, attr3, attr4, attr5));
				if (finalposarray == null)
					posarray[attr5] = c;
				else{
					
					finalposarray[c] = posarray[attr5];
					
				}

				c++;
			}

			System.out.println("getSortedVector");
			for (int i = 0; i < r.size(); i++) {
				Relation rel = (Relation) r.get(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return r;
	}

	public static Vector<Relation> getScanedVector(String relation) {

		AttrType[] Rtypes = new AttrType[5];
		Rtypes[0] = new AttrType(AttrType.attrInteger);
		Rtypes[1] = new AttrType(AttrType.attrInteger);
		Rtypes[2] = new AttrType(AttrType.attrInteger);
		Rtypes[3] = new AttrType(AttrType.attrInteger);
		Rtypes[4] = new AttrType(AttrType.attrInteger);

		short[] Rsizes = new short[1];
		Rsizes[0] = 0;
		FldSpec[] Rprojection = new FldSpec[5];
		Rprojection[0] = new FldSpec(new RelSpec(RelSpec.outer), 1);
		Rprojection[1] = new FldSpec(new RelSpec(RelSpec.outer), 2);
		Rprojection[2] = new FldSpec(new RelSpec(RelSpec.outer), 3);
		Rprojection[3] = new FldSpec(new RelSpec(RelSpec.outer), 4);
		Rprojection[4] = new FldSpec(new RelSpec(RelSpec.outer), 5);

		short[] Ssizes = new short[1];
		Ssizes[0] = 0; // first elt. is 30
		FileScan am = null;

		TupleOrder order = null;

		Vector<Relation> r = new Vector<Relation>();

		try {
			am = new FileScan(relation, Rtypes, Rsizes, (short) Rtypes.length, (short) Rprojection.length, Rprojection,
					null);

			Tuple t = new Tuple();

			while (true) {
				t = am.get_next();
				if (t == null)
					break;
				int attr1 = t.getIntFld(1);
				int attr2 = t.getIntFld(2);
				int attr3 = t.getIntFld(3);
				int attr4 = t.getIntFld(4);
				int attr5 = t.getIntFld(5);
				r.addElement(new Relation(attr1, attr2, attr3, attr4, attr5));

			}

			for (int i = 0; i < r.size(); i++) {
				Relation rel = (Relation) r.get(i);
//				System.out.println(rel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return r;
	}
}
