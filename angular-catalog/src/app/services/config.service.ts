import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

@Injectable()
export class ConfigService {

  private _api_url = environment.production ? '/api' : 'http://localhost:8090/api';

  private _get_books_all_url = this._api_url + '/book/all';

  private _get_books_by_genre_url = this._api_url + '/book/genre/';

  private _get_genres_all_url = this._api_url + '/genre/all';

  /**
   * Get api url for get all books
   * @return {string} api url
   */
  get books_all_url(): string {
    return this._get_books_all_url;
  }

  /**
   * Get api url for get books by genre
   * @return {string} api url
   */
  get books_by_genre_url(): string {
    return this._get_books_by_genre_url;
  }

  /**
   * Get api url for get all genres
   * @return {string} api url
   */
  get genres_all_url(): string {
    return this._get_genres_all_url;
  }
}
