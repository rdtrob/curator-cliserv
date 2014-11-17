/**
 * Never use logs in JUnit tests, instead, use only asserts
 * Created by robert on 10/2/14.
 */
import org.apache.curator.test.TestingServer;
import org.apache.zookeeper.KeeperException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.*;
import java.net.BindException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.apache.curator.test.DirectoryUtils.deleteDirectoryContents;
import static org.junit.Assert.assertEquals;

public class ZkManagerTest {
    private TestingServer server;
    private static final int RETRY_WAIT_MS = 5000;

    @Before // Create server
    public void createServer() throws Exception {
        while (server == null) {
            try {
                server = new TestingServer();
            } catch (Exception e) {
                System.err.println("> Exception, retrying to create server. <");
                server = null;
            }
        }
    }

    @After  // Kill server
    public void killServer() throws Exception {
        if(server != null) {
            try {
                server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testCreatePath() {
        ZkManager manager = new ZkManager(server.getConnectString());
        manager.createPath("/tmp/a");
        manager.createPath("/tmp/b/n");
        manager.createPath("/tmp/x/y/f");
        manager.createPath("/tmp/b/a/c");
        manager.createPath("/tmp/a/b");

        List<String> folders = manager.readPath("/tmp" + "/a");

        /**
         * Print all paths(directories, aka ... NODES!) in List<String> ..., contained in ZkManager.java
         * Asserts for each create path in this test
         */

        Assert.assertTrue(folders.contains("a"));
    }

 }