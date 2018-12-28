package io.xkniu.github.tiny.rpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Rpc 服务器，通过 <code>registerService</code> 来注册提供的服务
 */
public class RpcServer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    /** 注册的服务 **/
    private ConcurrentHashMap<String, ServiceInfo> providers = new ConcurrentHashMap<>();

    /** socket 处理线程池 */
    private ExecutorService executorService = Executors.newCachedThreadPool();

    /** 服务器关闭标识 **/
    private volatile boolean stop = false;

    /** 服务器 socket server **/
    private volatile ServerSocket serverSocket;

    /** 服务端口 **/
    private int servePort;

    public RpcServer(int servePort) {
        this.servePort = servePort;
    }

    /**
     * 注册服务
     * @param serviceInfo 服务详细信息
     */
    public void registerService(ServiceInfo serviceInfo) {
        providers.put(serviceInfo.getServiceName(), serviceInfo);
    }

    /**
     * 获取服务信息
     * @param serviceName 服务名称
     * @return 服务信息
     */
    public ServiceInfo getServiceInfo(String serviceName) {
        return providers.get(serviceName);
    }

    /**
     * 异步启动 server
     */
    public void startAsync() {
        stop = false;
        new Thread(this).start();
    }

    /**
     * 同步启动 server
     */
    public void start() {
        stop = false;
        this.run();
    }

    /**
     * 关闭 server
     */
    public void shutdown() {
        stop = true;
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (Exception ignore) {
            }
        }
    }

    @Override
    public void run() {
        logger.info("Server is starting");
        try (ServerSocket ss = (serverSocket = new ServerSocket(servePort))) {
            while (!stop) {
                Socket socket = ss.accept();
                ServerWorker worker = new ServerWorker(this, socket);
                executorService.submit(worker);
            }
        } catch (IOException ignore) {
        }
        logger.info("Server is shut down");
    }
}
