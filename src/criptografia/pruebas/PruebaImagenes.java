
package criptografia.pruebas;

import java.util.Collection;

import criptografia.cifrado.DataDivision;
import criptografia.utiles.SearchInterestRegion;
import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ImageProcessor;

public class PruebaImagenes {

	public static void main(final String[] args) {
		ImagePlus imgPlus = new ImagePlus("images/paisaje.jpg");
		ImageProcessor imgProcessor = imgPlus.getProcessor();
		imgProcessor.set(20, 30, 255);

		int[] rgb = new int[3];
		imgProcessor.getPixel(20, 30, rgb);
		SearchInterestRegion search = new SearchInterestRegion();
		search.setup(imgProcessor, 90, 5);
		System.out.println("R:" + rgb[0] + " G:" + rgb[1] + " B:" + rgb[2] + " Total:" + imgProcessor.getPixel(20, 30));
		System.out.println("HEIGTH:" + imgProcessor.getHeight() + " WIDTH:" + imgProcessor.getWidth());

		Collection<DataDivision> datas = search.run();
		for (DataDivision data : datas) {
			System.out.println("left:" + data.getLeft() + " top:" + data.getTop() + " height:" + data.getHeight() + " width:" + data.getWidth() + " ROI:" + data.getROI().toString());
		}
		ImagePlus imp2 = new ImagePlus("ALM", search.imgProcessor);
		FileSaver fs = new FileSaver(imp2);
		fs.saveAsJpeg("images/prueba3.jpg");
		System.out.println("HEIGTH:" + imgProcessor.getHeight() + " WIDTH:" + imgProcessor.getWidth());

	}

}
