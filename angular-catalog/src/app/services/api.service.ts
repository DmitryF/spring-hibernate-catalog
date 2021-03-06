import { HttpClient, HttpHeaders, HttpResponse, HttpRequest, HttpEventType, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';
import 'rxjs/add/observable/throw';
import { serialize } from '../shared/utilities/serialize';
import {TranslateService} from '../services/translate.service';

export enum RequestMethod {
  Get = 'GET',
  Head = 'HEAD',
  Post = 'POST',
  Put = 'PUT',
  Delete = 'DELETE',
  Options = 'OPTIONS',
  Patch = 'PATCH'
}

@Injectable()
export class ApiService {

  headers = new HttpHeaders({
    'Accept': 'application/json',
    'Content-Type': 'application/json',
    'Accept-Language': TranslateService.getLocale()
  });

  constructor( private http: HttpClient) { }

  /**
   * GET http request
   * @param  {string}          path request path
   * @param  {any}             args additional args
   * @return {Observable<any>}      request callback
   */
  get(path: string, args?: any): Observable<any> {
    const options = {
      headers: this.headers,
      withCredentials: false
    };

    if (args) {
      options['params'] = serialize(args);
    }

    return this.http.get(path, options)
      .catch(this.checkError.bind(this));
  }

  /**
   * POST http request
   * @param  {string}          path          request path
   * @param  {any}             body          request body
   * @param  {HttpHeaders}     customHeaders additional headers
   * @return {Observable<any>}               request callback
   */
  post(path: string, body: any, customHeaders?: HttpHeaders): Observable<any> {
    return this.request(path, body, RequestMethod.Post, customHeaders);
  }

  /**
   * PUT http request
   * @param  {string}          path          request path
   * @param  {any}             body          request body   
   * @return {Observable<any>}               request callback
   */
  put(path: string, body: any): Observable<any> {
    return this.request(path, body, RequestMethod.Put);
  }

  /**
   * DELETE http request
   * @param  {string}          path          request path
   * @param  {any}             body          request body   
   * @return {Observable<any>}               request callback
   */
  delete(path: string, body?: any): Observable<any> {
    return this.request(path, body, RequestMethod.Delete);
  }

  /**
   * Send http request
   * @param  {string}          path          request path
   * @param  {any}             body          request body  
   * @param  {[type]}          method        =             RequestMethod.Post request type
   * @param  {HttpHeaders}     custemHeaders additional headers
   * @return {Observable<any>}               request callback
   */
  private request(path: string, body: any, method = RequestMethod.Post, custemHeaders?: HttpHeaders): Observable<any> {
    const req = new HttpRequest(method, path, body, {
      headers: custemHeaders || this.headers,
      withCredentials: true
    });

    return this.http.request(req)
      .filter(response => response instanceof HttpResponse)
      .map((response: HttpResponse<any>) => response.body)
      .catch(error => this.checkError(error));
  }
  
  /**
   * Display error if logged in, otherwise redirect to IDP
   * @param  {any} error
   * @return {any} 
   */
  private checkError(error: any): any {
    if (error && error.status === 401) {
      // this.redirectIfUnauth(error);
    } else {
      // this.displayError(error);
    }
    throw error;
  }

}
