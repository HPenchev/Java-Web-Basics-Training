package fdmc.web.servlets;

import fdmc.domain.entities.Cat;
import fdmc.utils.HtmlReader;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cats/create")
public class CatCreateServlet extends HttpServlet {
    private static final String CREATE_CAT_HTML_PATH =
            "D:\\Code Projects\\Java-Web-Basics-Training\\FDMC\\src\\main\\resources\\views\\cat-create.html";

    private final HtmlReader htmlReader;

    @Inject
    public CatCreateServlet(HtmlReader htmlReader) {
        this.htmlReader = htmlReader;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.println(htmlReader.readHtmlFile(CREATE_CAT_HTML_PATH));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object sessionCats = session.getAttribute("cats");

        List<Cat> cats = null;

        if (sessionCats == null) {
            cats = new ArrayList<>();
            session.setAttribute("cats", cats);
        } else {
            cats = (List<Cat>) sessionCats;
        }

        String name = req.getParameter("name");

        cats.add(new Cat(name,
                req.getParameter("breed"),
                req.getParameter("colour"),
                Integer.parseInt(req.getParameter("age"))));

        resp.sendRedirect("/cats/profile?catName=" + name);
    }
}
