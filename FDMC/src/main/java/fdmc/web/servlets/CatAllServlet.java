package fdmc.web.servlets;

import fdmc.domain.entities.Cat;
import fdmc.utils.HtmlReader;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cats/all")
public class CatAllServlet extends HttpServlet {
    private static final String ALL_CATS_HTML_PATH =
            "D:\\Code Projects\\Java-Web-Basics-Training\\FDMC\\src\\main\\resources\\views\\all-cats.html";

    @Inject
    private HtmlReader htmlReader;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Cat> cats = null;
        Object sessionCats = req.getSession().getAttribute("cats");

        if (sessionCats != null) {
            cats = (List<Cat>) sessionCats;
        }

        String catsList = null;

        if (cats == null || cats.size() == 0) {
            catsList = "There are no cats. <a href = \"/cats/create\">Create some.</a></br>";
        } else {
            StringBuilder builder = new StringBuilder();

            for (Cat cat : cats) {
                String name = cat.getName();
                builder.append("<a href = \"/cats/profile?catName=" + name + "\">" + name + "</a></br>");
            }

            catsList = builder.toString();
        }

        resp.getWriter().
                println(htmlReader.readHtmlFile(ALL_CATS_HTML_PATH).
                        replace("{{All Cats}}", catsList + "/br"));
    }
}
