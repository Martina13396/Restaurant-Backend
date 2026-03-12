package com.example.restaurant.controller.securitycontroller;

import com.example.restaurant.helper.BundleMessage;
import com.example.restaurant.service.dto.AccountDto;
import com.example.restaurant.service.dto.ExceptionDto;
import com.example.restaurant.service.securityService.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Account Controller",
        description = " create and get accounts"
)
@RestController
@RequestMapping("api/account")
@CrossOrigin("http://localhost:4200")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(
            summary = "api to create account"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status get all categories"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionDto.class)
                    )
            ),

    })
    @PostMapping("/createAccount")
    ResponseEntity<AccountDto> createAccount(@RequestBody @Valid AccountDto accountDto){
        return ResponseEntity.ok(accountService.createAccount(accountDto));

    }
    @Operation(
            summary = "api to search account by username"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status get account By user name"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Http Status Not Found",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionDto.class)
                    )
            )
    })
    @GetMapping("/getByUserName")
    ResponseEntity<AccountDto> getAccountByUsername(@RequestParam String username){
        return ResponseEntity.ok(accountService.getAccountByUsername(username));

    }

    @Operation(
            summary = "api to change password"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status change password"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionDto.class)
                    )
            ),

    })

    @PutMapping("/changePassword")
    ResponseEntity<AccountDto> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword){
        return ResponseEntity.ok(accountService.changePassword( oldPassword, newPassword));

    }

    @PutMapping("/deleteAccount")
    ResponseEntity<Void> deleteAccount(@RequestParam Long accountId){
      this.accountService.deleteAccount(accountId);
      return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "api to get all accounts"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status get all accounts"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Http Status Not Found",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionDto.class)
                    )
            )
    })

    @GetMapping("/getAll")
    ResponseEntity<List<AccountDto>> getAllAccounts(){
        return ResponseEntity.ok(accountService.getAllAccounts());
    }
}
