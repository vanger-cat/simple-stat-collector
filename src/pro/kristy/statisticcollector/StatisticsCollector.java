package pro.kristy.statisticcollector;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: vanger
 * Date: 03.09.12
 * Time: 9:58
 */

public class StatisticsCollector extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

            String fieldName;
            String deviceId = "form field is not read yet";

            // в первом цикле определить deviceID
            for (FileItem item : items) {
                if (item.isFormField()) {
                    fieldName = item.getFieldName();
                    if (fieldName.equals("id")) {
                        deviceId = item.getString();
                    }
                }
            }

            String baseFolder;
            baseFolder = "/Users/vanger/stat-colector";

            for (FileItem item : items) {
                if (!item.isFormField()) {
                    fieldName = item.getFieldName();
                    InputStream filecontent = item.getInputStream();

                    String rootFolder = baseFolder + deviceId + "/";

                    boolean isCreated = new File(rootFolder).mkdir();
                    System.out.println("created folder = " + rootFolder + " (" + isCreated + ")");

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-SSS");
                    String fileName = deviceId + " - " + fieldName + "-" + simpleDateFormat.format(new Date()) + ".txt";
                    String filePath = rootFolder + fileName;

                    PrintWriter writer = new PrintWriter(new FileOutputStream(filePath, true));

                    BufferedReader reader = new BufferedReader(new InputStreamReader(filecontent));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.write(line);
                        writer.write("\n");
                    }

                    writer.close();
                    response.getWriter().println(filePath);
                    response.getWriter().flush();
                }

            }
        } catch (FileUploadException e) {
            throw new ServletException("Cannot parse multipart request.", e);
        }

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