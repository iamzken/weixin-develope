package com.imooc.servlet;
 
import java.io.File;
import java.io.IOException;

import javax.management.ObjectName;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Container;
import org.apache.catalina.ContainerServlet;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Globals;
import org.apache.catalina.Host;
import org.apache.catalina.Server;
import org.apache.catalina.Wrapper;
import org.apache.catalina.manager.Constants;
import org.apache.catalina.util.ContextName;
import org.apache.tomcat.util.res.StringManager;
 
public class ReloadServlet extends HttpServlet implements ContainerServlet{
    private static final long serialVersionUID = 3060161522501102057L;
     
    protected transient Host host = null;
    protected transient Wrapper wrapper = null;
    protected transient Context context = null;
    protected static final StringManager sm = StringManager.getManager(Constants.Package);
    protected int debug = 1;
    protected transient javax.naming.Context global = null;
    protected File versioned = null;
    protected File deployed = null;
    protected File configBase = null;
    protected ObjectName oname = null;
     
     
    public void init() throws ServletException {
 
        if ((wrapper == null) || (context == null))
            throw new UnavailableException(
                    sm.getString("managerServlet.noWrapper"));
 
        String value = null;
        try {
            value = getServletConfig().getInitParameter("debug");
            debug = Integer.parseInt(value);
        } catch (Throwable t) {
        	t.printStackTrace();
        }
 
        Server server = ((Engine)host.getParent()).getService().getServer();
        if (server != null) {
            global = server.getGlobalNamingContext();
        }
 
        versioned = (File) getServletContext().getAttribute
            (ServletContext.TEMPDIR);
 
        String appBase = ((Host) context.getParent()).getAppBase();
        deployed = new File(appBase);
        if (!deployed.isAbsolute()) {
            deployed = new File(System.getProperty(Globals.CATALINA_BASE_PROP),
                                appBase);
        }
        configBase = new File(System.getProperty(Globals.CATALINA_BASE_PROP), "conf");
        Container container = context;
        Container host = null;
        Container engine = null;
        while (container != null) {
            if (container instanceof Host)
                host = container;
            if (container instanceof Engine)
                engine = container;
            container = container.getParent();
        }
        if (engine != null) {
            configBase = new File(configBase, engine.getName());
        }
        if (host != null) {
            configBase = new File(configBase, host.getName());
        }
        if (debug >= 1) {
            log("init: Associated with Deployer '" +
                oname + "'");
            if (global != null) {
                log("init: Global resources are available");
            }
        }
 
    }
     
 
    public Wrapper getWrapper() {
        return this.wrapper;
    }
 
    public void setWrapper(Wrapper wrapper) {
        this.wrapper=wrapper;
        if (wrapper == null) {
            context = null;
            host = null;
            oname = null;
        } else {
            context = (Context) wrapper.getParent();
            host = (Host) context.getParent();
            Engine engine = (Engine) host.getParent();
            String name = engine.getName() + ":type=Deployer,host=" +
                    host.getName();
            try {
                oname = new ObjectName(name);
            } catch (Exception e) {
                log(sm.getString("managerServlet.objectNameFail", name), e);
            }
        }
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ContextName cn=new ContextName("/Weixin", request.getParameter("version"));
        Context context = (Context) host.findChild(cn.getName());
        context.reload();
    }
     
}