package com.example.tfuwape.flickrfindr.roomdb;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class AppDatabaseTest {


    private SearchTermDao mSearchTermDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mSearchTermDao = mDb.searchTermDao();
    }

    @After
    public void closeDb() {
        mDb.close();
    }

    @Test
    public void testInsertDeleteFetch() {
        //delete all existing records
        mSearchTermDao.deleteAll();
        List<SearchTerm> foundTerms = mSearchTermDao.getAll();
        assertThat(foundTerms.size(), equalTo(0));

        SearchTerm searchTerm = new SearchTerm();
        searchTerm.setText("football");
        mSearchTermDao.insert(searchTerm);

        SearchTerm foundTerm = mSearchTermDao.findText("football");
        assertThat(foundTerm.getText(), equalTo(searchTerm.getText()));

        //delete all created records
        mSearchTermDao.delete(foundTerm);
        foundTerms = mSearchTermDao.getAll();
        assertThat(foundTerms.size(), equalTo(0));
    }
}

