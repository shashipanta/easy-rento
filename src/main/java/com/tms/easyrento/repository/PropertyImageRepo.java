package com.tms.easyrento.repository;

import com.tms.easyrento.model.file.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/30/24 6:06 AM
 */
public interface PropertyImageRepo extends JpaRepository<PropertyImage, Long> {

    @Query(nativeQuery = true,
    value = """
            SELECT * FROM property_images p_i where p_i.property_id = ?1
            """)
    List<PropertyImage> getPropertyImageById(Long propertyId);
}
