package net.badgerclaw.jogltest;

import net.badgerclaw.jogltest.math.AbstractMatrix;

import java.util.ArrayDeque;
import java.util.Deque;

public interface MatrixStack<T extends AbstractMatrix> {
    final Deque<AbstractMatrix> stack = new ArrayDeque<>();


}
