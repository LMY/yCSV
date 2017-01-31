/**
 * @(#)CSVFile.java
 *
 *
 * @author Miro Salvagni
 * @version 1.00 2010/10/28
 */


import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;



public class CSVFile extends CSVAbstract
{
	public final static boolean DEBUG_MODE = false;


	protected LinkedList<String> fieldnames;
	protected LinkedList<String[]> values;

	public CSVFile()
	{
		super();
		fieldnames = new LinkedList<String>();
		values = new LinkedList<String[]>();
	}

	public int getFieldNumber()						{ return fieldnames.size(); }
	public int getLineNumber()						{ return values.size(); }
	public int getLineNumber(int i)					{ return values.get(i).length; }

	public String getFieldname(int i)				{ return fieldnames.get(i); }
	public LinkedList<String> getFieldnames()		{ return fieldnames; }

	public void setStringValue(int y, int x, String s)	{ values.get(y)[x] = s; }
	
	public String getStringValue(int y, int x)		{ return values.get(y)[x]; }
	public String[] getStringSerie(int y)			{ return values.get(y); }
	public LinkedList<String[]> getStringValues()	{ return values; }

	public void addLine(String[] line)				{ values.addLast(line); }


	public String[] getStringSerieByName(String name)
	{
		if (fieldnames.size() != values.size())
			JOptionPane.showMessageDialog(null, "fieldnames.size() != values.size() :"+fieldnames.size()+" != "+values.size());
		
		for (int i=0; i<fieldnames.size(); i++)
			if (fieldnames.get(i).equals(name))
				return values.get(i);

		return null;
	}

	public double getDoubleValue(int serien, int entryn)
	{
		String s = getStringValue(serien, entryn);
		try { return Double.parseDouble(s); }
		catch (NumberFormatException e) { return 0; }
	}

	public double[] getDoubleSerie(int serien)			{ return null; }
	public LinkedList<double[]> getDoubleValues()		{ return null; }

	public double[] getDoubleSerieByName(String name)	{ return null; }


	public boolean Remove(String serie)
	{
		for (int i=0; i<fieldnames.size(); i++)
			if (fieldnames.get(i).equals(serie))
				return Remove(i);

		return false;
	}

	public boolean Remove(int serien)
	{
		if (serien < fieldnames.size()) {
			LinkedList<String> newnames = new LinkedList<String>();

			for (int i=0; i<fieldnames.size(); i++)
				if (i != serien)
					newnames.addLast(fieldnames.get(i));

			LinkedList<String[]> newvalues = new LinkedList<String[]>();
			for (int i=0; i<values.size(); i++) {
				String[] line = values.get(i);
				String[] newline = new String[line.length-1];
				int index=0;

				for (int k=0; k<line.length; k++)
					if (k != serien)
						newline[index++] = line[k];

				newvalues.addLast(newline);
			}

			values = newvalues;
			fieldnames = newnames;

			return true;
		}
		else
			return false;
	}

	public boolean Rename(String oldname, String newname)
	{
		for (ListIterator<String> iterator = fieldnames.listIterator(); iterator.hasNext();) {
			String name = iterator.next();

			if (name.equals(oldname)) {
				iterator.remove();
				iterator.add(newname);
				return true;
			}
		}

		return false;
	}
/*
	public void OMG()
	{
		int index = -1;
		
		for (int i=0; i<fieldnames.size(); i++)
			if (fieldnames.get(i).equals("time")) {
				index = i;
				break;
			}
		
		
		if (fieldnames.size() != values.size())
			JOptionPane.showMessageDialog(null, ""+index+" fieldnames.size() != values.size() :"+fieldnames.size()+" != "+values.size());
		else
			JOptionPane.showMessageDialog(null, ""+index+" fieldnames.size() == values.size() = "+fieldnames.size());
	}*/


	public boolean Read(String filename)
	{
		return Read(filename, DEFAULT_DELIMITER);
	}


	public boolean Read(String filename, String delimiter)
	{
		BufferedReader  in = null;
		try { in = new BufferedReader( new InputStreamReader(new FileInputStream(filename), "ISO-8859-1")); }
		catch (IOException e) { return false; }

		String line;
		int linen=1;

		while (true) {
			try { line = in.readLine(); }
			catch (IOException e) { return false; }

			if (line == null)
				break;

			if (linen == 1) {
				while (line.startsWith("#"))
					line = line.substring(1);
				
				fieldnames.clear();
				String[] splitted = line.split(delimiter);

				for (int i=0; i<splitted.length; i++)
					fieldnames.addLast(splitted[i]);
			}
			else {
				String[] splitted = line.split(delimiter);

				if (splitted.length != getFieldNumber()) {
					if (DEBUG_MODE)
						System.out.println("Warning @line="+linen+" expected field number: "+getFieldNumber()+" but found: "+splitted.length);

					String[] original = splitted;
					splitted = new String[getFieldNumber()];

					for (int i=0; i<splitted.length; i++)
						splitted[i] = i<original.length ? original[i] : "";


					if (DEBUG_MODE)
						for (int k=0; k<splitted.length; k++)
							System.out.println(""+(k+1)+") "+splitted[k]+(k<original.length?"":" CORRECTED"));
				}

				values.addLast(splitted);
			}

			linen++;
		}

		try { in.close(); }
		catch (IOException e) { return false; }

		return true;
	}


	public boolean Write(String filename, String delimiter)
	{
		BufferedWriter out = null;
		try { out = new BufferedWriter(new FileWriter(new File(filename))); }
		catch (IOException e) { System.out.println("Cannot open output file ("+filename+")"); return false; }

		try {
			for (int i=0; i<fieldnames.size(); i++)
				out.write((i==0&&!fieldnames.get(0).startsWith("#") ? "#" : ";")+fieldnames.get(i));

			out.write("\n");
		}
		catch (IOException e) { System.out.println("Cannot write first line to output file ("+filename+")"); return false; }

		int linen=2;
		try {
			for (int y=0; y<values.size(); y++) {
				String[] line = values.get(y);

				for (int x=0; x<line.length; x++)
					out.write((x!=0 ? delimiter : "")+line[x]);

				out.write("\n");
				linen++;
			}
		}
		catch (IOException e) { System.out.println("Cannot write first line "+linen+"to output file ("+filename+")"); return false; }


		try { out.close(); }
		catch (IOException e) { return false; }

		return true;
	}


	public boolean WriteXML(String filename)
	{
		BufferedWriter out = null;
		try { out = new BufferedWriter(new FileWriter(new File(filename))); }
		catch (IOException e) { return false; }

		try {
			out.write("<xml>\n");

			for (int i=0; i<fieldnames.size(); i++) {
				out.write("\t<serie>\n");
				out.write("\t\t<name>"+fieldnames.get(i)+"</name>\n");

				String[] v = values.get(i);
				for (int k=0; k<v.length; k++)
					out.write("\t\t\t<value>"+v[k]+"</value>\n");

				out.write("\t</serie>\n");
			}

			out.write("</xml>\n");
		}
		catch (IOException e) { return false; }


		try { out.close(); }
		catch (IOException e) { return false; }

		return true;
	}


	public void New(LinkedList<String> fieldnames)
	{
		this.fieldnames = fieldnames;
		values = new LinkedList<String[]>();
	}

	public boolean AddLine(String[] line)
	{
		if (line.length != getFieldNumber()) {
			JOptionPane.showMessageDialog(null, "ERROR: Wrong size in CSVFile.AddLine(String[] line)");
			return false;
		}
		else {
			values.addLast(line);
			return true;
		}
	}


	public String getInfoString()
	{
		String s = "";

		// 0:it, 1:eng, 2:text, 3:generic/empty
		int prev = 0;
		int cur = 0;
		int count = 0;
		if (values.size() == 0)
			return s;
		
		String[] line = values.get(0);
		int n = getFieldNumber();

		for (int i=0; i<n; i++) {
			if (line[i].matches(INT_NUMBER_REGEXP) || line[i].equals("")) {
				if (prev == 0 || prev == 1)
					cur = prev;
				else
					cur = 3;
			}
			else if (line[i].matches(DECIMAL_IT_NUMBER_REGEXP))
				cur = 0;
			else if (line[i].matches(DECIMAL_EN_NUMBER_REGEXP))
				cur = 1;
			else
				cur = 2;

			if (prev==3 && (cur==1 || cur==0))		// if the prev field was generic and this field is a defined number, correct prev field
				prev=cur;
			
			if ((cur != prev && i != 0) || i == n-1) {
				if (i == n-1)
					count++;
					
				if (!s.equals(""))
					s += ",";
					
				if (prev == 0)
					s += "number/it";
				else if (prev == 1)
					s += "number/en";
				else if (prev == 3)
					s += "number/gen";
				else
					s += "text";
					
				s += "("+count+")";
				count=1;
			}
			else
				count++;

			prev=cur;
		}

		return s;
	}


	public void Info()
	{
		System.out.println("Field number: "+getFieldNumber());
		System.out.println("Line number: "+getLineNumber());

		int it = 0;
		int en = 0;

		for (ListIterator<String[]> iterator = values.listIterator(); iterator.hasNext();) {
			String[] line = iterator.next();

			for (int i=0; i<line.length; i++) {
				if (line[i].matches(DECIMAL_IT_NUMBER_REGEXP))
					it++;

				if (line[i].matches(DECIMAL_EN_NUMBER_REGEXP))
					en++;
			}
		}

		System.out.print("CVS type: ");

		if (it+en == 0)
			System.out.println("text.");
		else {
			double it_perc = (double)it/(it+en);

			if (it_perc == 1.0)
				System.out.println("numeric/it");
			else if (it_perc > 0.5)
				System.out.println("numeric/mixed, mostly it");
			else if (it_perc > 0)
				System.out.println("numeric/mixed, mostly end");
			else if (it_perc == 0)
				System.out.println("numeric/eng");
		}

		System.out.println();
		System.out.println("Field summary:");
		for (int i=0; i<fieldnames.size(); i++)
			System.out.println(""+(i+1)+") "+fieldnames.get(i));
	}

	public int getType()
	{
		return CSVAbstract.TYPE_TEXT;
	}

	public void Decimal2EN()
	{
		for (ListIterator<String[]> iterator = values.listIterator(); iterator.hasNext();) {
			String[] line = iterator.next();

			for (int i=0; i<line.length; i++)
				if (line[i].matches(DECIMAL_IT_NUMBER_REGEXP))
					line[i] = line[i].replace(",", ".");
		}
	}



	public void Decimal2IT()
	{
		for (ListIterator<String[]> iterator = values.listIterator(); iterator.hasNext();) {
			String[] line = iterator.next();

			for (int i=0; i<line.length; i++)
				if (line[i].matches(DECIMAL_EN_NUMBER_REGEXP))
					line[i] = line[i].replace(".", ",");
		}
	}


	public boolean Convert()
	{
		boolean ret = true;

		for (ListIterator<String[]> iterator = values.listIterator(); iterator.hasNext();) {
			String[] line = iterator.next();

			if (line.length < 2) {
				ret = false;
				continue;
			}

			StringTokenizer st = new StringTokenizer(line[0], "/-.:; ");

			int day=0, month=0, year=0, hour=0, min=0;

			try {
				day = Integer.parseInt((String) st.nextElement());
				month = Integer.parseInt((String) st.nextElement());
				year = Integer.parseInt((String) st.nextElement());
				hour = Integer.parseInt((String) st.nextElement());
				min = Integer.parseInt((String) st.nextElement());
			}
			catch (NumberFormatException e) { ret = false; continue; }


			GregorianCalendar cal = new GregorianCalendar(year, month-1, day, hour, min);
			cal.setTimeZone(TimeZone.getTimeZone("UTC"));
			line[0] = ""+cal.getTime().getTime();

			int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
			switch (day_of_week) {
				case Calendar.MONDAY: line[1] = "1"; break;
				case Calendar.TUESDAY: line[1] = "2"; break;
				case Calendar.WEDNESDAY: line[1] = "3"; break;
				case Calendar.THURSDAY: line[1] = "4"; break;
				case Calendar.FRIDAY: line[1] = "5"; break;
				case Calendar.SATURDAY: line[1] = "6"; break;
				case Calendar.SUNDAY: line[1] = "7"; break;
				default: line[1] = "-1"; ret = false;
			}	// DOMENICA è il 7

			for (int i=2; i<line.length; i++) {
				line[i] = line[i].trim();

				if (line[i].equals(""))
					line[i] = "0";
			}
		}

		Decimal2EN();

		return ret;
	}


	public boolean ConvertDates_Text2UTC() { return ConvertDates_Text2UTC(0); }
	public boolean ConvertDates_Text2UTC(int fieldn)
	{
		boolean ret = true;

		for (ListIterator<String[]> iterator = values.listIterator(); iterator.hasNext();) {
			String[] line = iterator.next();

			if (line.length < fieldn+1) {
				ret = false;
				continue;
			}

			StringTokenizer st = new StringTokenizer(line[fieldn], "/-.:; ");

			int day=0, month=0, year=0, hour=0, min=0;

			try {
				day = Integer.parseInt((String) st.nextElement());
				month = Integer.parseInt((String) st.nextElement());
				year = Integer.parseInt((String) st.nextElement());
				hour = Integer.parseInt((String) st.nextElement());
				min = Integer.parseInt((String) st.nextElement());
			}
			catch (NumberFormatException e) { line[fieldn] = "0"; ret = false; continue; }
			catch (NoSuchElementException e) { line[fieldn] = "0"; ret = false; continue; }

			GregorianCalendar cal = new GregorianCalendar(year, month-1, day, hour, min);
			cal.setTimeZone(TimeZone.getTimeZone("UTC"));
			line[fieldn] = ""+cal.getTime().getTime();
		}

		return ret;
	}



	public boolean ConvertDates_Text2Fields() { return ConvertDates_Text2Fields(0); }
	public boolean ConvertDates_Text2Fields(int fieldn)
	{
		boolean ret = true;

		LinkedList<String> new_fieldnames = new LinkedList<String>();
		for (int i=0; i<fieldn; i++)
			new_fieldnames.addLast(fieldnames.get(i));
		new_fieldnames.addLast("day");
		new_fieldnames.addLast("month");
		new_fieldnames.addLast("year");
		new_fieldnames.addLast("day_of_week");
		new_fieldnames.addLast("hour");
		new_fieldnames.addLast("min");
		for (int i=fieldn+1; i<fieldnames.size(); i++)
			new_fieldnames.addLast(fieldnames.get(i));

		fieldnames = new_fieldnames;


		LinkedList<String[]> new_values = new LinkedList<String[]>();

		for (ListIterator<String[]> iterator = values.listIterator(); iterator.hasNext();) {
			String[] line = iterator.next();

			if (line.length < fieldn+1) {
				ret = false;
				//new_values.addLast(line);	// skip this line?
				continue;
			}

			String[] new_line = new String[line.length+5];
			StringTokenizer st = new StringTokenizer(line[fieldn], "/-.:; ");

			int day=0, month=0, year=0, hour=0, min=0;

			try {
				day = Integer.parseInt((String) st.nextElement());
				month = Integer.parseInt((String) st.nextElement());
				year = Integer.parseInt((String) st.nextElement());
				hour = Integer.parseInt((String) st.nextElement());
				min = Integer.parseInt((String) st.nextElement());
			}
			catch (NumberFormatException e) { line[fieldn] = "0"; ret = false; continue; }
			catch (NoSuchElementException e) { line[fieldn] = "0"; ret = false; continue; }
			
			GregorianCalendar cal = new GregorianCalendar(year, month-1, day, hour, min);
			cal.setTimeZone(TimeZone.getTimeZone("UTC"));
			int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
			switch (day_of_week) {
			case Calendar.MONDAY: day_of_week = 1; break;
			case Calendar.TUESDAY: day_of_week = 2; break;
			case Calendar.WEDNESDAY: day_of_week = 3; break;
			case Calendar.THURSDAY: day_of_week = 4; break;
			case Calendar.FRIDAY: day_of_week = 5; break;
			case Calendar.SATURDAY: day_of_week = 6; break;
			case Calendar.SUNDAY: day_of_week = 7; break;
			default: day_of_week = 0; ret = false;
		}	// DOMENICA è il 7
			
			
			for (int i=0; i<fieldn; i++)
				new_line[i] = line[i];
			new_line[fieldn+0] = ""+day;
			new_line[fieldn+1] = ""+month;
			new_line[fieldn+2] = ""+year;
			new_line[fieldn+3] = ""+day_of_week;
			new_line[fieldn+4] = ""+hour;
			new_line[fieldn+5] = ""+min;
			for (int i=fieldn+1; i<line.length; i++)
				new_line[i+5] = line[i];

			new_values.addLast(new_line);
		}
		values = new_values;

		return ret;
	}
	
	
	public boolean ConvertDates_Finance2Fields() { return ConvertDates_Text2Fields(0); }
	public boolean ConvertDates_Finance2Fields(int fieldn)
	{
		boolean ret = true;

		LinkedList<String> new_fieldnames = new LinkedList<String>();
		for (int i=0; i<fieldn; i++)
			new_fieldnames.addLast(fieldnames.get(i));
		new_fieldnames.addLast("day");
		new_fieldnames.addLast("month");
		new_fieldnames.addLast("year");
		for (int i=fieldn+1; i<fieldnames.size(); i++)
			new_fieldnames.addLast(fieldnames.get(i));

		fieldnames = new_fieldnames;


		LinkedList<String[]> new_values = new LinkedList<String[]>();

		for (ListIterator<String[]> iterator = values.listIterator(); iterator.hasNext();) {
			String[] line = iterator.next();

			if (line.length < fieldn+1) {
				ret = false;
				//new_values.addLast(line);	// skip this line?
				continue;
			}

			String[] new_line = new String[line.length+2];
			StringTokenizer st = new StringTokenizer(line[fieldn], "/-.:; ");

			int day=0, month=0, year=0;

			try {
				year = Integer.parseInt((String) st.nextElement());
				month = Integer.parseInt((String) st.nextElement());
				day = Integer.parseInt((String) st.nextElement());
			}
			catch (NumberFormatException e) { line[fieldn] = "0"; ret = false; continue; }
			catch (NoSuchElementException e) { line[fieldn] = "0"; ret = false; continue; }
			
			for (int i=0; i<fieldn; i++)
				new_line[i] = line[i];
			new_line[fieldn+0] = ""+day;
			new_line[fieldn+1] = ""+month;
			new_line[fieldn+2] = ""+year;

			for (int i=fieldn+1; i<line.length; i++)
				new_line[i+2] = line[i];

			new_values.addLast(new_line);
		}
		values = new_values;

		return ret;
	}	
}
