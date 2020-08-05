package com.codesquad.rare;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.beans.SimpleBeanInfo;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootTest
class RareApplicationTests {

  private final Logger log = LoggerFactory.getLogger(RareApplicationTests.class);

  @Autowired
  ConfigurableEnvironment environment;

  final String VALUE = "rare-server-good";
  final String PASSWORD = "rare";
  final String CIPHER_TEXT_PASSWORD = "ZwTnm0qWd72MQ2x9D/gSpj7YkXu6wP+sUsBQoLU8nyM=";

  @Test
  public void 암호화_테스트_애플리케이션_프로퍼티() throws Exception {
    assertEquals("jdbc:h2:mem:testdb;", environment.getProperty("spring.datasource.url"));
    assertEquals("sa", environment.getProperty("spring.datasource.username"));
    assertEquals("", environment.getProperty("spring.datasource.password"));
   }

   @Test
   public void 암호화_테스트_매번_만드는_암호는_다르다() throws Exception {
     //given
     StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
     encryptor.setPassword(PASSWORD);

     String encryptedValue = encryptor.encrypt(VALUE);
     String encryptedValue2 = encryptor.encrypt(VALUE);

     log.info("##### encryptedValue: {}", encryptedValue);
     log.info("##### encryptedValue2: {}", encryptedValue2);

     assertFalse(encryptedValue2.equals(encryptedValue));
    }

    @Test
    public void 복호화_테스트() throws Exception {
      //given
      StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
      encryptor.setPassword(PASSWORD);

      //when
      String decryptedValue = encryptor.decrypt(CIPHER_TEXT_PASSWORD);

      //then
      assertThat(decryptedValue).isEqualTo(VALUE);
     }
}
