package io.vicp.frlib.nio.netty_3.textprotocols.securechat;

import io.vicp.frlib.nio.netty_3.ServerUtil;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.ssl.SslContext;
import org.jboss.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

/**
 * description : telnet之上添加ssl
 * user : zhoudr
 * time : 2017/6/27 18:03
 */

public class Server {
    public static void main(String[] args) {
        try {
            SelfSignedCertificate selfSignedCertificate = new SelfSignedCertificate();
            SslContext sslContext = SslContext.newServerContext(selfSignedCertificate.certificate(), selfSignedCertificate.privateKey());
            ChannelPipelineFactory pipelineFactory = new SecureServerPipelineFactory(sslContext);
            ServerUtil.startServer(pipelineFactory, 61284);
        } catch (CertificateException | SSLException e) {
            e.printStackTrace();
        }
    }
}
