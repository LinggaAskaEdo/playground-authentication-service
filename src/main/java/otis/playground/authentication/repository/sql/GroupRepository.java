package otis.playground.authentication.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import otis.playground.authentication.model.entity.Group;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID>  {
    Optional<Group> findByName(String name);
}
