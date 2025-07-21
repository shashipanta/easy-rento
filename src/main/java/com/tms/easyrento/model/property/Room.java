package com.tms.easyrento.model.property;

import com.tms.easyrento.enums.RoomType;
import jakarta.persistence.*;
import lombok.*;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-19 15:37
 */

@Getter
@Setter
@Entity(name = "rooms")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @SequenceGenerator(name = "rooms_seq_gen", sequenceName = "rooms_seq", allocationSize = 1)
    @GeneratedValue(generator = "rooms_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false)
    private RoomType roomType;

    @Column(name = "total_room", nullable = false)
    private Short totalRoom = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", nullable = false, foreignKey = @ForeignKey(name = "fk_room_property_id"))
    private Property property;
}
