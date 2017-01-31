/**
 * @(#)CSVAbstract.java
 *
 *
 * @author Miro Salvagni
 * @version 1.00 2010/10/28
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public abstract class CSVAbstract
{
	public final static int TYPE_TEXT = 0;
	public final static int TYPE_NUMBER = 1;
	public final static String DEFAULT_DELIMITER = ";";

	public final static String INT_NUMBER_REGEXP = "[\\+\\-]?\\d+";
	public final static String DECIMAL_EN_NUMBER_REGEXP = "[\\+\\-]?\\d+(\\.\\d+)?";
	public final static String DECIMAL_IT_NUMBER_REGEXP = "[\\+\\-]?\\d+(,\\d+)?";
	//public final static String DECIMAL__NUMBER_REGEXP = "^[+-]?\d*(([,.]\d{3})+)?([,.]\d+)?([eE][+-]?\d+)?$"



    public CSVAbstract() {}

	public abstract int getFieldNumber();
	public abstract int getLineNumber();
	public abstract int getLineNumber(int i);


	public abstract String getFieldname(int i);
	public abstract LinkedList<String> getFieldnames();

	public abstract double getDoubleValue(int serien, int entryn);
	public abstract double[] getDoubleSerie(int serien);
	public abstract LinkedList<double[]> getDoubleValues();
	public abstract double[] getDoubleSerieByName(String name);

	public abstract String getStringValue(int y, int x);
	public abstract String[] getStringSerie(int y);
	public abstract LinkedList<String[]> getStringValues();
	public abstract String[] getStringSerieByName(String name);

	public abstract boolean Read(String filename, String delimiter);
	public abstract boolean Write(String filename, String delimiter);
	public abstract boolean WriteXML(String filename);

	public abstract void New(LinkedList<String> fieldnames);

	public boolean Read(String filename)			{ return Read(filename, DEFAULT_DELIMITER); }
	public boolean Write(String filename)			{ return Write(filename, DEFAULT_DELIMITER); }


	public abstract boolean Remove(String serie);
	public abstract boolean Remove(int serien);

	public abstract boolean Rename(String oldname, String newname);

//	public abstract boolean AddSerie(String name, double[] serie);
//	public abstract boolean ImportCSV(String filename);

	public abstract void Info();
	public abstract int getType();
	
	public abstract String getInfoString();
	
	
	
	public boolean WriteFieldsfile(String filename)
	{
		BufferedWriter out = null;
		try { out = new BufferedWriter(new FileWriter(new File(filename))); }
		catch (IOException e) { return false; }

		try {
			LinkedList<String> fieldnames = getFieldnames();
			
			for (int i=0; i<fieldnames.size(); i++)
				out.write(fieldnames.get(i)+"\n");
		}
		catch (IOException e) { return false; }


		try { out.close(); }
		catch (IOException e) { return false; }

		return true;	
	}
}