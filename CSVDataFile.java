/**
 * @(#)CSVDataFile.java
 *
 *
 * @author Miro Salvagni
 * @version 1.00 2010/10/28
 */


import java.io.*;
import java.util.*;

//import javax.swing.JOptionPane;


public class CSVDataFile extends CSVAbstract
{
	protected LinkedList<String> fieldnames;
	protected LinkedList<double[]> values;

	public CSVDataFile()
	{
		super();
		fieldnames = new LinkedList<String>();
		values = new LinkedList<double[]>();
	}

	public CSVDataFile(CSVFile csv)
	{
		super();
		fieldnames = new LinkedList<String>();
		values = new LinkedList<double[]>();

		ImportCSV(csv);
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
			JOptionPane.showMessageDialog(null, "DATA "+index+" fieldnames.size() != values.size() :"+fieldnames.size()+" != "+values.size());
		else
			JOptionPane.showMessageDialog(null, "DATA "+index+" fieldnames.size() == values.size() = "+fieldnames.size());
	}*/

	public int getFieldNumber()							{ return fieldnames.size(); }
	public int getLineNumber()							{ return getLineNumber(0); }
	public int getLineNumber(int i)						{ return values.size() > 0 ? values.get(i).length : 0; }



	public String getFieldname(int i)					{ return fieldnames.get(i); }
	public LinkedList<String> getFieldnames()			{ return fieldnames; }

	public double getDoubleValue(int serien, int entryn){ return values.get(serien)[entryn]; }
	public double[] getDoubleSerie(int serien)			{ return values.get(serien); }
	public LinkedList<double[]> getDoubleValues()		{ return values; }

	public double[] getDoubleSerieByName(String name)
	{
		for (int i=0; i<fieldnames.size(); i++)
			if (fieldnames.get(i).equals(name))
				return values.get(i);

		return null;
	}
	
	public int getSerieIndexByName(String name)
	{
		for (int i=0; i<fieldnames.size(); i++)
			if (fieldnames.get(i).equals(name))
				return i;

		return -1;
	}

	public String getStringValue(int y, int x)			{ return ""+values.get(y)[x]; }
	public String[] getStringSerie(int y)				{ return null; }
	public LinkedList<String[]> getStringValues()		{ return null; }
	public String[] getStringSerieByName(String name)	{ return null; }


	public boolean Remove(String serie)
	{
		ListIterator<double[]> iteratorv = values.listIterator();

		for (ListIterator<String> iterator = fieldnames.listIterator(); iterator.hasNext();) {
			String name = iterator.next();
			iteratorv.next();

			if (serie.equals(name)) {
				iterator.remove();
				iteratorv.remove();
				return true;
			}
		}

		return false;
	}

	public boolean Remove(int serien)
	{
		if (serien < fieldnames.size()) {
			fieldnames.remove(serien);
			values.remove(serien);
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



	public boolean Read(String filename, String delimiter)
	{
		CSVFile textcsv = new CSVFile();

		if (!textcsv.Read(filename, delimiter))
			return false;

		if (!textcsv.Convert())
			return false;

		fieldnames = new LinkedList<String>();
		values = new LinkedList<double[]>();

		return ImportCSV(textcsv);
	}

	
	public boolean Write(String filename, String delimiter)
	{
		return Write(filename, delimiter, false);
	}
	
	public boolean Write(String filename, String delimiter, boolean it_format)
	{
		BufferedWriter out = null;
		try { out = new BufferedWriter(new FileWriter(new File(filename))); }
		catch (IOException e) { System.out.println("Cannot open output file ("+filename+")"); return false; }

		try {
			for (int i=0; i<fieldnames.size(); i++)
				out.write((i==0  ?   (!fieldnames.get(0).startsWith("#")  ?  "#"  :  "")  :  ";")+fieldnames.get(i));

			out.write("\n");
		}
		catch (IOException e) { System.out.println("Cannot write first line to output file ("+filename+")"); return false; }

		int linen=2;
		try {
			for (int x=0; x<values.get(0).length; x++) {
				for (int y=0; y<values.size(); y++) {
					double val = values.get(y)[x];
					
					if (val - (int) val == 0)
						out.write((y!=0 ? delimiter : "")+((int)val));
					else {
						String s = (y!=0 ? delimiter : "")+val;
						
						out.write(it_format  ?  s.replaceAll("\\.", ",")  :  s);
					}
				}

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

				double[] v = values.get(i);
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
		values = new LinkedList<double[]>();
	}

	public boolean AddDoubleSerie(String name, double[] serie)
	{
//		if (serie.length != getLineNumber()) {
			fieldnames.addLast(name);
			values.addLast(serie);
			return true;
	//	}
		//else
	//		return false;
	}

	public boolean ImportCSV(String filename)
	{
		CSVFile csv = new CSVFile();

		if (!csv.Read(filename))
			return false;

		csv.Decimal2EN();

		//Convert

		return ImportCSV(csv);
	}


	public boolean ImportCSV(CSVFile csv)
	{
		boolean ret = true;

		fieldnames = csv.getFieldnames();

		double[][] v = new double[fieldnames.size()][];
		for (int i=0; i<v.length; i++)
			v[i] = new double[csv.getLineNumber()];

		for (int x=0; x<v[0].length; x++) {
			String[] line = csv.getStringValues().get(x);

			for (int y=0; y<v.length; y++)
				if (line[y].equals(""))		// empty field, convert to 0, ret value shall be true
					v[y][x] = 0;
				else
					try { v[y][x] = Double.parseDouble(line[y]); }
					catch (NumberFormatException e) { v[y][x] = 0; ret = false; }
		}

		for (int i=0; i<v.length; i++)
			values.addLast(v[i]);

		return ret ? fieldnames.size() > 0 : false;
	}




	public void Info()
	{
		System.out.println("Field number: "+getFieldNumber());
		System.out.println("Line number: "+getLineNumber());

		System.out.println("CVS type: data");

		System.out.println();
		System.out.println("Field summary:");
		for (int i=0; i<fieldnames.size(); i++)
			System.out.println(""+(i+1)+") "+fieldnames.get(i));
	}

	public int getType()
	{
		return CSVAbstract.TYPE_NUMBER;
	}

	public CSVFile toCSVFile()
	{
		CSVFile file = new CSVFile();
		file.New(fieldnames);


		
		if (values.size() > 0)
			for (int y=0; y<values.get(0).length; y++) {
				String[] line = new String[fieldnames.size()];

				for (int x=0; x<line.length; x++) {
					double val = values.get(x)[y];
					
					if (val - (int) val == 0)
						line[x] = ""+((int)val);
					else
						line[x] = ""+val;
				}

				file.addLine(line);
			}
		
		
		return file;
	}
	
	public String getInfoString()
	{
		return "number("+getFieldNumber()+")";
	}
	
	public CSVDataFile Subset(int[] indexes)
	{
		CSVDataFile file = new CSVDataFile();
		
	//	ListIterator<String> itern = fieldnames.listIterator();
		int n=0;
		
		for (ListIterator<double[]> iter = values.listIterator(); iter.hasNext();) {
		//	String serie_name = itern.next();
			String serie_name = fieldnames.get(n++);
			
			double[] serie = iter.next();
			
			double[] newserie = new double[indexes.length];
			
			for (int i=0; i<indexes.length; i++)
				newserie[i] = serie[indexes[i]];
			
			file.AddDoubleSerie(serie_name, newserie);
		}
		
		return file;
	}
	
	
	public boolean Save3(String filename)
	{
		boolean ret = true;
		
		ret &= Write(filename+".csv", ";", false);
		ret &= Write(filename+"_gnu.csv", " ", false);
		ret &= Write(filename+"_xls.csv", ";", true);
	
		return ret;
	}
}
