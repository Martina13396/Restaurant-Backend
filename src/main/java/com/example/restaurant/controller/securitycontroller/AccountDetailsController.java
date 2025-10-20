package com.example.restaurant.controller.securitycontroller;

import com.example.restaurant.service.dto.AccountDetailsDto;
import com.example.restaurant.service.securityService.AccountDetailsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Tag(
        name = "account details controller",
        description = "for account details"
)
@RestController
@RequestMapping("api/accountDetails")
//@CrossOrigin("http://localhost:4200")
public class AccountDetailsController {

    private AccountDetailsService accountDetailsService;

    public AccountDetailsController(AccountDetailsService accountDetailsService) {
        this.accountDetailsService = accountDetailsService;
    }

    @PostMapping("/save")
    ResponseEntity<AccountDetailsDto> saveAccountDetails(@RequestBody @Valid AccountDetailsDto accountDetailsDto){
        return ResponseEntity.ok(accountDetailsService.saveAccountDetails(accountDetailsDto));
    }

    @GetMapping("/getDetails")
    ResponseEntity<AccountDetailsDto> getAccountDetails(){
        return ResponseEntity.ok(accountDetailsService.getAccountDetails());
    }
}
