package com.example.demo.service;

import com.example.demo.entity.Book;

import com.example.demo.entity.BookRequest;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderRequest;
import com.example.demo.exceptions.InsufficientOrderTotalException;
import com.example.demo.repository.BookRepository;
import com.example.demo.exceptions.BookNotFoundException;
import com.example.demo.exceptions.InsufficientStockException;
import com.example.demo.exceptions.OrderNotFoundException;
import com.example.demo.repository.OrderRepository;
import com.example.demo.entity.User;

import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.*;


@Service
@RequiredArgsConstructor
public class OrderService {
    // ...
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public void placeNewOrder(OrderRequest orderRequest) throws InsufficientStockException, BookNotFoundException, InsufficientOrderTotalException {
        // Convert the incoming OrderRequest into an Order

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<User> user = userRepository.findByEmail(currentPrincipalName);

        Order newOrder = new Order();
        newOrder.setUserId(user.get().getId()); // Set the user who placed the order
        double totalPrice = 0;

        // Create a list to store the Book objects
        List<Book> books = new ArrayList<>();

        for (BookRequest bookRequest : orderRequest.getBooks()) {

            Optional<Book> optionalBook = bookRepository.findBookByIsbn(bookRequest.getIsbn());

            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();
                int requestedQuantity = bookRequest.getQuantity();

                if (requestedQuantity <= book.getStockQuantity()) {

                    book.setStockQuantity(book.getStockQuantity() - requestedQuantity);
                    double fee;
                    fee = book.getPrice() * requestedQuantity;
                    totalPrice = totalPrice + fee;

                    books.add(book);
                } else {
                    // Handle insufficient stock
                    throw new InsufficientStockException("Insufficient stock for ISBN: " + book.getIsbn());
                }
            } else {
                // Handle book not found
                throw new BookNotFoundException("Book not found for ISBN: " + bookRequest.getIsbn());
            }
        }

        // Set the total price and books for the order
        newOrder.setTotalPrice(totalPrice);
        newOrder.setBooks(books);

        if (totalPrice < 25.0) {
            throw new InsufficientOrderTotalException("Order total is less than 25 dollars.");
        }

        Order savedOrder = orderRepository.save(newOrder);



    }

    public List<Order> getOrdersForUser(Long userId) {
        return orderRepository.findAllByUserIdOrderByUpdatedAtDesc(userId);
    }

    public Map<String, Object> getOrderDetailsById(Long orderId) throws OrderNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        List<Book> booksInOrder = order.getBooks(); // Assuming you have a proper relationship between Order and Book entities

        Map<String, Object> orderDetails = new HashMap<>();
        orderDetails.put("order", order);
        orderDetails.put("books", booksInOrder);

        return orderDetails;
    }
}