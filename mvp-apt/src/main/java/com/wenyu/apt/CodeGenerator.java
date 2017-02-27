package com.wenyu.apt;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    private Map<String, List<VariableElement>> mVariableElementMap = new HashMap<>();
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

    public CodeGenerator(Filer filer, Messager messager) {
        mFiler = filer;
        mMessager = messager;
    }

    public void add(VariableElement element) {
        List<VariableElement> variableElements = mVariableElementMap.get(element.getEnclosingElement().toString());
        if (variableElements == null) {
            variableElements = new ArrayList<>();
            //获得被注解的class的名称作为键
            mVariableElementMap.put(element.getEnclosingElement().toString(), variableElements);
        }

        //当前class下备注解的field
        variableElements.add(element);
    }

    public void generate() {

        if (mVariableElementMap.isEmpty()) {
            return;
        }

        init();

        try {
            for (Map.Entry<String, List<VariableElement>> entry : mVariableElementMap.entrySet()) {
                //把.都换成$
                String clazzName = "YellowPeach$" + entry.getKey().replaceAll("\\.", "\\$");
                //指定java文件写入的位置
                JavaFileObject javaFileObject = mFiler.createSourceFile(mPackage + "." + clazzName);
                mMessager.printMessage(Diagnostic.Kind.NOTE, "在" + mPackage + "." + clazzName + "生成代码");

                //开始写文件
                Writer writer = javaFileObject.openWriter();
                writer.write(generateSourceCode(entry, mPackage, clazzName));
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() {

        //先获得包名
        Iterator<Map.Entry<String, List<VariableElement>>> iterator = mVariableElementMap.entrySet().iterator();
        Map.Entry<String, List<VariableElement>> elementEntry = iterator.next();

        VariableElement variableElement = elementEntry.getValue().get(0);

        Element element = variableElement.getEnclosingElement();
        while (element != null && element.getEnclosingElement() != null) {
            mPackage = element.toString();
            element = element.getEnclosingElement();
        }

        mPackage = mPackage.substring(0, mPackage.lastIndexOf("."));
    }

    private static String generateSourceCode(Map.Entry<String, List<VariableElement>> entry, String packageName, String clazzName) {

        //包
        StringBuilder stringBuilder = new StringBuilder("package ");
        stringBuilder.append(packageName);
        stringBuilder.append(";\n");

        //import
        stringBuilder.append("import android.view.View;\n" +
                "\n" +
                "import com.chan.lib.Peach;\n" +
                "\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.List;");

        stringBuilder.append("public class ");
        stringBuilder.append(clazzName);
        stringBuilder.append(" implements Peach {\n");

        //成员变量
        stringBuilder.append("private List<View> mViews = new ArrayList<>();\n");

        //构造函数 参数为被注解的class
        stringBuilder.append("public ");
        stringBuilder.append(clazzName);
        stringBuilder.append("(");
        stringBuilder.append(entry.getKey());
        stringBuilder.append(" o){");

        for (VariableElement item : entry.getValue()) {
            stringBuilder.append("mViews.add(");
            stringBuilder.append("o.");
            //返回field的名字
            stringBuilder.append(item.getSimpleName());
            stringBuilder.append(");");
        }

        stringBuilder.append("}");

        //override的内容
        stringBuilder.append(" @Override\n" +
                "    public void setVisible(View... target) {\n" +
                "\n" +
                "        for (View v : mViews) {\n" +
                "            v.setVisibility(View.GONE);\n" +
                "        }\n" +
                "\n" +
                "        for (int i = 0; i < target.length; ++i) {\n" +
                "            final int index = mViews.indexOf(target[i]);\n" +
                "            if (index != -1) {\n" +
                "                mViews.get(index).setVisibility(View.VISIBLE);\n" +
                "            }\n" +
                "        }\n" +
                "    }");

        //结尾
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
