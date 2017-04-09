package com.wenyu.apt.impl;

import com.google.auto.service.AutoService;
import com.wenyu.apt.annotations.MvpInject;
import com.wenyu.apt.annotations.MvpModel;
import com.wenyu.apt.annotations.MvpPresenter;
import com.wenyu.apt.annotations.MvpView;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class MvpProcessor extends AbstractProcessor {

    /**
     * 用于写java文件
     */
    private Filer mFiler;
    /**
     * 可以理解为log
     */
    private Messager mMessager;
    /**
     * 注解检查器，用于判断被注解的field不是private的
     */
    private AnnotationChecker mAnnotationChecker;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
        mAnnotationChecker = new AnnotationChecker(mMessager);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        //找到被注解的field
        Set<? extends Element> presenterSet = roundEnv.getElementsAnnotatedWith(MvpPresenter.class);
        Set<? extends Element> modelSet = roundEnv.getElementsAnnotatedWith(MvpModel.class);
        Set<? extends Element> viewSet = roundEnv.getElementsAnnotatedWith(MvpView.class);
        Set<? extends Element> mvpInject = roundEnv.getElementsAnnotatedWith(MvpInject.class);

        //只处理一次
        if (presenterSet.isEmpty() && modelSet.isEmpty() && viewSet.isEmpty() && mvpInject.isEmpty()) {
            return true;
        }

        if (!mAnnotationChecker.checkAnnotation(presenterSet, modelSet, viewSet)) {
            return true;
        }

        CodeGenerator codeGenerator = new CodeGenerator(mFiler, mMessager);
        try {
            codeGenerator.generate(presenterSet, modelSet, viewSet, mvpInject);
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {

        //添加支持的注解类型 我们支持JoinView
        Set<String> set = new HashSet<>();
        set.add(MvpPresenter.class.getCanonicalName());
        set.add(MvpView.class.getCanonicalName());
        set.add(MvpModel.class.getCanonicalName());
        set.add(MvpInject.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }
}
