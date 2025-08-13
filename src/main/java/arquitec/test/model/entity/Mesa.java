package arquitec.test.model.entity;

import arquitec.test.model.enums.EstadoMesa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "mesas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mesa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(unique = true, nullable = false)
    private Integer numero;
    
    @Column(nullable = false)
    private Integer capacidad;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMesa estado;
    
    @Column(length = 500)
    private String ubicacion;
    
    @Column(nullable = false)
    private Boolean activa;
    
    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pedido> pedidos = new ArrayList<>();
    
    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    // Métodos de conveniencia
    public boolean estaDisponible() {
        return this.activa && this.estado == EstadoMesa.DISPONIBLE;
    }
    
    public boolean estaOcupada() {
        return this.estado == EstadoMesa.OCUPADA;
    }
    
    public boolean estaReservada() {
        return this.estado == EstadoMesa.RESERVADA;
    }
}
