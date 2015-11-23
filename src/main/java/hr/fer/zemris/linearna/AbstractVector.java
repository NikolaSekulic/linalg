package hr.fer.zemris.linearna;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Implementation of n-dimansional vectors.
 *
 * @author Nikola Sekulić
 *
 */
public abstract class AbstractVector implements IVector {

    @Override
    public abstract double get( int index );

    @Override
    public abstract IVector set( int index, double value );

    @Override
    public abstract int getDimension();

    @Override
    public abstract IVector copy();

    /**
     * Creates new instance of vector with first n elements of this vector. If n
     * is greater than dimension of this vector, non-existing elements are
     * replaced with 0.
     * 
     * @param n
     *            number of elements in this vector that are copied to new
     *            vector.
     * @return new vector
     */
    @Override
    public IVector copyPart( int n ) {

	final IVector newVector = newInstance( n );

	for ( int i = 0, dimension = getDimension(); (i < dimension) && (i < n); i++ ) {
	    newVector.set( i, get( i ) );
	}

	return newVector;
    }

    @Override
    public abstract IVector newInstance( int dimension );

    /**
     * Adds provided vector to this vector.
     * 
     * @param other
     *            vector to be added.
     * @return this vector
     * @throws IncompatibleOperandException
     *             if dimensions of vectors are different
     */
    @Override
    public IVector add( IVector other ) throws IncompatibleOperandException {

	if ( getDimension() != other.getDimension() ) {
	    throw new IncompatibleOperandException(
		    "Cannot add vector with different dimnesion" );
	}

	for ( int i = 0, dimension = getDimension(); i < dimension; i++ ) {
	    set( i, get( i ) + other.get( i ) );
	}

	return this;
    }

    /**
     * Adds provided vector to this vector. Does not change this vector.
     * 
     * @param other
     *            vector to be added.
     * @return added vectors
     * @throws IncompatibleOperandException
     *             if dimensions of vectors are different
     */
    @Override
    public IVector nAdd( IVector other ) throws IncompatibleOperandException {

	final IVector vector = copy();
	return vector.add( other );
    }

    /**
     * Subtract provided vector from this vector.
     * 
     * @param other
     *            vector to be subtracted.
     * @return this vector
     * @throws IncompatibleOperandException
     *             if dimensions of vectors are different
     */
    @Override
    public IVector sub( IVector other ) throws IncompatibleOperandException {

	if ( getDimension() != other.getDimension() ) {
	    throw new IncompatibleOperandException(
		    "Cannot subtract vector with different dimnesion" );
	}

	for ( int i = 0, dimension = getDimension(); i < dimension; i++ ) {
	    set( i, get( i ) - other.get( i ) );
	}

	return this;
    }

    /**
     * Subtract provided vector from this vector. Doesn't change this vector.
     * 
     * @param other
     *            vector to be subtracted.
     * @return subtracted vectors
     * @throws IncompatibleOperandException
     *             if dimensions of vectors are different
     */
    @Override
    public IVector nSub( IVector other ) throws IncompatibleOperandException {

	final IVector vector = copy();
	return vector.sub( other );
    }

    /**
     * Multiplies all elements of this vector with provided scalar.
     * 
     * @param byValue
     *            scalar for multiplication
     * @return this vector
     */
    @Override
    public IVector scalarMultiply( double byValue ) {

	for ( int i = 0, dimension = getDimension(); i < dimension; i++ ) {
	    set( i, get( i ) * byValue );
	}

	return this;
    }

    /**
     * Multiplies all elements of this vector with provided scalar. Doesn't
     * change this vector.
     * 
     * @param byValue
     *            scalar for multiplication
     * @return new vector
     */

    @Override
    public IVector nScalarMultiply( double byValue ) {

	final IVector vector = copy();
	return vector.scalarMultiply( byValue );
    }

    /**
     * Calculates norm of this vector.
     * 
     * @return norm of vector.
     */

    @Override
    public double norm() {

	return Math.sqrt( scalarProduct( this ) );
    }

    /**
     * Changes norm of this vector to 1.
     * 
     * @return this vector
     * @throws IncompatibleOperandException
     *             if norm of vector is 0.
     */

    @Override
    public IVector normalize() {

	if ( AbstractVector.isNulVector( this, 1e-20 ) ) {
	    throw new IncompatibleOperandException(
		    "Cannot noramlize null vector" );
	}
	final double norm = norm();
	scalarMultiply( 1.0 / norm );
	return this;
    }

    /**
     * Changes norm of this vector to 1. Does not change this vector.
     * 
     * @return new vector with norm 1.
     * @throws IncompatibleOperandException
     *             if norm of vector is 0.
     */

    @Override
    public IVector nNormalize() {

	final IVector vector = copy();
	return vector.normalize();
    }

    /**
     * Calculates cosine of angle between this vector and provided vector.
     * 
     * @param other
     *            vector
     * @return cosine of angle between vectors
     * @throws IncompatibleOperandException
     *             if this or provided vector have norm 0.
     * 
     */

    @Override
    public double cosine( IVector other ) throws IncompatibleOperandException {

	if ( getDimension() != other.getDimension() ) {
	    throw new IncompatibleOperandException(
		    "Cannot calculate cosine product of vectors with different dimnesion" );
	}

	if ( AbstractVector.isNulVector( this, 1e-20 )
		|| AbstractVector.isNulVector( other, 1e-20 ) ) {
	    throw new IncompatibleOperandException(
		    "Cannot calculate cosine with nul vectors" );
	}

	final double scalarProduct = scalarProduct( other );
	final double norm1 = norm();
	final double norm2 = other.norm();

	return scalarProduct / (norm1 * norm2);
    }

    /**
     * Calculates scalar product if of this and provided vectors.
     * 
     * @param other
     *            vector
     * @return scalar product of vectors
     * @throws IncompatibleOperandException
     *             if dimensions of vectors are not same.
     */

    @Override
    public double scalarProduct( IVector other )
	    throws IncompatibleOperandException {

	if ( getDimension() != other.getDimension() ) {
	    throw new IncompatibleOperandException(
		    "Cannot calculate scalar product of vectors with different dimnesion" );
	}

	double product = 0;

	for ( int i = 0, dimension = getDimension(); i < dimension; i++ ) {
	    product += get( i ) * other.get( i );
	}

	return product;
    }

    /**
     * Calculates vector product of this and provided vector.
     * 
     * @param other
     *            vector
     * @return cross product of vectors
     * @throws IncompatibleOperandException
     *             if dimension of this or provided vector is not 3.
     */

    @Override
    public IVector nVectorProduct( IVector other )
	    throws IncompatibleOperandException {

	final int dimension = 3;

	if ( (getDimension() != dimension)
		|| (other.getDimension() != dimension) ) {
	    throw new IncompatibleOperandException(
		    "Cannot calculate cross product with vector that's dimension is not 3!" );
	}

	// this vector is [x1, y1, z1]
	// other vector is [x2, y2, z2]

	final double x1 = get( 0 );
	final double y1 = get( 1 );
	final double z1 = get( 2 );

	final double x2 = other.get( 0 );
	final double y2 = other.get( 1 );
	final double z2 = other.get( 2 );

	final double x3 = (y1 * z2) - (z1 * y2);
	final double y3 = -1 * ((x1 * z2) - (z1 * x2));
	final double z3 = (x1 * y2) - (y1 * x2);

	final IVector vector = newInstance( dimension );
	vector.set( 0, x3 );
	vector.set( 1, y3 );
	vector.set( 2, z3 );

	return vector;
    }

    /**
     * Calculates vector that is same as this vector in non-homogeneus space.
     * 
     * @return vector with non-homogeneus coordinates
     * @throws IncompatibleOperandException
     *             if dimension of this vector is 2.
     */

    @Override
    public IVector nFromHomogeneus() {

	final int dimension = getDimension() - 1;

	if ( dimension == 0 ) {
	    throw new IncompatibleOperandException(
		    "Vector with dimension 1 cannot be homogeneus vector!" );
	}

	final double homogeneusCordinate = get( dimension );
	if ( Math.abs( homogeneusCordinate ) > 1e30 ) {
	    throw new IncompatibleOperandException(
		    "Homogeneus cordinate cannot be 0" );
	}
	final IVector vector = copyPart( dimension );
	return vector.scalarMultiply( 1.0 / get( dimension ) );
    }

    /**
     * Creates matrix with one row. Row is same as this vector.
     * 
     * @param liveView
     *            true if changes on this vector are propagated to matrix.
     * 
     * @return matrix with one row.
     */

    @Override
    public IMatrix toRowMatrix( boolean liveView ) {

	if ( liveView ) {
	    return new MatrixVectorView( this, true );
	} else {
	    return new MatrixVectorView( this, true ).copy();
	}
    }

    /**
     * Creates matrix with one column. Column is same as this vector.
     * 
     * @param liveView
     *            true if changes on this vector are propagated to matrix.
     * 
     * @return matrix with one row.
     */

    @Override
    public IMatrix toColumnMatrix( boolean liveView ) {

	if ( liveView ) {
	    return new MatrixVectorView( this, false );
	} else {
	    return new MatrixVectorView( this, false ).copy();
	}
    }

    /**
     * Returns array with elements of vector.
     * 
     * @return array of elements
     */
    @Override
    public double[] toArray() {

	final int dimension = getDimension();

	final double[] array = new double[dimension];

	for ( int i = 0; i < dimension; i++ ) {
	    array[i] = get( i );
	}

	return array;
    }

    /**
     * Checks if vector is null-vector
     * 
     * @param v
     *            vector to be checked
     * @param delta
     *            allowed difference between zero and element
     * @return true if and only if vector is null-vector.
     */

    protected static boolean isNulVector( IVector v, double delta ) {

	for ( int i = 0, dimension = v.getDimension(); i < dimension; i++ ) {
	    if ( Math.abs( v.get( i ) ) > delta ) {
		return false;
	    }
	}

	return true;
    }

    public String toString( int precision ) {

	String pattern = "0.";
	for ( int i = 0; i < precision; i++ ) {
	    pattern += "0";
	}

	final DecimalFormatSymbols symbols = new DecimalFormatSymbols();
	symbols.setDecimalSeparator( '.' );
	final DecimalFormat df = new DecimalFormat( pattern, symbols );

	final StringBuilder sb = new StringBuilder();

	for ( int i = 0; i < getDimension(); i++ ) {
	    sb.append( df.format( get( i ) ) );
	    sb.append( ' ' );
	}

	sb.deleteCharAt( sb.length() - 1 );

	return sb.toString();
    }

    @Override
    public String toString() {

	return this.toString( 3 );
    }

    /**
     * Checks if this vector is same as provided vector.
     * 
     * @param other
     *            other vector
     * @param delta
     *            allowed difference between elements
     * @return true if and only if vectors have sam elements.
     */
    public boolean equals( IVector other, double delta ) {

	if ( getDimension() != other.getDimension() ) {
	    return false;
	}

	for ( int i = 0; i < getDimension(); i++ ) {
	    final double diff = Math.abs( get( i ) - other.get( i ) );
	    if ( diff > delta ) {
		return false;
	    }
	}

	return true;
    }
}
