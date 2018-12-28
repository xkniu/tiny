package io.xkniu.github.tiny.rpc.server;

import java.util.Objects;

/**
 * 服务信息
 */
public class ServiceInfo {

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务实现的类，未设置服务实例时，每次创建新的服务实现
     */
    private Class<?> serviceImpl;

    /**
     * 服务实现实例，如果服务支持单例（无状态信息），可以提供实例
     */
    private Object serviceInstance;

    private ServiceInfo() {
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Class<?> getServiceImpl() {
        return serviceImpl;
    }

    public void setServiceImpl(Class<?> serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    public Object getServiceInstance() {
        return serviceInstance;
    }

    public void setServiceInstance(Object serviceInstance) {
        this.serviceInstance = serviceInstance;
    }


    public static final class ServiceInfoBuilder {
        private String serviceName;
        private Class<?> serviceImpl;
        private Object serviceInstance;

        private ServiceInfoBuilder(String serviceName, Class<?> serviceImpl) {
            Objects.requireNonNull(serviceName);
            Objects.requireNonNull(serviceImpl);

            this.serviceName = serviceName;
            this.serviceImpl = serviceImpl;
        }

        public static ServiceInfoBuilder newBuilder(String serviceName, Class<?> serviceImpl) {
            return new ServiceInfoBuilder(serviceName, serviceImpl);
        }

        public ServiceInfoBuilder withServiceInstance(Object serviceInstance) {
            this.serviceInstance = serviceInstance;
            return this;
        }

        public ServiceInfo build() {
            ServiceInfo serviceInfo = new ServiceInfo();
            serviceInfo.setServiceName(serviceName);
            serviceInfo.setServiceImpl(serviceImpl);
            serviceInfo.setServiceInstance(serviceInstance);
            return serviceInfo;
        }
    }
}
