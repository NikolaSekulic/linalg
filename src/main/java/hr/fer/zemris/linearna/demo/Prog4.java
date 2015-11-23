package hr.fer.zemris.linearna.demo;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.Matrix;
import hr.fer.zemris.linearna.Vector;

/**
 * Example of matrices and vectors usage.
 * @author Nikola Sekulić
 *
 */
public class Prog4 {

	/**
	 * Main method of program. Program calculate barycentric coordinates for
	 * vectors[1 0 0], [5 0 0] and [3 8 0]. Center of coordinates is [3 4 0].
	 * 
	 * @param args are ignored
	 */
	public static void main(String[] args) {
		
		IVector a = Vector.parseSimple("1 0 0");
		IVector b = Vector.parseSimple("5 0 0");
		IVector c = Vector.parseSimple("3 8 0");

		IVector t = Vector.parseSimple("3 4 0");
		
		double[][] array = new double[3][0];
		array[0] = a.set(2, 1).toArray();
		array[1] = b.set(2, 1).toArray();
		array[2] = c.set(2, 1).toArray();
		
		t.set(2, 1);
		
		IMatrix m = new Matrix(3, 3, array, false).nTranspose(false);
		
		IMatrix coordinatesMatrix = m.nInvert().nMultiply(t.toColumnMatrix(true));
		
		IVector bCoordinates = coordinatesMatrix.toVector(true);
		
		System.out.println("Baricentricne koordinate su: ");
		System.out.println(bCoordinates);
		
		
		
		
	}

}
