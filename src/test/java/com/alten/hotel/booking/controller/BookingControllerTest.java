package com.alten.hotel.booking.controller;

import com.alten.hotel.booking.entity.BookingEntity;
import com.alten.hotel.booking.repository.BookingRepository;
import com.alten.hotel.bookinghistory.repository.BookingHistoryRepository;
import com.alten.hotel.common.enums.CommonStatus;
import com.alten.hotel.user.entity.UserEntity;
import com.alten.hotel.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FieldDefaults(level = AccessLevel.PRIVATE)
class BookingControllerTest {

    @Autowired
    WebApplicationContext context;

    MockMvc mockMvc;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    BookingHistoryRepository bookingHistoryRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void tearDown() {
        bookingHistoryRepository.deleteAll();
        bookingRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser("admin")
    public void listAllBookingsTest() throws Exception {
        createBooking();
        MvcResult mvcResult = this.mockMvc.perform(get("/booking")).andReturn();
        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());
        Assertions.assertThat(1).isEqualTo(jsonArray.length());
    }

    @Test
    @WithMockUser("admin")
    public void evaluateExistenceFalseTest() throws Exception {
        createBooking();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        MvcResult mvcResult = this.mockMvc.perform(get("/booking/exists")
                        .param("begin", currentDate.plusDays(3).format(formatter))
                        .param("end", currentDate.plusDays(5).format(formatter)))
                .andReturn();
        boolean exists = Boolean.parseBoolean(mvcResult.getResponse().getContentAsString());
        Assertions.assertThat(exists).isFalse();
    }

    @Test
    @WithMockUser("admin")
    public void evaluateExistenceTrueTest() throws Exception {
        createBooking();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        MvcResult mvcResult = this.mockMvc.perform(get("/booking/exists")
                        .param("begin", currentDate.plusDays(1).format(formatter))
                        .param("end", currentDate.plusDays(2).format(formatter)))
                .andReturn();
        boolean exists = Boolean.parseBoolean(mvcResult.getResponse().getContentAsString());
        Assertions.assertThat(exists).isTrue();
    }

    @Test
    @WithMockUser("admin")
    public void cancelBookingTest() throws Exception {
        createBooking();
        List<BookingEntity> bookingEntityList = bookingRepository.findAll();
        String bookingUrl = String.format("/booking/%s", bookingEntityList.get(0).getId());
        MvcResult mvcResult = this.mockMvc.perform(delete(bookingUrl)).andReturn();
        JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString());

        Assertions.assertThat(bookingEntityList.get(0).getId()).isEqualTo(UUID.fromString(json.getString("bookingId")));
        Assertions.assertThat("UNACTIVE").isEqualTo(json.get("status"));
    }

    @Test
    @WithMockUser("admin")
    public void saveBookingTest() throws Exception {
        UserEntity user = createUser();
        LocalDate currentDate = LocalDate.now();

        JSONObject body = new JSONObject();
        body.put("begin", currentDate.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        body.put("end", currentDate.plusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        body.put("userId", user.getId());

        MvcResult mvcResult = this.mockMvc.perform(post("/booking").contentType(APPLICATION_JSON).content(body.toString())).andReturn();
        JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString());

        Assertions.assertThat(body.getString("begin")).isEqualTo(json.getString("begin"));
        Assertions.assertThat(body.getString("end")).isEqualTo(json.getString("end"));
        Assertions.assertThat(body.getString("userId")).isEqualTo(json.getString("userId"));
    }

    @Test
    @WithMockUser("admin")
    public void updateBookingTest() throws Exception {
        BookingEntity bookingEntity = createBooking();

        LocalDate currentDate = LocalDate.now();

        JSONObject body = new JSONObject();
        body.put("begin", currentDate.plusDays(5).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        body.put("end", currentDate.plusDays(6).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        String updateUrl = String.format("/booking/%s", bookingEntity.getId());
        MvcResult mvcResult = this.mockMvc.perform(put(updateUrl).contentType(APPLICATION_JSON).content(body.toString())).andReturn();
        JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString());

        Assertions.assertThat(body.getString("begin")).isEqualTo(json.getString("begin"));
        Assertions.assertThat(body.getString("end")).isEqualTo(json.getString("end"));
        Assertions.assertThat(bookingEntity.getUser().getId()).isEqualTo(UUID.fromString(json.getString("userId")));
        Assertions.assertThat(bookingEntity.getId()).isEqualTo(UUID.fromString(json.getString("bookingId")));
    }

    @Test
    @WithMockUser("admin")
    public void findByIdTest() throws Exception {
        BookingEntity bookingEntity = createBooking();

        String getUrl = String.format("/booking/%s", bookingEntity.getId());
        MvcResult mvcResult = this.mockMvc.perform(get(getUrl)).andReturn();
        JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString());

        Assertions.assertThat(bookingEntity.getUser().getId()).isEqualTo(UUID.fromString(json.getString("userId")));
        Assertions.assertThat(bookingEntity.getId()).isEqualTo(UUID.fromString(json.getString("bookingId")));
    }

    private UserEntity createUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("name");
        userEntity.setUsername("username");
        userEntity.setPassword("password");
        userEntity.setAlias("alias");
        userEntity.setTries(0);
        userEntity.setEmail("email");
        userEntity.setPhone("phone");
        userEntity.setDocument("document");
        userEntity.setStatus(CommonStatus.ACTIVE);
        return userRepository.save(userEntity);
    }

    private BookingEntity createBooking() {
        LocalDate currentDate = LocalDate.now();
        Instant begin = currentDate.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant end = currentDate.plusDays(2).atTime(23, 59, 59).toInstant(ZoneOffset.UTC);

        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setBegin(begin);
        bookingEntity.setEnd(end);
        bookingEntity.setBookingCode(String.valueOf(bookingEntity.hashCode()));
        bookingEntity.setStatus(CommonStatus.ACTIVE);
        bookingEntity.setUser(createUser());
        return bookingRepository.save(bookingEntity);
    }
}
