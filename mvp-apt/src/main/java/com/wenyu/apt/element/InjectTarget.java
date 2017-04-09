package com.wenyu.apt.element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chan on 17/4/6.
 */

public class InjectTarget {
    public Clazz component;
    public Clazz module;
    public Clazz dependency;
    public String target;
    public String packageName;
    public List<InjectElement> mInjectElements = new ArrayList<>();
}
