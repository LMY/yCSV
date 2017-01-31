
public abstract class ConditionAbstract
{
	public abstract boolean Check(CSVDataFile csv, int entryn);
	public abstract int[] Check(CSVDataFile csv);
	
	
	public static final int CONDITION_L = 0;
	public static final int CONDITION_LEQ = 1;
	public static final int CONDITION_G = 2;
	public static final int CONDITION_GEQ = 3;
	public static final int CONDITION_EQ = 4;
	public static final int CONDITION_NEQ = 5;
	public static final int CONDITION_BET = 6;
	
	public static final int CONDITION_AND = 7;
	public static final int CONDITION_OR = 8;

	
	
	protected int cond_type;

	public int getCond_type() {
		return cond_type;
	}
	public void setCond_type(int cond_type) {
		this.cond_type = cond_type;
	}
	
	
	public CSVDataFile Select(CSVDataFile document)
	{
		int[] indexes = Check(document);
		if (indexes == null)
			return null;
		
		return document.Subset(indexes);
	}
	
	
	public static CSVDataFile Select(CSVDataFile document, ConditionAbstract condition)
	{
		return condition.Select(document);	
	}
	
	
	
	public static CSVDataFile[] Select(CSVDataFile document, ConditionAbstract[] condition)
	{
		CSVDataFile[] array = new CSVDataFile[condition.length];
		
		for (int i=0; i<array.length; i++)
			array[i] = Select(document, condition[i]);
		
		return array;			
	}
	
	
	
	public static CSVDataFile[][] Select(CSVDataFile document, ConditionAbstract[][] condition)
	{
		CSVDataFile[][] array = new CSVDataFile[condition.length][];
		
		for (int i=0; i<array.length; i++)
			array[i] = Select(document, condition[i]);
		
		return array;			
	}
	
	
	
	public static CSVDataFile[][][] Select(CSVDataFile document, ConditionAbstract[][][] condition)
	{
		CSVDataFile[][][] array = new CSVDataFile[condition.length][][];
		
		for (int i=0; i<array.length; i++)
			array[i] = Select(document, condition[i]);
		
		return array;			
	}		
}
