package net.badgerclaw.jogltest;

import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;
import net.badgerclaw.jogltest.math.AbstractMatrix;
import net.badgerclaw.jogltest.math.Mat4;
import net.badgerclaw.jogltest.math.Vec3;

import javax.media.opengl.GL2ES2;
import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import java.nio.IntBuffer;

public class SimpleScene implements GLEventListener {

    private ShaderProgram program = new ShaderProgram();

    private final long startingTime = System.currentTimeMillis();

    private float[] vertexPositions = new float[]{
            0.75f, 0.75f, 0.0f, 1.0f,
            0.75f, -0.75f, 0.0f, 1.0f,
            -0.75f, -0.75f, 0.0f, 1.0f
    };

    private float[] vertexColours = new float[]{
            1f, 0f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 0f, 1f, 1f
    };

    private float[] GREEN_COLOR = new float[]{0.0f, 1.0f, 0.0f, 1.0f};
    private float[] BLUE_COLOR = new float[]{0.0f, 0.0f, 1.0f, 1.0f};
    private float[] RED_COLOR = new float[]{1.0f, 0.0f, 0.0f, 1.0f};
    //
    private float[] YELLOW_COLOR = new float[]{1.0f, 1.0f, 0.0f, 1.0f};
    private float[] CYAN_COLOR = new float[]{0.0f, 1.0f, 1.0f, 1.0f};
    private float[] MAGENTA_COLOR = new float[]{1.0f, 0.0f, 1.0f, 1.0f};
    private float[] vertexData = new float[]{
            //Front
            +1.0f, +1.0f, +1.0f,
            +1.0f, -1.0f, +1.0f,
            -1.0f, -1.0f, +1.0f,
            -1.0f, +1.0f, +1.0f,
            //Top
            +1.0f, +1.0f, +1.0f,
            -1.0f, +1.0f, +1.0f,
            -1.0f, +1.0f, -1.0f,
            +1.0f, +1.0f, -1.0f,
            //Left
            +1.0f, +1.0f, +1.0f,
            +1.0f, +1.0f, -1.0f,
            +1.0f, -1.0f, -1.0f,
            +1.0f, -1.0f, +1.0f,
            //Back
            +1.0f, +1.0f, -1.0f,
            -1.0f, +1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            +1.0f, -1.0f, -1.0f,
            //Bottom
            +1.0f, -1.0f, +1.0f,
            +1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, +1.0f,
            //Right
            -1.0f, +1.0f, +1.0f,
            -1.0f, -1.0f, +1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, +1.0f, -1.0f,
            //
            GREEN_COLOR[0], GREEN_COLOR[1], GREEN_COLOR[2], GREEN_COLOR[3],
            GREEN_COLOR[0], GREEN_COLOR[1], GREEN_COLOR[2], GREEN_COLOR[3],
            GREEN_COLOR[0], GREEN_COLOR[1], GREEN_COLOR[2], GREEN_COLOR[3],
            GREEN_COLOR[0], GREEN_COLOR[1], GREEN_COLOR[2], GREEN_COLOR[3],
            //
            BLUE_COLOR[0], BLUE_COLOR[1], BLUE_COLOR[2], BLUE_COLOR[3],
            BLUE_COLOR[0], BLUE_COLOR[1], BLUE_COLOR[2], BLUE_COLOR[3],
            BLUE_COLOR[0], BLUE_COLOR[1], BLUE_COLOR[2], BLUE_COLOR[3],
            BLUE_COLOR[0], BLUE_COLOR[1], BLUE_COLOR[2], BLUE_COLOR[3],
            //
            RED_COLOR[0], RED_COLOR[1], RED_COLOR[2], RED_COLOR[3],
            RED_COLOR[0], RED_COLOR[1], RED_COLOR[2], RED_COLOR[3],
            RED_COLOR[0], RED_COLOR[1], RED_COLOR[2], RED_COLOR[3],
            RED_COLOR[0], RED_COLOR[1], RED_COLOR[2], RED_COLOR[3],
            //
            YELLOW_COLOR[0], YELLOW_COLOR[1], YELLOW_COLOR[2], YELLOW_COLOR[3],
            YELLOW_COLOR[0], YELLOW_COLOR[1], YELLOW_COLOR[2], YELLOW_COLOR[3],
            YELLOW_COLOR[0], YELLOW_COLOR[1], YELLOW_COLOR[2], YELLOW_COLOR[3],
            YELLOW_COLOR[0], YELLOW_COLOR[1], YELLOW_COLOR[2], YELLOW_COLOR[3],
            //
            CYAN_COLOR[0], CYAN_COLOR[1], CYAN_COLOR[2], CYAN_COLOR[3],
            CYAN_COLOR[0], CYAN_COLOR[1], CYAN_COLOR[2], CYAN_COLOR[3],
            CYAN_COLOR[0], CYAN_COLOR[1], CYAN_COLOR[2], CYAN_COLOR[3],
            CYAN_COLOR[0], CYAN_COLOR[1], CYAN_COLOR[2], CYAN_COLOR[3],
            //
            MAGENTA_COLOR[0], MAGENTA_COLOR[1], MAGENTA_COLOR[2], MAGENTA_COLOR[3],
            MAGENTA_COLOR[0], MAGENTA_COLOR[1], MAGENTA_COLOR[2], MAGENTA_COLOR[3],
            MAGENTA_COLOR[0], MAGENTA_COLOR[1], MAGENTA_COLOR[2], MAGENTA_COLOR[3],
            MAGENTA_COLOR[0], MAGENTA_COLOR[1], MAGENTA_COLOR[2], MAGENTA_COLOR[3]};

    int numberOfVertices = 24;

    private int[] indexData = new int[]{
            0, 1, 2,
            2, 3, 0,
            //
            4, 5, 6,
            6, 7, 4,
            //
            8, 9, 10,
            10, 11, 8,
            //
            12, 13, 14,
            14, 15, 12,
            //
            16, 17, 18,
            18, 19, 16,
            //
            20, 21, 22,
            22, 23, 20};

    private int[] bufferObject = new int[2];
    private int[] vertexArrayObject = new int[1];

    @Override
    public void init(GLAutoDrawable drawable) {
        System.err.println("init called");
        drawable.getGL().setSwapInterval(1);
        GL3 gl3 = drawable.getGL().getGL3();

        buildShaders(gl3);

        initializeVertexArray(gl3);

        gl3.glEnable(GL3.GL_CULL_FACE);
        gl3.glCullFace(GL3.GL_BACK);
        gl3.glFrontFace(GL3.GL_CW);

        gl3.glEnable(GL3.GL_DEPTH_TEST);
        gl3.glDepthMask(true);
        gl3.glDepthFunc(GL3.GL_LEQUAL);
        gl3.glDepthRangef(0.0f, 1.0f);

    }

    private void initializeVertexArray(GL3 gl3) {

        gl3.glGenVertexArrays(1, IntBuffer.wrap(vertexArrayObject));
        gl3.glBindVertexArray(vertexArrayObject[0]);

        gl3.glGenBuffers(bufferObject.length, IntBuffer.wrap(bufferObject));

        gl3.glBindBuffer(GL3.GL_ARRAY_BUFFER, bufferObject[0]);
        {
            gl3.glBufferData(GL3.GL_ARRAY_BUFFER, vertexData.length * 4, GLBuffers.newDirectFloatBuffer(vertexData), GL3.GL_STATIC_DRAW);

            gl3.glEnableVertexAttribArray(0);
            gl3.glVertexAttribPointer(0, 3, GL3.GL_FLOAT, false, 0, 0);

            int colorDataOffset = numberOfVertices * 3 * 4;
            gl3.glEnableVertexAttribArray(1);
            gl3.glVertexAttribPointer(1, 4, GL3.GL_FLOAT, false, 0, colorDataOffset);
        }

        gl3.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, bufferObject[1]);
        {
            gl3.glBufferData(GL3.GL_ELEMENT_ARRAY_BUFFER, indexData.length * 4, GLBuffers.newDirectIntBuffer(indexData), GL3.GL_STATIC_DRAW);
        }

        gl3.glBindVertexArray(0);
        gl3.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0);
        gl3.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    private void buildShaders(GL3 gl3) {
        ShaderCode vertex = ShaderCode.create(gl3, GL2ES2.GL_VERTEX_SHADER, this.getClass(), "shaders", "shaders/bin", "basic", false);
        ShaderCode fragment = ShaderCode.create(gl3, GL2ES2.GL_FRAGMENT_SHADER, this.getClass(), "shaders", "shaders/bin", "basic", false);

        program.init(gl3);

        program.add(gl3, vertex, System.out);
        program.add(gl3, fragment, System.out);

        program.link(gl3, System.out);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        System.err.println("dispose called");

        program.release(drawable.getGL().getGL3());
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        float time = (System.currentTimeMillis() - startingTime) / 1000f;

        GL3 gl3 = drawable.getGL().getGL3();

        gl3.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl3.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);

        program.useProgram(gl3, true);
        {
            gl3.glBindVertexArray(vertexArrayObject[0]);
            setUniform(gl3, "cameraToClipMatrix", calcPerspectiveMatrix());

            Mat4 m1 = calcTranslationMatrix(0, 0, -10)
                    .mul(calcRotationMatrix(new Vec3(1, 0, 0.3f), time))
                    .push()
                    .mul(calcScaleMatrix(1f, 1f, 0.2f));

            setUniform(gl3, "modelToCameraMatrix", m1);
            gl3.glDrawElements(GL3.GL_TRIANGLES, indexData.length, GL3.GL_UNSIGNED_INT, 0);

            m1 = m1.pop()
                    .mul(calcTranslationMatrix(0, 0, 1.2f))
                    .mul(calcScaleMatrix(0.25f, 0.25f, 1f))
                    .mul(calcRotationMatrix(new Vec3(0, 0, 1), time*4.2f));
            setUniform(gl3, "modelToCameraMatrix", m1);
            gl3.glDrawElements(GL3.GL_TRIANGLES, indexData.length, GL3.GL_UNSIGNED_INT, 0);
        }
        program.useProgram(gl3, false);
    }

    private Mat4 calcTranslationMatrix(float x, float y, float z) {
        Mat4 matrix = Mat4.identity();

        matrix.m[Mat4.M03] = x;
        matrix.m[Mat4.M13] = y;
        matrix.m[Mat4.M23] = z;

        return matrix;
    }

    private Mat4 calcScaleMatrix(float xScale, float yScale, float zScale) {
        Mat4 matrix = new Mat4();
        matrix.m[Mat4.M00] = xScale;
        matrix.m[Mat4.M11] = yScale;
        matrix.m[Mat4.M22] = zScale;
        matrix.m[Mat4.M33] = 1f;
        return matrix;
    }

    private Mat4 calcRotationMatrix(Vec3 axis, float time) {
        Mat4 rotationMatrix = Mat4.rotation(axis.normalize(), time);

        return rotationMatrix;
    }

    private float calculatFrustumScale(float fFovDeg) {
        float degToRad = 3.14159f * 2.0f / 360.0f;
        float fFovRad = fFovDeg * degToRad;
        return (float) (1.0f / Math.tan(fFovRad / 2.0f));
    }

    private Mat4 calcPerspectiveMatrix() {
        float fFrustumScale = calculatFrustumScale(45);
        float fzNear = 0.5f;
        float fzFar = 100.0f;

        Mat4 perspectiveMatrix = new Mat4();

        perspectiveMatrix.m[0] = fFrustumScale;
        perspectiveMatrix.m[5] = fFrustumScale;
        perspectiveMatrix.m[10] = (fzFar + fzNear) / (fzNear - fzFar);
        perspectiveMatrix.m[14] = (2 * fzFar * fzNear) / (fzNear - fzFar);
        perspectiveMatrix.m[11] = -1.0f;

        return perspectiveMatrix;
    }

    private void setUniform(GL3 gl3, String name, AbstractMatrix matrix) {
        int id = gl3.glGetUniformLocation(program.id(), name);
        if (id == -1) {
            throw new IllegalArgumentException("Invalid uniform parameter " + name);
        }
        switch(matrix.dimensions()) {
            case 2:
                gl3.glUniformMatrix2fv(id, 1, false, matrix.m, 0);
                break;
            case 3:
                gl3.glUniformMatrix3fv(id, 1, false, matrix.m, 0);
                break;
            case 4:
                gl3.glUniformMatrix4fv(id, 1, false, matrix.m, 0);
                break;
            default:
                throw new IllegalArgumentException("Cannot set a uniform with a matrix of dimensionality " + matrix.dimensions());
        }
    }

    private void setUniform(GL3 gl3, String name, float ... val) {
        int id = gl3.glGetUniformLocation(program.id(), name);
        if (id == -1) {
            System.err.println("Warning: Invalid uniform parameter " + name);
            return;
        }
        switch (val.length) {
            case 1:
                gl3.glUniform1fv(id, 1, val, 0);
                break;
            case 2:
                gl3.glUniform2fv(id, 1, val, 0);
                break;
            case 3:
                gl3.glUniform3fv(id, 1, val, 0);
                break;
            case 4:
                gl3.glUniform4fv(id, 1, val, 0);
                break;
            default:
                throw new IllegalArgumentException("Cannot set a uniform of " + val.length + " values");
        }
    }


    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        System.err.println("reshape called");
    }
}
