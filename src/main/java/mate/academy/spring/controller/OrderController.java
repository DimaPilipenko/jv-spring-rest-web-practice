package mate.academy.spring.controller;

import java.util.List;
import java.util.stream.Collectors;
import mate.academy.spring.mapper.DtoResponseMapper;
import mate.academy.spring.model.Order;
import mate.academy.spring.model.ShoppingCart;
import mate.academy.spring.model.dto.response.OrderResponseDto;
import mate.academy.spring.service.OrderService;
import mate.academy.spring.service.ShoppingCartService;
import mate.academy.spring.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final DtoResponseMapper<OrderResponseDto, Order> responseMapper;
    private final UserService userService;
    private final ShoppingCartService cartService;

    public OrderController(OrderService orderService,
                           DtoResponseMapper<OrderResponseDto, Order> responseMapper,
                           UserService userService,
                           ShoppingCartService cartService) {
        this.orderService = orderService;
        this.responseMapper = responseMapper;
        this.userService = userService;
        this.cartService = cartService;
    }

    @PostMapping("/complete")
    public OrderResponseDto completeOrder(@RequestParam Long userId) {
        ShoppingCart shoppingCart = cartService.getByUser(userService.get(userId));
        return responseMapper.toDto(orderService.completeOrder(shoppingCart));
    }

    @GetMapping
    public List<OrderResponseDto> getOrderHistory(@RequestParam Long userId) {
        return orderService.getOrdersHistory(userService.get(userId)).stream()
                .map(responseMapper::toDto)
                .collect(Collectors.toList());
    }
}