package hr.fer.zemris.linearna;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Implementation of IMatrix interface. Implements basic's matrix operations.
 * 
 * @author Nikola Sekulić
 *
 */
public abstract class AbstractMatrix implements IMatrix {

    /** offset for double comparison **/
    private final double delta = 1e-30;

    @Override
    public abstract int getRowsCount();

    @Override
    public abstract int getColsCount();

    @Override
    public abstract double get( int row, int col );

    @Override
    public abstract IMatrix set( int row, int col, double value );

    @Override
    public abstract IMatrix copy();

    @Override
    public abstract IMatrix newInstance( int rows, int cols );

    /**
     * Creates transposed matrix.
     * 
     * @param liveView
     *            true if changes on transposed matrix should change this
     *            matrix.
     * @return transposed matrix
     * 
     * @throws IncompatibleOperandException
     *             if this matrices have not same size.
     */
    @Override
    public IMatrix nTranspose( boolean liveView ) {

	if ( liveView ) {
	    return new MatrixTransposeView( this );
	} else {

	    final int nRows = getColsCount();
	    final int nCols = getRowsCount();

	    final IMatrix matrix = newInstance( nRows, nCols );

	    for ( int row = 0; row < nRows; row++ ) {
		for ( int col = 0; col < nCols; col++ ) {
		    matrix.set( row, col, get( col, row ) );
		}
	    }

	    return matrix;
	}
    }

    /**
     * Adds provided matrix to this matrix.
     * 
     * @param other
     *            matrix to be added.
     * @return this matrix
     * 
     * @throws IncompatibleOperandException
     *             if this matrices have not same size.
     */
    @Override
    public IMatrix add( IMatrix other ) {

	if ( !AbstractMatrix.sameSize( this, other ) ) {
	    throw new IncompatibleOperandException(
		    "Cannot add matrix with different size!" );
	}

	for ( int row = 0, nRows = getRowsCount(); row < nRows; row++ ) {
	    for ( int col = 0, nCols = getColsCount(); col < nCols; col++ ) {
		set( row, col, get( row, col ) + other.get( row, col ) );
	    }
	}

	return this;

    }

    /**
     * Subtract provided matrix from this matrix.
     * 
     * @param other
     *            matrix to be subtracted.
     * @return this matrix
     * 
     * @throws IncompatibleOperandException
     *             if this matrices have not same size.
     */
    @Override
    public IMatrix nAdd( IMatrix other ) {

	final IMatrix matrix = copy();
	return matrix.add( other );
    }

    /**
     * Subtract provided matrix from this matrix. Does not change this matrix.
     * 
     * @param other
     *            matrix to be subtracted.
     * @return this matrix
     * 
     * @throws IncompatibleOperandException
     *             if this matrices have not same size.
     */
    @Override
    public IMatrix sub( IMatrix other ) {

	if ( !AbstractMatrix.sameSize( this, other ) ) {
	    throw new IncompatibleOperandException(
		    "Cannot subtract matrix with different size!" );
	}

	for ( int row = 0, nRows = getRowsCount(); row < nRows; row++ ) {
	    for ( int col = 0, nCols = getColsCount(); col < nCols; col++ ) {
		set( row, col, get( row, col ) - other.get( row, col ) );
	    }
	}

	return this;
    }

    /**
     * Subtract provided matrix from this matrix. Does not change this matrix.
     * 
     * @param other
     *            matrix to be subtracted.
     * @return subtracted matrix
     * 
     * @throws IncompatibleOperandException
     *             if this matrices have not same size.
     */
    @Override
    public IMatrix nSub( IMatrix other ) {

	final IMatrix matrix = copy();
	return matrix.sub( other );
    }

    /**
     * Multiplies this matrix with provided matrix. Does not change this matrix.
     * 
     * @param other
     *            matrix to be multiplied
     * @return multiplied matrix
     * @throws IncompatibleOperandException
     *             if this matrix hasn't number of columns as provided matrix
     *             rows
     */
    @Override
    public IMatrix nMultiply( IMatrix other ) {

	if ( getColsCount() != other.getRowsCount() ) {
	    throw new IncompatibleOperandException(
		    "First matrix must have same number of columns as second matrix rows!" );
	}

	final int nRows = getRowsCount();
	final int nCols = other.getColsCount();

	final IMatrix m = newInstance( nRows, nCols );

	final int nColsFirstM = getColsCount();

	for ( int row = 0; row < nRows; row++ ) {
	    for ( int col = 0; col < nCols; col++ ) {

		double value = 0;

		for ( int i = 0; i < nColsFirstM; i++ ) {
		    value += get( row, i ) * other.get( i, col );
		}

		m.set( row, col, value );
	    }
	}

	return m;
    }

    /**
     * Calculates determinant of matrix. Calculation is implemented with
     * diagonalization of matrix. Complexity of method is O(n^3).
     * 
     * @return determinant of matrix
     * 
     * @throws IncompatibleOperandException
     *             if matrix is not square matrix.
     */
    @Override
    public double determinant() throws IncompatibleOperandException {

	if ( !isSqareMatrix() ) {
	    throw new IncompatibleOperandException( "Matrix has to be square" );
	}

	if ( getColsCount() == 1 ) {
	    return get( 0, 0 );
	}

	final IMatrix m = copy();

	double determinant = 1;

	for ( int row = 0, nRows = getRowsCount(); row < (nRows - 1); row++ ) {

	    if ( Math.abs( m.get( row, row ) ) <= delta ) {
		boolean isZero = true;

		for ( int i = row + 1; i < nRows; i++ ) {
		    if ( Math.abs( m.get( i, row ) ) > delta ) {
			determinant *= -1;
			isZero = false;
			for ( int col = 0, nCols = m.getColsCount(); col < nCols; col++ ) {
			    final double temp = m.get( row, col );
			    m.set( row, col, m.get( i, col ) );
			    m.set( i, col, temp );
			}
			break;
		    }
		}

		if ( isZero ) {
		    return 0.0;
		}
	    }

	    for ( int i = row + 1; i < nRows; i++ ) {

		final double C = m.get( i, row ) / m.get( row, row );

		for ( int col = 0, nCols = getColsCount(); col < nCols; col++ ) {
		    m.set( i, col, m.get( i, col ) - (C * m.get( row, col )) );
		}
	    }
	}

	for ( int i = 0, dimension = getColsCount(); i < dimension; i++ ) {
	    determinant *= m.get( i, i );
	}

	return determinant;
    }

    /**
     * Creates matrix that is same as this matrix without provided row and
     * column.
     * 
     * @param liveView
     *            true changes on this matrix should change new matrix
     * 
     * @return submatrix of this matrix
     */
    @Override
    public IMatrix subMatrix( int row, int col, boolean liveView ) {

	if ( liveView ) {
	    return new MatrixSubMatrixView( this, row, col );
	} else {
	    return new MatrixSubMatrixView( this, row, col ).copy();
	}
    }

    /**
     * Inverts this matrix.
     * 
     * @return inverted matrix.
     * @throws IncompatibleOperandException
     *             if matrix is not square matrix, or if matrix is singular.
     */
    @Override
    public IMatrix nInvert() {

	if ( !isSqareMatrix() ) {
	    throw new IncompatibleOperandException(
		    "Cannot inverse matrix that is not square matrix" );
	}

	final double det = determinant();

	if ( Math.abs( det ) <= delta ) {
	    throw new IncompatibleOperandException(
		    "Cannot inverse singular matrix" );
	}

	final int cols = getColsCount();
	final int rows = getRowsCount();

	if ( rows == 1 ) {
	    final double[][] newElements = new double[1][1];
	    newElements[0][0] = 1.0 / get( 0, 0 );
	    return new Matrix( 1, 1, newElements, false );
	}

	final IMatrix inverse = new Matrix( rows, cols );

	for ( int row = 0; row < rows; row++ ) {
	    for ( int col = 0; col < cols; col++ ) {
		final IMatrix subMatrix = subMatrix( row, col, false );
		double d = subMatrix.determinant();
		if ( ((row + col) % 2) == 1 ) {
		    d *= -1.0;
		}

		inverse.set( row, col, d );
	    }
	}

	return inverse.scalarMultiply( 1.0 / det ).nTranspose( false );
    }

    /**
     * Returns two dimensional array with elements of matrix.
     * 
     * @return elements of matrix
     */
    @Override
    public double[][] toArray() {

	final int rows = getRowsCount();
	final int cols = getColsCount();

	final double[][] array = new double[rows][cols];

	for ( int row = 0; row < rows; row++ ) {
	    for ( int col = 0; col < cols; col++ ) {
		array[row][col] = get( row, col );
	    }
	}

	return array;
    }

    /**
     * Returns vector same as matrix (if matrix has one row or one column)
     * 
     * @return vector
     */
    @Override
    public IVector toVector( boolean liveView ) {

	if ( liveView ) {
	    return new VectorMatrixView( this );
	} else {
	    return new VectorMatrixView( this ).copy();
	}
    }

    /**
     * Multiplies this matrix with scalar.
     * 
     * @param value
     *            scalar with this matrix is multiplied
     * @return multiplied matrix.
     */
    @Override
    public IMatrix nScalarMultiply( double value ) {

	return copy().scalarMultiply( value );
    }

    /**
     * Multiplies this matrix with scalar. Does not changes this matrix.
     * 
     * @param value
     *            scalar with this matrix is multiplied
     * @return this matrix
     */
    @Override
    public IMatrix scalarMultiply( double value ) {

	for ( int row = 0, rows = getRowsCount(); row < rows; row++ ) {
	    for ( int col = 0, cols = getColsCount(); col < cols; col++ ) {
		set( row, col, value * get( row, col ) );
	    }
	}

	return this;
    }

    /**
     * Change this matrix to unit matrix.
     * 
     * @return this matrix
     * @throws IncompatibleOperandException
     *             if matrix is not square matrix.
     */
    @Override
    public IMatrix makeIdentity() {

	if ( !isSqareMatrix() ) {
	    throw new IncompatibleOperandException(
		    "Matrix is not square matrix" );
	}
	for ( int row = 0, rows = getRowsCount(); row < rows; row++ ) {
	    for ( int col = 0, cols = getColsCount(); col < cols; col++ ) {
		set( row, col, row == col ? 1.0 : 0.0 );
	    }
	}

	return this;
    }

    /**
     * Checks if two matrices have same size
     * 
     * @param m1
     *            first matrix
     * @param m2
     *            second matrix
     * @return true if m1 and m2 have same size
     */
    protected static boolean sameSize( IMatrix m1, IMatrix m2 ) {

	if ( (m1.getRowsCount() == m2.getRowsCount())
		&& (m1.getColsCount() == m2.getColsCount()) ) {
	    return true;
	}

	return false;
    }

    /**
     * Checks if this matrix is square matrix
     * 
     * @return true if and only if this matrix is square matrix.
     */
    protected boolean isSqareMatrix() {

	if ( getColsCount() == getRowsCount() ) {
	    return true;
	}

	return false;
    }

    /**
     * Checks if all elements of this matrix are same as elements of provided
     * matrix.
     * 
     * @param other
     *            provided matrix
     * @param delta
     *            allowed difference between elements
     * @return true is and only if matrices have same size and same elements.
     */
    public boolean equals( IMatrix other, double delta ) {

	if ( (getColsCount() != other.getColsCount())
		|| (getRowsCount() != other.getRowsCount()) ) {
	    return false;
	}

	for ( int row = 0, rows = getRowsCount(); row < rows; row++ ) {
	    for ( int col = 0, cols = getColsCount(); col < cols; col++ ) {
		if ( Math.abs( get( row, col ) - other.get( row, col ) ) > delta ) {
		    return false;
		}
	    }
	}

	return true;
    }

    /**
     * Creates string representation of matrix.
     * 
     * @param precision
     *            number of digits after decimal point.
     * @return string representation of matrix.
     */
    public String toString( int precision ) {

	String pattern = "0.";
	for ( int i = 0; i < precision; i++ ) {
	    pattern += "0";
	}

	final DecimalFormatSymbols symbols = new DecimalFormatSymbols();
	symbols.setDecimalSeparator( '.' );
	final DecimalFormat df = new DecimalFormat( pattern, symbols );

	final StringBuilder sb = new StringBuilder();

	for ( int row = 0, rows = getRowsCount(); row < rows; row++ ) {

	    sb.append( "[ " );

	    for ( int col = 0, cols = getColsCount(); col < cols; col++ ) {
		sb.append( df.format( get( row, col ) ) );
		sb.append( ' ' );
	    }

	    sb.append( "]\r\n" );
	}

	sb.deleteCharAt( sb.length() - 1 );
	sb.deleteCharAt( sb.length() - 1 );

	return sb.toString();
    }

    @Override
    public String toString() {

	return this.toString( 3 );
    }
}
