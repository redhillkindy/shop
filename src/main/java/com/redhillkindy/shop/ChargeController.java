package com.redhillkindy.shop;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log
@Controller
public class ChargeController {

    @Autowired
    StripeService paymentsService;

    @PostMapping("/charge")
    public String charge(ChargeRequest chargeRequest, Model model, @RequestParam("quantity") int quantity) throws StripeException {
        chargeRequest.setDescription("Unlimited ride pass");
        chargeRequest.setCurrency(ChargeRequest.Currency.AUD);
        chargeRequest.setAmount(2777*quantity);
        Charge charge = paymentsService.charge(chargeRequest);
        model.addAttribute("id", charge.getId());
        model.addAttribute("status", charge.getStatus());
        model.addAttribute("chargeId", charge.getId());
        model.addAttribute("balance_transaction", charge.getBalanceTransaction());
        return "result";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }
}
