package tn.esprit.mfb.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("tn.esprit.mfb.entity")
@EnableJpaRepositories("tn.esprit.mfb.Repository")
@EnableTransactionManagement
public class DomainConfig {
}
