package servlet;

import dao.ImageDao;
import exception.DaoException;
import jdbc.JdbcDaoFactory;
import model.Image;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DeleteImagePageServlet", urlPatterns = "/deleteImage")
public class DeleteImagePageServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(DeleteImagePageServlet.class);
    private ImageDao imageDao = (ImageDao) JdbcDaoFactory.getInstance().getDao(ImageDao.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long imageId = 0;
        try {
            if(request.getParameter("image_id") != null && request.getParameter("image_id") != "") {
                imageId = Long.parseLong((request.getParameter("image_id")));
                System.out.println("div: imageId="+imageId);    //?????????????????????????????????????????????????

                if(imageId == 0) { //никогда не вызовится 99.99%
//                    request.setAttribute("images", images);
                    LOGGER.error("Удаление изображения с id = 0!");
                    getServletContext().getRequestDispatcher("/userGallery.jsp").forward(request, response);
                }

                imageDao.removeById(imageId);

                int persId = Integer.parseInt(request.getParameter("pers_id"));
                System.out.println("del: pers_id="+persId);//????????????????????????????????????
//                List<Image> images=new ArrayList(imageDao.findAllByPerson(persId));
                List<Image> imagesAll = imageDao.findAll();
                request.getSession().setAttribute("images", imagesAll);

                ServletContext servletContext = getServletContext();
                RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/userGallery.jsp");
                requestDispatcher.forward(request, response);
            }

        } catch (DaoException e) {
            LOGGER.error(e);
            getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/userGallery.jsp").forward(request, response);
    }
}
