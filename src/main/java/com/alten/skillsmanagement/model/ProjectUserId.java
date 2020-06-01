package com.alten.skillsmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
@Embeddable
public class ProjectUserId implements Serializable {
    private Integer projectId;
    private Long userId;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((projectId == null) ? 0 : projectId.hashCode());
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
        ProjectUserId other = (ProjectUserId) obj;
        return Objects.equals(getProjectId(), other.getProjectId()) && Objects.equals(getUserId(), other.getUserId());
    }
}
