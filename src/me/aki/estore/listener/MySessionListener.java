package me.aki.estore.listener; /**
 * Created by Aki on 2017/2/12.
 */

import me.aki.estore.domain.Product;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;

public class MySessionListener implements HttpSessionListener {

    // Public constructor is required by servlet spec
    public MySessionListener() {
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
      /* Session is created. */
      se.getSession().setAttribute("shoppingCart", new HashMap<Product, Integer>());
    }

    public void sessionDestroyed(HttpSessionEvent se) {
      /* Session is destroyed. */
    }

}
