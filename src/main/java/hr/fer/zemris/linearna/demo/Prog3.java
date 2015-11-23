package hr.fer.zemris.linearna.demo;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.Matrix;

/**
 * Example of matrices usage.
 * @author Nikola Sekulić
 *
 */
public class Prog3 {

	/**
	 * Main method of program. Program resolves two equations with two variables.
	 * Variables are x and y. Equations are 3x + 5y = 2 and 2x + 8y = 10.
	 * 
	 * @param args are ignored
	 */
	public static void main(String[] args) {
		
		IMatrix a = Matrix.parseSimple("3 5 | 2 10");
		IMatrix r = Matrix.parseSimple("2 | 8");
		IMatrix v = a.nInvert().nMultiply(r);
		
		System.out.println("3x + 5y = 2");
		System.out.println("2x +10y = 8");
		System.out.println();
		
		System.out.println("Rjesenje sustava je: ");
		System.out.println(v);

	}

}
