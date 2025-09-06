package com.AsadBabayev.controller;

import com.AsadBabayev.controller.impl.CompanyControllerImpl;
import com.AsadBabayev.dto.company.CompanyDto;
import com.AsadBabayev.dto.company.CompanyRequestDto;
import com.AsadBabayev.services.CompanyService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CompanyControllerImplUnitTest {
//Check Ci pipline
    private MockMvc mockMvc;

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private CompanyControllerImpl companyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();
    }

    @Test
    void shouldReturnCompanyDtoForGivenId() throws Exception {
        CompanyDto dto = CompanyDto.builder()
                .id(1)
                .name("OpenAi")
                .description("Ai research company")
                .location("Krakow")
                .email("contact@openai.com")
                .phone("+123456789")
                .build();

        when(companyService.getCompanyById(1)).thenReturn(dto);

        mockMvc.perform(get("/rest/api/company/getById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("OpenAi"))
                .andExpect(jsonPath("$.description").value("Ai research company"))
                .andExpect(jsonPath("$.location").value("Krakow"))
                .andExpect(jsonPath("$.email").value("contact@openai.com"))
                .andExpect(jsonPath("$.phone").value("+123456789"))
                .andExpect(jsonPath("$.jobs").isArray());
    }

    @Test
    public void shouldReturnListOfCompanies() throws Exception {
        List<CompanyDto> dtoList = List.of(
                CompanyDto.builder()
                        .id(1)
                        .name("OpenAi")
                        .description("AI research company")
                        .build(),
                CompanyDto.builder()
                        .id(2)
                        .name("DeepMind")
                        .description("Deep learning research").build()
        );

        when(companyService.getAllCompany()).thenReturn(dtoList);

        mockMvc.perform(get("/rest/api/company/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("OpenAi"))
                .andExpect(jsonPath("$[0].description").value("AI research company"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("DeepMind"))
                .andExpect(jsonPath("$.[1].description").value("Deep learning research"));
    }

    @Test
    public void shouldSaveCompanyAndReturnDto() throws Exception {
        CompanyRequestDto requestDto = new CompanyRequestDto();
        requestDto.setName("OpenAi");
        requestDto.setDescription("AI research");
        requestDto.setLocation("Krakow");
        requestDto.setEmail("contact@openai.com");
        requestDto.setPhone("+123456789");

        CompanyDto responseDto = CompanyDto.builder()
                .id(1)
                .name("OpenAi")
                .description("AI research").
                location("Krakow")
                .email("contact@openai.com")
                .phone("+123456789")
                .build();

        when(companyService.saveCompany(requestDto)).thenReturn(responseDto);

        ObjectMapper mapper = new ObjectMapper();
        String  jsonResponse = mapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/rest/api/company/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonResponse))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("OpenAi"))
                .andExpect(jsonPath("$.description").value("AI research"));

    }

}