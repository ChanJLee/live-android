package com.wenyu.apt;

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

    public boolean checkAnnotation(Element element) {
        VariableElement variableElement = (VariableElement) element;
        if (variableElement.getModifiers().contains(Modifier.PRIVATE)) {
            mMessager.printMessage(Diagnostic.Kind.ERROR, "Presenter不能用于private field: "
                    + variableElement.getEnclosingElement() + " -> " + variableElement.getSimpleName());
            return false;
        }
        
        return true;
    }
}
