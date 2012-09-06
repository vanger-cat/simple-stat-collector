package pro.kristy.statisticcollector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: vanger
 * Date: 03.09.12
 * Time: 9:58
 */
public class StatisticsCollector extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader in = request.getReader();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss SSS Z");
        String fileName = "statistic_" + simpleDateFormat.format(new Date()) + ".txt";

        String rootFolder;
        rootFolder = "/home/user1/stat-colector/";
//        rootFolder  = "/Users/vanger/";
        String filePath = rootFolder + fileName;
        PrintWriter writer = new PrintWriter(new FileOutputStream(filePath, true));


        String line;
        while ((line = in.readLine()) != null) {
            writer.write(line);
            writer.write("\n");
        }

        writer.close();

        response.getWriter().println(filePath);
        response.getWriter().flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");

        if (name == null) {
            name = "anonymus";
        }

        PrintWriter writer = response.getWriter();
        writer.write("Hello, " + name + ". Have a nice day!");
        writer.close();
    }
}
