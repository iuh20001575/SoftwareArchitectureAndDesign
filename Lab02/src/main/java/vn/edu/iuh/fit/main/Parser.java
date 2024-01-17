package vn.edu.iuh.fit.main;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Parser {
    private final static JavaParser parser = new JavaParser();

    public static String parserFile(File file) throws Exception {
        ArrayList<String> list = new ArrayList<>();

        ParseResult<CompilationUnit> parseResult = parser.parse(file);

        Optional<CompilationUnit> optional = parseResult.getResult();

        if (optional.isPresent()) {
            CompilationUnit compilationUnit = optional.get();
            Optional<ClassOrInterfaceDeclaration> clasDeclaration = compilationUnit.findFirst(ClassOrInterfaceDeclaration.class);

            if (clasDeclaration.isEmpty())
                return null;

            list.add(String.format("================== %s ==================",
                    clasDeclaration.get().getName()));

            list.addAll(getFields(compilationUnit));
            list.addAll(getMethods(compilationUnit));
        }

        return String.join("\n", list);
    }

    private static List<String> getFields(CompilationUnit unit) {
        List<FieldDeclaration> fields = unit.findAll(FieldDeclaration.class);
        ArrayList<String> list = new ArrayList<>();

        for (FieldDeclaration field : fields) {
            String string = field.toString();

            list.add('\t' + string.substring(0, string.length() - 1));
        }

        return list;
    }

    private static List<String> getMethods(CompilationUnit compilationUnit) {
        ArrayList<String> list = new ArrayList<>();

        List<MethodDeclaration> methods = compilationUnit.findAll(MethodDeclaration.class);

        for (MethodDeclaration method : methods) {
            ArrayList<String> result = new ArrayList<>();

            ArrayList<String> parameterTypes = new ArrayList<>();
            NodeList<Parameter> parameters = method.getParameters();

            for (Parameter parameter : parameters)
                parameterTypes.add(parameter.getTypeAsString());

            result.add(method.getAccessSpecifier().asString());

            if (method.isStatic())
                result.add("static");

            result.add(String.format("%s(%s):", method.getNameAsString(), String.join(", ", parameterTypes)));

            result.add(method.getTypeAsString());

            list.add("\t" + String.join(" ", result));
        }

        return list;
    }

    public static List<String> parserFolder(File folderName) throws Exception {
        ArrayList<String> list = new ArrayList<>();
        File[] files = folderName.listFiles();

        assert files != null;
        for (File file : files)
            if (file.isDirectory())
                list.addAll(parserFolder(file));
            else
                list.add(parserFile(file));


        return list;
    }

    private static String handleString(String s) {
        String[] split = s.split(" ");
        ArrayList<String> list = new ArrayList<>();

        for (String string : split) {
            String[] strings = string.split("\\.");

            list.add(strings[strings.length - 1]);
        }

        return String.join(" ", list);
    }

    public static String reflection(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Method[] methods = clazz.getDeclaredMethods();

        ArrayList<String> list = new ArrayList<>();

        list.add(String.format("================== %s ==================", clazz.getSimpleName()));

        for (Field field : fields) list.add('\t' + handleString(field.toString()));

        for (Method method : methods)
            list.add('\t' + handleString(method.toString()));

        return String.join("\n", list);
    }
}
