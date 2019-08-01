package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.dao.MongoTagsDAO;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.Silent.class)
public class TagsServiceTest {

    @Mock
    private MongoTagsDAO mongoTagsDAO;
    @Mock
    private Mapper tagMapper;

    private TagsService testObject;

    @Before
    public void init() {
        testObject = new TagsService(mongoTagsDAO, tagMapper);
    }




}