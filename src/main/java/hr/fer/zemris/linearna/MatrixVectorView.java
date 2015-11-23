package hr.fer.zemris.linearna;

/**
 * View on vector that behaves like matrix with one row or one column.
 *
 * @author Nikola Sekulić
 *
 */
public class MatrixVectorView extends AbstractMatrix {

    /**
     * Vector that is viewed.
     */
    private IVector vector;

    /**
     * True if matrix has one row, false if matrix has one column
     */
    private final boolean asRowMatrix;

    /**
     * Creates new view.
     * 
     * @param original
     *            vector that is viewed
     * @param asRowMatrix
     *            true if matrix has one row, false if matrix has one column
     */
    public MatrixVectorView(IVector original, boolean asRowMatrix) {

	vector = original;
	this.asRowMatrix = asRowMatrix;
    }

    /**
     * Number of rows getter.
     * 
     * @return number of rows
     */
    @Override
    public int getRowsCount() {

	return asRowMatrix ? 1 : vector.getDimension();
    }

    /**
     * Number of columns getter.
     * 
     * @return number of colums
     */
    @Override
    public int getColsCount() {

	return asRowMatrix ? vector.getDimension() : 1;
    }

    /**
     * Element getter.
     * 
     * @param row
     *            row of matrix
     * @param col
     *            column of matrix
     * @return element at specified row and column
     */
    @Override
    public double get( int row, int col ) {

	if ( asRowMatrix && (row > 1) ) {
	    throw new IndexOutOfBoundsException( row + " " + col );
	}

	if ( !asRowMatrix && (col > 1) ) {
	    throw new IndexOutOfBoundsException( row + " " + col );
	}
	return asRowMatrix ? vector.get( col ) : vector.get( row );
    }

    /**
     * Element setter. Change of element on this matrix changes element on
     * viewed vector.
     * 
     * @param row
     *            row of matrix
     * @param col
     *            column of matrix
     * @return this matrix
     */
    @Override
    public IMatrix set( int row, int col, double value ) {

	if ( (col < 0) || (col >= getColsCount()) || (row < 0)
		|| (row >= getRowsCount()) ) {
	    throw new IndexOutOfBoundsException( row + " " + col );
	}

	vector = asRowMatrix ? vector.set( col, value ) : vector.set( row,
		value );

	return this;
    }

    /**
     * Copies this matrix. Changes on copy does not take affect on this matrix
     * or viewed vector.
     * 
     * @return new matrix with same elements.
     */
    @Override
    public IMatrix copy() {

	final int rows = getRowsCount();
	final int cols = getColsCount();

	final IMatrix matrix = new Matrix( rows, cols );

	for ( int row = 0; row < rows; row++ ) {
	    for ( int col = 0; col < cols; col++ ) {
		matrix.set( row, col, get( row, col ) );
	    }
	}

	return matrix;
    }

    /**
     * Returns new instance of this matrix. All elements of new instance are 0.
     * 
     * @param rows
     *            of rows.
     * @param cols
     *            number of columns.
     */
    @Override
    public IMatrix newInstance( int rows, int cols ) {

	if ( !((rows == 1) || (cols == 1)) ) {
	    throw new IncompatibleOperandException(
		    "Cannot transform matrix to vector" );
	}
	final IVector v = new Vector( getRowsCount() == 1 ? getColsCount()
		: getRowsCount() );
	return new MatrixVectorView( v, asRowMatrix );
    }

}
