package com.example.securitymethod;

import com.example.securitymethod.controller.ProductController;
import com.example.securitymethod.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void shouldAllowListProductsForAuthenticatedUser() throws Exception {
        when(productService.listProducts()).thenReturn(List.of("Notebook", "Mouse"));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void shouldDenyCreateProductForUserRole() throws Exception {
        mockMvc.perform(post("/products").param("name", "Monitor"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldAllowCreateProductForAdmin() throws Exception {
        when(productService.createProduct("Monitor")).thenReturn("Produto criado: Monitor");

        mockMvc.perform(post("/products").param("name", "Monitor"))
                .andExpect(status().isOk());
    }
}
