package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/")
public class DirServlet extends HttpServlet {

    private String defaultFolder = "C:/";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getParameter("path");
        path = path == null ? defaultFolder : path;


        Path spath = Paths.get(path);
        File file = spath.toFile();

        if (file.isFile()) {
            resp.setHeader("Content-Type", "application/octet-stream");
            resp.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));

            FileInputStream inStream = new FileInputStream(file);
            OutputStream outStream = resp.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            inStream.close();
            outStream.close();
        }

        else {
            printDirectory(req, path == null ? defaultFolder : path);

            req.setAttribute("now", new Date());
            req.setAttribute("name", "Directories");
            req.getRequestDispatcher("explorer.jsp").forward(req, resp);
        }
    }

    private void printDirectory(HttpServletRequest req, String path) {
        StringBuilder attrFiles = new StringBuilder();
        StringBuilder attrFolders = new StringBuilder();

        if (path.contains("/"))
            addDirectory(attrFolders, path.substring(0, path.lastIndexOf('/')), "return", 0, 0);

        File[] files = new File(path).listFiles();

        if (files == null || files.length == 0)
            return;

        for (File file : files) {
            if (file.isDirectory())
                addDirectory(attrFolders, path + "/" + file.getName(), file.getName(), file.lastModified(), file.length());

            else
                addFile(attrFiles, file.getName(), file.lastModified(), file.length(), path);
        }

        req.setAttribute("folders", attrFolders);
        req.setAttribute("files", attrFiles);
    }


    private void addDirectory(StringBuilder attrFiles, String path, String text, long date, long length) {
        attrFiles.append("<tr><td><img src=\"https://icons.iconarchive.com/icons/hopstarter/sleek-xp-basic/16/Folder-icon.png\"><a href=\"?path=")
                .append(path)
                .append("\">")
                .append(text)
                .append("</a></td><td>")
                .append(length)
                .append(" Bytes")
                .append("</td><td>")
                .append(new SimpleDateFormat("MM.dd.yyyy HH:mm:ss").format(new Date(date)))
                .append("</td>");
    }

    private void addFile(StringBuilder attrFiles, String text, long date, long length, String path) {
        attrFiles.append("<tr><td><a href=\"?path=")
                .append(path + "/" + text)
                .append("\">")
                .append(text)
                .append("</a></td><td>")
                .append(length)
                .append(" Bytes")
                .append("</td><td>")
                .append(new SimpleDateFormat("MM.dd.yyyy HH:mm:ss").format(new Date(date)))
                .append("</td></tr>");
    }
}