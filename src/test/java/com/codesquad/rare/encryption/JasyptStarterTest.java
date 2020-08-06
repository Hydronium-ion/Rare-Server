package com.codesquad.rare.encryption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootTest
public class JasyptStarterTest {

  private final Logger log = LoggerFactory.getLogger(JasyptStarterTest.class);

  @Autowired
  ConfigurableEnvironment environment;

  final String VALUE = "rare-server-good";
  final String PASSWORD = "rare";
  final String CIPHER_TEXT_PASSWORD = "ZwTnm0qWd72MQ2x9D/gSpj7YkXu6wP+sUsBQoLU8nyM=";

  @DisplayName("application.yml에 있는 암호화 된 속성 테스트")
  @Test
  public void testEncryptionApplicationProperties() throws Exception {
    log.info("example: {}", environment.getProperty("spring.datasource.url"));
    assertEquals("jdbc:h2:mem:testdb;DB_CLOSE_ON_EXIT=FALSE", environment.getProperty("spring.datasource.url"));
    assertEquals("sa", environment.getProperty("spring.datasource.username"));
    assertEquals("", environment.getProperty("spring.datasource.password"));
  }

  @DisplayName("매 번 만들어지는 암호는 같지 않다고 증명하는 테스트")
  @Test
  public void testEncryptionEveryTimeCreatedPasswordNotEquals() throws Exception {
    //given
    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
    encryptor.setPassword(PASSWORD);

    String encryptedValue = encryptor.encrypt(VALUE);
    String encryptedValue2 = encryptor.encrypt(VALUE);

    log.info("##### encryptedValue: {}", encryptedValue);
    log.info("##### encryptedValue2: {}", encryptedValue2);

    assertFalse(encryptedValue2.equals(encryptedValue));
  }

  @DisplayName("복호화_테스트")
  @Test
  public void testDecryption() throws Exception {
    //given
    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
    encryptor.setPassword(PASSWORD);

    //when
    String decryptedValue = encryptor.decrypt(CIPHER_TEXT_PASSWORD);

    //then
    assertThat(decryptedValue).isEqualTo(VALUE);
  }
}
