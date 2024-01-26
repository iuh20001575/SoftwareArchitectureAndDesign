package vn.edu.vn.iuh.fit;

import java.util.List;

public class HTML {
    public static String getPackage(List<List<String>> list) {
        if (list.isEmpty()) return "";

        StringBuilder s = new StringBuilder();

        for (List<String> strings : list)
            s.append(String.format("<tr><td>%s</td><td>%s</td></tr>", strings.get(0), strings.get(1)));

        return "<table><thead><tr><th>Package</th><th>Path</th></tr></thead><tbody>" +
                s + "</tbody></table>";
    }

    public static String getTable(List<String> headers, List<List<String>> list) {
        if (list.isEmpty()) return "";

        StringBuilder tableHeader = new StringBuilder();
        StringBuilder tableBody = new StringBuilder();

        for (String header : headers)
            tableHeader.append(String.format("<th>%s</th>", header));

        for (List<String> strings : list) {
            int length = strings.size();
            String name = strings.get(0);
            String path = strings.get(3);
            String start = strings.get(1);

            for (int i = 4; i < length; i++)
                tableBody.append(String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>",
                        name, strings.get(i), path, start));
        }

        return "        <table>\n" +
                "            <thead>\n" +
                "                <tr>\n" +
                tableHeader +
                "                </tr>\n" +
                "            </thead>\n" +
                "            <tbody>\n" +
                tableBody +
                "            </tbody>\n" +
                "        </table>\n";
    }
}
