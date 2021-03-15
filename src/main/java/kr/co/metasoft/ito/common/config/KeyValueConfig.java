package kr.co.metasoft.ito.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.map.repository.config.EnableMapRepositories;

@Configuration
@EnableMapRepositories (basePackages = {"kr.co.metasoft.ito.common.keyvalue"})
public class KeyValueConfig {

}