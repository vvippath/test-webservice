package com.dylan.test.springboot.web;

import com.dylan.test.springboot.domain.posts.Posts;
import com.dylan.test.springboot.domain.posts.PostsRepository;
import com.dylan.test.springboot.web.dto.PostsSaveRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Test
    public void 메인페이지_로딩() {
        String body = this.restTemplate.getForObject("/", String.class);
        assertThat(body).contains("스프링 부트로 시작하는 웹 서비스");

    }

    @Test
    public void 글등록페이지_로딩() {
        String body = this.restTemplate.getForObject("/posts/save", String.class);
        assertThat(body).contains("게시글 등록");
    }

    @Test
    public void 글수정페이지_로딩() {
        String title = "title";
        String content = "content";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);

        String body = this.restTemplate.getForObject("/posts/update/" + all.get(0).getId(), String.class);
        assertThat(body).contains("게시글 수정");
    }
}
