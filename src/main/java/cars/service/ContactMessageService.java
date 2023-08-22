package cars.service;

import cars.domain.ContactMessage;
import cars.exception.ResourceNotFoundException;
import cars.exception.message.ErrorMessage;
import cars.repository.ContactMessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Service
@AllArgsConstructor//we are making a constructor injection here.
public class ContactMessageService {

    /*
    this is an example of a constructor injection
     */
    private ContactMessageRepository contactMessageRepository;
    public void saveMessage(ContactMessage contactMessage){
        contactMessageRepository.save(contactMessage);
    }

    //we are not injecting anything here. Here, you are directly using contactMessageRepository to call the findAll() method without passing it as a parameter. This implies that contactMessageRepository is an instance variable or a field of your class that has been set somewhere else (usually through constructor injection or setter injection) before this method is called.
    public List<ContactMessage>getAll(){
        return contactMessageRepository.findAll();
    }

    //to delete we need to find the id from contact message so we need to write another method to find the id first before deleting.
    public ContactMessage findContactMessageId(Long id){
     return contactMessageRepository.findById(id).orElseThrow(()->
             new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));

    }


    public void deleteContactMessage(Long id) {
        //first of all we need to contact message by id information
      //ContactMessage contactMessage=contactMessageRepository.deleteById(findContactMessageId())
        ContactMessage contactMessage=findContactMessageId(id);
        contactMessageRepository.delete(contactMessage);
    }

    public void updateContactMessage(Long id,ContactMessage contactMessage){
        ContactMessage foundContactMessage=findContactMessageId(id);
        foundContactMessage.setName(contactMessage.getName());
        foundContactMessage.setSubject(contactMessage.getSubject());
        foundContactMessage.setEmail(contactMessage.getEmail());
        foundContactMessage.setBody(contactMessage.getBody());

        contactMessageRepository.save(foundContactMessage);
    }

    public Page<ContactMessage> getAll(Pageable pageable){
          return  contactMessageRepository.findAll(pageable);

    }

}
