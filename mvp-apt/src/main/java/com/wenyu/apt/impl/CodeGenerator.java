package com.wenyu.apt.impl;

import com.wenyu.apt.annotations.MvpPresenter;
import com.wenyu.apt.element.Bundle;
import com.wenyu.apt.element.Clazz;
import com.wenyu.apt.element.ModelElement;
import com.wenyu.apt.element.PresenterElement;
import com.wenyu.apt.element.ViewElement;
import com.wenyu.apt.utils.ClazzMapUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;


/**
 * Created by chan on 16/10/15.
 * jiacheng.li@shanbay.com
 */
public class CodeGenerator {
	/**
	 * 用于写java文件
	 */
	private Filer mFiler;
	/**
	 * logger
	 */
	private Messager mMessager;

	private Map<Element, Bundle> mTypeElementBundleMap;

	public CodeGenerator(Filer filer, Messager messager) {
		mFiler = filer;
		mMessager = messager;
		mTypeElementBundleMap = new HashMap<>();
	}

	public void generate(Set<? extends Element> presenterSet, Set<? extends Element> modelSet, Set<? extends Element> viewSet) throws IOException {
		parsePresenter(presenterSet);
		parseModel(modelSet);
		parseView(viewSet);
		generate();
	}

	private void parsePresenter(Set<? extends Element> presenterSet) {

		for (Element aPresenterSet : presenterSet) {
			VariableElement variableElement = (VariableElement) aPresenterSet;

			PresenterElement presenterElement = null;
			for (AnnotationMirror annotationMirror : variableElement.getAnnotationMirrors()) {
				if (MvpPresenter.class.getCanonicalName().equals(annotationMirror.getAnnotationType().toString())) {
					Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotationMirror.getElementValues();
					TypeMirror mirror = variableElement.asType();
					presenterElement = new PresenterElement(variableElement.getSimpleName().toString(), variableElement.getEnclosingElement().toString(), mirror.toString());
					for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {

						String key = entry.getKey().getSimpleName().toString();
						if ("module".equals(key)) {
							presenterElement.module = new Clazz(entry.getValue().toString());
						} else if ("component".equals(key)) {
							presenterElement.component = new Clazz(entry.getValue().toString());
						} else if ("dependency".equals(key)) {
							presenterElement.dependency = new Clazz(entry.getValue().toString());
						}
					}
				}
			}

			String packageName = null;
			Element element = variableElement.getEnclosingElement();
			while (element != null && element.getEnclosingElement() != null) {
				packageName = element.toString();
				element = element.getEnclosingElement();
			}

			packageName = packageName.substring(0, packageName.lastIndexOf("."));

			Bundle bundle = getBundle(variableElement.getEnclosingElement());
			bundle.mPackageName = packageName;
			bundle.mPresenterElement = presenterElement;
		}
	}

	private void parseModel(Set<? extends Element> modelSet) {

		for (Element aModelSet : modelSet) {
			VariableElement variableElement = (VariableElement) aModelSet;
			TypeMirror mirror = variableElement.asType();
			ModelElement modelElement = new ModelElement(variableElement.getSimpleName().toString(), variableElement.getEnclosingElement().toString(), mirror.toString());
			Bundle bundle = getBundle(variableElement.getEnclosingElement());
			bundle.mModelElement = modelElement;
		}
	}

	private void parseView(Set<? extends Element> viewSet) {
		for (Element aViewSet : viewSet) {
			VariableElement variableElement = (VariableElement) aViewSet;
			TypeMirror mirror = variableElement.asType();
			ViewElement viewElement = new ViewElement(variableElement.getSimpleName().toString(), variableElement.getEnclosingElement().toString(), mirror.toString());
			Bundle bundle = getBundle(variableElement.getEnclosingElement());
			mMessager.printMessage(Diagnostic.Kind.NOTE, "view-> " + variableElement.getSimpleName());
			bundle.mViewElement = viewElement;
		}
	}

	public void generate() throws IOException {

		for (Bundle bundle : mTypeElementBundleMap.values()) {

			mMessager.printMessage(Diagnostic.Kind.NOTE, bundle.mPackageName + ">>package");
			mMessager.printMessage(Diagnostic.Kind.NOTE, bundle.mPresenterElement + ">>presenter");
			mMessager.printMessage(Diagnostic.Kind.NOTE, bundle.mModelElement + ">>model");
			mMessager.printMessage(Diagnostic.Kind.NOTE, bundle.mViewElement + ">>view");

			//指定java文件写入的位置
			String clazzName = ClazzMapUtils.getClazzName(bundle.mPresenterElement.enclosingClazzName);
			JavaFileObject javaFileObject = mFiler.createSourceFile(bundle.mPackageName + "." + clazzName);

			//开始写文件
			Writer writer = javaFileObject.openWriter();
			writer.write(generateSourceCode(bundle, clazzName));
			writer.flush();
			writer.close();
		}
	}


	private String generateSourceCode(Bundle bundle, String clazzName) {

		//包
		StringBuilder stringBuilder = new StringBuilder("package ");
		stringBuilder.append(bundle.mPackageName);
		stringBuilder.append(";\n");


		stringBuilder.append("public class ");
		stringBuilder.append(clazzName);
		stringBuilder.append("{");

		stringBuilder.append("public static void inject(");
		stringBuilder.append(bundle.mPresenterElement.enclosingClazzName);
		stringBuilder.append(" o");
		stringBuilder.append(",");
		if (bundle.mPresenterElement.dependency != null) {
			stringBuilder.append(bundle.mPresenterElement.dependency.getCanonicalName());
			stringBuilder.append(" o1");
		} else {
			stringBuilder.append("Object o1");
		}
		stringBuilder.append("){");

		String daggerComponentName = bundle.mPresenterElement.component.getPackage() + ".Dagger" + bundle.mPresenterElement.component.getSimpleName();
		String builderName = daggerComponentName + ".Builder";

		stringBuilder.append(builderName);
		stringBuilder.append(" builder = ");
		stringBuilder.append(daggerComponentName);
		stringBuilder.append(".builder();\n");


		stringBuilder.append(bundle.mPresenterElement.component.getCanonicalName());
		stringBuilder.append(" component = builder.");
		stringBuilder.append(simpleName2FunctionName(bundle.mPresenterElement.module.getSimpleName()));
		stringBuilder.append("(new ");
		stringBuilder.append(bundle.mPresenterElement.module.getCanonicalName());
		stringBuilder.append("(o))");
		if (bundle.mPresenterElement.dependency != null) {
			stringBuilder.append(".");
			stringBuilder.append(simpleName2FunctionName(bundle.mPresenterElement.dependency.getSimpleName()));
			stringBuilder.append("(o1)");
		}
		stringBuilder.append(".build();");

		stringBuilder.append("component.inject(o);");
		stringBuilder.append("o.");
		stringBuilder.append(bundle.mPresenterElement.fieldName);
		stringBuilder.append("= new ");
		stringBuilder.append(bundle.mPresenterElement.type);

		stringBuilder.append("(o.");
		stringBuilder.append(bundle.mViewElement.fieldName);
		stringBuilder.append(",");
		if (bundle.mModelElement == null) {
			stringBuilder.append("null");
		} else {
			stringBuilder.append("o.");
			stringBuilder.append(bundle.mModelElement.fieldName);
		}
		stringBuilder.append(");");

		//结尾
		stringBuilder.append("}}");
		return stringBuilder.toString();
	}

	private Bundle getBundle(Element encloseElement) {
		Bundle bundle = mTypeElementBundleMap.get(encloseElement);
		if (bundle == null) {
			bundle = new Bundle();
			mTypeElementBundleMap.put(encloseElement, bundle);
		}

		return bundle;
	}

	public static String simpleName2FunctionName(String simpleName) {

		if (simpleName == null || simpleName.isEmpty()) {
			return simpleName;
		}

		if (simpleName.length() == 1) {
			return String.valueOf(Character.toLowerCase(simpleName.charAt(0)));
		}

		return Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
	}
}
