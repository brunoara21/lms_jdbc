package com.ss.lms.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ AuthorDAOTest.class, BorrowerDAOTest.class, PublisherDAOTest.class, GenreDAOTest.class,
		LibraryBranchDAOTest.class, BookDAOTest.class, BookAuthorsDAOTest.class, BookGenresDAOTest.class,
		BookCopiesDAOTest.class, BookLoansDAOTest.class })
public class TestSuite {

}
