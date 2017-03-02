package com.wenyu.apt;

import com.wenyu.apt.annotations.MvpPresenter;
import com.wenyu.apt.element.ModelElement;
import com.wenyu.apt.element.PresenterElement;
import com.wenyu.apt.element.ViewElement;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
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

	/**
	 * APT生成代码所在的包名
	 */
	private String mPackage;

	private PresenterElement mPresenterElement;
	private ViewElement mViewElement;
	private ModelElement mModelElement;

	public CodeGenerator(Filer filer, Messager messager) {
		mFiler = filer;
		mMessager = messager;
	}

	public void generate(Set<? extends Element> presenterSet, Set<? extends Element> modelSet, Set<? extends Element> viewSet) throws IOException {
		parsePresenter(presenterSet);
		parseModel(modelSet);
		parseView(viewSet);
		generate();
	}

	private void parsePresenter(Set<? extends Element> presenterSet) {
		Iterator<? extends Element> iterator = presenterSet.iterator();
		VariableElement variableElement = (VariableElement) iterator.next();
		MvpPresenter mvpPresenter = variableElement.getAnnotation(MvpPresenter.class);
		mPresenterElement = new PresenterElement(variableElement.getSimpleName().toString(), variableElement.getEnclosingElement().toString(), mvpPresenter.component(), mvpPresenter.module());

		Element element = variableElement.getEnclosingElement();
		while (element != null && element.getEnclosingElement() != null) {
			mPackage = element.toString();
			element = element.getEnclosingElement();
		}

		mPackage = mPackage.substring(0, mPackage.lastIndexOf("."));
	}

	private void parseModel(Set<? extends Element> modelSet) {
		Iterator<? extends Element> iterator = modelSet.iterator();
		VariableElement variableElement = (VariableElement) iterator.next();
		mModelElement = new ModelElement(variableElement.getSimpleName().toString(), variableElement.getEnclosingElement().toString());
	}

	private void parseView(Set<? extends Element> viewSet) {
		Iterator<? extends Element> iterator = viewSet.iterator();
		VariableElement variableElement = (VariableElement) iterator.next();
		mViewElement = new ViewElement(variableElement.getSimpleName().toString(), variableElement.getEnclosingElement().toString());
	}

	public void generate() throws IOException {
		String clazzName = "Mvp" + mPresenterElement.enclosingClazzName.replaceAll("\\.", "\\$");
		//指定java文件写入的位置
		JavaFileObject javaFileObject = mFiler.createSourceFile(mPackage + "." + clazzName);
		mMessager.printMessage(Diagnostic.Kind.NOTE, "在" + mPackage + "." + clazzName + "生成代码");

		//开始写文件
		Writer writer = javaFileObject.openWriter();
		writer.write(generateSourceCode(mPackage, clazzName));
		writer.flush();
		writer.close();
	}


	private String generateSourceCode(String packageName, String clazzName) {

		//包
		StringBuilder stringBuilder = new StringBuilder("package ");
		stringBuilder.append(packageName);
		stringBuilder.append(";\n");


		stringBuilder.append("public class ");
		stringBuilder.append(clazzName);
		stringBuilder.append("{");

		stringBuilder.append("public static void inject(");
		stringBuilder.append(mPresenterElement.enclosingClazzName);
		stringBuilder.append(" o){");

//		String daggerComponentName = "Dagger" + mPresenterElement.component.getCanonicalName();
//		String builderName = daggerComponentName + ".Builder";
//
//		stringBuilder.append(builderName);
//		stringBuilder.append(" builder = ");
//		stringBuilder.append(daggerComponentName);
//		stringBuilder.append(".builder();\n");
//
//		String functionName = mPresenterElement.module.getSimpleName();
//
//		stringBuilder.append(daggerComponentName);
//		stringBuilder.append(" component = builder.");
//		stringBuilder.append(Character.toLowerCase(functionName.charAt(0)) + functionName.substring(1));
//		stringBuilder.append("(new ");
//		stringBuilder.append(mPresenterElement.module.getCanonicalName());
//		stringBuilder.append("(o)).build();\n");
//		stringBuilder.append("component.inject(o);");

		//结尾
		stringBuilder.append("}");
		return stringBuilder.toString();
	}
}
