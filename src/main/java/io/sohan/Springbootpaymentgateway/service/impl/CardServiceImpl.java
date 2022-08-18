package io.sohan.Springbootpaymentgateway.service.impl;

import com.google.gson.Gson;
import com.razorpay.Card;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import io.sohan.Springbootpaymentgateway.model.Cards;
import io.sohan.Springbootpaymentgateway.model.Customers;
import io.sohan.Springbootpaymentgateway.repository.CardRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardServiceImpl {
    private final Gson gson = new Gson();
    private CardRepository cardRepository;
    @Value("${Razorpay.keyId}")
    private String key_id;
    @Value("${Razorpay.Secret-key}")
    private String key_secret;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Cards getCard(String id) throws RazorpayException {
        Optional<Cards> optionalCards = cardRepository.findById(id);
        if (optionalCards.isPresent()) {
            System.out.println(optionalCards.get());
            return optionalCards.get();
        }
        RazorpayClient razorpay = new RazorpayClient(key_id, key_secret);
        Card card = razorpay.Cards.fetch(id);
        Cards cards = gson.fromJson(card.toString(), Cards.class);
        System.out.println(cards);
        return cards;
    }

    public void addCard(String cardId, Customers customers) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(key_id, key_secret);
        Card card = razorpay.Cards.fetch(cardId);
        Cards cards = gson.fromJson(card.toString(), Cards.class);
        if (cardRepository.findByIdAndCustomersId(cardId, customers.getId()).isPresent()) {
            return;
        }
        cards.setCustomers(customers);
        cardRepository.save(cards);
        System.out.println(cards);
    }
}
