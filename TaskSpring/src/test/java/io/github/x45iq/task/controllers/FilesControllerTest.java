package io.github.x45iq.task.controllers;

import io.github.x45iq.task.Application;
import io.github.x45iq.task.models.FileEntity;
import io.github.x45iq.task.repository.FilesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class FilesControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private FilesRepository filesRepository;

    @BeforeEach
    public void setUp() {
        filesRepository.deleteAll();
    }

    @Test
    void post() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/files/").content("""
                {
                     "title":"tit",
                     "description":"desc",
                     "creationDate":"2010-10-10 10:10:10",
                     "data":"IyMjIEludGVsbGlKIElERUEgIyMjCm91dC8KISoqL3NyYy9tYWluLyoqL291dC8KISoqL3NyYy90ZXN0LyoqL291dC8KCiMjIyBFY2xpcHNlICMjIwouYXB0X2dlbmVyYXRlZAouY2xhc3NwYXRoCi5mYWN0b3J5cGF0aAoucHJvamVjdAouc2V0dGluZ3MKLnNwcmluZ0JlYW5zCi5zdHM0LWNhY2hlCmJpbi8KISoqL3NyYy9tYWluLyoqL2Jpbi8KISoqL3NyYy90ZXN0LyoqL2Jpbi8KCiMjIyBOZXRCZWFucyAjIyMKL25icHJvamVjdC9wcml2YXRlLwovbmJidWlsZC8KL2Rpc3QvCi9uYmRpc3QvCi8ubmItZ3JhZGxlLwoKIyMjIFZTIENvZGUgIyMjCi52c2NvZGUvCgojIyMgTWFjIE9TICMjIwouRFNfU3RvcmU="
                }
                """).contentType(MediaType.APPLICATION_JSON)).andExpectAll(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getFound() throws Exception {
        FileEntity fileEntity = filesRepository.save(FileEntity.builder().title("title").description("description").creationDate(LocalDateTime.now()).data("IyMjIEludGVsbGlKIElERUEgIyMjCm91dC8KISoqL3NyYy8=").build());
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/files/%s".formatted(fileEntity.getId())))
                .andExpectAll(MockMvcResultMatchers.status().isOk(), MockMvcResultMatchers.jsonPath("$.title").value("title"), MockMvcResultMatchers.jsonPath("$.description").value("description"),MockMvcResultMatchers.jsonPath("$.data").value("IyMjIEludGVsbGlKIElERUEgIyMjCm91dC8KISoqL3NyYy8="));
    }

    @Test
    void getNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/files/1")).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void all() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/files/")).andExpect(MockMvcResultMatchers.status().isNoContent());
        Long id1 = filesRepository.save(FileEntity.builder().title("title1").description("").creationDate(LocalDateTime.parse("2009-12-03T10:15:30")).data("IyMjIEludGVsbGlKIElERUEgIyMjCm91dC8KISoqL3NyYy9").build()).getId();
        Long id2 = filesRepository.save(FileEntity.builder().title("title2").description("").creationDate(LocalDateTime.parse("2008-12-03T10:15:30")).data("IyMjIEludGVsbGlKIElERUEgIyMjCm91dC8KISoqL3N").build()).getId();
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/files/")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        [{"id":2,"data":"IyMjIEludGVsbGlKIElERUEgIyMjCm91dC8KISoqL3M=","creationDate":"2008-12-03 10:15:30","title":"title2","description":""},{"id":1,"data":"IyMjIEludGVsbGlKIElERUEgIyMjCm91dC8KISoqL3NyYy8=","creationDate":"2009-12-03 10:15:30","title":"title1","description":""}]
                        """.formatted(id1, id2)));
    }
}