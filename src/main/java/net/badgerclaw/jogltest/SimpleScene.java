package net.badgerclaw.jogltest;

import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;

import javax.media.opengl.*;
import java.nio.FloatBuffer;
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

    private int[] positionBufferObject = new int[2];
    private int[] vertexArrayObject = new int[1];

    @Override
    public void init(GLAutoDrawable drawable) {
        System.err.println("init called");
        drawable.getGL().setSwapInterval(1);
        GL3 gl3 = drawable.getGL().getGL3();

        buildShaders(gl3);

        initializeVertexArray(gl3);

    }

    private void initializeVertexArray(GL3 gl3) {

        gl3.glGenVertexArrays(1, IntBuffer.wrap(vertexArrayObject));
        gl3.glBindVertexArray(vertexArrayObject[0]);

        gl3.glGenBuffers(positionBufferObject.length, IntBuffer.wrap(positionBufferObject));

        gl3.glBindBuffer(GL3.GL_ARRAY_BUFFER, positionBufferObject[0]);
        {
            FloatBuffer buffer = GLBuffers.newDirectFloatBuffer(vertexPositions);

            gl3.glBufferData(GL3.GL_ARRAY_BUFFER, vertexPositions.length * 4, buffer, GL3.GL_STATIC_DRAW);
            gl3.glVertexAttribPointer(0, 4, GL.GL_FLOAT, false, 0, 0);
            gl3.glEnableVertexAttribArray(0);
        }


        gl3.glBindBuffer(GL3.GL_ARRAY_BUFFER, positionBufferObject[1]);
        {
            FloatBuffer buffer = GLBuffers.newDirectFloatBuffer(vertexColours);

            gl3.glBufferData(GL3.GL_ARRAY_BUFFER, vertexColours.length * 4, buffer, GL3.GL_STATIC_DRAW);
            gl3.glVertexAttribPointer(1, 4, GL.GL_FLOAT, false, 0, 0);
            gl3.glEnableVertexAttribArray(1);
        }

        gl3.glBindVertexArray(0);
        gl3.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0);
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
        GL3 gl3 = drawable.getGL().getGL3();

        gl3.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl3.glClear(GL3.GL_COLOR_BUFFER_BIT);

        program.useProgram(gl3, true);
        {
            setUniform(gl3, "rot", (System.currentTimeMillis() - startingTime) / 10f);
            gl3.glBindVertexArray(vertexArrayObject[0]);
            gl3.glDrawArrays(GL3.GL_TRIANGLES, 0, 3);
        }
        program.useProgram(gl3, false);
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
