package com.alten.skillsmanagement.repository;

import com.alten.skillsmanagement.model.Skill;
import com.alten.skillsmanagement.model.SkillsMatrix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import java.util.ArrayList;
import java.util.List;

/*@SqlResultSetMapping(
        name = "skillsMatrixMapping",
        classes = {
                @ConstructorResult(
                        targetClass = SkillsMatrix.class,
                        columns = {
                                @ColumnResult(name = "id"),
                                @ColumnResult(name = "title")
                        }
                )
        }
)

@NamedNativeQuery(name = "getTitle", query = "select m.id, m.title from skills_matrix m inner join skills_matrix_skill sm on m.id = sm.skills_matrix_id inner join skills s on sm.skill_id = s.id where s.id = :skillId",
        resultSetMapping = "skillsMatrixMapping")*/

public interface SkillsMatrixRepository extends JpaRepository<SkillsMatrix, Long> {
    //Optional<SkillsMatrix> getSkillsMatrixByAppUser(AppUser appUser);
    //List<SkillsMatrix> getSkillsMatricesBySkillsIn(ArrayList<Skill> skills);
    //List<SkillsMatrix> getSkillsMatricesBySkills(Skill skill); // working but i'll not use it


    @Query(value = "select m.id, m.title from skills_matrix m inner join skills_matrix_skill sm on m.id = sm.skills_matrix_id inner join skills s on sm.skill_id = s.id where s.id = :skillId",
            nativeQuery = true)
    List<TitleOnly> getSkillsMatricesBySkillId(@Param("skillId") Long skillId);

    @Query(value = "select new com.alten.skillsmanagement.model.SkillsMatrix(m.id, m.title) from SkillsMatrix m")
    List<SkillsMatrix> getSkillsMatricesTitleOnly();

    interface TitleOnly {
        Long getId();
        String getTitle();
    }



    //List<SkillsMatrix> getSkillsMatrices(@Param("skillId") Long skillId);


}
