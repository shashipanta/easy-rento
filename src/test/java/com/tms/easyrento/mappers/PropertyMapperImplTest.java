package com.tms.easyrento.mappers;

import com.tms.easyrento.dto.request.PropertyOwnershipRequest;
import com.tms.easyrento.dto.request.PropertyRequest;
import com.tms.easyrento.enums.AddressType;
import com.tms.easyrento.model.Address;
import com.tms.easyrento.model.property.Property;
import com.tms.easyrento.model.propertyOwnership.PropertyOwnership;
import com.tms.easyrento.model.propertyOwnership.PropertyOwnershipId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-15 13:34
 */

@DisplayName("Unit tests for PropertyMapperImpl")
public class PropertyMapperImplTest {

    private PropertyMapperImpl mapper;
    private PropertyOwnershipMappingHelper mockOwnershipMappingHelper;

    @BeforeEach
    void setUp() throws Exception {
        mapper = new PropertyMapperImpl();
        mockOwnershipMappingHelper = mock(PropertyOwnershipMappingHelper.class);

        // inject mock via reflection
        Field field = PropertyMapperImpl.class.getDeclaredField("propertyOwnershipMappingHelper");
        field.setAccessible(true);
        field.set(mapper, mockOwnershipMappingHelper);
    }

    @Nested
    @DisplayName("requestToEntity tests")
    class requestToEntityTests {

        @Test
        @DisplayName("Should map basic fields and address correctly")
        void shouldMapBasicFields() {
            PropertyRequest request = new PropertyRequest();
            request.setPropertyTitle("New Land");
            request.setWardNo((short) 3);
            request.setStreetName("Hill Street");
            request.setAddressType(AddressType.P.name());
            request.setPropertyOwnershipRequests(Collections.emptyList());

            when(mockOwnershipMappingHelper.mapList(anyList())).thenReturn(Collections.emptyList());

            Property entity = mapper.requestToEntity(request);

            assertNotNull(entity);
            assertEquals("New Land", entity.getTitle());
            assertNotNull(entity.getAddress());
            assertEquals(3L, entity.getAddress().getWardNo());
            assertEquals("Hill Street", entity.getAddress().getStreetName());
            assertEquals(AddressType.P, entity.getAddress().getAddressType());
            assertTrue(entity.getPropertyOwnerships().isEmpty());
        }

        @Test
        @DisplayName("Should return null when request is null")
        void shouldReturnNullOnNullRequest() {
            Property entity = mapper.requestToEntity(null);
            assertNull(entity);
        }
    }

    @Nested
    @DisplayName("partialUpdate tests")
    class PartialUpdateTests {

        @Test
        @DisplayName("Should partially update title and address")
        void shouldPartiallyUpdateEntity() {
            PropertyRequest request = new PropertyRequest();
            request.setPropertyTitle("Updated Title");
            request.setWardNo((short) 8);
            request.setStreetName("Updated St");
            request.setAddressType("T");

            Property existing = new Property();
            existing.setTitle("Old Title");
            Address addr = new Address();
            addr.setWardNo(5L);
            existing.setAddress(addr);

            when(mockOwnershipMappingHelper.mapList(any())).thenReturn(null);

            Property updated = mapper.partialUpdate(request, existing);

            assertEquals("Updated Title", updated.getTitle());
            assertEquals(8L, updated.getAddress().getWardNo());
            assertEquals("Updated St", updated.getAddress().getStreetName());
        }

        @Test
        @DisplayName("Should create address if not present")
        void shouldCreateAddressIfNull() {
            PropertyRequest request = new PropertyRequest();
            request.setWardNo((short) 9);
            request.setStreetName("New Street");
            request.setAddressType("T");

            Property existing = new Property();
            existing.setAddress(null);

            Property updated = mapper.partialUpdate(request, existing);

            assertNotNull(updated.getAddress());
            assertEquals(9L, updated.getAddress().getWardNo());
            assertEquals("New Street", updated.getAddress().getStreetName());
            assertEquals(AddressType.T, updated.getAddress().getAddressType());
        }

        @Test
        @DisplayName("Should update ownership if request is provided with one : if existing is not provided then its replaced")
        void shouldUpdateOwnerships() {

            // property request
            PropertyRequest request = new PropertyRequest();
            PropertyOwnershipRequest propertyOwnershipRequest = new PropertyOwnershipRequest();
            propertyOwnershipRequest.setId(new PropertyOwnershipId(2L, 2L));
            request.setPropertyOwnershipRequests(List.of(propertyOwnershipRequest));

            PropertyOwnership mappedOwnership = new PropertyOwnership();
            mappedOwnership.setId(new PropertyOwnershipId(2L, 2L));

            // Property Ownership for Property entity
            PropertyOwnership ownership = new PropertyOwnership();
            ownership.setId(new PropertyOwnershipId(1L, 2L));
            ownership.setStartDate(LocalDate.now());
            ownership.setOwnershipPercentage(100.0);
            List<PropertyOwnership> mockOwnerships = new ArrayList<>();
            mockOwnerships.add(ownership);

            Property property = new Property();
            property.setPropertyOwnerships(mockOwnerships);

            when(mockOwnershipMappingHelper.mapList(
                    argThat(list -> list != null && !list.isEmpty()))
            ).thenReturn(List.of(mappedOwnership));

            // Act
            mapper.partialUpdate(request, property);

            // Assert: old one is replaced
            assertEquals(1, property.getPropertyOwnerships().size());
            assertSame(mappedOwnership, property.getPropertyOwnerships().get(0));

            assertTrue(property.getPropertyOwnerships()
                    .stream()
                    .noneMatch(propertyOwnership -> propertyOwnership.getId().equals(1L))
            );

            verify(mockOwnershipMappingHelper).mapList(request.getPropertyOwnershipRequests());
            verify(mockOwnershipMappingHelper, times(1)).mapList(List.of(propertyOwnershipRequest));
            verifyNoMoreInteractions(mockOwnershipMappingHelper);

        }

        @Test
        @DisplayName("Should skip ownership update if request list is empty")
        void shouldSkipOwnershipUpdateIfRequestIsEmpty() {
            PropertyRequest request = new PropertyRequest();
            request.setPropertyOwnershipRequests(Collections.emptyList());

            Property property = new Property();
            List<PropertyOwnership> originalList = new ArrayList<>();
            originalList.add(new PropertyOwnership());
            property.setPropertyOwnerships(originalList);

            mapper.partialUpdate(request, property);

            // Ensure original list remains unchanged
            assertEquals(originalList.size(), property.getPropertyOwnerships().size());

        }
    }

}
