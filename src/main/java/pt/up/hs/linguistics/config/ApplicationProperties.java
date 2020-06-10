package pt.up.hs.linguistics.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Linguistics.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final NlpRedis nlpRedis = new NlpRedis();

    public NlpRedis getNlpRedis() {
        return nlpRedis;
    }

    public static class NlpRedis {
        private String hostname = "localhost";
        private int port = 6379;
        private String password;

        public String getHostname() {
            return hostname;
        }

        public void setHostname(String hostname) {
            this.hostname = hostname;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
