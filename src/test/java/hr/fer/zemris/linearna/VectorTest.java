package hr.fer.zemris.linearna;

import org.junit.Assert;
import org.junit.Test;

public class VectorTest {

    @Test
    public void testCosine() {

	IVector v1 = new Vector( 3.14, 3.14, 3.14 );
	IVector v2 = new Vector( true, true, 3.14, 3.14, 0 );

	double cosine = v2.cosine( v1 );

	Assert.assertEquals( "Cosine should be 0.82", 0.8165, cosine, 1e-4 );

    }

    @Test
    public void testNormalize() {

	IVector v1 = Vector.parseSimple( " 3.14   3.85e-1    -17.  	" );
	IVector v2 = v1.nNormalize();
	double norm1 = v1.norm();
	double norm2 = v2.norm();

	Assert.assertEquals( "Norm of v1 should be 17.291843", 17.291843,
		norm1, 1e-6 );
	Assert.assertEquals( "Norm of v2 should be 1.00", 1.0, norm2, 1e-6 );
    }

    @Test
    public void testVectorProductAndFromHomogeneus() {

	IVector v1 = Vector.parseSimple( "1 0 0" );
	IVector v2 = new Vector( 0, 3.14, 0, 3.14 );
	v2 = v2.nFromHomogeneus();

	IVector v3 = v1.nVectorProduct( v2 );

	Assert.assertEquals( "First element sould be 0.0", 0.0, v3.get( 0 ),
		1e-6 );
	Assert.assertEquals( "Second element sould be 0.0", 0.0, v3.get( 1 ),
		1e-6 );
	Assert.assertEquals( "Third element sould be 1.0", 1.0, v3.get( 2 ),
		1e-6 );
    }

    @Test
    public void subTest() {

	IVector v1 = new Vector( 3, 3, 3 );
	IVector v2 = new Vector( 2, 2, 2 );
	v1.sub( v2 );

	Assert.assertEquals( "First element should be 1", 1.0, v1.get( 0 ),
		1e-6 );
	Assert.assertEquals( "Second element should be 1", 1.0, v1.get( 1 ),
		1e-6 );
	Assert.assertEquals( "third element should be 1", 1.0, v1.get( 2 ),
		1e-6 );
    }

    @Test
    public void nSubTest() {

	IVector v1 = new Vector( 3, 3, 3 );
	IVector v2 = new Vector( 2, 2, 2 );
	IVector v3 = v1.nSub( v2 );

	Assert.assertEquals( "First element should be 1", 1.0, v3.get( 0 ),
		1e-6 );
	Assert.assertEquals( "Second element should be 1", 1.0, v3.get( 1 ),
		1e-6 );
	Assert.assertEquals( "third element should be 1", 1.0, v3.get( 2 ),
		1e-6 );

	Assert.assertEquals( "First element of v1 sould be unchanged", 3.0,
		v1.get( 0 ), 1e-6 );
	Assert.assertEquals( "Second element of v1 sould be unchanged", 3.0,
		v1.get( 1 ), 1e-6 );
	Assert.assertEquals( "Third element of v1 sould be unchanged", 3.0,
		v1.get( 2 ), 1e-6 );
    }

    @Test
    public void nAddTest() {

	IVector v1 = new Vector( 3, 3, 3 );
	IVector v2 = new Vector( 2, 2, 2 );
	IVector v3 = v1.nAdd( v2 );

	Assert.assertEquals( "First element should be 5", 5.0, v3.get( 0 ),
		1e-6 );
	Assert.assertEquals( "Second element should be 5", 5.0, v3.get( 1 ),
		1e-6 );
	Assert.assertEquals( "third element should be 5", 5.0, v3.get( 2 ),
		1e-6 );
    }

    @Test
    public void testToArray() {

	IVector v1 = Vector.parseSimple( "3 6 9 3" ).nFromHomogeneus();
	double[] array = v1.toArray();
	double[] expectedArray = {
		1, 2, 3
	};

	Assert.assertArrayEquals( "Array should be {1, 2, 3}", expectedArray,
		array, 1e-6 );
    }

    @Test
    public void nScalarMultiplyTest() {

	IVector v1 = new Vector( 2, 3, 4 );
	IVector v2 = v1.nScalarMultiply( 10.00 );

	Assert.assertEquals( "First element should be 20", 20.0, v2.get( 0 ),
		1e-6 );
	Assert.assertEquals( "Second element should be 30", 30.0, v2.get( 1 ),
		1e-6 );
	Assert.assertEquals( "third element should be 40", 40.0, v2.get( 2 ),
		1e-6 );
    }

    @Test
    public void testparseSimpleAndToString() {

	Vector v1 = new Vector( 3.14, 2.78, 1.42 );
	Vector v2 = Vector.parseSimple( v1.toString() );

	Assert.assertTrue( "v1 and v2 shild be equals", v1.equals( v2, 1e-6 ) );
    }

    @Test
    public void testToRowMatrixView() {

	IVector v = Vector.parseSimple( "0 0 0" );
	IMatrix m1 = v.toRowMatrix( true );
	IMatrix m2 = v.toRowMatrix( false );

	v.set( 0, 1 );

	Assert.assertTrue( "Matrix m1 should be [1 0 0]",
		Matrix.parseSimple( "1 0 0" ).equals( m1, 0.001 ) );
	Assert.assertTrue( "Matrix m2 should be [0 0 0]",
		Matrix.parseSimple( "0 0 0" ).equals( m2, 0.001 ) );

    }

    @Test
    public void testToColumnMatrixView() {

	IVector v = Vector.parseSimple( "0 0 0" );
	IMatrix m1 = v.toColumnMatrix( true );
	IMatrix m2 = v.toColumnMatrix( false );

	v.set( 0, 1 );

	Assert.assertTrue( "Matrix m1 should be [1 | 0 | 0]", Matrix
		.parseSimple( "1|0|0" ).equals( m1, 0.001 ) );
	Assert.assertTrue( "Matrix m2 should be [0 | 0 | 0]", Matrix
		.parseSimple( "0|0|0" ).equals( m2, 0.001 ) );

    }

    @Test
    public void testHashAndEquals() {

	IVector v1 = Vector.parseSimple( "2.01 2 2.03 3 2.04 45" );
	IVector v2 = v1.copy();

	Assert.assertEquals( "Hashes should be equal", v1.hashCode(),
		v2.hashCode() );
	Assert.assertTrue( "Matices should be equal", v2.equals( v1 ) );
	Assert.assertTrue( "Matices should be equal", v1.equals( v2 ) );
	Assert.assertTrue( "Matices should be equal", v1.equals( v1 ) );
    }

}
