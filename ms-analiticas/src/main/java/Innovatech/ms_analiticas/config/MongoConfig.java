package Innovatech.ms_analiticas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * Configuración de MongoDB para habilitar auditoría automática (@CreatedDate).
 */
@Configuration
@EnableMongoAuditing
public class MongoConfig {
}
