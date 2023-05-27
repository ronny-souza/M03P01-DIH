package tech.devinhouse.aviation.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SeatControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Seats List - OK")
    public void listSeats() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/seat")).andExpect(status().isOk());
    }


}
