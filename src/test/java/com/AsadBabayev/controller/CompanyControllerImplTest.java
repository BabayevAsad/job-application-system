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
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CompanyControllerImplUnitTest {
    private MockMvc mockMvc;
    @Mock
    private CompanyService companyService;
    @InjectMocks
    private CompanyControllerImpl companyController;
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String BASE_URL ="/rest/api/company";
    private CompanyDto openAiDto;
    private CompanyDto deepMindDto;
    private CompanyRequestDto requestDto;
    private CompanyDto responseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();

        openAiDto = CompanyDto.builder()
                .id(1)
                .name("OpenAi")
                .description("AI research company")
                .location("Krakow")
                .email("contact@openai.com")
                .phone("+123456789")
                .build();

        deepMindDto = CompanyDto.builder()
                .id(2)
                .name("DeepMind")
                .description("Deep learning research")
                .build();

        requestDto = new CompanyRequestDto();
        responseDto = new CompanyDto();

        BeanUtils.copyProperties(openAiDto, responseDto);
        BeanUtils.copyProperties(responseDto, requestDto);
    }

    @Test
    void shouldReturnCompanyDtoForGivenId() throws Exception {

        when(companyService.getCompanyById(1)).thenReturn(openAiDto);

        mockMvc.perform(get(BASE_URL+"/getById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("OpenAi"))
                .andExpect(jsonPath("$.description").value("AI research company"))
                .andExpect(jsonPath("$.location").value("Krakow"))
                .andExpect(jsonPath("$.email").value("contact@openai.com"))
                .andExpect(jsonPath("$.phone").value("+123456789"))
                .andExpect(jsonPath("$.jobs").isArray());
    }

    @Test
    public void shouldReturnListOfCompanies() throws Exception {
        List<CompanyDto> dtoList = List.of(openAiDto,deepMindDto);

        when(companyService.getAllCompany()).thenReturn(dtoList);

        mockMvc.perform(get(BASE_URL+"/getAll"))
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

        when(companyService.saveCompany(requestDto)).thenReturn(responseDto);

        String  jsonResponse = mapper.writeValueAsString(requestDto);

        mockMvc.perform(post(BASE_URL+"/save")
                     .contentType(MediaType.APPLICATION_JSON)
                     .content(jsonResponse))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("OpenAi"))
                .andExpect(jsonPath("$.description").value("AI research company"));
    }

    @Test
    public void shouldUpdateCompanyAndReturnCompanyDto() throws Exception {

    when(companyService.updateCompany(requestDto,1)).thenReturn(responseDto);

    String jsonResponse = mapper.writeValueAsString(requestDto);

    mockMvc.perform(put(BASE_URL+"/update/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonResponse))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("OpenAi"))
            .andExpect(jsonPath("$.description").value("AI research company"));

    }

    @Test
    public void shouldDeleteCompany() throws Exception {

        mockMvc.perform(delete(BASE_URL+"/delete/1"))
                        .andExpect(status().isOk());

        verify(companyService).deleteCompany(1);
    }
}