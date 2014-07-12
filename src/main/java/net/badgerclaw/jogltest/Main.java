package net.badgerclaw.jogltest;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.*;

public class Main {
    static {
        GLProfile.initSingleton();
    }

    public static void main(String[] args) throws InterruptedException {
        GLProfile profile = GLProfile.getMaxProgrammable(true);
        GLCapabilities caps = new GLCapabilities(profile);
        System.err.println("Profile: " + profile);
        System.err.println("Caps: " + caps);

        GLWindow window = GLWindow.create(caps);
        window.setSize(300, 300);
        window.setVisible(true);
        window.setTitle("NEWT Window Test");
        window.addGLEventListener(new SimpleScene());

        final FPSAnimator animator = new FPSAnimator(window, 60);
        animator.start();

        window.addWindowListener(new WindowAdapter() {
            public void windowDestroyNotify(WindowEvent arg0) {
                animator.stop();
            }
        });

    }
}
