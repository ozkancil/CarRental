package cars.repository;

import cars.domain.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository//will be responsible for data base connection
public interface ContactMessageRepository extends JpaRepository<ContactMessage,Long> {

/*
its a polimorphic method interface.
 */


}
