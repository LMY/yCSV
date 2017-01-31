
import java.io.*;

public class Plotter
{
	private int status;
	
	private BufferedWriter out;
	
	public static final String GNUPLOT_PATH = "c:\\programmi\\gnuplot\\binary\\gnuplot.exe";
	
	private String plot_filename;
	private String outdir;

	
	
	
	public Plotter(String filename)
	{
		status = 0;
		graph_counter = 0;
		plot_filename = filename;
		
		outdir = plot_filename.substring(0, plot_filename.lastIndexOf("\\"));
		outdir = outdir + (outdir.endsWith("\\") || outdir.endsWith("/") ? "" : "\\");
		
		
		try { out = new BufferedWriter(new FileWriter(new File(filename))); }
		catch (IOException e) { return; }
		
		status = 1;
		
		try { 
			out.write("set grid\n");
			out.write("set term jpeg medium size 1024,768\n");

//			out.write("set xdata time\n");
			out.write("set timefmt \"%m/%d/%y\"\n");
			//out.write("set xrange [\"03/21/95\":\"03/22/95\"]\n");
//			out.write("set format x \"%m/%d\"\n");
//			out.write("set timefmt \"%m/%d/%y %H:%M\"\n");
//			out.write("plot \"data\" using 1:3\n");		
		}
		catch (IOException e) { status = 2; return; }
	}
	
	public int getStatus() { return status; }
	
	
	public void close()
	{
		try { out.close(); status = 3; }
		catch (IOException e) { status = 2; }
		
		try {
			out = new BufferedWriter(new FileWriter(new File(outdir+"create_graphs.bat")));
			out.write("@"+GNUPLOT_PATH+" "+plot_filename);
			out.close();
		}
		catch (IOException e) { return; }
	}

	
	void setXrange(double a, double b)
	{
		try { 
			out.write("set xrange ["+a+":"+b+"]\n");
		}
		catch (IOException e) { status = 2; return; }
	}

	void setYrange(double a, double b)
	{
		try { 
			out.write("set yrange ["+a+":"+b+"]\n");
		}
		catch (IOException e) { status = 2; return; }
	}
	
	void unsetXrange(double a, double b)
	{
		try { 
			out.write("unset xrange\n");
		}
		catch (IOException e) { status = 2; return; }
	}

	void unsetYrange(double a, double b)
	{
		try { 
			out.write("unset yrange\n");
		}
		catch (IOException e) { status = 2; return; }
	}
	
	
	
	public void plot(CSVDataFile csv, String seriey, String filename)
	{
		plot(csv, "", seriey, filename);
	}
	
	public void plot(CSVDataFile csv, String seriex, String seriey, String filename)
	{
		String pic_filename = "graph_" +filename + "-" + seriey +".jpg"; 
		String real_filename = filename.endsWith("_gnu.csv") ? filename : filename + "_gnu.csv";
		
		int ix = csv.getSerieIndexByName(seriex);
		int iy = csv.getSerieIndexByName(seriey);
		
		try { 
			out.write("set output \""+pic_filename+"\"\n");
			out.write("plot \""+real_filename+"\" u " + (ix >= 0  ?  ix+1+":"  :  "")+(iy+1)+" ti \""+seriey+"\" w lp\n");
		}
		catch (IOException e) { status = 2; return; }
	}
	
	
	private int graph_counter;
	
	public void beginPlot(String filename)
	{
		try { 
			out.write("set output \""+filename+(filename.toLowerCase().endsWith(".jpg")?"":".jpg")+"\"\n");
			out.write("plot ");
		}
		catch (IOException e) { status = 2; return; }
		
		graph_counter = 0;
	}
	
	public void continuePlot(CSVDataFile csv, String seriey, String filename)
	{
		continuePlot(csv, "", seriey, seriey, filename);
	}
	
	public void continuePlot(CSVDataFile csv, String seriex, String seriey, String filename, String title)
	{
		String real_filename = filename.endsWith("_gnu.csv") ? filename : filename + "_gnu.csv";
		
		int ix = csv.getSerieIndexByName(seriex);
		int iy = csv.getSerieIndexByName(seriey);
		
		try {
			if (graph_counter++ != 0)
				out.write(", ");
					
			out.write("\""+real_filename+"\" u " + (ix >= 0  ?  ix+1+":"  :  "")+(iy+1)+" ti \""+title+"\" w lp");
		}
		catch (IOException e) { status = 2; return; }
	}
	
	public void endPlot()
	{
		try { 
			out.write("\n");
			graph_counter = 0;
		}
		catch (IOException e) { status = 2; return; }
		
		graph_counter = 0;
	}
}
