package com.alten.skillsmanagement.controller;

import com.alten.skillsmanagement.dto.PositionDto;
import com.alten.skillsmanagement.model.Position;
import com.alten.skillsmanagement.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("positions")
public class PositionController {

    private PositionService positionService;

    @Autowired
    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_CONSULTANT')")
    @GetMapping("/{positionId}")
    public ResponseEntity<Position> getPosition(@PathVariable Integer positionId) {
        Position position = positionService.getPosition(positionId);
        return ResponseEntity.ok(position);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping
    public ResponseEntity<List<Position>> getPositions() {
        List<Position> positions = positionService.getPositions();
        return ResponseEntity.ok(positions);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Position> createPosition(@Valid @RequestBody PositionDto positionDto) {
        Position position = positionService.createPosition(positionDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{positionId}")
                .buildAndExpand(position.getId()).toUri();
        return ResponseEntity.created(location).body(position);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{positionId}")
    public ResponseEntity<Position> updatePosition(@PathVariable Integer positionId,
                                                   @Valid @RequestBody  PositionDto positionDto) {
        Position position = positionService.updatePosition(positionId, positionDto);
        return ResponseEntity.ok(position);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{positionId}")
    public ResponseEntity<String> deletePosition(@PathVariable Integer positionId) {
        positionService.deletePosition(positionId);
        return ResponseEntity.ok("POSITION DELETED");
    }
}
