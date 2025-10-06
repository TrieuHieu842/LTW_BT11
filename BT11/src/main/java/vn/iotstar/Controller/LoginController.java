package vn.iotstar.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.iotstar.Entity.Product;
import vn.iotstar.Service.ProductService;

import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private ProductService service;

    // ✅ Handler khi đăng nhập thành công
    @PostMapping("/login_success_handler")
    public String loginSuccessHandler() {
        System.out.println("✅ Logging user login success...");
        return "redirect:/"; // quay về trang chủ
    }

    // ✅ Handler khi đăng nhập thất bại
    @PostMapping("/login_failure_handler")
    public String loginFailureHandler() {
        System.out.println("❌ Login failure handler....");
        return "login";
    }

    // ✅ Trang chủ: hiển thị danh sách sản phẩm
    @GetMapping("/list")
    public String viewHomePage(Model model) {
        List<Product> listProducts = service.listAll();
        model.addAttribute("listProducts", listProducts);
        return "index";
    }

    // ✅ Trang thêm mới sản phẩm
    @GetMapping("/new")
    public String showNewProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "newproduct";
    }

    // ✅ Lưu sản phẩm mới hoặc chỉnh sửa
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product) {
        service.save(product);
        return "redirect:/";
    }

    // ✅ Trang chỉnh sửa sản phẩm
    @GetMapping("/edit/{id}")
    public ModelAndView showEditProductForm(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("edit_product");
        Product product = service.get(id);
        mav.addObject("product", product);
        return mav;
    }

    // ✅ Xóa sản phẩm
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:/";
    }
    @GetMapping("/403")
    public String accessDenied() {
        return "access_denied"; // access_denied.html
    }
}
