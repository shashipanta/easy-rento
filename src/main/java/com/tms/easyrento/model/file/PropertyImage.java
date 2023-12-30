package com.tms.easyrento.model.file;

import com.tms.easyrento.model.AbstractAuditor;
import com.tms.easyrento.model.property.Property;
import jakarta.persistence.*;
import lombok.*;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/23/23 5:42 AM
 */
@Getter
@Setter
@Entity
@Builder
@Table(name = "property_images")
@NoArgsConstructor
@AllArgsConstructor
public class PropertyImage extends AbstractAuditor {

    @Id
    @SequenceGenerator(name = "property_images_seq_gen", sequenceName = "property_images_seq", allocationSize = 1)
    @GeneratedValue(generator = "property_images_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "original_file_name", nullable = false)
    private String originalFileName;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_size")
    private Short fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", foreignKey = @ForeignKey(name = "fk_property-images_property-id"))
    private Property property;
}
