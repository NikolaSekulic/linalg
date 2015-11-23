package hr.fer.zemris.linearna.demo;

/**
 * Example of vectors usage.
 */
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.Vector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Prog5 {

	/**
	 * Main method of program. Program calculates vector r that is reflection of
	 * vector m in regard vector n. Vectors m and n are defined by user from
	 * standard input.
	 * 
	 * @param args
	 *            are ignored
	 */
	public static void main(String[] args) {

		IVector m = null;
		IVector n = null;
		IVector r = null;

		while (true) {
			System.out.println("Unesite vektor n: ");
			n = readVector();

			if (n == null || n.getDimension() < 2 || n.getDimension() > 3) {
				System.out.println("Dimnezija vektora m mora biti 2 ili 3!");
				continue;
			}

			break;
		}

		while (true) {
			System.out.println("Unesite vektor m: ");
			m = readVector();

			if (m == null || m.getDimension() != n.getDimension()) {
				System.out.println("Vektori m i n moraju biti iste dimenzije");
				continue;
			}

			break;
		}

		n = n.normalize();

		r = n.scalarMultiply(m.scalarProduct(n)).nScalarMultiply(2).nSub(m);

		System.out.println("Reflektirani vektor m u odnosu na n je: ");
		System.out.println(r);

	}

	/**
	 * Reads vector from standard input.
	 * @return new vector
	 */
	private static IVector readVector() {

		IVector vector = null;

		try {

			BufferedReader bf = new BufferedReader(new InputStreamReader(
					System.in, StandardCharsets.UTF_8));

			while (true) {
				String line = bf.readLine();

				try {
					vector = Vector.parseSimple(line);
				} catch (NumberFormatException nfe) {
					System.out.println("Vektor mora sadrzavati samo brojeve!");
					continue;
				}

				break;
			}

		} catch (Exception e) {
			System.out.println("IO error");
			System.exit(-1);
		}

		return vector;
	}
}
