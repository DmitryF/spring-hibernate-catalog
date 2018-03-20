import './polyfills';
import 'hammerjs';
import {
  enableProdMode, TRANSLATIONS, TRANSLATIONS_FORMAT, MissingTranslationStrategy
} from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { environment } from './environments/environment';
import {TranslateService} from './app/services/translate.service';

if (environment.production) {
  enableProdMode();
}

declare const require;
let translateService = TranslateService;
const translations = require('raw-loader!./locale/messages.' + translateService.getLocale() + '.xlf');

platformBrowserDynamic().bootstrapModule(AppModule, {
	missingTranslation: MissingTranslationStrategy.Error,
	providers: [
		{provide: TRANSLATIONS, useValue: translations},
		{provide: TRANSLATIONS_FORMAT, useValue: 'xlf'}
	]
}).catch(err => console.log(err));

