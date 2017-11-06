package com.xinghen.udf.core.utils;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClassUtil {

    private ClassUtil() {
    }

    /**
     * 获取指定包下某个class的所有非抽象子类
     *
     * @param parentClass 父类
     * @param packagePath 指定包路径
     * @param <E>         泛型类
     * @return 子类列表
     * @throws ClassNotFoundException
     */
    public static <E> List<Class<E>> getSubClasses(final Class<E> parentClass, final String packagePath) throws ClassNotFoundException {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(parentClass));
        Set<BeanDefinition> components = provider.findCandidateComponents(packagePath);
        List<Class<E>> subClasses = new ArrayList<>();
        for (BeanDefinition component : components) {
            Class<E> clazz = (Class<E>) Class.forName(component.getBeanClassName());
            if (Modifier.isAbstract(clazz.getModifiers())) {
                continue;
            }
            subClasses.add(clazz);
        }
        return subClasses;
    }

}
