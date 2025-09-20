package com.example.restaurant.controller;

import com.example.restaurant.helper.BundleMessage;
import com.example.restaurant.service.ContactInfoService;
import com.example.restaurant.service.dto.ContactInfoDto;
import com.example.restaurant.service.dto.ExceptionDto;
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
@Tag(
        name = "Contact-Info Controller",
        description = "save contact information"
)
@RestController
@RequestMapping("/api/contact")
//@CrossOrigin("http://localhost:4200")

public class ContactInfoController {

    private ContactInfoService contactInfoService;

    @Autowired
    public ContactInfoController(ContactInfoService contactInfoService) {
        this.contactInfoService = contactInfoService;
    }
    @Operation(
            summary = "api to save contact information"
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
    @PostMapping("/save")
    ResponseEntity<ContactInfoDto> save(@RequestBody @Valid ContactInfoDto contactInfoDto){
        System.out.println("Received Contact Info: " + contactInfoDto.toString());
        return ResponseEntity.ok(contactInfoService.save(contactInfoDto));
    }
}
