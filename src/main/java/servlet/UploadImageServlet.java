package servlet;

import dao.ImageDao;
import dao.PersonDao;
import exception.DaoException;
import jdbc.JdbcDaoFactory;
import model.Image;
import model.Person;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UploadImageServlet", urlPatterns ="/upload")
@MultipartConfig(
        fileSizeThreshold = 2560*1060*2,
        maxFileSize = 2560*1060*10,
        maxRequestSize = 2560*1060*50 )

public class UploadImageServlet extends HttpServlet {

    public final static String SAVEDIR="Images";    //webapp/../Image/
    private final Logger LOGGER= Logger.getLogger(UploadImageServlet.class);
    private ImageDao imageDao = (ImageDao) JdbcDaoFactory.getInstance().getDao(ImageDao.class);
    private final PersonDao personDao= (PersonDao) JdbcDaoFactory.getInstance().getDao(PersonDao.class);
    String filename = "";
    Person person = null;
    List<Image> images = null;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int persId = Integer.parseInt(request.getParameter("pers_id"));
        Image image = new Image();

       //если выбран файл для переноса  - выполняется ВСЕГДА, т.к. POST!
        if(request.getParts().size()>0)    {
            Part p = request.getPart("file");
            filename = p.getSubmittedFileName();

//            System.out.println("UploadServ: pers_id="+pers_id);     //52!
//            System.out.println("UploadServ: filename="+filename);       //!

            //есть ли такой в БД у пользователя? (не вообще в БД - у разн. пользов. могут быть одинак. файлы - одинак. ссылки)
//            System.out.println("imageDao.findByImgNameByPersId: "+imageDao.findByImgNameByPersId(filename, pers_id).getImage_name()); //ifox.jpg  в БД!

// 1- скачиван. нового файла если его нету у пользователя в БД )
            if(!filename.equals(imageDao.findByImgNameByPersId(filename, persId).getImageName())) {   //если такого изобр. нет у юзера
                this.doUpload(request, p);  //а)-скачать
//                System.out.println("UploadServ: файл скачивался");     //!!
                try {
                    images = new ArrayList<>(imageDao.findAllByPerson(persId));
                } catch (DaoException e) {
                    e.printStackTrace();
                }
                if(filename.equals(""))     //если назв. нов. изобр. пустое
                    filename = images.stream().findFirst().get().getImageName();    //выбрать первое img

                image.setImageName(filename);   //..и установ. назв.
                image.setPersId(persId);        //в чьей коллекции?
                try {
                    imageDao.save(image);   //б)сделать запись в БД //!!!
                } catch (DaoException e) {
                    e.printStackTrace();
                    LOGGER.error(e);
                }

            }

            //флаг для  аватара (из uploadImage.jsp
            boolean flagAvatar = Boolean.parseBoolean(request.getParameter("flagAvatar"));
//            System.out.println("UploadServ: flagAvatar="+flagAvatar);     //true
            //и если это аватар
            if(flagAvatar){
                try {                    //----------------------вытащить все img актора
                    images = imageDao.findAllByPerson(persId);
                } catch (DaoException e) {
                    e.printStackTrace();
                    LOGGER.error(e);
                }
                                        //---------после добавл. в БД вытащить img по его назв.
                String finalFilename = filename;
                image=images.stream().filter(i->i.getImageName().equals(finalFilename)).findFirst().get();
//                System.out.println("UploadServ: image.name="+image.getImage_name());                    //!!
//                System.out.println("UploadServ: image.getId()="+image.getId());     //!!

 //найти person и установ. ему avatar_id  + измен. в БД/ (jsp сама распределит на аватар и галерею)

                try {
//                    person = personDao.findById((long) pers_id);          //если не исп. сессию
                    person = (Person) request.getSession().getAttribute("person");
                    person.setAvatarId(Math.toIntExact(image.getId())); //установ. нов. автара
                    personDao.update(person);                          //внес. изм. в БД

                } catch (DaoException e) {
                    e.printStackTrace();
                    LOGGER.error(e);
                }
            }

            //для всех случ. получить весь список img и отправить в браузер
            try {
                images = new ArrayList<>(imageDao.findAllByPerson(persId));

            } catch (DaoException e) {
                e.printStackTrace();
                LOGGER.error(e);
            }
//  //вытащить человека для стр. mainForUser.jsp - если не исп. сессию
//            Person person = null;
//            List<Person> persons = null;
//            try {
//                person = personDao.findById((long) pers_id);
//                persons = personDao.findAll();
//            } catch (DaoException e) {
//                e.printStackTrace();
//            }
//            request.setAttribute("person", person);

//            request.setAttribute("persons", persons);
//            request.setAttribute("images", images);
            List<Image> imagesAll = null;           //и т.к. изобр. уже добавл. в БД в стр. 72..
            try {
                imagesAll = imageDao.findAll(); //вытащить
            } catch (DaoException e) {
                e.printStackTrace();
            }
            request.getSession().setAttribute("images", imagesAll);
        }
        getServletContext().getRequestDispatcher("/userGallery.jsp").forward(request, response);
    }

    private void doUpload(HttpServletRequest request, Part p) {

        String root=request.getServletContext().getRealPath("");

        String savePath=root+  UploadImageServlet.SAVEDIR;  //+ путь до папки Images +File.separator+
        File dir=new File(savePath);    //созд. объекта работы с файлом
        if(!dir.exists())
            dir.mkdir();

        InputStream in=null;
        FileOutputStream fos=null;
        try{
            in=p.getInputStream();
            byte [] b=new byte[in.available()]; //!
            in.read(b); //запись из потока в массив байт
            fos = new FileOutputStream(savePath+File.separator+filename);
            fos.write(b);
            fos.close();

        } catch (IOException ex)    {
            LOGGER.error(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
