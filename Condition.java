

public class Condition extends ConditionAbstract
{
	public Condition()
	{
		field_name = "";
		cond_type = CONDITION_EQ;
		val0 = val1 = 0;
	}
	
	public Condition(int type, String field_name, double val)
	{
		this.field_name = field_name;
		this.cond_type = type;
		this.val1 = this.val0 = val;
	}
	
	public Condition(int type, String field_name, double val0, double val1)
	{
		this.field_name = field_name;
		this.cond_type = type;
		this.val0 = val0; 
		this.val1 = val1;
	}
	
	
	
	private String field_name;
	private double val0;
	private double val1;
	
	
	
	public String getField_name() {
		return field_name;
	}
	public void setField_name(String field_name) {
		this.field_name = field_name;
	}
	public double getVal0() {
		return val0;
	}
	public void setVal0(double val0) {
		this.val0 = val0;
	}
	public double getVal1() {
		return val1;
	}
	public void setVal1(double val1) {
		this.val1 = val1;
	}
	
	
	
	private boolean ValueCheck(double value)
	{
		switch (cond_type)
		{
			case CONDITION_L: return (value < val0);
			case CONDITION_LEQ: return (value <= val0);
			case CONDITION_G: return (value > val0);
			case CONDITION_GEQ: return (value >= val0);
			case CONDITION_EQ: return (value == val0);
			case CONDITION_NEQ: return (value != val0);
			case CONDITION_BET: return (value >= val0  &&  value < val1);
			
			default: return false;
		}
	}
	
	
	public boolean Check(CSVDataFile csv, int entryn)
	{
		double[] values = csv.getDoubleSerieByName(field_name);
		if (values == null  ||  values.length <= entryn)
			return false;
		
		return ValueCheck(values[entryn]);
	}

	
	public int[] Check(CSVDataFile csv)
	{
		int[] temp = new int[csv.getLineNumber()];
		int len = 0;
		
		double[] values = csv.getDoubleSerieByName(field_name);
		if (values == null)
			return new int[0];

		for (int i=0; i<csv.getLineNumber(); i++)
			if (ValueCheck(values[i]))
				temp[len++] = i;

		int[] indexes = new int[len];
		for (int i=0; i<len; i++)
			indexes[i] = temp[i];
		
		return indexes;
	}
	
	
	public static Condition CreateCondition(String name, int type, double val)
	{
		Condition c = new Condition();
		c.setField_name(name);
		c.setCond_type(type);
		c.setVal0(val);
		
		return c;
	}
	
	public static Condition CreateBetweenCondition(String name, double val0, double val1)
	{
		Condition c = new Condition();
		c.setField_name(name);
		c.setCond_type(CONDITION_BET);
		c.setVal0(val0);
		c.setVal0(val1);
		
		return c;
	}	
}
