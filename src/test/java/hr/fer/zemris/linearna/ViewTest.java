package hr.fer.zemris.linearna;

import org.junit.Assert;
import org.junit.Test;


public class ViewTest {

    @Test
    public void testMatrixVectorView() {

	IVector v = LinAlgDefaults.defaultIVector( 3 );
	IMatrix m1 = new MatrixVectorView( v, true );

	m1.set( 0, 1, 1 );

	m1 = new MatrixVectorView( v, false );

	m1.set( 2, 0, 2 );

	Assert.assertTrue( "Vector shold became [0 1 2]", ((AbstractVector) v)
		.equals( Vector.parseSimple( "0 1 2" ), 0.00001 ) );
    }

    @Test
    public void testVectorMatrixViewRowMatrix() {
	
	new LinAlgDefaults();

	IMatrix m = LinAlgDefaults.defaultMatrix( 1, 3 );
	IVector v = new VectorMatrixView( m );
	v.set( 1, 1 );
	v.set( 2, 2 );

	Assert.assertTrue( "Matrix should became [[0 1 2]]", Matrix
		.parseSimple( "0 1 2" ).equals( m, 0.001 ) );
    }

    @Test
    public void testCopyOfMatrixVectorView() {

	IVector v = LinAlgDefaults.defaultIVector( 3 );
	IMatrix m1 = new MatrixVectorView( v, true );

	IMatrix m2 = m1.copy();
	m2.set( 0, 1, 1 );

	Assert.assertEquals( "Matrix m1 should not be changed", m1.get( 0, 1 ),
		0.0, 0.0001 );
    }

    @Test
    public void testnewInstanceOfMatrixVectorView() {

	IVector v = LinAlgDefaults.defaultIVector( 3 );
	IMatrix m1 = new MatrixVectorView( v, true );

	IMatrix m2 = m1.newInstance( 1, 3 );

	Assert.assertTrue( "Matrix m2 should MatrixViewVector",
		m2 instanceof MatrixVectorView );
    }

    @Test
    public void testCopyIfVectorMatrixView() {

	IMatrix m = LinAlgDefaults.defaultMatrix( 5, 1 );
	IVector v1 = new VectorMatrixView( m );
	IVector v2 = v1.copy();

	v2.set( 2, 2 );

	Assert.assertEquals( "Vector v1 should not be cahnged", v1.get( 2 ),
		0.0, 0.001 );
    }

    @Test
    public void testNewInstanceOfVectorMatrixView() {

	IMatrix m = LinAlgDefaults.defaultMatrix( 1, 99 );
	IVector v = new VectorMatrixView( m );
	IVector v2 = v.newInstance( 34 );
	Assert.assertTrue( "Vector v2 should be instace of VectorMatrixView",
		v2 instanceof VectorMatrixView );
    }

    @Test
    public void testCopyOfTransposeView() {

	IMatrix m = LinAlgDefaults.defaultMatrix( 5, 5 );
	IMatrix m2 = new MatrixTransposeView( m );
	IMatrix m3 = m2.copy();

	m2.set( 2, 2, 2 );

	Assert.assertEquals( "Matrix m3 should not be cahnged", m3.get( 2, 2 ),
		0.0, 0.001 );
    }

    @Test
    public void testNewInstanceOfTransposeView() {

	IMatrix m = LinAlgDefaults.defaultMatrix( 22, 99 );
	IMatrix m2 = new MatrixTransposeView( m );
	IMatrix m3 = m2.newInstance( 4, 4 );

	Assert.assertTrue(
		"Matrix m3 should be instace of MatrixTransposeView",
		m3 instanceof MatrixTransposeView );
    }

    @Test
    public void testCopyOfSubView() {

	IMatrix m = LinAlgDefaults.defaultMatrix( 5, 5 );
	IMatrix m2 = new MatrixSubMatrixView( m, 4, 4 );
	IMatrix m3 = m2.copy();

	m2.set( 2, 2, 2 );

	Assert.assertEquals( "Matrix m3 should not be cahnged", m3.get( 2, 2 ),
		0.0, 0.001 );
    }

    @Test
    public void testNewInstanceOfSubView() {

	IMatrix m = LinAlgDefaults.defaultMatrix( 22, 99 );
	IMatrix m2 = new MatrixSubMatrixView( m, 11, 13 );
	IMatrix m3 = m2.newInstance( 3, 4 );

	Assert.assertTrue(
		"Matrix m3 should be instace of MatrixSubMatrixView",
		m3 instanceof MatrixSubMatrixView );
    }
}
