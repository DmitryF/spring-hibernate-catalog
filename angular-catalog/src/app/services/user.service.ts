import {Injectable} from '@angular/core';
import {Http, Headers} from '@angular/http';

import {DataService} from '../shared/data.service';

import {User} from '../models/user';

@Injectable()
export class UserService {

	users: User[];


	constructor(private http: Http) {
		// code...
	}

	getUsers() {

	}

	getUserById(id: string) {

	}

	getUserByName(username: string) {
		const tokenUrl = DataService.BACKEND_URL + '/rest/user/userName';
    const headers = new Headers({'Content-Type': 'application/json', 'Authorization': 'Bearer ' + localStorage.getItem('token')});
		return this.http.post(tokenUrl, username, {headers: headers});
	}
}
