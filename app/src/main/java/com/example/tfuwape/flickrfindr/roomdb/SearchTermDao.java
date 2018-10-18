package com.example.tfuwape.flickrfindr.roomdb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SearchTermDao {

    @Query("SELECT * FROM search_term ORDER BY creation_at LIMIT 7")
    List<SearchTerm> getAll();

    @Query("SELECT * FROM search_term WHERE text LIKE :mText ORDER BY creation_at DESC LIMIT 1")
    SearchTerm findText(String mText);

    @Insert
    void insert(SearchTerm searchTerm);

    @Delete
    void delete(SearchTerm searchTerm);

    @Query("DELETE FROM search_term")
    void deleteAll();
}
