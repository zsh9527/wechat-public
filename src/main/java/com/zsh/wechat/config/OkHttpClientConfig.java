package com.zsh.wechat.config;

import lombok.AllArgsConstructor;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * OkHttpClient 配置
 */
@Configuration
@AllArgsConstructor
public class OkHttpClientConfig {

    private final HttpProp httpProp;

    /**
     * 配置okHttpClient
     */
    @Bean
    public OkHttpClient okHttpClient() {
        // 最大请求数
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(httpProp.getMaxRequest());
        dispatcher.setMaxRequestsPerHost(httpProp.getMaxPerHostRequest());
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // TODO 调试
        //configProxy(builder);
        OkHttpClient okHttpClient = builder.retryOnConnectionFailure(true)
            .connectTimeout(httpProp.getConnectTimeOut(), TimeUnit.SECONDS)
            .writeTimeout(httpProp.getReadTimeOut(),TimeUnit.SECONDS)
            .readTimeout(httpProp.getWriteTimeOut(),TimeUnit.SECONDS)
            .connectionPool(new ConnectionPool(httpProp.getMaxConnection(), 5, TimeUnit.MINUTES))
            .dispatcher(dispatcher)
            .build();
        return okHttpClient;
    }

    /**
     * 调试使用, 配置代理
     * TODO 后续需要配置代理池
     */
    private void configProxy(OkHttpClient.Builder builder) {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 18888));
        builder.proxy((proxy))
            .sslSocketFactory(sslSocketFactory(), x509TrustManager());
    }

    private X509TrustManager x509TrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {}

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {}

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    private SSLSocketFactory sslSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[] {x509TrustManager()}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }
}
