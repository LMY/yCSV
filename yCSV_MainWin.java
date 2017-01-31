/**
 * @(#)yCSV_MainWin.java
 *
 *
 * @author Miro Salvagni
 * @version 1.00 2010/10/28
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
//import javax.swing.event.*;
//import java.awt.datatransfer.*;
//import java.awt.dnd.*;


public class yCSV_MainWin extends JFrame implements ActionListener
{
	private CSVInfoPanel infopanel;

	static final long serialVersionUID = 61237613;
	
    public yCSV_MainWin()
    {
		super("yCSV");
        setSize(new Dimension(420, 420));
        center();

        setDelimiter(";");
        document = null;
        document_modified = false;
        document_filename = "";


     	// Look and Feel
     	setDefaultCloseOperation(EXIT_ON_CLOSE);
        try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception exception) {
			System.out.println("Invalid look and feel.");
		}

		Output.Initialize(this);

        Container content = this.getContentPane();
		content.setLayout(new BorderLayout());

		JTabbedPane tabs = new JTabbedPane();

//		tabs.add("main", new JPanel());
		infopanel = new CSVInfoPanel();
		tabs.add("info", infopanel);
		tabs.add("output", Output.getInstance().CreateOutputPanel());


        content.add(tabs, BorderLayout.CENTER);

		Menu_Create();
		Open(null);

		setVisible(true);
    }



	public void center()
	{
		Dimension screen_dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension window_dim = getSize();

		int posX = (screen_dim.width - window_dim.width) / 2;
		int posY = (screen_dim.height- window_dim.height) / 2;

		setLocation(posX, posY);
	}


	private String delimiter;

	public String getDelimiter()			{ return delimiter; }
	public void setDelimiter(String s)		{ delimiter = s; }


	private JMenuItem file_open;
	private JMenuItem file_close;
	private JMenuItem file_delimiter;
	private JMenuItem file_save;
	private JMenuItem file_saveas;
	private JMenuItem file_export_fieldnames;
	private JMenuItem file_export_week_it;	
	private JMenuItem file_export_week_en;
	private JMenuItem file_export_correlations;
	private JMenuItem file_exit;

	private JMenuItem convert_decimal;
	private JMenuItem convert_text;
	private JMenuItem convert_it;
	private JMenuItem convert_en;
	private JMenuItem convert_clean;

	private JMenuItem calculate_meanweek;
	private JMenuItem calculate_cleanmeanweek;
	private JMenuItem calculate_variance;
	private JMenuItem calculate_deltamean;
	private JMenuItem calculate_meandays;
	private JMenuItem calculate_vardays;
	
	
	private JMenuItem inspect_date;
	private JMenuItem inspect_invalid;
	private JMenuItem inspect_select;
	
	private JMenuItem thesis_weekly_graphs;
	private JMenuItem thesis_dayofweek_graphs;
	
	private JMenuItem help_help;
	private JMenuItem help_about;

	private JMenu popup_menu;
	private JMenuItem popup_first;
	private JMenuItem popup_prev;
	private JMenuItem popup_next;
	private JMenuItem popup_last;
	private JMenuItem popup_rename;
	private JMenuItem popup_remove;
	private JMenuItem popup_date_std;
	private JMenuItem popup_date_utc;
	private JMenuItem popup_date_finance;

	private JMenuItem popup_calc_d;
	private JMenuItem popup_calc_d2;
	private JMenuItem popup_calc_dperc;
	private JMenuItem popup_calc_mm;
	private JMenuItem popup_calc_emm;
	private JMenuItem popup_calc_leqm;
	private JMenuItem popup_calc_pplus;
	private JMenuItem popup_calc_p0;
	private JMenuItem popup_calc_pmin;
	private JMenuItem popup_calc_stub;



	private void Menu_Create()
	{
		JMenuBar menu = new JMenuBar();

		JMenu file = new JMenu("File");

		file_open = new JMenuItem("Open...");
		file_open.addActionListener(this);
		file_close = new JMenuItem("Close");
		file_close.addActionListener(this);
		file_delimiter = new JMenuItem("Set Delimiter... ("+delimiter+")");
		file_delimiter.addActionListener(this);
		file_save = new JMenuItem("Save");
		file_save.addActionListener(this);
		file_saveas = new JMenuItem("Save As...");
		file_saveas.addActionListener(this);
		
		file_export_fieldnames = new JMenuItem("Field names...");
		file_export_fieldnames.addActionListener(this);
		
		file_export_week_en = new JMenuItem("Weeks (en)...");
		file_export_week_en.addActionListener(this);
		file_export_week_it = new JMenuItem("Weeks (it)...");
		file_export_week_it.addActionListener(this);		
		file_export_correlations = new JMenuItem("Correlations...");
		file_export_correlations.addActionListener(this);
		
		
		
		file_exit = new JMenuItem("Exit");
		file_exit.addActionListener(this);

		file.add(file_open);
		file.add(file_close);
		file.addSeparator();
		file.add(file_delimiter);
		file.add(file_save);
		file.add(file_saveas);
		
		JMenu export = new JMenu("Export");
		export.add(file_export_fieldnames);
		export.addSeparator();
		export.add(file_export_week_en);
		export.add(file_export_week_it);
		export.addSeparator();
		export.add(file_export_correlations);
		file.add(export);
		
		file.addSeparator();
		file.add(file_exit);

		menu.add(file);


		JMenu operations = new JMenu("Conversions");
		convert_decimal = new JMenuItem("Convert to decimal");
		convert_decimal.addActionListener(this);
		convert_text = new JMenuItem("Convert to text");
		convert_text.addActionListener(this);


		convert_it = new JMenuItem("Convert to it");
		convert_it.addActionListener(this);

		convert_en = new JMenuItem("Convert to en");
		convert_en.addActionListener(this);

		convert_clean = new JMenuItem("Clean invalid datas");
		convert_clean.addActionListener(this);

		operations.add(convert_decimal);
		operations.add(convert_text);
		operations.addSeparator();
		operations.add(convert_it);
		operations.add(convert_en);
		operations.addSeparator();
		operations.add(convert_clean);

		menu.add(operations);

		
		JMenu calculations = new JMenu("Calculate");
		calculate_meanweek = new JMenuItem("Mean Week");
		calculate_meanweek.addActionListener(this);
		
		calculate_cleanmeanweek = new JMenuItem("Clean Mean Week");
		calculate_cleanmeanweek.addActionListener(this);
		
		calculate_variance = new JMenuItem("Variance Week");
		calculate_variance.addActionListener(this);
		
		calculate_deltamean = new JMenuItem("Delta from mean week");
		calculate_deltamean.addActionListener(this);
		
		calculate_meandays = new JMenuItem("Mean days");
		calculate_meandays.addActionListener(this);
		
		calculate_vardays = new JMenuItem("Variance days");
		calculate_vardays.addActionListener(this);
		
		
		calculations.add(calculate_meanweek);
		calculations.add(calculate_variance);
		calculations.add(calculate_cleanmeanweek);
		calculations.add(calculate_deltamean);
		calculations.addSeparator();
		calculations.add(calculate_meandays);
		calculations.add(calculate_vardays);
		menu.add(calculations);
		
		JMenu inspect = new JMenu("Inspect");
		inspect_date = new JMenuItem("Duplicate/missing times");
		inspect_date.addActionListener(this);
		
		inspect_invalid = new JMenuItem("Clean corrupted datas");
		inspect_invalid.addActionListener(this);
		
		inspect_select = new JMenuItem("Select...");
		inspect_select.addActionListener(this);
		
		inspect.add(inspect_date);
		inspect.add(inspect_invalid);
		inspect.addSeparator();
		inspect.add(inspect_select);
		
		menu.add(inspect);
		
		
		JMenu thesis = new JMenu("Thesis");
		thesis_weekly_graphs = new JMenuItem("Weekly graphs...");
		thesis_weekly_graphs.addActionListener(this);
		thesis_dayofweek_graphs = new JMenuItem("Weekly graphs (with meteo)...");
		thesis_dayofweek_graphs.addActionListener(this);
		
		thesis.add(thesis_weekly_graphs);
		thesis.add(thesis_dayofweek_graphs);	
		menu.add(thesis);

		
		
		
		JMenu help = new JMenu("Help");
		help_help = new JMenuItem("Help");
		help_help.addActionListener(this);

		help_about = new JMenuItem("About");
		help_about.addActionListener(this);

		help.add(help_help);
		help.addSeparator();
		help.add(help_about);

		menu.add(help);


		setJMenuBar(menu);

		popup_menu = new JMenu();

	  	popup_first = new JMenuItem("First");
	 	popup_prev = new JMenuItem("Prev");
		popup_next = new JMenuItem("Next");
		popup_last = new JMenuItem("Last");
		popup_remove = new JMenuItem("Delete");
		popup_rename = new JMenuItem("Rename");
		
		
		popup_menu.add(popup_first);
		popup_first.addActionListener(infopanel);
		popup_menu.add(popup_prev);
		popup_prev.addActionListener(infopanel);
		popup_menu.add(popup_next);
		popup_next.addActionListener(infopanel);
		popup_menu.add(popup_last);
		popup_last.addActionListener(infopanel);
		popup_menu.addSeparator();
		popup_menu.add(popup_remove);
		popup_remove.addActionListener(infopanel);
		popup_menu.add(popup_rename);
		popup_rename.addActionListener(infopanel);
		popup_menu.addSeparator();

		JMenu popup_convert = new JMenu("Convert");
		popup_date_std = new JMenuItem("Convert date to std");
		popup_date_utc = new JMenuItem("Convert date to utc");
		popup_date_finance = new JMenuItem("Convert finance to std");
			popup_convert.add(popup_date_std);
			popup_date_std.addActionListener(infopanel);
			popup_convert.add(popup_date_utc);
			popup_date_utc.addActionListener(infopanel);
			popup_convert.add(popup_date_finance);
			popup_date_finance.addActionListener(infopanel);
		popup_menu.add(popup_convert);
			
		
		JMenu popup_calc = new JMenu("Add Serie");
			popup_calc_d = new JMenuItem("Derivate");
			popup_calc_d2 = new JMenuItem("Derivate2");
			popup_calc_dperc = new JMenuItem("Derivate%");
			popup_calc_mm = new JMenuItem("MM");
		 	popup_calc_emm = new JMenuItem("EMM");
		 	popup_calc_leqm = new JMenuItem("Mean Leq");
			popup_calc_pplus = new JMenuItem("Probability plus");
			popup_calc_p0 = new JMenuItem("Probability zero");
			popup_calc_pmin = new JMenuItem("Probability minus");
			popup_calc_stub = new JMenuItem("Stub()");
			
		 	popup_calc.add(popup_calc_d);
		 	popup_calc.add(popup_calc_d2);
		 	popup_calc.add(popup_calc_dperc);
		 	popup_calc.add(popup_calc_mm);
		 	popup_calc.add(popup_calc_emm);
		 	popup_calc.add(popup_calc_leqm);
		 	popup_calc.addSeparator();
		 	popup_calc.add(popup_calc_pplus);
		 	popup_calc.add(popup_calc_p0);
		 	popup_calc.add(popup_calc_pmin);
		 	popup_calc.addSeparator();
		 	popup_calc.add(popup_calc_stub);
		 	
		 	popup_calc_d.addActionListener(infopanel);
		 	popup_calc_d2.addActionListener(infopanel);
		 	popup_calc_dperc.addActionListener(infopanel);
		 	popup_calc_mm.addActionListener(infopanel);
		 	popup_calc_emm.addActionListener(infopanel);
		 	popup_calc_leqm.addActionListener(infopanel);
		 	popup_calc_pplus.addActionListener(infopanel);
		 	popup_calc_p0.addActionListener(infopanel);
		 	popup_calc_pmin.addActionListener(infopanel);
		 	popup_calc_stub.addActionListener(infopanel);
	 	popup_menu.add(popup_calc);
		
	}


	public void Menu_NoneOpened()
	{
		file_close.setEnabled(false);
		file_save.setEnabled(false);
		file_saveas.setEnabled(false);
		file_export_fieldnames.setEnabled(false);
		file_export_week_en.setEnabled(false);
		file_export_week_it.setEnabled(false);
		file_export_correlations.setEnabled(false);

		convert_decimal.setEnabled(false);
		convert_text.setEnabled(false);
		convert_it.setEnabled(false);
		convert_en.setEnabled(false);
		convert_clean.setEnabled(false);

		calculate_meanweek.setEnabled(false);
		calculate_variance.setEnabled(false);
		calculate_cleanmeanweek.setEnabled(false);
		calculate_deltamean.setEnabled(false);
		calculate_meandays.setEnabled(false);
		calculate_vardays.setEnabled(false);
		
		inspect_date.setEnabled(false);
		inspect_invalid.setEnabled(false);
		inspect_select.setEnabled(false);
		
		thesis_weekly_graphs.setEnabled(false);
		thesis_dayofweek_graphs.setEnabled(false);
		
		
		popup_date_std.setEnabled(false);
		popup_date_utc.setEnabled(false);
		popup_date_finance.setEnabled(false);
		popup_calc_d.setEnabled(false);
		popup_calc_d2.setEnabled(false);
		popup_calc_dperc.setEnabled(false);
		popup_calc_mm.setEnabled(false);
	 	popup_calc_emm.setEnabled(false);
	 	popup_calc_leqm.setEnabled(false);
		popup_calc_pplus.setEnabled(false);
		popup_calc_p0.setEnabled(false);
		popup_calc_pmin.setEnabled(false);
		popup_calc_stub.setEnabled(false);
	}

	public void Menu_TextOpened()
	{
		file_close.setEnabled(true);
		file_save.setEnabled(true);
		file_saveas.setEnabled(true);
		file_export_fieldnames.setEnabled(true);
		file_export_week_en.setEnabled(false);
		file_export_week_it.setEnabled(false);
		file_export_correlations.setEnabled(false);

		convert_decimal.setEnabled(true);
		convert_text.setEnabled(false);
		convert_it.setEnabled(true);
		convert_en.setEnabled(true);
		convert_clean.setEnabled(false);
		
		calculate_meanweek.setEnabled(false);
		calculate_variance.setEnabled(false);
		calculate_cleanmeanweek.setEnabled(false);
		calculate_deltamean.setEnabled(false);
		calculate_meandays.setEnabled(false);
		calculate_vardays.setEnabled(false);		
		
		inspect_date.setEnabled(false);
		inspect_invalid.setEnabled(false);
		inspect_select.setEnabled(false);
		
		thesis_weekly_graphs.setEnabled(false);
		thesis_dayofweek_graphs.setEnabled(false);
		
		popup_date_std.setEnabled(true);
		popup_date_utc.setEnabled(true);
		popup_date_finance.setEnabled(true);
		popup_calc_d.setEnabled(false);
		popup_calc_d2.setEnabled(false);
		popup_calc_dperc.setEnabled(false);
		popup_calc_mm.setEnabled(false);
	 	popup_calc_emm.setEnabled(false);
	 	popup_calc_leqm.setEnabled(false);
		popup_calc_pplus.setEnabled(false);
		popup_calc_p0.setEnabled(false);
		popup_calc_pmin.setEnabled(false);
		popup_calc_stub.setEnabled(false);
	}

	public void Menu_DataOpened()
	{
		file_close.setEnabled(true);
		file_save.setEnabled(true);
		file_saveas.setEnabled(true);
		file_export_fieldnames.setEnabled(true);
		file_export_week_en.setEnabled(true);
		file_export_week_it.setEnabled(true);
		file_export_correlations.setEnabled(true);

		convert_decimal.setEnabled(false);
		convert_text.setEnabled(true);
		convert_it.setEnabled(false);
		convert_en.setEnabled(false);
		convert_clean.setEnabled(true);
		
		calculate_meanweek.setEnabled(true);
		calculate_variance.setEnabled(true);
		calculate_cleanmeanweek.setEnabled(true);
		calculate_deltamean.setEnabled(true);
		calculate_meandays.setEnabled(true);
		calculate_vardays.setEnabled(true);
		
		inspect_date.setEnabled(true);
		inspect_invalid.setEnabled(true);
		inspect_select.setEnabled(true);
		
		thesis_weekly_graphs.setEnabled(true);
		thesis_dayofweek_graphs.setEnabled(true);
		
		popup_date_std.setEnabled(false);
		popup_date_utc.setEnabled(false);
		popup_date_finance.setEnabled(false);
		popup_calc_d.setEnabled(true);
		popup_calc_d2.setEnabled(true);
		popup_calc_dperc.setEnabled(true);
		popup_calc_mm.setEnabled(true);
	 	popup_calc_emm.setEnabled(true);
	 	popup_calc_leqm.setEnabled(true);
		popup_calc_pplus.setEnabled(true);
		popup_calc_p0.setEnabled(true);
		popup_calc_pmin.setEnabled(true);
		popup_calc_stub.setEnabled(true);
	}


    public void actionPerformed(ActionEvent e)
    {
		if (e.getSource().equals(file_open)) {
			final JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File("."));

        	if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
        		if (document != null  &&  document_modified) {
        			int res = JOptionPane.showConfirmDialog(null, "Do you want to save the file before opening a new one?");

					if (res == 0)		// yes
						Save();
					else if (res == 2)	// cancel
						return;
        		}

            	Open(fc.getSelectedFile().getAbsolutePath());
        	}
		}
		else if (e.getSource().equals(file_close)) {
    		if (document != null  &&  document_modified) {
    			int res = JOptionPane.showConfirmDialog(null, "Do you want to save the file before closing it?", "Please confirm", JOptionPane.YES_NO_OPTION);

				if (res == 0)		// yes
					Save();
    		}

			Open(null);
		}
		else if (e.getSource().equals(file_delimiter)) {
			String new_delimiter =  JOptionPane.showInputDialog(this, "Enter desired delimiter... (current: "+delimiter+")");

			if (delimiter != null)
				if(!delimiter.equals(new_delimiter)) {
					delimiter = new_delimiter;
					file_delimiter.setText("Set Delimiter... ("+delimiter+")");
				}
		}
		else if (e.getSource().equals(file_save))
			Save();
		else if (e.getSource().equals(file_saveas)) {
			final JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File("."));

        	if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
	           	SaveAs(fc.getSelectedFile().getAbsolutePath());
		}
		else if (e.getSource().equals(file_exit))
			System.exit(0);

		else if (e.getSource().equals(convert_it)) {
			if (document.getType() == CSVAbstract.TYPE_TEXT) {
				((CSVFile) document).Decimal2IT();
				document_modified = true;
				infopanel.Load(document);
			}
			else
				JOptionPane.showMessageDialog(this, "Currently using data format. Can't convert to IT", "Can't Convert", JOptionPane.INFORMATION_MESSAGE);
		}
		else if (e.getSource().equals(convert_en)) {
			if (document.getType() == CSVAbstract.TYPE_TEXT) {
				((CSVFile) document).Decimal2EN();
				document_modified = true;
				infopanel.Load(document);
			}
			else
				JOptionPane.showMessageDialog(this, "Currently using data format. Can't convert to EN", "Can't Convert", JOptionPane.INFORMATION_MESSAGE);
		}
		else if (e.getSource().equals(convert_decimal)) {
			if (document.getType() == CSVAbstract.TYPE_TEXT) {
				document = new CSVDataFile((CSVFile) document);
				Menu_DataOpened();
//				document_modified = true;
				infopanel.Load(document);
			}
			else
				JOptionPane.showMessageDialog(this, "Already in data format", "Can't Convert", JOptionPane.INFORMATION_MESSAGE);
		}
		else if (e.getSource().equals(convert_text)) {
			if (document.getType() == CSVAbstract.TYPE_NUMBER) {
				document = ((CSVDataFile) document).toCSVFile();
				infopanel.Load(document);
//				document_modified = true;
				Menu_TextOpened();
			}
			else
				JOptionPane.showMessageDialog(this, "Already in text format", "Can't Convert", JOptionPane.INFORMATION_MESSAGE);
		}
		else if (e.getSource().equals(convert_clean)) {
			if (document.getType() == CSVAbstract.TYPE_NUMBER) {
				int ln0 = document.getLineNumber();
				document = CSVOperations.CleanAllInvalid((CSVDataFile) document);
				infopanel.Load(document);
				int ln1 = document.getLineNumber();
				document_modified = true;

				JOptionPane.showMessageDialog(this, "Document cleaned.\nThere were "+ln0+" entries.\nNow there are "+ln1+" entries.\nTotal removed: "+(ln1-ln0)+".", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
			else
				JOptionPane.showMessageDialog(this, "Cannot clean in text format. Convert first.", "Can't Clean", JOptionPane.INFORMATION_MESSAGE);

		}
		else if (e.getSource().equals(file_export_fieldnames)) {
			final JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File("."));

        	if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        		document.WriteFieldsfile(fc.getSelectedFile().getAbsolutePath());			
		}
		else if (e.getSource().equals(file_export_week_en)  ||  e.getSource().equals(file_export_week_it)) {
			boolean it_format = e.getSource().equals(file_export_week_it);
			
			if (document.getType() == CSVAbstract.TYPE_NUMBER) {
				JFileChooser chooser = new JFileChooser();
			    chooser.setCurrentDirectory(new File("."));
			    chooser.setDialogTitle("Choose output directory");
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);
	
			    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			    	if (!ExportWeeks(chooser.getSelectedFile().getAbsolutePath(), it_format))
			    		JOptionPane.showMessageDialog(this, "Error exporting weeks.", "ERROR", JOptionPane.INFORMATION_MESSAGE);
			}
			else
				JOptionPane.showMessageDialog(this, "Cannot cut weeks in text format. Convert first.", "Can't Cut", JOptionPane.INFORMATION_MESSAGE);
		}
		else if (e.getSource().equals(file_export_correlations)) {
			if (document.getType() == CSVAbstract.TYPE_NUMBER) {
				final JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File("."));

	        	if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
	        		CSVOperations.out_correlations((CSVDataFile) document, fc.getSelectedFile().getAbsolutePath());
			}
			else
				JOptionPane.showMessageDialog(this, "Cannot calculate correlations in text format. Convert first.", "Can't Correlate", JOptionPane.INFORMATION_MESSAGE);
		}			
		else if (e.getSource().equals(calculate_meanweek)) {
			CSVDataFile[] files = CSVOperations.cutWeeks_DayOfWeek((CSVDataFile) document);
			document = CSVOperations.calculate_mean_week(files);
			document_modified = true;
			infopanel.Load(document);
		}	
		else if (e.getSource().equals(calculate_cleanmeanweek)) {
			CSVDataFile[] files = CSVOperations.cutWeeks_DayOfWeek((CSVDataFile) document);
			
			CSVDataFile[] mv = CSVOperations.calculate_meanvar_week(files);
			for (int i =0; i<2; i++)
				mv = CSVOperations.calculate_meanvar_cleaned_week(files, mv[0], mv[1], 1);
			
			document = mv[0];
			document_modified = true;
			infopanel.Load(document);
		}			
		else if (e.getSource().equals(calculate_variance)) {
			CSVDataFile[] files = CSVOperations.cutWeeks_DayOfWeek((CSVDataFile) document);
			CSVDataFile[] mv = CSVOperations.calculate_meanvar_week(files);
			
			document=mv[1];
			document_modified = true;
			infopanel.Load(document);
		}
		else if (e.getSource().equals(calculate_deltamean)) {
			CSVDataFile[] files = CSVOperations.cutWeeks_DayOfWeek((CSVDataFile) document);
			CSVDataFile[] mv = CSVOperations.calculate_meanvar_week(files);
			for (int i =0; i<2; i++)
				mv = CSVOperations.calculate_meanvar_cleaned_week(files, mv[0], mv[1], 1);
			files = CSVOperations.difference_weeks(files, mv[0]);
			
			double[] sums = CSVOperations.sum_difference_weeks(files, 27);
			int imax1=sums[0]<sums[1]?0:1;
			int imax2=sums[0]<sums[1]?1:0;
			for (int i=2; i<sums.length; i++)
				if (sums[i] <= sums[imax1]) {
					imax2=imax1;
					imax1=i;					
				}
				else if (sums[i] < sums[imax2])
					imax2=i;
			
			for (int i=0; i<files.length; i++)
				Output.getInstance().outline(""+i+") "+files[i].getDoubleSerieByName("day")[0]+" "+files[i].getDoubleSerieByName("month")[0]+" "+files[i].getDoubleSerieByName("year")[0]+" = "+sums[i]);
			Output.getInstance().outline("");
			Output.getInstance().outline("Best week["+imax1+"]: "+files[imax1].getDoubleSerieByName("day")[0]+" "+files[imax1].getDoubleSerieByName("month")[0]+" "+files[imax1].getDoubleSerieByName("year")[0]+" = "+sums[imax1]+"\n"
					+"2nd week["+imax2+"]: "+files[imax2].getDoubleSerieByName("day")[0]+" "+files[imax2].getDoubleSerieByName("month")[0]+" "+files[imax2].getDoubleSerieByName("year")[0]+" = "+sums[imax2]+"\n");
			
			
			document = CSVOperations.join_weeks(files);
			document_modified = true;
			infopanel.Load(document);
		}
		else if (e.getSource().equals(calculate_meandays)) {
			CSVDataFile[] files = CSVOperations.cutWeeks_DayOfWeek((CSVDataFile) document);
			CSVDataFile[] mv = CSVOperations.calculate_meanvar_days(files);
			
			document=mv[0];
			document_modified = true;
			infopanel.Load(document);			
		}
		else if (e.getSource().equals(calculate_vardays)) {
			CSVDataFile[] files = CSVOperations.cutWeeks_DayOfWeek((CSVDataFile) document);
			CSVDataFile[] mv = CSVOperations.calculate_meanvar_days(files);
			
			document=mv[1];
			document_modified = true;
			infopanel.Load(document);				
		}
		else if (e.getSource().equals(inspect_date))
			CSVOperations.inspect_date((CSVDataFile) document);
		else if (e.getSource().equals(inspect_invalid)) {
			boolean ret = CSVOperations.clean_corrupted((CSVDataFile) document);
			
			if (ret) {
				document_modified = true;
				infopanel.Load(document);
			}
		}
		
		
		else if (e.getSource().equals(inspect_select))
			CSVOperations.inspect_mean_datas((CSVDataFile) document);

		else if (e.getSource().equals(thesis_weekly_graphs)) {
			boolean it_format = e.getSource().equals(file_export_week_it);
			
			JFileChooser chooser = new JFileChooser();
		    chooser.setCurrentDirectory(new File("."));
		    chooser.setDialogTitle("Choose output directory");
		    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    chooser.setAcceptAllFileFilterUsed(false);
	
		    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
		    	Thesis_WeeklyGraphs(chooser.getSelectedFile().getAbsolutePath(), it_format);
		}
		else if (e.getSource().equals(thesis_dayofweek_graphs)) {
			boolean it_format = e.getSource().equals(file_export_week_it);
			
			JFileChooser chooser = new JFileChooser();
		    chooser.setCurrentDirectory(new File("."));
		    chooser.setDialogTitle("Choose output directory");
		    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    chooser.setAcceptAllFileFilterUsed(false);
	
		    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
		    	Thesis_DayOfWeekGraphs(chooser.getSelectedFile().getAbsolutePath(), it_format);		
		}

		
    	else if (e.getSource().equals(help_about))
    		JOptionPane.showMessageDialog(this, "yCSV 1.0.0\nReleased under GPL v3.0\n(c) 2011 by Miro Salvagni - ARPA FVG");
	}




	protected CSVAbstract document;
	protected boolean document_modified;
	protected String document_filename;

	public CSVAbstract getDocument()	{ return document; }
	public String getDocumentFilename()	{ return document_filename; }



	public boolean Open(String filename)
	{
		if (filename != null) {
			document_modified = false;
			document = new CSVFile();

			boolean ret = document.Read(filename);
			if (!ret) {
				JOptionPane.showMessageDialog(this, "Error opening "+filename);
				document = null;
				document_filename = "";
				Menu_NoneOpened();
				infopanel.Unload();
			}
			else {
				document_filename = filename;
				infopanel.Load(document);
				Menu_TextOpened();
			}

			return ret;
		}
		else {
			document_modified = false;			
			Menu_NoneOpened();
			infopanel.Unload();
			return false;
		}
	}

	public boolean Save()
	{
		return SaveAs(document_filename);
	}

	public boolean SaveAs(String filename)
	{
		File current_file = new File(filename);
		if (current_file.exists())
			if (JOptionPane.showConfirmDialog(null, "File already exists, do you want to overwrite it?", "Warning", JOptionPane.YES_NO_OPTION) != 0)
				return false;

		boolean ret = document.Write(filename, delimiter);

		if (ret) {
			document_filename = filename;
			document_modified = false;
		}
		else
			JOptionPane.showMessageDialog(this, "Error saving "+filename);

		return ret;
	}


	
	public boolean ExportWeeks(String filename, boolean it_format)
	{
		CSVDataFile[] files = CSVOperations.cutWeeks_DayOfWeek((CSVDataFile) document);
		
		
		String dirname = filename + (filename.endsWith("\\") || filename.endsWith("/") ? "" : "\\");
		
		if (files == null)
			return false;
		
		boolean ret = true;
		
		for (int i=0; i<files.length; i++) {
			int index = i+1;
			
			String file_i = dirname + "week_"+(index<100?(index<10?"00":"0"):"")+index+".csv";
	
			ret &= files[i].Write(file_i, delimiter, it_format);
		}
		
		if (files.length > 0) {
			ret &= files[0].WriteFieldsfile(dirname + "fieldnames.txt");
		
			CSVDataFile[] stat_weeks = CSVOperations.calculate_meanvar_week(files);
			ret &= stat_weeks[0].Write(dirname + "week_mean.csv", delimiter, it_format);
			ret &= stat_weeks[1].Write(dirname + "week_var.csv", delimiter, it_format);
			ret &= CSVOperations.out_correlations(stat_weeks[0], dirname + "corr_week_mean.txt");
	
			CSVDataFile[] stat_days = CSVOperations.calculate_meanvar_days(files);
			ret &= stat_days[0].Write(dirname + "days_mean.csv", delimiter, it_format);
			ret &= stat_days[1].Write(dirname + "days_var.csv", delimiter, it_format);
			ret &= CSVOperations.out_correlations(stat_days[0], dirname + "corr_days_mean.txt");
			
	
			
			// clean e varianza sulla settimana pulita
			// calcolata solo su i dati entro alpha*varianza dalla media (iterando cleaning_times volte)
			final int cleaning_times = 1;
			double[] alphas = { 0.01, 0.05, 0.10, 0.15, 0.25, 0.50, 1.0, 2.0 };
			
			for (int i=0; i<alphas.length; i++) {
				CSVDataFile[] clean_stat_weeks = stat_weeks;
				for (int k=0; k<cleaning_times; k++)
					clean_stat_weeks = CSVOperations.calculate_meanvar_cleaned_week(files, clean_stat_weeks[0], clean_stat_weeks[1], alphas[i]);
	
				ret &= clean_stat_weeks[0].Write(dirname + "week_clean_"+alphas[i]+"_mean.csv", delimiter, it_format);
				ret &= clean_stat_weeks[1].Write(dirname + "week_clean_"+alphas[i]+"_var.csv", delimiter, it_format);
				ret &= CSVOperations.out_correlations(clean_stat_weeks[0], dirname + "corr_week_clean_"+alphas[i]+"_mean.txt");
				
				
				CSVDataFile[] clean_stat_days = stat_days;
				for (int k=0; k<cleaning_times; k++)
					clean_stat_days = CSVOperations.calculate_meanvar_cleaned_days(files, clean_stat_days[0], clean_stat_days[1], alphas[i]);
				
				ret &= clean_stat_days[0].Write(dirname + "days_clean_"+alphas[i]+"_mean.csv", delimiter, it_format);
				ret &= clean_stat_days[1].Write(dirname + "days_clean_"+alphas[i]+"_var.csv", delimiter, it_format);
				ret &= CSVOperations.out_correlations(clean_stat_days[0], dirname + "corr_days_clean_"+alphas[i]+"_mean.txt");
			}
		}
		else
			JOptionPane.showMessageDialog(this, "ERROR: no weeks found.");
		
		return ret;
	}

	
	public boolean Thesis_WeeklyGraphs(String path, boolean it_format)
	{
		String dirname = path + (path.endsWith("\\") || path.endsWith("/") ? "" : "\\");
		Plotter plotter = new Plotter (dirname + "plot.p");
		
		boolean ret = true;

		// profilo del giorno infrasettimanale medio
		ConditionAbstract[] conditions_giorno_infrasettmanale = ConditionFactory.CreateArray_Infrasettimanale_DayMins();
		CSVDataFile profile_giorno_medio_infrasettimanale = CSVOperations.aggregate_mean_and_join(ConditionAbstract.Select((CSVDataFile)document, conditions_giorno_infrasettmanale));
		ret &= profile_giorno_medio_infrasettimanale.Save3(dirname + "profile_Infrasettimanale");
		plotter.beginPlot("graph-profilo_infrasettimanale-Leq-south");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Leq(A)_s" , "profile_Infrasettimanale");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Lmax(A)_s" , "profile_Infrasettimanale");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Lmin(A)_s" , "profile_Infrasettimanale");
		plotter.endPlot();
		plotter.beginPlot("graph-profilo_infrasettimanale-Leq-north");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Leq(A)_n" , "profile_Infrasettimanale");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Lmax(A)_n" , "profile_Infrasettimanale");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Lmin(A)_n" , "profile_Infrasettimanale");
		plotter.endPlot();
		plotter.beginPlot("graph-profilo_infrasettimanale-Leqs");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Leq(A)_s" , "profile_Infrasettimanale");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Leq(A)_n" , "profile_Infrasettimanale");
		plotter.endPlot();
		plotter.beginPlot("graph-profilo_infrasettimanale-VolTot");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Vol_VTot_s" , "profile_Infrasettimanale");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Vol_VTot_n" , "profile_Infrasettimanale");
		plotter.endPlot();
		plotter.beginPlot("graph-profilo_infrasettimanale-VolVP");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Vol_VP_s" , "profile_Infrasettimanale");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Vol_VP_n" , "profile_Infrasettimanale");
		plotter.endPlot();
		plotter.beginPlot("graph-profilo_infrasettimanale-VolVL");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Vol_VL_s" , "profile_Infrasettimanale");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Vol_VL_n" , "profile_Infrasettimanale");
		plotter.endPlot();
		plotter.beginPlot("graph-profilo_infrasettimanale-SpdTot");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Spd_VTot_s" , "profile_Infrasettimanale");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Spd_VTot_n" , "profile_Infrasettimanale");
		plotter.endPlot();
		plotter.beginPlot("graph-profilo_infrasettimanale-SpdVP");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Spd_VP_s" , "profile_Infrasettimanale");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Spd_VP_n" , "profile_Infrasettimanale");
		plotter.endPlot();
		plotter.beginPlot("graph-profilo_infrasettimanale-SpdVL");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Spd_VL_s" , "profile_Infrasettimanale");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Spd_VL_n" , "profile_Infrasettimanale");
		plotter.endPlot();
		
		
		// medie mensili
		ConditionAbstract[] conditions_months = ConditionFactory.CreateArray_YearMonths();
		CSVDataFile year_months_mean_profile = CSVOperations.aggregate_mean_and_join(ConditionAbstract.Select((CSVDataFile)document, conditions_months));
		ret &= year_months_mean_profile.Save3(dirname + "profile_YearMonths");

		
		// i 365 giorni dell'anno: medie e somme
		ConditionAbstract[] conditions_days = ConditionFactory.CreateArray_YearDays();
		CSVDataFile year_days_mean_profile = CSVOperations.aggregate_mean_and_join(ConditionAbstract.Select((CSVDataFile)document, conditions_days));
		ret &= year_days_mean_profile.Save3(dirname + "profile_YearDays");

		year_days_mean_profile = CSVOperations.aggregate_sum_and_join(ConditionAbstract.Select((CSVDataFile)document, conditions_days));
		ret &= year_days_mean_profile.Save3(dirname + "profilesum_YearDays");


		// medie stagionali
		ConditionAbstract[] condition_seasons = ConditionFactory.CreateArray_YearSeasons();
		CSVDataFile profile_seasons_mean = CSVOperations.aggregate_mean_and_join(ConditionAbstract.Select((CSVDataFile)document, condition_seasons));
		ret &= profile_seasons_mean.Save3(dirname + "profile_YearSeasons");

		
		// settimana media
		ConditionAbstract[] condition_meanweek = ConditionFactory.CreateArray_YearDayofweekmins();
		CSVDataFile condition_meanweek_mean = CSVOperations.aggregate_mean_and_join(ConditionAbstract.Select((CSVDataFile)document, condition_meanweek));
		ret &= condition_meanweek_mean.Save3(dirname + "profile_MeanWeek");
	
		
		// profilo medio giornaliero per i 7 giorni della settimana
		ConditionAbstract[][] conditions_daysofweek = ConditionFactory.CreateArray_YearDayofweekMins();
		CSVDataFile[][] array_docs = ConditionAbstract.Select((CSVDataFile)document, conditions_daysofweek);
		CSVDataFile[] days_of_week = new CSVDataFile[7];
		plotter.beginPlot("graph-DaysOfWeek.jpg");
		
		for (int d=0; d<7; d++) {
			days_of_week[d] = CSVOperations.aggregate_mean_and_join(array_docs[d]);
			ret &= days_of_week[d].Save3(dirname + "profile_YearDayofweek"+(d+1));
			plotter.continuePlot(days_of_week[d], "Leq(A)_s", "profile_YearDayofweek"+(d+1));	
		}
		plotter.endPlot();
		
		
		
		// per le 4 stagioni, profilo medio giornaliero per i 7 giorni della settimana
		ConditionAbstract[][][] conditions_seasonsdaysofweek = ConditionFactory.CreateArrays_YearSeasonsDaysofweekMins();
		CSVDataFile[][][] year_seasons_dayofweekmins = ConditionAbstract.Select((CSVDataFile)document, conditions_seasonsdaysofweek);
		CSVDataFile[] year_seasons = new CSVDataFile[7];

		
		for (int season=0; season<4; season++)
			for (int d=0; d<7; d++) {
				year_seasons[d] = CSVOperations.aggregate_mean_and_join(year_seasons_dayofweekmins[season][d]);
				ret &= year_seasons[d].Save3(dirname + "profile_YearSeason_"+(season+1)+"_Dayofweek"+(d+1));
			}		
		
		plotter.close();
		return ret;
	}

	
	
	public boolean Thesis_DayOfWeekGraphs(String path, boolean it_format)
	{
		String dirname = path + (path.endsWith("\\") || path.endsWith("/") ? "" : "\\");
		Plotter plotter = new Plotter (dirname + "plot.p");
		
		boolean ret = true;

		// profilo del giorno infrasettimanale medio CreateArray_Infrasettimanale_DayHours!!! CreateArray_Infrasettimanale_DayMins!!!
		ConditionAbstract[] conditions_giorno_infrasettmanale = ConditionFactory.Compose(ConditionFactory.Create_Meteo(), ConditionFactory.CreateArray_Infrasettimanale_DayHours());
		CSVDataFile[] mean_var_profilo  = CSVOperations.aggregate_meanvar_and_join(ConditionAbstract.Select((CSVDataFile)document, conditions_giorno_infrasettmanale));
		
		CSVDataFile profile_giorno_medio_infrasettimanale = mean_var_profilo[0];
		ret &= profile_giorno_medio_infrasettimanale.Save3(dirname + "profile_Infrasettimanale");
		ret &=  mean_var_profilo[1].Save3(dirname + "profile_Infrasettimanale_rms");
		
		/*
		plotter.beginPlot("graph-profilo_infrasettimanale-Leq-south");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Leq(A)_s" , "profile_Infrasettimanale");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Lmax(A)_s" , "profile_Infrasettimanale");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Lmin(A)_s" , "profile_Infrasettimanale");
		plotter.endPlot();
		plotter.beginPlot("graph-profilo_infrasettimanale-Leq-north");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Leq(A)_n" , "profile_Infrasettimanale");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Lmax(A)_n" , "profile_Infrasettimanale");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Lmin(A)_n" , "profile_Infrasettimanale");
		plotter.endPlot();
		plotter.beginPlot("graph-profilo_infrasettimanale-Leqs");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Leq(A)_s" , "profile_Infrasettimanale");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Leq(A)_n" , "profile_Infrasettimanale");
		plotter.endPlot();
		plotter.beginPlot("graph-profilo_infrasettimanale-VolTot");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Vol_VTot_s" , "profile_Infrasettimanale");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Vol_VTot_n" , "profile_Infrasettimanale");
		plotter.endPlot();
		plotter.beginPlot("graph-profilo_infrasettimanale-VolVP");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Vol_VP_s" , "profile_Infrasettimanale");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Vol_VP_n" , "profile_Infrasettimanale");
		plotter.endPlot();
		plotter.beginPlot("graph-profilo_infrasettimanale-VolVL");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Vol_VL_s" , "profile_Infrasettimanale");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Vol_VL_n" , "profile_Infrasettimanale");
		plotter.endPlot();
		plotter.beginPlot("graph-profilo_infrasettimanale-SpdTot");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Spd_VTot_s" , "profile_Infrasettimanale");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Spd_VTot_n" , "profile_Infrasettimanale");
		plotter.endPlot();
		plotter.beginPlot("graph-profilo_infrasettimanale-SpdVP");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Spd_VP_s" , "profile_Infrasettimanale");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Spd_VP_n" , "profile_Infrasettimanale");
		plotter.endPlot();
		plotter.beginPlot("graph-profilo_infrasettimanale-SpdVL");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Spd_VL_s" , "profile_Infrasettimanale");
		plotter.continuePlot(profile_giorno_medio_infrasettimanale, "Spd_VL_n" , "profile_Infrasettimanale");
		plotter.endPlot();

		
		
		// medie mensili
		ConditionAbstract[] conditions_months = ConditionFactory.Compose(ConditionFactory.Create_Meteo(), ConditionFactory.CreateArray_YearMonths());
		CSVDataFile year_months_mean_profile = CSVOperations.aggregate_mean_and_join(ConditionAbstract.Select((CSVDataFile)document, conditions_months));
		ret &= year_months_mean_profile.Save3(dirname + "profile_YearMonths");

		
		// i 365 giorni dell'anno: medie e somme
		ConditionAbstract[] conditions_days = ConditionFactory.Compose(ConditionFactory.Create_Meteo(), ConditionFactory.CreateArray_YearDays());
		CSVDataFile year_days_mean_profile = CSVOperations.aggregate_mean_and_join(ConditionAbstract.Select((CSVDataFile)document, conditions_days));
		ret &= year_days_mean_profile.Save3(dirname + "profile_YearDays");

		year_days_mean_profile = CSVOperations.aggregate_sum_and_join(ConditionAbstract.Select((CSVDataFile)document, conditions_days));
		ret &= year_days_mean_profile.Save3(dirname + "profilesum_YearDays");


		// medie stagionali
		ConditionAbstract[] condition_seasons = ConditionFactory.Compose(ConditionFactory.Create_Meteo(), ConditionFactory.CreateArray_YearSeasons());
		CSVDataFile profile_seasons_mean = CSVOperations.aggregate_mean_and_join(ConditionAbstract.Select((CSVDataFile)document, condition_seasons));
		ret &= profile_seasons_mean.Save3(dirname + "profile_YearSeasons");

		
		// settimana media
		ConditionAbstract[] condition_meanweek = ConditionFactory.Compose(ConditionFactory.Create_Meteo(), ConditionFactory.CreateArray_YearDayofweekmins());
		CSVDataFile condition_meanweek_mean = CSVOperations.aggregate_mean_and_join(ConditionAbstract.Select((CSVDataFile)document, condition_meanweek));
		ret &= condition_meanweek_mean.Save3(dirname + "profile_MeanWeek");
	*/
		
		// profilo medio giornaliero per i 7 giorni della settimana
		//ConditionAbstract[][] conditions_daysofweek = ConditionFactory.Compose(ConditionFactory.Create_Meteo(), ConditionFactory.CreateArray_YearDayofweekMins());
		ConditionAbstract[][] conditions_daysofweek = ConditionFactory.Compose(ConditionFactory.Create_Meteo(), ConditionFactory.CreateArray_YearDayofweekHours());
		CSVDataFile[][] array_docs = ConditionAbstract.Select((CSVDataFile)document, conditions_daysofweek);
//		CSVDataFile[] days_of_week = new CSVDataFile[7];
//		plotter.beginPlot("graph-DaysOfWeek.jpg");

		CSVDataFile[] domenica = CSVOperations.aggregate_meanvar_and_join(array_docs[6]);
		ret &= domenica[0].Save3(dirname + "profilo-domenica");
		ret &= domenica[1].Save3(dirname + "profilo-domenica-var");
		            
		//ret &= days_of_week[d].Save3(dirname + "profile_YearDayofweek"+(d+1));

		/*
		for (int d=0; d<7; d++) {
			days_of_week[d] = CSVOperations.aggregate_mean_and_join(array_docs[d]);
			ret &= days_of_week[d].Save3(dirname + "profile_YearDayofweek"+(d+1));
			plotter.continuePlot(days_of_week[d], "Leq(A)_s", "profile_YearDayofweek"+(d+1));	
		}
		plotter.endPlot();
		
		

		// per le 4 stagioni, profilo medio giornaliero per i 7 giorni della settimana
		ConditionAbstract[][][] conditions_seasonsdaysofweek = ConditionFactory.Compose(ConditionFactory.Create_Meteo(), ConditionFactory.CreateArrays_YearSeasonsDaysofweekMins());
		CSVDataFile[][][] year_seasons_dayofweekmins = ConditionAbstract.Select((CSVDataFile)document, conditions_seasonsdaysofweek);
		CSVDataFile[] year_seasons = new CSVDataFile[7];

		
		for (int season=0; season<4; season++)
			for (int d=0; d<7; d++) {
				year_seasons[d] = CSVOperations.aggregate_mean_and_join(year_seasons_dayofweekmins[season][d]);
				ret &= year_seasons[d].Save3(dirname + "profile_YearSeason_"+(season+1)+"_Dayofweek"+(d+1));
			}		
		*/
		plotter.close();
		return ret;
	}	

	

	class CSVInfoPanel extends JPanel implements ActionListener /*DragSourceListener, DragGestureListener*/
	{
		private JList field_list;
		private JList number_list;
		private JLabel field_number;
		private JLabel line_number;
		private JLabel type;
		private DefaultListModel model_field;
		private DefaultListModel model_number;

		static final long serialVersionUID = 6123761;

		public CSVInfoPanel()
		{
			super();

			setLayout(new BorderLayout());

			JPanel north = new JPanel();
			north.setLayout(new GridLayout(3,2));

			north.add(new JLabel("Field Number:"));
			field_number = new JLabel("");
			north.add(field_number);
			north.add(new JLabel("Line Number:"));
			line_number = new JLabel("");
			north.add(line_number);
			north.add(new JLabel("Type:"));
			type = new JLabel("");
			north.add(type);


			model_field = new DefaultListModel();
			model_number = new DefaultListModel();
			field_list = new JList(model_field);
			number_list = new JList(model_number);
			field_list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//			field_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			number_list.setEnabled(false);
			addListMouseListener();

			JPanel center_panel = new JPanel();
			center_panel.setLayout(new BorderLayout());
			center_panel.add(number_list, BorderLayout.WEST);
			center_panel.add(field_list, BorderLayout.CENTER);
			
			JScrollPane textareaScroller = new JScrollPane(center_panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

			add(textareaScroller, BorderLayout.CENTER);
			add(north, BorderLayout.NORTH);

		}

		public void Load(CSVAbstract csv)
		{
			field_number.setText(""+csv.getFieldNumber());
			line_number.setText(""+csv.getLineNumber());
			type.setText((csv.getType() == CSVAbstract.TYPE_TEXT ? "text" : "data")+" "+csv.getInfoString());

			model_field.clear();
			model_number.clear();
			LinkedList<String> fields = csv.getFieldnames();
			for (int i=0; i<fields.size(); i++) {
				model_field.add(i, fields.get(i));
				model_number.add(i, ""+(i+1));
				
			}
		}

		public void Unload()
		{
			field_number.setText("");
			line_number.setText("");
			type.setText("none");
			model_field.clear();
			model_number.clear();
		}




		private void addListMouseListener()
		{
			field_list.addMouseListener(new MouseAdapter() {
				public void mousePressed(final MouseEvent e)	{ this.maybeShowPopup(e); }
				public void mouseReleased(final MouseEvent e)	{ this.maybeShowPopup(e); }

				public void mouseClicked(final MouseEvent e)
				{
		//			if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2)
		//				editAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_FIRST, "double click"));
				}

				private void maybeShowPopup(final MouseEvent e)
				{
					if (e.isPopupTrigger())
						popup_menu.getPopupMenu().show(field_list, e.getX(), e.getY());
				}
			});
		}

		public void actionPerformed(ActionEvent e)
    	{
			int[] indexes = field_list.getSelectedIndices();
			
//			String sindexes = "";
//			for (int i=0; i<indexes.length; i++) sindexes += (i!=0?",":"")+model_field.get(indexes[i]); 
//			JOptionPane.showMessageDialog(this, "Selected: "+field_list.getSelectedValue()+"\nIndex = "+indexes.length+"\n"+sindexes);
			
			if (e.getSource().equals(popup_first)) {
				document_modified = true;
			}
			else if (e.getSource().equals(popup_prev)) {
				document_modified = true;
			}
			else if (e.getSource().equals(popup_next)) {
				document_modified = true;
			}
			else if (e.getSource().equals(popup_last)) {
				document_modified = true;
			}
			else if (e.getSource().equals(popup_rename)) {
				if (indexes.length == 1) {
					String new_name =  JOptionPane.showInputDialog(this, "Enter new name for serie "+field_list.getSelectedValue()+"...");

					if (new_name != null) {
						if (!document.Rename((String) field_list.getSelectedValue(), new_name))
							JOptionPane.showMessageDialog(this, "Cannot rename: "+field_list.getSelectedValue()+"\nSerie not present!", "ERROR", JOptionPane.INFORMATION_MESSAGE);

						document_modified = true;
						Load(document);
					}
				}
			}
			else if (e.getSource().equals(popup_remove)) {

				for (int i=indexes.length-1; i>=0; i--) {
					String names = (String) model_field.get(indexes[i]);
				
					if (!document.Remove(names))
						JOptionPane.showMessageDialog(this, "Cannot remove: "+names+"\nSerie not present!", "ERROR", JOptionPane.INFORMATION_MESSAGE);
				}

				document_modified = true;
				Load(document);
			}
			else if (e.getSource().equals(popup_date_std)) {
				if (indexes.length == 1) {
					((CSVFile) document).ConvertDates_Text2Fields(indexes[0]);
					document_modified = true;
					Load(document);
				}
			}			
			else if (e.getSource().equals(popup_date_utc)) {
				if (indexes.length == 1) {
					((CSVFile) document).ConvertDates_Text2UTC(indexes[0]);
					document_modified = true;
					Load(document);
				}
			}
			else if (e.getSource().equals(popup_date_finance)) {
				if (indexes.length == 1) {
					((CSVFile) document).ConvertDates_Finance2Fields(indexes[0]);
					document_modified = true;
					Load(document);
				}
			}
			else if (e.getSource().equals(popup_calc_d)) {
				for (int i=indexes.length-1; i>=0; i--) {
					String name = (String) model_field.get(indexes[i]);
					double[] serie = document.getDoubleSerieByName(name);
				
					((CSVDataFile)document).AddDoubleSerie("d("+name+")", Functions.calculate_d(serie));
				}
				
				document_modified = true;
				Load(document);
			}		
			else if (e.getSource().equals(popup_calc_d2)) {
				for (int i=indexes.length-1; i>=0; i--) {
					String name = (String) model_field.get(indexes[i]);
					double[] serie = document.getDoubleSerieByName(name);
				
					((CSVDataFile)document).AddDoubleSerie("d2("+name+")", Functions.calculate_d2(serie));
				}
				
				document_modified = true;
				Load(document);
			}		
			else if (e.getSource().equals(popup_calc_dperc)) {
				for (int i=indexes.length-1; i>=0; i--) {
					String name = (String) model_field.get(indexes[i]);
					double[] serie = document.getDoubleSerieByName(name);
				
					((CSVDataFile)document).AddDoubleSerie("d%("+name+")", Functions.calculate_dperc(serie));
				}
				
				document_modified = true;
				Load(document);
			}		
			else if (e.getSource().equals(popup_calc_mm)) {
				int len = 20;

				String new_len =  JOptionPane.showInputDialog(this, "Desired EMM length?");
				if (new_len==null || new_len.equals(""))
					return;
				try { len = Integer.parseInt(new_len); }
				catch (NumberFormatException exc) { return; }
				
				for (int i=indexes.length-1; i>=0; i--) {
					String name = (String) model_field.get(indexes[i]);
					double[] serie = document.getDoubleSerieByName(name);
				
					((CSVDataFile)document).AddDoubleSerie("mm"+len+"("+name+")", Functions.calculate_mm(serie, len));
				}				
				
				
				document_modified = true;
				Load(document);
			}		
			else if (e.getSource().equals(popup_calc_emm)) {
				int len = 20;
				double alpha = 1.2;

				String new_len =  JOptionPane.showInputDialog(this, "Desired EMM length?");
				if (new_len==null || new_len.equals(""))
					return;
				try { len = Integer.parseInt(new_len); }
				catch (NumberFormatException exc) { return; }
				
				String new_alpha =  JOptionPane.showInputDialog(this, "Desired alpha parameter?");
				if (new_alpha==null || new_alpha.equals(""))
					return;
				try { alpha = Double.parseDouble(new_alpha); }
				catch (NumberFormatException exc) { return; }
				
				
				
				for (int i=indexes.length-1; i>=0; i--) {
					String name = (String) model_field.get(indexes[i]);
					double[] serie = document.getDoubleSerieByName(name);
				
					((CSVDataFile)document).AddDoubleSerie("emm_"+len+"_"+alpha+"("+name+")", Functions.calculate_emm(serie, len, alpha));
				}				
				document_modified = true;
				Load(document);
			}
			else if (e.getSource().equals(popup_calc_leqm)) {
				if (indexes.length < 2) {
					JOptionPane.showMessageDialog(this, "Only one serie selected.", "ERROR", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
					
				double[][] series = new double[indexes.length][];
				         
				for (int i=indexes.length-1; i>=0; i--) {
					String sname = (String) model_field.get(indexes[i]);
					
					series[i] = document.getDoubleSerieByName(sname);
					
					if (series[i] == null)
						JOptionPane.showMessageDialog(this, "Cannot Find Serie \""+sname+"\".", "ERROR", JOptionPane.INFORMATION_MESSAGE);
				}
				((CSVDataFile)document).AddDoubleSerie("Leqm", Functions.calculate_leqm(series));

				document_modified = true;
				Load(document);
			}
			
			else if (e.getSource().equals(popup_calc_pplus)) {
				int len = 20;
				double alpha = 1.2;

				String new_len =  JOptionPane.showInputDialog(this, "Desired Pplus length?");
				if (new_len==null || new_len.equals(""))
					return;
				try { len = Integer.parseInt(new_len); }
				catch (NumberFormatException exc) { return; }
				
				String new_alpha =  JOptionPane.showInputDialog(this, "Desired alpha parameter (default=0)?");
				if (new_alpha==null || new_alpha.equals(""))
					return;
				try { alpha = Double.parseDouble(new_alpha); }
				catch (NumberFormatException exc) { return; }
				
				for (int i=indexes.length-1; i>=0; i--) {
					String name = (String) model_field.get(indexes[i]);
					double[] serie = document.getDoubleSerieByName(name);
				
					((CSVDataFile)document).AddDoubleSerie("Pplus_"+len+"("+name+")", Functions.calculate_Pplus(serie, len, alpha));
				}
				
				document_modified = true;
				Load(document);
			}	
			else if (e.getSource().equals(popup_calc_pmin)) {
				int len = 20;
				double alpha = 1.2;

				String new_len =  JOptionPane.showInputDialog(this, "Desired Pminus length?");
				if (new_len==null || new_len.equals(""))
					return;
				try { len = Integer.parseInt(new_len); }
				catch (NumberFormatException exc) { return; }
				
				String new_alpha =  JOptionPane.showInputDialog(this, "Desired alpha parameter (default=0)?");
				if (new_alpha==null || new_alpha.equals(""))
					return;
				try { alpha = Double.parseDouble(new_alpha); }
				catch (NumberFormatException exc) { return; }
				
				for (int i=indexes.length-1; i>=0; i--) {
					String name = (String) model_field.get(indexes[i]);
					double[] serie = document.getDoubleSerieByName(name);
				
					((CSVDataFile)document).AddDoubleSerie("Pminus_"+len+"("+name+")", Functions.calculate_Pminus(serie, len, alpha));
				}
				
				document_modified = true;
				Load(document);
			}				
			else if (e.getSource().equals(popup_calc_p0)) {
				int len = 20;
				double alpha = 1.2;

				String new_len =  JOptionPane.showInputDialog(this, "Desired P0 length?");
				if (new_len==null || new_len.equals(""))
					return;
				try { len = Integer.parseInt(new_len); }
				catch (NumberFormatException exc) { return; }
				
				String new_alpha =  JOptionPane.showInputDialog(this, "Desired alpha parameter (default=0)?");
				if (new_alpha==null || new_alpha.equals(""))
					return;
				try { alpha = Double.parseDouble(new_alpha); }
				catch (NumberFormatException exc) { return; }
				
				for (int i=indexes.length-1; i>=0; i--) {
					String name = (String) model_field.get(indexes[i]);
					double[] serie = document.getDoubleSerieByName(name);
				
					((CSVDataFile)document).AddDoubleSerie("Pminus_"+len+"("+name+")", Functions.calculate_Pminus(serie, len, alpha));
				}
				
				document_modified = true;
				Load(document);
			}
			else if (e.getSource().equals(popup_calc_stub)) {
				for (int i=indexes.length-1; i>=0; i--) {
					String name = (String) model_field.get(indexes[i]);
					double[] serie = document.getDoubleSerieByName(name);
				
					((CSVDataFile)document).AddDoubleSerie("stub("+name+")", Functions.calculate_trade(serie, 10000));
				}
				
				document_modified = true;
				Load(document);
			}	
				

			
			
			
    	}
	}

	
}