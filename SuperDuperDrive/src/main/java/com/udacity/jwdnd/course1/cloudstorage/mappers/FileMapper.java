package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE fileId = #{id}")
    File getFileById(Integer id);

    @Select("SELECT * FROM FILE WHERE userid = #{userid}")
    List<File> getAllFilesByuserId(int userid);
    @Insert("INSERT INTO FILES (filename,contenttype,filesize,userid,filedata) " +
            "VALUES (#{filename},#{contenttype},#{filesize},#{userid},#{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);


    @Delete("DELETE FROM FILE WHERE fileId = #{fileId}")
    void deleteFile(int fileId);

}
