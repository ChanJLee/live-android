package com.wenyu.apt.impl;

import com.wenyu.apt.annotations.MvpInject;
import com.wenyu.apt.annotations.MvpModel;
import com.wenyu.apt.annotations.MvpPresenter;
import com.wenyu.apt.annotations.MvpView;
import com.wenyu.apt.element.InjectElement;
import com.wenyu.apt.element.Clazz;
import com.wenyu.apt.element.InjectTarget;
import com.wenyu.apt.element.ModelElement;
import com.wenyu.apt.element.PresenterElement;
import com.wenyu.apt.element.ViewElement;
import com.wenyu.apt.utils.ClazzMapUtils;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
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

    private Map<Element, InjectTarget> mTypeElementBundleMap;

    public CodeGenerator(Filer filer, Messager messager) {
        mFiler = filer;
        mMessager = messager;
        mTypeElementBundleMap = new HashMap<>();
    }

    public void generate(Set<? extends Element> presenterSet, Set<? extends Element> modelSet, Set<? extends Element> viewSet, Set<? extends Element> mvpInjectSet) throws IOException {
        parsePresenter(presenterSet);
        parseModel(modelSet);
        parseView(viewSet);
        parseMvpInject(mvpInjectSet);
        generate();
    }

    private void parseMvpInject(Set<? extends Element> mvpInjectSet) {
        for (Element element : mvpInjectSet) {

            InjectTarget injectTarget = null;
            for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
                if (MvpInject.class.getCanonicalName().equals(annotationMirror.getAnnotationType().toString())) {
                    Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotationMirror.getElementValues();
                    injectTarget = getInjectTarget(element);
                    for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {

                        String key = entry.getKey().getSimpleName().toString();
                        if ("module".equals(key)) {
                            injectTarget.module = new Clazz(entry.getValue().toString());
                        } else if ("component".equals(key)) {
                            injectTarget.component = new Clazz(entry.getValue().toString());
                        } else if ("dependency".equals(key)) {
                            injectTarget.dependency = new Clazz(entry.getValue().toString());
                        }
                    }
                }
            }

            injectTarget.target = element.toString();
            injectTarget.packageName = element.getEnclosingElement().toString();
        }
    }

    private void parsePresenter(Set<? extends Element> presenterSet) {

        for (Element element : presenterSet) {
            VariableElement variableElement = (VariableElement) element;

            PresenterElement presenterElement = null;
            for (AnnotationMirror annotationMirror : variableElement.getAnnotationMirrors()) {
                if (MvpPresenter.class.getCanonicalName().equals(annotationMirror.getAnnotationType().toString())) {
                    TypeMirror mirror = variableElement.asType();
                    presenterElement = new PresenterElement(variableElement.getSimpleName().toString(), variableElement.getEnclosingElement().toString(), mirror.toString());
                }
            }

            InjectTarget injectTarget = getInjectTarget(variableElement.getEnclosingElement());
            MvpPresenter mvpPresenter = variableElement.getAnnotation(MvpPresenter.class);
            InjectElement injectElement = getBundle(injectTarget.mInjectElements, mvpPresenter.tag());
            if (injectElement == null) {
                injectElement = new InjectElement();
                injectElement.mTag = mvpPresenter.tag();
                injectTarget.mInjectElements.add(injectElement);
            }

            injectElement.mPresenterElement = presenterElement;
        }
    }

    private void parseModel(Set<? extends Element> modelSet) {

        for (Element aModelSet : modelSet) {
            VariableElement variableElement = (VariableElement) aModelSet;
            TypeMirror mirror = variableElement.asType();
            ModelElement modelElement = new ModelElement(variableElement.getSimpleName().toString(), variableElement.getEnclosingElement().toString(), mirror.toString());
            InjectTarget injectTarget = getInjectTarget(variableElement.getEnclosingElement());
            MvpModel mvpModel = variableElement.getAnnotation(MvpModel.class);
            InjectElement injectElement = getBundle(injectTarget.mInjectElements, mvpModel.tag());
            if (injectElement == null) {
                injectElement = new InjectElement();
                injectElement.mTag = mvpModel.tag();
                injectTarget.mInjectElements.add(injectElement);
            }

            injectElement.mModelElement = modelElement;
        }
    }

    private void parseView(Set<? extends Element> viewSet) {
        for (Element aViewSet : viewSet) {
            VariableElement variableElement = (VariableElement) aViewSet;
            TypeMirror mirror = variableElement.asType();
            ViewElement viewElement = new ViewElement(variableElement.getSimpleName().toString(), variableElement.getEnclosingElement().toString(), mirror.toString());
            InjectTarget injectTarget = getInjectTarget(variableElement.getEnclosingElement());
            MvpView mvpView = variableElement.getAnnotation(MvpView.class);
            InjectElement injectElement = getBundle(injectTarget.mInjectElements, mvpView.tag());
            if (injectElement == null) {
                injectElement = new InjectElement();
                injectElement.mTag = mvpView.tag();
                injectTarget.mInjectElements.add(injectElement);
            }

            injectElement.mViewElement = viewElement;
        }
    }

    private void generate() throws IOException {

        for (InjectTarget injectTarget : mTypeElementBundleMap.values()) {

            if (injectTarget.mInjectElements.isEmpty()) {
                continue;
            }

            //指定java文件写入的位置
            String clazzName = ClazzMapUtils.getClazzName(injectTarget.target.toString());
            JavaFileObject javaFileObject = mFiler.createSourceFile(injectTarget.packageName + "." + clazzName);

            //开始写文件
            Writer writer = javaFileObject.openWriter();
            writer.write(generateSourceCode(injectTarget, clazzName));
            writer.flush();
            writer.close();
        }
    }


    private String generateSourceCode(InjectTarget injectTarget, String clazzName) {

        //包
        StringBuilder stringBuilder = new StringBuilder("package ");
        stringBuilder.append(injectTarget.packageName);
        stringBuilder.append(";");

        stringBuilder.append("public class ");
        stringBuilder.append(clazzName);
        stringBuilder.append("{");

        stringBuilder.append("public static void inject(");
        stringBuilder.append(injectTarget.target);
        stringBuilder.append(" o");
        stringBuilder.append(",");
        if (injectTarget.dependency != null) {
            stringBuilder.append(injectTarget.dependency.getCanonicalName());
            stringBuilder.append(" o1");
        } else {
            stringBuilder.append("Object o1");
        }
        stringBuilder.append("){");

        String builderSymbol = "builder";
        String componentSymbol = "component";

        String daggerComponentName = injectTarget.component.getPackage() + ".Dagger" + injectTarget.component.getSimpleName();
        String builderName = daggerComponentName + ".Builder";

        stringBuilder.append(builderName);

        stringBuilder.append(String.format(" %s = ", builderSymbol));
        stringBuilder.append(daggerComponentName);
        stringBuilder.append(".builder();\n");

        stringBuilder.append(injectTarget.component.getCanonicalName());
        stringBuilder.append(String.format(" %s = %s.", componentSymbol, builderSymbol));
        stringBuilder.append(simpleName2FunctionName(injectTarget.module.getSimpleName()));
        stringBuilder.append("(new ");
        stringBuilder.append(injectTarget.module.getCanonicalName());
        stringBuilder.append("(o))");
        if (injectTarget.dependency != null) {
            stringBuilder.append(".");
            stringBuilder.append(simpleName2FunctionName(injectTarget.dependency.getSimpleName()));
            stringBuilder.append("(o1)");
        }
        stringBuilder.append(".build();");

        stringBuilder.append(String.format("%s.inject(o);", componentSymbol));

        for (int i = 0; i < injectTarget.mInjectElements.size(); ++i) {
            InjectElement injectElement = injectTarget.mInjectElements.get(i);
            stringBuilder.append("o.");
            stringBuilder.append(injectElement.mPresenterElement.fieldName);
            stringBuilder.append("= new ");
            stringBuilder.append(injectElement.mPresenterElement.type);

            stringBuilder.append("(o.");
            stringBuilder.append(injectElement.mViewElement.fieldName);
            stringBuilder.append(",");
            if (injectElement.mModelElement == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append("o.");
                stringBuilder.append(injectElement.mModelElement.fieldName);
            }
            stringBuilder.append(");");
        }


        //结尾
        stringBuilder.append("}}");
        return stringBuilder.toString();
    }

    private InjectTarget getInjectTarget(Element encloseElement) {
        InjectTarget injectTarget = mTypeElementBundleMap.get(encloseElement);
        if (injectTarget == null) {
            injectTarget = new InjectTarget();
            mTypeElementBundleMap.put(encloseElement, injectTarget);
        }

        return injectTarget;
    }

    private static InjectElement getBundle(List<InjectElement> injectElements, String tag) {
        for (InjectElement b : injectElements) {
            if (StringUtils.equals(tag, b.mTag)) {
                return b;
            }
        }

        return null;
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
