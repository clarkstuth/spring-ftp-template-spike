package com.clarkstuth.spike.spring;

import org.apache.commons.net.ftp.FTPFile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;

@Configuration
public class AppConfig {

    @Bean
    public DefaultFtpSessionFactory sessionFactory() {
        DefaultFtpSessionFactory sessionFactory = new DefaultFtpSessionFactory();
        sessionFactory.setHost("localhost");
        sessionFactory.setUsername("ftp");
        sessionFactory.setPassword("ftp");
        sessionFactory.setClientMode(2);

        return sessionFactory;
    }

    @Bean
    public CachingSessionFactory<FTPFile> cachingSessionFactory(DefaultFtpSessionFactory sessionFactory) {
        return new CachingSessionFactory<>(sessionFactory);
    }

    @Bean
    public FtpRemoteFileTemplate ftpRemoteFileTemplate(CachingSessionFactory<FTPFile> cachingSessionFactory) {
        FtpRemoteFileTemplate template = new FtpRemoteFileTemplate(cachingSessionFactory);
        template.setRemoteDirectoryExpression(new LiteralExpression("/"));
        return template;
    }

}
