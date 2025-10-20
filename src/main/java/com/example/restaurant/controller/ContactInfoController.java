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

import java.util.List;

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
                    description = "Http Status save contact"
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

    @GetMapping("/getByAccount")
    ResponseEntity<List<ContactInfoDto>> findAllByAccountId( @RequestParam Long accountId) {
        return  ResponseEntity.ok(contactInfoService.findAllByAccountId(accountId));
    }

    @GetMapping("/getAll")
    ResponseEntity<List<ContactInfoDto>> findAllByAccountId(){
        return  ResponseEntity.ok(contactInfoService.findAllOrderByAccountId());

    }

    @PutMapping("/markRead")
    ResponseEntity<Void> markAsRead(@RequestParam Long id){
        contactInfoService.markAsRead(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/getUnread")
    ResponseEntity<Long> getUnreadCount(){
        return  ResponseEntity.ok(contactInfoService.getUnreadCount());
    }

    @GetMapping("/getGlobalUnread")
    ResponseEntity<Long> getGlobalUnreadCount(){
        return ResponseEntity.ok(contactInfoService.getGlobalUnreadCount());

    }

    @PutMapping("/saveReply")
    ResponseEntity<ContactInfoDto> saveReplyMessage(@RequestParam Long id , @RequestParam String replyMessage){
        return  ResponseEntity.ok(contactInfoService.saveReplyMessage(id, replyMessage));

    }

    @GetMapping("/getReply")
    ResponseEntity<List<ContactInfoDto>> getReplyMessagesByAccountId(){
        return  ResponseEntity.ok(contactInfoService.getReplyMessagesByAccountId());

    }

    @GetMapping("/getReadReplies")
    ResponseEntity<Long> getUnreadRepliesCount(){
        return  ResponseEntity.ok(contactInfoService.getUnreadRepliesCount());

    }

    @PutMapping("/markReadReply")
    ResponseEntity<Void> markAsReadReply(@RequestParam Long id){
        contactInfoService.markAsReadReply(id);
        return  ResponseEntity.noContent().build();
    }
}
