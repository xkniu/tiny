package io.xkniu.github.tiny.rpc.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * 服务提供者
 */
public class RpcProvider {

    /**
     * The address of the server
     */
    private String serverAddress;

    /**
     * The serve port of the server
     */
    private int port;

    public RpcProvider(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    /**
     * 提供连接远程服务器的代理实现
     * @param serviceInterface 待实现服务接口
     * @param <T> 服务接口类型
     * @return 服务实现代理，调用远端服务
     */
    @SuppressWarnings("unchecked")
    public <T> T provide(Class<T> serviceInterface) {
        if (!serviceInterface.isInterface()) {
            throw new IllegalArgumentException("Param must be interface");
        }
        return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class[]{serviceInterface}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                try (Socket socket = new Socket(serverAddress, port);
                     ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                     ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ) {
                    out.writeUTF(serviceInterface.getName());
                    out.writeUTF(method.getName());
                    out.writeObject(method.getParameterTypes());
                    out.writeObject(args);

                    return in.readObject();
                }
            }
        });
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
