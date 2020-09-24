package com.hillel.company;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) throws Exception {

        TextContainer textContainerInstance = new TextContainer();
        Class <?> classInstance = textContainerInstance.getClass();

        //regionTask1
        Method method = AnnotationClass.class.getMethod("test", int.class, int.class);
        if(method.isAnnotationPresent(TestAnnotation.class)){
                TestAnnotation annotation = method.getAnnotation(TestAnnotation.class);
                System.out.println(annotation.a() + " | " + annotation.b());
            }else{
                System.out.println("Annotation 'TestAnnotation' was not found!");
            }
        //endregion

        //regionTask2
        if (classInstance.isAnnotationPresent(SaveTo.class)) {
            for (Method meth : TextContainer.class.getDeclaredMethods()) {
                if (meth.isAnnotationPresent(Saver.class)) {
                    SaveTo saveTo = classInstance.getAnnotation(SaveTo.class);
                    meth.invoke(textContainerInstance, textContainerInstance.text, saveTo.path());
                }else{
                    System.out.println("Not found method!!");
                }
            }
        }else{
            System.out.println("Annotation 'SaveTo' was not found!\n");
        }
        //endregion
        //regionTask3
        AnnotationClass annotationClass1 = new AnnotationClass();
        AnnotationClass annotationClass2 ;
        annotationClass1.setAge(10);
        annotationClass1.setName("Galya");

        SaveTo saveTo = classInstance.getAnnotation(SaveTo.class);
        //textContainerInstance.serialize(saveTo.path(), annotationClass1);
        annotationClass2 = textContainerInstance.deserialize(saveTo.path(), AnnotationClass.class);
        System.out.println(annotationClass2.getAge() + " | " + annotationClass2.getName());
        //endregion

    }
}
