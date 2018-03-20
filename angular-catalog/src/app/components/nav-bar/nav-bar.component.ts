import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {LoginService} from '../../services/login.service';
import {TranslateService} from '../../services';

@Component({
	selector: 'app-nav-bar',
	styleUrls: ['./nav-bar.component.css'],
	templateUrl: './nav-bar.component.html',
  encapsulation: ViewEncapsulation.None
})
export class NavBarComponent implements OnInit{

	selectedLanguage;

	languages: string[];

	public getLoginService() {
		return this.loginService;
	}

	constructor (private loginService: LoginService) {
	  this.languages = TranslateService.getLanguages();
	  console.log(this.languages);
	}

  onLanguageChange(event: any) {
	  TranslateService.setLocale(this.selectedLanguage);
	  window.location.reload();
  }

  ngOnInit(): void {
    this.selectedLanguage = TranslateService.getLocale();
  }

	logout() {
		if (this.loginService.isLogined()){
			this.loginService.logout();
		}
	}
}
