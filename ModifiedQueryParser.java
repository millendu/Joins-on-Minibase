package tests;
/*
 * Predicates stored in predicates variable
 * Table filenames stored in relations variable
 * Table Aliases stored in relations_alias variable
 *
 *
 * */

import java.io.*;
import java.util.*;

public class ModifiedQueryParser {

	public List<String> all;
	public List<String> predicates;
	public List<String> relations = new ArrayList<String>();
	public List<String> selections;
	public List<String> relations_alias = new ArrayList<String>();
	public List<String> predicateslist = new ArrayList<String>();
	public HashMap<String, String> aliasmap = new HashMap<String, String>();

	public int num_predicates = 0;
	public int num_relations = 0;
	public int num_selections = 0;

	Map<Integer, List<Integer>> capturePredicatesMap = new HashMap<Integer, List<Integer>>();

	// public CondExpr[] outFilter = null;

	// public ResultantSet rs;

	// private void Query_CondExpr(CondExpr[] expr, String
	// relations,ArrayList<String> conditions) {
	//
	// int i = 0;
	//
	// for (i = 0; i < expr.length; i++) {
	// String tokens[] = conditions.get(i).split(" ");
	// expr[i].next = null;
	// expr[i].op = new AttrOperator(Integer.parseInt(tokens[1]));
	// expr[i].type1 = new AttrType(AttrType.attrSymbol);
	// expr[i].type2 = new AttrType(AttrType.attrSymbol);
	// expr[i].operand1.symbol = new FldSpec(new RelSpec(RelSpec.outer), 1);
	// expr[i].operand2.symbol = new FldSpec(
	// new RelSpec(RelSpec.innerRel), 1);
	// }
	//
	// expr[i] = null;
	// }

	public static void main(String args[]) {

		ModifiedQueryParser qp = new ModifiedQueryParser("q2c.txt");
		// qp.setOutFilter();

		// System.out.println(qp.outFilter[0].next);
		// System.out.println(qp.outFilter[0].op);
		// System.out.println(qp.outFilter[0].type1);
		// System.out.println(qp.outFilter[0].type2);
		// System.out.println(qp.outFilter[0].operand1.symbol.offset);
		// System.out.println(qp.outFilter[0].operand2.symbol.relation.key);
		// System.out.println(qp.outFilter[0].operand1.symbol.offset);
		// System.out.println(qp.outFilter[0].operand2.symbol.relation.key);
		//
		// System.out.println(qp.outFilter[1].next);
		// System.out.println(qp.outFilter[1].op);
		// System.out.println(qp.outFilter[1].type1);
		// System.out.println(qp.outFilter[1].type2);
		// System.out.println(qp.outFilter[1].operand1.symbol.offset);
		// System.out.println(qp.outFilter[1].operand2.symbol.relation.key);
		// System.out.println(qp.outFilter[1].operand1.symbol.offset);
		// System.out.println(qp.outFilter[1].operand2.symbol.relation.key);

	}

	// public void setOutFilter() {
	//
	// if (this.num_predicates == 1) {
	// outFilter = new CondExpr[2];
	// outFilter[0] = new CondExpr();
	// outFilter[1] = new CondExpr();
	//
	//
	// String pred_tokens[] = this.predicates.get(0).split(" ");
	// System.out.println("printing "+outFilter);
	// outFilter[0].next = null;
	// outFilter[0].op = new AttrOperator(Integer.parseInt(pred_tokens[1]));
	// outFilter[0].type1 = new
	// AttrType(relations.contains(pred_tokens[2].split("_")[0])?3:1);
	// outFilter[0].type2 = new
	// AttrType(relations.contains(pred_tokens[2].split("_")[0])?3:1);
	// outFilter[0].operand1.symbol = new FldSpec(new RelSpec(RelSpec.outer),
	// Integer.parseInt(pred_tokens[0].split("_")[1]));
	// outFilter[0].operand2.symbol = new FldSpec(new RelSpec(RelSpec.innerRel),
	// Integer.parseInt(pred_tokens[2].split("_")[1]));
	//
	// outFilter[1] = null;
	// }
	//
	// else if (this.num_predicates == 2) {
	// outFilter = new CondExpr[3];
	//
	// String pred_tokens[] = this.predicates.get(0).split(" ");
	// outFilter[0] = new CondExpr();
	// outFilter[0].next = null;
	// outFilter[0].op = new AttrOperator(Integer.parseInt(pred_tokens[1]));
	// outFilter[0].type1 = new
	// AttrType(relations.contains(pred_tokens[0].split("_")[0])?3:1);
	// outFilter[0].type2 = new
	// AttrType(relations.contains(pred_tokens[2].split("_")[0])?3:1);
	// outFilter[0].operand1.symbol = new FldSpec(new RelSpec(RelSpec.outer),
	// Integer.parseInt(pred_tokens[0].split("_")[1]));
	// outFilter[0].operand2.symbol = new FldSpec(new RelSpec(RelSpec.innerRel),
	// Integer.parseInt(pred_tokens[2].split("_")[1]));
	//
	// String pred_tokens2[] = this.predicates.get(1).split(" ");
	// outFilter[1] = new CondExpr();
	// outFilter[1].next = null;
	// outFilter[1].op = new AttrOperator(Integer.parseInt(pred_tokens2[1]));
	// outFilter[1].type1 = new
	// AttrType(relations.contains(pred_tokens2[0].split("_")[0])?3:1);
	// outFilter[1].type2 = new
	// AttrType(relations.contains(pred_tokens2[2].split("_")[0])?3:1);
	// outFilter[1].operand1.symbol = new FldSpec(new RelSpec(RelSpec.outer),
	// Integer.parseInt(pred_tokens2[0].split("_")[1]));
	// outFilter[1].operand2.symbol = new FldSpec(new RelSpec(RelSpec.innerRel),
	// Integer.parseInt(pred_tokens2[2].split("_")[1]));
	//
	// outFilter[2] = new CondExpr();
	// outFilter[2] = null;
	//
	// }
	//
	// }

	public ModifiedQueryParser(String inputFile) {
		BufferedReader br;
		String sel_t = null;
		String rel_t = null;
		List<String> predicates_preprocess = new ArrayList<String>();
		try {
			FileReader fr = new FileReader(inputFile);
			br = new BufferedReader(fr);
			String s;
			predicates = new ArrayList<String>();

			all = new ArrayList<String>();
			while ((s = br.readLine()) != null) {
				all.add(s);
			}
			int lineNo = 0;
			for (String line : all) {
				if (lineNo == 0) {
					sel_t = line;
				} else if (lineNo == 1) {
					rel_t = line;
				} else {
					predicates_preprocess.add(line);
				}
				lineNo++;
			}

			num_predicates = predicates.size();

			// ***Parsing Relations***
			List<String> relations_preprocess = Arrays.asList(rel_t.split("FROM")[1].split(","));

			for (String rel : relations_preprocess) {
				String[] reltemp = rel.split(" ");
				relations.add(reltemp[1]);
				relations_alias.add(reltemp[2]);
				aliasmap.put(reltemp[2], reltemp[1]);
			}
			// ***Printing relations and alias***
			// for(String rel : relations)
			// System.out.print(rel+",");
			// System.out.println();
			// for(String rel : relations_alias)
			// System.out.print(rel+",");

			// ***Parsing Selections***
			selections = Arrays.asList(sel_t.split(" "));
			num_selections = selections.size();
			// Query1_CondExpr(outFilter, tables, conditions);

			// ***Parsing Predicates***
			String[] temp_pred = new String[predicates_preprocess.size()];
			for (int i = 0; i < predicates_preprocess.size(); i++) {
				String[] temparr = predicates_preprocess.get(i).split(" AND ");
				for (String pre : temparr) {
					pre = pre.replaceAll(" ", "");
					pre = pre.replaceAll("AND", "");
					pre = pre.replaceAll("WHERE", "");
					pre = pre.replaceAll(";", "");
					// System.out.println("pre is "+pre);
					if (!pre.isEmpty())
						predicates.add(pre);
				}

			}

			for (String predlist : predicates) {
				if (predlist.contains(">")) {
					String temp_p[] = predlist.split(">");
					predicateslist.add(temp_p[0]);
					predicateslist.add(">");
					predicateslist.add(temp_p[1]);
				}

				else if (predlist.contains("<")) {
					String temp_p[] = predlist.split("<");
					predicateslist.add(temp_p[0]);
					predicateslist.add("<");
					predicateslist.add(temp_p[1]);
				}

			}

			for (String str : predicateslist) {

				// captuaring all attrs
				if (str.contains(".attr")) {

					String[] s1 = str.split(".attr");

					String t = aliasmap.get(s1[0]);
					int table = 0;
					if(t.contains("1"))table = 1;
					else if(t.contains("2"))table = 2;
					else if(t.contains("3"))table = 3;
					else if(t.contains("4"))table = 4;
					else if(t.contains("5"))table = 5;
					
					
					int attrnum = Integer.parseInt(s1[1]);
					List<Integer> l = null;
					
					if (capturePredicatesMap.containsKey(table)) {
						l = capturePredicatesMap.get(table);
						
					} else {
						
						l = new ArrayList<Integer>();
						capturePredicatesMap.put(table, l);
					}
					l.add((table-1)*7+attrnum);

				}

			}

			System.out.println(predicateslist);
			System.out.println(aliasmap);

			// this.setOutFilter();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
