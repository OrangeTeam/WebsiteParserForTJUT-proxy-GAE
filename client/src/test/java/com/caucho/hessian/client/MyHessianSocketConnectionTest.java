package com.caucho.hessian.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.orange.parser.entity.Post;
import org.orange.parser.proxy.api.GetterInterface;

import java.net.MalformedURLException;
import java.util.List;

@RunWith(JUnit4.class)
public class MyHessianSocketConnectionTest {
    private static final String URL = "http://baijie1991-hrd.appspot.com/getter";
    private static final int TIMEOUT = 1000;

    HessianProxyFactory mProxyFactory;
    GetterInterface mGetter;
    @Before
    public void setupProxyFactory() {
        mProxyFactory = new HessianProxyFactory();
        MyHessianSocketConnectionFactory connectionFactory = new MyHessianSocketConnectionFactory();
        connectionFactory.setHessianProxyFactory(mProxyFactory);
        mProxyFactory.setConnectionFactory(connectionFactory);

        mProxyFactory.setConnectTimeout(TIMEOUT);

        try {
            mGetter = (GetterInterface) mProxyFactory.create(GetterInterface.class, URL);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testEcho() {
        String testString = "Low Low Low.中文哦，hi哈。";
        String echo = mGetter.echo(testString);
        Assert.assertTrue(echo.contains(testString));
        System.out.printf("getter.echo(\"%s\") = %s%n", testString, echo);
    }
    @Test
    public void testGetPosts() {
        List<Post> posts = mGetter.getPosts(
                Post.convertToDate(2014, 4, 1), Post.convertToDate(2014, 5, 1), -1);
        Assert.assertEquals(40, posts.size());
        System.out.printf("得到 %d 个通知。%n", posts.size());
        for (Post post : posts)
            System.out.println(post); // TODO check posts
    }
}
