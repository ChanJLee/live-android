package com.wenyu.apt;

import com.google.auto.service.AutoService;
import com.wenyu.apt.annotations.Presenter;

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
import javax.lang.model.element.VariableElement;

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
        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(Presenter.class);

        if (set != null) {

            CodeGenerator codeGenerator = new CodeGenerator(mFiler, mMessager);
            for (Element element : set) {
                //先检查权限
                if (!mAnnotationChecker.checkAnnotation(element)) {
                    return false;
                }
                //把备注解的field添加到生成器里，准备用来生成代码
                codeGenerator.add((VariableElement) element);
            }
            //开始生成代码
            codeGenerator.generate();
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {

        //添加支持的注解类型 我们支持JoinView
        Set<String> set = new HashSet<>();
        set.add(Presenter.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }
}
