package cars.controller;

import cars.domain.ContactMessage;
import cars.dto.ContactMessageDTO;
import cars.dto.request.ContactMessageRequest;
import cars.dto.response.CRResponse;
import cars.dto.response.ResponseMessage;
import cars.mapper.ContactMessageMapper;
import cars.service.ContactMessageService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort.Direction;


import java.util.List;

@RestController
@RequestMapping("/contactmessage")
@AllArgsConstructor
public class ContactMessageController {
    private ContactMessageService contactMessageService;
    private ContactMessageMapper contactMessageMapper;
    /*
    Inside the method, contactMessageMapper.contactMessageRequestToContactMessage();
    is called. This likely represents a mapping from the ContactMessageRequest DTO to the ContactMessage entity
    object using the ContactMessageMapper. This step is typically used to transform data from one representation
     (DTO) to another (entity) before it's saved to the database.
     */
    //This here saves the DTO message into the entity db
    //so here we are checking if the request body is valid and using RequestBody we are making sure
    // that the request in the json format turns into an java object form which spring does in the background

    /**
     * This kind of service layer implementations should be done in service layer. here we put everything under controller.
     * @param contactMessageRequest
     * @return
     */
    @PostMapping("visitors")
    public ResponseEntity<CRResponse>createMessage(@Valid @RequestBody ContactMessageRequest contactMessageRequest) {

        /*
        so here we are saying using the mapper interface post the requests to contactMessage entity the db using the mapper interface.
        In the interface we have told the @Mapper to map the request to the entity and in the controller we are defining this?
         */
        ContactMessage contactMessage = contactMessageMapper.contactMessageRequestToContactMessage(contactMessageRequest);
/*
we are saying using the contactMessageMapper we are turning contactMessageRequest to ContactMessage. We already defined contactMessageRequestToContactMessage
in the ContactMessageMapper interface.
 */

        /*
        then we will return the contactMessage but the contactMessage must be saved in the server cuz we are POSTing for the clients
        request. This is done by the service layer.
         */



        /*
        Here we are saving our contactMessage which has received its request from ContactMessageRequest
        that has been turned into the entity and using the service class it has been saved to the db by the repository extending into the
        jpaRepository interface that provides a set of methods for performing common database operations like saving, updating, deleting,
         and querying JPA entities.
         */
        //Here the contact message is saved into the db after the data is converted from DTO to Entity.
        contactMessageService.saveMessage(contactMessage);

        ///example of hard coding
        //CRResponse response=new CRResponse("it worked",true);
        CRResponse response = new CRResponse(ResponseMessage.CONTACT_MESSAGE_SAVE_RESPONSE, true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContactMessageDTO>>getAllContactMessage(){
List<ContactMessage>contactMessageList=contactMessageService.getAll();
List<ContactMessageDTO>contactMessageDTOList=contactMessageMapper.map(contactMessageList);

        return ResponseEntity.ok(contactMessageDTOList);

    }

    //delete it according to id. this is why we have injected id in this method.
    @DeleteMapping("{id}")
    public ResponseEntity<CRResponse>deleteContactMessage(@PathVariable Long id){
        contactMessageService.deleteContactMessage(id);
    CRResponse response=new CRResponse(ResponseMessage.CONTACT_MESSAGE_DELETED,true);
    return ResponseEntity.ok(response);
    }

    @GetMapping("/request")
    public ResponseEntity<ContactMessageDTO>getRequestWithRequestParam(@RequestParam("id") Long id){

        ContactMessage contactMessage=contactMessageService.findContactMessageId(id);
        ContactMessageDTO contactMessageDTO=contactMessageMapper.contactMessageToDTO(contactMessage);
        return ResponseEntity.ok(contactMessageDTO);

    }

    @GetMapping("{id}")
    public ResponseEntity<ContactMessageDTO>getRequestWithPath(@PathVariable Long id){
        ContactMessage contactMessage=contactMessageService.findContactMessageId(id);
        ContactMessageDTO contactMessageDTO=contactMessageMapper.contactMessageToDTO(contactMessage);
        return ResponseEntity.ok(contactMessageDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<CRResponse>updateContactMessage(@PathVariable Long id,
                                                          @Valid @RequestBody ContactMessageRequest contactMessageRequest){
        /*
        i need to update so i have to send the request to the database and update, after update I will
        return a response saying it has been updated.
         */
        ContactMessage contactMessage=contactMessageMapper.contactMessageRequestToContactMessage(contactMessageRequest);
        contactMessageService.updateContactMessage(id,contactMessage);
        CRResponse response=new CRResponse(ResponseMessage.CONTACT_MESSAGE_UPDATE_RESPONSE,true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pages")
    public ResponseEntity<Page<ContactMessageDTO>>getAllContactMessageWithPage(@RequestParam("page") int page,
                                                                               @RequestParam("size") int size,
                                                                               @RequestParam("sort") String prop,
                                                                               @RequestParam(value="direction", required = false,defaultValue = "DESC")Direction direction){

        Pageable pageable= PageRequest.of(page,size, Sort.by(direction,prop));
        Page<ContactMessage>contactMessagePage=contactMessageService.getAll(pageable);
        Page<ContactMessageDTO>contactMessageDTOS=getPageDTO(contactMessagePage);
        return ResponseEntity.ok(contactMessageDTOS);

    }

    //this implementation should exist in service layer
    public Page<ContactMessageDTO>getPageDTO(Page<ContactMessage>contactMessagePage){
        return contactMessagePage.map(contactMessage -> contactMessageMapper.contactMessageToDTO(contactMessage));

    }


}
