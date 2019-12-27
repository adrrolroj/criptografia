
package criptografia.pruebas;

import java.util.Collection;

import criptografia.cifrado.DataDivision;
import criptografia.utiles.OmegaGenerator;
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
		//obtenemos omega
		OmegaGenerator omega = new OmegaGenerator();
		omega.setup(datas.stream().findFirst().get(), 7, 8, 2, 2, new int[] {
			0, 1, 1, 1
		});
		int[][] resOmega = omega.run();
		
		System.out.println();
		
		for(int i=0; i<resOmega.length; i++) {
			System.out.print("[");
			for(int j = 0; j<resOmega[0].length; j++) {
				System.out.print(resOmega[i][j] + ",	");
			}
			System.out.println("]");
		}
		
		//obtenemos psi
		OmegaGenerator psi = new OmegaGenerator();
		psi.setup(datas.stream().findFirst().get(), 4, 5, 3, 2, new int[] {
				0, 1, 1, 1
		});
		int[][] resPsi = psi.run();
		
		System.out.println();
		
		for(int i=0; i<resPsi.length; i++) {
			System.out.print("[");
			for(int j = 0; j<resPsi[0].length; j++) {
				System.out.print(resPsi[i][j] + ",	");
			}
			System.out.println("]");
		}
		
		ImagePlus imp2 = new ImagePlus("ALM", search.imgProcessor);
		FileSaver fs = new FileSaver(imp2);
		fs.saveAsJpeg("images/prueba3.jpg");
		System.out.println("HEIGTH:" + imgProcessor.getHeight() + " WIDTH:" + imgProcessor.getWidth());

	}

}
