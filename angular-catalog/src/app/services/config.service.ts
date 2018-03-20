import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

@Injectable()
export class ConfigService {

  private _api_url = '/api';//'http://localhost:8090/catalog'

  private _refresh_token_url = this._api_url + '/refresh';

  private _login_url = this._api_url + '/login';

  private _logout_url = this._api_url + '/logout';

  private _change_password_url = this._api_url + '/changePassword';

  private _whoami_url = this._api_url + '/whoami';

  private _user_url = this._api_url + '/user';

  private _users_url = this._user_url + '/all';

  private _reset_credentials_url = this._user_url + '/reset-credentials';

  private _signup_url = this._api_url + '/signup';

  private _get_books_url = this._api_url + '/book/all';

  private _get_books_by_genre_url = this._api_url + '/book/genre/';

  private _get_genres_book_url = this._api_url + '/book/genres';

  get books_url(): string {
    return this._get_books_url;
  }

  get books_by_genre_url(): string {
    return this._get_books_by_genre_url;
  }

  get genres_book_url(): string {
    return this._get_genres_book_url;
  }

  get reset_credentials_url(): string {
    return this._reset_credentials_url;
  }

  get refresh_token_url(): string {
    return this._refresh_token_url;
  }

  get whoami_url(): string {
    return this._whoami_url;
  }

  get users_url(): string {
    return this._users_url;
  }

  get login_url(): string {
    return this._login_url;
  }

  get logout_url(): string {
    return this._logout_url;
  }

  get change_password_url(): string {
    return this._change_password_url;
  }

  get signup_url(): string {
    return this._signup_url;
  }

}
