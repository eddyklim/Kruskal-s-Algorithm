import java.util.Comparator;

public class EdgeComparator implements Comparator<Edge> {
	@Override
	public int compare(Edge o1, Edge o2) {
		if ((Double) o1.getData() < (Double) o2.getData())
			return -1;
		else if ((Double) o1.getData() == (Double) o2.getData())
			return 0;
		else
			return 1;
	}
}
