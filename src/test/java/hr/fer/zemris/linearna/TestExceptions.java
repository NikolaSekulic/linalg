package hr.fer.zemris.linearna;

import org.junit.Test;

public class TestExceptions {

    @Test( expected = IncompatibleOperandException.class )
    public void testAbstactMatrixAdd() {

	IMatrix a = new Matrix( 2, 3 );
	IMatrix b = new Matrix( 2, 2 );

	a.add( b );
    }

    @Test( expected = IncompatibleOperandException.class )
    public void testAbstactMatrixSub() {

	IMatrix a = new Matrix( 2, 3 );
	IMatrix b = new Matrix( 2, 2 );

	a.sub( b );
    }

    @Test( expected = IncompatibleOperandException.class )
    public void nMultiplyTest() {

	IMatrix a = new Matrix( 2, 3 );
	IMatrix b = new Matrix( 2, 2 );
	a.nMultiply( b );
    }

    @Test( expected = IncompatibleOperandException.class )
    public void testDeterminant() {

	IMatrix a = new Matrix( 2, 3 );
	a.determinant();
    }

    @Test( expected = IncompatibleOperandException.class )
    public void testInverseSingulaMatrix() {

	IMatrix m = Matrix.parseSimple( "0 1 3 | 0 2 6 | 7 7 7" );
	m.nInvert();
    }

    @Test( expected = IncompatibleOperandException.class )
    public void testIncverseNonSquareMatrix() {

	IMatrix m = Matrix.parseSimple( "1 2 3 | 23 24 212" );
	m.nInvert();
    }

    @Test( expected = IncompatibleOperandException.class )
    public void testAddVectors() {

	IVector v1 = new Vector( 1, 2, 3 );
	IVector v2 = new Vector( 2, 2 );
	v1.add( v2 );
    }

    @Test( expected = IncompatibleOperandException.class )
    public void testCosineVectorsDifferentDimnesion() {

	IVector v1 = new Vector( 1, 2, 3 );
	IVector v2 = new Vector( 2, 2 );
	v1.cosine( v2 );
    }

    @Test( expected = IncompatibleOperandException.class )
    public void testCosineVectorsNullVector() {

	IVector v1 = new Vector( 1, 2, 3 );
	IVector v2 = new Vector( 0, 0, 0 );
	v1.cosine( v2 );
    }

    @Test( expected = IllegalArgumentException.class )
    public void testMatrixConstructor() {

	double[][] elements = new double[4][5];

	new Matrix( 5, 5, elements, true );
    }

    @Test( expected = IllegalArgumentException.class )
    public void testMatrixConstructor2() {

	double[][] elements = new double[4][5];

	new Matrix( 4, 6, elements, true );
    }

    @Test( expected = IndexOutOfBoundsException.class )
    public void testMatrixSetIndexOutOfBound() {

	double[][] elements = new double[5][6];

	IMatrix m = new Matrix( 10, 11, elements, false );

	m.set( 10, 11, 0.1 );
    }

    @Test( expected = IndexOutOfBoundsException.class )
    public void testMatrixGetIndexOutOfBound() {

	double[][] elements = new double[5][6];

	IMatrix m = new Matrix( 10, 11, elements, false );

	m.get( 10, 11 );
    }

    @Test( expected = UnmodifiableObjectException.class )
    public void testChangeUnmodifableVector() {

	IVector v = new Vector( true, true, new double[] {
		1, 2, 3
	} );
	v.normalize();
    }
    
    
    @Test(expected = IncompatibleOperandException.class)
    public void testMatrixVectorViewNewInstance() {
	
	Vector v = new Vector( 1.22, 1.23, 11.22 );
	MatrixVectorView view = new MatrixVectorView( v, true );
	view.newInstance( 2, 2 );
	
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testMatrixVectorViewGetAsRowMatrix() {
	
	Vector v = new Vector( 1.22, 1.23, 11.22 );
	MatrixVectorView view = new MatrixVectorView( v, true );
	view.get( 2, 1 );
	
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testMatrixVectorViewGetNotAsRowMatrix() {
	
	Vector v = new Vector( 1.22, 1.23, 11.22 );
	MatrixVectorView view = new MatrixVectorView( v, false );
	view.get( 1, 2 );
	
    }
}
