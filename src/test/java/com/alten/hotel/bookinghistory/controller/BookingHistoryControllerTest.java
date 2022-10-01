package com.alten.hotel.bookinghistory.controller;

import com.alten.hotel.booking.dto.BookingDTO;
import com.alten.hotel.booking.entity.BookingEntity;
import com.alten.hotel.booking.repository.BookingRepository;
import com.alten.hotel.booking.service.BookingService;
import com.alten.hotel.bookinghistory.repository.BookingHistoryRepository;
import com.alten.hotel.common.enums.CommonStatus;
import com.alten.hotel.user.entity.UserEntity;
import com.alten.hotel.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.json.JSONArray;
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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@FieldDefaults(level = AccessLevel.PRIVATE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookingHistoryControllerTest {

    @Autowired
    WebApplicationContext context;

    MockMvc mockMvc;

    @Autowired
    BookingService bookingService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    BookingHistoryRepository bookingHistoryRepository;

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
    @WithMockUser(value = "admin", authorities = {"admin"})
    public void listAllBookingsTest() throws Exception {
        BookingEntity bookingEntity = createBooking();

        LocalDate currentDate = LocalDate.now();
        LocalDate newBegin = currentDate.plusDays(4);
        LocalDate newEnd = currentDate.plusDays(5);

        BookingDTO dto = new BookingDTO();
        dto.setBegin(newBegin);
        dto.setEnd(newEnd);

        bookingService.update(bookingEntity.getId(), dto);
        MvcResult mvcResult = this.mockMvc.perform(get("/booking-history")).andReturn();
        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());
        Assertions.assertThat(1).isEqualTo(jsonArray.length());
    }

    @Test
    @WithMockUser(value = "admin")
    public void listAllBookingsForbiddenTest() throws Exception {
        BookingEntity bookingEntity = createBooking();

        LocalDate currentDate = LocalDate.now();
        LocalDate newBegin = currentDate.plusDays(4);
        LocalDate newEnd = currentDate.plusDays(5);

        BookingDTO dto = new BookingDTO();
        dto.setBegin(newBegin);
        dto.setEnd(newEnd);

        bookingService.update(bookingEntity.getId(), dto);
        MvcResult mvcResult = this.mockMvc.perform(get("/booking-history")).andReturn();
        Assertions.assertThat(403).isEqualTo(mvcResult.getResponse().getStatus());
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