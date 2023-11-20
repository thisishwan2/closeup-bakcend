package farmSystem.closeUp.repository.profile;

import farmSystem.closeUp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


    public interface ProfileRepository extends JpaRepository<User, Long>, ProfileRepositoryCustom {

    }

