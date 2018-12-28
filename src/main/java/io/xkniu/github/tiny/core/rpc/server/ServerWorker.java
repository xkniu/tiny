package io.xkniu.github.tiny.core.rpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * 服务器的 worker，处理具体的 socket，找到对应服务实现后返回结果.
 */
public class ServerWorker implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    /**
     * 服务器 server
     */
    private RpcServer server;

    /**
     * 待处理的 socket
     */
    private Socket socket;

    public ServerWorker(RpcServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    @Override
    public void run() {
        try (Socket s = socket;
             ObjectInputStream in = new ObjectInputStream(s.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
        ) {
            String serviceName = in.readUTF();

            // read all protocol from input
            String methodName = in.readUTF();
            Class<?>[] parameterTypes = (Class<?>[]) in.readObject();
            Object[] params = (Object[]) in.readObject();

            ServiceInfo serviceInfo = server.getServiceInfo(serviceName);
            if (serviceInfo == null) {
                out.writeObject(null);
            } else {
                Class<?> serviceImpl = serviceInfo.getServiceImpl();
                Object serviceInstance = serviceInfo.getServiceInstance();
                if (serviceInstance == null) {
                    serviceInstance = serviceImpl.getConstructor().newInstance();
                }

                // invoke method
                Method method = serviceImpl.getMethod(methodName, parameterTypes);
                Object result = method.invoke(serviceInstance, params);

                // write to output
                out.writeObject(result);
            }
        } catch (Exception ignore) {
        }
    }
}
