package com.example.restaurant.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Contact Information Dto" , description = "Contact Info Dto for Contact Info table")
public class ContactInfoDto {

//    @Schema(name = "ContactInfo ID", description = "ID for contact information" , example = "1")
//    private Long id;
    @Schema(name = "name", description = "customer name" , example = "Amr")
    @NotBlank(message = "name.required")
    @Size(min = 2 , max = 50 , message = "name.size")
    private String name;
    @Schema(name = "email", description = "customer email" , example = "Amr@gmail.com")
    @NotBlank(message = "email.required")
    @Email(message = "email.invalid")
    private String email;

    @NotBlank(message = "message.required")
    @Size(min = 2 , max = 100 , message = "message.size")
    private String message;

    @NotBlank(message = "subject.required")
    @Size(min = 2 , max = 1000 , message = "subject.size")
    private String subject;
}
