package hr.fer.zemris.linearna;

import java.util.Arrays;

/**
 * Class vector implements vector. Element of vector are stored in array.
 *
 * @author Nikola Sekulić
 *
 */
public class Vector extends AbstractVector {

    /**
     * Elements of vector
     */
    private double[] elements;

    /**
     * Dimension of vector
     */
    private final int dimension;

    /**
     * Read only flag
     */
    private final boolean readOnly;

    /**
     * Creates new vector from specified elements.
     * 
     * @param elements
     *            element of vector.
     */
    public Vector(double ... elements) {

	this.elements = elements.clone();
	dimension = elements.length;
	readOnly = false;
    }

    /**
     * Creates new vector from specified elements.
     * 
     * @param readOnly
     *            true if vector need to be unmodifiable.
     * @param shallowCopy
     *            true if changes on this vector sholud take effect on provided
     *            elements
     * @param elements
     *            elements of vector.
     */
    public Vector(boolean readOnly, boolean shallowCopy, double ... elements) {

	this.readOnly = readOnly;

	if ( shallowCopy ) {
	    this.elements = elements;
	} else {
	    this.elements = elements.clone();
	}

	dimension = elements.length;
    }

    /**
     * Element getter.
     * 
     * @param index
     *            index of element.
     */
    @Override
    public double get( int index ) {

	return elements[index];
    }

    /**
     * Element setter.
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

	if ( readOnly ) {
	    throw new UnmodifiableObjectException( "Vector is unmodifiable" );
	}

	elements[index] = value;
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
     * Copies this vector. Changes on copy does not take affect on this vector.
     * 
     * @return new vector with same elements.
     */
    @Override
    public IVector copy() {

	return new Vector( false, false, elements );
    }

    /**
     * Returns new instance of this vector. All elements of new instance are 0.
     * 
     * @param dimension
     *            dimension of new vector.
     */
    @Override
    public IVector newInstance( int dimension ) {

	final double[] array = new double[dimension];
	return new Vector( array );
    }

    /**
     * Creates vector from string representation. Elements in string are
     * separated with one or more spaces.
     * 
     * @param vector
     *            string representation of vector.
     * @return parsed vector
     */
    public static Vector parseSimple( String vector ) {

	final String[] values = vector.trim().split( "\\s+" );

	final double[] elements = new double[values.length];

	for ( int i = 0; i < elements.length; i++ ) {
	    elements[i] = Double.parseDouble( values[i] );
	}

	return new Vector( elements );
    }

    /**
     * Returns hash code of vector. Hash codes of vectors with same values of
     * elements are equal.
     * 
     * @return hash code of vector.
     */
    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = (prime * result) + Arrays.hashCode( elements );
	return result;
    }

    /**
     * Checks if vectors are equal. Vectors are considered equal if they have
     * same values of elements.
     * 
     * @param obj
     *            vector to be compared with
     * 
     * @return true if and only is vectors have same size, and values of
     *         elements are same.
     */
    @Override
    public boolean equals( Object obj ) {

	if ( this == obj ) {
	    return true;
	}
	if ( obj == null ) {
	    return false;
	}
	if ( !(obj instanceof Vector) ) {
	    return false;
	}
	final Vector other = (Vector) obj;
	if ( !Arrays.equals( elements, other.elements ) ) {
	    return false;
	}

	return true;
    }

}
