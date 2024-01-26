package vn.edu.vn.iuh.fit;

import com.github.javaparser.Position;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        File projectDir = new File("C:\\Code\\WWW_IntelliJ\\WWW_OnTapCK_03");

        DirExplorer.FileHandler fileHandler = (level, path, file) -> {
            try {
                new VoidVisitorAdapter<>() {
                    @Override
                    public void visit(PackageDeclaration n, Object arg) {
                        super.visit(n, arg);
//                        checkPackage(n, "vn.edu.iuh.fit.backend");
                    }

                    @Override
                    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                        super.visit(n, arg);

                        boolean isInterface = n.isInterface();

//                        if (!isInterface)
//                            checkClassName(n);

//                        checkComment(n, "@Author", "@Created-date");

                        List<FieldDeclaration> fields = n.getFields();

//                        fields.forEach(Main::checkFieldName);

//                        fields.forEach(field -> checkConstant(field, isInterface));
                    }

                    @Override
                    public void visit(MethodDeclaration n, Object arg) {
                        super.visit(n, arg);

//                        checkMethodName(n);

                        checkMethodComment(n);
                    }
                }.visit(StaticJavaParser.parse(file), null);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        };

        DirExplorer.Filter filter = (level, path, file) -> file.getName().endsWith(".java");

        new DirExplorer(fileHandler, filter).explore(projectDir);
    }

    private static void checkPackage(PackageDeclaration n, String packageStart) {
        String packageName = n.getNameAsString();

        if (!packageName.startsWith(packageStart))
            System.out.printf("Package %s isn't start with %s\n", packageName, packageStart);
    }

    private static void checkClassName(ClassOrInterfaceDeclaration n) {
        String name = n.getNameAsString();
        List<String> words = splitName(name);
        boolean nounOrNounPhrase = NLP.isNounOrNounPhrase(String.join(" ", words));
        Optional<Position> oPosition = n.getBegin();

        if (oPosition.isEmpty()) return;

        Position position = oPosition.get();

        if (!nounOrNounPhrase)
            System.out.printf("Class %s has a name that is not a noun or noun phrase, position start: %d\n", name, position.line);

        char firstCharacter = name.charAt(0);

        if (firstCharacter < 'A' || firstCharacter > 'Z')
            System.out.printf("Class %s does not begin with a capital letter\n, position start: %d", name, position.line);
    }

    private static void checkFieldName(FieldDeclaration n) {
        NodeList<VariableDeclarator> variables = n.getVariables();

        variables.forEach(variable -> {
            String name = variable.getNameAsString();

            List<String> words = splitName(name);

            boolean nounOrNounPhrase = NLP.isNounOrNounPhrase(String.join(" ", words));

            NLP.isNounOrNounPhrase(String.join(" ", words));

            if (!nounOrNounPhrase)
                System.out.printf("Field %s is not a noun or noun phrase\n", name);

            if (name.charAt(0) < 'a' || name.charAt(0) > 'z')
                System.out.printf("Field %s does not begin with a lowercase letter\n", name);
        });
    }

    private static void checkComment(ClassOrInterfaceDeclaration n, String ...criteria) {
        String name = n.getNameAsString();
        Optional<Comment> oComment = n.getComment();

        if (oComment.isEmpty()) {
            System.out.printf("Class %s has no descriptive comments for the class\n", name);
            return;
        }

        Comment comment = oComment.get();
        String sComment = comment.asString();

        for (String cr : criteria)
            if (!sComment.contains(cr))
                System.out.printf("Class %s's comment does not have a %s\n", name, cr);
    }

    private static void checkConstant(FieldDeclaration n, boolean isInterface) {
        NodeList<VariableDeclarator> variables = n.getVariables();
        boolean isFinal = n.isFinal();

        if (!isFinal) return;

        Pattern pattern = Pattern.compile("[A-Z_]*");

        variables.forEach(variable -> {
            String name = variable.getNameAsString();

            if (!pattern.matcher(name).matches())
                System.out.printf("Constant %s does not capitalize\n", name);

            if (!isInterface)
                System.out.printf("Constant %s is not in the interface\n", name);
        });
    }

    private static void checkMethodName(MethodDeclaration n) {
        String name = n.getNameAsString();

        List<String> strings = splitName(name);
        String firstWord = strings.get(0);

        if (!NLP.isVerb(firstWord))
            System.out.printf("Method %s starts not a verb\n", name);

        if (!Pattern.matches("[a-z]*", firstWord))
            System.out.printf("Method %s with the beginning is not a normal word\n", name);
    }

    private static void checkMethodComment(MethodDeclaration n) {
        String name = n.getNameAsString();

        if (Pattern.matches("^(get|set|is).*", name))
            return;

        String[] exclude = new String[] {"equals", "hashCode", "toString"};

        for (String string : exclude) 
           if (string.equals(name))
               return;

        Optional<Comment> oComment = n.getComment();

        if (oComment.isEmpty())
            System.out.printf("Method %s has no notes to describe the job of the method\n", name);
    }

    private static List<String> splitName(String name) {
        List<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();
        int length = name.length();

        for (int i = 0; i < length; i++) {
            char ch = name.charAt(i);

            if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
                if (ch <= 'Z' && !word.isEmpty()) {
                    words.add(word.toString());
                    word = new StringBuilder();
                }
                word.append(ch);
            }
        }

        words.add(word.toString());

        return words;
    }
}