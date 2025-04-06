package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/")
public class DirServlet extends HttpServlet {

    private String defaultFolder = "C:/";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getParameter("path");

        printDirectory(req, path == null ? defaultFolder : path);

        req.setAttribute("name", "Directories");
        req.getRequestDispatcher("explorer.jsp").forward(req, resp);
    }

    private void printDirectory(HttpServletRequest req, String path) {
        StringBuilder attrFiles = new StringBuilder();
        StringBuilder attrFolders = new StringBuilder();

        if (path.contains("/"))
            addDirectory(attrFolders, path.substring(0, path.lastIndexOf('/')), "return");

        File[] files = new File(path).listFiles();

        if(files == null || files.length == 0)
            return;

        for (File file : files) {
            if (file.isDirectory())
                addDirectory(attrFolders, path + "/" + file.getName(), file.getName());

            else
                addFile(attrFiles, file.getName());
        }

        req.setAttribute("folders", attrFolders);
        req.setAttribute("files", attrFiles);
    }


    private void addDirectory(StringBuilder attrFiles, String path, String text) {
        attrFiles.append("<li><a href=\"?path=")
                .append(path)
                .append("\">")
                .append(text)
                .append("</a></li>");
    }

    private void addFile(StringBuilder attrFiles, String text) {
        attrFiles.append("<li><a>")
                .append(text)
                .append("</a></li>");
    }
}