package tests;

public class ResultantSet {
	public Relation r1;
	public Relation r2;

	ResultantSet(Relation rel1, Relation rel2) {
		r1 = rel1;
		r2 = rel2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((r1 == null) ? 0 : r1.hashCode());
		result = prime * result + ((r2 == null) ? 0 : r2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResultantSet other = (ResultantSet) obj;
		if (r1 == null) {
			if (other.r1 != null)
				return false;
		} else if (!r1.equals(other.r1))
			return false;
		if (r2 == null) {
			if (other.r2 != null)
				return false;
		} else if (!r2.equals(other.r2))
			return false;
		return true;
	}

	public void print(int first_sel, int second_sel) {

		switch (first_sel) {
		case 1:
			System.out.print(r1.attr1);
			break;
		case 2:
			System.out.print(r1.attr2);
			break;
		case 3:
			System.out.print(r1.attr3);
			break;
		case 4:
			System.out.print(r1.attr4);
			break;
		}
		System.out.print(", ");

		switch (second_sel) {
		case 1:
			System.out.println(r2.attr1);
			break;
		case 2:
			System.out.println(r2.attr2);
			break;
		case 3:
			System.out.println(r2.attr3);
			break;
		case 4:
			System.out.println(r2.attr4);
			break;
		}
	}
	
	

}
