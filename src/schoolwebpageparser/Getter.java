package schoolwebpageparser;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import util.GetterInterface;
import util.webpage.Post;

import com.caucho.hessian.server.HessianServlet;

public class Getter extends HessianServlet implements GetterInterface {
	private static final long serialVersionUID = -9013527113403890093L;

	@Override
	public String echo(String in) {
		return "echo:"+in;
	}

	@Override
	public List<Post> getPosts(Date start, Date end, int max) {
		List<Post> posts = new LinkedList<Post>();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Post.class);
		if(start!=null && end!=null)
			query.setFilter("this.date >= start && this.date < end");
		else if(start!=null && end==null)
			query.setFilter("this.date >= start");
		else if(start==null && end!=null)
			query.setFilter("this.date < end");
		query.declareParameters("java.util.Date start, java.util.Date end");
		query.setOrdering("date desc");
		if(max>=0)
			query.setRange(0, max);
		try{
			posts = (List<Post>) query.execute(start, end);
		} finally {
			query.closeAll();
		}
		return posts;
	}

}
