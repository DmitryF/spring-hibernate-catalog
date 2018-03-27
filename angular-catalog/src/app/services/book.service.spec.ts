import { TestBed, async } from '@angular/core/testing';
import { inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import {Http, HttpModule, BaseRequestOptions, XHRBackend, ResponseOptions} from '@angular/http';
import { HttpClient, HttpHeaders, HttpResponse, HttpRequest, HttpEvent, HttpEventType, HttpParams, HttpClientModule } from '@angular/common/http';

import {
  HttpClientTestingModule,
  HttpTestingController
} from '@angular/common/http/testing';

import {
  BookService,
  ConfigService,
  ApiService
} from '../services';

import {Book} from '../models/book';
import {Genre} from '../models/genre';

describe('Service: BookService', () => {
  let service, mockBackend;

  beforeEach(() => TestBed.configureTestingModule({
    imports: [                
        HttpClientModule,
        HttpClientTestingModule
    ],
    providers: [       
      BookService,
      ConfigService,
      ApiService,
      { provide: XHRBackend, useClass: MockBackend }
    ]
  }));


  it('should return all books', 
    inject(
      [BookService, ConfigService, HttpTestingController], 
      (service, config, httpMock) => {

      let responseBooks = [];
      const countBooks = 3;
      for (var indexBook = 0; indexBook < countBooks; indexBook++) {
        const book: Book = new Book();
        book.id = indexBook.toString();
        book.name = "book" + indexBook.toString();
        responseBooks.push(book);      
      }
       
      service.getAllBooks().subscribe(books => {           
        expect(books).toEqual(responseBooks);      
        expect(books.length).toBe(3);
      });

      const mockReq = httpMock.expectOne(config.books_all_url);

      expect(mockReq.cancelled).toBeFalsy();
      expect(mockReq.request.responseType).toEqual('json');

      mockReq.flush(responseBooks);

      httpMock.verify();
    })
  );

  it('should return books by genre', 
    inject(
      [BookService, ConfigService, HttpTestingController], 
      (service, config, httpMock) => {

      let responseBooks = [];
      const countBooks = 3;
      let genre: Genre = new Genre();
      genre.name = 'comedy';
      for (var indexBook = 0; indexBook < countBooks; indexBook++) {
        const book: Book = new Book();
        book.id = indexBook.toString();
        book.name = "book" + indexBook.toString();
        book.genre = genre;
        responseBooks.push(book);      
      }
       
      service.getBooksByGenre('comedy').subscribe(books => {           
        expect(books).toEqual(responseBooks); 
        expect(books[0].genre.name).toContain(genre.name);     
        expect(books.length).toBe(3);
      });

      const mockReq = httpMock.expectOne(config.books_by_genre_url + genre.name);

      expect(mockReq.cancelled).toBeFalsy();
      expect(mockReq.request.responseType).toEqual('json');

      mockReq.flush(responseBooks);

      httpMock.verify();
    })
  );
});