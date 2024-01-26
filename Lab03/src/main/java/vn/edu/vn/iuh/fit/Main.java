package vn.edu.vn.iuh.fit;

import com.github.javaparser.Position;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.ironsoftware.ironpdf.License;
import com.ironsoftware.ironpdf.PdfDocument;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        File projectDir = new File("C:\\Code\\WWW_IntelliJ\\WWW_OnTapCK_03");
        String packNameStart = "vn.edu.iuh.fit";

        List<List<List<String>>> list = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            ArrayList<List<String>> temp = new ArrayList<>();

            list.add(temp);
        }

        DirExplorer.FileHandler fileHandler = (level, path, file) -> {
            try {
                new VoidVisitorAdapter<>() {
                    @Override
                    public void visit(PackageDeclaration n, Object arg) {
                        super.visit(n, arg);

                        String string = checkPackage(n, packNameStart);

                        if (!string.isEmpty()) {
                            List<String> temp = new ArrayList<>();

                            temp.add(string);
                            temp.add(path);

                            list.get(0).add(temp);
                        }
                    }

                    @Override
                    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                        super.visit(n, arg);

                        boolean isInterface = n.isInterface();

                        if (!isInterface) {
                            List<String> result = checkClassName(n, path);

                            if (!result.isEmpty())
                                list.get(1).add(result);
                        }

                        List<String> result = checkComment(n, path, "@Author", "@Created-date");
                        list.get(2).add(result);

                        List<FieldDeclaration> fields = n.getFields();

                        fields.forEach(field -> {
                            List<List<String>> res = checkFieldName(field, path);

                            list.get(3).addAll(res);
                        });

                        fields.forEach(field -> list.get(4).addAll(checkConstant(field, isInterface, path)));
                    }

                    @Override
                    public void visit(MethodDeclaration n, Object arg) {
                        super.visit(n, arg);

                        List<String> methodName = checkMethodName(n, path);
                        List<String> methodComment = checkMethodComment(n, path);

                        if (!methodName.isEmpty())
                            list.get(5).add(methodName);

                        if (!methodComment.isEmpty())
                            list.get(6).add(methodComment);
                    }
                }.visit(StaticJavaParser.parse(file), null);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        };

        DirExplorer.Filter filter = (level, path, file) -> file.getName().endsWith(".java");

        new DirExplorer(fileHandler, filter).explore(projectDir);

        List<String> classNameHeaders = List.of(new String[]{"Class Name", "Error", "Path", "Position"});
        List<String> commentClassHeaders = List.of(new String[]{"Class Name", "Error", "Path", "Position"});
        List<String> fieldNameHeaders = List.of(new String[]{"Field Name", "Error", "Path", "Position"});
        List<String> methodHeaders = List.of(new String[]{"Method", "Error", "Path", "Position"});

        PdfDocument myPdf = PdfDocument.renderHtmlAsPdf("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Document</title>\n" +
                "\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: sans-serif;\n" +
                "        }\n" +
                "\n" +
                "        h1 {\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        table {\n" +
                "            width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        table,\n" +
                "        thead,\n" +
                "        tr,\n" +
                "        th,\n" +
                "        td {\n" +
                "            border-collapse: collapse;\n" +
                "        }\n" +
                "\n" +
                "        tbody tr:nth-child(odd) {\n" +
                "            background-color: #f2f2f2;\n" +
                "        }\n" +
                "\n" +
                "        tr:not(:last-child) {\n" +
                "            border-bottom: 1px solid rgb(222, 226, 230);\n" +
                "        }\n" +
                "\n" +
                "        th,\n" +
                "        td {\n" +
                "            padding: 8px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <main>\n" +
                "        <h1>\n" +
                "            Report\n" +
                "        </h1>\n" +
                "\n" +
                "        <h2>Package</h2>\n" +
                "        <p>Must follow the <strong><i>\"vn.edu.iuh.fit\"</i></strong></p>\n" +
                "\n" +
                HTML.getPackage(list.get(0)) +
                "\n" +
                "        <h2>Class Name</h2>\n" +
                "        <p><strong>Rule</strong>: Is a noun or noun phrase and must start in capital</p>\n" +
                "\n" +
                HTML.getTable(classNameHeaders, list.get(1)) +
                "        <h2>Comment of class</h2>\n" +
                "        <p><strong>Rule</strong>: Each class must have a descriptive comment for the class. The comment must include the created-date and author</p>\n" +
                "\n" +
                HTML.getTable(commentClassHeaders, list.get(2)) +
                "        <h2>Field Name</h2>\n" +
                "        <p><strong>Rule</strong>: Fields in classes must be nouns or noun phrases and must begin with a lowercase letter</p>\n" +
                "\n" +
                HTML.getTable(fieldNameHeaders, list.get(3)) +
                "        <h2>Constant</h2>\n" +
                "        <p><strong>Rule</strong>: Must be in uppercase letters and must be in an interface</p>\n" +
                "\n" +
                HTML.getTable(fieldNameHeaders, list.get(4)) +
                "        <h2>Method Name</h2>\n" +
                "        <p><strong>Rule</strong>: Must begin with a verb and must be lower case</p>\n" +
                "\n" +
                HTML.getTable(methodHeaders, list.get(5)) +
                "        <h2>Method Comment</h2>\n" +
                "        <p><strong>Rule</strong>: There must be a note describing what the method does</p>\n" +
                "\n" +
                HTML.getTable(methodHeaders, list.get(6)) +
                "    </main>\n" +
                "</body>\n" +
                "\n" +
                "</html>");

        License.setLicenseKey("IRONSUITE.THAOANHHAA1.GMAIL.COM.15873-D007478200-FTECTQ2UMY6WCB-DC2SR24QV6Q5-XGHTI24NFNDZ-YTZUXV42R5V3-2XRPDE4FR6DY-DRE36LBXYWOC-6LQ6Y4-TJJJJKPLSWGLUA-DEPLOYMENT.TRIAL-IOWBUF.TRIAL.EXPIRES.25.FEB.2024");

        String path = "C:\\Users\\HaAnhThao\\OneDrive - Industrial University of HoChiMinh City\\FolderMe\\HK3-2023\\KT\\Project\\Lab03\\html_saved.pdf";

        myPdf.saveAs(path);

        File file = new File(path);
        Desktop desktop = Desktop.getDesktop();
        if(file.exists())
            desktop.open(file);
    }

    private static String checkPackage(PackageDeclaration n, String packageStart) {
        String packageName = n.getNameAsString();

        if (!packageName.startsWith(packageStart))
            return packageName;

        return "";
    }

    private static List<String> checkClassName(ClassOrInterfaceDeclaration n, String path) {
        String name = n.getNameAsString();
        List<String> words = splitName(name);
        boolean nounOrNounPhrase = NLP.isNounOrNounPhrase(String.join(" ", words));
        Optional<Position> oBeginPosition = n.getBegin();
        Optional<Position> oEndPosition = n.getEnd();

        List<String> result = initialTable(name, oBeginPosition, oEndPosition);
        result.add(path);

        if (!nounOrNounPhrase)
            result.add("No nouns or phrases");

        char firstCharacter = name.charAt(0);

        if (firstCharacter < 'A' || firstCharacter > 'Z')
            result.add("Not starting with capital letters");

        if (result.size() == 4)
            return new ArrayList<>();

        return result;
    }

    private static List<List<String>> checkFieldName(FieldDeclaration n, String path) {
        NodeList<VariableDeclarator> variables = n.getVariables();

        List<List<String>> list = new ArrayList<>();

        variables.forEach(variable -> {
            String name = variable.getNameAsString();

            List<String> words = splitName(name);

            boolean nounOrNounPhrase = NLP.isNounOrNounPhrase(String.join(" ", words));

            NLP.isNounOrNounPhrase(String.join(" ", words));

            Optional<Position> oBeginPosition = n.getBegin();
            Optional<Position> oEndPosition = n.getEnd();

            List<String> result = initialTable(name, oBeginPosition, oEndPosition);
            result.add(path);

            if (!nounOrNounPhrase)
                result.add("Not a noun or noun phrase");

            if (name.charAt(0) < 'a' || name.charAt(0) > 'z')
                result.add("Must begin with a lowercase letter");

            if (result.size() > 4)
                list.add(result);
        });

        return list;
    }

    private static List<String> checkComment(ClassOrInterfaceDeclaration n, String path, String ...criteria) {
        String name = n.getNameAsString();
        Optional<Comment> oComment = n.getComment();

        Optional<Position> oBeginPosition = n.getBegin();
        Optional<Position> oEndPosition = n.getEnd();

        List<String> result = initialTable(name, oBeginPosition, oEndPosition);
        result.add(path);

        if (oComment.isEmpty())
            result.add("There are no comments");
        else {
            Comment comment = oComment.get();
            String sComment = comment.asString();

            for (String cr : criteria)
                if (!sComment.contains(cr))
                    result.add(String.format("Comment without %s", cr));
        }

        return result;
    }

    private static List<List<String>> checkConstant(FieldDeclaration n, boolean isInterface, String path) {
        NodeList<VariableDeclarator> variables = n.getVariables();
        boolean isFinal = n.isFinal();

        if (!isFinal) return new ArrayList<>();

        Pattern pattern = Pattern.compile("[A-Z_]*");
        Optional<Position> oBeginPosition = n.getBegin();
        Optional<Position> oEndPosition = n.getEnd();

        List<List<String>> list = new ArrayList<>();
        List<String> item;

        for (VariableDeclarator variable : variables) {
            String name = variable.getNameAsString();
            item = initialTable(name, oBeginPosition, oEndPosition);
            item.add(path);

            if (!pattern.matcher(name).matches())
                item.add("Do not capitalize");

            if (!isInterface)
                item.add("Not in the interface");

            list.add(item);
        }

        return list;
    }

    private static List<String> checkMethodName(MethodDeclaration n, String path) {
        String name = n.getNameAsString();
        Optional<Position> oBeginPosition = n.getBegin();
        Optional<Position> oEndPosition = n.getEnd();

        List<String> result = initialTable(name, oBeginPosition, oEndPosition);
        result.add(path);

        List<String> strings = splitName(name);
        String firstWord = strings.get(0);

        if (!NLP.isVerb(firstWord))
            result.add("Don't start with a verb");

        if (!Pattern.matches("[a-z]*", firstWord))
            result.add("Must begin with a lowercase letter");

        return result;
    }

    private static List<String> checkMethodComment(MethodDeclaration n, String path) {
        String name = n.getNameAsString();
        Optional<Position> oBeginPosition = n.getBegin();
        Optional<Position> oEndPosition = n.getEnd();

        List<String> result = initialTable(name, oBeginPosition, oEndPosition);
        result.add(path);

        if (Pattern.matches("^(get|set|is).*", name))
            return new ArrayList<>();

        String[] exclude = new String[] {"equals", "hashCode", "toString"};

        for (String string : exclude) 
           if (string.equals(name))
               return new ArrayList<>();

        Optional<Comment> oComment = n.getComment();

        if (oComment.isEmpty())
            result.add("There are no comments");

        return result;
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

    private static List<String> initialTable(String name, Optional<Position> oBeginPosition, Optional<Position> oEndPosition) {
        if (oBeginPosition.isEmpty() || oEndPosition.isEmpty())
            return new ArrayList<>();

        List<String> result = new ArrayList<>();

        Position beginPosition = oBeginPosition.get();
        Position endPosition = oEndPosition.get();

        result.add(name);
        result.add(String.valueOf(beginPosition.line));
        result.add(String.valueOf(endPosition.line));

        return  result;
    }
}