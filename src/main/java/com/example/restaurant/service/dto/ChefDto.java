package com.example.restaurant.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.message.Message;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Chef Dto" , description = "Chef Dto for Chef table")
public class ChefDto {

    @Schema(name = "Chef ID", description = "id for chef" , example = "1")
    private Long id;
    @Schema(name = "Chef Name", description = "chef name" , example = "Ahmed")
     @NotBlank(message = "chef.name.required")
     @Size(min = 2 , max = 100 ,message = "chef.name.size")
    private  String name;
    @Schema(name = "Logo Path", description = "url for logo" , example = "team-4.jpg")
     @NotNull(message = "chef.logo.required")
     @URL(message = "chef.logo.url")
    private String logoPath;
    @Schema(name = "FaceBook Link", description = "link for chef facebook" , example = "https://facebook.com/chef4")
     @URL(message = "chef.facebook.url")
    private String facebookLink;

     @URL(message = "chef.twitter.url")
     @Schema(name = "Twitter Link", description = "Link for chef twitter" , example = "https://twitter.com/chef4")
    private String twitterLink;

     @URL(message = "chef.instagram.url")
     @Schema(name = "Instagram Link", description = "link for chef instagram" , example = "https://instagram.com/chef4")
    private String instagramLink;

     @NotBlank(message = "chef.speciality.required")
     @Size (min = 2 , max = 100 , message = "chef.speciality.size")
    private  String specialty;
}
