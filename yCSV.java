/**
 * @(#)yCSV.java
 *
 *
 * @author Miro Salvagni
 * @version 1.00 2010/10/28
 */


//import java.io.*;
//import java.util.*;



public class yCSV
{
	final static String DELIMITER = ";";


	public static void main(String Args[])
	{
		int command = -1;		//	-1:help, usage.   0:info.   1:convert.   2:convert_xml.   4:win.
		String input = "";
		String output = "";
		String out_delimiter = ";";
		int out_decimal = 0;	//	0 = '.'		1 = ','

		int verbose = 0;		// 0=no		1=yes	2=very		-1=quiet
		int argi = 0;

		if (Args.length == 0)
			command = 4;

		while (argi < Args.length) {
			if (Args[argi].equals("-info")  ||  Args[argi].equals("--info")) {
				if (command != -1) {
					usage(Args[argi]+": already specified a command.");
					return;
				}

				command = 0;
			}
			else if (Args[argi].equals("-convert")  ||  Args[argi].equals("--convert")) {
				if (command != -1) {
					usage(Args[argi]+": already specified a command.");
					return;
				}

				command = 1;
			}
			else if (Args[argi].equals("-xml")  ||  Args[argi].equals("--xml")) {
				if (command != -1) {
					usage("-xml: already specified a command.");
					return;
				}

				command = 2;
			}
			else if (Args[argi].equals("-win")  ||  Args[argi].equals("--window")) {
				if (command != -1) {
					usage(Args[argi]+": already specified a command.");
					return;
				}

				command = 4;
			}
			else if (Args[argi].equals("--help")  ||  Args[argi].equals("--usage")) {
				usage(null);
				return;
			}
			else if (Args[argi].startsWith("-d")) {
				out_delimiter = Args[argi].substring(2);
				if (out_delimiter.equalsIgnoreCase("space"))
					out_delimiter = " ";

				if (out_delimiter.equals("")) {
					usage(Args[argi]+": empty delimiter.");
					return;
				}
			}
			else if (Args[argi].equals("-it")  ||  Args[argi].equals("--it"))
				out_decimal = 1;
			else if (Args[argi].equals("-en")  ||  Args[argi].equals("--en"))
				out_decimal = 0;
			else if (Args[argi].equals("-i")  ||  Args[argi].equals("-in")  ||  Args[argi].equals("--in")  ||  Args[argi].equals("--input")) {
				if (argi == Args.length-1) {
					usage(Args[argi]+": no input file specified.");
					return;
				}
				else if (!input.equals("")) {
					usage(Args[argi]+": you already specified an input file.");
					return;
				}

				input = Args[++argi];
			}
			else if (Args[argi].equals("-o")  ||  Args[argi].equals("-out")  ||  Args[argi].equals("--out")  ||  Args[argi].equals("--output")) {
				if (argi == Args.length-1) {
					usage(Args[argi]+": no output file specified.");
					return;
				}
				else if (!output.equals("")) {
					usage(Args[argi]+": you already specified an output file.");
					return;
				}

				output = Args[++argi];
			}
			else if (Args[argi].equals("-v")  ||  Args[argi].equals("-verbose")  ||  Args[argi].equals("--verbose")) {
				if (verbose == -1) {
					usage(Args[argi]+": -quiet specified");
					return;
				}

				verbose++;
			}
			else if (Args[argi].equals("-q")  ||  Args[argi].equals("-quiet")  ||  Args[argi].equals("--quiet")) {
				if (verbose > 0) {
					usage(Args[argi]+": -verbose specified");
					return;
				}

				verbose = -1;
			}
			else {
				usage("Unknown command ("+Args[argi]+")");
				return;
			}

			argi++;
		}

		if (command == -1 && !input.equals("") && !output.equals("")) {
			if (output.endsWith("xml"))
				command = 2;
			else
				command = 1;
		}


		if (command == -1) {
			usage("no command speficied.");
			return;
		}
		else if (command == 0) {
			if (input.equals("")) {
				usage("--info: no input file speficied.");
				return;
			}

			CSVFile csv = new CSVFile();
			if (!csv.Read(input)) {
				System.out.println("Error reading input file ("+input+")");
				return;
			}
			csv.Info();
		}
		else if (command == 1) {
			if (output.equals("")) {
				usage("--convert: no output file speficied.");
				return;
			}

			CSVFile csv = new CSVFile();
			if (!csv.Read(input)) {
				System.out.println("Error reading input file ("+input+")");
				return;
			}

			csv.Convert();			// not standard line, converts date to UTC and remove second column, replacing it with daynumber

			if (out_decimal == 1)
				csv.Decimal2IT();

			csv.Write(output, out_delimiter);

		}
		else if (command == 2) {
			if (output.equals("")) {
				usage("--convert: no output file speficied.");
				return;
			}

			CSVFile csv = new CSVFile();
			if (!csv.Read(input)) {
				System.out.println("Error reading input file ("+input+")");
				return;
			}

			csv.Convert();			// not standard line, converts date to UTC and remove second column, replacing it with daynumber

			CSVDataFile data = new CSVDataFile(csv);
			data.WriteXML(output);
		}
		else if (command == 4)
			new yCSV_MainWin();
	}



	public static void usage(String error)
	{
		if (error != null)
			System.out.println("Commandline ERROR: "+error);

		System.out.println("Field Summary:");
		System.out.println("-i,-in,--input <file>\tSpeficy input file.");
		System.out.println("-o,-out,--output <file>\tSpeficy output file.");
		System.out.println("-it\t\t\tOutput file in italian decimal format.");
		System.out.println("-en\t\t\tOutput file in english decimal format (default).");
		System.out.println("-d<delimiter>\t\tOutput file uses <delimiter> as delimiter string. (default: \";\")");
		System.out.println("-q,--quiet\t\tBe quiet");
		System.out.println("-v,--verbose\t\tBe verbose. Write twice if you want yCSV to be even more verbose.");
		System.out.println("--convert\t\tOperation: Convert file.");
		System.out.println("--help\t\t\tOperation: Print this help menu and quit.");
		System.out.println("--info\t\t\tOperation: Print information about input file and quit.");
		System.out.println("--win\t\t\tOperation: Display yCSV Window.");
		System.out.println("--xml <file>\t\tOperation: Convert csv file in xml.");
		System.out.println("");
		System.out.println("Examples:");
		System.out.println("yCSV --help\nPrint this help menu.\n");
		System.out.println("yCSV --info -i <file.csv>\nPrint informations about input csv file.\n");
		System.out.println("yCSV --convert -i <input.csv> -o <output.csv> [-en|-it] [-d<delimiter>]\nConvert a csv file.\n");
		System.out.println("yCSV --convert -i <input.csv> -xml <output.csv> [-en|-it] [-d<delimiter>]\nConvert a csv file into an xml one.");
		System.out.println("...and, please remember that \"-dspace\" will use spaces as delimiters in the output file.");
	}
}
