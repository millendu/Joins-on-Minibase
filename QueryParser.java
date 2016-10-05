package iterator;

import global.AttrOperator;
import global.AttrType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tests.ResultantSet;

public class QueryParser {

	public List<String> all;
	public List<String> predicates;
	public List<String> relations;
	public List<String> selections;

	public int num_predicates = 0;
	public int num_relations = 0;
	public int num_selections = 0;

	public CondExpr[] outFilter = null;

	public ResultantSet rs;

	private void Query_CondExpr(CondExpr[] expr, String relations, ArrayList<String> conditions) {

		int i = 0;

		for (i = 0; i < expr.length; i++) {
			String tokens[] = conditions.get(i).split(" ");
			expr[i].next = null;
			expr[i].op = new AttrOperator(Integer.parseInt(tokens[1]));
			expr[i].type1 = new AttrType(AttrType.attrSymbol);
			expr[i].type2 = new AttrType(AttrType.attrSymbol);
			expr[i].operand1.symbol = new FldSpec(new RelSpec(RelSpec.outer), 1);
			expr[i].operand2.symbol = new FldSpec(new RelSpec(RelSpec.innerRel), 1);
		}

		expr[i] = null;
	}

	public static void main(String args[]) {

		QueryParser qp = new QueryParser("query_1b.txt");
		qp.setOutFilter();

		System.out.println(qp.outFilter[0].next);
		System.out.println(qp.outFilter[0].op);
		System.out.println(qp.outFilter[0].type1);
		System.out.println(qp.outFilter[0].type2);
		System.out.println(qp.outFilter[0].operand1.symbol.offset);
		System.out.println(qp.outFilter[0].operand2.symbol.relation.key);
		System.out.println(qp.outFilter[0].operand1.symbol.offset);
		System.out.println(qp.outFilter[0].operand2.symbol.relation.key);

		System.out.println(qp.outFilter[1].next);
		System.out.println(qp.outFilter[1].op);
		System.out.println(qp.outFilter[1].type1);
		System.out.println(qp.outFilter[1].type2);
		System.out.println(qp.outFilter[1].operand1.symbol.offset);
		System.out.println(qp.outFilter[1].operand2.symbol.relation.key);
		System.out.println(qp.outFilter[1].operand1.symbol.offset);
		System.out.println(qp.outFilter[1].operand2.symbol.relation.key);

	}

	public void setOutFilter() {

		if (this.num_predicates == 1) {
			outFilter = new CondExpr[2];
			outFilter[0] = new CondExpr();
			outFilter[1] = new CondExpr();

			String pred_tokens[] = this.predicates.get(0).split(" ");
			System.out.println("printing  " + outFilter);
			outFilter[0].next = null;
			outFilter[0].op = new AttrOperator(Integer.parseInt(pred_tokens[1]));
			outFilter[0].type1 = new AttrType(relations.contains(pred_tokens[2].split("_")[0]) ? 3 : 1);
			outFilter[0].type2 = new AttrType(relations.contains(pred_tokens[2].split("_")[0]) ? 3 : 1);
			outFilter[0].operand1.symbol = new FldSpec(new RelSpec(RelSpec.outer),
					Integer.parseInt(pred_tokens[0].split("_")[1]));
			outFilter[0].operand2.symbol = new FldSpec(new RelSpec(RelSpec.innerRel),
					Integer.parseInt(pred_tokens[2].split("_")[1]));

			outFilter[1] = null;
		}

		else if (this.num_predicates == 2) {
			outFilter = new CondExpr[3];

			String pred_tokens[] = this.predicates.get(0).split(" ");
			outFilter[0] = new CondExpr();
			outFilter[0].next = null;
			outFilter[0].op = new AttrOperator(Integer.parseInt(pred_tokens[1]));
			outFilter[0].type1 = new AttrType(relations.contains(pred_tokens[0].split("_")[0]) ? 3 : 1);
			outFilter[0].type2 = new AttrType(relations.contains(pred_tokens[2].split("_")[0]) ? 3 : 1);
			outFilter[0].operand1.symbol = new FldSpec(new RelSpec(RelSpec.outer),
					Integer.parseInt(pred_tokens[0].split("_")[1]));
			outFilter[0].operand2.symbol = new FldSpec(new RelSpec(RelSpec.innerRel),
					Integer.parseInt(pred_tokens[2].split("_")[1]));

			String pred_tokens2[] = this.predicates.get(1).split(" ");
			outFilter[1] = new CondExpr();
			outFilter[1].next = null;
			outFilter[1].op = new AttrOperator(Integer.parseInt(pred_tokens2[1]));
			outFilter[1].type1 = new AttrType(relations.contains(pred_tokens2[0].split("_")[0]) ? 3 : 1);
			outFilter[1].type2 = new AttrType(relations.contains(pred_tokens2[2].split("_")[0]) ? 3 : 1);
			outFilter[1].operand1.symbol = new FldSpec(new RelSpec(RelSpec.outer),
					Integer.parseInt(pred_tokens2[0].split("_")[1]));
			outFilter[1].operand2.symbol = new FldSpec(new RelSpec(RelSpec.innerRel),
					Integer.parseInt(pred_tokens2[2].split("_")[1]));

			outFilter[2] = new CondExpr();
			outFilter[2] = null;

		}

	}

	public QueryParser(String inputFile) {
		BufferedReader br;
		String sel_t = null;
		String rel_t = null;
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
					if (lineNo % 2 == 0)
						predicates.add(line);
				}
				lineNo++;
			}

			num_predicates = predicates.size();
			relations = Arrays.asList(rel_t.split(" "));
			num_relations = relations.size();
			selections = Arrays.asList(sel_t.split(" "));
			num_selections = selections.size();
			// Query1_CondExpr(outFilter, tables, conditions);

			this.setOutFilter();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getFirstSel() {
		return Integer.parseInt(selections.get(0).split("_")[1]);
	}

	public int getSecondSel() {
		return Integer.parseInt(selections.get(1).split("_")[1]);
	}

	public void printHeader() {
		if (relations.size() == 1)
			System.out.println(selections.get(0) +" "+ selections.get(1));
		else
			System.out.println(selections.get(0) +" "+ selections.get(1));

	}
}