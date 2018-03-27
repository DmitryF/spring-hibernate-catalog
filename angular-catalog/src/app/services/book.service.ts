import { Injectable } from '@angular/core';
import { Headers } from '@angular/http';
import { ApiService } from './api.service';
import { ConfigService } from './config.service';
import {Subject} from 'rxjs/Subject';

@Injectable()
export class BookService {

  private genreChangeSource = new Subject<string>();

  genreChanged = this.genreChangeSource.asObservable();

  constructor(
    private apiService: ApiService,
    private config: ConfigService
  ) { }

  /**
   * Get all books
   */
  getAllBooks() {
    return this.apiService.get(this.config.books_all_url);
  }

  /**
   * Select genre
   * @param {string} genre
   */
  selectGenre(genre: string) {
    this.genreChangeSource.next(genre);
  }

  /**
   * Get book by genre
   * @param {string} genre
   */
  getBooksByGenre(genre: string) {
    return this.apiService.get(this.config.books_by_genre_url + genre);
  }

  /**
   * Get all genres
   */
  getAllGenres() {
    return this.apiService.get(this.config.genres_all_url);
  }
}
