package com.prismasolutions.DWbackend.controller;


import com.prismasolutions.DWbackend.dto.comment.CommentDto;
import com.prismasolutions.DWbackend.dto.diploma.DiplomaDto;
import com.prismasolutions.DWbackend.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comment")
@CrossOrigin
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CommentDto commentDto){
        try {
            return ResponseEntity.ok().body(commentService.create(commentDto));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
            commentService.delete(id);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-by-diploma/{diplomaID}")
    public ResponseEntity<?> getByDiploma(@PathVariable Long diplomaID){
        try {
            return ResponseEntity.ok().body(commentService.getByDiploma(diplomaID));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
