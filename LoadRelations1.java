package tests;

import java.io.FileReader;
import java.util.*;

public class LoadRelations1 {

	public static Map<String, Vector<Relation>> heap = new HashMap<String, Vector<Relation>>();

	// public static Vector<Relation> getRel(String input) {
	//
	// if (heap.containsKey(input))
	// return heap.get(input);
	//
	// Vector<Relation> RelRVector = new Vector<Relation>();
	// try {
	// FileReader f = new FileReader(input);
	// Scanner sc = new Scanner(f);
	// int lineNo = 0;
	// sc.nextLine();
	// while (sc.hasNextLine()) {
	// lineNo++;
	// String line = sc.nextLine();
	// // System.out.println(line);
	// String[] split = line.split(",");
	//
	// int attr1 = Integer.parseInt(split[0]);
	// int attr2 = Integer.parseInt(split[1]);
	// int attr3 = Integer.parseInt(split[2]);
	// int attr4 = Integer.parseInt(split[3]);
	// Relation tupleq = new Relation(attr1, attr2, attr3, attr4);
	// RelRVector.add(tupleq);
	//
	// }
	// System.out.println("total lines: " + lineNo);
	//
	// } catch (Exception e) {
	//
	// }
	//
	// heap.put(input, RelRVector);
	//
	// return RelRVector;
	// }

	public static Vector<Relation> ModifiedRel(String input, boolean createSample, ModifiedQueryParser qp) {
		String sampleinput = "SAMPLE" + input;

		if (createSample) {
			if (heap.containsKey(sampleinput))
				return heap.get(sampleinput);
		}

		if (heap.containsKey(input))
			return heap.get(input);

		Vector<Relation> RelRVector = new Vector<Relation>();
		try {
			FileReader f = new FileReader(input);
			Scanner sc = new Scanner(f);
			int lineNo = 0;
			sc.nextLine();
			while (sc.hasNextLine()) {
				lineNo++;
				String line = sc.nextLine();
				// System.out.println(line);
				String[] split = line.split(",");
				int[] attrArr = new int[7];
				int ii = 0;
				for (ii = 0; ii < split.length; ii++) {
					attrArr[ii] = Integer.parseInt(split[ii]);
				}
				for (int iii = ii; iii < 7; iii++) {
					attrArr[iii] = -1;
				}

				int table = 0;

				if (input.contains("1"))
					table = 1;
				else if (input.contains("2"))
					table = 2;
				else if (input.contains("3"))
					table = 3;
				else if (input.contains("4"))
					table = 4;
				else if (input.contains("5"))
					table = 5;

				Relation tupleq = new Relation(attrArr, table, qp);

				RelRVector.add(tupleq);

			}
			// System.out.println("total lines in " + input + " : " + lineNo);

		} catch (Exception e) {
			e.printStackTrace();
		}
		heap.put(input, RelRVector);
		System.out.println("total lines in  " + input + " : " + RelRVector.size());

		if (createSample) {

			return createSample(input);
		}

		return RelRVector;
	}

	public static Vector<Relation> createSample(String input) {
		String sampleinput = "SAMPLE" + input;

		if (heap.containsKey(sampleinput))
			return heap.get(sampleinput);
		;

		Vector<Relation> vec = heap.get(input);
		Vector<Relation> samplevec = new Vector<Relation>();
		
		int n = (vec.size() / 100) * 1;
		int uniform = vec.size() / n;
		int option = 1;

		// Random sampling
		// randsample.add(table.get(tupnum[i]));
		//
		switch (option) {
		case 1:
			int size = vec.size();
			int numtuples = n;
			Random r = new Random(System.currentTimeMillis());
			int[] tupnum = new int[numtuples];
			for (int i = 0; i < numtuples; i++) {
				tupnum[i] = Math.abs(r.nextInt()) % size;
			}
			for (int i = 0; i < numtuples; i++)
				samplevec.addElement(vec.get(tupnum[i]));
			break;
		case 2:
			for (int i = 0; i < vec.size(); i = i + uniform)
				samplevec.addElement(vec.get(i));
			break;
		case 3:
			// first 1% sampling
			for (int i = 0; i <= n; i++) {
				samplevec.addElement(vec.get(i));
			}

			break;

		}

		heap.put(sampleinput, samplevec);
		System.out.println("total lines in  " + sampleinput + " : " + heap.get(sampleinput).size());

		return heap.get(sampleinput);

	}
	// public static Vector<ResultantSet> ModifiedRel1(String input) {
	//
	// Vector<ResultantSet> RelRVector = new Vector<ResultantSet>();
	// try {
	// FileReader f = new FileReader(input);
	// Scanner sc = new Scanner(f);
	// int lineNo = 0;
	// sc.nextLine();
	// while (sc.hasNextLine()) {
	// lineNo++;
	// String line = sc.nextLine();
	// // System.out.println(line);
	// String[] split = line.split(",");
	// int[] attrArr = new int[7];
	//
	// for (int ii = 0; ii < split.length; ii++) {
	// attrArr[ii] = Integer.parseInt(split[ii]);
	// }
	//
	// Relation tupleq = new Relation(attrArr[0], attrArr[1], attrArr[2],
	// attrArr[3], attrArr[4], attrArr[5],
	// attrArr[6], 0);
	//
	// Relation f1 = null;
	// Relation f2 = null;
	// Relation f3 = null;
	// Relation f4 = null;
	// Relation f5 = null;
	//
	// if (input.contains("1"))
	// f1 = tupleq;
	// else if (input.contains("2"))
	// f2 = tupleq;
	// else if (input.contains("3"))
	// f3 = tupleq;
	// else if (input.contains("4"))
	// f4 = tupleq;
	// else if (input.contains("5"))
	// f5 = tupleq;
	// ResultantSet rs = new ResultantSet(f1, f2, f3, f4, f5);
	//
	// RelRVector.add(rs);
	//
	// }
	// System.out.println("total lines: " + lineNo);
	//
	// } catch (Exception e) {
	//
	// }
	//
	// return RelRVector;
	// }

	// public static void main(String[] args) {
	// getRel("RelR.txt");
	// }
}