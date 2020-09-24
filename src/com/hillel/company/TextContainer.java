package com.hillel.company;

import java.io.*;
import java.lang.reflect.Field;

@SaveTo(path = "/home/dbotvin/Desktop/testFile.txt")
public class TextContainer {
    public String text = "Something";

    @Saver
    public void save(String text, String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(text);
        writer.close();
        System.out.println("Text successfully wrote into file");
    }

    public String read(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String text = reader.readLine();
        reader.close();
        System.out.println("Text successfully read into file");
        return text;
    }

    public void serialize(String path, Object object) throws IllegalAccessException, IOException {
        StringBuilder stringBuilder = new StringBuilder();
        Class<?> cl = object.getClass();
        Field[] fields = cl.getDeclaredFields();
        for(Field field : fields){
            if(field.isAnnotationPresent(SaveField.class)) {
                field.setAccessible(true);
                stringBuilder.append(field.getName() + ":");
                if (field.getType() == String.class) {
                    stringBuilder.append((String)field.get(object));
                } else if (field.getType() == int.class) {
                    stringBuilder.append((field.getInt(object)));
                }
                stringBuilder.append(";");
            }else{
                System.out.println("Fields not found!");
            }
        }

        String text = stringBuilder.toString();
        save(text, path);
    }


    public <T> T deserialize(String path, Class<T> cls) throws Exception {
        String text = read(path);
        Object obj =(T)cls.newInstance();
        String[] strings = text.split(";");
        for(String string : strings){
            String[] st = string.split(":");

            String name = st[0];
            String value = st[1];
            Field field = cls.getDeclaredField(name);
            field.setAccessible(true);
            if(field.isAnnotationPresent(SaveField.class)){
                if(field.getType() == int.class){
                    field.setInt(obj, Integer.parseInt(value));
                }else if(field.getType() == String.class){
                    field.set(obj, value);
                }
            }
        }
        return (T)obj;
    }
}
