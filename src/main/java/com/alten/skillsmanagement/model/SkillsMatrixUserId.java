package com.alten.skillsmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Embeddable
public class SkillsMatrixUserId implements Serializable {
    private Long skillsMatrixId;
    private Long userId;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((skillsMatrixId == null) ? 0 : skillsMatrixId.hashCode());
        result = prime * result
                + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SkillsMatrixUserId other = (SkillsMatrixUserId) obj;
        return Objects.equals(getSkillsMatrixId(), other.getSkillsMatrixId()) && Objects.equals(getUserId(), other.getUserId());
    }
}
