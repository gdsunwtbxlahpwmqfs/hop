/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hop.ui.hopgui;

import jakarta.servlet.ServletContextEvent;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.server.ServerContainer;
import jakarta.websocket.server.ServerEndpointConfig;
import java.util.logging.Logger;
import org.apache.hop.core.HopEnvironment;
import org.apache.hop.core.exception.HopException;
import org.apache.hop.core.logging.LogChannel;
import org.apache.hop.history.AuditManager;
import org.apache.hop.ui.hopgui.terminal.PtyWebSocketEndpoint;
import org.eclipse.rap.rwt.engine.RWTServletContextListener;

public class HopWebServletContextListener extends RWTServletContextListener {
  private static final Logger logger =
      Logger.getLogger(HopWebServletContextListener.class.getName());

  @Override
  public void contextInitialized(ServletContextEvent event) {
    logger.info("=== Hop Web ServletContextListener initialization started ===");

    /*
     *  The following lines are from HopGui.main
     *  because they are application-wide context.
     */
    try {
      logger.info("Initializing HopEnvironment...");
      HopEnvironment.init();
      logger.info("Initializing HopGuiEnvironment...");
      HopGuiEnvironment.init();
      logger.info("Hop Web environment initialized successfully");
      LogChannel.UI.logBasic("Hop Web environment initialized");
    } catch (HopException e) {
      logger.severe("Failed to initialize Hop Web environment: " + e.getMessage());
      LogChannel.UI.logError("Failed to initialize Hop Web environment", e);
      throw new RuntimeException("Failed to initialize Hop Web environment", e);
    } catch (Exception e) {
      logger.warning("Non-critical error during Hop environment initialization: " + e.getMessage());
      LogChannel.UI.logBasic(
          "Non-critical error during Hop environment initialization: " + e.getMessage());
    }

    // Ensure JAXP uses the JDK built-in TransformerFactory instead of looking up
    // a (possibly absent) Xalan implementation via META-INF/services. Without this,
    // POI's XMLHelper fails with TransformerFactoryConfigurationError when writing
    // XLSX files in the web (RAP) environment.
    if (System.getProperty("javax.xml.transform.TransformerFactory") == null) {
      logger.info("Setting JAXP TransformerFactory to JDK built-in implementation");
      System.setProperty(
          "javax.xml.transform.TransformerFactory",
          "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl");
    } else {
      logger.info(
          "JAXP TransformerFactory already set: "
              + System.getProperty("javax.xml.transform.TransformerFactory"));
    }

    // Use per-user audit folders in Hop Web when the user is authenticated
    logger.info("Setting session audit manager provider...");
    AuditManager.setSessionAuditManagerProvider(new HopWebAuditManagerProvider());

    logger.info("Calling super.contextInitialized() for RWT initialization...");
    try {
      super.contextInitialized(event);
      logger.info("RWT initialization completed successfully");
    } catch (Exception e) {
      logger.severe("Failed to initialize RWT: " + e.getMessage());
      e.printStackTrace();
      LogChannel.UI.logError("Failed to initialize RWT", e);
      throw e;
    }

    try {
      logger.info("Registering PTY WebSocket endpoint...");
      registerPtyWebSocketEndpoint(event);
    } catch (Exception e) {
      logger.warning("Failed to register PTY WebSocket endpoint: " + e.getMessage());
      LogChannel.UI.logError("Failed to register PTY WebSocket endpoint", e);
    }

    logger.info("=== Hop Web ServletContextListener initialization completed ===");
  }

  private void registerPtyWebSocketEndpoint(ServletContextEvent event) {
    try {
      logger.fine("Looking up WebSocket ServerContainer...");
      ServerContainer container =
          (ServerContainer)
              event.getServletContext().getAttribute("jakarta.websocket.server.ServerContainer");
      if (container != null) {
        logger.info("WebSocket ServerContainer found, registering endpoint at /pty/{ptyId}");
        ServerEndpointConfig config =
            ServerEndpointConfig.Builder.create(PtyWebSocketEndpoint.class, "/pty/{ptyId}").build();
        container.addEndpoint(config);
        logger.info("PTY WebSocket endpoint registered successfully at /pty/{ptyId}");
        LogChannel.UI.logBasic("Registered PTY WebSocket endpoint at /pty/{ptyId}");
      } else {
        logger.warning("WebSocket ServerContainer not found - PTY terminal may not work");
        LogChannel.UI.logError("WebSocket ServerContainer not found - PTY terminal may not work");
      }
    } catch (DeploymentException e) {
      logger.severe("Failed to register PTY WebSocket endpoint: " + e.getMessage());
      LogChannel.UI.logError("Failed to register PTY WebSocket endpoint", e);
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent event) {
    logger.info("=== Hop Web ServletContextListener contextDestroyed called ===");
    logger.info("Calling super.contextDestroyed() for RWT cleanup...");
    super.contextDestroyed(event);
    logger.warning("Shutting down Hop Web VM...");
    // Kill all remaining things in this VM!
    System.exit(0);
  }
}
