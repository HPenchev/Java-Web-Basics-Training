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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/cats/profile")
public class CatProfileServlet extends HttpServlet {
    private static final String CAT_PROFILE_PATH =
            "D:\\Code Projects\\Java-Web-Basics-Training\\FDMC\\src\\main\\resources\\views\\cat-profile.html";


    private static final String NO_CAT_PATH =
            "D:\\Code Projects\\Java-Web-Basics-Training\\FDMC\\src\\main\\resources\\views\\no-cat.html";

    private HtmlReader htmlReader;

    @Inject
    public CatProfileServlet(HtmlReader htmlReader) {
        this.htmlReader = htmlReader;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = getUrlParameters(req.getQueryString()).get("catName");

        Cat cat = null;

        Object cats = req.getSession().getAttribute("cats");
        if (cats != null) {
            cat = ((Collection<Cat>)cats)
                    .stream().filter(c -> c.getName().equals(name)).findFirst().orElse(null);
        }



        String response = null;

        if (cat == null) {
            response = htmlReader.readHtmlFile(NO_CAT_PATH);
        } else {
            response = htmlReader.readHtmlFile(CAT_PROFILE_PATH)
                    .replace("{{catBreed}}", cat.getBreed())
                    .replace("{{catColour}}", cat.getColour())
                    .replace("{{catAge}}", cat.getAge().toString());
        }

        response = response.replace("{{catName}}", name);

        resp.getWriter().println(response);
    }

    private static Map<String, String> getUrlParameters(String queryString) {
        Map<String, String> response = new HashMap<>();

        if (queryString != null) {
            String[] parameters = queryString.split("&");
            for (int i = 0; i < parameters.length; i++) {
                String[] parameter = parameters[i].split("=");

                response.put(parameter[0], parameter[1]);
            }
        }

        return response;
    }
}
