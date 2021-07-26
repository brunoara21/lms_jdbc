/**
 * 
 */
package com.ss.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.ss.lms.dao.AuthorDAO;
import com.ss.lms.dao.BookAuthorsDAO;
import com.ss.lms.dao.BookCopiesDAO;
import com.ss.lms.dao.BookDAO;
import com.ss.lms.dao.BookGenresDAO;
import com.ss.lms.dao.BookLoansDAO;
import com.ss.lms.dao.BorrowerDAO;
import com.ss.lms.dao.GenreDAO;
import com.ss.lms.dao.LibraryBranchDAO;
import com.ss.lms.dao.PublisherDAO;
import com.ss.lms.model.Author;
import com.ss.lms.model.BaseModel;
import com.ss.lms.model.Book;
import com.ss.lms.model.BookAuthors;
import com.ss.lms.model.BookCopies;
import com.ss.lms.model.BookGenres;
import com.ss.lms.model.BookLoans;
import com.ss.lms.model.Borrower;
import com.ss.lms.model.Genre;
import com.ss.lms.model.LibraryBranch;
import com.ss.lms.model.Publisher;

/**
 * @author Bruno
 *
 */
public class AdminService extends BaseService{

	Util util = new Util();

	public String add(BaseModel toAdd) {
		if (toAdd instanceof Publisher) {
			return addPublisher((Publisher) toAdd);
		} else if (toAdd instanceof Author) {
			return addAuthor((Author) toAdd);
		} else if (toAdd instanceof Genre) {
			return addGenre((Genre) toAdd);
		} else if (toAdd instanceof LibraryBranch) {
			return addLibraryBranch((LibraryBranch) toAdd);
		} else if (toAdd instanceof Book) {
			return addBook((Book) toAdd);
		} else if (toAdd instanceof Borrower) {
			return addBorrower((Borrower) toAdd);
		}

		return "Something went wrong when adding.";
	}

	public String update(BaseModel toUpdate) {
		if (toUpdate instanceof Publisher) {
			return updatePublisher((Publisher) toUpdate);
		} else if (toUpdate instanceof Author) {
			return updateAuthor((Author) toUpdate);
		} else if (toUpdate instanceof Genre) {
			return updateGenre((Genre) toUpdate);
		} else if (toUpdate instanceof LibraryBranch) {
			return updateLibraryBranch((LibraryBranch) toUpdate);
		} else if (toUpdate instanceof Book) {
			return updateBook((Book) toUpdate);
		} else if (toUpdate instanceof Borrower) {
			return updateBorrower((Borrower) toUpdate);
		}

		return "Something went wrong when updating.";
	}

	public String delete(BaseModel toDelete) {
		if (toDelete instanceof Publisher) {
			return deletePublisher((Publisher) toDelete);
		} else if (toDelete instanceof Author) {
			return deleteAuthor((Author) toDelete);
		} else if (toDelete instanceof Genre) {
			return deleteGenre((Genre) toDelete);
		} else if (toDelete instanceof LibraryBranch) {
			return deleteLibraryBranch((LibraryBranch) toDelete);
		} else if (toDelete instanceof Book) {
			return deleteBook((Book) toDelete);
		} else if (toDelete instanceof Borrower) {
			return deleteBorrower((Borrower) toDelete);
		}

		return "Something went wrong when deleting.";
	}

	

	public String addBook(Book toAdd) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BookDAO bdao = new BookDAO(conn);
				BookAuthorsDAO badao = new BookAuthorsDAO(conn);
				BookGenresDAO bgdao = new BookGenresDAO(conn);

				Integer pK = bdao.createBook(toAdd);
				toAdd.setBookId(pK);
				
				for (Author a : toAdd.getBookAuthors()) {
					if (a != null) {
						BookAuthors bookAuthor = new BookAuthors();
						bookAuthor.setValues(Arrays.asList(a, toAdd));
						badao.createBookAuthors(bookAuthor);
					} 
				}

				for (Genre g : toAdd.getBookGenres()) {
					if (g != null) {
						BookGenres bookGenre = new BookGenres();
						bookGenre.setValues(Arrays.asList(g, toAdd));
						bgdao.createBookGenres(bookGenre);
					}
				}

				conn.commit();
				return "Book added successfully";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Book could not be added";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Book could not be added, problem with connection.";
		}
	}

	public String addAuthorToBook(Book toUpdate, Author toAdd) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BookAuthorsDAO badao = new BookAuthorsDAO(conn);
				BookAuthors ba = new BookAuthors();
				ba.setValues(Arrays.asList(toAdd, toUpdate));
				badao.createBookAuthors(ba);
				conn.commit();
				return "Author '" + toAdd.getName() + "' successfully added into Book";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Author could not be added";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Author could not be added, problem with connection.";
		}
	}
	
	public String deleteAuthorFromBook(Book toUpdate, Author toDelete) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BookAuthorsDAO badao = new BookAuthorsDAO(conn);
				BookAuthors ba = new BookAuthors();
				ba.setValues(Arrays.asList(toDelete, toUpdate));
				badao.deleteBookAuthors(ba);
				conn.commit();
				return "Author '" + toDelete.getName() + "' successfully deleted from Book";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Author could not be deleted";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Author could not be deleted, problem with connection.";
		}
	}
	
	public String updateAuthorFromBook(Book book, Author toUpdate, Author fromUpdate) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BookAuthorsDAO badao = new BookAuthorsDAO(conn);
				BookAuthors ba = new BookAuthors();
				ba.setValues(Arrays.asList(toUpdate, book));
				badao.updateBookAuthors_Author(ba, fromUpdate.getAuthorId());
				conn.commit();
				return "Author '" + toUpdate.getName() + "' to '"+ fromUpdate.getName()+"' successfully updated from Book";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Author could not be updated";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Author could not be updated, problem with connection.";
		}
	}
	
	public String addGenreToBook(Book toUpdate, Genre toAdd) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BookGenresDAO bgdao = new BookGenresDAO(conn);
				BookGenres bg = new BookGenres();
				bg.setValues(Arrays.asList(toAdd, toUpdate));
				bgdao.createBookGenres(bg);
				conn.commit();
				return "Genre '" + toAdd.getName() + "' successfully added into Book";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Genre could not be added";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Genre could not be added, problem with connection.";
		}
	}
	
	public String deleteGenreFromBook(Book toUpdate, Genre toDelete) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BookGenresDAO bgdao = new BookGenresDAO(conn);
				BookGenres bg = new BookGenres();
				bg.setValues(Arrays.asList(toDelete, toUpdate));
				bgdao.deleteBookGenres(bg);
				conn.commit();
				return "Genre '" + toDelete.getName() + "' successfully deleted from Book";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Genre could not be deleted";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Genre could not be deleted, problem with connection.";
		}
	}
	
	public String updateGenreFromBook(Book book, Genre toUpdate, Genre fromUpdate) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BookGenresDAO bgdao = new BookGenresDAO(conn);
				BookGenres bg = new BookGenres();
				bg.setValues(Arrays.asList(toUpdate, book));
				bgdao.updateBookGenres_Genre(bg, fromUpdate.getGenreId());
				conn.commit();
				return "Genre '" + toUpdate.getName() + "' to '"+ fromUpdate.getName()+"' successfully updated from Book";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Genre could not be updated";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Genre could not be updated, problem with connection.";
		}
	}
	
	public String updatePublisherFromBook(Book book, Publisher toUpdate) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BookDAO bdao = new BookDAO(conn);
				String oldPublisher = book.getPublisher().getName();
				book.setPublisher(toUpdate);
				bdao.updateBook(book);
				
				conn.commit();
				return "Publisher from '" + oldPublisher + "' to '" + toUpdate.getName() + "' successfully updated for Book";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Publisher could not be updated";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Publisher could not be updated, problem with connection.";
		}
	}
	
	public String updateTitleFromBook(Book book, String toUpdate) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BookDAO bdao = new BookDAO(conn);
				book.setTitle(toUpdate);
				bdao.updateBook(book);
				
				conn.commit();
				return "Title successfully updated to '" + toUpdate + "' from Book";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Title could not be updated";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Title could not be updated, problem with connection.";
		}
	}
	
	public String updateBook(Book toUpdate) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BookDAO pdao = new BookDAO(conn);
				pdao.updateBook(toUpdate);
				conn.commit();
				return "Book '" + toUpdate.getTitle() + "' successfully updated";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Book could not be updated";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Book could not be updated, problem with connection.";
		}
	}

	public String deleteBook(Book toDelete) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BookDAO bdao = new BookDAO(conn);
				bdao.deleteBook(toDelete);
				conn.commit();
				return "Book '" + toDelete.getTitle() + "' successfully deleted";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Book could not be deleted";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Book could not be deleted, problem with connection.";
		}
	}

	public List<Book> readBooks() {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BookDAO bdao = new BookDAO(conn);
				BookAuthorsDAO badao = new BookAuthorsDAO(conn);
				BookGenresDAO bgdao = new BookGenresDAO(conn);
				BookCopiesDAO bcdao = new BookCopiesDAO(conn);

				List<Book> books = bdao.readAllBooks();
				for(Book b: books) {
					b.setBookAuthors(badao.readAllBookAuthors_Authors(b.getBookId()));
					b.setBookGenres(bgdao.readAllBookGenres_Genres(b.getBookId()));
					b.setBookBranches(bcdao.readAllBookCopies_Branches(b.getBookId()));
				}
				
				return books;
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String addAuthor(Author toAdd) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				AuthorDAO adao = new AuthorDAO(conn);
				Integer pK = adao.createAuthor(toAdd);
				toAdd.setAuthorId(pK);
				conn.commit();
				return "Author '" + toAdd.getAuthorName() + "' added successfully";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Author could not be added";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Author could not be added, problem with connection.";
		}
	}

	public String updateAuthor(Author toUpdate) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				AuthorDAO adao = new AuthorDAO(conn);
				adao.updateAuthor(toUpdate);
				conn.commit();
				return "Author '" + toUpdate.getAuthorName() + "' successfully updated";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Author could not be updated";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Author could not be updated, problem with connection.";
		}
	}

	public String deleteAuthor(Author toDelete) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				AuthorDAO adao = new AuthorDAO(conn);
				adao.deleteAuthor(toDelete);
				conn.commit();
				return "Author '" + toDelete.getAuthorName() + "' successfully deleted";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Author could not be deleted";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Author could not be deleted, problem with connection.";
		}
	}

	public List<Author> readAuthors() {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				AuthorDAO adao = new AuthorDAO(conn);
				List<Author> authors = adao.readAllAuthors();
				// TODO POPULATE CHILD ELEMENTS
				return authors;
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Author readAuthor(Integer pK) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				AuthorDAO adao = new AuthorDAO(conn);
				Author author = adao.readAuthor(pK);
				// TODO POPULATE CHILD ELEMENTS
				return author;
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String addPublisher(Publisher toAdd) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				PublisherDAO pdao = new PublisherDAO(conn);
				Integer pK = pdao.createPublisher(toAdd);
				toAdd.setPublisherId(pK);
				conn.commit();
				return "Publisher '" + toAdd.getPublisherName() + "' added successfully";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Publisher could not be added";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Publisher could not be added, problem with connection.";
		}
	}

	public String updatePublisher(Publisher toUpdate) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				PublisherDAO pdao = new PublisherDAO(conn);
				pdao.updatePublisher(toUpdate);
				conn.commit();
				return "Publisher '" + toUpdate.getPublisherName() + "' successfully updated";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Publisher could not be updated";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Publisher could not be updated, problem with connection.";
		}
	}

	public String deletePublisher(Publisher toDelete) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				PublisherDAO pdao = new PublisherDAO(conn);
				pdao.deletePublisher(toDelete);
				conn.commit();
				return "Publisher '" + toDelete.getPublisherName() + "' successfully deleted";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Publisher could not be deleted";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Publisher could not be deleted, problem with connection.";
		}
	}

	public List<Publisher> readPublishers() {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				PublisherDAO pdao = new PublisherDAO(conn);
				List<Publisher> publishers = pdao.readAllPublishers();
				// TODO POPULATE CHILD ELEMENTS
				return publishers;
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public Publisher readPublisher(Integer pK) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				PublisherDAO pdao = new PublisherDAO(conn);
				Publisher publisher = pdao.readPublisher(pK);
				// TODO POPULATE CHILD ELEMENTS
				return publisher;
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String addGenre(Genre toAdd) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				GenreDAO pdao = new GenreDAO(conn);
				Integer pK = pdao.createGenre(toAdd);
				toAdd.setGenreId(pK);
				conn.commit();
				return "Genre '" + toAdd.getGenreName() + "' added successfully";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Genre could not be added";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Genre could not be added, problem with connection.";
		}
	}

	public String updateGenre(Genre toUpdate) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				GenreDAO pdao = new GenreDAO(conn);
				pdao.updateGenre(toUpdate);
				conn.commit();
				return "Genre '" + toUpdate.getGenreName() + "' successfully updated";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Genre could not be updated";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Genre could not be updated, problem with connection.";
		}
	}

	public String deleteGenre(Genre toDelete) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				GenreDAO pdao = new GenreDAO(conn);
				pdao.deleteGenre(toDelete);
				conn.commit();
				return "Genre '" + toDelete.getGenreName() + "' successfully deleted";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Genre could not be deleted";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Genre could not be deleted, problem with connection.";
		}
	}

	public List<Genre> readGenres() {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				GenreDAO pdao = new GenreDAO(conn);
				List<Genre> genres = pdao.readAllGenres();
				// TODO POPULATE CHILD ELEMENTS
				return genres;
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Genre readGenre(Integer pK) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				GenreDAO gdao = new GenreDAO(conn);
				Genre genre = gdao.readGenre(pK);
				// TODO POPULATE CHILD ELEMENTS
				return genre;
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String addBorrower(Borrower toAdd) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BorrowerDAO pdao = new BorrowerDAO(conn);
				Integer pK = pdao.createBorrower(toAdd);
				toAdd.setCardNo(pK);
				conn.commit();
				return "Borrower '" + toAdd.getBorrowerName() + "' added successfully";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Borrower could not be added";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Borrower could not be added, problem with connection.";
		}
	}

	public String updateBorrower(Borrower toUpdate) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BorrowerDAO pdao = new BorrowerDAO(conn);
				pdao.updateBorrower(toUpdate);
				conn.commit();
				return "Borrower '" + toUpdate.getBorrowerName() + "' successfully updated";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Borrower could not be updated";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Borrower could not be updated, problem with connection.";
		}
	}

	public String deleteBorrower(Borrower toDelete) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BorrowerDAO pdao = new BorrowerDAO(conn);
				pdao.deleteBorrower(toDelete);
				conn.commit();
				return "Borrower '" + toDelete.getBorrowerName() + "' successfully deleted";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Borrower could not be deleted";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Borrower could not be deleted, problem with connection.";
		}
	}

	public List<Borrower> readBorrowers() {

		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BorrowerDAO pdao = new BorrowerDAO(conn);
				List<Borrower> borrowers = pdao.readAllBorrowers();
				// TODO POPULATE CHILD ELEMENTS
				return borrowers;
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Borrower readBorrower(Integer cardNo) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BorrowerDAO bdao = new BorrowerDAO(conn);
				Borrower borrower = bdao.readBorrower(cardNo);
				// TODO POPULATE CHILD ELEMENTS
				return borrower;
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<BookLoans> readBookLoansFromBorrower(Borrower borrower) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BookLoansDAO bldao = new BookLoansDAO(conn);
				List<BookLoans> bookLoans = bldao.readAllBookLoans_FromBorrower(borrower);
				
				// TODO POPULATE CHILD ELEMENTS
				return bookLoans;
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
public String updateDueDate(BookLoans bookLoan) {
	Connection conn = null;
	try {
		try {
			conn = util.getConnection();
			BookLoansDAO bldao = new BookLoansDAO(conn);
			bldao.updateBookLoans(bookLoan);
			conn.commit();
			return "Book Loan succesfully updated. New due date is '" + bookLoan.getDueDate().toLocalDateTime().toLocalDate() + "'";

		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Book Loan could not be updated";

		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	} catch (SQLException e) {
		e.printStackTrace();
		return "Book Loan could not be updated, problem with connection.";
	}
}
	
	public String addLibraryBranch(LibraryBranch toAdd) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				LibraryBranchDAO pdao = new LibraryBranchDAO(conn);
				Integer pK = pdao.createLibraryBranch(toAdd);
				toAdd.setBranchId(pK);
				conn.commit();
				return "Library Branch '" + toAdd.getBranchName() + "' added successfully";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Library Branch could not be added";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Library Branch could not be added, problem with connection.";
		}
	}

	public String updateLibraryBranch(LibraryBranch toUpdate) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				LibraryBranchDAO pdao = new LibraryBranchDAO(conn);
				pdao.updateLibraryBranch(toUpdate);
				conn.commit();
				return "Library Branch '" + toUpdate.getBranchName() + "' successfully updated";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Library Branch could not be updated";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Library Branch could not be updated, problem with connection.";
		}
	}

	public String deleteLibraryBranch(LibraryBranch toDelete) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				LibraryBranchDAO pdao = new LibraryBranchDAO(conn);
				pdao.deleteLibraryBranch(toDelete);
				conn.commit();
				return "Library Branch '" + toDelete.getBranchName() + "' successfully deleted";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Library Branch could not be deleted";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Library Branch could not be deleted, problem with connection.";
		}
	}

	public List<LibraryBranch> readLibraryBranches() {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				LibraryBranchDAO pdao = new LibraryBranchDAO(conn);
				List<LibraryBranch> publishers = pdao.readAllLibraryBranches();
				// TODO POPULATE CHILD ELEMENTS
				return publishers;
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public LibraryBranch readLibraryBranch(Integer pK) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
				LibraryBranch libBranch = lbdao.readLibraryBranch(pK);
				// TODO POPULATE CHILD ELEMENTS
				return libBranch;
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String addBookCopies(BookCopies toAdd) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BookCopiesDAO bcdao = new BookCopiesDAO(conn);
				bcdao.createBookCopies(toAdd);
				conn.commit();
				return "Book Copies added successfully";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Book Copies could not be added";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Book Copies could not be added, problem with connection.";
		}
	}
}
