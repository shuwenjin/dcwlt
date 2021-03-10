package com.dcits.dcwlt.pay.batch.sftp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SftpConfig {

    /**
     * 主机地址
     */
    @Value("${sftp.client.host}")
    private String host;

    @Value("${sftp.client.port}")
    private Integer port;

    @Value("${sftp.client.protocol}")
    private String protocol;

    @Value("${sftp.client.username}")
    private String username;

    @Value("${sftp.client.password}")
    private String password;

    @Value("${sftp.client.root}")
    private String root;

    @Value("${sftp.client.privateKey}")
    private String privateKey;

    @Value("${sftp.client.passphrase}")
    private String passphrase;

    @Value("${sftp.client.sessionStrictHostKeyChecking}")
    private String sessionStrictHostKeyChecking;

    @Value("${sftp.client.sessionConnectTimeout}")
    private Integer sessionConnectTimeout;

    @Value("${sftp.client.channelConnectedTimeout}")
    private Integer channelConnectedTimeout;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }

    public String getSessionStrictHostKeyChecking() {
        return sessionStrictHostKeyChecking;
    }

    public void setSessionStrictHostKeyChecking(String sessionStrictHostKeyChecking) {
        this.sessionStrictHostKeyChecking = sessionStrictHostKeyChecking;
    }

    public Integer getSessionConnectTimeout() {
        return sessionConnectTimeout;
    }

    public void setSessionConnectTimeout(Integer sessionConnectTimeout) {
        this.sessionConnectTimeout = sessionConnectTimeout;
    }

    public Integer getChannelConnectedTimeout() {
        return channelConnectedTimeout;
    }

    public void setChannelConnectedTimeout(Integer channelConnectedTimeout) {
        this.channelConnectedTimeout = channelConnectedTimeout;
    }

    @Override
    public String toString() {
        return "SftpConfig{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", protocol='" + protocol + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", root='" + root + '\'' +
                ", privateKey='" + privateKey + '\'' +
                ", passphrase='" + passphrase + '\'' +
                ", sessionStrictHostKeyChecking='" + sessionStrictHostKeyChecking + '\'' +
                ", sessionConnectTimeout=" + sessionConnectTimeout +
                ", channelConnectedTimeout=" + channelConnectedTimeout +
                '}';
    }
}
