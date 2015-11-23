package hr.fer.zemris.linearna;

/**
 * View on another matrix that behave same as transposed viewed matrix. Any
 * change on viewed matrix take effect on viewed matrix.
 *
 * @author Nikola Sekulić
 *
 */
public class MatrixTransposeView extends AbstractMatrix {

    /**
     * Matrix that is viewed.
     */
    private IMatrix matrix;

    /**
     * Creates new view
     * 
     * @param matrix
     *            viewed matrix
     */
    public MatrixTransposeView(IMatrix matrix) {

	this.matrix = matrix;
    }

    /**
     * Number of rows getter.
     * 
     * @return number of rows
     */
    @Override
    public int getRowsCount() {

	return matrix.getColsCount();
    }

    /**
     * Number of columns getter.
     * 
     * @return number of columns
     */
    @Override
    public int getColsCount() {

	return matrix.getRowsCount();
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

	return matrix.get( col, row );
    }

    /**
     * Element setter. Change of element on this matrix changes element on
     * viewed matrix.
     * 
     * @param row
     *            row of matrix
     * @param col
     *            column of matrix
     * @return this matrix
     */
    @Override
    public IMatrix set( int row, int col, double value ) {

	matrix.set( col, row, value );
	return this;
    }

    /**
     * Copies this matrix. Changes on copy does not take affect on this matrix
     * or viewed matrix.
     * 
     * @return new matrix with same elements.
     */
    @Override
    public IMatrix copy() {

	int rows = getRowsCount();
	int cols = getColsCount();

	IMatrix matrix = new Matrix( rows, cols );

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
     *            number of rows.
     * @param cols
     *            number of columns.
     */
    @Override
    public IMatrix newInstance( int rows, int cols ) {

	IMatrix matrix = new Matrix( rows, cols );
	return new MatrixTransposeView( matrix );
    }

}
