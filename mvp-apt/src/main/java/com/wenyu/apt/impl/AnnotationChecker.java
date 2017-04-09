package com.wenyu.apt.impl;

import java.util.Iterator;
import java.util.Set;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;

/**
 * Created by chan on 16/10/15.
 * jiacheng.li@shanbay.com
 */
public class AnnotationChecker {

    private Messager mMessager;

    public AnnotationChecker(Messager messager) {
        mMessager = messager;
    }

    public boolean checkAnnotation(Set<? extends Element> presenterSet, Set<? extends Element> modelSet, Set<? extends Element> viewSet) {
        return checkPresenterAnnotation(presenterSet) &&
                checkModelAnnotation(modelSet) &&
                checkViewAnnotation(viewSet);
    }

    private boolean checkPresenterAnnotation(Set<? extends Element> elements) {
        Iterator iterator = elements.iterator();
        while (iterator.hasNext()) {
            if (!checkModifier((Element) iterator.next())) {
                return false;
            }
        }

        return true;
    }

    private boolean checkModelAnnotation(Set<? extends Element> elements) {
        Iterator iterator = elements.iterator();
        while (iterator.hasNext()) {
            if (!checkModifier((Element) iterator.next())) {
                return false;
            }
        }

        return true;
    }

    private boolean checkViewAnnotation(Set<? extends Element> elements) {
        Iterator iterator = elements.iterator();
        while (iterator.hasNext()) {
            if (!checkModifier((Element) iterator.next())) {
                return false;
            }
        }

        return true;
    }

    private boolean checkModifier(Element element) {

        if (!(element instanceof VariableElement)) {
            return false;
        }

        VariableElement variableElement = (VariableElement) element;
        if (variableElement.getModifiers().contains(Modifier.PRIVATE)) {

            String error = String.format("%s中的%s不能为private", variableElement.getEnclosingElement().toString(), variableElement.getSimpleName().toString());

            mMessager.printMessage(Diagnostic.Kind.ERROR, error);
            return false;
        }

        return true;
    }
}
