package fdmc.web.servlets;

import fdmc.utils.HtmlReader;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/")
public class IndexServlet extends HttpServlet {
    private final static String INDEX_HTML_FILE_PATH =
            "D:\\Code Projects\\Java-Web-Basics-Training\\FDMC\\src\\main\\resources\\views\\FDMC.html ";

    @Inject
    private HtmlReader reader;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.println(reader.readHtmlFile(INDEX_HTML_FILE_PATH));
    }
}
