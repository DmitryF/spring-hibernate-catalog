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

  getBooks() {
    return this.apiService.get(this.config.books_url);
  }

  selectGenre(genre: string) {
    this.genreChangeSource.next(genre);
  }

  getBooksByGenre(genre: string) {
    return this.apiService.get(this.config.books_by_genre_url + genre);
  }

  getGenres() {
    return this.apiService.get(this.config.genres_book_url);
  }
}
