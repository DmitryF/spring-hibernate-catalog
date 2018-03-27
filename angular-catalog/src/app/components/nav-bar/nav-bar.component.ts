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

  constructor (private loginService: LoginService) {
    this.languages = TranslateService.getLanguages();
  }

  ngOnInit(): void {
    this.selectedLanguage = TranslateService.getLocale();
  }

  public getLoginService() {
    return this.loginService;
  }

  /**
   * Change language listener
   * @param {any} event
   */
  onLanguageChange(event: any) {
	  TranslateService.setLocale(this.selectedLanguage);
	  window.location.reload();
  }

  /**
   * Logout
   */
  logout() {
	if (this.loginService.isLogined()){
		this.loginService.logout();
	}
  }
}
