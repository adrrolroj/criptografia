
package criptografia.utiles;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import criptografia.cifrado.DataDivision;

public class OmegaGenerator {

	public DataDivision	data;
	public int			a;
	public int			b;
	public int			c;
	public int			d;
	public int[]		X;
	private Integer		L;
	private int			F;
	private int[][]		matrixA;
	private int			E;
	private int[]		U	= {
		2, 3, 5, 1
	};


	public void setup(final DataDivision data, final int a, final int b, final int c, final int d, final int[] x) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.X = x;
		this.data = data;
		this.L = data.getHeight() * data.getWidth();
		this.F = a * (c + 3) + 2 * b * c * (a + 1) + 2;
		this.E = 2 * (a + 1) * (d + b * c * (d + 1)) + a * (c + 1) * (d + 1);
		this.matrixA = this.calculateA();
	}

	private int[][] calculateA() {
		int[][] result = new int[4][4];
		result[0][0] = (2 * this.b + this.c) * (this.d + 1) + 3 * this.d + 1;
		result[0][1] = 2 * (this.b + 1);
		result[0][2] = 2 * this.b * this.c + this.c + 3;
		result[0][3] = 4 * this.b + 3;
		result[1][0] = this.E;
		result[1][1] = 2 * (this.a + this.b + this.a * this.b) + 1;
		result[1][2] = this.F;
		result[1][3] = 3 * this.a + 4 * this.b * (this.a + 1) + 1;
		result[2][0] = 3 * this.b * this.c * (this.d + 1) + 3 * this.d;
		result[2][1] = 3 * this.b + 1;
		result[2][2] = 3 * this.b * this.c + 3;
		result[2][3] = 6 * this.b + 1;
		result[3][0] = this.c * (this.b + 1) * (this.d + 1) + this.d;
		result[3][1] = this.b + 1;
		result[3][2] = this.b * this.c + this.c + 1;
		result[3][3] = 2 * this.b + 2;
		return result;
	}

	public int[][] run() {
		int i = (int) Math.ceil(this.L / 16.0);
		int[][] result = new int[this.data.getHeight()][this.data.getWidth()];
		int[] Y = new int[i * 4];
		BigInteger[] Z = new BigInteger[i * 4];
		for (int j = 1; j <= i; j++) {
			int t = this.X[0] * this.U[0] + this.X[1] * this.U[1] + this.X[2] * this.U[2] + this.X[3] * this.U[3];
			for (int b = 1; b <= t; b++) {
				this.X = this.multiplicaAX();
			}
			int v = 0;
			for (int r = (j - 1) * 4; r <= Y.length - 1; r++) {
				Y[r] = this.X[v];
				if (v >= 3) {
					v = 0;
				} else {
					v++;
				}
			}
		}
		for (int o = 0; o <= Y.length - 1; o++) {
			Long l = (long) (Math.pow(2, 32) * Y[o]);
			Z[o] = new BigInteger(l.toString());
			Z[o] = OmegaGenerator.convertirDecimalABinarioManual(l);
		}
		List<String> Phi = new ArrayList<String>();
		return result;
	}

	private int[] multiplicaAX() {
		int[] result = new int[4];
		for (int w = 0; w <= 3; w++) {
			for (int p = 0; p <= 3; p++) {
				result[w] = (result[w] + this.matrixA[w][p] * this.X[p]) % 2;
			}
		}
		return result;
	}

	private static BigInteger convertirDecimalABinarioManual(final long decimal) {
		return new BigInteger(Long.toBinaryString(decimal));
	}

}
