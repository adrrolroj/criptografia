
package criptografia.utiles;

import java.awt.Rectangle;

import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.gui.Roi;
import ij.process.ImageProcessor;

public class ExtractSubImage {

	public int			left	= 0;
	public int			top		= 0;
	public int			height	= 500;
	public int			width	= 1000;

	public ImagePlus	imp		= null;


	public void setup(final ImagePlus imp, final int left, final int top, final int height, final int width) {
		this.imp = imp;
		this.left = left;
		this.top = top;
		this.height = height;
		this.width = width;
	}

	public ImageProcessor run(final ImageProcessor ip) {
		Roi roi = this.imp.getRoi();
		if (roi != null) {
			@SuppressWarnings("deprecation")
			Rectangle rect = roi.getBoundingRect();
			this.left = rect.x;
			this.top = rect.y;
			this.width = rect.width;
			this.height = rect.height;
		}

		ip.setRoi(this.left, this.top, this.width, this.height);
		ImageProcessor ip2 = ip.crop();
		return ip2;
	}

	boolean getParameters() {
		GenericDialog gd = new GenericDialog("Gaussian Filter");
		gd.addNumericField("Left", this.left, 0);
		gd.addNumericField("Top", this.top, 0);
		gd.addNumericField("Width", this.width, 0);
		gd.addNumericField("Height", this.height, 0);
		gd.showDialog();
		if (gd.wasCanceled()) {
			return false;
		}
		this.left = (int) gd.getNextNumber();
		this.top = (int) gd.getNextNumber();
		this.width = (int) gd.getNextNumber();
		this.height = (int) gd.getNextNumber();
		return true;
	}
}
