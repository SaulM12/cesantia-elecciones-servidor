package com.cesantia.elections.dtos;

import com.cesantia.elections.entities.Delegate;
import com.cesantia.elections.enums.InvitationStatus;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
public class QuadrantDto {
    private Long id;
    private String description;
    private String acronym;
    private Integer quadrantOrder;
    private List<TableDto> tables = new ArrayList<>();

    public QuadrantDto(Long id, String description, String acronym, Integer quadrantOrder) {
        this.id = id;
        this.description = description;
        this.acronym = acronym;
        this.quadrantOrder = quadrantOrder;
    }

    // Constructor, getters y setters
    @Data
    public static class TableDto {
        private Long id;
        private Integer tableNumber;
        private List<InvitationDto> invitations = new ArrayList<>();

        public TableDto(Long id, Integer tableNumber) {
            this.id = id;
            this.tableNumber = tableNumber;
        }
        // Constructor, getters y setters
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InvitationDto {
        private Long id;
        private Integer chairNumber;
        private InvitationStatus status;
        private Delegate delegate;


        public InvitationDto(Integer chairNumber, InvitationStatus status) {
            this.chairNumber = chairNumber;
            this.status = status;
        }
        // Constructor, getters y setters
    }
    public QuadrantDto(Long id, String description, String acronym, Integer quadrantOrder,
                       Long tableId, Integer tableNumber,
                       Long invitationId, Integer chairNumber, InvitationStatus status, Delegate delegate) {
        this.id = id;
        this.description = description;
        this.acronym = acronym;
        this.quadrantOrder = quadrantOrder;

        // Agrega lógica para evitar duplicados y organizar los datos en sublistas
        if (this.tables == null) {
            this.tables = new ArrayList<>();
        }
        TableDto table = this.tables.stream()
                .filter(t -> t.getId().equals(tableId))
                .findFirst()
                .orElseGet(() -> {
                    TableDto newTable = new TableDto(tableId, tableNumber);
                    this.tables.add(newTable);
                    return newTable;
                });

        if (invitationId != null) {
            table.getInvitations().add(new InvitationDto(invitationId, chairNumber, status, delegate));
        }
    }
}
