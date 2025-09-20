package com.example.restaurant.controller.securitycontroller;

import com.example.restaurant.config.ExceptionConfig;
import com.example.restaurant.controller.vm.AuthRequestVm;
import com.example.restaurant.controller.vm.AuthResponseVm;
import com.example.restaurant.helper.BundleMessage;
import com.example.restaurant.service.dto.AccountDto;
import com.example.restaurant.service.dto.ExceptionDto;
import com.example.restaurant.service.securityService.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.SystemException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Tag(
        name = "Auth Controller",
        description = "for authorization"
)
@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:4200")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @Operation(
            summary = "api to login for an account"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Http Status sign up"),
            @ApiResponse(responseCode = "500", description = "Http Status internal server error",
                    content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
    })
    @PostMapping("/login")
    ResponseEntity<AuthResponseVm> login (@RequestBody @Valid AuthRequestVm authRequestVm){
        return  ResponseEntity.ok(authService.login(authRequestVm));

    }

   @Operation(
           summary = "api to signup for an account"
   )
   @ApiResponses({
           @ApiResponse(responseCode = "200", description = "Http Status sign up"),
           @ApiResponse(responseCode = "500", description = "Http Status internal server error",
                   content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
   })
    @PostMapping("/sign-up")
    ResponseEntity<AuthResponseVm> signup (@RequestBody @Valid AccountDto accountDto) throws SystemException {
        return ResponseEntity.ok(authService.signup(accountDto));
    }
}
