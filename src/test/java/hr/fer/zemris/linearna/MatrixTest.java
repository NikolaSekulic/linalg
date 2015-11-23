package hr.fer.zemris.linearna;

import org.junit.Assert;
import org.junit.Test;

public class MatrixTest {

    @Test
    public void testDeterminant() {

	IMatrix m1 = Matrix.parseSimple( " 1 2 3 | 2 3 4 | 3 4 5" );

	double d = m1.determinant();

	Assert.assertEquals( "Determinant should be 0", 0.0, d, 1e-6 );
    }

    @Test
    public void testDeterminant2() {

	IMatrix m1 = Matrix.parseSimple( "0 1|1|0 0 1|0 0 0 1| 0 0 0 0 1" );

	double d = m1.determinant();

	Assert.assertEquals( "Determinant should be -1", -1.0, d, 1e-6 );
    }

    @Test
    public void testDeterminant3() {

	IMatrix m1 = Matrix.parseSimple( " 1 2 3 | 2 4 6 | 3 6 9" );

	double d = m1.determinant();

	Assert.assertEquals( "Determinant should be 0", 0.0, d, 1e-6 );
    }

    @Test
    public void testDeterminant4() {

	IMatrix m1 = Matrix
		.parseSimple( "0 1 0 0 |0 2 4 6 | 0 3 27 9 | 0 111 11111 99" );

	double d = m1.determinant();

	Assert.assertEquals( "Determinant should be 0", 0.0, d, 1e-6 );
    }

    @Test
    public void testDeterminant5() {

	IMatrix m1 = Matrix.parseSimple( "2 3 5 | 7 11 13 | 17 19 23 " );

	double d = m1.determinant();

	Assert.assertEquals( "Determinant should be -78", -78.0, d, 1e-6 );
    }

    @Test
    public void testDeterminantOfMatrix1x1() {

	double[][] elements = new double[1][1];
	elements[0][0] = 3.14;
	IMatrix m = new Matrix( 1, 1, elements, true );

	double d = m.determinant();

	Assert.assertEquals( "Determinant should be 3.14", 3.14, d, 1e-6 );
    }

    @Test
    public void testDeterminantOrMatrix2x2() {

	IMatrix m = Matrix.parseSimple( " 0 -3 | 4 0 " );
	double d = m.determinant();
	Assert.assertEquals( "Determinant should be 12", 12.0, d, 1e-6 );

    }

    @Test
    public void testInverse1x1() {

	IMatrix m = Matrix.parseSimple( "10" );
	IMatrix i = m.nInvert();

	Assert.assertTrue( "Inverse matrix should be [0.1]",
		((AbstractMatrix) i).equals( Matrix.parseSimple( "0.1" ), 1e-6 ) );
    }

    @Test
    public void testInverst2x2() {

	IMatrix m = Matrix.parseSimple( "3.14 2.78 | 1.42 0.33" );
	IMatrix i = m.nInvert();

	Assert.assertTrue(
		"Inverse matrix should be [-0.113 0.955 | 0.488 -1.079]",
		((AbstractMatrix) i).equals(
			Matrix.parseSimple( "-0.113 0.955 | 0.488 -1.079" ),
			1e-3 ) );
    }

    @Test
    public void testInverse5x5() {

	IMatrix m = Matrix.parseSimple( "1 2 3 4 5" + "| 1.5 2.5 3.5 4.5 5.5"
		+ "| 3.14 10 3.14 10 2.14" + "| 0 1 2 1 0" + "| 1 0 1 0 1" );
	
	

	AbstractMatrix m2 = Matrix
		.parseSimple( "19.000 -19.000   1.000  -0.500   7.360"
			+ "| -43.500  43.000  -2.000   0.500 -14.720"
			+ "|   1.000  -1.000   0.000   0.500   0.500"
			+ "|  41.500 -41.000   2.000  -0.500  13.720"
			+ "| -20.000  20.000  -1.000   0.000  -6.860" );

	IMatrix i = m.nInvert();

	Assert.assertTrue( "Inverse should be same as m2", m2.equals( i, 0.001 ) );
    }

    @Test
    public void testInverseMultiplictionAndMakeIdentity() {

	IMatrix m1 = Matrix.parseSimple( "1 2 3 4" + "| 2 3 5 7"
		+ "| 11 13 17 19" + "| 23 29 31 37" );
	IMatrix m2 = m1.nInvert();
	IMatrix m3 = m1.nMultiply( m2 );

	IMatrix I = m1.copy().makeIdentity();

	Assert.assertTrue( "Matrix m3 sholud be indentity",
		((AbstractMatrix) I).equals( m3, 1e-6 ) );
    }

    @Test
    public void testAddTransposeViewAndMatrixSubView() {

	IMatrix m1 = Matrix.parseSimple( "1 2 3 | 1 2 3 | 1 2 3" );
	IMatrix m2 = Matrix
		.parseSimple( "1 1 1 1 | 2 2 2 2 | 3 3 3 3 | 4 4 4 4" );
	IMatrix m3 = new MatrixTransposeView( m2.subMatrix( 2, 2, true ) );

	m3.add( m1 );

	Matrix m4 = Matrix
		.parseSimple( "2 2 1 2 | 4 4 2 4 | 3 3 3 3 | 7 7 4 7" );

	Assert.assertTrue( "Matix m2 soulld become as matrix m4",
		m4.equals( m2, 1e-6 ) );
    }

    @Test
    public void nAddTest() {

	IMatrix m1 = Matrix.parseSimple( "1 2 3 4 | 1 2 3 4 | 1 2 3 4 " );
	IMatrix m2 = Matrix.parseSimple( "1 1 1 1 | 2 2 2 2 | 3 3 3 3 " );
	IMatrix m3 = m1.nAdd( m2 );
	Matrix m4 = Matrix.parseSimple( "2 3 4 5 | 3 4 5 6 | 4 5 6 7" );
	Assert.assertTrue( "Matrix m3 should be same as m4", m4.equals( m3, 0 ) );
    }

    @Test
    public void nSubTest() {

	IMatrix m1 = Matrix.parseSimple( "1 2 3 4 | 1 2 3 4 | 1 2 3 4 " );
	IMatrix m2 = Matrix.parseSimple( "1 1 1 1 | 2 2 2 2 | 3 3 3 3 " );
	IMatrix m3 = m1.nSub( m2 );
	Matrix m4 = Matrix.parseSimple( "0 1 2 3 | -1 0 1 2 | -2 -1 0 1" );
	Assert.assertTrue( "Matrix m3 should be same as m4", m4.equals( m3, 0 ) );
    }

    @Test
    public void nScalarMultiplyTest() {

	IMatrix m1 = Matrix.parseSimple( "2 4 6 | 1 2 3" );
	IMatrix m2 = m1.nScalarMultiply( 0.5 );

	Matrix m3 = Matrix.parseSimple( "1 2 3 | 0.5 1 1.5" );

	Assert.assertTrue( "Matrix m2 should be sam as m3",
		m3.equals( m2, 0.001 ) );
    }

    @Test
    public void nMultiplyTest() {

	IMatrix m1 = Matrix.parseSimple( "1 1 | 2 2 | 3 3 " );
	IMatrix m2 = Matrix.parseSimple( "1 2 3 4 | 2 3 4 5 " );

	double[][] array = {
		{
			3.000, 5.000, 7.000, 9.000
		}, {
			6.000, 10.000, 14.000, 18.000
		}, {
			9.000, 15.000, 21.000, 27.000
		}
	};

	double[][] matrixArray = m1.nMultiply( m2 ).toArray();

	Assert.assertEquals( "NUmber of rows has o be 3", array.length,
		matrixArray.length );
	Assert.assertArrayEquals( array[0], matrixArray[0], 1e-6 );
	Assert.assertArrayEquals( array[1], matrixArray[1], 1e-6 );
	Assert.assertArrayEquals( array[2], matrixArray[2], 1e-6 );
    }

    @Test
    public void testParseSimpleAndToString() {

	double[][] elements = new double[3][2];

	for ( int i = 0; i < 3; i++ ) {
	    for ( int j = 0; j < 2; j++ ) {
		elements[i][j] = (i * 2) + j;
	    }
	}

	IMatrix m1 = new Matrix( 3, 2, elements, false );
	Matrix m2 = Matrix.parseSimple( m1.toString().replaceAll( "\\[", "" )
		.replaceAll( "\\]", "|" ).replaceAll( "\\s*\\|\\s*\\z", "" ) );

	Assert.assertTrue( "Matrices m1 and m2 should be same",
		m2.equals( m2, 0.0001 ) );
    }

    @Test
    public void testToVecor() {

	Matrix m1 = Matrix.parseSimple( "1 2 3" );
	IVector v2 = m1.toVector( false );
	IVector v1 = m1.nTranspose( false ).toVector( true );

	Assert.assertEquals(
		"Scalar product of vecctor should be same as square of norm",
		v1.scalarProduct( v2 ), Math.pow( v1.norm(), 2 ), 0.001 );
    }

    @Test
    public void testHashAndEquals() {

	IMatrix m1 = Matrix.parseSimple( "2.01 2 2.03 3 2.04 45" );
	IMatrix m2 = m1.copy();

	Assert.assertEquals( "Hashes should be equal", m1.hashCode(),
		m2.hashCode() );
	Assert.assertTrue( "Matices should be equal", m2.equals( m1 ) );
	Assert.assertTrue( "Matices should be equal", m1.equals( m2 ) );
	Assert.assertTrue( "Matices should be equal", m1.equals( m1 ) );
    }
}
