package io.xkniu.github.tiny.core.rpc;

import io.xkniu.github.tiny.core.base.AbstractTestBase;
import io.xkniu.github.tiny.core.rpc.client.RpcProvider;
import io.xkniu.github.tiny.core.rpc.server.RpcServer;
import io.xkniu.github.tiny.core.rpc.server.ServiceInfo;
import io.xkniu.github.tiny.core.rpc.service.GreetingService;
import io.xkniu.github.tiny.core.rpc.service.impl.GreetingServiceImpl;
import org.junit.Before;
import org.junit.Test;

public class RpcTest extends AbstractTestBase {

    /**
     * 启动一个 RPC server
     */
    @Before
    public void testServer() {
        RpcServer rpcServer = new RpcServer(8765);

        // 注册 greeting 服务
        ServiceInfo greetingService = ServiceInfo.ServiceInfoBuilder.newBuilder(GreetingService.class.getName(), GreetingServiceImpl.class).withServiceInstance(new GreetingServiceImpl()).build();
        rpcServer.registerService(greetingService);

        // 异步启动 server
        rpcServer.startAsync();
    }

    @Test
    public void testClient() {
        RpcProvider rpcProvider = new RpcProvider("127.0.0.1", 8765);
        GreetingService greetingService = rpcProvider.provide(GreetingService.class);

        String result0 = greetingService.greeting("test rpc");
        logger.info(result0);

        String result1 = greetingService.greeting("welcome to utopia");
        logger.info(result1);
    }
}
