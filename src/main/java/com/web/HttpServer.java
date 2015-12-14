package com.web;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;

import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class HttpServer {
    protected Server jetty;
    private ServletContextHandler context;

    public HttpServer(String host, int port, String contextPath) {
        jetty = new Server();
        context = new ServletContextHandler(jetty, "/", ServletContextHandler.SESSIONS);
        context.setContextPath(contextPath);
        context.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
        jetty.setHandler(context);
        ServerConnector connector = new ServerConnector(jetty);
        connector.setHost(host);
        connector.setPort(port);
        jetty.setConnectors(new Connector[] {connector});
    }

    public void join() throws InterruptedException {
        jetty.join();
    }

    public void stop() throws Exception {
        jetty.stop();
    }

    public void start() throws Exception {
        jetty.start();
    }

    public void add(String path, HttpServlet servlet) {
        context.addServlet(new ServletHolder(servlet), path);
    }

    public void addFilter(Filter filter) {
        context.addFilter(new FilterHolder(filter), "*", EnumSet.of(DispatcherType.REQUEST));
    }

    public void add(ServletHolder holder, String string) {
        context.addServlet(holder, string);
    }
}