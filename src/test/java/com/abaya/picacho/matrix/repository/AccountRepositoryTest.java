package com.abaya.picacho.matrix.repository;

import com.abaya.picacho.matrix.entity.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountRepositoryTest {
  @Autowired
  private AccountRepository repository;

  @Test
  public void shouldFindRepository() {
    assertThat(repository).isNotNull();
  }

  @Test
  public void saveAndQueryAccount() {
    Account account = new Account("ZhangSan", "abcd1234", "张三", "user");
    repository.save(account);

    Account result = repository.findByUsername("ZhangSan").get(0);
    assertThat(result.getName()).isEqualToIgnoringCase("张三");
  }
}
