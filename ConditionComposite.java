
import java.util.*;



public class ConditionComposite extends ConditionAbstract
{
	private LinkedList<ConditionAbstract> conditions;
	
	public ConditionComposite()
	{
		this(CONDITION_AND);
	}
	
	public ConditionComposite(int type)
	{
		conditions = new LinkedList<ConditionAbstract>();
		this.setCond_type(type);
	}
	
	public LinkedList<ConditionAbstract> getConditions()	{ return conditions; }
	public void addCondition(ConditionAbstract c) 			{ conditions.addLast(c); }
	
	
	public boolean Check(CSVDataFile csv, int entryn)
	{
		for (int i=0; i<conditions.size(); i++)
			if (cond_type == CONDITION_OR   &&  conditions.get(i).Check(csv, entryn) == true)
				return true;
			else if (cond_type == CONDITION_AND   &&  conditions.get(i).Check(csv, entryn) == false)
				return false;
			
		if (cond_type == CONDITION_OR)
			return false;
		else
			return true;
	}
	
	public int[] Check(CSVDataFile csv)
	{
		int[][] indexes = new int[conditions.size()][];
		
		// get indexes result of each condition
		for (int i=0; i<indexes.length; i++)
			indexes[i] = conditions.get(i).Check(csv);
		
		int[] temp;
		int len = 0;		// length of temp
		
		
		if (cond_type == CONDITION_AND) {
			temp = new int[indexes[0].length];
			
			for (int k=0; k<indexes[0].length; k++) {
				boolean found = true;
				
				for (int i=1; i<indexes.length; i++)
					if (Arrays.binarySearch(indexes[i], indexes[0][k]) < 0) {
						found = false;
						break;
					}
				
				if (found)
					temp[len++] = indexes[0][k];
			}
		}
		else {
			int totsize = 0;
			for (int i=0; i<indexes.length; i++)
				totsize += indexes[i].length;
			
			temp = new int[totsize];
			
			int[] indx = new int[indexes.length]; // for each condition, pointer to next element
			
			while (true) {
				// if all series are terminated, quit
				boolean quit = true;
				for (int i=0; i<indexes.length; i++)
					if (indx[i] < indexes[i].length) {
						quit = false;
						break;
					}
				if (quit)
					break;
				
				// otherwise, find the min between series
				int best_serie = 0;
				while (indx[best_serie] == indexes[best_serie].length)		// get over the completed ones
					best_serie++;
				
				for (int i=best_serie+1; i<indexes.length; i++)
					if (indx[i] < indexes[i].length  &&  indexes[i][indx[i]] < indexes[best_serie][indx[best_serie]])	// considering not-completed series
						best_serie = i;
				
				int val = indexes[best_serie][indx[best_serie]];	// store min
				temp[len++] = val;
				
				for (int i=0; i<indexes.length; i++)				// advance every serie that started with that number!
					while (indx[i] < indexes[i].length  &&  indexes[i][indx[i]] == val)
						indx[i]++;
			}
		}

		// convert array length
		int[] results = new int[len];
		for (int i=0; i<len; i++)
			results[i] = temp[i];
		
		return results;
	}
	
	public static ConditionAbstract AND(ConditionAbstract a, ConditionAbstract b)
	{
		ConditionAbstract c = new ConditionComposite(CONDITION_AND);
		((ConditionComposite) c).addCondition(a);
		((ConditionComposite) c).addCondition(b);
		
		return c;
	}
	
	public static ConditionAbstract OR(ConditionAbstract a, ConditionAbstract b)
	{
		ConditionAbstract c = new ConditionComposite(CONDITION_OR);
		((ConditionComposite) c).addCondition(a);
		((ConditionComposite) c).addCondition(b);
		
		return c;
	}	
}
