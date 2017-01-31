/**
 * @(#)Functions.java
 *
 *
 * @author Miro Salvagni
 * @version 1.00 2010/10/28
 */


//import java.util.*;


public class Functions
{
	public static double min(double[] x)
	{
		if (x.length == 0)
			return 0;

		double s = x[0];

		for (int i=1; i<x.length; i++)
			if (x[i] < s)
				s = x[i];

		return s;
	}


	public static double max(double[] x)
	{
		if (x.length == 0)
			return 0;

		double s = x[0];

		for (int i=1; i<x.length; i++)
			if (x[i] > s)
				s = x[i];

		return s;
	}


	public static double sum(double[] x)
	{
		double s = 0;

		for (int i=0; i<x.length; i++)
			s += x[i];

		return s;
	}


	public static double product(double[] x)
	{
		double s = 1;

		for (int i=0; i<x.length; i++)
			s *= x[i];

		return s;
	}


	public static double geometric_mean(double[] x)
	{
		return Math.pow(product(x), 1/(double)x.length);
	}


	public static double energetic_mean(double[] x)
	{
		double s = 0;

		for (int i=0; i<x.length; i++)
			s += Math.pow(10, x[i]/10);

		return 10*Math.log(s/x.length)/Math.log(10);
	}


	public static int count(double[] x, double e)
	{
		int c = 0;

		for (int i=0; i<x.length; i++)
			if (x[i] == e)
				c++;

		return c;
	}


	public static int index_of(double[] x, double e)
	{
		return index_of(x, e, 0);
	}


	public static int index_of(double[] x, double e, int first_index)
	{
		for (int i=first_index; i<x.length; i++)
			if (x[i] == e)
				return i;

		return -1;
	}


	public static int last_index_of(double[] x, double e)
	{
		return index_of(x, e, x.length-1);
	}


	public static int last_index_of(double[] x, double e, int last_index)
	{
		for (int i=last_index; i>=0; i--)
			if (x[i] == e)
				return i;

		return -1;
	}


	public static int count_equal_or_less(double[] x, double e)
	{
		int c = 0;

		for (int i=0; i<x.length; i++)
			if (x[i] <= e)
				c++;

		return c;
	}


	public static int count_equal_or_more(double[] x, double e)
	{
		int c = 0;

		for (int i=0; i<x.length; i++)
			if (x[i] >= e)
				c++;

		return c;
	}


	public static double mean(double[] x)
	{
		return x.length == 0 ? 0 : sum(x)/x.length;
	}


	public static double rms(double[] x)
	{
		double r = 0;

		for (int i=0; i<x.length; i++)
			r += Math.pow(x[i], 2);

		return r;
	}


	public static double harmonic_mean(double[] x)
	{
		double r = 0;

		for (int i=0; i<x.length; i++)
			r += 1/x[i];

		return 1/r;
	}


	public static double variance(double[] x)
	{
		double s = 0;
		double s2 = 0;

		for (int i=0; i<x.length; i++) {
			s += x[i];
			s2 += Math.pow(x[i], 2);
		}

		return (s2-Math.pow(s, 2))/x.length;
	}


	public static double standard_deviation(double[] x)
	{
		return Math.sqrt(variance(x));
	}


	public static double dispersion(double[] x)
	{
		double s = 0;
		double s2 = 0;

		for (int i=0; i<x.length; i++) {
			s += x[i];
			s2 += Math.pow(x[i], 2);
		}

		return (s2-Math.pow(s, 2))/s;
	}


	public static double correlation(double[] x, double[] y)
	{
		if (x.length != y.length)
			return 999;

		double sum_x = 0;
		double sum_y = 0;
		double sum_x2 = 0;
		double sum_y2 = 0;

		for (int i=0; i<x.length; i++) {
			sum_x += x[i];
			sum_y += y[i];
			sum_x2 += Math.pow(x[i], 2);
			sum_y2 += Math.pow(y[i], 2);
		}

		double mean_x = sum_x/x.length;
		double mean_x2 = sum_x2/x.length;

		double mean_y = sum_y/y.length;
		double mean_y2 = sum_y2/y.length;

		double sx = Math.sqrt(mean_x2 - Math.pow(mean_x, 2));
		double sy = Math.sqrt(mean_y2 - Math.pow(mean_y, 2));

		double corr = 0;

		for (int i=0; i<x.length; i++)
			corr += (x[i] - mean_x)*(y[i] - mean_y);

		return corr/(x.length*sx*sy);
	}


	public static double yfunc(double[] x)
	{
		return yfunc(x, 0.5);
	}


	public static double yfunc(double[] x, double k)
	{
		double r = x[0];

		for (int i=1; i<x.length; i++)
			r = k*r + (1-k)*x[i];

		return r;
	}


	// funzioni da sharpTrade
	public static double[] calculate_d(double[] f)
	{ return calculate_d(f, 1); }

	public static double[] calculate_d2(double[] f)
	{ return calculate_d2(f, 1); }

	 public static double[] calculate_dperc(double[] f)
	{ return calculate_d(f, 1); }


	// derivata prima
	public static double[] calculate_d(double[] f, int n)
	{
		if (f == null  ||  f.length == 0)
			return null;

		double[] ret = new double[f.length];

		for (int i = 0; i < n; i++)
			ret[i] = 0;

		for (int i = n; i < f.length; i++)
			ret[i] = (f[i] - f[i - n])/n;


		return ret;
	}

	// derivata seconda
	public static double[] calculate_d2(double[] f, int n)
	{
		if (f == null || f.length == 0)
			return null;

		double[] ret = new double[f.length];

		for (int i = 0; i < 2*n; i++)
			ret[i] = 0;

		for (int i = 2*n; i < f.length; i++)
			ret[i] = (f[i] - 2*f[i - n] + f[i - 2*n])/Math.pow(n, 2);


		return ret;
	}

	// variazione percentuale n giorni precedenti
	public static double[] calculate_dperc(double[] f, int n)
	{
		if (f == null || f.length == 0)
			return null;

		double[] ret = new double[f.length];

		for (int i = 0; i < n; i++)
			ret[i] = 0;

		for (int i = n; i < f.length - 2; i++)
			ret[i] = (f[i] - f[i - n])/f[i];


		return ret;
	}

	// media mobile (lineare)
	public static double[] calculate_mm(double[] values, int n)
	{
		double[] w = new double[n];
		for (int i = 0; i < n; i++)
			w[i] = 1;

		return calculate_mm_gen(values, n, w);
	}

	// media mobile (esponenziale)
	public static double[] calculate_emm(double[] values, int n, double alpha) // alpha < 1
	{
		double[] w = new double[n];
		for (int i = 0; i < n; i++)
			w[i] = Math.pow(alpha, i);

		return calculate_mm_gen(values, n, w);
	}

	// media mobile (generalizzata)
	private static double[] calculate_mm_gen(double[] values, int n, double[] w)
	{
		double[] newvalues = new double[values.length];

		double[] norm = new double[n];
		norm[0] = w[0];
		for (int i = 1; i < n; i++)
			norm[i] = norm[i - 1] + w[i];

		double[] past = new double[n];
		for (int i = 0; i < n; i++)
			past[i] = 0;

		for (int i = 0; i < values.length; i++)
		{
			for (int j = 0; j < n; j++)
				past[j] += values[i] * w[j];

			newvalues[i] = past[0] / norm[i < n ? i : n - 1];

			// shift left
			for (int k = 0; k < n - 1; k++)
				past[k] = past[k + 1];
			past[n - 1] = 0;
		}

		return newvalues;
	}
	public static double[] calculate_mm_base_new(double[] values, int n, double _base)
	{
		if (n==1) return values;

		int len = values.length;
		double[] newvalues = new double[len];
		for(int i=0;i<len;i++)
			newvalues[i] = 0;

		double[] past = new double[n];
		for (int i = 0; i < n; i++)
			past[i] = 0;


		double normalize=0;
		for(int i = 0;i<n;i++)
			normalize+= 1/Math.pow(_base,i+1);

		for (int i = 0; i < len; i++)
		{
			for (int j = 0; j < n; j++){
				past[j] += values[i];
				past[j] /= _base;
			}

			newvalues[i] = past[0]/normalize;

			// shift left
			for (int k = 0; k < n - 1; k++)
				past[k] = past[k + 1];

			past[n - 1] = 0;
		}

		for(int i=0;i<n;i++)
			newvalues[i] = 0;

		return newvalues;

	}



	public static double[] calculate_mm_base(double[] values, int n, double _base)
	{
		int len = values.length;
		double[] ret = new double[len];
		for(int i=0;i<len;i++)
			ret[i] = 0;

		double normalize = (Math.pow(_base,n)-1);
		if (_base == 1) normalize = n;

		for(int i=n;i<len;i++){
			for(int j=0;j<n;j++)
				ret[i] += (values[i-j] * Math.pow(_base,n-j-1));

			ret[i]/=normalize;
		}

		return ret;
	}

	public static double[] calculate_leqm(double[][] values)
	{
		double[] newvalues = new double[values[0].length];

		for (int k=0; k<values[0].length; k++)
		{
			double ener = 0;
			int count = 0;
			
			for (int i=0; i<values.length; i++) {
				ener += Math.pow(10, values[i][k]/10);
				count++;
			}
		
			newvalues[k] = 10*Math.log10(ener/count);
		}

		return newvalues;
	}
	
	
	
	
	
	
	
	// deviazione standard (lineare)
	public static double[] calculate_sigma(double[] values, int n)
	{
		double[] w = new double[n];
		for (int i = 0; i < n; i++)
			w[i] = 1;

		return calculate_sigma_gen(values, n, w);
	}

	// deviazione standard (esponenziale)
	public static double[] calculate_esigma(double[] values, int n, double alpha)
	{
		double[] w = new double[n];
		for (int i = 0; i < n; i++)
			w[i] = Math.pow(alpha, i);

		return calculate_sigma_gen(values, n, w);
	}

	// deviazione standard (generalizzata)
	private static double[] calculate_sigma_gen(double[] values, int n, double[] w) // ok
	{
		double[] newvalues = new double[values.length];

		double[] norm = new double[n];
		norm[0] = w[0];
		for (int i = 1; i < n; i++)
			norm[i] = norm[i - 1] + w[i];

		double[] past = new double[n];
		double[] past2 = new double[n];
		for (int i = 0; i < n; i++)
		{
			past[i] = 0;
			past2[i] = 0;
		}

		for (int i = 0; i < values.length; i++)
		{
			for (int j = 0; j < n; j++)
			{
				past[j] += values[i] * w[j];
				past2[j] += Math.pow(values[i] * w[j], 2);
			}

			double ex = past[0] / norm[i < n ? i : n - 1];
			double ex2 = past2[0] / norm[i < n ? i : n - 1];
			newvalues[i] = Math.sqrt(ex2 - Math.pow(ex, 2));

			// shift left
			for (int k = 0; k < n - 1; k++)
			{
				past[k] = past[k + 1];
				past2[k] = past2[k + 1];
			}
			past[n - 1] = 0;
			past2[n - 1] = 0;
		}

		return newvalues;
	}

	// banda di Bolinger superiore
	public static double[] calculate_BBhigh(double[] values, int n, double lambda)
	{
		double[] newvalues = calculate_mm(values, n);
		double[] valuesvar = calculate_sigma(values, n);

		for (int i = 0; i < values.length; i++){

			newvalues[i] += lambda * valuesvar[i];
		}

		return newvalues;
	}

	// banda di Bolinger inferiore
	public static double[] calculate_BBlow(double[] values, int n, double lambda)
	{
		double[] newvalues = calculate_mm(values, n);
		double[] valuesvar = calculate_sigma(values, n);

		for (int i = 0; i < values.length; i++){
//				System.Console.WriteLine(values[i] + " " + newvalues[i] + " " + valuesvar[i] + " " + newvalues[i]+lambda*valuesvar[i]);
			newvalues[i] -= lambda * valuesvar[i];

		}

		return newvalues;
	}


	// Indicatore stocastico K
	public static double[] calculate_stochasticK(double[] p, double[] pmax, double[] pmin)
	{
		double[] newvalues = new double[p.length];

		for (int i = 0; i < p.length; i++)
		{
			newvalues[i] = 100 * (p[i] - pmin[i])/(pmax[i]-pmin[i]);
		}

		return newvalues;
	}

	// Indicatorie stocastico D (media mobile lineare di K)
	public static double[] calculate_stochasticD(double[] f, int n)
	{
		double[] newvalues = new double[f.length];
		newvalues = calculate_mm(f,n);
		return newvalues;
	}

	// Indicatorie stocastico D (media mobile esponenziale di K)
	public static double[] calculate_stochasticD(double[] f, int n, int alpha) // alpha < 1
	{

		double[] newvalues = new double[f.length];
		newvalues = calculate_emm(f,n,alpha);
		return newvalues;
	}





	// Relative Strength Index (contando il # di variazioni positive e negative)
	public static double[] calculate_RSIC(double[] values, int n)
	{
		double[] newvalues = new double[values.length];
		double zero = Math.pow(10,-4);

		double[] pastp = new double[n];
		double[] pastm = new double[n];
		for (int i = 0; i < n; i++)
		{
			pastp[i] = zero;
			pastm[i] = zero;
		}

		newvalues[0] = 0;
		for (int i = 1; i < values.length; i++)
		{
			for (int j = 0; j < n; j++)
			{
				if (values[i] > values[i - 1])
					pastp[j]++;
				if (values[i] < values[i - 1])
					pastm[j]++;
			}

			newvalues[i] = 100 - 100/(1+pastp[0]/pastm[0]);

			// shift left
			for (int k = 0; k < n - 1; k++)
			{
				pastp[k] = pastp[k + 1];
				pastm[k] = pastm[k + 1];
			}
			pastp[n - 1] = zero;
			pastm[n - 1] = zero;
		}

		return newvalues;
	}


	// Relative Strength Index (contando l'entità delle variazioni positive e negative)
	public static double[] calculate_RSIV(double[] values, int n)
	{
		double[] newvalues = new double[values.length];
		double zero = Math.pow(10,-4);

		double[] pastp = new double[n];
		double[] pastm = new double[n];
		for (int i = 0; i < n; i++)
		{
			pastp[i] = zero;
			pastm[i] = zero;
		}

		newvalues[0] = 0;
		for (int i = 1; i < values.length; i++)
		{
			for (int j = 0; j < n; j++)
			{
				if (values[i] > values[i - 1])
					pastp[j] += values[i]/values[i-1]-1;
				if (values[i] < values[i - 1])
					pastm[j] -= values[i]/values[i - 1]-1;            // negative.
			}

			newvalues[i] = 100 - 100/(1+pastp[0]/pastm[0]);

			// shift left
			for (int k = 0; k < n - 1; k++)
			{
				pastp[k] = pastp[k + 1];
				pastm[k] = pastm[k + 1];
			}
			pastp[n - 1] = zero;
			pastm[n - 1] = zero;
		}

		return newvalues;
	}

	public static double[] min(double[] data,int n)
	{
		double[] newvalues = new double[data.length];

		for(int i =0;i<n;i++)
		{
			newvalues[i]=0;
		}

		for(int i=n;i<data.length;i++)
		{
			newvalues[i] = data[i];
			for(int j=1;j<n;j++)
			{
				newvalues[i] = Math.min(newvalues[i],data[i-j]);
			}
		}


		return newvalues;
	}




	public static double[] sub(double[] data, int begin, int end)
	{
		double[] res = new double[end - begin + 1];

		for (int i = 0; i < res.length; i++)
			res[i] = data[i+begin];

		return res;
	}


	// Media di un campione
	public static double scalar_mean(double[] values)
	{
		double sum = 0;

		for (int i = 0; i < values.length; i++)
			sum += values[i];

		return (sum / values.length);
	}


	// Deviazione standard di un campione
	public static double scalar_sigma(double[] values)
	{
		double sum = 0;
		double sum2 = 0;


		for (int i = 0; i < values.length; i++)
		{
			sum += values[i];
			sum2 += Math.pow(values[i], 2);
		}

		sum /= values.length;
		sum2 /= values.length;

		return Math.sqrt(sum2 - Math.pow(sum, 2));
	}


	//  Minimo, Massimo, Media, Deviazione Standard di un campione
	public static int[] stats(double[] data)
	{
		int len = data.length;
		double[] res = new double[4]; // min,max,mean,sigma
		double min = data[0];
		double max = data[0];
		double sum = data[0];
		double sum2 = Math.pow(data[0],2);
		double current;
		double current2;
		for(int i=1;i<len;i++)
		{
			current = data[i];
			current2 = Math.pow(data[i],2);
			if(current>max)
				max = current;

			if(current<min)
				min = current;

			sum += current;
			sum2 += current2;
		}
		res[0] = min;
		res[1] = max;
		res[2] = sum/len;
		sum /= len;
		sum2 /= len;
		res[3] = Math.sqrt(sum2 - Math.pow(sum, 2));
		int[] ret = new int[4];
		for(int i=0;i<4;i++){
			ret[i] = (int) res[i];
		}
		return ret;
	}


	// Trasla in giù l'array (al posto del valore di oggi mette quello di ieri)
	public static double[] trasla_down(double[] data, int n)
	{
		int len = data.length;
		double[] res = new double[len];
		for(int i=0;i<n;i++){
			res[i] = 0;
		}
		for(int i=n;i<len;i++){
			res[i]=data[i-n];
		}
		return res;
	}

	// Trasla in su l'array (al posto del valore di oggi mette quello di domani)
	public static double[] trasla_up(double[] data, int n)
	{
		int len = data.length;
		double[] res = new double[len];
		for(int i=0;i<len-n;i++){
			res[i] = data[i+n];
		}
		for(int i=len-n;i<len;i++){
			res[i] = 0;
		}
		return res;
	}

	public static double[] divide(double[] data1,double[] data2)
	{
		if (data1.length!=data2.length) return null;

		double[] ret = new double[data1.length];

		for(int i=0;i<data1.length;i++)
			if (data2[i]!=0)
				ret[i] = data1[i]/data2[i];

		return ret;
	}

	public static double[] multiply(double[] data,double val)
	{
		double[] ret = new double[data.length];

		for(int i=0;i<data.length;i++)
			ret[i] = data[i]*val;

		return ret;
	}
	
	
	public static double[] calculate_Pplus(double[] data, int n) { return calculate_Pplus(data,n,0); }
	public static double[] calculate_Pplus(double[] data, int n, double alpha)
	{
		double[] ret = new double[data.length];	
	
		for (int i=0; i<data.length; i++) {
			ret[i] = 0;
			
			for (int k=i+1; k<Math.min(data.length, i+1+n); k++)
				if (data[k] - data[i] > data[i]*alpha)
					ret[i]++;
			
			ret[i] /= Math.min(data.length, i+1+n);
		}
		
		return ret;
	}

	public static double[] calculate_Pminus(double[] data, int n) { return calculate_Pminus(data,n,0); }
	public static double[] calculate_Pminus(double[] data, int n, double alpha)
	{
		double[] ret = new double[data.length];	
	
		for (int i=0; i<data.length; i++) {
			ret[i] = 0;
			
			for (int k=i+1; k<Math.min(data.length, i+1+n); k++)
				if (data[k] - data[i] < data[i]*alpha)
					ret[i]++;
			
			ret[i] /= Math.min(data.length, i+1+n);
		}
		
		return ret;
	}
	
	public static double[] calculate_P0(double[] data, int n) { return calculate_P0(data,n,0); }
	public static double[] calculate_P0(double[] data, int n, double alpha)
	{
		double[] Pplus = calculate_Pplus(data,n,alpha);
		double[] Pminus = calculate_Pminus(data,n,alpha);
		double[] ret = new double[data.length];	
		
		for (int i=0; i<data.length; i++)
			ret[i] = 1-Pplus[i]-Pminus[i];
		
		return ret;
	}
	
	public static double[] calculate_trade(double[] data, double c0)
	{
		double[] Pplus = calculate_Pplus(data, 30, 0.04);
		
		double[] d1 = calculate_dperc(data);
		double[] mmd1_5 = calculate_mm(d1, 5);
		
		double[] d2 = calculate_d2(data);
		double[] mmd2_5 = calculate_mm(d2, 5);
//		double[] mm20 = calculate_mm(data, 20);
//		double[] mm60 = calculate_mm(data, 60);
		
		
		final double Arand = 0.05;
		
		for (int i=0; i<Pplus.length; i++)
			Pplus[i] += (Math.random()-0.5)*2*Arand;
		
		
		double[] ret = new double[data.length];
		
		// capital
		double n = 0;
		double c = c0;
		@SuppressWarnings("unused")
		int ibuy = 0;
		@SuppressWarnings("unused")
		double open_val = 0;
		
		for (int i=0; i<data.length; i++) {
			if (n==0) {
				// if (Pplus[i] > 0.10) {
				//if (d1[i] > 0 && d2[i] > 0) {
				if (mmd1_5[i] > 0.05 && mmd2_5[i] > 0) {
					n = (int) (Math.floor(c/data[i])/2);
					c -= n*data[i];
//					c -= n*data[i]*0.05;		// commission
					open_val = data[i];
					ibuy=i;
				}
			}
			else {
				// if (i-ibuy >= 7 && Pplus[i] < 0.1)
				// if (d1[i] < 0 && d2[i] < 0) {
				if (mmd1_5[i] < 0 && mmd2_5[i] < 0) {
					c += n*data[i];
					
//					if (data[i] > open_val)			// commission
//						c -= n*(data[i]-open_val)*0.03;
					
					n=0;
					ibuy=i;
				}
			}
			
			ret[i] = c; //+n*data[i];
		}
		
		if (n>0)
			ret[data.length-1]+=n*data[data.length-1];
			
		
		return ret;
	}	
}
