
package criptografia.utiles;

import java.util.ArrayList;
import java.util.Collection;

import criptografia.cifrado.DataDivision;
import ij.process.ImageProcessor;

public class SearchInterestRegion {

	public int				umbral;
	public int				s;
	public ImageProcessor	imgProcessor;
	private int				height;
	private int				width;


	public void setup(final ImageProcessor imgProcessor, final int umbral, final int s) {
		this.umbral = (int) Math.pow((umbral + 20) * 2, 3);
		this.s = s;
		this.imgProcessor = imgProcessor;
		this.height = imgProcessor.getHeight();
		this.width = imgProcessor.getWidth();
	}
	public Collection<DataDivision> run() {
		Collection<DataDivision> result = new ArrayList<DataDivision>();
		for (int j = 0; j < this.height; j = j + this.s) {
			for (int i = 0; i < this.width; i = i + this.s) {
				if (i >= this.width || j >= this.height) {
					break;
				} else {
					int mean = this.mean(i, j);
					int e = this.blockValue(i, j, mean);
					DataDivision data = new DataDivision();
					data.setTop(j);
					data.setLeft(i);
					if (j + this.s >= this.height) {
						data.setHeight(this.s - (j + this.s - this.height));
					} else {
						data.setHeight(this.s);
					}
					if (i + this.s >= this.width) {
						data.setWidth(this.s - (i + this.s - this.width));
					} else {
						data.setWidth(this.s);
					}
					data.setROI(e > this.umbral);
					if (e > this.umbral) {
						this.imgProcessor = this.convert(i, j);
					}
					result.add(data);
				}
			}
		}
		return result;
	}

	private ImageProcessor convert(final int i, final int j) {
		ImageProcessor res = this.imgProcessor;
		for (int x = j; x < this.s + j; x = x + 1) {
			for (int z = i; z < this.s + i; z = z + 1) {
				if (z >= this.width || x >= this.height) {
					break;
				} else {
					res.convertToRGB().set(z, x, -255);
				}
			}
		}
		return res;
	}

	private int blockValue(final int i, final int j, final int mean) {
		int result = 0;
		for (int x = j; x < this.s + j; x = x + 1) {
			for (int z = i; z < this.s + i; z = z + 1) {
				if (z >= this.width || x >= this.height) {
					break;
				} else {
					result += Math.abs(this.imgProcessor.getPixel(z, x) - mean);
				}
			}
		}
		return result;
	}
	private int mean(final int i, final int j) {
		int result = 0;
		int count = 0;
		for (int x = j; x < this.s + j; x = x + 1) {
			for (int z = i; z < this.s + i; z = z + 1) {
				if (z >= this.width || x >= this.height) {
					break;
				} else {
					result += this.imgProcessor.getPixel(z, x);
					count++;
				}
			}
		}
		return result / count;
	}
}
