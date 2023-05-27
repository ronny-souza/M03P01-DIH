package tech.devinhouse.aviation.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import tech.devinhouse.aviation.model.transport.CreateBoardingTicketDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PassengerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private static Gson gson;

    @BeforeAll
    public static void executeBeforeAll() {
        PassengerControllerTests.gson = new Gson();
    }

    @Test
    @DisplayName("Passenger CPF Search - OK")
    public void findByCpf() throws Exception {
        String cpfToFind = "333.333.333-33";
        this.mockMvc.perform(MockMvcRequestBuilders.get("/passenger/{cpf}", cpfToFind)).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Passenger Search - CPF Not Found")
    public void findByCpfNotFound() throws Exception {
        String cpfToFind = "333.333.333-3";
        this.mockMvc.perform(MockMvcRequestBuilders.get("/passenger/{cpf}", cpfToFind)).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Passenger List - OK")
    public void listPassengers() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/passenger")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Passenger Checkin - OK")
    public void realizeCheckin() throws Exception {
        CreateBoardingTicketDTO createBoardingTicketDTO = new CreateBoardingTicketDTO("333.333.333-33", "5B", true);
        String bodyAsJson = gson.toJson(createBoardingTicketDTO);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/passenger/confirmation").contentType("application/json").content(bodyAsJson)).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Passenger Checkin - CPF Not Found")
    public void realizeCheckinPassengerNotFound() throws Exception {
        CreateBoardingTicketDTO createBoardingTicketDTO = new CreateBoardingTicketDTO("333.333.333-3", "5B", true);
        String bodyAsJson = gson.toJson(createBoardingTicketDTO);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/passenger/confirmation").contentType("application/json").content(bodyAsJson)).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Passenger Checkin - Seat Currently Reserved")
    public void realizeCheckinSeatCurrentlyReserved() throws Exception {
        CreateBoardingTicketDTO createBoardingTicketDTO = new CreateBoardingTicketDTO("333.333.333-33", "5B", true);
        String bodyAsJson = gson.toJson(createBoardingTicketDTO);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/passenger/confirmation").contentType("application/json").content(bodyAsJson)).andExpect(status().isOk());
        this.mockMvc.perform(MockMvcRequestBuilders.post("/passenger/confirmation").contentType("application/json").content(bodyAsJson)).andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Passenger Checkin - Passenger is Under Age to Emergency Seat")
    public void realizeCheckinPassengerUnderAgeEmergencySeat() throws Exception {
        CreateBoardingTicketDTO createBoardingTicketDTO = new CreateBoardingTicketDTO("123.456.789-10", "5B", true);
        String bodyAsJson = gson.toJson(createBoardingTicketDTO);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/passenger/confirmation").contentType("application/json").content(bodyAsJson)).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Passenger Checkin - Is Emergency Seat and Passenger has Not Checked Bags")
    public void realizeCheckinEmergencySeatAndPassengerHasNotCheckedBags() throws Exception {
        CreateBoardingTicketDTO createBoardingTicketDTO = new CreateBoardingTicketDTO("333.333.333-33", "5B", false);
        String bodyAsJson = gson.toJson(createBoardingTicketDTO);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/passenger/confirmation").contentType("application/json").content(bodyAsJson)).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Passenger Checkin - Recalculate Miles")
    public void realizeCheckinRecalculateMiles() throws Exception {
        CreateBoardingTicketDTO createBoardingTicketDTO = new CreateBoardingTicketDTO("333.333.333-33", "5B", true);
        String bodyAsJson = gson.toJson(createBoardingTicketDTO);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/passenger/confirmation").contentType("application/json").content(bodyAsJson)).andExpect(status().isOk());

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get("/passenger/{cpf}", createBoardingTicketDTO.cpf())).andExpect(status().isOk()).andReturn();
        JsonObject responseAsJson = gson.fromJson(response.getResponse().getContentAsString(), JsonObject.class);

        int miles = responseAsJson.get("miles").getAsInt();
        assertEquals(180, miles);
    }
}
