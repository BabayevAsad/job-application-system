package com.AsadBabayev.controller;

import com.AsadBabayev.controller.impl.JobControllerImpl;
import com.AsadBabayev.dto.company.CompanyActionDto;
import com.AsadBabayev.dto.job.JobDto;
import com.AsadBabayev.dto.job.JobRequestDto;
import com.AsadBabayev.services.JobService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class JobControllerImplUnitTest {
    private MockMvc mockMvc;
    @Mock
    private JobService jobService;
    @InjectMocks
    private JobControllerImpl jobController;
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String BASE_URL = "/rest/api/job";
    private JobDto jobDto1;
    private JobDto jobDto2;
    private JobRequestDto jobRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(jobController).build();

        CompanyActionDto companyDto = new CompanyActionDto();
        companyDto.setId(1);
        companyDto.setName("OpenAi");
        companyDto.setDescription("AI research company");
        companyDto.setLocation("Krakow");
        companyDto.setEmail("contact@openai.com");
        companyDto.setPhone("+123456789");

        jobDto1 = JobDto.builder()
                .id(10)
                .title("Software Engineer")
                .description("Develop AI applications")
                .requirements("Java, Spring Boot")
                .location("Krakow")
                .salaryRange("10k-15k PLN")
                .active(true)
                .companyActionDto(companyDto)
                .build();

        jobDto2 = JobDto.builder()
                .id(11)
                .title("Data Scientist")
                .description("Analyze ML datasets")
                .requirements("Python, TensorFlow")
                .location("London")
                .salaryRange("12k-18k PLN")
                .active(true)
                .companyActionDto(companyDto)
                .build();

        jobRequestDto = new JobRequestDto();
        jobRequestDto.setTitle("New Job");
        jobRequestDto.setDescription("Job description at least 10 chars");
        jobRequestDto.setRequirements("Java, Spring");
        jobRequestDto.setLocation("Warsaw");
        jobRequestDto.setSalaryRange("8k-12k PLN");
        jobRequestDto.setActive(true);
        jobRequestDto.setCompanyId(1);
    }


    @Test
    void shouldReturnListOfJobs() throws Exception {
        when(jobService.getAllJob()).thenReturn(List.of(jobDto1, jobDto2));

        mockMvc.perform(get(BASE_URL + "/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(10))
                .andExpect(jsonPath("$[0].title").value("Software Engineer"))
                .andExpect(jsonPath("$[0].company.name").value("OpenAi"))
                .andExpect(jsonPath("$[1].id").value(11))
                .andExpect(jsonPath("$[1].title").value("Data Scientist"))
                .andExpect(jsonPath("$[1].company.name").value("OpenAi"));
    }

    @Test
    void shouldReturnJobById() throws Exception {
        when(jobService.getJobById(10)).thenReturn(jobDto1);

        mockMvc.perform(get(BASE_URL + "/getById/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.title").value("Software Engineer"))
                .andExpect(jsonPath("$.company").exists())
                .andExpect(jsonPath("$.company.name").value("OpenAi"))
                .andExpect(jsonPath("$.company.description").value("AI research company"));
    }

    @Test
    void shouldReturnNullWhenJobNotFound() throws Exception {
        when(jobService.getJobById(999)).thenReturn(null);

        mockMvc.perform(get(BASE_URL + "/getById/999"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void shouldSaveJobAndReturnDto() throws Exception {
        when(jobService.saveJob(jobRequestDto)).thenReturn(jobDto1);

        String jsonRequest = mapper.writeValueAsString(jobRequestDto);

        mockMvc.perform(post(BASE_URL + "/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.title").value("Software Engineer"))
                .andExpect(jsonPath("$.company.name").value("OpenAi"));
    }

    @Test
    void shouldUpdateJobAndReturnDto() throws Exception {
        when(jobService.updateJob(jobRequestDto, 10)).thenReturn(jobDto1);

        String jsonRequest = mapper.writeValueAsString(jobRequestDto);

        mockMvc.perform(put(BASE_URL + "/update/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.title").value("Software Engineer"))
                .andExpect(jsonPath("$.company.name").value("OpenAi"));
    }

    @Test
    void shouldDeleteJobById() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/delete/10"))
                .andExpect(status().isOk());

        verify(jobService).deleteJob(10);
    }

    @Test
    void shouldReturnEmptyListWhenNoJobsExist() throws Exception {
        when(jobService.getAllJob()).thenReturn(List.of());

        mockMvc.perform(get(BASE_URL + "/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}