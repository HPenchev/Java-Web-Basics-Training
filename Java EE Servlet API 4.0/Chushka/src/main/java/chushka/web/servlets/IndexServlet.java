package chushka.web.servlets;

import chushka.service.ProductService;
import chushka.service.ProductServiceImpl;
import chushka.util.HtmlReader;
import chushka.util.ModelMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class IndexServlet extends HttpServlet {
    private static final String INDEX_HTML_FILE_PATH =
            "D:\\Code Projects\\Java-Web-Basics-Training\\Java-Web-Basics-Training\\" +
                    "Java EE Servlet API 4.0\\Chushka\\src\\main\\resources\\views\\index.html";

//    @Inject
//    private  ProductService productService;

    private final HtmlReader htmlReader;

    //private final ModelMapper modelMapper;

    @Inject
    public IndexServlet(HtmlReader htmlReader){
            //, ModelMapper modelMapper) {
        //this.productService = productService;
        this.htmlReader = htmlReader;
        //this.modelMapper = modelMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String htmlFileContent = this.htmlReader.readHtmlFile(INDEX_HTML_FILE_PATH);
        resp.getWriter().println("test");
    }
}
