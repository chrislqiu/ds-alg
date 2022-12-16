import java.util.Comparator;

public class EdgeSort implements Comparator<Edge> 
{
    public int compare(Edge a, Edge b) 
    {
	    int diff = a.w - b.w;
	    int stopSort = b.level- a.level;
        if (stopSort != 0) {
            return stopSort;
        }
        if(diff == 0)
        {
            diff = a.u.compareTo(b.u);
        }
	    
        if(diff == 0) 
        {
            diff = a.v.compareTo(b.v);
        }
	    
        return diff;
    }
}
