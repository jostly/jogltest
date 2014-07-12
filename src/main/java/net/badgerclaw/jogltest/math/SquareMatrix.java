package net.badgerclaw.jogltest.math;

import java.util.Arrays;

public abstract class SquareMatrix<T extends SquareMatrix> {

    final float[] m = new float[size() * size()];

    /**
     * The size of the matrix is the number of rows (or columns) it has
     *
     * @return the size of this matrix
     */
    abstract int size();

    /**
     * Copies the elements of this matrix into another matrix of the same size
     *
     * @param dest the matrix to copy elements to
     * @return dest
     */
    public T copyTo(T dest) {
        System.arraycopy(m, 0, dest.m, 0, m.length);
        return dest;
    }

    /**
     * Set this matrix to the identity matrix
     *
     * @return this matrix object - the operation modifies the original object
     */
    public T identity() {
        float[] m = this.m;
        int n = m.length;
        int s = size() + 1;
        for (int i = 0; i < n; i++) {
            if (i % s == 0) {
                m[i] = 1f;
            } else {
                m[i] = 0f;
            }
        }
        return (T) this;
    }

    /**
     * Add the elements of that matrix to the elments of this matrix. The operation can be expressed as
     * <code>this = this + that</code>
     *
     * @param that the matrix to add to this
     * @return this matrix object - the operation modifies the original object
     */
    public T add(T that) {
        float[] a = this.m;
        float[] b = that.m;
        float n = a.length;
        for (int i = 0; i < n; i++) {
            a[i] += b[i];
        }
        return (T) this;
    }

    /**
     * Subtract the elements of that matrix from the elements of this matrix. The operation can be expressed as
     * <code>this = this - that</code>
     *
     * @param that the matrix to subtract from this
     * @return this matrix object - the operation modifies the original object
     */
    public T sub(T that) {
        float[] a = this.m;
        float[] b = that.m;
        float n = a.length;
        for (int i = 0; i < n; i++) {
            a[i] -= b[i];
        }
        return (T) this;
    }

    /**
     * Multiply this matrix by another 2x2 matrix. The operation can be expressed as
     * <code>this = this * that</code>
     *
     * @param that matrix to multiply this by
     * @return this matrix object - the operation modifies the original object
     */
    public T mul(T that) {
        float[] a = this.m;
        float[] b = that.m;
        float[] temp = new float[a.length];
        int s = size();

        for (int row = 0; row < s; row++) {
            for (int col = 0; col < s; col++) {
                int tempIndex = col * s + row;
                for (int k = 0; k < s; k++) {
                    temp[tempIndex] += a[k * s + row] * b[col * s + k];
                }
            }
        }
        System.arraycopy(temp, 0, a, 0, a.length);

        return (T) this;
    }

    /**
     * Multiply each element of this matrix by a scalar value
     *
     * @param scalar
     * @return this matrix object - the operation modifies the original object
     */
    public T scalar(float scalar) {
        float[] m = this.m;
        int n = m.length;
        for (int i = 0; i < n; i++) {
            m[i] *= scalar;
        }
        return (T) this;
    }

    /**
     * Negate each element of the matrix
     *
     * @return this matrix object - the operation modifies the original object
     */
    public T neg() {
        float[] m = this.m;
        int n = m.length;
        for (int i = 0; i < n; i++) {
            m[i] = -m[i];
        }
        return (T) this;
    }

    /**
     * Transpose the elements of the matrix
     *
     * @return this matrix object - the operation modifies the original object
     */
    public T transpose() {
        float[] m = this.m;
        int s = size();
        for (int row = 0; row < s; row++) {
            for (int col = row + 1; col < s; col++) {
                float t = m[col * s + row];
                m[col * s + row] = m[row * s + col];
                m[row * s + col] = t;
            }
        }
        return (T) this;
    }

    /**
     * Calculate the determinant of the matrix
     *
     * @return the calculated determinant
     */
    abstract public float getDeterminant();

    /**
     * Calculate the inverse of this matrix
     *
     * @return this matrix object - the operation modifies the original object
     */
    abstract public T invert();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SquareMatrix matrix = (SquareMatrix) o;

        if (!Arrays.equals(m, matrix.m)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(m);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n");
        int s = size();
        for (int row = 0; row < s; row++) {
            sb.append("| ");
            for (int col = 0; col < s; col++) {
                sb.append(String.format("%6.2f ", m[col * s + row]));
            }
            sb.append("|\n");
        }
        return sb.toString();
    }
}
