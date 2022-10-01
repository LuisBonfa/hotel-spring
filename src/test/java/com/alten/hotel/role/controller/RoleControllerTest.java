package com.alten.hotel.role.controller;


import com.alten.hotel.common.enums.CommonStatus;
import com.alten.hotel.role.entity.RoleEntity;
import com.alten.hotel.role.repository.RoleRepository;
import com.alten.hotel.userrole.repository.UserRoleRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RoleControllerTest {

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class ForbiddenTest {

        @Autowired
        WebApplicationContext context;

        @Autowired
        RoleRepository roleRepository;

        MockMvc mockMvc;

        @BeforeEach
        public void setup() {
            this.mockMvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(springSecurity())
                    .build();
        }

        @Test
        @WithMockUser(value = "bonfa")
        public void listAllRolesForbiddenTest() throws Exception {
            MvcResult mvcResult = this.mockMvc.perform(get("/role")).andReturn();
            Assertions.assertThat(403).isEqualTo(mvcResult.getResponse().getStatus());
        }


        @Test
        @WithMockUser(value = "bonfa")
        public void findByIdForbiddenTest() throws Exception {
            List<RoleEntity> roles = roleRepository.findAll();
            String roleUrl = String.format("/role/%s", roles.get(0).getId());
            MvcResult mvcResult = this.mockMvc.perform(get(roleUrl)).andReturn();
            Assertions.assertThat(403).isEqualTo(mvcResult.getResponse().getStatus());
        }

        @Test
        @WithMockUser(value = "bonfa")
        public void deleteForbiddenTest() throws Exception {
            String roleUrl = String.format("/role/%s", UUID.randomUUID());
            MvcResult mvcResult = this.mockMvc.perform(delete(roleUrl)).andReturn();
            Assertions.assertThat(403).isEqualTo(mvcResult.getResponse().getStatus());
        }

        @Test
        @WithMockUser("admin")
        public void saveRoleForbiddenTest() throws Exception {
            JSONObject body = new JSONObject();
            body.put("name", "teste");

            MvcResult mvcResult = this.mockMvc.perform(post("/role").contentType(APPLICATION_JSON).content(body.toString())).andReturn();
            Assertions.assertThat(403).isEqualTo(mvcResult.getResponse().getStatus());
        }

        @Test
        @WithMockUser("admin")
        public void updateBookingForbiddenTest() throws Exception {
            JSONObject body = new JSONObject();
            body.put("name", "teste");

            String updateUrl = String.format("/role/%s", UUID.randomUUID());
            MvcResult mvcResult = this.mockMvc.perform(post(updateUrl).contentType(APPLICATION_JSON).content(body.toString())).andReturn();
            Assertions.assertThat(403).isEqualTo(mvcResult.getResponse().getStatus());
        }
    }

    @Nested
    @Transactional
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class WorkingTest {

        @Autowired
        WebApplicationContext context;

        MockMvc mockMvc;

        @Autowired
        RoleRepository roleRepository;

        @Autowired
        UserRoleRepository userRoleRepository;

        @BeforeEach
        public void setup() {
            this.mockMvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(springSecurity())
                    .build();
        }

        @AfterEach
        public void tearDown() {
            userRoleRepository.deleteAll();
            roleRepository.deleteAll();
        }

        @Test
        @WithMockUser(value = "bonfa", authorities = {"admin"})
        public void listAllRolesTest() throws Exception {
            MvcResult mvcResult = this.mockMvc.perform(get("/role")).andReturn();
            JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());
            Assertions.assertThat(2).isEqualTo(jsonArray.length());
        }

        @Test
        @WithMockUser(value = "bonfa", authorities = {"admin"})
        public void findByIdTest() throws Exception {
            List<RoleEntity> roles = roleRepository.findAll();
            String roleUrl = String.format("/role/%s", roles.get(0).getId());
            MvcResult mvcResult = this.mockMvc.perform(get(roleUrl)).andReturn();
            JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString());
            Assertions.assertThat(roles.get(0).getName()).isEqualTo(json.getString("name"));
        }

        @Test
        @WithMockUser(value = "bonfa", authorities = {"admin"})
        public void deleteTest() throws Exception {
            RoleEntity role = new RoleEntity();
            role.setStatus(CommonStatus.ACTIVE);
            role.setName("teste");
            roleRepository.save(role);

            String roleUrl = String.format("/role/%s", role.getId());
            MvcResult mvcResult = this.mockMvc.perform(delete(roleUrl)).andReturn();
            JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString());
            Assertions.assertThat(role.getName()).isEqualTo(json.getString("name"));
            Assertions.assertThat(role.getStatus().name()).isEqualTo(json.getString("status"));
        }

        @Test
        @WithMockUser(value = "admin", authorities = {"admin"})
        public void saveBookingTest() throws Exception {
            JSONObject body = new JSONObject();
            body.put("name", "teste");

            MvcResult mvcResult = this.mockMvc.perform(post("/role").contentType(APPLICATION_JSON).content(body.toString())).andReturn();
            JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString());

            Assertions.assertThat("teste").isEqualTo(json.getString("name"));
            Assertions.assertThat(CommonStatus.ACTIVE.name()).isEqualTo(json.getString("status"));
        }

        @Test
        @WithMockUser(value = "admin", authorities = {"admin"})
        public void updateBookingTest() throws Exception {
            List<RoleEntity> roles = roleRepository.findAll();
            RoleEntity role = roles.get(0);

            JSONObject body = new JSONObject();
            body.put("name", "teste");

            String roleUrl = String.format("/role/%s", role.getId());
            MvcResult mvcResult = this.mockMvc.perform(put(roleUrl).contentType(APPLICATION_JSON).content(body.toString())).andReturn();
            JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString());

            Assertions.assertThat(role.getName()).isEqualTo(json.getString("name"));
            Assertions.assertThat(role.getStatus().name()).isEqualTo(json.getString("status"));
        }
    }
}