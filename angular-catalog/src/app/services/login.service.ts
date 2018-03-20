import {Injectable} from '@angular/core';
import {Http, Headers} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {DataService} from '../shared/data.service';

@Injectable()
export class LoginService {
	
	token: string;


	constructor(private http: Http) {
		// code...
	}

	sendCredential(model){
		let tokenUrl1 = DataService.BACKEND_URL + "/user/login";		
		let headers1 = new Headers({'Content-Type':'application/json'});
		console.log(JSON.stringify(model));
		return this.http.post(tokenUrl1, JSON.stringify(model),
			 {headers: headers1});
	}

	sendToken(token, userName) {
		let tokenUrl2 = DataService.BACKEND_URL + "/rest/user/userName";		
		console.log('Bearer ' + token);

		let getHeaders = new Headers({'Authorization':'Bearer ' + token});
		return this.http.post(tokenUrl2, userName, {headers: getHeaders});
	}

	logout() {
		localStorage.setItem("token","");
		localStorage.setItem("currentUserName", '');
		alert("You just logged out.");
	}

	isLogined() {
		if (localStorage.getItem("currentUserName") != null &&
			localStorage.getItem("currentUserName") != '' &&
			localStorage.getItem("token") != null &&
			localStorage.getItem("token") != ''){			
		    return true;
		} else {
			return false;
		}
	}
}
