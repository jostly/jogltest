package net.badgerclaw.jogltest.math;

/**
 * 4x4 matrix of floats
 */
public class Mat4 extends SquareMatrix<Mat4> {
    final public static int
            M00 = 0,
            M10 = 1,
            M20 = 2,
            M30 = 3,
            M01 = 4,
            M11 = 5,
            M21 = 6,
            M31 = 7,
            M02 = 8,
            M12 = 9,
            M22 = 10,
            M32 = 11,
            M03 = 12,
            M13 = 13,
            M23 = 14,
            M33 = 15;


    public Mat4() {
    }

    public Mat4(float m00, float m01, float m02, float m03,
                float m10, float m11, float m12, float m13,
                float m20, float m21, float m22, float m23,
                float m30, float m31, float m32, float m33) {
        m[M00] = m00;
        m[M01] = m01;
        m[M02] = m02;
        m[M03] = m03;
        m[M10] = m10;
        m[M11] = m11;
        m[M12] = m12;
        m[M13] = m13;
        m[M20] = m20;
        m[M21] = m21;
        m[M22] = m22;
        m[M23] = m23;
        m[M30] = m30;
        m[M31] = m31;
        m[M32] = m32;
        m[M33] = m33;
    }

    @Override
    public float getDeterminant() {
        float f = m[M00]
                * ((m[M11] * m[M22] * m[M33] + m[M12] * m[M23] * m[M31] + m[M13] * m[M21] * m[M32])
                - m[M13] * m[M22] * m[M31]
                - m[M11] * m[M23] * m[M32]
                - m[M12] * m[M21] * m[M33]);
        f -= m[M01]
                * ((m[M10] * m[M22] * m[M33] + m[M12] * m[M23] * m[M30] + m[M13] * m[M20] * m[M32])
                - m[M13] * m[M22] * m[M30]
                - m[M10] * m[M23] * m[M32]
                - m[M12] * m[M20] * m[M33]);
        f += m[M02]
                * ((m[M10] * m[M21] * m[M33] + m[M11] * m[M23] * m[M30] + m[M13] * m[M20] * m[M31])
                - m[M13] * m[M21] * m[M30]
                - m[M10] * m[M23] * m[M31]
                - m[M11] * m[M20] * m[M33]);
        f -= m[M03]
                * ((m[M10] * m[M21] * m[M32] + m[M11] * m[M22] * m[M30] + m[M12] * m[M20] * m[M31])
                - m[M12] * m[M21] * m[M30]
                - m[M10] * m[M22] * m[M31]
                - m[M11] * m[M20] * m[M32]);
        return f;
    }

    /**
     * Calculate the determinant of a 3x3 matrix
     * @return result
     */

    private float determinant3x3(float t00, float t01, float t02,
                                        float t10, float t11, float t12,
                                        float t20, float t21, float t22)
    {
        return   t00 * (t11 * t22 - t12 * t21)
                + t01 * (t12 * t20 - t10 * t22)
                + t02 * (t10 * t21 - t11 * t20);
    }
    
    @Override
    public Mat4 invert() {
        float[] m = this.m;
        float determinant = getDeterminant();
        float determinant_inv = 1f / determinant;

        // first row
        float t00 =  determinant3x3(m[M11], m[M12], m[M13], m[M21], m[M22], m[M23], m[M31], m[M32], m[M33]);
        float t01 = -determinant3x3(m[M10], m[M12], m[M13], m[M20], m[M22], m[M23], m[M30], m[M32], m[M33]);
        float t02 =  determinant3x3(m[M10], m[M11], m[M13], m[M20], m[M21], m[M23], m[M30], m[M31], m[M33]);
        float t03 = -determinant3x3(m[M10], m[M11], m[M12], m[M20], m[M21], m[M22], m[M30], m[M31], m[M32]);
        // second row
        float t10 = -determinant3x3(m[M01], m[M02], m[M03], m[M21], m[M22], m[M23], m[M31], m[M32], m[M33]);
        float t11 =  determinant3x3(m[M00], m[M02], m[M03], m[M20], m[M22], m[M23], m[M30], m[M32], m[M33]);
        float t12 = -determinant3x3(m[M00], m[M01], m[M03], m[M20], m[M21], m[M23], m[M30], m[M31], m[M33]);
        float t13 =  determinant3x3(m[M00], m[M01], m[M02], m[M20], m[M21], m[M22], m[M30], m[M31], m[M32]);
        // third row
        float t20 =  determinant3x3(m[M01], m[M02], m[M03], m[M11], m[M12], m[M13], m[M31], m[M32], m[M33]);
        float t21 = -determinant3x3(m[M00], m[M02], m[M03], m[M10], m[M12], m[M13], m[M30], m[M32], m[M33]);
        float t22 =  determinant3x3(m[M00], m[M01], m[M03], m[M10], m[M11], m[M13], m[M30], m[M31], m[M33]);
        float t23 = -determinant3x3(m[M00], m[M01], m[M02], m[M10], m[M11], m[M12], m[M30], m[M31], m[M32]);
        // fourth row
        float t30 = -determinant3x3(m[M01], m[M02], m[M03], m[M11], m[M12], m[M13], m[M21], m[M22], m[M23]);
        float t31 =  determinant3x3(m[M00], m[M02], m[M03], m[M10], m[M12], m[M13], m[M20], m[M22], m[M23]);
        float t32 = -determinant3x3(m[M00], m[M01], m[M03], m[M10], m[M11], m[M13], m[M20], m[M21], m[M23]);
        float t33 =  determinant3x3(m[M00], m[M01], m[M02], m[M10], m[M11], m[M12], m[M20], m[M21], m[M22]);

        // transpose and divide by the determinant
        m[M00] = t00*determinant_inv;
        m[M11] = t11*determinant_inv;
        m[M22] = t22*determinant_inv;
        m[M33] = t33*determinant_inv;
        m[M01] = t10*determinant_inv;
        m[M10] = t01*determinant_inv;
        m[M20] = t02*determinant_inv;
        m[M02] = t20*determinant_inv;
        m[M12] = t21*determinant_inv;
        m[M21] = t12*determinant_inv;
        m[M03] = t30*determinant_inv;
        m[M30] = t03*determinant_inv;
        m[M13] = t31*determinant_inv;
        m[M31] = t13*determinant_inv;
        m[M32] = t23*determinant_inv;
        m[M23] = t32*determinant_inv;
        return this;
    }

    @Override
    int size() {
        return 4;
    }

    @Override
    public Mat4 clone() {
        return copyTo(new Mat4());
    }

}
