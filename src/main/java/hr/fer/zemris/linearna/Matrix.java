package hr.fer.zemris.linearna;

import java.util.Arrays;

/**
 * Class Matrix is implementation of math matrices. Elements of matrix are
 * stored in array.
 *
 * @author Nikola Sekulić
 *
 */
public class Matrix extends AbstractMatrix {

    /**
     * Elements of matrix
     */
    protected double[][] elements;

    /**
     * Number of rows
     */
    protected int rows;

    /**
     * Number of columns
     */
    protected int cols;

    /**
     * Creates new matrix. All elements of new matrix are 0.
     * 
     * @param rows
     *            number of rows
     * @param cols
     *            number of columns
     */
    public Matrix(int rows, int cols) {

	this.rows = rows;
	this.cols = cols;
	elements = new double[rows][cols];
    }

    /**
     * Creates new matrix.
     * 
     * @param rows
     *            number of rows
     * @param cols
     *            number of columns
     * @param elements
     *            elements of matrix
     * @param shallowCopy
     *            true if changes on matrix change provided elements.
     * @throws IllegalArgumentException
     *             if shallowCopy is true and elements size are smaller then
     *             provided size of matrix
     */
    public Matrix(int rows, int cols, double[][] elements, boolean shallowCopy) {

	this.cols = cols;
	this.rows = rows;

	if ( shallowCopy ) {
	    if ( elements.length < rows ) {
		throw new IllegalArgumentException( "wrong size of elements" );
	    }

	    for ( int i = 0; i < rows; i++ ) {
		if ( elements[i].length > rows ) {
		    throw new IllegalArgumentException(
			    "wrong size of elements" );
		}
	    }

	    this.elements = elements;

	} else {

	    this.elements = new double[rows][cols];

	    for ( int row = 0; row < rows; row++ ) {
		for ( int col = 0; col < cols; col++ ) {

		    if ( row >= elements.length ) {
			this.elements[row][col] = 0.0;
		    } else if ( col >= elements[row].length ) {
			this.elements[row][col] = 0.0;
		    } else {
			this.elements[row][col] = elements[row][col];
		    }
		}
	    }
	}
    }

    /**
     * Number of rows getter.
     * 
     * @return number of rows
     */
    @Override
    public int getRowsCount() {

	return rows;
    }

    /**
     * Number of columns getter.
     * 
     * @return number of columns
     */
    @Override
    public int getColsCount() {

	return cols;
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

	if ( (row >= rows) || (col >= cols) || (row < 0) || (col < 0) ) {
	    throw new IndexOutOfBoundsException( row + " " + col );
	}

	return elements[row][col];
    }

    /**
     * Element setter.
     * 
     * @param row
     *            row of matrix
     * @param col
     *            column of matrix
     * @return this matrix
     */
    @Override
    public IMatrix set( int row, int col, double value ) {

	if ( (row >= rows) || (col >= cols) || (row < 0) || (col < 0) ) {
	    throw new IndexOutOfBoundsException();
	}

	elements[row][col] = value;

	return this;
    }

    /**
     * Copies this matrix. Changes on copy does not take affect on this matrix.
     * 
     * @return new matrix with same elements.
     */
    @Override
    public IMatrix copy() {

	return new Matrix( rows, cols, elements, false );
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

	return new Matrix( rows, cols );

    }

    /**
     * Creates matrix from string representation. Elements in string are
     * separated with one or more spaces. Rows are separated with '|'
     * 
     * @param matrix
     *            string representation of amtrix.
     * @return new matrix
     */
    public static Matrix parseSimple( String matrix ) {

	final String[] rows = matrix.split( "\\|" );

	final double[][] elements = new double[rows.length][0];

	int maxCol = 0;

	for ( int row = 0; row < rows.length; row++ ) {
	    final String[] colStr = rows[row].trim().split( "\\s+" );
	    final double[] col = new double[colStr.length];

	    for ( int j = 0; j < colStr.length; j++ ) {
		col[j] = Double.parseDouble( colStr[j] );
	    }

	    if ( col.length > maxCol ) {
		maxCol = col.length;
	    }

	    elements[row] = col;
	}

	return new Matrix( elements.length, maxCol, elements, false );
    }

    /**
     * Returns hash code of matrix. Hash codes of matrices with same values of
     * elements are equal.
     * 
     * @return hash code of matrix.
     */
    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = (prime * result) + cols;
	result = (prime * result) + Arrays.deepHashCode( elements );
	result = (prime * result) + rows;
	return result;
    }

    /**
     * Checks if matrices are equal. Matrices are considered equal if they have
     * same values of elements.
     * 
     * @return <code>true</code> if and only is matrices have same size, and
     *          values of elements are same.
     */
    @Override
    public boolean equals( Object obj ) {

	if ( this == obj ) {
	    return true;
	}
	if ( obj == null ) {
	    return false;
	}
	if ( !(obj instanceof Matrix) ) {
	    return false;
	}
	final Matrix other = (Matrix) obj;
	if ( cols != other.cols ) {
	    return false;
	}

	if ( rows != other.rows ) {
	    return false;
	}

	if ( !Arrays.deepEquals( elements, other.elements ) ) {
	    return false;
	}

	return true;
    }

}
