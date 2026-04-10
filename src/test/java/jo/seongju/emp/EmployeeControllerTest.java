package jo.seongju.emp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldListSeededEmployees() throws Exception {
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(greaterThanOrEqualTo(3)))
                .andExpect(jsonPath("$[*].name", hasItem("Kim Minji")));
    }

    @Test
    void shouldCreateAndFetchEmployee() throws Exception {
        var createPayload = """
                {
                  "name": "Choi Hana",
                  "email": "hana.choi@example.com",
                  "department": "OPERATIONS",
                  "status": "ACTIVE",
                  "hiredDate": "2026-04-10",
                  "skills": ["communication", "reporting"]
                }
                """;

        var createResponse = mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.departmentLabel").value("Operations"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode createdEmployee = objectMapper.readTree(createResponse);
        String employeeId = createdEmployee.get("id").asText();

        mockMvc.perform(get("/api/employees/{id}", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Choi Hana"))
                .andExpect(jsonPath("$.skills[0]").value("communication"));
    }

    @Test
    void shouldUpdateAndDeleteEmployee() throws Exception {
        var createPayload = """
                {
                  "name": "Jung Woojin",
                  "email": "woojin.jung@example.com",
                  "department": "ENGINEERING",
                  "status": "PROBATION",
                  "hiredDate": "2026-01-02",
                  "skills": ["java", "testing"]
                }
                """;

        var createResponse = mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createPayload))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String employeeId = objectMapper.readTree(createResponse).get("id").asText();

        var updatePayload = """
                {
                  "name": "Jung Woojin",
                  "email": "woojin.jung@example.com",
                  "department": "ENGINEERING",
                  "status": "ACTIVE",
                  "hiredDate": "2026-01-02",
                  "skills": ["java", "testing", "spring"]
                }
                """;

        mockMvc.perform(put("/api/employees/{id}", employeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatePayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusLabel").value("Active"))
                .andExpect(jsonPath("$.skills[2]").value("testing"));

        mockMvc.perform(delete("/api/employees/{id}", employeeId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/employees/{id}", employeeId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnSystemInfo() throws Exception {
        mockMvc.perform(get("/api/health/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.applicationName").value("employee-management"))
                .andExpect(jsonPath("$.instanceId").exists())
                .andExpect(jsonPath("$.podName").isNotEmpty())
                .andExpect(jsonPath("$.hostName").isNotEmpty());
    }
}
