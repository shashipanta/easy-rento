package com.tms.easyrento.mappers;

import com.tms.easyrento.dto.request.PropertyRequest;
import com.tms.easyrento.enums.AddressType;
import com.tms.easyrento.model.Address;
import com.tms.easyrento.model.property.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class PropertyMapperTest {

    private PropertyMapper propertyMapper;
    private PropertyOwnershipMappingHelper mockOwnershipHelper;

    @BeforeEach
    void setUp() throws Exception {
        propertyMapper = new PropertyMapperImpl();
        mockOwnershipHelper = mock(PropertyOwnershipMappingHelper.class);

        // Inject mock into private field via reflection
        Field helperField = PropertyMapperImpl.class.getDeclaredField("propertyOwnershipMappingHelper");
        helperField.setAccessible(true);
        helperField.set(propertyMapper, mockOwnershipHelper);
    }

    @Test
    void requestToEntity_shouldMapFlatAddressFieldsToNestedEntity() {
        // setup
        PropertyRequest request = PropertyRequestTestDataFactory.withAddressFields();

        // mock behaviour

        Property entity = propertyMapper.requestToEntity(request);

        assertThat(entity.getTitle()).isEqualTo("Luxury Apartment");
        assertThat(entity.getAddress()).isNotNull();
        assertThat(entity.getAddress().getWardNo()).isEqualTo(5L);
        assertThat(entity.getAddress().getStreetName()).isEqualTo("Main Street");
        assertThat(entity.getAddress().getGoogleLocation()).isEqualTo("https://maps.google.com/maps?");
        assertThat(entity.getAddress().getAddressType()).isEqualTo(AddressType.P);
    }

    @Test
    void testRequestToEntity_mapsFieldsCorrectly() {
        // Arrange
        PropertyRequest request = new PropertyRequest();
        request.setPropertyTitle("Test Title");
        request.setWardNo((short) 5);
        request.setAddressType("T");
        request.setStreetName("Main St");
        request.setPropertyOwnershipRequests(Collections.emptyList());

        // Mock helper call
        when(mockOwnershipHelper.mapList(any())).thenReturn(Collections.emptyList());

        // Act
        Property entity = propertyMapper.requestToEntity(request);

        // Assert
        assertNotNull(entity);
        assertEquals("Test Title", entity.getTitle());
        assertNotNull(entity.getAddress());
        assertEquals(5L, entity.getAddress().getWardNo());
        assertEquals(AddressType.T, entity.getAddress().getAddressType());
        assertEquals("Main St", entity.getAddress().getStreetName());

        // Verify helper was called
        verify(mockOwnershipHelper).mapList(request.getPropertyOwnershipRequests());
    }

    @Test
    void testPartialUpdate_updatesOnlyNonNullFields() {
        // Arrange
        PropertyRequest request = new PropertyRequest();
        request.setPropertyTitle("Updated Title");
        request.setWardNo((short) 10);
        // leave other fields null

        Property property = new Property();
        property.setTitle("Old Title");
        property.setAddress(new Address());
        property.getAddress().setWardNo(5L);

        when(mockOwnershipHelper.mapList(any())).thenReturn(Collections.emptyList());

        // Act
        propertyMapper.partialUpdate(request, property);

        // Assert
        assertEquals("Updated Title", property.getTitle());
        assertEquals(10L, property.getAddress().getWardNo());
    }



}