package me.aki.estore.controller.product;

import me.aki.estore.domain.Product;
import me.aki.estore.factory.BasicFactory;
import me.aki.estore.service.ProductService;
import me.aki.estore.util.IOUtil;
import me.aki.estore.util.PicUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Aki on 2017/2/11.
 */
@WebServlet(name = "AddProductServlet", urlPatterns = "/servlet/AddProductServlet")
public class AddProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String encode = this.getServletContext().getInitParameter("encode");
        Map<String, String> paramMap = new HashMap<>();

        // 准备文件上传
        String tmpDir = this.getServletContext().getRealPath("/WEB-INF/tmp");
        File tmpFile = new File(tmpDir);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }

        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        // 缓存大小为100K
        fileItemFactory.setSizeThreshold(1024*100);
        fileItemFactory.setRepository(tmpFile);

        ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
        servletFileUpload.setHeaderEncoding(encode);
        servletFileUpload.setFileSizeMax(1024*1024); // 单个文件最大1M
        servletFileUpload.setSizeMax(10*1024*1024);

        if (!servletFileUpload.isMultipartContent(request)) {
            throw new RuntimeException("请使用正确的表单上传！");
        }

        try {
            List<FileItem> fileItemList = servletFileUpload.parseRequest(request);
            for (FileItem item : fileItemList) {
                if (item.isFormField()) {
                    // 处理普通表单数据
                    String itemName = item.getFieldName();
                    String itemValue = item.getString(encode);
                    paramMap.put(itemName, itemValue);
                } else {
                    // 处理上传文件
                    // 上传文件保存的根路径
                    String baseUploadDir = this.getServletContext().getRealPath("/WEB-INF/upload");
                    File file = new File(baseUploadDir);
                    if (!file.exists()) {
                        file.mkdirs();
                    }

                    // 有的浏览器上传带有全路径
                    String fileName = item.getName();
                    String fileNameWithoutPath = fileName.substring(fileName.lastIndexOf("/") + 1);
                    // 防止文件重名
                    String uniFileName = UUID.randomUUID().toString() + "_" + fileNameWithoutPath;
                    // 持久化图片路径时使用个相对路径
                    String imgurl = "/WEB-INF/upload";

                    int a = (uniFileName.hashCode() & 0x0f);       // 0~15
                    int b = (uniFileName.hashCode() & 0xf0) >> 4;  // 0~15
                    String realUploadDir = baseUploadDir + "/" + a + "/" + b;
                    File realDirFile = new File(realUploadDir);
                    if (!realDirFile.exists()) {
                        realDirFile.mkdirs();
                    }
                    imgurl = imgurl + "/" + a + "/" + b + "/" + uniFileName;
                    paramMap.put("imgurl", imgurl);

                    InputStream inputStream = item.getInputStream();
                    OutputStream outputStream = new FileOutputStream(new File(realUploadDir, uniFileName));

                    IOUtil.In2Out(inputStream, outputStream);

                    item.delete();

                    //--生成缩略图,在同一个目录下生成一个以_s结尾的新图片
                    PicUtils pic = new PicUtils(this.getServletContext().getRealPath(imgurl));
                    pic.resizeByHeight(140);
                }
            }

            // 2 调用Service将商品添加到数据库
            Product product = new Product();
            BeanUtils.populate(product, paramMap);
            ProductService productService = BasicFactory.getFactory().getInstance(ProductService.class);
            productService.addProduct(product);

            response.getWriter().write("提交成功");
            response.setHeader("Refresh", "3;url=" + request.getContextPath() + "/index.jsp");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    private String createRealDir(String baseDir, String fileName) {
        int a = (fileName.hashCode() & 0x0f);       // 0~15
        int b = (fileName.hashCode() & 0xf0) >> 4;  // 0~15
        String realDir = baseDir + "/" + a + "/" + b;
        File file = new File(realDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return realDir;
    }
}
