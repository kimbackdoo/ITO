package kr.co.metasoft.test.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.map.repository.config.EnableMapRepositories;

@Configuration
@EnableMapRepositories (basePackages = {"kr.co.metasoft.test.common.repository.keyvalue"})
public class KeyValueConfig {

}