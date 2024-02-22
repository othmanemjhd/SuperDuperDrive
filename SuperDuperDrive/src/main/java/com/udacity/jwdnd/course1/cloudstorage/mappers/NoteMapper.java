package com.udacity.jwdnd.course1.cloudstorage.mappers;


import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Note getNotebyId(int noteid);

    @Select("SELECT * FROM NOTES WHERE userid=#{userid}")
    List<Note> getAllNoteByUser(int userid);


    @Insert("INSERT INTO NOTES (notetitle,notedescription,userid) " +
            "VALUES (#{notetitle},#{notedescription},#{userid}")
    @Options(useGeneratedKeys = true,keyProperty = "noteid")
    int insertNote(Note note);


    @Delete("DELETE FROM NOTE WHERE noteid = #{noteid}")
    void deleteNote(int noteid);
}
