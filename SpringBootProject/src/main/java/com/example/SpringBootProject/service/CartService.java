package com.example.SpringBootProject.service;

import com.example.SpringBootProject.Exceptions.CustomException;
import com.example.SpringBootProject.dto.CartDTO;
import com.example.SpringBootProject.dto.CartDetailDTO;
import com.example.SpringBootProject.dto.Request.CartAddDTOReq;
import com.example.SpringBootProject.dto.Request.CartDetailUpdateDTO;
import com.example.SpringBootProject.enums.ErrorCode;
import com.example.SpringBootProject.mapper.CartMapper;
import com.example.SpringBootProject.model.*;
import com.example.SpringBootProject.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;


    public List<CartDTO> getAllCarts(){
        return CartMapper.INSTANCE.cartListToCartDTOList(cartRepository.findAll());
    }

    @Transactional
    public CartDTO makePurchase(Long id_user) {
        User user = userRepository.findById(id_user)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND,"User not found id: "+id_user));

        Cart cart = user.getCart();
        List<CartDetail> cartDetails = cart.getCartDetails();

        Order order = new Order();
        order.setUser(user);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartDetail cartDetail : cartDetails) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(cartDetail.getProduct());
            orderDetail.setProductsAmount(cartDetail.getProductsAmount());
            orderDetails.add(orderDetail);
        }
        order.setOrderDetails(orderDetails);
        order.setOrderDate(LocalDateTime.now());
        order.setComments("No comments");
        order.setTotalPrice(cart.getTotalPrice());
        orderRepository.save(order);
        orderDetailRepository.saveAll(orderDetails);

        cartDetailRepository.deleteAll(cartDetails);
        cartRepository.delete(cart);

        user.setCart(null);
        userRepository.save(user);
        System.err.println(cart.getId());
        return CartMapper.INSTANCE.cartToCartDTO(cart);
    }

    @Transactional
    public Double updateTotalPrice(Long userId){
        Cart cart=cartRepository.findByUserId(userId)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND, "Cart not found user_id: "+userId));
        Double totalPrice=0.0;
        for(CartDetail cd : cart.getCartDetails()){
            totalPrice+=cd.getProduct().getPrice();
        }
        cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);
        return totalPrice;
    }

    public Double getTotalPrice(Long userId){
        return updateTotalPrice(userId);
    }

    public CartDTO getCartByUserId(Long userId){
        return CartMapper.INSTANCE.cartToCartDTO(cartRepository.findById(userId).orElseThrow(
                ()->new CustomException(ErrorCode.NOT_FOUND, "Cart not found for id: "+userId)
        ));
    }

    @Transactional
    public Cart createCartById(Long id){
        Cart cart=new Cart();
        cart.setUser(userRepository.findById(id).orElseThrow(
                ()->new CustomException(ErrorCode.NOT_FOUND ,"User not found id: "+id))
        );
        cart.setCartDetails(new ArrayList<>());
        cart.setTotalPrice(0.0);
        return cart;
    }

    @Transactional
    public CartDTO addToCartForUser(Long id_user, CartAddDTOReq cartAddDTOReq){
        User user= userRepository.findById(id_user).orElseThrow(
                ()->new CustomException(ErrorCode.NOT_FOUND, "User not found id: "+id_user)
        );

        Product product=productRepository.findById(cartAddDTOReq.id_product()).orElseThrow(
                ()->new CustomException(ErrorCode.NOT_FOUND, "Product not found id: "+ cartAddDTOReq.id_product())
        );

        Cart cart= cartRepository.findByUserId(id_user).orElseGet(()->createCartById(id_user));
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            user.setCart(cart);
        }
        List<CartDetail> cartDetails=cart.getCartDetails();
        if(cartDetails==null){cartDetails=new ArrayList<>(); cart.setCartDetails(cartDetails); }
        CartDetail cartDetail=null;
        for(CartDetail cd : cartDetails){
            if(cd.getProduct().getId().equals(cartAddDTOReq.id_product())){
                cartDetail=cd;
                break;
            }
        }
        if(cartDetail!=null){
            cartDetail.setProductsAmount(cartDetail.getProductsAmount()+ cartAddDTOReq.productsAmount());
        }else{
            cartDetail=CartDetail.builder()
                    .cart(cart)
                    .product(product)
                    .productsAmount(cartAddDTOReq.productsAmount())
                    .build();
            cart.getCartDetails().add(cartDetail);
        }
        cartDetailRepository.save(cartDetail);
        cartRepository.save(cart);
        updateTotalPrice(id_user);
        return CartMapper.INSTANCE.cartToCartDTO(cart);
    }

    @Transactional
    public void cartClear(Long id_user){
        Cart cart=cartRepository.findByUserId(id_user)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND, "Cart not found user_id: "+id_user));
        cartDetailRepository.deleteAll(cart.getCartDetails());
        cart.setTotalPrice(0.0);

//        updateTotalPrice(id_user);
    }

    @Transactional
    public CartDetailDTO updateCartDetailAmount(CartDetailUpdateDTO cartDetailUpdateDTO) {
        CartDetail cartItem = cartDetailRepository.findById(cartDetailUpdateDTO.cartDetailId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "Cart detail not found id: "+cartDetailUpdateDTO.cartDetailId()));

        cartItem.setProductsAmount(cartDetailUpdateDTO.amount());
        updateTotalPrice(cartItem.getCart().getUser().getId());
        return CartMapper.INSTANCE.cartDetailToCartDetailDTO(cartDetailRepository.save(cartItem));
    }

    @Transactional
    public CartDTO removeCartDetailFromCart(Long cartDetailId){
        Cart cart=cartDetailRepository.findById(cartDetailId)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND, "Cart detail not found id: "+cartDetailId))
                .getCart();
        cartDetailRepository.deleteById(cartDetailId);
        updateTotalPrice(cart.getUser().getId());
        return CartMapper.INSTANCE.cartToCartDTO(cart);
    }
}
