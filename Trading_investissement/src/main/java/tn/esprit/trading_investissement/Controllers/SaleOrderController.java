package tn.esprit.trading_investissement.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.trading_investissement.Entities.SaleOrder;
import tn.esprit.trading_investissement.Entities.User;
import tn.esprit.trading_investissement.Repositories.SaleOrderRepository;
import tn.esprit.trading_investissement.Repositories.UserRepository;
import tn.esprit.trading_investissement.Services.SaleOrderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sale-orders")
public class SaleOrderController {
    @Autowired
    private SaleOrderService saleOrderService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SaleOrderRepository saleOrderRepository;



    @PostMapping
    public SaleOrder createSaleOrder(@RequestBody SaleOrder saleOrder, @RequestParam Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        saleOrder.setUser(user); // Link the SaleOrder to the User
        return saleOrderService.createSaleOrder(saleOrder);
    }


    @GetMapping
    public List<SaleOrder> getAllSaleOrders() {
        return saleOrderService.getAllSaleOrders();
    }


    @DeleteMapping("/{id}")
    public void deleteSaleOrder(@PathVariable Long id) {
        saleOrderService.deleteSaleOrder(id);
    }
    @GetMapping("/by-company/{companyName}")
    public List<SaleOrder> getSaleOrdersByCompany(@PathVariable String companyName) {
        return saleOrderService.getSaleOrdersByCompany(companyName);
    }


        @GetMapping("/all-sale-orders")
        public List<Map<String, Object>>  getAllSaleOrdersWithUsers() {
                return saleOrderRepository.findAllWithUsers().stream().map(order -> {
            Map<String, Object> result = new HashMap<>();
            result.put("id", order.getId());
            result.put("companyName", order.getCompanyName());
            result.put("desiredPrice", order.getDesiredPrice());
            result.put("saleQuantity", order.getSaleQuantity());
            result.put("lastDate", order.getLastDate());
            result.put("user", order.getUser().getUsername()); // Inclure le username
            return result;
        }).collect(Collectors.toList());

    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SaleOrder>> getSaleOrdersByUser(@PathVariable Long userId) {
        List<SaleOrder> orders = saleOrderService.getSaleOrdersByUser(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/by-company-and-user")
    public List<SaleOrder> getSaleOrdersByCompanyAndUser(
            @RequestParam String companyName,
            @RequestParam Long userId
    ) {
        return saleOrderService.getSaleOrdersByCompanyAndUser(companyName, userId);
    }







}
