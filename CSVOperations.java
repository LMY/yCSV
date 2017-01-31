/**
 * @(#)CSVOperations.java
 *
 *
 * @author Miro Salvagni
 * @version 1.00 2010/10/29
 */

//import java.io.*;
import java.util.*;
import java.io.*;
//import java.awt.*;
//import javax.swing.*;

//import javax.swing.JOptionPane;


class CSVOperations
{
	// create day,month,year,hour,minutes Series from DateTime
	public static void UTC_to_Date(CSVDataFile csv)
	{

	}


	// create DateTime Series from day,month,year,hour,minutes
	public static void Date_to_UTC(CSVDataFile csv)
	{

	}


	// converts text Date to UTC
	public static void TextDate_to_UTC(CSVFile csv)
	{

	}


	// converts text Date to day,month,year,hour,minutes Series
	public static void TextDate_to_Date(CSVFile csv)
	{

	}


	public static CSVFile Data_to_Text(CSVDataFile csv)
	{
		CSVFile text = new CSVFile();
		text.New(csv.getFieldnames());


		return text;
	}


	public static CSVDataFile Text_to_Data(CSVFile csv)
	{
		return new CSVDataFile(csv);
	}


	public static void CleanInvalid(CSVFile csv)
	{
		LinkedList<String[]> values = csv.getStringValues();

		for (ListIterator<String[]> iterator = values.listIterator(); iterator.hasNext();) {
			String[] line = iterator.next();

			boolean to_be_removed = false;
			for (int field=24; field <34; field++)
				if (line[field].equals("0"))
					to_be_removed = true;

			if (to_be_removed)
				iterator.remove();
		}
	}


	
	public static boolean out_correlations(CSVDataFile data, String filename)
	{
		BufferedWriter out = null;
		try { out = new BufferedWriter(new FileWriter(new File(filename))); }
		catch (IOException e) { return false; }		
		
		for (int x=0; x<data.getFieldNumber(); x++)
			try {
				out.write((x==0?"#"+data.getFieldname(x):"")+data.getFieldname(x)+(x==data.getFieldNumber()-1?"\n":";"));
			}
			catch (IOException e) { return false; }

		/*
		for (int x=0; x<data.getFieldNumber(); x++) {
			for (int y=0; y<data.getFieldNumber(); y++)
				try {
//					out.write((y==0?data.getFieldname(x)+";":";")+Functions.correlation(data.getSerie(x), data.getSerie(y)));	// correlation matrix, column1 is name. as csv.
//					out.write((y==0?"":" ")+Functions.correlation(data.getSerie(x), data.getSerie(y)));							// correlation matrix, column1 is empty. as white space separed.
					
//					out.write(""+x+" "+y+" "+Functions.correlation(data.getDoubleSerie(x), data.getDoubleSerie(y))+"\n");		// correlation for gnuplot
				}
				catch (IOException e) { return false; }

			try { out.write("\n"); }
			catch (IOException e) { return false; }
		}*/
			
		double[][] corrmatrix = new double[data.getFieldNumber()][];
		for (int y=0; y<corrmatrix.length; y++)
			corrmatrix[y] = new double[corrmatrix.length];

		for (int y=0; y<corrmatrix.length; y++)
			for (int x=0; x<corrmatrix.length; x++)
				corrmatrix[y][x] = Functions.correlation(data.getDoubleSerie(x), data.getDoubleSerie(y));
			
		for (int x=0; x<corrmatrix.length; x++) {
			for (int y=0; y<corrmatrix.length; y++)
				try {
					out.write(""+x+" "+y+" "+corrmatrix[y][x]+"\n");
					out.write(""+x+" "+(y+1)+" "+corrmatrix[y][x]+"\n");
				}
				catch (IOException e) { return false; }

			try { out.write("\n"); }
			catch (IOException e) { return false; }
				
			for (int y=0; y<corrmatrix.length; y++)
				try {
					out.write(""+(x+1)+" "+y+" "+corrmatrix[y][x]+"\n");
					out.write(""+(x+1)+" "+(y+1)+" "+corrmatrix[y][x]+"\n");
				}
				catch (IOException e) { return false; }

			try { out.write("\n"); }
			catch (IOException e) { return false; }				
		}
			
		
		try { out.close(); }
		catch (IOException e) { return false; }
		
		return true;
	}




	public static CSVDataFile CleanAllInvalid(CSVDataFile csv)
	{
/*		String[] series_names = { "Leq(A)_south", "Leq(A)_north", "Lmax(A)_south", "Lmax(A)_north", "Lmax(Lin)_south", "Lmax(Lin)_north", "Lmin(A)_south", "Lmin(A)_north", "Lmin(Lin)_south", "Lmin(Lin)_north", "SPD_VL_north", "SPD_VL_north_fast", "SPD_VL_south_slow", "SPD_VL_south_fast", "SPD_VL_tot", "SPD_VL_north_slow", "SPD_VL_south", "SPD_VP_north", "SPD_VP_north_fast", "SPD_VP_south_slow",
			"SPD_VP_south_fast", "SPD_VP_tot", "SPD_VP_north_slow", "SPD_VP_south", "SPD_VTot_north", "SPD_VTot_north_fast", "SPD_VTot_south_slow", "SPD_VTot_south_fast", "SPD_VTot_tot", "SPD_VTot_north_slow", "SPD_VTot_south", "VOLUME_VL_north", "VOLUME_VL_north_fast", "VOLUME_VL_south_slow", "VOLUME_VL_south_fast",
			"VOLUME_VL_tot", "VOLUME_VL_north_slow", "VOLUME_VL_south", "VOLUME_VP_north", "VOLUME_VP_north_fast", "VOLUME_VP_south_slow", "VOLUME_VP_south_fast", "VOLUME_VP_tot", "VOLUME_VP_north_slow", "VOLUME_VP_south", "VOLUME_VTot_north", "VOLUME_VTot_north_fast", "VOLUME_VTot_south_slow", "VOLUME_VTot_south_fast", "VOLUME_VTot_tot", "VOLUME_VTot_north_slow", "VOLUME_VTot_south" };
*/
		String[] series_names = { "Leq(A)_south", "Leq(A)_north", "Lmax(A)_south", "Lmax(A)_north", "Lmax(Lin)_south", "Lmax(Lin)_north", "Lmin(A)_south", "Lmin(A)_north", "Lmin(Lin)_south", "Lmin(Lin)_north" };


		boolean[] valid = new boolean[csv.getLineNumber()];
		for (int i=0; i<valid.length; i++)
			valid[i] = true;

		for (int s=0; s<series_names.length; s++) {
			double[] serie = csv.getDoubleSerieByName(series_names[s]);
			if (serie == null)							// serie not present, skip
				continue;

			for (int x=0; x<serie.length; x++)
				if (serie[x] <= 0)
					valid[x] = false;					// &=
		}

		CSVDataFile newcsv = new CSVDataFile();

		int tot_remove = 0;
		for (int i=0; i<valid.length; i++)
			if (!valid[i])
				tot_remove++;

		for (int s=0; s<csv.getFieldNumber(); s++) {
			double[] cur_serie = csv.getDoubleSerie(s);
			double[] new_serie = new double[valid.length-tot_remove];
			int k=0;

			for (int i=0; i<valid.length; i++)
				if (valid[i])
					new_serie[k++] = cur_serie[i];

			newcsv.AddDoubleSerie(csv.getFieldname(s), new_serie);
		}

		return newcsv;
	}

	public static CSVDataFile[] cutWeeks(CSVDataFile csv)
	{
		double[] day = csv.getDoubleSerieByName("day");
		if (day == null)
			day = csv.getDoubleSerieByName("#day");
		
		double[] month = csv.getDoubleSerieByName("month");
		double[] year = csv.getDoubleSerieByName("year");

		if (day==null || month==null || year==null)
			return null;
		
		int day_count = 1;
		for (int i=1; i<day.length; i++)
			if (day[i] != day[i-1]  ||  month[i] != month[i-1]  ||  year[i] != year[i-1])
				day_count++;

		CSVDataFile[] out = new CSVDataFile[(int) Math.ceil((double)day_count/7)];
		int index_from=0;
		int index_to=1;

		for (int i=0; i<out.length; i++) {
			out[i] = new CSVDataFile();
			
			int dayc = 0;
			while (dayc < (i==0?7:8) && index_to != day.length) {			// ...qui si rileverà un cambio di segno di più
				if (day[index_to] != day[index_to-1]  ||  month[index_to] != month[index_to-1]  ||  year[index_to] != year[index_to-1])
					dayc++;
				
				index_to++;
			}
			
			if (index_to != day.length-1)
				index_to--;													// a causa di questo "--"...
			
			
			for (int s=0; s<csv.getFieldNumber(); s++) {
				double[] serie = csv.getDoubleSerie(s);
				double[] newserie = new double[index_to-index_from];
				
				for (int k=index_from; k<index_to; k++)
					newserie[k-index_from] = serie[k];
				
				out[i].AddDoubleSerie(csv.getFieldname(s), newserie);
			}
			
			index_from = index_to;
		}
		
		return out;
	}
	
	
	public static CSVDataFile[] cutWeeks_DayOfWeek(CSVDataFile csv)
	{
		double[] day = csv.getDoubleSerieByName("day");
		if (day == null)
			day = csv.getDoubleSerieByName("#day");
		
		double[] month = csv.getDoubleSerieByName("month");
		double[] year = csv.getDoubleSerieByName("year");
		double[] day_of_week = csv.getDoubleSerieByName("day_of_week");

		if (day==null || month==null || year==null  ||  day_of_week==null)
			return null;
		
		int week_count = 1;
		for (int i=1; i<day_of_week.length; i++)
			if (day_of_week[i-1]==7 && day_of_week[i]==1)
				week_count++;

		CSVDataFile[] out = new CSVDataFile[week_count];
		int index_from=0;
		int index_to=1;

		for (int i=0; i<out.length; i++) {
			out[i] = new CSVDataFile();
			
			while (index_to != day.length) {
				if (day_of_week[index_to]==1 && day_of_week[index_to-1]==7)
					break;
				
				index_to++;
			}
			
			for (int s=0; s<csv.getFieldNumber(); s++) {
				double[] serie = csv.getDoubleSerie(s);
				double[] newserie = new double[index_to-index_from];
				
				for (int k=index_from; k<index_to; k++)
					newserie[k-index_from] = serie[k];
				
				out[i].AddDoubleSerie(csv.getFieldname(s), newserie);
			}
			
			index_from = index_to;
			index_to++;
		}
		
		return out;
	}
	public static CSVDataFile calculate_mean(CSVDataFile csv)
	{
		double[] series = new double[csv.getFieldNumber()];
		int[] series_len = new int[series.length];

		
		double[] day_of_week = csv.getDoubleSerieByName("day_of_week");
		double[] hour = csv.getDoubleSerieByName("hour");
		double[] min = csv.getDoubleSerieByName("min");
		
		if (day_of_week == null  ||  hour == null  ||  min == null) {
//			JOptionPane.showMessageDialog(null, "ERROR in calculate_mean(CSVDataFile csv) "+(day_of_week==null?"day_of_week NULL ":"")+(hour==null?"hour NULL ":"")+(min==null?"min NULL ":""));
			return null;
		}

		boolean[] energ_mean = new boolean[csv.getFieldNumber()];

		for (int i=0; i<csv.getFieldNumber(); i++) {
			String sname = csv.getFieldname(i).toLowerCase();
			
			if (sname.startsWith("leq")  ||  sname.startsWith("lmin")  ||  sname.startsWith("lmax"))
				energ_mean[i] = true;
			else
				energ_mean[i] = false;

			double[] s = csv.getDoubleSerie(i);

			for (int t=0; t<s.length; t++) {
				if (s[t] == INVALID_DATA) continue;
				if (energ_mean[i]  &&  s[t] == 0) continue;
				
				series[i] += energ_mean[i]  ?  Math.pow(10, s[t]/10)  :  s[t];
				series_len[i]++;
			}
		}
		
		for (int i=0; i<series.length; i++)
			if (series_len[i] > 0) {
				series[i] /= series_len[i];
			
				if (energ_mean[i])
					series[i] = 10*Math.log10(series[i]);
			}
			else
				series[i] = 0;
		
		
		CSVDataFile ret = new CSVDataFile();

		for (int k=0; k<csv.getFieldNumber(); k++) {
			double[] newserie = new double[1];
			newserie[0] = series[k];
			
			ret.AddDoubleSerie(csv.getFieldname(k), newserie);
		}
		
		return ret;	
	}
	public static CSVDataFile[] calculate_meanvar(CSVDataFile csv)
	{
		double[] series = new double[csv.getFieldNumber()];
		double[] series2 = new double[series.length];
		int[] series_len = new int[series.length];

		
		double[] day_of_week = csv.getDoubleSerieByName("day_of_week");
		double[] hour = csv.getDoubleSerieByName("hour");
		double[] min = csv.getDoubleSerieByName("min");
		
		if (day_of_week == null  ||  hour == null  ||  min == null) {
//			JOptionPane.showMessageDialog(null, "ERROR in calculate_mean(CSVDataFile csv) "+(day_of_week==null?"day_of_week NULL ":"")+(hour==null?"hour NULL ":"")+(min==null?"min NULL ":""));
			return null;
		}

		boolean[] energ_mean = new boolean[csv.getFieldNumber()];

		for (int i=0; i<csv.getFieldNumber(); i++) {
			String sname = csv.getFieldname(i).toLowerCase();
			
			if (sname.startsWith("leq")  ||  sname.startsWith("lmin")  ||  sname.startsWith("lmax"))
				energ_mean[i] = true;
			else
				energ_mean[i] = false;

			double[] s = csv.getDoubleSerie(i);

			series[i] = 0;
			series2[i] = 0;
			series_len[i] = 0;
			
			for (int t=0; t<s.length; t++) {
				if (s[t] == INVALID_DATA) continue;
				if (energ_mean[i]  &&  s[t] == 0) continue;
				if (s[t] == 0) continue;					// scommentare per ignorare i valori uguali a 0 
				
				series[i] += energ_mean[i]  ?  Math.pow(10, s[t]/10)  :  s[t];
				series2[i] += Math.pow(energ_mean[i]  ?  Math.pow(10, s[t]/10)  :  s[t], 2);
				series_len[i]++;
			}

			
			// calculate mean and rms /n
			if (series_len[i] > 0) {
				series2[i] = Math.sqrt(series2[i]*series_len[i] - Math.pow(series[i], 2))/series_len[i];
				series[i] /= series_len[i];
				
				series2[i] /= series[i];		// VARIANZA/MEDIA
				
				if (energ_mean[i]) {
					series[i] = 10*Math.log10(series[i]);
					series2[i] = 10*Math.log10(series2[i]);
				}
			}
			else {
				series[i] = 0;
				series2[i] = 0;
			}
		}
		
		
		
		CSVDataFile[] ret = new CSVDataFile[2];
		ret[0] = new CSVDataFile();
		ret[1] = new CSVDataFile();

		for (int k=0; k<csv.getFieldNumber(); k++) {
			double[] newserie = new double[1];
			newserie[0] = series[k];
			ret[0].AddDoubleSerie(csv.getFieldname(k), newserie);
			
			double[] newserie2 = new double[1];
			newserie2[0] = series2[k];
			ret[1].AddDoubleSerie(csv.getFieldname(k), newserie2);
		}
		
		return ret;	
	}
	
	public static CSVDataFile calculate_sum(CSVDataFile csv)
	{
		double[] series = new double[csv.getFieldNumber()];
		int[] series_len = new int[series.length];

		
		double[] day_of_week = csv.getDoubleSerieByName("day_of_week");
		double[] hour = csv.getDoubleSerieByName("hour");
		double[] min = csv.getDoubleSerieByName("min");
		
		if (day_of_week == null  ||  hour == null  ||  min == null) {
//			JOptionPane.showMessageDialog(null, "ERROR in calculate_mean(CSVDataFile csv) "+(day_of_week==null?"day_of_week NULL ":"")+(hour==null?"hour NULL ":"")+(min==null?"min NULL ":""));
			return null;
		}

		boolean[] energ_mean = new boolean[csv.getFieldNumber()];

		for (int i=0; i<csv.getFieldNumber(); i++) {
			String sname = csv.getFieldname(i).toLowerCase();
			
			if (sname.startsWith("leq")  ||  sname.startsWith("lmin")  ||  sname.startsWith("lmax"))
				energ_mean[i] = true;
			else
				energ_mean[i] = false;

			double[] s = csv.getDoubleSerie(i);

			for (int t=0; t<s.length; t++) {
				if (s[t] == INVALID_DATA) continue;
				if (energ_mean[i]  &&  s[t] == 0) continue;
				
				series[i] += energ_mean[i]  ?  Math.pow(10, s[t]/10)  :  s[t];
				series_len[i]++;
			}
		}
		
		for (int i=0; i<series.length; i++)
			if (series_len[i] > 0) {
				if (energ_mean[i]) {
					series[i] /= series_len[i];
					series[i] = 10*Math.log10(series[i]);
				}
			}
			else
				series[i] = 0;
		
		
		CSVDataFile ret = new CSVDataFile();

		for (int k=0; k<csv.getFieldNumber(); k++) {
			double[] newserie = new double[1];
			newserie[0] = series[k];
			
			ret.AddDoubleSerie(csv.getFieldname(k), newserie);
		}
		
		return ret;	
	}
	
	
	
	
	public static CSVDataFile calculate_mean_week(CSVDataFile[] csv)
	{
		double[][] series = new double[csv[0].getFieldNumber()][];
		for (int i=0; i<series.length; i++)
			series[i] = new double[7*24*2];

		int[][] series_len = new int[series.length][];
		for (int i=0; i<series_len.length; i++)
			series_len[i] = new int[7*24*2];		
		
		for (int i=0; i<csv.length; i++) {
			double[] day_of_week = csv[i].getDoubleSerieByName("day_of_week");
			double[] hour = csv[i].getDoubleSerieByName("hour");
			double[] min = csv[i].getDoubleSerieByName("min");
			
			if (day_of_week == null  ||  hour == null  ||  min == null)
				continue;
			
			for (int k=0; k<csv[i].getFieldNumber(); k++) {
				double[] s = csv[i].getDoubleSerie(k);
				
				for (int w=0; w<s.length; w++) {
					if (s[w] == INVALID_DATA) continue;
					int index = index_of_daytime((int) day_of_week[w], (int) hour[w], (int) min[w]);
					series[k][index] += s[w];
					series_len[k][index]++;
				}
			}
		}
		
		for (int i=0; i<series.length; i++)
			for (int k=0; k<series[i].length; k++)
				if (series_len[i][k] > 0)
					series[i][k] /= series_len[i][k];
				else
					series[i][k] = 0;
		
		CSVDataFile ret = new CSVDataFile();

		for (int k=0; k<csv[0].getFieldNumber(); k++)
			ret.AddDoubleSerie(csv[0].getFieldname(k), series[k]);
		
		
		return ret;	
	}
	
	private static double INVALID_DATA = -9999;
	public static void setInvalidData(double NEW_INVALID_DATA_CONSTANT) { INVALID_DATA = NEW_INVALID_DATA_CONSTANT; }
	
	
	public static CSVDataFile[] calculate_meanvar_week(CSVDataFile[] csv)
	{
		double[][] series = new double[csv[0].getFieldNumber()][];
		for (int i=0; i<series.length; i++)
			series[i] = new double[7*24*2];

		double[][] series2 = new double[csv[0].getFieldNumber()][];
		for (int i=0; i<series2.length; i++)
			series2[i] = new double[7*24*2];

		
		int[][] series_len = new int[series.length][];
		for (int i=0; i<series_len.length; i++)
			series_len[i] = new int[7*24*2];		
		
		for (int i=0; i<csv.length; i++) {
			double[] day_of_week = csv[i].getDoubleSerieByName("day_of_week");
			double[] hour = csv[i].getDoubleSerieByName("hour");
			double[] min = csv[i].getDoubleSerieByName("min");
			
			if (day_of_week == null  ||  hour == null  ||  min == null)
				continue;
			
			for (int k=0; k<csv[i].getFieldNumber(); k++) {
				double[] s = csv[i].getDoubleSerie(k);
				
				
				for (int w=0; w<s.length; w++) {
					if (s[w] == INVALID_DATA) continue;
					int index = index_of_daytime((int) day_of_week[w], (int) hour[w], (int) min[w]);
					
					series[k][index] += s[w];
					series2[k][index] += Math.pow(s[w], 2);
					series_len[k][index]++;
				}
			}
		}
		
		for (int i=0; i<series.length; i++)
			for (int k=0; k<series[i].length; k++)
				if (series_len[i][k] > 0) {
					series[i][k] /= series_len[i][k];
					series2[i][k] = series2[i][k]/series_len[i][k] - Math.pow(series[i][k], 2);
				}
				else {
					series[i][k] = 0;
					series2[i][k] = 0;
				}
		
		CSVDataFile[] ret = new CSVDataFile[2];
		ret[0] = new CSVDataFile();
		ret[1] = new CSVDataFile();

		for (int k=0; k<csv[0].getFieldNumber(); k++) {
			ret[0].AddDoubleSerie(csv[0].getFieldname(k), series[k]);
			ret[1].AddDoubleSerie(csv[0].getFieldname(k), series2[k]);
		}
		
		
		return ret;	
	}
	
	// calcola media e varianza SOLO dei dati che rientrano in [media-k_fact*var; media+k_fact*var]
	public static CSVDataFile[] calculate_meanvar_cleaned_week(CSVDataFile[] csv, CSVDataFile mean, CSVDataFile var, double k_fact)
	{
		double[][] series = new double[csv[0].getFieldNumber()][];
		for (int i=0; i<series.length; i++)
			series[i] = new double[7*24*2];

		double[][] series2 = new double[csv[0].getFieldNumber()][];
		for (int i=0; i<series2.length; i++)
			series2[i] = new double[7*24*2];

		
		int[][] series_len = new int[series.length][];
		for (int i=0; i<series_len.length; i++)
			series_len[i] = new int[7*24*2];		
		
		for (int i=0; i<csv.length; i++) {
			double[] day_of_week = csv[i].getDoubleSerieByName("day_of_week");
			double[] hour = csv[i].getDoubleSerieByName("hour");
			double[] min = csv[i].getDoubleSerieByName("min");
			
			if (day_of_week == null  ||  hour == null  ||  min == null)
				continue;
			
			for (int k=0; k<csv[i].getFieldNumber(); k++) {
				double[] s = csv[i].getDoubleSerie(k);
				double[] smean = mean.getDoubleSerie(k);
				double[] svar = var.getDoubleSerie(k);
				

				for (int w=0; w<s.length; w++) {
					int index = index_of_daytime((int) day_of_week[w], (int) hour[w], (int) min[w]);
					double rms = Math.sqrt(svar[index]);
					
					if (s[w] == INVALID_DATA) continue;
					
					if (s[w] > smean[index]-k_fact*rms  &&  s[w] < smean[index]+k_fact*rms) {
						series[k][index] += s[w];
						series2[k][index] += Math.pow(s[w], 2);
						series_len[k][index]++;
					}
				}
			}
			
		}
		
		for (int i=0; i<series.length; i++)
			for (int k=0; k<series[i].length; k++)
				if (series_len[i][k] > 0) {
					series[i][k] /= series_len[i][k];
					series2[i][k] = series2[i][k]/series_len[i][k] - Math.pow(series[i][k], 2);
				}
				else {
					series[i][k] = 0;
					series2[i][k] = 0;
				}
/*		
		String resstring = "";
		for (int i=0; i<series.length; i++) {
			double min = series_len[i][0];
			
			for (int k=1; k<series[i].length; k++)
				if (series_len[i][k] < min)
					min = series_len[i][k];
			
			resstring += (resstring.equals("")?"":", ")+min;
		}
		JOptionPane.showMessageDialog(null, "Result is:\n"+resstring, "DEBUG", JOptionPane.INFORMATION_MESSAGE);
*/		
		
		CSVDataFile[] ret = new CSVDataFile[2];
		ret[0] = new CSVDataFile();
		ret[1] = new CSVDataFile();

		for (int k=0; k<csv[0].getFieldNumber(); k++) {
			ret[0].AddDoubleSerie(csv[0].getFieldname(k), series[k]);
			ret[1].AddDoubleSerie(csv[0].getFieldname(k), series2[k]);
		}
		
		
		return ret;	
	}
	
	
	private static int index_of_daytime(int day_of_week, int h, int m)
	{
		int daybin = 2*h + (m<30?0:1);
		return 24*2*(day_of_week-1)+daybin;
	}
	
	
	public static CSVDataFile[] difference_weeks(CSVDataFile[] weeks, CSVDataFile mean)
	{
		CSVDataFile[] ret = new CSVDataFile[weeks.length];
		
		for (int i=0; i<weeks.length; i++) {
			ret[i] = new CSVDataFile();
			
			for (int k=0; k<mean.getFieldNumber(); k++) {
				String serie_name = weeks[i].getFieldname(k);
				double[] serie = weeks[i].getDoubleSerie(k);
				double[] mserie = mean.getDoubleSerie(k);
				
				double[] newserie = new double[serie.length];
				
				
				if (serie_name.equals("day") || serie_name.equals("month") || serie_name.equals("year") || serie_name.equals("hour") || serie_name.equals("min")/* || serie_name.equals("day_of_week")*/) {
					for (int w=0; w<serie.length; w++)
						newserie[w] = serie[w];
				}
				else if (serie_name.equals("day_of_week"))
					for (int w=0; w<serie.length; w++)
						newserie[w] = i;
				else {
					if (mserie.length == serie.length) {		// settimane nel mezzo
						for (int w=0; w<serie.length; w++)
							newserie[w] = Math.abs(serie[w]-mserie[w]);					
					}
					else if (i == 0) {							// prima settimana
						for (int w=0; w<serie.length; w++)
							newserie[w] = Math.abs(serie[w]-mserie[w+mserie.length - serie.length]);					
					}
					else if (i == weeks.length-1) {				// ultima settimana
						for (int w=0; w<serie.length; w++)
							newserie[w] = Math.abs(serie[w]-mserie[w]);					
					}
					else {
						Output.getInstance().outline("difference_weeks()::difference in length{week="+i+": name="+serie_name+" mserie="+mserie.length+" serie="+serie.length);
						
						for (int w=0; w<serie.length; w++)
							newserie[w] = 100;
					}
				}
				
				ret[i].AddDoubleSerie(weeks[i].getFieldname(k), newserie);
			}
		
		}			
		
		return ret;
	}
	
	public static double[] sum_difference_weeks(CSVDataFile[] weeks, int index)
	{
		double[] ret = new double[weeks.length];
		
		for (int i=0; i<weeks.length; i++) {
			double[] serie = weeks[i].getDoubleSerie(index);
			
			for (int k=0; k<serie.length; k++)
				ret[i] += Math.pow(serie[k], 2);
			
			if (serie.length != 0)
				ret[i] /= serie.length;
			else
				ret[i] = 999;
		}

		return ret;
	}
	
	public static CSVDataFile join_weeks(CSVDataFile[] weeks)
	{
		CSVDataFile res = new CSVDataFile();
		
		for (int s=0; s<weeks[0].getFieldNumber(); s++) {
			int slen = 0;
			
			for (int i=0; i<weeks.length; i++)
				slen += weeks[i].getLineNumber(s);
			
			double[] serie = new double[slen];
			int k=0;
			
			for (int i=0; i<weeks.length; i++) {
				double[] original = weeks[i].getDoubleSerie(s);
				
				for (int w=0; w<original.length; w++)
					serie[k++] = original[w];					
			}
			
			res.AddDoubleSerie(weeks[0].getFieldname(s), serie);				
		}
		
		return res;		
	}
	
	
	public static void inspect_date(CSVDataFile doc)
	{
		double[] day = doc.getDoubleSerieByName("day");
		double[] month = doc.getDoubleSerieByName("month");
		double[] year = doc.getDoubleSerieByName("year");
		double[] day_of_week = doc.getDoubleSerieByName("day_of_week");
		double[] hour = doc.getDoubleSerieByName("hour");
		double[] min = doc.getDoubleSerieByName("min");
		
		if (day==null) Output.getInstance().outline("WARNING! null=day.");
		if (month==null) Output.getInstance().outline("WARNING! null=month.");
		if (year==null) Output.getInstance().outline("WARNING! null=year.");
		if (day_of_week==null) Output.getInstance().outline("WARNING! null=day_of_week.");
		if (hour==null) Output.getInstance().outline("WARNING! null=hour.");
		if (min==null) Output.getInstance().outline("WARNING! null=min.");
		
		if (day == null && month == null && year == null && day_of_week == null && hour == null && min == null)
			Output.getInstance().outline("All time series are null. End of inspection.");
		else {
			boolean duplicated = false;
			
			for (int i=1; i<day.length; i++)
				if ((day == null || day[i] == day[i-1]) &&
						(month == null || month[i] == month[i-1]) &&
						(year == null || year[i] == year[i-1]) &&
						(day_of_week == null || day_of_week[i] == day_of_week[i-1]) &&
						(hour == null || hour[i] == hour[i-1]) &&
						(min == null || min[i] == min[i-1])) {
					Output.getInstance().outline("Duplicated at line="+i+". Date= "+(int)day[i]+"/"+(int)month[i]+"/"+(int)year[i]+"."+(int)hour[i]+":"+(int)min[i]);
					duplicated = true;
				}
			
			if (!duplicated)
				Output.getInstance().outline("No duplications found.");
			
			if (min == null)
				Output.getInstance().outline("\"min\" serie is null. End of inspection.");
			
			double lastday=0, lastmonth=0, lastyear=0, lastweek=0, lasthour=0, lastmin = 0;
			if (day!=null) lastday=day[0];
			if (month!=null) lastmonth=month[0];
			if (year!=null) lastyear=year[0];
			if (day_of_week!=null) lastweek=day_of_week[0];
			if (hour!=null) lasthour=hour[0];
			if (min!=null) lastmin=min[0];
			int count=1;
			boolean missing=false;
			
			for (int i=1; i<day.length; i++) {
				if ((day == null || day[i] == lastday) &&
						(month == null || month[i] == lastmonth) &&
						(year == null || year[i] == lastyear) &&
						(day_of_week == null || day_of_week[i] == lastweek) &&
						(hour == null || hour[i] == lasthour)) {
							if (min != null && min[i] != lastmin)	// not duplicated
								count++;
						}
				else {
					if (count < 2) {
						missing = true;
						Output.getInstance().outline("Missing line="+i+". Date= "+(int)lastday+"/"+(int)lastmonth+"/"+(int)lastyear+"."+(int)lasthour);
					}
					
					count = 1;
					if (day!=null) lastday=day[i];
					if (month!=null) lastmonth=month[i];
					if (year!=null) lastyear=year[i];
					if (day_of_week!=null) lastweek=day_of_week[i];
					if (hour!=null) lasthour=hour[i];
					if (min!=null) lastmin=min[i];
				}
			}
			
			if (!missing)
				Output.getInstance().outline("No data is missing.");			
		}
	}
	
	public static boolean clean_corrupted(CSVDataFile doc)
	{
		int serie_num = doc.getFieldNumber();
		int[] to_be_deleted = new int[serie_num];
		int to_be_deln = 0;
		
		for (int s=0; s<serie_num; s++) {
			String sname=doc.getFieldname(s);
			
			if (!sname.toLowerCase().endsWith("status") || s==0)
				continue;
				
			String sname_ref = doc.getFieldname(s-1);
			to_be_deleted[to_be_deln++] = s;
			
			Output.getInstance().outline(sname+" is correctness of "+sname_ref);
			double[] serie = doc.getDoubleSerie(s-1);
			double[] status = doc.getDoubleSerie(s);
			
			if (serie.length != status.length)
				Output.getInstance().outline("Warning: length of ["+sname+"] and ["+sname_ref+"] are diffeernt! ("+serie.length+"/"+status.length);
			
 
			// for valid series, loop through status and replace invalid values
			for (int i=0; i<serie.length; i++) {
				if (status[i] == 10)		// normal data
					;
				else if (status[i] == 3)	// traffic OK
					;
				else if (status[i] == 0)	// traffic VP status is 0 when traffic is 0
					;
				else if (status[i] == 7 && sname_ref.startsWith("Gl 24m"))
					serie[i] = 0;
				else if (status[i] == 8 && sname_ref.startsWith("Gl 24m"))
					;
				else {						// invalidate data
					serie[i] = -9999;
				}
				
			}			
		}
		
		// delete status series
		for (int k=to_be_deln-1; k>=0; k--)
			doc.Remove(to_be_deleted[k]);
		
		return (to_be_deln > 0);
	}
	
	
	
	public static void inspect_mean_datas(CSVDataFile doc)
	{
		Output.getInstance().outline("#linen: "+doc.getFieldNumber()+" x "+doc.getLineNumber());
		Output.getInstance().outline("#lines  fieldname:          mean      var      min      max      cg0=cg0%      percentili(5, 25, 50, 75, 90, 95)");
	
		for (int i=0; i<doc.getFieldNumber(); i++) {
			double sum = 0;
			double sum2 = 0;
			int count = 0;
			int cg0 = 0;
			double[] serie = doc.getDoubleSerie(i);
			double smin = serie.length > 0 ? serie[0] : 0;
			double smax = serie.length > 0 ? serie[0] : 0;

			
			for (int k=0; k<serie.length; k++) {
				sum += serie[k];
				sum2 += Math.pow(serie[k], 2);
				count++;
				
				if (serie[k] < smin)
					smin = serie[k];
				if (serie[k] > smax)
					smax = serie[k];
				if (serie[k] > 0)
					cg0++;
			}
			
			Arrays.sort(serie);
			double perc005 = serie[(int) (serie.length*0.25)];
			double perc025 = serie[(int) (serie.length*0.25)];
			double perc050 = serie[(int) (serie.length*0.50)];
			double perc075 = serie[(int) (serie.length*0.75)];
			double perc090 = serie[(int) (serie.length*0.90)];
			double perc095 = serie[(int) (serie.length*0.95)];
			
			
			
			if (count != 0) {
				sum /= count;
				sum2 = sum2/count - Math.pow(sum, 2);
				
				Output.getInstance().outline(count+" "+doc.getFieldname(i)+":          mean="+sum+"     var="+sum2+"    min="+smin+"    max="+smax+"   cg0="+cg0+" ("+(100*(double)cg0/count)+"%)   percentili("+perc005+"      "+perc025+"      "+perc050+"      "+perc075+"      "+perc090+"      "+perc095+")");
			}
			else
				Output.getInstance().outline(""+doc.getFieldname(i)+": empty serie.");
		}
	}
	
	
	// mean day
	private static int index_of_daytime_days(int day_of_week, int h, int m)
	{
		if (day_of_week == 1  &&  h < 12)
			return -1;
		
		if (day_of_week == 5  &&  h > 15)
			return -1;
		
		if (day_of_week > 5)
			return -1;
		
		return 2*h + (m<30?0:1);
	}
	
	
	// dei giorni INFRASETTIMANALI
	public static CSVDataFile[] calculate_meanvar_days(CSVDataFile[] csv)
	{
		double[][] series = new double[csv[0].getFieldNumber()][];
		for (int i=0; i<series.length; i++)
			series[i] = new double[1*24*2];

		double[][] series2 = new double[csv[0].getFieldNumber()][];
		for (int i=0; i<series2.length; i++)
			series2[i] = new double[1*24*2];

		
		int[][] series_len = new int[series.length][];
		for (int i=0; i<series_len.length; i++)
			series_len[i] = new int[1*24*2];		
		
		for (int i=0; i<csv.length; i++) {
			double[] day_of_week = csv[i].getDoubleSerieByName("day_of_week");
			double[] hour = csv[i].getDoubleSerieByName("hour");
			double[] min = csv[i].getDoubleSerieByName("min");
			
			if (day_of_week == null  ||  hour == null  ||  min == null)
				continue;
			
			for (int k=0; k<csv[i].getFieldNumber(); k++) {
				double[] s = csv[i].getDoubleSerie(k);
				
				for (int w=0; w<s.length; w++) {
					int index = index_of_daytime_days((int) day_of_week[w], (int) hour[w], (int) min[w]);
					if (index < 0)
						continue;
					
					series[k][index] += s[w];
					series2[k][index] += Math.pow(s[w], 2);
					series_len[k][index]++;
				}
			}
		}
		
		for (int i=0; i<series.length; i++)
			for (int k=0; k<series[i].length; k++)
				if (series_len[i][k] > 0) {
					series[i][k] /= series_len[i][k];
					series2[i][k] = series2[i][k]/series_len[i][k] - Math.pow(series[i][k], 2);
				}
				else {
					series[i][k] = 0;
					series2[i][k] = 0;
				}
		
		CSVDataFile[] ret = new CSVDataFile[2];
		ret[0] = new CSVDataFile();
		ret[1] = new CSVDataFile();

		for (int k=0; k<csv[0].getFieldNumber(); k++) {
			ret[0].AddDoubleSerie(csv[0].getFieldname(k), series[k]);
			ret[1].AddDoubleSerie(csv[0].getFieldname(k), series2[k]);
		}
		
		
		return ret;	
	}
	
	
	public static CSVDataFile[][] calculate_dayofweek_means(CSVDataFile[] csv)
	{
		// series[field][day_of_week][time]
		double[][][] series = new double[csv[0].getFieldNumber()][][];
		double[][][] series2 = new double[csv[0].getFieldNumber()][][];
		int[][][] series_len = new int[csv[0].getFieldNumber()][][];
		
		for (int i=0; i<series.length; i++) {
			series[i] = new double[7][];
			series2[i] = new double[7][];
			series_len[i] = new int[7][];
			
			for (int k=0; k<series[i].length; k++) {
				series[i][k] = new double[2*24];
				series2[i][k] = new double[2*24];
				series_len[i][k] = new int[2*24];
			}
		}
		
		boolean[] energ_mean = new boolean[csv[0].getFieldNumber()];
		for (int k=0; k<csv[0].getFieldNumber(); k++) {
			String sname = csv[0].getFieldname(k).toLowerCase();
			
			if (sname.startsWith("Leq")  ||  sname.startsWith("Lmin")  ||  sname.startsWith("Lmax"))
				energ_mean[k] = true;
			else
				energ_mean[k] = false;
		}
			
		
		
		for (int i=0; i<csv.length; i++) {
			double[] day_of_week = csv[i].getDoubleSerieByName("day_of_week");
			double[] hour = csv[i].getDoubleSerieByName("hour");
			double[] min = csv[i].getDoubleSerieByName("min");
			
			if (day_of_week == null  ||  hour == null  ||  min == null)
				continue;
			
			for (int k=0; k<csv[i].getFieldNumber(); k++) {
				double[] s = csv[i].getDoubleSerie(k);
				
				for (int w=0; w<s.length; w++) {
					if (s[w] == INVALID_DATA) continue;
					
					int ihour = (int) hour[w];
					int imin = (int) min[w];
					int iday_of_week = (int) day_of_week[w]-1;

					int time_index = (imin==15?0:1)+2*ihour;
					
					if (energ_mean[k]) {
						double e = Math.pow(10, s[w]/10);
						
						series[k][iday_of_week][time_index] += e;
						series2[k][iday_of_week][time_index] += Math.pow(e, 2);
					}
					else {
						series[k][iday_of_week][time_index] += s[w];
						series2[k][iday_of_week][time_index] += Math.pow(s[w], 2);
					}

					series_len[k][iday_of_week][time_index]++;

				}
			}
		}
		
		
		
		for (int i=0; i<series.length; i++)
			for (int d=0; d<series[i].length; d++)
				for (int k=0; k<series[i][d].length; k++)
					if (series_len[i][d][k] > 0) {

						series[i][d][k] /= series_len[i][d][k];
						series2[i][d][k] = series2[i][d][k]/series_len[i][d][k] - Math.pow(series[i][d][k], 2);

						if (energ_mean[k]) {
							series[i][d][k] = 10*Math.log10(series[i][d][k]);
							series2[i][d][k] = 10*Math.log10(series2[i][d][k]);
						}
					}
					else {
						series[i][d][k] = 0;
						series2[i][d][k] = 0;
					}
		
		CSVDataFile[][] ret = new CSVDataFile[7][];
		for (int d=0; d<7; d++) {
			ret[d] = new CSVDataFile[2];
			ret[d][0] = new CSVDataFile();
			ret[d][1] = new CSVDataFile();
			
			for (int i=0; i<csv[0].getFieldNumber(); i++) {
				ret[d][0].AddDoubleSerie(csv[0].getFieldname(i), series[i][d]);
				ret[d][1].AddDoubleSerie(csv[0].getFieldname(i), series2[i][d]);
			}
		}
		
		
		
		return ret;
	}
	
	
	
	// calcola media e varianza SOLO dei dati che rientrano in [media-k_fact*var; media+k_fact*var]
	public static CSVDataFile[] calculate_meanvar_cleaned_days(CSVDataFile[] csv, CSVDataFile mean, CSVDataFile var, double k_fact)
	{
		double[][] series = new double[csv[0].getFieldNumber()][];
		for (int i=0; i<series.length; i++)
			series[i] = new double[1*24*2];

		double[][] series2 = new double[csv[0].getFieldNumber()][];
		for (int i=0; i<series2.length; i++)
			series2[i] = new double[1*24*2];

		
		int[][] series_len = new int[series.length][];
		for (int i=0; i<series_len.length; i++)
			series_len[i] = new int[1*24*2];		
		
		for (int i=0; i<csv.length; i++) {
			double[] day_of_week = csv[i].getDoubleSerieByName("day_of_week");
			double[] hour = csv[i].getDoubleSerieByName("hour");
			double[] min = csv[i].getDoubleSerieByName("min");
			
			if (day_of_week == null  ||  hour == null  ||  min == null)
				continue;
			
			for (int k=0; k<csv[i].getFieldNumber(); k++) {
				double[] s = csv[i].getDoubleSerie(k);
				double[] smean = mean.getDoubleSerie(k);
				double[] svar = var.getDoubleSerie(k);
				

				for (int w=0; w<s.length; w++) {
					int index = index_of_daytime_days((int) day_of_week[w], (int) hour[w], (int) min[w]);
					if (index < 0)
						continue;

					double rms = Math.sqrt(svar[index]);
					
					if (s[w] > smean[index]-k_fact*rms  &&  s[w] < smean[index]+k_fact*rms) {
						series[k][index] += s[w];
						series2[k][index] += Math.pow(s[w], 2);
						series_len[k][index]++;
					}
				}
			}
			
		}
		
		for (int i=0; i<series.length; i++)
			for (int k=0; k<series[i].length; k++)
				if (series_len[i][k] > 0) {
					series[i][k] /= series_len[i][k];
					series2[i][k] = series2[i][k]/series_len[i][k] - Math.pow(series[i][k], 2);
				}
				else {
					series[i][k] = 0;
					series2[i][k] = 0;
				}

		CSVDataFile[] ret = new CSVDataFile[2];
		ret[0] = new CSVDataFile();
		ret[1] = new CSVDataFile();

		for (int k=0; k<csv[0].getFieldNumber(); k++) {
			ret[0].AddDoubleSerie(csv[0].getFieldname(k), series[k]);
			ret[1].AddDoubleSerie(csv[0].getFieldname(k), series2[k]);
		}
		
		
		return ret;	
	}
	
	public static final int SEASON_ALL = 0;
	public static final int SEASON_SPRING = 1;
	public static final int SEASON_SUMMER = 2;
	public static final int SEASON_AUTUMN = 3;
	public static final int SEASON_WINTER = 4;
	
	public static boolean season_is_dayof(int day, int month, int SEASON)
	{
		int lm, ld, hm, hd;
		switch (SEASON) {
			case SEASON_SPRING: ld=21; lm=3; hd=21; hm=6; break;
			case SEASON_SUMMER: ld=21; lm=6; hd=23; hm=9; break;
			case SEASON_AUTUMN: ld=23; lm=9; hd=21; hm=12; break;
			case SEASON_WINTER: ld=21; lm=12; hd=21; hm=3; break;
			default:			return true; // ld=1; lm=1; hd=31; hm=12; break;
		}

		if (month==lm)
			return (day >= ld);
		else if (month==hm)
			return (day < hd);
		else {
			if (SEASON != SEASON_WINTER)
				return (month < hm && month > lm);
			else
				return (month < hm);
		}
	}
	
	
	public static CSVDataFile calculate_mean_week_season(CSVDataFile[] csv, int SEASON)
	{
		double[][] series = new double[csv[0].getFieldNumber()][];
		for (int i=0; i<series.length; i++)
			series[i] = new double[7*24*2];

		int[][] series_len = new int[series.length][];
		for (int i=0; i<series_len.length; i++)
			series_len[i] = new int[7*24*2];		
		
		for (int i=0; i<csv.length; i++) {
			double[] day_of_week = csv[i].getDoubleSerieByName("day_of_week");
			double[] hour = csv[i].getDoubleSerieByName("hour");
			double[] min = csv[i].getDoubleSerieByName("min");
			double[] day = csv[i].getDoubleSerieByName("day");
			double[] month = csv[i].getDoubleSerieByName("month");
			
			if (day_of_week == null  ||  hour == null  ||  min == null  ||  day == null  ||  month == null)
				continue;
			
			for (int k=0; k<csv[i].getFieldNumber(); k++) {
				double[] s = csv[i].getDoubleSerie(k);
				
				for (int w=0; w<s.length; w++) {
					if (s[w] == INVALID_DATA) continue;
					if (!season_is_dayof((int) day[w], (int) month[w], SEASON)) continue;
					
					int index = index_of_daytime((int) day_of_week[w], (int) hour[w], (int) min[w]);
					series[k][index] += s[w];
					series_len[k][index]++;
				}
			}
		}
		
		for (int i=0; i<series.length; i++)
			for (int k=0; k<series[i].length; k++)
				if (series_len[i][k] > 0)
					series[i][k] /= series_len[i][k];
				else
					series[i][k] = 0;
		
		CSVDataFile ret = new CSVDataFile();

		for (int k=0; k<csv[0].getFieldNumber(); k++)
			ret.AddDoubleSerie(csv[0].getFieldname(k), series[k]);
		
		
		return ret;	
	}
	
	public static CSVDataFile aggregate(CSVDataFile[] array)
	{
		CSVDataFile file = new CSVDataFile();
//		file.New(array[0].getFieldnames());
		
		int serie_len = 0;
		for (int i=0; i<array.length; i++)
			serie_len += array[i].getLineNumber();
		
		for (int i=0; i<array[0].getFieldNumber(); i++) {
			String sname = array[0].getFieldname(i);
			
			double[] newserie = new double[serie_len];
			int pos = 0;
			
			for (int f=0; f<array.length; f++) {
				double[] serie = array[f].getDoubleSerie(i);
				
				for (int k=0; k<serie.length; k++)
					newserie[pos++] = serie[k];
			}
			
			file.AddDoubleSerie(sname, newserie);
		}
		
		return file;
	}
	
	
	public static CSVDataFile[] aggregate_meanvar_and_join(CSVDataFile[] array)
	{
		CSVDataFile[] means = new CSVDataFile[array.length];
		CSVDataFile[] rms = new CSVDataFile[array.length];
		
		
		
		for (int i=0; i<means.length; i++) {
			CSVDataFile[] res = calculate_meanvar(array[i]);
			means[i] = res[0];
			rms[i] = res[1];
		}
		
		CSVDataFile[] ret = new CSVDataFile[2];
		ret[0] = aggregate(means);
		ret[1] = aggregate(rms);
		
		return ret;
	}
	
	
	public static CSVDataFile aggregate_mean_and_join(CSVDataFile[] array)
	{
		CSVDataFile[] means = new CSVDataFile[array.length];
		
		for (int i=0; i<means.length; i++)
			means[i] = calculate_mean(array[i]);
		
		return aggregate(means);
	}
	
	public static CSVDataFile aggregate_sum_and_join(CSVDataFile[] array)
	{
		CSVDataFile[] means = new CSVDataFile[array.length];
		
		for (int i=0; i<means.length; i++)
			means[i] = calculate_sum(array[i]);
		
		return aggregate(means);
	}
}
