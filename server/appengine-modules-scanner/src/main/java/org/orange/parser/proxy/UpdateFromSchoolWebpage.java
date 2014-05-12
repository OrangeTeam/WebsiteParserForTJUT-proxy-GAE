package org.orange.parser.proxy;

import org.orange.parser.entity.Post;
import org.orange.parser.parser.SchoolWebpageParser;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateFromSchoolWebpage extends HttpServlet {

    private static final long serialVersionUID = -6407706274584693180L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if("true".equals(req.getParameter("echo"))) {
            Enumeration parameterNames = req.getParameterNames();
            while(parameterNames.hasMoreElements()) {
                final String name = (String) parameterNames.nextElement();
                final String[] values = req.getParameterValues(name);
                resp.getWriter().println(name + " : " + Arrays.toString(values));
            }
            return;
        }
        List<Post> result = null;
        Date lastDate = null;
        PersistenceManager pm = PMF.get().getPersistenceManager();
        //最新通知日期
        Query query = pm.newQuery(Post.class);
        query.setOrdering("date desc");
        query.setRange(0, 1);
        result = (List<Post>) query.execute();
        if (!result.isEmpty()) {
            lastDate = result.get(0).getDate();
        }
        //更新
        SchoolWebpageParser parser = new SchoolWebpageParser();
        result = parser.parsePosts(lastDate, null, -1);

        try {
            pm.makePersistentAll(result);
        } finally {
            query.closeAll();
            pm.close();
        }

        resp.getWriter().println("updated or overwrote " + result.size() + " posts.");
    }

}
