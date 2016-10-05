package tests;

import java.util.*;

public class Relation {

	public Map<Integer, Integer> attrMap;
	public int posVal;

	Relation(int[] attrArr, int table, ModifiedQueryParser qp) {
		attrMap = new HashMap<>();
		List<Integer> li = qp.capturePredicatesMap.get(table);

		for (int i = 1; i <= 35; i++) {

			int attr = i;
			int arrIndex = (int) (attr - 1) % 7;
			if (attrArr[arrIndex % 7] != -1) {
				if (li.contains(attr))
					attrMap.put(attr, attrArr[arrIndex]);
			}

		}

	}

	Relation(int[] attrArr) {
		attrMap = new HashMap<>();
		for (int i = 1; i <= attrArr.length; i++) {
			if (attrArr[i] != -1)
				attrMap.put((int) i, attrArr[i - 1]);

		}

	}

	Relation() {

	}

	public int getPosVal() {
		return posVal;
	}

	public void setPosVal(int posVal) {
		this.posVal = posVal;
	}

	public static Comparator<Relation> comparatorAttr1Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(1) - o2.getAttr(1);
		}
	};

	public static Comparator<Relation> comparatorAttr1Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(1) - o1.getAttr(1);
		}
	};

	public static Comparator<Relation> comparatorAttr2Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(2) - o2.getAttr(2);
		}
	};

	public static Comparator<Relation> comparatorAttr2Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(2) - o1.getAttr(2);
		}
	};

	public static Comparator<Relation> comparatorAttr3Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(3) - o2.getAttr(3);
		}
	};

	public static Comparator<Relation> comparatorAttr3Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(3) - o1.getAttr(3);
		}
	};

	public static Comparator<Relation> comparatorAttr4Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(4) - o2.getAttr(4);
		}
	};

	public static Comparator<Relation> comparatorAttr4Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(4) - o1.getAttr(4);
		}
	};

	public static Comparator<Relation> comparatorAttr5Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(5) - o2.getAttr(5);
		}
	};

	public static Comparator<Relation> comparatorAttr5Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(5) - o1.getAttr(5);
		}
	};

	public static Comparator<Relation> comparatorAttr6Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(6) - o2.getAttr(6);
		}
	};

	public static Comparator<Relation> comparatorAttr6Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(6) - o1.getAttr(6);
		}
	};

	public static Comparator<Relation> comparatorAttr7Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(7) - o2.getAttr(7);
		}
	};

	public static Comparator<Relation> comparatorAttr7Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(7) - o1.getAttr(7);
		}
	};

	public static Comparator<Relation> comparatorAttr8Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(8) - o2.getAttr(8);
		}
	};

	public static Comparator<Relation> comparatorAttr8Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(8) - o1.getAttr(8);
		}
	};

	public static Comparator<Relation> comparatorAttr9Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(9) - o2.getAttr(9);
		}
	};

	public static Comparator<Relation> comparatorAttr9Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(9) - o1.getAttr(9);
		}
	};

	public static Comparator<Relation> comparatorAttr10Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(10) - o2.getAttr(10);
		}
	};

	public static Comparator<Relation> comparatorAttr10Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(10) - o1.getAttr(10);
		}
	};

	public static Comparator<Relation> comparatorAttr11Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(11) - o2.getAttr(11);
		}
	};

	public static Comparator<Relation> comparatorAttr11Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(11) - o1.getAttr(11);
		}
	};

	public static Comparator<Relation> comparatorAttr12Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(12) - o2.getAttr(12);
		}
	};

	public static Comparator<Relation> comparatorAttr12Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(12) - o1.getAttr(12);
		}
	};

	public static Comparator<Relation> comparatorAttr13Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(13) - o2.getAttr(13);
		}
	};

	public static Comparator<Relation> comparatorAttr13Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(13) - o1.getAttr(13);
		}
	};

	public static Comparator<Relation> comparatorAttr14Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(14) - o2.getAttr(14);
		}
	};

	public static Comparator<Relation> comparatorAttr14Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(14) - o1.getAttr(14);
		}
	};

	public static Comparator<Relation> comparatorAttr15Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(15) - o2.getAttr(15);
		}
	};

	public static Comparator<Relation> comparatorAttr15Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(15) - o1.getAttr(15);
		}
	};

	public static Comparator<Relation> comparatorAttr16Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(16) - o2.getAttr(16);
		}
	};

	public static Comparator<Relation> comparatorAttr16Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(16) - o1.getAttr(16);
		}
	};

	public static Comparator<Relation> comparatorAttr17Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(17) - o2.getAttr(17);
		}
	};

	public static Comparator<Relation> comparatorAttr17Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(17) - o1.getAttr(17);
		}
	};

	public static Comparator<Relation> comparatorAttr18Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(18) - o2.getAttr(18);
		}
	};

	public static Comparator<Relation> comparatorAttr18Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(18) - o1.getAttr(18);
		}
	};

	public static Comparator<Relation> comparatorAttr19Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(19) - o2.getAttr(19);
		}
	};

	public static Comparator<Relation> comparatorAttr19Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(19) - o1.getAttr(19);
		}
	};

	public static Comparator<Relation> comparatorAttr20Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(20) - o2.getAttr(20);
		}
	};

	public static Comparator<Relation> comparatorAttr20Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(20) - o1.getAttr(20);
		}
	};

	public static Comparator<Relation> comparatorAttr21Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(21) - o2.getAttr(21);
		}
	};

	public static Comparator<Relation> comparatorAttr21Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(21) - o1.getAttr(21);
		}
	};

	public static Comparator<Relation> comparatorAttr22Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(22) - o2.getAttr(22);
		}
	};

	public static Comparator<Relation> comparatorAttr22Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(22) - o1.getAttr(22);
		}
	};

	public static Comparator<Relation> comparatorAttr23Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(23) - o2.getAttr(23);
		}
	};

	public static Comparator<Relation> comparatorAttr23Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(23) - o1.getAttr(23);
		}
	};

	public static Comparator<Relation> comparatorAttr24Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(24) - o2.getAttr(24);
		}
	};

	public static Comparator<Relation> comparatorAttr24Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(24) - o1.getAttr(24);
		}
	};

	public static Comparator<Relation> comparatorAttr25Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(25) - o2.getAttr(25);
		}
	};

	public static Comparator<Relation> comparatorAttr25Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(25) - o1.getAttr(25);
		}
	};

	public static Comparator<Relation> comparatorAttr26Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(26) - o2.getAttr(26);
		}
	};

	public static Comparator<Relation> comparatorAttr26Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(26) - o1.getAttr(26);
		}
	};

	public static Comparator<Relation> comparatorAttr27Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(27) - o2.getAttr(27);
		}
	};

	public static Comparator<Relation> comparatorAttr27Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(27) - o1.getAttr(27);
		}
	};

	public static Comparator<Relation> comparatorAttr28Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(28) - o2.getAttr(28);
		}
	};

	public static Comparator<Relation> comparatorAttr28Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(28) - o1.getAttr(28);
		}
	};

	public static Comparator<Relation> comparatorAttr29Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(29) - o2.getAttr(29);
		}
	};

	public static Comparator<Relation> comparatorAttr29Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(29) - o1.getAttr(29);
		}
	};

	public static Comparator<Relation> comparatorAttr30Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(30) - o2.getAttr(30);
		}
	};

	public static Comparator<Relation> comparatorAttr30Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(30) - o1.getAttr(30);
		}
	};

	public static Comparator<Relation> comparatorAttr31Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(31) - o2.getAttr(31);
		}
	};

	public static Comparator<Relation> comparatorAttr31Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(31) - o1.getAttr(31);
		}
	};

	public static Comparator<Relation> comparatorAttr32Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(32) - o2.getAttr(32);
		}
	};

	public static Comparator<Relation> comparatorAttr32Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(32) - o1.getAttr(32);
		}
	};

	public static Comparator<Relation> comparatorAttr33Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(33) - o2.getAttr(33);
		}
	};

	public static Comparator<Relation> comparatorAttr33Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(33) - o1.getAttr(33);
		}
	};

	public static Comparator<Relation> comparatorAttr34Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(34) - o2.getAttr(34);
		}
	};

	public static Comparator<Relation> comparatorAttr34Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(34) - o1.getAttr(34);
		}
	};

	public static Comparator<Relation> comparatorAttr35Asc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o1.getAttr(35) - o2.getAttr(35);
		}
	};

	public static Comparator<Relation> comparatorAttr35Desc = new Comparator<Relation>() {
		@Override
		public int compare(Relation o1, Relation o2) {
			return o2.getAttr(35) - o1.getAttr(35);
		}
	};

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Integer i : attrMap.keySet()) {
			sb.append(i + "-" + attrMap.get(i) + " ");
		}

		sb.append("POSVAL: " + posVal + "\n");
		return sb.toString();
	}

	public static Comparator<Relation> getComparator(int i, boolean asc) {

		if (asc) {
			switch (i) {
			case 1:
				return comparatorAttr1Asc;
			case 2:
				return comparatorAttr2Asc;
			case 3:
				return comparatorAttr3Asc;
			case 4:
				return comparatorAttr4Asc;
			case 5:
				return comparatorAttr5Asc;
			case 6:
				return comparatorAttr6Asc;
			case 7:
				return comparatorAttr7Asc;

			case 8:
				return comparatorAttr8Asc;
			case 9:
				return comparatorAttr9Asc;
			case 10:
				return comparatorAttr10Asc;
			case 11:
				return comparatorAttr11Asc;
			case 12:
				return comparatorAttr12Asc;
			case 13:
				return comparatorAttr13Asc;
			case 14:
				return comparatorAttr14Asc;

			case 15:
				return comparatorAttr15Asc;
			case 16:
				return comparatorAttr16Asc;
			case 17:
				return comparatorAttr17Asc;
			case 18:
				return comparatorAttr18Asc;
			case 19:
				return comparatorAttr19Asc;
			case 20:
				return comparatorAttr20Asc;
			case 21:
				return comparatorAttr21Asc;

			case 22:
				return comparatorAttr22Asc;
			case 23:
				return comparatorAttr23Asc;
			case 24:
				return comparatorAttr24Asc;
			case 25:
				return comparatorAttr25Asc;
			case 26:
				return comparatorAttr26Asc;
			case 27:
				return comparatorAttr27Asc;
			case 28:
				return comparatorAttr28Asc;

			case 29:
				return comparatorAttr29Asc;
			case 30:
				return comparatorAttr30Asc;
			case 31:
				return comparatorAttr31Asc;
			case 32:
				return comparatorAttr32Asc;
			case 33:
				return comparatorAttr33Asc;
			case 34:
				return comparatorAttr34Asc;
			case 35:
				return comparatorAttr35Asc;

			}
		} else {
			switch (i) {
			case 1:
				return comparatorAttr1Desc;
			case 2:
				return comparatorAttr2Desc;
			case 3:
				return comparatorAttr3Desc;
			case 4:
				return comparatorAttr4Desc;
			case 5:
				return comparatorAttr5Desc;
			case 6:
				return comparatorAttr6Desc;
			case 7:
				return comparatorAttr7Desc;

			case 8:
				return comparatorAttr8Desc;
			case 9:
				return comparatorAttr9Desc;
			case 10:
				return comparatorAttr10Desc;
			case 11:
				return comparatorAttr11Desc;
			case 12:
				return comparatorAttr12Desc;
			case 13:
				return comparatorAttr13Desc;
			case 14:
				return comparatorAttr14Desc;

			case 15:
				return comparatorAttr15Desc;
			case 16:
				return comparatorAttr16Desc;
			case 17:
				return comparatorAttr17Desc;
			case 18:
				return comparatorAttr18Desc;
			case 19:
				return comparatorAttr19Desc;
			case 20:
				return comparatorAttr20Desc;
			case 21:
				return comparatorAttr21Desc;

			case 22:
				return comparatorAttr22Desc;
			case 23:
				return comparatorAttr23Desc;
			case 24:
				return comparatorAttr24Desc;
			case 25:
				return comparatorAttr25Desc;
			case 26:
				return comparatorAttr26Desc;
			case 27:
				return comparatorAttr27Desc;
			case 28:
				return comparatorAttr28Desc;

			case 29:
				return comparatorAttr29Desc;
			case 30:
				return comparatorAttr30Desc;
			case 31:
				return comparatorAttr31Desc;
			case 32:
				return comparatorAttr32Desc;
			case 33:
				return comparatorAttr33Desc;
			case 34:
				return comparatorAttr34Desc;
			case 35:
				return comparatorAttr35Desc;

			}
		}

		return null;

	}

	public int getAttr(int operand) {

		return attrMap.get((int) operand);
	}

}
