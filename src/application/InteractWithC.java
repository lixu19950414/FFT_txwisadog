package application;

public class InteractWithC {
	static{
		System.loadLibrary("FFT");
	}
	
	public native static double FFT(int NumSamples, boolean InverseTransform,
			float[] RealIn, float[] ImagIn, float[] RealOut, float[] ImagOut);
	
	public static double timeFFT(int NumSamples, boolean InverseTransform,
			float[] RealIn, float[] ImagIn, float[] RealOut, float[] ImagOut) {
		return FFT(NumSamples, InverseTransform, RealIn, ImagIn, RealOut, ImagOut);
	}
}
