
public class ConditionFactory
{
	public static ConditionAbstract[] CreateArray_YearDays()
	{
		ConditionAbstract[] array = new ConditionAbstract[365];
		int n=0;
		
		int[] days_in_month = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		
		for (int m=0; m<12; m++)
			for (int d=0; d<days_in_month[m]; d++)
				array[n++] = ConditionComposite.AND(new Condition(Condition.CONDITION_EQ, "month", m+1), new Condition(Condition.CONDITION_EQ, "day", d+1));
		
		return array;
	}
	
	public static ConditionAbstract[][] CreateArray_YearMonthsDays()
	{
		ConditionAbstract[][] array = new ConditionAbstract[12][];
		
		int[] days_in_month = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		
		for (int m=0; m<12; m++) {
			array[m] = new ConditionAbstract[days_in_month[m]];
			
			for (int d=0; d<days_in_month[m]; d++)
				array[m][d] = ConditionComposite.AND(new Condition(Condition.CONDITION_EQ, "month", m+1), new Condition(Condition.CONDITION_EQ, "day", d+1));
		}
		
		return array;
	}
	
	
	public static ConditionAbstract[] CreateArray_Infrasettimanale_DayMins()
	{
		ConditionAbstract cond_infrasettimanale = new Condition(ConditionAbstract.CONDITION_BET, "day_of_week", 2, 5);
		ConditionAbstract[] array = CreateArray_DayMins();
		
		for (int i=0; i<array.length; i++)
			((ConditionComposite) array[i]).addCondition(cond_infrasettimanale);		
		
		return array;		
	}
	
	public static ConditionAbstract[] CreateArray_Infrasettimanale_DayHours()
	{
		ConditionAbstract cond_infrasettimanale = new Condition(ConditionAbstract.CONDITION_BET, "day_of_week", 2, 5);
		ConditionAbstract[] array = CreateArray_DayHours();
		
		for (int i=0; i<array.length; i++)
			((ConditionComposite) array[i]).addCondition(cond_infrasettimanale);		
		
		return array;		
	}	
	
	public static ConditionAbstract[] CreateArray_DayMins()
	{
		ConditionAbstract[] array = new ConditionAbstract[48];
		
		for (int i=0; i<48; i++)
			array[i] = ConditionComposite.AND(new Condition(Condition.CONDITION_EQ, "hour", (double) ((int) i/2)), 
												new Condition(Condition.CONDITION_EQ, "min", i%2==0 ? 15 : 45));
		
		return array;		
	}

	public static ConditionAbstract[] CreateArray_DayHours()
	{
		ConditionAbstract[] array = new ConditionAbstract[48];
		
		for (int i=0; i<24; i++)
			array[i] = new Condition(Condition.CONDITION_EQ, "hour", (double) i);
		
		return array;		
	}
	
	public static ConditionAbstract[] CreateArray_WeekMins()
	{
		ConditionAbstract[] array = new ConditionAbstract[48*7];

		for (int d=0; d<7; d++) {
			ConditionAbstract[] temp = 	CreateArray_DayMins();
			
			for (int t=0; t<48; t++)
				array[d*48 + t] = ConditionComposite.AND(temp[t], new Condition(Condition.CONDITION_EQ, "day_of_week", (double) d+1, 0));
		}
		
		return array;
	}
	
	
	
	public static ConditionAbstract[] CreateArray_YearMonths()
	{
		ConditionAbstract[] array = new ConditionAbstract[12];

		for (int m=0; m<12; m++)
			array[m] = new Condition(Condition.CONDITION_EQ, "month", (double) m+1, 0);
		
		return array;
	}
		
	public static ConditionAbstract[][] CreateArray_YearDayofweekHours()
	{
		ConditionAbstract[][] array = new ConditionAbstract[7][];
		
		ConditionAbstract[] cond_one_day = CreateArray_DayHours();
		
		for (int d=0; d<7; d++) {
			ConditionAbstract cond_dayofweek = new Condition(Condition.CONDITION_EQ, "day_of_week", (double) d+1, 0);
			
			array[d] = new ConditionAbstract[48];
			
			for (int t=0; t<48; t++)
				array[d][t] = ConditionComposite.AND(cond_one_day[t], cond_dayofweek);
		}

		return array;
	}
	
	
	
	public static ConditionAbstract[][] CreateArray_YearDayofweekMins()
	{
		ConditionAbstract[][] array = new ConditionAbstract[7][];
		
		ConditionAbstract[] cond_one_day = CreateArray_DayMins();
		
		for (int d=0; d<7; d++) {
			ConditionAbstract cond_dayofweek = new Condition(Condition.CONDITION_EQ, "day_of_week", (double) d+1, 0);
			
			array[d] = new ConditionAbstract[48];
			
			for (int t=0; t<48; t++)
				array[d][t] = ConditionComposite.AND(cond_one_day[t], cond_dayofweek);
		}

		return array;
	}
	
	
	public static ConditionAbstract[] CreateArray_YearDayofweekmins()
	{
		ConditionAbstract[][] array_in = CreateArray_YearDayofweekMins();
		ConditionAbstract[] array = new ConditionAbstract[array_in.length*array_in[0].length];
		
		int n=0;
		for (int y=0; y<array_in.length; y++)
			for (int x=0; x<array_in[0].length; x++)
				array[n++] = array_in[y][x];
		
		
		return array;
	}
	
	public static ConditionAbstract Create_Meteo()
	{
		ConditionAbstract cond = ConditionComposite.AND(new Condition(Condition.CONDITION_EQ, "Prec24m", 0), new Condition(Condition.CONDITION_EQ, "Basf0m", 0));
		((ConditionComposite) cond).addCondition(new Condition(Condition.CONDITION_LEQ, "WS10m", 3.0));
		
		return cond;
	}
	
	
	
	
	public static final int SEASON_SPRING = 0;
	public static final int SEASON_SUMMER = 1;
	public static final int SEASON_AUTUMN = 2;
	public static final int SEASON_WINTER = 3;
	public static final int SEASON_ALL = 10;
	
	public static ConditionAbstract Create_Season(int season)
	{
		ConditionComposite cond = null;
		
		switch (season)
		{
		case SEASON_SPRING:
			cond = new ConditionComposite(ConditionAbstract.CONDITION_OR);
			cond.addCondition(ConditionComposite.AND(new Condition(Condition.CONDITION_EQ, "month", 3), new Condition(Condition.CONDITION_GEQ, "day", 21)));
			cond.addCondition(ConditionComposite.AND(new Condition(Condition.CONDITION_EQ, "month", 6), new Condition(Condition.CONDITION_L, "day", 21)));
			cond.addCondition(ConditionComposite.AND(new Condition(Condition.CONDITION_G, "month", 3), new Condition(Condition.CONDITION_L, "month", 6)));
			return cond;
		
		case SEASON_SUMMER:
			cond = new ConditionComposite(ConditionAbstract.CONDITION_OR);
			cond.addCondition(ConditionComposite.AND(new Condition(Condition.CONDITION_EQ, "month", 6), new Condition(Condition.CONDITION_GEQ, "day", 21)));
			cond.addCondition(ConditionComposite.AND(new Condition(Condition.CONDITION_EQ, "month", 9), new Condition(Condition.CONDITION_L, "day", 23)));
			cond.addCondition(ConditionComposite.AND(new Condition(Condition.CONDITION_G, "month", 6), new Condition(Condition.CONDITION_L, "month", 9)));
			return cond;
		
		case SEASON_AUTUMN:
			cond = new ConditionComposite(ConditionAbstract.CONDITION_OR);
			cond.addCondition(ConditionComposite.AND(new Condition(Condition.CONDITION_EQ, "month", 9), new Condition(Condition.CONDITION_GEQ, "day", 23)));
			cond.addCondition(ConditionComposite.AND(new Condition(Condition.CONDITION_EQ, "month", 12), new Condition(Condition.CONDITION_L, "day", 21)));
			cond.addCondition(ConditionComposite.AND(new Condition(Condition.CONDITION_G, "month", 9), new Condition(Condition.CONDITION_L, "month", 12)));
			return cond;
		
		case SEASON_WINTER:
			cond = new ConditionComposite(ConditionAbstract.CONDITION_OR);
			cond.addCondition(ConditionComposite.AND(new Condition(Condition.CONDITION_EQ, "month", 12), new Condition(Condition.CONDITION_GEQ, "day", 21)));
			cond.addCondition(ConditionComposite.AND(new Condition(Condition.CONDITION_EQ, "month", 3), new Condition(Condition.CONDITION_L, "day", 21)));
			cond.addCondition(new Condition(Condition.CONDITION_L, "month", 3));
			return cond;

		
		default:
			cond = new ConditionComposite(ConditionAbstract.CONDITION_AND);
			cond.addCondition(new Condition(Condition.CONDITION_GEQ, "month", 1));
			cond.addCondition(new Condition(Condition.CONDITION_LEQ, "month", 12));
			cond.addCondition(new Condition(Condition.CONDITION_GEQ, "day", 1));
			cond.addCondition(new Condition(Condition.CONDITION_LEQ, "day", 31));
			return cond;
		}
	}
	
	public static ConditionAbstract[] CreateArray_YearSeasons()
	{
		ConditionAbstract[] array = new ConditionAbstract[4];
		
		for (int i=0; i<4; i++)
			array[i] = Create_Season(i);
		
		return array;
	}
	
	
	public static ConditionAbstract[][][] CreateArrays_YearSeasonsDaysofweekMins()
	{
		ConditionAbstract[] seasons = new ConditionAbstract[4];
		ConditionAbstract[][] day_of_week = CreateArray_YearDayofweekMins();
		ConditionAbstract[][][] array = new ConditionAbstract[4][][];
		
		for (int s=0; s<4; s++) {
			seasons[s] = Create_Season(s);
			
			array[s] = new ConditionAbstract[day_of_week.length][];
			
			for (int d=0; d<7; d++) {
				array[s][d] = new ConditionAbstract[day_of_week[d].length];
				
				for (int t=0; t<48; t++)
					array[s][d][t] = ConditionComposite.AND(seasons[s], day_of_week[d][t]);
			}
		}
		
		return array;
	}
	
	public static ConditionAbstract[][] CreateArray_YearSeasonsDayofweekmins()
	{
		ConditionAbstract[] yearDayofweekmins = CreateArray_YearDayofweekmins();
		ConditionAbstract[][] array = new ConditionAbstract[4][];
		
		for (int s=0; s<4; s++) {
			array[s] = new ConditionAbstract[yearDayofweekmins.length];
			
			for (int k=0; k<array[s].length; k++)
				array[s][k] = ConditionComposite.AND(Create_Season(s), yearDayofweekmins[k]);
		}
		
		return array;
	}
	
	
	
	public static final int PERIOD_ALL = 0;
	public static final int PERIOD_DAY = 1;
	public static final int PERIOD_EVENING = 2;
	public static final int PERIOD_NIGHT = 3;
	
	public static ConditionAbstract Create_ReferencePeriod(int period)
	{
		switch (period) {
			case PERIOD_DAY:		return ConditionComposite.AND(new Condition(Condition.CONDITION_GEQ, "hour", 7), new Condition(Condition.CONDITION_L, "hour", 19));
			case PERIOD_EVENING:	return ConditionComposite.AND(new Condition(Condition.CONDITION_GEQ, "hour", 19), new Condition(Condition.CONDITION_L, "hour", 23));
			case PERIOD_NIGHT:		return ConditionComposite.OR(new Condition(Condition.CONDITION_EQ, "hour", 23), new Condition(Condition.CONDITION_L, "hour", 7));
			
			default:				return ConditionComposite.AND(new Condition(Condition.CONDITION_GEQ, "hour", 0), new Condition(Condition.CONDITION_L, "hour", 24));
		}
	}
	
	
	public static ConditionAbstract Compose(ConditionAbstract a, ConditionAbstract b)
	{
		return ConditionComposite.AND(a, b);
	}

	public static ConditionAbstract[] Compose(ConditionAbstract a, ConditionAbstract[] b)
	{
		ConditionAbstract[] array = new ConditionAbstract[b.length];
		
		for (int i=0; i<array.length; i++)
			array[i] = Compose(a, b[i]);
		
		return array;
	}
	
	public static ConditionAbstract[][] Compose(ConditionAbstract a, ConditionAbstract[][] b)
	{
		ConditionAbstract[][] array = new ConditionAbstract[b.length][];
		
		for (int i=0; i<array.length; i++)
			array[i] = Compose(a, b[i]);
		
		return array;
	}
	
	public static ConditionAbstract[][][] Compose(ConditionAbstract a, ConditionAbstract[][][] b)
	{
		ConditionAbstract[][][] array = new ConditionAbstract[b.length][][];
		
		for (int i=0; i<array.length; i++)
			array[i] = Compose(a, b[i]);
		
		return array;
	}	
}
