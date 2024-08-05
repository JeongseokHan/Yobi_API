package org.example.yobiapi.contenttypes.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.yobiapi.contenttypes.dto.ContentTypesDTO;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contenttypes")
public class ContentTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "content_id")
    private Integer contentId;

    @Column(nullable = false, name = "name")
    private String name;

    public static ContentTypes toContentTypes(ContentTypesDTO contentTypesDTO) {
        ContentTypes contentTypes = new ContentTypes();
        contentTypes.setName(contentTypesDTO.getName());

        return contentTypes;
    }
}
