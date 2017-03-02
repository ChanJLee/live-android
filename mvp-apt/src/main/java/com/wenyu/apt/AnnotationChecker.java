package com.wenyu.apt;

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

	private static final int MAX_PRESENTER_SIZE = 1;
	private static final int MAX_VIEW_SIZE = 1;
	private static final int MAX_MODEL_SIZE = 1;

	private Messager mMessager;

	public AnnotationChecker(Messager messager) {
		mMessager = messager;
	}

	public boolean checkAnnotation(Set<? extends Element> presenterSet, Set<? extends Element> modelSet, Set<? extends Element> viewSet) {
		//没有presenter Model view 则报错
		if (presenterSet.isEmpty() && modelSet.isEmpty() && viewSet.isEmpty()) {
			mMessager.printMessage(Diagnostic.Kind.ERROR, "缺少View Presenter Model注释");
			return false;
		}

		//如果没有presenter 而有Model 或者view 则报错
		if (presenterSet.isEmpty() && (!modelSet.isEmpty() || !viewSet.isEmpty())) {
			mMessager.printMessage(Diagnostic.Kind.ERROR, "缺少presenter");
			return false;
		}

		//有Presenter而没有View
		if (!presenterSet.isEmpty() && viewSet.isEmpty()) {
			mMessager.printMessage(Diagnostic.Kind.ERROR, "缺少view");
			return false;
		}

		return checkPresenterAnnotation(presenterSet) && checkModelAnnotation(modelSet) && checkViewAnnotation(viewSet);

	}

	private boolean checkPresenterAnnotation(Set<? extends Element> elements) {
		if (elements.size() > MAX_PRESENTER_SIZE) {
			mMessager.printMessage(Diagnostic.Kind.ERROR, "presenter至多之只能有一个");
			return false;
		}

		if (!elements.isEmpty()) {
			Iterator iterator = elements.iterator();
			return checkModifier((Element) iterator.next());
		}

		return true;
	}

	private boolean checkModelAnnotation(Set<? extends Element> elements) {
		if (elements.size() > MAX_MODEL_SIZE) {
			mMessager.printMessage(Diagnostic.Kind.ERROR, "model至多之只能有一个");
			return false;
		}

		if (!elements.isEmpty()) {
			Iterator iterator = elements.iterator();
			return checkModifier((Element) iterator.next());
		}

		return true;
	}

	private boolean checkViewAnnotation(Set<? extends Element> elements) {

		if (elements.size() > MAX_VIEW_SIZE) {
			mMessager.printMessage(Diagnostic.Kind.ERROR, "view至多之只能有一个");
			return false;
		}

		if (!elements.isEmpty()) {
			Iterator iterator = elements.iterator();
			return checkModifier((Element) iterator.next());
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
