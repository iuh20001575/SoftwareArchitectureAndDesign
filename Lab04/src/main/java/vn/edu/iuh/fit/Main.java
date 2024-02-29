package vn.edu.iuh.fit;

import jdepend.xmlui.JDepend;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String projectDirectory = "C:\\Code\\WWW_IntelliJ";
        String projectPrefix = "org";

        JDepend jDepend = new JDepend(new PrintWriter("report.xml"));

        jDepend.addDirectory(projectDirectory);

        jDepend.analyze();

        File file = new File("report.xml");
        String reportPath = file.getAbsoluteFile().toString();
        String jdependUiPath = reportPath.replace("report.xml", "jdepend-ui");

        Process processInstallLib = new ProcessBuilder("cmd", "/c", "npm i")
                .directory(new File(jdependUiPath))
                .redirectErrorStream(true)
                .start();
        processInstallLib.waitFor();

        Process processCreateIndex = new ProcessBuilder("cmd", "/c", String.format("npm run jdepend-ui \"%s\" %s", reportPath, projectPrefix))
                .directory(new File(jdependUiPath))
                .start();
        processCreateIndex.waitFor();

        new ProcessBuilder("cmd", "/c", String.format("start \"\" \"%s\\index.html\"", jdependUiPath)).start();
    }
}