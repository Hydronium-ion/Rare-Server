package com.codesquad.rare.domain.post;

import com.codesquad.rare.domain.account.Account;
import com.codesquad.rare.domain.account.AccountRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Component
public class PostDataRunner implements ApplicationRunner {

  private final PostRepository postRepository;

  private final AccountRepository accountRepository;

  private final static String[] luda = new String[] {
      "http://talkimg.imbc.com/TVianUpload/tvian/TViews/image/2018/05/25/GK56FYRKQNhU636628664137530011.jpg",
      "https://i.ytimg.com/vi/rpQGtR9GUdY/maxresdefault.jpg",
      "https://t1.daumcdn.net/cfile/tistory/99D059475D0DA61412",
      "https://upload.wikimedia.org/wikipedia/commons/8/80/181005_%EC%9A%B0%EC%A3%BC%EC%86%8C%EB%85%80_%EB%A3%A8%EB%8B%A4_%EC%97%AC%EB%A6%84_%EC%B2%AD%EC%9B%90%EC%83%9D%EB%AA%85%EC%B6%95%EC%A0%9C_%2815%29.jpg",
      "https://www.shinailbo.co.kr/news/photo/201804/1065837_368226_5920.png",
      "https://media.vingle.net/images/ca_l/58l5wrwtf9.jpg",
      "https://media.vingle.net/images/ca_l/qkcuuzmokd.jpg",
      "https://media.vingle.net/images/ca_l/1srkbuplrb.jpg",
      "https://media.vingle.net/images/ca_l/bfuo4dx9mb.jpg",
      "https://media.vingle.net/images/ca_l/6b9l45l7mo.jpg",
      "https://media.vingle.net/images/ca_l/fjzzk2e6hr.jpg",
      "https://media.vingle.net/images/ca_l/kaewqrbum9.jpg",
      "https://media.vingle.net/images/ca_l/56vpwpwh4m.jpg",
      "https://media.vingle.net/images/ca_l/2xszp1u8ne.jpg",
      "https://media.vingle.net/images/ca_l/vj57sjedbk.jpg",
      "https://media.vingle.net/images/ca_l/i6yirrkgb2.jpg",
      "https://media.vingle.net/images/ca_l/prs0xqrj6a.jpg",
      "https://media.vingle.net/images/ca_l/lhd8rwrd1v.jpg",
      "https://media.vingle.net/images/ca_l/bl7rd8o8w6.jpg",
      "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQGnpGZaha-U_4gtmkyu_qUDSPJHdnMtPt55Q&usqp=CAU",
      "https://img.hankyung.com/photo/201810/03.17984849.1.jpg",
      "https://i.ytimg.com/vi/_1aEirp0rYs/maxresdefault.jpg",
      "https://pbs.twimg.com/media/ED4Nf4rU0AIcfnv.jpg",
      "https://www.city.kr/files/attach/images/238/656/965/021/28567cfef89005fc4c80310f0b9a065b.jpg",
      "https://i.ytimg.com/vi/tWLCJGJzE2k/maxresdefault.jpg",
      "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcShyEGEAjEzBWcR2CY3z2SZgJg9BvK-8lRfhQ&usqp=CAU",
      "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQDyQzMygF5L0oZhyB2pes0PN_Arhgu0GS2OA&usqp=CAU",
      "https://www.city.kr/files/attach/images/238/656/965/021/16b7ca888ab6423287532a03126f6059.jpg",
      "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSJbCMsTJ9qbfBKVbDejIXbrhCkudKmJAQ61Q&usqp=CAU",
      "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSAD3lN8k5ax3KezaxRKYEdt_WeUtOIXWU1Fw&usqp=CAU",
      "https://upload.wikimedia.org/wikipedia/commons/8/89/181005_%EC%9A%B0%EC%A3%BC%EC%86%8C%EB%85%80_%EB%A3%A8%EB%8B%A4_%EC%97%AC%EB%A6%84_%EC%B2%AD%EC%9B%90%EC%83%9D%EB%AA%85%EC%B6%95%EC%A0%9C_%283%29.jpg",
      "https://pbs.twimg.com/media/Ed3XIrHU0AIqz5n.jpg",
      "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQ5AQ7shr5vssJKzVktO8lnH1c-sitxPmOO3g&usqp=CAU",
      "https://i.ytimg.com/vi/Yknwa-SSRsw/maxresdefault.jpg",
      "https://i.pinimg.com/originals/97/d8/77/97d877391d77d4d7f902b31120d624ff.jpg",
      "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQMzI4GgvdzznK174GwH9r3Ug7HCYu4Irwagw&usqp=CAU",
      "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcT3Lyl3LFkMUCSBuqJSjZliyI_-b8R681iIwg&usqp=CAU",
      "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQUviV6ZEbeKY-weBWHfFocuf8vCRgKtWN5PA&usqp=CAU",
      "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTHtAiuqFEtDA7akXxK7VSnsMTjfm9xWH-Fng&usqp=CAU",
      "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR9heKrgcIhBBN0XwDee5hzPy6Yx-QuG-yaDQ&usqp=CAU",
      "https://scontent-iad3-1.cdninstagram.com/v/t51.2885-15/sh0.08/e35/c0.180.1440.1440a/s640x640/107282414_320596419118799_6888967052950248295_n.jpg?_nc_ht=scontent-iad3-1.cdninstagram.com&_nc_cat=111&_nc_ohc=ffzHG0LErSMAX-4gsrq&oh=f3a6823ab8a83a0742db9688459f0b28&oe=5F4296AA",
      "https://i2.tcafe2a.com/2007/20200727190407_43b89c81f2578ba7dbfba25273f9aa42_89wr.jpg"
  };

  @Override
  public void run(ApplicationArguments args) {

    Random random = new Random();
    LocalDateTime localDateTime = LocalDateTime.now();
    List<Post> postList = new ArrayList<>();
    int length = luda.length;

    Account won = Account.builder()
        .name("won")
        .avatarUrl("https://img.hankyung.com/photo/201906/03.19979855.1.jpg")
        .build();

    saveAccount(won);

    for (int i = 1; i <= 10; i++) {
      Post post = Post.builder()
          .title(i + "번째 포스팅 입니다")
          .content("이런 저런 내용이 담겨있어요")
          .author(won)
          .likes(random.nextInt(99))
          .tags(i + "번")
          .views(random.nextInt(999))
          .thumbnail(luda[random.nextInt(length)])
          .createdAt(localDateTime.plusDays(random.nextInt(10 - 5 + 1) + 5))
          .build();

      postList.add(post);
    }
    save(postList);
  }

  @Transactional
  public void saveAccount(Account account) {
    accountRepository.save(account);
  }

  @Transactional
  public void save(List<Post> postList) {
    postRepository.saveAll(postList);
  }
}
