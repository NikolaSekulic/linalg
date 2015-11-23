package hr.fer.zemris.linearna;

/**
 * Service for creating default vectors and matrices
 *
 * @author Nikola Sekulić
 *
 */
public class LinAlgDefaults {

    /**
     * Creates matrix with specified number of rows an columns. All elements of
     * matrix are 0.
     * 
     * @param rows
     *            number of rows
     * @param cols
     *            number of columns.
     * @return new matrix
     */
    public static IMatrix defaultMatrix( int rows, int cols ) {

	return new Matrix( rows, cols );
    }

    /**
     * Creates vector with specified dimension. All elements of vector are 0.
     * 
     * @param dimension
     *            dimension of vector
     * @return new vector
     */
    public static IVector defaultIVector( int dimension ) {

	return new Vector( new double[dimension] );
    }
}
