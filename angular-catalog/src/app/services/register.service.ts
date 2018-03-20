import {Injectable} from '@angular/core';
import {Http, Headers, RequestOptions} from '@angular/http';
import {User} from '../models/user';
import {Observable} from 'rxjs/Observable';
import {DataService} from '../shared/data.service';

@Injectable()
export class RegisterService {
	
	constructor(private http: Http) {
		// code...
	}

	sendUser(user: User){
		let url = DataService.BACKEND_URL + "/user/register";		
		let headers1 = new Headers({'Content-Type': 'application/json'});		
		return this.http.post(url, JSON.stringify(user), {headers: headers1});
	}
}