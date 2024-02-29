package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;
    private UserService userService;

    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }


    public int addNote(Note note) {
        return this.noteMapper.insertNote(note);
    }

    public boolean noteAlreadyExists(int noteId){
        return this.noteMapper.getNoteById(noteId)!= null;
    }

    public List<Note> getAllNotes() {
        return this.noteMapper.getAllNoteByUser(this.userService.getCurrentUserId());
    }

    public void deleteNote(int noteId) {
         this.noteMapper.deleteNote(noteId);
    }

    public Note getNoteById(int noteId) {
        return this.noteMapper.getNoteById(noteId);
    }

    public int updateNote(Note noteToUpdate) {
        return this.noteMapper.updateNote(noteToUpdate);
    }
}
