import {Injectable} from '@angular/core';

@Injectable()
export class TranslateService {

  private static languages = ['ru', 'en'];

  private static DEFAULT_LOCALE = 'en';

  private static currentLanguage;

  static getLocale() {
    const supportLanguages: string[] = this.getLanguages();
    this.currentLanguage = localStorage.getItem('language');
    if (!this.currentLanguage) {
      const currentLocale = (navigator.language || TranslateService.DEFAULT_LOCALE);
      for (const locale of supportLanguages) {
        if (currentLocale.startsWith(locale)) {
          this.setLocale(locale);
          break;
        }
      }
    }
    console.log(this.currentLanguage);
    return this.currentLanguage;
  }

  static setLocale(locale: string) {
    this.currentLanguage = locale;
    localStorage.setItem('language', locale);
  }

  static getDefaultLocale() {
    return TranslateService.DEFAULT_LOCALE;
  }

  static getLanguages() {
    return this.languages;
  }
}


