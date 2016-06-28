/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.app.xmlui.aspect.administrative;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.avalon.framework.activity.Disposable;
import org.apache.avalon.framework.service.ServiceException;
import org.apache.avalon.framework.service.ServiceManager;
import org.apache.avalon.framework.service.Serviceable;
import org.apache.cocoon.ProcessingException;
import org.apache.cocoon.configuration.Settings;
import org.apache.cocoon.environment.ObjectModelHelper;
import org.apache.cocoon.environment.Request;
import org.apache.excalibur.store.Store;
import org.apache.excalibur.store.StoreJanitor;
import org.apache.log4j.Logger;
import org.dspace.app.xmlui.aspect.administrative.controlpanel.AbstractControlPanelTab;
import org.dspace.app.xmlui.aspect.administrative.controlpanel.ControlPanelTab;
import org.dspace.app.xmlui.cocoon.AbstractDSpaceTransformer;
import org.dspace.app.xmlui.utils.UIException;
import org.dspace.app.xmlui.wing.Message;
import org.dspace.app.xmlui.wing.WingException;
import org.dspace.app.xmlui.wing.element.Body;
import org.dspace.app.xmlui.wing.element.Division;
import org.dspace.app.xmlui.wing.element.List;
import org.dspace.app.xmlui.wing.element.PageMeta;
import org.dspace.authorize.AuthorizeException;
import org.dspace.authorize.factory.AuthorizeServiceFactory;
import org.dspace.authorize.service.AuthorizeService;
import org.dspace.core.factory.CoreServiceFactory;
import org.dspace.services.ConfigurationService;
import org.dspace.services.factory.DSpaceServicesFactory;
import org.xml.sax.SAXException;

/**
 * This page displays control panel tabs which provide information about running dspace and admin functionalities.
 *
 * based on ControlPanel by Jay Paz and Scott Phillips
 * @author LINDAT/CLARIN dev team (http://lindat.cz)
 */
public class ControlPanel extends AbstractDSpaceTransformer implements Serviceable, Disposable {

    /** log4j category */
    private static final Logger log = Logger.getLogger(ControlPanel.class);

    /** Language Strings */
    private static final Message T_DSPACE_HOME		= message("xmlui.general.dspace_home");
    private static final Message T_title			= message("xmlui.administrative.ControlPanel.title");
    private static final Message T_trail			= message("xmlui.administrative.ControlPanel.trail");
    private static final Message T_head				= message("xmlui.administrative.ControlPanel.head");
    
    private static final Message T_select_panel		= message("xmlui.administrative.ControlPanel.select_panel");

    protected AuthorizeService authorizeService = AuthorizeServiceFactory.getInstance().getAuthorizeService();
    protected ConfigurationService configurationService = DSpaceServicesFactory.getInstance().getConfigurationService();

    /**
     * The service manager allows us to access the continuation's 
     * manager.  It is obtained from the Serviceable API
     */
    private ServiceManager serviceManager;

    /**
     * The Cocoon Settings (used to display Cocoon info)
     */
    private Settings settings;

    /**
     * The Cocoon StoreJanitor (used for cache statistics)
     */
    private StoreJanitor storeJanitor;

     /**
     * The Cocoon Default Store (used for cache statistics)
     */
    private Store storeDefault;

    /**
     * The Cocoon Persistent Store (used for cache statistics)
     */
    private Store storePersistent;
    
    /**
     * From the <code>org.apache.avalon.framework.service.Serviceable</code> API, 
     * give us the current <code>ServiceManager</code> instance.
     * <P>
     * Much of this ServiceManager logic/code has been borrowed from the source
     * code of the Cocoon <code>StatusGenerator</code> class:
     * http://svn.apache.org/repos/asf/cocoon/tags/cocoon-2.2/cocoon-sitemap-components/cocoon-sitemap-components-1.0.0/src/main/java/org/apache/cocoon/generation/StatusGenerator.java
     */
    @Override
    public void service(ServiceManager serviceManager) throws ServiceException 
    {
        this.serviceManager = serviceManager;
        
        this.settings = (Settings) this.serviceManager.lookup(Settings.ROLE);
        
        if(this.serviceManager.hasService(StoreJanitor.ROLE))
            this.storeJanitor = (StoreJanitor) this.serviceManager.lookup(StoreJanitor.ROLE);
        
        if (this.serviceManager.hasService(Store.ROLE))
            this.storeDefault = (Store) this.serviceManager.lookup(Store.ROLE);
        
        if(this.serviceManager.hasService(Store.PERSISTENT_STORE))
            this.storePersistent = (Store) this.serviceManager.lookup(Store.PERSISTENT_STORE);
    }

    @Override
    public void addPageMeta(PageMeta pageMeta) throws SAXException,
                    WingException, UIException, SQLException, IOException,
                    AuthorizeException 
    {
        pageMeta.addMetadata("title").addContent(T_title);

        pageMeta.addTrailLink(contextPath + "/", T_DSPACE_HOME);
        pageMeta.addTrailLink(null, T_trail);
    }

    @Override
    public void addBody(Body body) throws SAXException, WingException,
                    UIException, SQLException, IOException, AuthorizeException 
    {

        if (!authorizeService.isAdmin(context))
        {
            throw new AuthorizeException("You are not authorized to view this page.");
        }

        Division div = body.addInteractiveDivision("control-panel", contextPath+"/admin/panel", Division.METHOD_POST, "primary administrative");
        div.setHead(T_head);

<<<<<<< HEAD
=======
        // LIST: options
        List options = div.addList("options",List.TYPE_SIMPLE,"horizontal");

        // our options, selected or not....
        if (option == OPTIONS.java)
        {
            options.addItem().addHighlight("bold").addXref("?java", T_option_java);
        }
        else
        {
            options.addItemXref("?java", T_option_java);
        }

        if (option == OPTIONS.dspace)
        {
            options.addItem().addHighlight("bold").addXref("?dspace", T_option_dspace);
        }
        else
        {
            options.addItemXref("?dspace", T_option_dspace);
        }

        if (option == OPTIONS.alerts)
        {
            options.addItem().addHighlight("bold").addXref("?alerts", T_option_alerts);
        }
        else
        {
            options.addItemXref("?alerts", T_option_alerts);
        }

        if (option == OPTIONS.harvest)
        {
            options.addItem().addHighlight("bold").addXref("?harvest", T_option_harvest);
        }
        else
        {
            options.addItemXref("?harvest", T_option_harvest);
        }

        String userSortTarget = "?activity";
        if (request.getParameter("sortBy") != null)
        {
            userSortTarget += "&sortBy=" + request.getParameter("sortBy");
        }
        if (option == OPTIONS.activity)
        {
            options.addItem().addHighlight("bold").addXref(userSortTarget, "Current Activity");
        }
        else
        {
            options.addItemXref(userSortTarget, "Current Activity");
        }


        // The main content:
        if (option == OPTIONS.java)
        {
            addJavaInformation(div);
        }
        else if (option == OPTIONS.dspace)
        {
            addDSpaceConfiguration(div);
        }
        else if (option == OPTIONS.alerts)
        {
            addAlerts(div);
        }
        else if (option == OPTIONS.activity)
        {
            addActivity(div);
        }
        else if (option == OPTIONS.harvest)
        {
            addHarvest(div);
        }
        else
        {
            div.addPara(T_select_panel);
        }

    }

    /**
     * Add specific java information including JRE, OS, and runtime memory statistics.
     */
    private void addJavaInformation(Division div) throws WingException
    {
        // Get memory statistics
        int processors = Runtime.getRuntime().availableProcessors();
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long usedMemory = totalMemory-freeMemory;
        
        // Convert bytes into MiB
        maxMemory   = maxMemory   / 1024 / 1024;
        totalMemory = totalMemory / 1024 / 1024;
        usedMemory  = usedMemory  / 1024 / 1024;
        freeMemory  = freeMemory  / 1024 / 1024;
		
        // LIST: Java
        List list = div.addList("javaOs");
        list.setHead(T_JAVA_HEAD);
        list.addLabel(T_JAVA_VERSION);
        list.addItem(System.getProperty("java.version"));
        list.addLabel(T_JAVA_VENDOR);
        list.addItem(System.getProperty("java.vm.name"));
        list.addLabel(T_OS_NAME);
        list.addItem(System.getProperty("os.name"));
        list.addLabel(T_OS_ARCH);
        list.addItem(System.getProperty("os.arch"));
        list.addLabel(T_OS_VERSION);
        list.addItem(System.getProperty("os.version"));
		
        // LIST: memory
        List runtime = div.addList("runtime");
        runtime.setHead(T_RUNTIME_HEAD);
        runtime.addLabel(T_RUNTIME_PROCESSORS);
        runtime.addItem(String.valueOf(processors));
        runtime.addLabel(T_RUNTIME_MAX);
        runtime.addItem(String.valueOf(maxMemory) + " MiB");
        runtime.addLabel(T_RUNTIME_TOTAL);
        runtime.addItem(String.valueOf(totalMemory) + " MiB");
        runtime.addLabel(T_RUNTIME_USED);
        runtime.addItem(String.valueOf(usedMemory) + " MiB");
        runtime.addLabel(T_RUNTIME_FREE);
        runtime.addItem(String.valueOf(freeMemory) + " MiB");
        
        //List: Cocoon Info & Cache
        addCocoonInformation(div);
    }

    /**
     * Add specific Cocoon information, especially related to the Cocoon Cache.
     * <P>
     * For more information about Cocoon Caches/Stores, see:
     * http://wiki.apache.org/cocoon/StoreComponents  
     */
    private void addCocoonInformation(Division div) throws WingException
    {
        // List: Cocoon Info & Caches
        List cocoon = div.addList("cocoon");
        cocoon.setHead(T_COCOON_HEAD);
        
        cocoon.addLabel(T_COCOON_VERSION);
        cocoon.addItem(org.apache.cocoon.Constants.VERSION);

        // attempt to Display some basic info about Cocoon's Settings & Caches

        // Get access to basic Cocoon Settings
        if(this.settings!=null)
        {
            //Output Cocoon's Work Directory & Cache Directory
            cocoon.addLabel(T_COCOON_WORK_DIR);
            cocoon.addItem(this.settings.getWorkDirectory());
            cocoon.addLabel(T_COCOON_CACHE_DIR);
            cocoon.addItem(this.settings.getCacheDirectory());
        }    

        // Check if we have access to Cocoon's Default Cache
        // Cocoon's Main (Default) Store is used to store objects that are serializable
        if(this.storeDefault!=null)
        {
            // Store name is just the className (remove the package info though, just to save space)
            String storeName = this.storeDefault.getClass().getName();
            storeName = storeName.substring(storeName.lastIndexOf(".")+1); 

            // display main store's cache info
            cocoon.addLabel(T_COCOON_MAIN_CACHE_SIZE.parameterize(storeName + ", 0x" + Integer.toHexString(this.storeDefault.hashCode())));

            // display cache size & link to clear Cocoon's main cache
            Item defaultSize = cocoon.addItem();
            defaultSize.addContent(String.valueOf(this.storeDefault.size()) + "  ");
            defaultSize.addXref(contextPath + "/admin/panel?java=true&clearcache=true", T_COCOON_CACHE_CLEAR);
        }

        // Check if we have access to Cocoon's Persistent Cache
        // Cocoon's Persistent Store may be used by the Default Cache/Store to delegate persistent storage
        // (it's an optional store which may not exist)
        if(this.storePersistent!=null)
        {
            // Store name is just the className (remove the package info though, just to save space)
            String storeName = this.storeDefault.getClass().getName();
            storeName = storeName.substring(storeName.lastIndexOf(".")+1);

            // display persistent store's cache size info
            cocoon.addLabel(T_COCOON_PERSISTENT_CACHE_SIZE.parameterize(storeName + ", 0x" + Integer.toHexString(this.storePersistent.hashCode())));
            cocoon.addItem(String.valueOf(this.storePersistent.size()));
        }

        // Check if we have access to Cocoon's StoreJanitor
        // The Store Janitor manages all of Cocoon's "transient caches/stores"
        // These "transient" stores are used for non-serializable objects or objects whose
        // storage doesn't make sense across a server restart. 
        if(this.storeJanitor!=null)
        {
            // For each Cache Store in Cocoon's StoreJanitor
            Iterator i = this.storeJanitor.iterator();
            while(i.hasNext())
            {
                // get the Cache Store
                Store store = (Store) i.next();

                // Store name is just the className (remove the package info though, just to save space)
                String storeName = store.getClass().getName();
                storeName = storeName.substring(storeName.lastIndexOf(".")+1); 

                // display its size information
                cocoon.addLabel(T_COCOON_TRANS_CACHE_SIZE.parameterize(storeName + ", 0x" + Integer.toHexString(store.hashCode())));
                cocoon.addItem(String.valueOf(store.size()));
            }
        }
    }

    private static final String T_UNSET = "UNSET";

    /**
     * Guarantee a non-null String.
     *
     * @param value candidate string.
     * @return {@code value} or a constant indicating an unset value.
     */
    private static String notempty(String value) { return (null == value || "".equals(value)) ? T_UNSET : value; }

    /**
     * List important DSpace configuration parameters.
     */
    private void addDSpaceConfiguration(Division div) throws WingException 
    {

        // LIST: DSpace
        List dspace = div.addList("dspace");
        dspace.setHead(T_DSPACE_HEAD);

        dspace.addLabel(T_DSPACE_VERSION);
        dspace.addItem(Util.getSourceVersion());

        dspace.addLabel(T_DSPACE_DIR);
        dspace.addItem(notempty(ConfigurationManager.getProperty("dspace.dir")));

        dspace.addLabel(T_DSPACE_URL);
        dspace.addItem(notempty(ConfigurationManager.getProperty("dspace.url")));

        dspace.addLabel(T_DSPACE_HOST_NAME);
        dspace.addItem(notempty(ConfigurationManager.getProperty("dspace.hostname")));

        dspace.addLabel(T_DSPACE_NAME);
        dspace.addItem(notempty(ConfigurationManager.getProperty("dspace.name")));

        dspace.addLabel(T_DB_NAME);
        dspace.addItem(notempty(DatabaseManager.getDbName()));

        dspace.addLabel(T_DB_URL);
        dspace.addItem(notempty(ConfigurationManager.getProperty("db.url")));

        dspace.addLabel(T_DB_DRIVER);
        dspace.addItem(notempty(ConfigurationManager.getProperty("db.driver")));

        dspace.addLabel(T_DB_MAX_CONN);
        dspace.addItem(notempty(ConfigurationManager.getProperty("db.maxconnections")));

        dspace.addLabel(T_DB_MAX_WAIT);
        dspace.addItem(notempty(ConfigurationManager.getProperty("db.maxwait")));

        dspace.addLabel(T_DB_MAX_IDLE);
        dspace.addItem(notempty(ConfigurationManager.getProperty("db.maxidle")));

       	dspace.addLabel(T_MAIL_SERVER);
        dspace.addItem(notempty(ConfigurationManager.getProperty("mail.server")));

        dspace.addLabel(T_MAIL_FROM_ADDRESS);
        dspace.addItem(notempty(ConfigurationManager.getProperty("mail.from.address")));

        dspace.addLabel(T_FEEDBACK_RECIPIENT);
        dspace.addItem(notempty(ConfigurationManager.getProperty("feedback.recipient")));

        dspace.addLabel(T_MAIL_ADMIN);
        dspace.addItem(notempty(ConfigurationManager.getProperty("mail.admin")));
    }

    /**
     * Add a section that allows administrators to activate or deactivate system-wide alerts.
     */
    private void addAlerts(Division div) throws WingException 
    {
        // Remember we're in the alerts section
        div.addHidden("alerts").setValue("true");

        List form = div.addList("system-wide-alerts",List.TYPE_FORM);
        form.setHead(T_alerts_head);

        form.addItem(T_alerts_warning);

        TextArea message = form.addItem().addTextArea("message");
        message.setAutofocus("autofocus");
        message.setLabel(T_alerts_message_label);
        message.setSize(5, 45);
        if (SystemwideAlerts.getMessage() == null)
        {
            message.setValue(T_alerts_message_default);
        }
        else
        {
            message.setValue(SystemwideAlerts.getMessage());
        }
		
        Select countdown = form.addItem().addSelect("countdown");
        countdown.setLabel(T_alerts_countdown_label);

        countdown.addOption(0,T_alerts_countdown_none);
        countdown.addOption(5,T_alerts_countdown_5);
        countdown.addOption(15,T_alerts_countdown_15);
        countdown.addOption(30,T_alerts_countdown_30);
        countdown.addOption(60,T_alerts_countdown_60);

        // Is there a current countdown active?
        if (SystemwideAlerts.isAlertActive() && SystemwideAlerts.getCountDownToo() - System.currentTimeMillis() > 0)
        {
            countdown.addOption(true, -1, T_alerts_countdown_keep);
        }
        else
        {
            countdown.setOptionSelected(0);
        }
		
        Select restrictsessions = form.addItem().addSelect("restrictsessions");
        restrictsessions.setLabel(T_alerts_session_label);
        restrictsessions.addOption(SystemwideAlerts.STATE_ALL_SESSIONS,T_alerts_session_all_sessions);
        restrictsessions.addOption(SystemwideAlerts.STATE_CURRENT_SESSIONS,T_alerts_session_current_sessions);
        restrictsessions.addOption(SystemwideAlerts.STATE_ONLY_ADMINISTRATIVE_SESSIONS,T_alerts_session_only_administrative);
        restrictsessions.setOptionSelected(SystemwideAlerts.getRestrictSessions());

        form.addItem(T_alerts_session_note);


        Item actions = form.addItem();
        actions.addButton("submit_activate").setValue(T_alerts_submit_activate);
        actions.addButton("submit_deactivate").setValue(T_alerts_submit_deactivate);
		
    }
	
    /** The possible sorting parameters */
    private static enum EventSort { TIME, URL, SESSION, AGENT, IP };
	
    /**
     * Create a list of all activity.
     */
    private void addActivity(Division div) throws WingException, SQLException 
    {
		
        // 0) Update recording settings
>>>>>>> 88ed833e2cd8f0852b8c8f1f2fa5e419ea70b1a4
        Request request = ObjectModelHelper.getRequest(objectModel);
        String selected_tab = "";

        if (request.getParameter("tab") != null)
        {
        	selected_tab = request.getParameter("tab");
        	div.addHidden("tab").setValue(selected_tab);
        }

        // LIST: options
        List options = div.addList("options", List.TYPE_SIMPLE, "horizontal");
        
        String tabs[] = configurationService.getArrayProperty("controlpanel.tabs");
        
        for(String tab : tabs) {
        	tab = tab.trim();
            Message linkText = message("xmlui.administrative.ControlPanel.tabs." + tab);
        	if(tab.equals(selected_tab)) {
        		options.addItem().addHighlight("bold").addXref("?tab=" + selected_tab, linkText);
        	} else {
        		options.addItemXref(contextPath + "/admin/panel?tab=" + tab, linkText);
        	}
        }
        
        if(selected_tab.equals("")) {
        	div.addPara(T_select_panel);
        } else {
        	ControlPanelTab cpTab = (ControlPanelTab)CoreServiceFactory.getInstance().getPluginService().getNamedPlugin(ControlPanelTab.class, selected_tab);
        	if(cpTab instanceof AbstractControlPanelTab) {
        		try {
        			((AbstractControlPanelTab) cpTab).setup(null, objectModel, null, parameters);
					((AbstractControlPanelTab) cpTab).service(serviceManager);
					((AbstractControlPanelTab) cpTab).setWebLink(contextPath + "/admin/panel?tab=" + selected_tab);
				} catch (ServiceException e) {
					log.error(e);
				} catch (ProcessingException e) {
					log.error(e);
				}
        	}        	
        	cpTab.addBody(objectModel, div);
        }        
    }

    
    /**
     * Release all Cocoon resources.
     * @see org.apache.avalon.framework.activity.Disposable#dispose()
     */
    @Override
    public void dispose() 
    {
        if (this.serviceManager != null) 
        {
            this.serviceManager.release(this.storePersistent);
            this.serviceManager.release(this.storeJanitor);
            this.serviceManager.release(this.storeDefault);
            this.serviceManager.release(this.settings);
            this.storePersistent = null;
            this.storeJanitor = null; 	
            this.storeDefault = null;
            this.settings = null;
        }
        super.dispose();
    }
}
