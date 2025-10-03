package vn.iotstar.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.Model.Customer;

@RestController
@EnableMethodSecurity
public class CustomerController {

    // Tạo danh sách khách hàng mẫu
    final private List<Customer> customers = List.of(
            new Customer("001", "Trieu Phuc Hieu", "0909000001", "hieutpsptk@gmail.com"),
            new Customer("002", "Phuc Hieu", "0909000002", "phuchieu@gmail.com")
    );
    @GetMapping("/")
    public void root(HttpServletResponse response) throws IOException {
        response.sendRedirect("/hello");
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello is Guest");
    }

    // chỉ ADMIN mới được xem tất cả khách hàng
    @GetMapping("/customer/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Customer>> getCustomerList() {
        return ResponseEntity.ok(this.customers);
    }

    // chỉ USER mới được xem chi tiết theo ID
    @GetMapping("/customer/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") String id) {
        return this.customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
