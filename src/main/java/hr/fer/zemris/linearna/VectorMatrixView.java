package hr.fer.zemris.linearna;

/**
 * View in one row or one column matrix that behaves same as vector. All changes
 * on this view take effect on viewed matrix.
 *
 * @author Nikola Sekulić
 *
 */
public class VectorMatrixView extends AbstractVector {

    /**
     * Dimension of vector.
     */
    private int dimension;

    /**
     * Flag, true if matrix has one row
     */
    private boolean rowMatrix;

    /**
     * Viewed matrix
     */
    private IMatrix matrix;

    /**
     * Creates new view on matrix.
     * 
     * @param matrix
     *            matrix to be viewed.
     */
    public VectorMatrixView(IMatrix matrix) {

	this.matrix = matrix;

	if ( matrix.getRowsCount() == 1 ) {
	    rowMatrix = true;
	    dimension = matrix.getColsCount();
	} else if ( matrix.getColsCount() == 1 ) {
	    rowMatrix = false;
	    dimension = matrix.getRowsCount();
	} else {
	    throw new IncompatibleOperandException(
		    "Matrix cannot represent vector!" );
	}
    }

    /**
     * Element getter.
     * 
     * @param index
     *            index of element.
     */
    @Override
    public double get( int index ) {

	return rowMatrix ? matrix.get( 0, index ) : matrix.get( index, 0 );
    }

    /**
     * Element setter. Change is propagated to viewed matrix.
     * 
     * @param index
     *            index of element
     * @param value
     *            value to be set.
     * 
     * @throws UnmodifiableObjectException
     *             if vector is unmodifiable
     */
    @Override
    public IVector set( int index, double value ) {

	matrix = rowMatrix ? matrix.set( 0, index, value ) : matrix.set( index,
		0, value );
	return this;
    }

    /**
     * Dimension getter.
     * 
     * @return dimension of vector.
     */
    @Override
    public int getDimension() {

	return dimension;
    }

    /**
     * Copies this vector. Changes on copy does not take affect on this vector
     * or viewed matrix.
     * 
     * @return new vector with same elements.
     */
    @Override
    public IVector copy() {

	final double[] elements = new double[dimension];
	for ( int i = 0; i < dimension; i++ ) {
	    elements[i] = get( i );
	}

	return new Vector( elements );
    }

    /**
     * Returns new instance of this vector. All elements of new instance are 0.
     * 
     * @param dimension
     *            dimension of new vector.
     */
    @Override
    public IVector newInstance( int dimension ) {

	final IMatrix m = new Matrix( dimension, 1 );
	return new VectorMatrixView( m );
    }

}
