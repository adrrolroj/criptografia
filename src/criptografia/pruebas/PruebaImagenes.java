
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
		ImagePlus imgPlus = new ImagePlus("images/Cripto.jpg");
		ImageProcessor imgProcessor = imgPlus.getProcessor();

		int[] rgb = new int[3];
		imgProcessor.getPixel(1, 0, rgb);
		SearchInterestRegion search = new SearchInterestRegion();
		ImagePlus imgPlus2 = new ImagePlus("images/Cripto.jpg");
		ImageProcessor imgProcessor2 = imgPlus2.getProcessor();
		search.setup(imgProcessor2, 90, 5);
		System.out.println("R:" + rgb[0] + " G:" + rgb[1] + " B:" + rgb[2]);
		System.out.println("HEIGTH:" + imgProcessor.getHeight() + " WIDTH:" + imgProcessor.getWidth());

		Collection<DataDivision> datas = search.run();
		//for (DataDivision data : datas) {
		//System.out.println("left:" + data.getLeft() + " top:" + data.getTop() + " height:" + data.getHeight() + " width:" + data.getWidth() + " ROI:" + data.getROI().toString());
		//}
		for (int i = 0; i <= 5 - 1; i++) {
			for (int j = 0; j <= 5 - 1; j++) {
				int[] rgb2 = new int[3];
				imgProcessor.getPixel(i, j, rgb2);
				System.out.print(rgb2[0] + "	");
			}
			System.out.println(" ");
		}
		System.out.println("--------------------------------");
		for (int i = 0; i <= 5 - 1; i++) {
			for (int j = 0; j <= 5 - 1; j++) {
				int[] rgb2 = new int[3];
				imgProcessor.getPixel(i, j, rgb2);
				System.out.print(rgb2[1] + "	");
			}
			System.out.println(" ");
		}
		System.out.println("--------------------------------");
		for (int i = 0; i <= 5 - 1; i++) {
			for (int j = 0; j <= 5 - 1; j++) {
				int[] rgb2 = new int[3];
				imgProcessor.getPixel(i, j, rgb2);
				System.out.print(rgb2[2] + "	");
			}
			System.out.println(" ");
		}
		System.out.println("------------------------------");
		OmegaGenerator omega = new OmegaGenerator();
		omega.setup(datas.stream().findFirst().get(), 7, 8, 2, 2, new double[] {
			0.2, 0.7, 1, 0.3
		});
		OmegaGenerator psi = new OmegaGenerator();
		psi.setup(datas.stream().findFirst().get(), 6, 1, 5, 2, new double[] {
			0.4, 0.8, 0.1, 0.9
		});
		int[][] sal = omega.run();
		int[][] sal2 = psi.run();
		System.out.println("-----------OMEGA--------------");
		for (int i = 0; i <= sal.length - 1; i++) {
			for (int j = 0; j <= sal[0].length - 1; j++) {
				System.out.print(sal[i][j] + "	");
			}
			System.out.println(" ");
		}
		System.out.println("-----------PSI--------------");
		for (int i = 0; i <= sal2.length - 1; i++) {
			for (int j = 0; j <= sal2[0].length - 1; j++) {
				System.out.print(sal2[i][j] + "	");
			}
			System.out.println(" ");
		}
		ImagePlus imp2 = new ImagePlus("ALM", search.imgProcessor);
		FileSaver fs = new FileSaver(imp2);
		fs.saveAsJpeg("images/prueba3.jpg");

	}

}
