package com.example.demo.loaders;

import com.example.demo.repositories.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Component
public class DataInitializer {
    private final DataSource dataSource;
    private final RoleRepository roleRepository;

    @Bean
    public ApplicationRunner initializer() {
        return args -> {
            if (roleRepository.count() == 0) {
                ResourceDatabasePopulator resourceDatabasePopulator =
                        new ResourceDatabasePopulator(
                                false,
                                false,
                                "UTF-8",
                                new ClassPathResource("data.sql")
                        );
                resourceDatabasePopulator.execute(dataSource);
            }
        };
    }
}
