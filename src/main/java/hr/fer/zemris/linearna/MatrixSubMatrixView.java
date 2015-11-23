package hr.fer.zemris.linearna;

/**
 * View on another matrix that behave same as matrix. Any change on viewed
 * elements from viewed matrix take effect on viewed matrix.
 *
 * @author Nikola Sekulić
 *
 */
public class MatrixSubMatrixView extends AbstractMatrix {

    /**
     * Rows that are viewed on another matrix
     */
    private int[] rows;

    /**
     * Columns that are viewed on another matrix.
     */
    private int[] cols;

    /**
     * Matrix that is viewed.
     */
    private IMatrix matrix;

    /**
     * Creates new view.
     * 
     * @param matrix
     *            matrix that is viewed
     * @param row
     *            row that is not viewed
     * @param col
     *            column taht is not viewed.
     */
    public MatrixSubMatrixView(IMatrix matrix, int row, int col) {

	this.matrix = matrix;

	rows = new int[matrix.getRowsCount() - 1];
	cols = new int[matrix.getColsCount() - 1];

	for ( int i = 0; i < rows.length; i++ ) {
	    if ( i < row ) {
		rows[i] = i;
	    } else {
		rows[i] = i + 1;
	    }
	}

	for ( int i = 0; i < cols.length; i++ ) {
	    if ( i < col ) {
		cols[i] = i;
	    } else {
		cols[i] = i + 1;
	    }
	}
    }

    /**
     * Creates new view.
     * 
     * @param matrix
     *            matrix that is viewed
     * @param rows
     *            rows form viewed matrix thats are viewed
     * @param cols
     *            ccolumns feom viewed matrix that are viewed.
     */
    private MatrixSubMatrixView(IMatrix matrix, int[] rows, int[] cols) {

	this.matrix = matrix;
	this.rows = rows.clone();
	this.cols = cols.clone();
    }

    /**
     * Number of rows getter.
     * 
     * @return number of rows
     */
    @Override
    public int getRowsCount() {

	return rows.length;
    }

    /**
     * Number of columns getter.
     * 
     * @return number of columns
     */
    @Override
    public int getColsCount() {

	return cols.length;
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

	return matrix.get( rows[row], cols[col] );
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

	matrix.set( rows[row], cols[col], value );
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

	IMatrix m = new Matrix( rows.length, cols.length );

	for ( int row = 0; row < rows.length; row++ ) {
	    for ( int col = 0; col < cols.length; col++ ) {
		m.set( row, col, get( row, col ) );
	    }
	}

	return m;
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

	IMatrix m = new Matrix( rows, cols );
	int[] nCols = new int[cols];
	int[] nRows = new int[rows];

	for ( int i = 0; i < nCols.length; i++ ) {
	    nCols[i] = i;
	}

	for ( int i = 0; i < nRows.length; i++ ) {
	    nRows[i] = i;
	}

	return new MatrixSubMatrixView( m, nRows, nCols );
    }

}
