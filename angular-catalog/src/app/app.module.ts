import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { LOCALE_ID, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';
import { LayoutModule } from 'ng2-flex-layout';

import { Http, HttpModule } from '@angular/http';
import { HttpClientModule, HttpClient} from '@angular/common/http';
import { routing } from './app.routing';

import {AppComponent} from './app.component';
import {NavBarComponent} from './components/nav-bar/nav-bar.component';
import {FooterComponent} from './components/footer/footer.component';
import {RegisterComponent} from './register/register.component';
import {LoginComponent} from './login/login.component';
import {BookComponent} from './books/book/book.component';
import {BooksComponent} from './books/books.component';
import {GenresComponent} from './genres/genres.component';

import {
  RegisterService,
  LoginService,
  UserService,
  BookService,
  ApiService,
  ConfigService,
  TranslateService
} from './services';


import {CdkTableModule} from '@angular/cdk/table';

import {
  MatAutocompleteModule,
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatCheckboxModule,
  MatChipsModule,
  MatDatepickerModule,
  MatDialogModule,
  MatExpansionModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatNativeDateModule,
  MatPaginatorModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  MatRadioModule,
  MatRippleModule,
  MatSelectModule,
  MatSidenavModule,
  MatSliderModule,
  MatSlideToggleModule,
  MatSnackBarModule,
  MatSortModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule,
  MatTooltipModule,
  MatStepperModule,
} from '@angular/material';

@NgModule({
  exports: [
    CdkTableModule,
    MatAutocompleteModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatChipsModule,
    MatStepperModule,
    MatDatepickerModule,
    MatDialogModule,
    MatExpansionModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatRippleModule,
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatSortModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
  ]
})
export class MaterialModule {}

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    FooterComponent,
    RegisterComponent,
    LoginComponent,
    BookComponent,
    BooksComponent,
    GenresComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpModule,
    HttpClientModule,
    ReactiveFormsModule,
    MaterialModule,
    LayoutModule,
    routing
  ],
  providers: [
    ApiService,
    ConfigService,
    RegisterService,
    LoginService,
    UserService,
    BookService,
    TranslateService,
    { provide: LOCALE_ID, useValue: TranslateService.getLocale() }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
