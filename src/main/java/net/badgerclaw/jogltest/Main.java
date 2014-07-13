package net.badgerclaw.jogltest;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;

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
        window.setSize(500, 500);
        window.setVisible(true);
        window.setTitle("NEWT Window Test");
        SimpleScene simpleScene = new SimpleScene(window);
        window.addGLEventListener(simpleScene);
        window.addKeyListener(simpleScene);
        window.addMouseListener(simpleScene);
        window.requestFocus();
        window.confinePointer(true);
        window.setPointerVisible(false);
        window.addWindowListener(simpleScene);

        final FPSAnimator animator = new FPSAnimator(window, 60);
        animator.start();

        window.addWindowListener(new WindowAdapter() {
            public void windowDestroyNotify(WindowEvent arg0) {
                animator.stop();
            }
        });

    }
}
